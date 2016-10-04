package com.example.computer.sqliteexample.View.BeforeLogin;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.computer.sqliteexample.DBManager.InfoDBManager;
import com.example.computer.sqliteexample.R;

public class MembershipActivity extends Activity
{
    EditText    inputid  = null;
    EditText    inputpw  = null;
    EditText    inputname  = null;
    EditText    inputphone  = null;


    public InfoDBManager mDbManager = null;
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_membership);

        inputid = (EditText)findViewById( R.id.inputid );
        inputpw = (EditText)findViewById( R.id.inputpw );
        inputname = (EditText)findViewById( R.id.inputname );
        inputphone = (EditText)findViewById( R.id.inputphone );

        mDbManager = InfoDBManager.getInstance(this);
    }
    
    public void onClick( View v )
    {
        switch ( v.getId() )
        {
            // 1. 하나의 학생 레코드 추가하기
            // ================================================================
            case R.id.insert :
            {
                ContentValues addRowValue = new ContentValues();
                if(inputid.getText().toString().equals("")) {
                    Toast.makeText(this, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    addRowValue.put("_id", inputid.getText().toString() );

                    if(inputpw.getText().toString().equals("")) {
                        Toast.makeText(this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        addRowValue.put("pw", inputpw.getText().toString());

                        if(inputname.getText().toString().equals("")) {
                            Toast.makeText(this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            addRowValue.put("name", inputname.getText().toString());

                            if(inputphone.getText().toString().equals("")) {
                                Toast.makeText(this, "전화번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                addRowValue.put("phone", inputphone.getText().toString());
                                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                }
                long insertRecordId = mDbManager.insert( addRowValue );

                break;
            }
            // ================================================================
            
            // 2. 레코드 쿼리
            // ================================================================
           /* case R.id.query :
            {
                String[] columns = new String[] { "_id", "pw", "name",
                                                       "phone" };
                
                Cursor c = mDbManager.query( columns, 
                                             null, null, null, null, null);
                          
                if( c != null )
                {
                    mDisplayDbEt.setText( "" );
                    
                    while( c.moveToNext() )
                    {
                        int     id          = c.getInt( 0 );
                        String  number      = c.getString( 1 );
                        String  name        = c.getString( 2 );
                        String  department  = c.getString( 3 );
                        int     grade       = c.getInt( 4 );
                        
                        mDisplayDbEt.append( 
                                         "id : " + id + "\n" +
                                         "number : " + number + "\n" +
                                         "name : " + name + "\n" +
                                         "department : " + department + "\n" +
                                         "------------------------------");
                    }
                    
                    mDisplayDbEt.append("\n Total : " + c.getCount());
                    
                    c.close();
                }
                   
                break;
            }*/
            // ================================================================
            
            // 3. 특정 레코드 수정하기
            // ================================================================
         /*   case R.id.update :
            {
                ContentValues updateRowValue = new ContentValues();
                updateRowValue.put("name", "고길동");
                
                int updateRecordCnt = mDbManager.update( updateRowValue,
                                                         "number=200106054",
                                                         null );
                
                mDisplayDbEt.setText( "레코드 갱신 : " + updateRecordCnt );
                
                break;
            }*/
            // ================================================================
            
            // 4. 특정 레코드 삭제하기
            // ================================================================
         /*   case R.id.delete :
            {
                int deleteRecordCnt = mDbManager.delete( null, null );
                
                break;
            }*/
            // ================================================================
        }
    }
}
