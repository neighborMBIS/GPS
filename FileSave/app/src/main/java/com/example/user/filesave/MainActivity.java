package com.example.user.filesave;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    final String FILENAME = "Data.log";

    EditText edit;
    TextView text;

    String state= Environment.getExternalStorageState(); //외부저장소(SDcard)의 상태 얻어오기
    File path;    //저장 데이터가 존재하는 디렉토리경로
    File file;     //파일명까지 포함한 경로

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit= (EditText)findViewById(R.id.edit);
        text= (TextView)findViewById(R.id.text);
    }

    //Button을 클릭했을 때 자동으로 호출되는 callback method....
    public void mOnClick(View v){

        switch(v.getId()){
            case R.id.btn_save:  //External Storage(SDcard)에 file 저장하기
                saveButton();
                break;

            case R.id.btn_load:  //External Storage(SDcard)에서 file 읽어오기
                loadButton();
                break;

        }

    }

    private void loadButton() {
        // SDcard 의 상태가 읽기/쓰기가 가능하거나 또는 읽기만 가능한 상태로 마운트되었는지 확인
        if( !(state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) ){
            Toast.makeText(this, "SDcard Not Mounted", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuffer buffer= new StringBuffer();

        path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        file= new File(path, FILENAME);

        try {
            FileReader in= new FileReader(file);
            BufferedReader reader= new BufferedReader(in);

            String str= reader.readLine();

            while( str!=null ){
                buffer.append(str+"\n");
                str= reader.readLine();//한 줄씩 읽어오기
            }

            text.setText(buffer.toString());
            reader.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void saveButton() {
        if(!state.equals(Environment.MEDIA_MOUNTED)){ // SDcard 의 상태가 쓰기 가능한 상태로 마운트되었는지 확인
            Toast.makeText(this, "SDcard Not Mounted", Toast.LENGTH_SHORT).show();
            return;
        }

        String data= edit.getText().toString();  //EditText의 Text 얻어오기

        //SDcard에 데이터 종류(Type)에 따라 자동으로 분류된 저장 폴더 경로선택
        //Environment.DIRECTORY_DOWNLOADS : 사용자에의해 다운로드가 된 파일들이 놓여지는 표준 저장소
        path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        file= new File(path, FILENAME); //파일명까지 포함함 경로의 File 객체 생성

        try {
            //데이터 추가가 가능한 파일 작성자(FileWriter 객체생성)
            FileWriter wr= new FileWriter(file,true); //두번째 파라미터 true: 기존파일에 추가할지 여부를 나타냅니다.

            PrintWriter writer= new PrintWriter(wr);
            writer.println(data);
            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //소프트 키보드 없애기
        InputMethodManager imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

} // end of class
