package wj.networktest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by user on 2016-09-27.
 */

public class ToServer extends Thread {
    private String IP;
    private int PORT;
    private Socket socket;

    public static boolean runFlag = true;

    private Handler mHandler;
    private SocketAddress sAddress;

    private InputStream is;
    private DataInputStream dis;
    private OutputStream os;
    private DataOutputStream dos;


//    private static ToServer ourInstance = null;
//
//    public static ToServer getInstance(String IP, int PORT, Handler mHandler) {
//        if (ourInstance == null) {
//            ourInstance = new ToServer(IP, PORT, mHandler);
//        }
//        return ourInstance;
//    }

    public ToServer(String IP, int PORT, Handler mHandler) {
        runFlag = true;
        this.IP = IP;
        this.PORT = PORT;
        this.mHandler = mHandler;

        socket = new Socket();
        sAddress = new InetSocketAddress(IP, PORT);
    }

    @SuppressWarnings("null")
    @Override
    public synchronized void run() {
        try {
            //타임아웃 : 5초
            socket.connect(sAddress, HandlerPosition.SERVER_CONNECT_TIMEOUT);
//            socket = new Socket(this.IP, this.PORT);

            is = socket.getInputStream();
            dis = new DataInputStream(is);
            os = socket.getOutputStream();
            dos = new DataOutputStream(os);

            Log.d("[Client]", " Server connected !!");
        } catch (IOException e) {
            mHandler.sendEmptyMessage(HandlerPosition.SOCKET_CONNECT_ERROR);
        }
        if (dis != null) {
            readData();
        }
    }

    public void writeData(byte[] data) {
        try {
            if (socket != null && dos != null) {
                dos.write(data);
                Log.d("[sendData]", " send byte Data !!");
            } else {
                mHandler.sendEmptyMessage(HandlerPosition.WRITE_SERVER_DISCONNECT_ERROR);
                Log.d("[sendData]", " Failed send byte Data null !!");
            }
        } catch (IOException e) {
            mHandler.sendEmptyMessage(HandlerPosition.WRITE_SERVER_DISCONNECT_ERROR);
            Log.d("[sendData]", " Failed send byte Data !!");
        }
    }

    public void close() {
        try {
            runFlag = false;
            if(os != null && is != null &&
                    dos != null && dis != null) {
                os.close();
                is.close();
                dos.close();
                dis.close();
            }
            socket.close();
        } catch (IOException e) {
        }
    }

    private void readData() {
        while (runFlag) {
            try {
                //바이트 크기는 넉넉하게 잡아서 할 것.
                //가변적으로 못바꾸니 넉넉하게 잡고 알아서 fix 하기
                if (dis.read() == -1) {
                    //서버의 연결이 끊김 = 재연결 시도 메세지를 핸들러에 보냄
                    mHandler.sendEmptyMessage(HandlerPosition.READ_SERVER_DISCONNECT_ERROR);
                    close();
                } else {
                    byte[] bb = new byte[18];
                    dis.read(bb);

                    byte[] dataLengthBuf = new byte[4];
                    for (int i = 0; i < 4; i++) {
                        dataLengthBuf[i] = bb[i + 14];
                    }
                    int dataLength = Func.byteToInteger(dataLengthBuf);
                    byte[] bodyData = new byte[dataLength];
                    dis.read(bodyData);

                    Data.readData = bodyData;
                    mHandler.sendEmptyMessage(HandlerPosition.READ_SUCESS);
                }
            } catch (IOException e) {
                //e.printStackTrace();
                //status = false;
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
