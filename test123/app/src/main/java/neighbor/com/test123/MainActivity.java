package neighbor.com.test123;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    //통신 변수들
    Socket socket;
    final String IP = "127.0.0.1"; //genymotion host
    final int PORT = 12345; // port number


    private InputStream is;
    private OutputStream os;
    private DataInputStream dis;
    private DataOutputStream dos;

    private Thread thread;

    byte[] b = new byte[]{
        0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19,
                0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29,
                0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39,
                0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49,
                0x50, 0x51, 0x52, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59,
                0x60, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69,
    };
    String s = "test1234";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectServer();
    }

    //서버와 연결하는 메소드
    public void connectServer() {
        new Thread() {
            public void run() {
                try {
                    socket = new Socket(IP, PORT);
                    Log.d("[Client]", " Server connected !!");

                    //자바에서 한거랑 똑같으니 참고하기
                    is = socket.getInputStream();
                    dis = new DataInputStream(is);
                    os = socket.getOutputStream();
                    dos = new DataOutputStream(os);

                    //서버에 접속하면 성별을 보내는 메서드 호출
//                    dos.write(0x01);
                    Log.d("[ChatActivity]", " connectServer() Success !!");

                    thread = new Thread(new ReceiveMsg());
                    thread.setDaemon(true);
                    thread.start();

                } catch (Exception e) {
                    e.printStackTrace();
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
                    byte[] b = new byte[4096];
                    dis.read(b);

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
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void sendData(byte[] data) {        //메세지를 보내는 메서드
        try {
            dos.write(data);
            Log.d("[sendData]", " send byte Data !!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendData(String str) {        //메세지를 보내는 메서드
        try {
            byte[] bb;
            bb = str.getBytes("ksc5601");    //한글로 바꿔주는 작업
            dos.write(bb); //.writeUTF(str);
            Log.d("[sendData]", " send String Data !!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                sendData(s);
                break;
            case R.id.button2:
                sendData(b);
                break;
        }
    }
}
