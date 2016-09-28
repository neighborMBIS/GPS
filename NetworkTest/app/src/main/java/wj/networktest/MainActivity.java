package wj.networktest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String IP = "127.0.0.1"; //genymotion host
    int PORT = 12345; // port number
    TextView tv;

    ToServer toServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);

        toServer = new ToServer(IP, PORT, mHandler);
        toServer.start();
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HandlerPosition.SOCKET_CONNECT_ERROR:
                    tv.append("Socket Conect Error\n");
                    toServer.close();
                    retryConnection();
                    break;

                case HandlerPosition.READ_SERVER_DISCONNECT_ERROR:
                    tv.append("Server Disconnect Error(Read)\n");
                    retryConnection();
                    break;


                case HandlerPosition.WRITE_SERVER_DISCONNECT_ERROR:
                    tv.append("Server Disconnect Error(Write)\n");
                    toServer.close();
                    retryConnection();
                    break;

                case HandlerPosition.READ_SUCESS:
                    tv.append("read success\n");
//                    for (int i = 0; i < Data.readData.length; i++)
//                        tv.append(String.format("%02x ", Data.readData[i]));
                    break;
            }
        }
    };
    private void retryConnection() {
        toServer = new ToServer(IP, PORT, mHandler);
        toServer.start();
    }

    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                toServer.writeData(new byte[]{
                        //헤더
                        0x01, 0x31, 0x00, 0x05, 0x12, 0x34, 0x56, 0x78, 0x12, 0x34, 0x56, 0x78, 0x00, 0x02, 0x00, 0x00, 0x00, 0x36
                        //날짜, 시간
                        , 0x31, 0x36, 0x30, 0x39, 0x32, 0x38, 0x30, 0x39, 0x33, 0x37, 0x32, 0x33
                        //단말기 ID
                        , 0x00, 0x00, 0x00, (byte) 0xE3, 0x06, (byte) 0xE4, (byte) 0x96, (byte) 0xCE
                        //노선정보
                        , 0x00, 0x00, 0x00, 0x00, 0x07, 0x5B, (byte) 0xCD, 0x15
                        , 0x31, 0x31, 0x39, 0x32, 0x30, 0x33, 0x30, 0x01, 0x00, 0x01
                        //앞차 정류소간격,이격시간,차량번호
                        , 0x21, 0x11, 0x01, 0x2E
                        //뒷차 정류소간격,이격시간,차량번호
                        , 0x22, 0x12, 0x01, 0x2F
                        //잔여거리,남은시간,예약
                        , 0x04, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00
                });
        }
    }

}
