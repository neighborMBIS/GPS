package wj.timertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView time, log;
    Button button;
    int count = 30;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = (TextView) findViewById(R.id.timer);
        log = (TextView) findViewById(R.id.log);
        button = (Button) findViewById(R.id.button);
        log.append("\n");

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(count > 0) {
                            time.setText(String.valueOf(count));
                        } else {
                            time.setText("Time out!");
                            count = 30;
                            log.append("\nTime out!");
                        }
                    }
                });
                count--;
            }
        }, 1000, 1000);
    }

    public void restart(View v) {
        switch (v.getId()) {
            case R.id.button:
                count = 30;
                time.setText(String.valueOf(count));
                break;
        }
    }
}
