package wj.readfiletest;

import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    File file;
    TextView tv;
    int line;
    int i = 0;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);

        tv.setText("");
        // sample.txt 파일을 File 객체로 가져온다.161011 location.log
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "161011 location.log");

        FileReader reader = null;

//        fileWrite();

        String s;
        try {
            reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            while ((s = br.readLine()) != null) {
                if (s.charAt(0) == '#') {
                    String y[] = s.split(",");
                    String x[] = y[0].split("#");

                    Util.x.add(Double.parseDouble(x[1]));
                    Util.y.add(Double.parseDouble(y[1]));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        sleep(1000);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
        };

        thread.start();
    }


    public void fileWrite() {
        try {
            Log.e("SaveData: ", "11");
            try {
                Log.e("SaveData: ", "22");
                //데이터 추가가 가능한 파일 작성자(FileWriter 객체생성)
                FileWriter wr = new FileWriter(file, true); //두번째 파라미터 true: 기존파일에 추가할지 여부를 나타냅니다.

                PrintWriter writer = new PrintWriter(wr);
                writer.println("test\n테스트\n1234\n");
                writer.close();

            } catch (IOException e) {
                Log.e("SaveData: ", "33");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (i < Util.x.size()) {
                        tv.append(Util.x.get(i) + ", " + Util.y.get(i) + "\n");
                        i++;
                    } else {
                        thread.interrupt();
                    }
                    break;
            }
        }
    };
}
