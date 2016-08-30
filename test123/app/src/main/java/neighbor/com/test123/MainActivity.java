package neighbor.com.test123;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    //통신 변수들
    Socket socket;
    TextView tv;

    final String IP = "127.0.0.1"; //genymotion host
    final int PORT = 12345; // port number

    static boolean serverShutdown = true;

    private InputStream is;
    private OutputStream os;
    private DataInputStream dis;
    private DataOutputStream dos;

    private Thread thread;
    String recv="";

    byte[] b = new byte[]{
            50, 55, 49, 57, 52, 49,
            50, 55, 49, 57, 52, 49,
            0x50, 0x55, 0x49, 0x57, 52, 49,
            50, 55, 49, 57, 52, 49,
            0x11, 0x22
//            ,
//                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19,
//                0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29,
//                0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39,
//                0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49,
//                0x50, 0x51, 0x52, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59,
//                0x60, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69,
    };
    String s = "test1234";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
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

                    sendData(b);

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
                    byte[] bb = new byte[25];

                    dis.read(bb);
                    for(int i=0 ; i<bb.length ; i++) {
                        recv = recv + String.format("%02X ", bb[i]);
                        if(i%10 == 9) {
                            recv = recv + "\n";
                        }
                    }
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

    public void sendData(String str) {        //메세지를 보내는 메서드
        try {
            byte[] bb;
            bb = str.getBytes("ksc5601");    //한글로 바꿔주는 작업
            if (socket != null) {
                dos.write(bb); //.writeUTF(str);
                Log.d("[sendData]", " send String Data !!");
            } else {
                Log.d("[sendData]", " Failed send String Data null !!");
            }
        } catch (IOException e) {
            Log.d("[sendData]", " Failed send String Data !!");
            connectServer();
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
            if(msg.what==1) {
                tv.append("\n" + recv);
                recv = "";
            }
        }
    };
}
