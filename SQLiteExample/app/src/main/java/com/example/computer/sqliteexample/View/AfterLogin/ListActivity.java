package com.example.computer.sqliteexample.View.AfterLogin;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.computer.sqliteexample.DBManager.InfoDBManager;
import com.example.computer.sqliteexample.R;



public class ListActivity extends Activity {

    public InfoDBManager mDbManager = null;
    TextView tt = null;
    RadioButton byID = null;
    RadioButton byName = null;
    RadioButton byPhone = null;
    EditText input = null;
    RadioGroup rg;

    static int TYPE_OF_SEARCH = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        byID = (RadioButton)findViewById(R.id.byID);
        byName = (RadioButton)findViewById(R.id.byName);
        byPhone = (RadioButton)findViewById(R.id.byPhone);
        input = (EditText)findViewById(R.id.inputsearch);
        tt = (TextView)findViewById(R.id.outputAll);
        rg = (RadioGroup)findViewById(R.id.rg);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId== R.id.byID) {
                    TYPE_OF_SEARCH = 0;
                } else if(checkedId== R.id.byName) {
                    TYPE_OF_SEARCH = 1;
                } else if(checkedId== R.id.byPhone) {
                    TYPE_OF_SEARCH = 2;
                }
            }
        });



        mDbManager = InfoDBManager.getInstance(this);
        String[] columns = new String[] { "_id", "pw", "name", "phone" };

        Cursor c = mDbManager.query( columns, null, null, null, null, null);

        if( c != null )
        {
            tt.setText( "" );

            while( c.moveToNext() )
            {
                String  _id          = c.getString( 0 );
                String  pw      = c.getString( 1 );
                String  name        = c.getString( 2 );
                String  phone  = c.getString(3);

                tt.append(
                        "id : " + _id + "\n" +
                                "name : " + name + "\n" +
                                "phone : " + phone + "\n" +
                                "---------------------------------");
            }

            tt.append("\n Total : " + c.getCount());

            c.close();
        }
    }

    public void viewAll(View v) {
        String[] columns = new String[] { "_id", "pw", "name", "phone" };

        Cursor c = mDbManager.query( columns, null, null, null, null, null);

        if( c != null )
        {
            tt.setText( "" );

            while( c.moveToNext() )
            {
                String  _id          = c.getString( 0 );
                String  pw      = c.getString( 1 );
                String  name        = c.getString( 2 );
                String  phone  = c.getString(3);

                tt.append(
                        "id : " + _id + "\n" +
                                "name : " + name + "\n" +
                                "phone : " + phone + "\n" +
                                "---------------------------------\n");
            }

            tt.append("\n Total : " + c.getCount());

            c.close();
        }
    }

    public void Search(View v) {
        if(TYPE_OF_SEARCH==0) {
            String[] columns = new String[] { "_id", "pw", "name", "phone" };

            Cursor c = mDbManager.query( columns, "_id=?", new String[] {input.getText().toString()}, null, null, null);

            print(c);
        } else if(TYPE_OF_SEARCH==1) {
            String[] columns = new String[] { "_id", "pw", "name", "phone" };

            Cursor c = mDbManager.query( columns, "name=?", new String[] {input.getText().toString()}, null, null, null);

            print(c);
        } else if(TYPE_OF_SEARCH==2) {
            String[] columns = new String[] { "_id", "pw", "name", "phone" };

            Cursor c = mDbManager.query( columns, "phone=?", new String[] {input.getText().toString()}, null, null, null);

            print(c);
        }
    }

    public void print(Cursor c) {
        if (c != null) {
            tt.setText("");

            while (c.moveToNext()) {
                String _id = c.getString(0);
                String pw = c.getString(1);
                String name = c.getString(2);
                String phone = c.getString(3);

                tt.append(
                        "id : " + _id + "\n" +
                                "name : " + name + "\n" +
                                "phone : " + phone + "\n" +
                                "---------------------------------");
            }

            tt.append("\n Total : " + c.getCount());

            c.close();
        }
    }

}






































//        if( c != null ) {
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


















//    public void test(View v) {
//
//        String[] columns = new String[] { "_id", "pw", "name", "phone" };
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