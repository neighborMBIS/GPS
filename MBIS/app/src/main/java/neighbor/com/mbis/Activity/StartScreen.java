package neighbor.com.mbis.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import neighbor.com.mbis.R;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        Handler h = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                startActivity(new Intent(StartScreen.this, LoginActivity.class));
                finish();
            }
        };
        h.sendEmptyMessageDelayed(0, 2000);
       /* h.sendEmptyMessageDelayed(0, 2500);*/
    }
}
