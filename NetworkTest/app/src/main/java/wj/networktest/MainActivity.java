package wj.networktest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    final String IP = "197.168.100.20"; //genymotion host
//    final int PORT = 33000; // port number
    final String IP = "211.189.132.190"; //genymotion host
    final int PORT = 33000; // port number

    TextView tv;
    SocketNetwork toServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);

        toServer = new SocketNetwork(IP, PORT, mHandler);
        toServer.start();

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //소켓 연결 성공!
                case HandlerPosition.SOCKET_CONNECT_SUCCESS:
                    cTimer.cancel();
                    cTimer.start();
                    tv.append("Socket Connect Success\n");
                    break;
                //소켓 연결 실패!
                case HandlerPosition.SOCKET_CONNECT_ERROR:
                    tv.append("Socket Connect Error\n");
                    retryConnection();
                    break;
                //연결 중 서버가 죽었을 때
                case HandlerPosition.READ_SERVER_DISCONNECT_ERROR:
                    tv.append("Server Disconnect Error(Read)\n");
                    retryConnection();
                    break;
                //데이터 전송이 실패했을 때
                case HandlerPosition.WRITE_SERVER_DISCONNECT_ERROR:
                    tv.append("Server Disconnect Error(Write)\n");
                    retryConnection();
                    break;
                //데이터 수신 성공!
                case HandlerPosition.DATA_READ_SUCESS:
                    cTimer.cancel();
                    cTimer.start();
                    for (int i = 0; i < Data.readData.length; i++)
                        tv.append(String.format("%02x ", Data.readData[i]));
                    tv.append("\n");
                    break;
                //잘못된 데이터가 왔을 때
                case HandlerPosition.READ_DATA_ERROR:
                    tv.append("Server Read Data Error\n");
                    break;
                //타임아웃!
                case HandlerPosition.READ_TIMEOUT_ERROR:
                    tv.append("\nTime out!\n");
                    retryConnection();
                    cTimer.cancel();
                    cTimer.start();
                    break;


            }
        }
    };

    private void retryConnection() {
        cTimer.cancel();
        toServer.close();
        toServer = new SocketNetwork(IP, PORT, mHandler);
        toServer.start();
    }
    SocketReadTimeout cTimer = new SocketReadTimeout(1000 * 60 * 30, 1000* 60, mHandler);

    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                toServer.writeData(new byte[]{
                        0x01, 0x22, 0x00, 0x01, 0x00, 0x00, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, (byte) 0xAB
                        , (byte) 0xCD, (byte) 0xEF, 0x00, 0x01, 0x00, 0x00, 0x00, 0x59, 0x31, 0x36
                        , 0x30, 0x39, 0x32, 0x33, 0x30, 0x39, 0x30, 0x38, 0x35, 0x35
                        , 0x31, 0x36, 0x30, 0x39, 0x32, 0x33, 0x30, 0x39, 0x30, 0x38
                        , 0x35, 0x35, 0x00, 0x00, 0x00, 0x00, 0x07, 0x5B, (byte) 0xCD, 0x15
                        , 0x31, 0x31, 0x39, 0x32, 0x30, 0x33, 0x30, 0x31, 0x30, 0x30
                        , 0x00, 0x39, 0x36, (byte) 0x86, 0x00, (byte) 0xC1, (byte) 0xF9, 0x4B, 0x00, (byte) 0xF5
                        , 0x01, 0x0E, 0x00, 0x00, 0x07, 0x39, (byte) 0x8C, (byte) 0xD9, 0x00, 0x01
                        , 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00


//                        //헤더
//                        0x01, 0x33, 0x00, 0x05, 0x12, 0x34, 0x56, 0x78, 0x12, 0x34, 0x56, 0x78, 0x00, 0x02, 0x00, 0x00, 0x00, 0x36
//                        //날짜, 시간
//                        , 0x31, 0x36, 0x30, 0x39, 0x32, 0x38, 0x30, 0x39, 0x33, 0x37, 0x32, 0x33
//                        //단말기 ID
//                        , 0x00, 0x00, 0x00, (byte) 0xE3, 0x06, (byte) 0xE4, (byte) 0x96, (byte) 0xCE
//                        //노선정보
//                        , 0x00, 0x00, 0x00, 0x00, 0x07, 0x5B, (byte) 0xCD, 0x15
//                        , 0x31, 0x31, 0x39, 0x32, 0x30, 0x33, 0x30, 0x01, 0x00, 0x01
//                        //앞차 정류소간격,이격시간,차량번호
//                        , 0x21, 0x11, 0x01, 0x2E
//                        //뒷차 정류소간격,이격시간,차량번호
//                        , 0x22, 0x12, 0x01, 0x2F
//                        //잔여거리,남은시간,예약
//                        , 0x04, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00
                });
        }
    }

    @Override
    protected void onDestroy() {
        toServer.close();
    }

}
