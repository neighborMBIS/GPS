package neighbor.com.mbis.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import neighbor.com.mbis.Function.FileManage;
import neighbor.com.mbis.Function.Func;
import neighbor.com.mbis.Function.Setter;
import neighbor.com.mbis.MapUtil.BytePosition;
import neighbor.com.mbis.MapUtil.Data;
import neighbor.com.mbis.MapUtil.Form.Form_Header;
import neighbor.com.mbis.MapUtil.HandlerPosition;
import neighbor.com.mbis.MapUtil.MakeFile.MakeRouteFile;
import neighbor.com.mbis.MapUtil.MakeFile.MakeRouteStationFile;
import neighbor.com.mbis.MapUtil.MakeFile.MakeStationFile;
import neighbor.com.mbis.MapUtil.OPUtil;
import neighbor.com.mbis.MapUtil.Receive_OP;
import neighbor.com.mbis.MapUtil.Thread.SocketNetwork;
import neighbor.com.mbis.MapUtil.Thread.SocketReadTimeout;
import neighbor.com.mbis.MapUtil.Value.MapVal;
import neighbor.com.mbis.Network.NetworkUtil;
import neighbor.com.mbis.R;


public class LoginActivity extends AppCompatActivity {

    //통신 변수들
    TextView tv;

    EditText getPhone;
    EditText getBusNum;

    FileManage eventFileManage;
    SocketNetwork sNetwork;

    MapVal mv = MapVal.getInstance();
    Form_Header h = Form_Header.getInstance();
    static byte[] headerBuf = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sNetwork = new SocketNetwork(NetworkUtil.IP, NetworkUtil.PORT, mHandler);
        sNetwork.start();

        TimeZone jst = TimeZone.getTimeZone("JST");
        Calendar cal = Calendar.getInstance(jst);
        String packetFileName = String.format("%02d", cal.get(Calendar.YEAR) - 2000) + String.format("%02d", (cal.get(Calendar.MONTH) + 1)) + String.format("%02d", cal.get(Calendar.DATE)) + " packet";

        eventFileManage = new FileManage(packetFileName);

