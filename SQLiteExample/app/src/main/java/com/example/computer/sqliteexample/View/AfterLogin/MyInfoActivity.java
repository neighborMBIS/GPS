package com.example.computer.sqliteexample.View.AfterLogin;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.computer.sqliteexample.DBManager.InfoDBManager;
import com.example.computer.sqliteexample.Manager.ActivityManager;
import com.example.computer.sqliteexample.R;


public class MyInfoActivity extends ActionBarActivity {

    EditText Oid = null;
    EditText Opw = null;
    EditText Oname = null;
    EditText Ophone = null;

    ImageButton Finish = null;
    ImageButton Back = null;
    ImageButton Reset = null;

    String getid = null;
    String getpw = null;

    private ActivityManager actManager = ActivityManager.getInstance();
    public InfoDBManager mDbManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        actManager.addActivity(this);
        mDbManager = InfoDBManager.getInstance(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        getid = bundle.getString("getID");
        getpw = bundle.getString("getPW");

        Oid = (EditText) findViewById(R.id.outputid);
        Opw = (EditText) findViewById(R.id.outputpw);
        Oname = (EditText) findViewById(R.id.outputname);
        Ophone = (EditText) findViewById(R.id.outputphone);

        Finish = (ImageButton) findViewById(R.id.finish);
        Back = (ImageButton) findViewById(R.id.back);
        Reset = (ImageButton)findViewById(R.id.reset);

        String[] columns = new String[]{"_id", "pw", "name", "phone"};
        Cursor c = mDbManager.query(columns, null, null, null, null, null);

        if (c != null) {
            String Sid = null;
            String Spw = null;
            String Sname = null;
            String Sphone = null;

            while (c.moveToNext()) {
                if (getid.equals(c.getString(0))) {
                    Sid = c.getString(0);
                    Spw = c.getString(1);
                    Sname = c.getString(2);
                    Sphone = c.getString(3);
                    break;
                }
            }

            Oid.setText(Sid);
            Opw.setHint(Spw);
            Oname.setHint(Sname);
            Ophone.setHint(Sphone);
        }

    }


    public void Rollback(View v) {
//        startActivity(new Intent(this, SuccessLoginActivity.class));
        finish();
    }


    public void Commit(View v) {

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("정말 정보를 수정하시겠습니까?").setCancelable(false).setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ContentValues updateRowValue = new ContentValues();

                        if (Opw.getText().toString().equals("")) {
                            updateRowValue.put("pw", Opw.getHint().toString());
                        } else {
                            updateRowValue.put("pw", Opw.getText().toString());
                        }

                        if (Oname.getText().toString().equals("")) {
                            updateRowValue.put("name", Oname.getHint().toString());
                        } else {
                            updateRowValue.put("name", Oname.getText().toString());
                        }

                        if (Ophone.getText().toString().equals("")) {
                            updateRowValue.put("phone", Ophone.getHint().toString());
                        } else {
                            updateRowValue.put("phone", Ophone.getText().toString());
                        }
                        mDbManager.update(updateRowValue,
                                "_id='" + Oid.getText().toString() + "'",
                                null);
                        Toast.makeText(getApplicationContext(), "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alt_bld.create();
        alert.setTitle("다시 한번 묻겠다.");
        //alert.setIcon(R.drawable.icon);
        //경고메세지 아이콘 그림
        alert.show();
    }


    public void AllReset(View v) { AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("정말 정보를 모두 삭제하시겠습니까?").setCancelable(false).setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        mDbManager.delete("_id='" + Oid.getText().toString() + "'", null);
                        actManager.finishAllActivity();
                    }
                }).setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alt_bld.create();
        alert.setTitle("다시 한번 묻겠다.");
        //alert.setIcon(R.drawable.icon);
        //경고메세지 아이콘 그림
        alert.show();

    }



}
