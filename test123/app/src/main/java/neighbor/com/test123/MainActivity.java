package neighbor.com.test123;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    //통신 변수들
    Socket socket;
    TextView tv;

    static String IP = "127.0.0.1"; //genymotion host
    static int PORT = 12345; // port number

    private InputStream is;
    private OutputStream os;
    private DataInputStream dis;
    private DataOutputStream dos;

    private Thread thread;

    EditText getIp;
    EditText getPort;
    EditText getPhone;
    EditText getBusNum;

    static byte[] sendData;
    static byte[] recvData;

    static long deviceID;
    String recv = "";

    byte[] buf = new byte[8];
    String did;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIp = (EditText) findViewById(R.id.ipNum);
        getPort = (EditText) findViewById(R.id.portNum);
        getPhone = (EditText) findViewById(R.id.phoneNum);
        getBusNum = (EditText) findViewById(R.id.busNum);

        getIp.setText("197.168.100.20");
        getPort.setText("33000");
        getPhone.setText("12312312");
        getBusNum.setText("304");

        tv = (TextView) findViewById(R.id.textView);
    }


    //서버와 연결하는 메소드
    public void connectServer() {
        new Thread() {
            public void run() {
                try {
                    socket = new Socket(IP, PORT);
                    Log.d("[Client]", " Server connected !!");

                    is = socket.getInputStream();
                    dis = new DataInputStream(is);
                    os = socket.getOutputStream();
                    dos = new DataOutputStream(os);


                    thread = new Thread(new ReceiveMsg());
                    thread.setDaemon(true);
                    thread.start();
                } catch (Exception e) {
                    run();
                    Log.d("[ChatActivity]", " connectServer() Exception !!");
                }
            }
        }.start();

    }

    // 내부클래스로 서버에서 받은 메세지를 처리
    class ReceiveMsg implements Runnable {

        @SuppressWarnings("null")
        @Override
        public synchronized void run() {
            while (true) {
                try {
                    //바이트 크기는 넉넉하게 잡아서 할 것.
                    //가변적으로 못바꾸니 넉넉하게 잡고 알아서 fix 하기
                    byte[] bb = new byte[1024];

                    dis.read(bb);
                    System.arraycopy(bb, 12, buf, 0, 8);

                    for (int i = 0; i < buf.length; i++) {
                        recv = recv + String.format("%02X ", buf[i]);
                        if (i % 10 == 9) {
                            recv = recv + "\n";
                        }
                    }

//                    recv.trim();

//                    deviceID = Func.byteToLong(buf);

                    mHandler.sendEmptyMessage(1);
                } catch (IOException e) {
                    //e.printStackTrace();
                    //status = false;
                    try {
                        os.close();
                        is.close();
                        dos.close();
                        dis.close();
                        socket.close();
                        break;
                    } catch (IOException e1) {
                    }
                }
            }
        }
    }

    public void sendData(byte[] data) {        //메세지를 보내는 메서드
        try {
            if (socket != null) {
                dos.write(data);
                Log.d("[sendData]", " send byte Data !!");
            } else {
                Log.d("[sendData]", " Failed send byte Data null !!");
            }
        } catch (IOException e) {
            Log.d("[sendData]", " Failed send byte Data !!");
            connectServer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            os.close();
            is.close();
            dos.close();
            dis.close();
            socket.close();
        } catch (IOException e) {
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                tv.append("\n" + recv);
                tv.append("\n" + String.format("%08d", Func.byteToLong(buf)));
                recv = "";
            }
        }
    };

    public void nextPage(View v) {
        switch (v.getId()) {
            case R.id.setIpPort:
                IP = getIp.getText().toString();
                PORT = Integer.parseInt(getPort.getText().toString());
//                Toast.makeText(this, "IP, PORT 설정 완료!", Toast.LENGTH_SHORT).show();
                connectServer();
                break;
            case R.id.sendButton:
                TimeZone jst = TimeZone.getTimeZone("JST");
                Calendar cal = Calendar.getInstance(jst);

                String date = String.format("%02d", cal.get(Calendar.YEAR) - 2000) + String.format("%02d", (cal.get(Calendar.MONTH) + 1)) + String.format("%02d", cal.get(Calendar.DATE));
                String time = String.format("%02d", ((cal.get(Calendar.HOUR_OF_DAY)) + 9)) + String.format("%02d", (cal.get(Calendar.MINUTE))) + String.format("%02d", cal.get(Calendar.SECOND));
                byte[] dt = Func.stringToByte(date + time);
                did = String.format("%08d", Integer.parseInt(getPhone.getText().toString())) +
                        String.format("%04d", Integer.parseInt(getBusNum.getText().toString()));
//                byte[] phone = Func.integerToByte(Integer.parseInt(getPhone.getText().toString()), 4);
//                byte[] bus = Func.integerToByte(Integer.parseInt(getBusNum.getText().toString()), 2);
                byte[] dID = Func.longToByte(Long.parseLong(did), 8);
                byte[] res = new byte[]{0x00, 0x00};

                sendData = Func.mergyByte(Func.mergyByte(dt, dID), res);

                sendData(sendData);
                break;
            case R.id.login:
                if(Func.byteToLong(buf) == Long.parseLong(did)) {
                    finish();
                    startActivity(new Intent(this, NextActivity.class));
                    try {
                        os.close();
                        is.close();
                        dos.close();
                        dis.close();
                        socket.close();
                    } catch (IOException e) {
                    }
                } else {
                    Toast.makeText(this, "from. Server : " + Func.byteToLong(buf) + "\nMy data : " + Long.parseLong(did), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
