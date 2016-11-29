package wj.ttt;

import android.os.Environment;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by user on 2016-11-03.
 */

public class NetworkThread extends Thread {

    @SuppressWarnings("null")
    @Override
    public synchronized void run() {
        String state;
        String server = "197.168.100.20";
        int port = 21;
        String user = "admin";
        String pass = "test1234";

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            state = Environment.getExternalStorageState(); //외부저장소(SDcard)의 상태 얻어오기
            if (!state.equals(Environment.MEDIA_MOUNTED)) { // SDcard 의 상태가 쓰기 가능한 상태로 마운트되었는지 확인

            }
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            // APPROACH #1: using retrieveFile(String, OutputStream)
            //서버에서 검색 할 파일 이름
            String remoteFile1 = "Home/info.txt";
            //다운로드 할 파일 경로
            File downloadFile1 = new File(path, "info.txt");
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
            outputStream1.close();

            System.out.println(success);

            if (success) {
                System.out.println("File #1 has been downloaded successfully.");
            }

            // APPROACH #2: using InputStream retrieveFileStream(String)
//            String remoteFile2 = "Home\\test.txt";
//            File downloadFile2 = new File("test.txt");
//            OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
//            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
//            byte[] bytesArray = new byte[4096];
//            int bytesRead = -1;
//            if(inputStream == null) {
//                System.out.println("inputStream == null");
//            } else {
//                while ((bytesRead = inputStream.read(bytesArray)) != -1) {
//                    outputStream2.write(bytesArray, 0, bytesRead);
//                }
//            }
//
//            success = ftpClient.completePendingCommand();
//            if (success) {
//                System.out.println("File #2 has been downloaded successfully.");
//            }
//            outputStream2.close();
//            inputStream.close();

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