        getPhone = (EditText) findViewById(R.id.phoneNum);
        getBusNum = (EditText) findViewById(R.id.busNum);
        getPhone.setText("12341234");
        getBusNum.setText("5678");
//
//        tv = (TextView) findViewById(R.id.textView);
        findViewById(R.id.sendButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerPosition.DATA_READ_SUCESS:
                    recvData(Data.readData[BytePosition.HEADER_OPCODE]);
                    break;

                case HandlerPosition.TIME_CHANGE:
                    break;
                //소켓 연결 성공!
                case HandlerPosition.SOCKET_CONNECT_SUCCESS:
                    retryCountdownTimer();
                    break;
                //소켓 연결 실패!
                case HandlerPosition.SOCKET_CONNECT_ERROR:
                    retryConnection();
                    break;
                //연결 중 서버가 죽었을 때
                case HandlerPosition.READ_SERVER_DISCONNECT_ERROR:
                    retryConnection();
                    break;
                //데이터 전송이 실패했을 때
                case HandlerPosition.WRITE_SERVER_DISCONNECT_ERROR:
                    retryConnection();
                    break;
                //잘못된 데이터가 왔을 때
                case HandlerPosition.READ_DATA_ERROR:
                    retryConnection();
                    retryCountdownTimer();
                    break;
                //타임아웃!
                case HandlerPosition.READ_TIMEOUT_ERROR:
                    retryConnection();
                    retryCountdownTimer();
                    break;
            }
        }
    };

    public void nextPage(View v) {
        switch (v.getId()) {
            case R.id.sendButton:

                if (getPhone.length() == 8 || getPhone.length() == 7) {
                    if (getBusNum.length() > 0 && getBusNum.length() < 5) {

                        byte[] op = new byte[]{0x03};
                        mv.setDataLength(BytePosition.BODY_USER_CERTIFICATION_SIZE - BytePosition.HEADER_SIZE);
                        h.setOp_code(op);
                        Setter.setHeader();
                        byte[] otherBusInfo = makeBodyOtherBusInfo();
                        makeHeader();

                        Data.writeData = Func.mergyByte(headerBuf, otherBusInfo);

                        sendData();
                    } else {
                        Toast.makeText(getApplicationContext(), "차량번호를 다시 한 번 확인 해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "전화번호를 다시 한 번 확인 해 주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cheat:
                sNetwork.close();
                cTimer.cancel();
                mv.setDeviceID(2567812341234L);
                finish();
                startActivity(new Intent(getApplicationContext(), SelectRouteActivity.class));
                break;
            case R.id.zzzzz:
                Data.writeData = new byte[] {
                        0x01, 0x11, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x00, 0x00, 0x00, (byte) 0x32
                        , 0x31, 0x36, 0x31, 0x30, 0x32, 0x37, 0x31, 0x34, 0x33, 0x36, 0x30, 0x30
                        , 0x31, 0x36, 0x31, 0x30, 0x32, 0x37, 0x31, 0x34, 0x33, 0x36, 0x30, 0x30
                        , 0x00, 0x39, 0x36, 0x73, 0x00, (byte) 0xC1, (byte) 0xF9, 0x7F, 0x00, 0x00, 0x00, 0x00
                        , 0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01
                        , 0x00, 0x00, 0x00, 0x00
                };
                sendData();
                Toast.makeText(this, "send data", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] makeBodyOtherBusInfo() {
        TimeZone jst = TimeZone.getTimeZone("JST");
        Calendar cal = Calendar.getInstance(jst);

        String date = String.format("%02d", cal.get(Calendar.YEAR) - 2000) + String.format("%02d", (cal.get(Calendar.MONTH) + 1)) + String.format("%02d", cal.get(Calendar.DATE));
        String time = String.format("%02d", ((cal.get(Calendar.HOUR_OF_DAY)) + 9)) + String.format("%02d", (cal.get(Calendar.MINUTE))) + String.format("%02d", cal.get(Calendar.SECOND));
        byte[] dt = Func.stringToByte(date + time);
        byte[] phone = Func.integerToByte(Integer.parseInt(getPhone.getText().toString()), 4);
        byte[] bus = Func.integerToByte(Integer.parseInt(getBusNum.getText().toString()), 2);
        byte[] res = Func.integerToByte(mv.getReservation(), 4);

        return Func.mergyByte(Func.mergyByte(dt, phone), Func.mergyByte(bus, res));
    }

    private byte[] makeHeader() {
        headerBuf = new byte[BytePosition.HEADER_SIZE];

        putHeader(h.getVersion(), BytePosition.HEADER_VERSION_START);
        putHeader(h.getOp_code(), BytePosition.HEADER_OPCODE);
        putHeader(h.getSr_cnt(), BytePosition.HEADER_SRCNT);
        putHeader(h.getDeviceID(), BytePosition.HEADER_DEVICEID);
        putHeader(h.getLocalCode(), BytePosition.HEADER_LOCALCODE);
        putHeader(h.getDataLength(), BytePosition.HEADER_DATALENGTH);

        return headerBuf;
    }

    private void putHeader(byte[] b, int position) {
        System.arraycopy(b, 0, headerBuf, position, b.length);
    }

    private void retryConnection() {
        cTimer.cancel();
        sNetwork.close();
        sNetwork = new SocketNetwork(NetworkUtil.IP, NetworkUtil.PORT, mHandler);
        sNetwork.start();
    }

    private void retryCountdownTimer() {
        cTimer.cancel();
        cTimer.start();
    }

    SocketReadTimeout cTimer = new SocketReadTimeout(HandlerPosition.SERVER_READ_TIMEOUT, mHandler);

    private void sendData() {
        String dd = "";
        for (int j = 0; j < Data.writeData.length; j++) {
            dd = dd + String.format("%02X ", Data.writeData[j]);
        }
        eventFileManage.saveData("\n(" + mv.getSendYear() + ":" + mv.getSendMonth() + ":" + mv.getSendDay() +
                " - " + mv.getSendHour() + ":" + mv.getSendMin() + ":" + mv.getSendSec() +
                ")\n[SEND:" + Data.writeData.length + "] - " + dd);

        sNetwork.writeData(Data.writeData);
    }

    private void recvData(byte opCode) {
        String dd = "";
        for (int i = 0; i < Data.readData.length; i++) {
            dd = dd + String.format("%02x ", Data.readData[i]);
//            readText.append(String.format("%02x ", Data.readData[i]));
        }
        TimeZone jst = TimeZone.getTimeZone("JST");
        Calendar cal = Calendar.getInstance(jst);
        String packetFileName = String.format("%02d", cal.get(Calendar.YEAR) - 2000) + String.format("%02d", (cal.get(Calendar.MONTH) + 1)) + String.format("%02d", cal.get(Calendar.DATE)) + " packet";

        eventFileManage.saveData("\n(" + cal.get(Calendar.YEAR) + ":" + (cal.get(Calendar.MONTH) + 1) + ":" + cal.get(Calendar.DATE) +
                " - " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) +
                ")\n[RECV:" + Data.readData.length + "] - " + dd);
//        tv.append("\n");

//        new Receive_OP(Data.readData[BytePosition.HEADER_OPCODE]);
        new Receive_OP(opCode);
        if(opCode == OPUtil.OP_USER_CERTIFICATION_AFTER_DEVICEID_SEND) {
            userCertificationSuccess();
        } else if(opCode == OPUtil.OP_ROUTE_DATA_INFO) {
            new MakeRouteFile();
        } else if(opCode == OPUtil.OP_STATION_DATA_INFO) {
            new MakeStationFile();
        } else if(opCode == OPUtil.OP_ROUTE_STATION_DATA_INFO) {
            new MakeRouteStationFile();
        }
    }



    private void userCertificationSuccess() {
        if (mv.getDeviceID() != 0) {
            sNetwork.close();
            cTimer.cancel();
            finish();
            startActivity(new Intent(getApplicationContext(), SelectRouteActivity.class));
            Toast.makeText(getApplicationContext(), "[인증 성공] from. Server : " + mv.getDeviceID(), Toast.LENGTH_SHORT).show();
        }
    }
}
