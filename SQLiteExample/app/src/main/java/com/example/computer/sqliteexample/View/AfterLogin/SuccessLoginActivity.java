package com.example.computer.sqliteexample.View.AfterLogin;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.computer.sqliteexample.Manager.ActivityManager;
import com.example.computer.sqliteexample.R;
import com.example.computer.sqliteexample.View.BeforeLogin.MainActivity;


public class SuccessLoginActivity extends ActionBarActivity {

    String getid;
    String getpw;
    private ActivityManager actManager = ActivityManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actManager.addActivity(this);
        setContentView(R.layout.activity_success_login);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        getid = bundle.getString("getID");
        getpw = bundle.getString("getPW");

    }

    public void goListActivity(View v) {
        startActivity(new Intent(this, ListActivity.class));
    }

    public void goMyinfoActivity(View v) {

        Intent i = new Intent();
        i.setClass(this, MyInfoActivity.class);
        i.putExtra("getID", getid);
        i.putExtra("getPW", getpw);
        startActivityForResult(i, 0);
    }

    public void LogOut(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private long mExitModeTime=0L;
    public void onBackPressed() {
        if(mExitModeTime != 0 && SystemClock.uptimeMillis() - mExitModeTime < 2000) {
            finish();
        } else {
            Toast.makeText(this, "한번 더 취소 버튼을 누르면 프로그램이 종료됩니다.", Toast.LENGTH_LONG).show();

            mExitModeTime = SystemClock.uptimeMillis();
        }
    }

}
