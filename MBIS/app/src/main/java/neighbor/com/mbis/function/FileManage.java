package neighbor.com.mbis.function;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManage {

    String state;
    File path;    //저장 데이터가 존재하는 디렉토리경로
    File file;     //파일명까지 포함한 경로

    public FileManage(String name) {
        getState(name);
    }

    //파일 저장하는 함수
    public void getState(String name) {
        state = Environment.getExternalStorageState(); //외부저장소(SDcard)의 상태 얻어오기
        if (!state.equals(Environment.MEDIA_MOUNTED)) { // SDcard 의 상태가 쓰기 가능한 상태로 마운트되었는지 확인

        }
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        file = new File(path, name + ".log"); //파일명까지 포함함 경로의 File 객체 생성

    }

    public void saveData(String data) {
        try {
            Log.e("SaveData: ", "11");
            try {
                Log.e("SaveData: ", "22");
                //데이터 추가가 가능한 파일 작성자(FileWriter 객체생성)
                FileWriter wr = new FileWriter(file, true); //두번째 파라미터 true: 기존파일에 추가할지 여부를 나타냅니다.

                PrintWriter writer = new PrintWriter(wr);
                writer.println(data);
                writer.close();

            } catch (IOException e) {
                Log.e("SaveData: ", "33");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
        }

    }

    public void saveData(byte [] data) {
        try {
            Log.e("SaveData: ", "11");
            try {
                Log.e("SaveData: ", "22");
                //데이터 추가가 가능한 파일 작성자(FileWriter 객체생성)
                FileWriter wr = new FileWriter(file, true); //두번째 파라미터 true: 기존파일에 추가할지 여부를 나타냅니다.

                PrintWriter writer = new PrintWriter(wr);

                String hexStrin2 = new java.math.BigInteger(data).toString(16);
                for(int i =0; i < data.length ; i++) {
                    writer.print(data[i] + " ");
                }
                writer. println(hexStrin2);
                writer.close();
            } catch (IOException e) {
                Log.e("SaveData: ", "33");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
        }

    }
    public void saveData(char[] data) {
        try {
            Log.e("SaveData: ", "11");
            try {
                Log.e("SaveData: ", "22");
                //데이터 추가가 가능한 파일 작성자(FileWriter 객체생성)

                FileWriter wr = new FileWriter(file, true); //두번째 파라미터 true: 기존파일에 추가할지 여부를 나타냅니다.

                PrintWriter writer = new PrintWriter(wr);

                writer.println(data);
                writer.close();


            } catch (IOException e) {
                Log.e("SaveData: ", "33");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
        }

    }
    public void saveData(double data) {
        try {
            Log.e("SaveData: ", "11");
            try {
                Log.e("SaveData: ", "22");
                //데이터 추가가 가능한 파일 작성자(FileWriter 객체생성)

                FileWriter wr = new FileWriter(file, true); //두번째 파라미터 true: 기존파일에 추가할지 여부를 나타냅니다.

                PrintWriter writer = new PrintWriter(wr);

                writer.println(data);
                writer.close();


            } catch (IOException e) {
                Log.e("SaveData: ", "33");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
        }

    }

}