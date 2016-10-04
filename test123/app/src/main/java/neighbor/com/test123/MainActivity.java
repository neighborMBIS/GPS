package neighbor.com.test123;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import neighbor.com.test123.ddd.Data;
import neighbor.com.test123.ddd.Func;
import neighbor.com.test123.ddd.HandlerPosition;
import neighbor.com.test123.ddd.MapVal;
import neighbor.com.test123.ddd.NetworkUtil;
import neighbor.com.test123.ddd.SocketNetwork;

public class MainActivity extends AppCompatActivity {

    //통신 변수들
    TextView tv;

    EditText getPhone;
    EditText getBusNum;

    SocketNetwork sNetwork;

    MapVal mv = MapVal.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sNetwork = new SocketNetwork(NetworkUtil.IP, NetworkUtil.PORT, mHandler);
        sNetwork.start();

        getPhone = (EditText) findViewById(R.id.phoneNum);
        getBusNum = (EditText) findViewById(R.id.busNum);

        getPhone.setText("12312312");
        getBusNum.setText("304");

        tv = (TextView) findViewById(R.id.textView);

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerPosition.DATA_READ_SUCESS:
                    new Receive_OP(Data.readData[BytePosition.HEADER_OPCODE]);
                    Toast.makeText(getApplicationContext(), "from. Server : " + mv.getDeviceID(), Toast.LENGTH_SHORT).show();
                    String dd = "";
                    for(int i=0 ; i < Data.readData.length ; i++) {
                        dd = dd + String.format("%02d ", Data.readData[i]);
                    }
                    tv.append(dd + "\n");
                    tv.append("Device ID : " + mv.getDeviceID() + "\n");
                    break;
            }
        }
    };

    public void nextPage(View v) {
        switch (v.getId()) {
            case R.id.sendButton:
                TimeZone jst = TimeZone.getTimeZone("JST");
                Calendar cal = Calendar.getInstance(jst);

                String date = String.format("%02d", cal.get(Calendar.YEAR) - 2000) + String.format("%02d", (cal.get(Calendar.MONTH) + 1)) + String.format("%02d", cal.get(Calendar.DATE));
                String time = String.format("%02d", ((cal.get(Calendar.HOUR_OF_DAY)) + 9)) + String.format("%02d", (cal.get(Calendar.MINUTE))) + String.format("%02d", cal.get(Calendar.SECOND));
                byte[] dt = Func.stringToByte(date + time);
                byte[] phone = Func.integerToByte(Integer.parseInt(getPhone.getText().toString()), 4);
                byte[] bus = Func.integerToByte(Integer.parseInt(getBusNum.getText().toString()), 2);
                byte[] res = Func.integerToByte(mv.getReservation(), 4);
                byte[] header = new byte[] { 0x01, 0x03, 0x00, 0x00, 0x00
                        , 0x00, 0x00, 0x00, 0x00, 0x00
                        , 0x00, 0x00, 0x00, 0x00, 0x00
                        , 0x00, 0x00, 0x16 };


                Data.writeData = Func.mergyByte(header, Func.mergyByte(Func.mergyByte(dt, phone), Func.mergyByte(bus, res)));

                sNetwork.writeData(Data.writeData);

                break;
            case R.id.login:
                if(mv.getDeviceID() != 0) {
                    sNetwork.close();
                    finish();
                    startActivity(new Intent(this, NextActivity.class));
                }
                Toast.makeText(this, "from. Server : " + mv.getDeviceID(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
