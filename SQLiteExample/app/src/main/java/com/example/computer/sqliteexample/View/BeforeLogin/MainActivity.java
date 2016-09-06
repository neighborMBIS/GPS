package com.example.computer.sqliteexample.View.BeforeLogin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computer.sqliteexample.DBManager.InfoDBManager;
import com.example.computer.sqliteexample.R;
import com.example.computer.sqliteexample.View.AfterLogin.SuccessLoginActivity;


public class MainActivity extends ActionBarActivity {

    ImageButton gomembership = null;
    ImageButton gosuccesslogin = null;

    EditText getid = null;
    EditText getpw = null;

    public InfoDBManager mDbManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gomembership = (ImageButton)findViewById(R.id.goMembershipActivity);
        gosuccesslogin = (ImageButton)findViewById(R.id.goSuccessLoginActivity);

        getid = (EditText)findViewById(R.id.getid);
        getpw = (EditText)findViewById(R.id.getpw);


        mDbManager = InfoDBManager.getInstance(this);

        //============================================//
        ContentValues addRowValue = new ContentValues();
        //============================================//
        //================초기DB설정====================//
        //============================================//
        addRowValue.put("_id", "aa");
        addRowValue.put("pw", "aa");
        addRowValue.put("name", "홍길동");
        addRowValue.put("phone", "010-1111-2222");
        mDbManager.insert( addRowValue );
        //============================================//
        addRowValue.put("_id", "bb");
        addRowValue.put("pw", "ab");
        addRowValue.put("name", "홍길서");
        addRowValue.put("phone", "010-3333-4444");
        mDbManager.insert( addRowValue );
        //============================================//
        addRowValue.put("_id", "cc");
        addRowValue.put("pw", "ac");
        addRowValue.put("name", "홍길북");
        addRowValue.put("phone", "010-5555-6666");
        mDbManager.insert( addRowValue );
        //============================================//
        addRowValue.put("_id", "dd");
        addRowValue.put("pw", "ad");
        addRowValue.put("name", "홍길남");
        addRowValue.put("phone", "010-7777-8888");
        mDbManager.insert( addRowValue );
        //============================================//
        addRowValue.put("_id", "ee");
        addRowValue.put("pw", "ae");
        addRowValue.put("name", "홍길중");
        addRowValue.put("phone", "010-9999-0000");
        mDbManager.insert( addRowValue );
        //============================================//
        addRowValue.put("_id", "ff");
        addRowValue.put("pw", "af");
        addRowValue.put("name", "홍길녀");
        addRowValue.put("phone", "010-1234-5678");
        mDbManager.insert( addRowValue );
        //============================================//
        //================초기DB설정====================//
        //============================================//
    }

    public void goSuccessLoginActivity(View v) {

        String[] columns = new String[] { "_id", "pw", "name", "phone" };

        Cursor c = mDbManager.query( columns, null, null, null, null, null);

        if( c != null )
        {
            while( c.moveToNext() ) {
                String _id = c.getString(0);
                String pw = c.getString(1);

                if(getid.getText().toString().equals(_id)) {

                    if(getpw.getText().toString().equals(pw)) {
                        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, SuccessLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        intent.putExtra("getID", getid.getText().toString());
                        intent.putExtra("getPW", getpw.getText().toString());

                        startActivity(intent);
                        startActivityForResult(intent, 0);
                    }  else {
                        Toast.makeText(this, "아이디가 없거나 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            c.close();
        }

    }

//    public void test(View v) {
//
//        String[] columns = new String[] { "_id", "pw", "name", "phone" };
//
//        Cursor c = mDbManager.query( columns, null, null, null, null, null);
//
//        if( c != null )
//        {
//            tt.setText( "" );
//
//            while( c.moveToNext() )
//            {
//                String  _id          = c.getString( 0 );
//                String  pw      = c.getString( 1 );
//                String  name        = c.getString( 2 );
//                String  phone  = c.getString(3);
//
//                tt.append(
//                        "id : " + _id + "\n" +
//                                "pw : " + pw + "\n" +
//                                "name : " + name + "\n" +
//                                "phone : " + phone + "\n" +
//                                "-----------------------------------------------");
//            }
//
//            tt.append("\n Total : " + c.getCount());
//
//            c.close();
//        }
//    }

    public void goMembershipActivity(View v) {
        startActivity(new Intent(this, MembershipActivity.class));
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
