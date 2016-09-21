package wj.timertest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView time, log;
    Button button;

    BusLocationInfoTimer busTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = (TextView) findViewById(R.id.timer);
        log = (TextView) findViewById(R.id.log);
        button = (Button) findViewById(R.id.button);
        log.append("\n");

        busTimerTask = new BusLocationInfoTimer(mHandler);
        busTimerTask.start();
    }

    public void restart(View v) {
        switch (v.getId()) {
            case R.id.button:
                MessagePosition.count = 30;
                time.setText(String.valueOf(MessagePosition.count));
                break;
        }
    }

    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case MessagePosition.SEND_BUS_LOCATION_INFO:
                    time.setText(String.valueOf(MessagePosition.count));
                    log.append("\nTime Out!!");
                    MessagePosition.count = 30;
                    break;
                case MessagePosition.TIME_CHANGE:
                    time.setText(String.valueOf(MessagePosition.count));
                    break;
            }
        }
    };
}
