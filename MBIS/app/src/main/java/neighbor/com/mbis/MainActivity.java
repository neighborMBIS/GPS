package neighbor.com.mbis;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import neighbor.com.mbis.Packet.B_0x15;
import neighbor.com.mbis.Packet.Packet;
import neighbor.com.mbis.file.FileManage;
import neighbor.com.mbis.function.GPS_Info;
import neighbor.com.mbis.function.TransByte;
import neighbor.com.mbis.googlemap.AddMarker;

public class MainActivity extends Activity implements OnMapReadyCallback {

    private GoogleMap gmap;
    private MapView mapView;
    AddMarker maddmarker;
    FileManage fileManage;
    Marker busMarker;
    LocationManager locationManager;

    TextView currentlatView, currentlonView, eventtextView, realtimetextView;
    ScrollView eventscroll, realscroll;

    PolylineOptions rectOptions;

    ArrayList<String> referenceNamePosition;
    ArrayList<Double> referenceLatPosition;
    ArrayList<Double> referenceLngPosition;
    double disValue[] = {0, 1, 2, 3, 4};

    //통신 변수들
    Socket socket;
    final String IP = "127.0.0.1"; //genymotion host
    final int PORT = 12345; // port number

    private InputStream is;
    private OutputStream os;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Thread thread;

    //이벤트 발생할 때 데이터 전송하려면 이벤트 발생하는곳에 사용 : sendData(byte[]배열);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectServer();

        checkGpsService();
        getItem();
        setLog();

    }

    private boolean checkGpsService() {

        String gps = android.provider.Settings.Secure.getString(getContentResolver(),
                android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        Log.d(gps, "check GPS Page");

        if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {

            // GPS OFF 일때 Dialog 표시
            AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
            gsDialog.setTitle("GPS 설정");
            gsDialog.setMessage("무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n" +
                    "GPS 기능을 설정하시겠습니까?");
            gsDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // GPS설정 화면으로 이동
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "GPS를 켜야 사용 가능합니다.", Toast.LENGTH_SHORT);
                            return;
                        }
                    }).create().show();
            return false;

        } else {
            return true;
        }
    }

    public void getItem() {
        referenceNamePosition = new ArrayList<String>();
        referenceLatPosition = new ArrayList<Double>();
        referenceLngPosition = new ArrayList<Double>();

        currentlatView = (TextView) findViewById(R.id.curlat);
        currentlonView = (TextView) findViewById(R.id.curlon);
        eventtextView = (TextView) findViewById(R.id.eventtext);
        realtimetextView = (TextView) findViewById(R.id.realtimetext);
        eventscroll = (ScrollView) findViewById(R.id.eventscroll);
        realscroll = (ScrollView) findViewById(R.id.realscroll);

        mapView = (MapView) findViewById(R.id.map);
        mapView.getMapAsync(this);

        referenceNamePosition.add("00100-01");
        referenceNamePosition.add("00100-02");
        referenceNamePosition.add("00100-03");
        referenceNamePosition.add("00100-04");
        referenceNamePosition.add("00100-05");

        referenceLatPosition.add(37.49568);
        referenceLatPosition.add(37.49502);
        referenceLatPosition.add(37.49455);
        referenceLatPosition.add(37.49413);
        referenceLatPosition.add(37.49373);

        referenceLngPosition.add(127.12259);
        referenceLngPosition.add(127.12140);
        referenceLngPosition.add(127.12063);
        referenceLngPosition.add(127.11979);
        referenceLngPosition.add(127.11907);
    }

    private double getMin(double arr[]) {
        double min = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min) min = arr[i];
        }
        return min;
    }

    public void setLog() {

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //위치 정보를 가져올 수 있는 메소드
                //위치 이동이나 시간 경과등으로 인해 호출된다.
                //최신 위치는 location 파라메터가 가지고 있으니 최신 위치를 가져오려면 location 파라메터를 이용하면 됨
                //즉 처음의 위치를 알고싶으면 처음의 값을 저장 해 주고 비교하면 됨.
            }


            public void onStatusChanged(String provider, int status, Bundle extras) {
                //위치공급자의 상태가 바뀔 때 호출
                //켬 -> 끔 or 끔 -> 켬
                Toast.makeText(getApplicationContext(), "GPS 상태 변환", Toast.LENGTH_SHORT).show();
            }

            public void onProviderEnabled(String provider) {
                //위치 공급자가 사용 가능해 질 때 호출
                //즉 GPS를 켜면 호출됨
                Toast.makeText(getApplicationContext(), "GPS on", Toast.LENGTH_SHORT).show();
            }

            public void onProviderDisabled(String provider) {
                //위치 공급자가 사용 불가능해질(disabled) 때 호출
                //GPS 꺼지면 여기서 예외처리 해주면 됨
                Toast.makeText(getApplicationContext(), "GPS off", Toast.LENGTH_SHORT).show();
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.gmap = map;
        maddmarker = new AddMarker(this.gmap);
        //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gmap.setMyLocationEnabled(true);
        rectOptions = new PolylineOptions().color(0xffff0000);
        for (int i = 0; i < referenceNamePosition.size(); i++) {
            rectOptions
                    .add(new LatLng(referenceLatPosition.get(i), referenceLngPosition.get(i)));
        }


        Polyline polyline = gmap.addPolyline(rectOptions);

        for (int i = 0; i < referenceNamePosition.size(); i++) {
            busMarker = maddmarker.getMark(referenceLatPosition.get(i), referenceLngPosition.get(i), getApplicationContext());

        }

    }

    //서버와 연결하는 메소드
    public void connectServer() {
        new Thread() {
            public void run() {
                try {
                    socket = new Socket(IP, PORT);
                    Log.d("[Client]", " Server connected !!");

                    //자바에서 한거랑 똑같으니 참고하기
                    is = socket.getInputStream();
                    dis = new DataInputStream(is);
                    os = socket.getOutputStream();
                    dos = new DataOutputStream(os);

                    //서버에 접속하면 성별을 보내는 메서드 호출
//                    dos.write(0x01);
                    Log.d("[ChatActivity]", " connectServer() Success !!");

                    thread = new Thread(new ReceiveMsg());
                    thread.setDaemon(true);
                    thread.start();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("[ChatActivity]", " connectServer() Exception !!");
                }
            }
        }.start();

    }
    // 내부클래스로 서버에서 받은 메세지를 처리
    class ReceiveMsg implements Runnable {

        @SuppressWarnings("null")
        @Override
        public synchronized void run() {
            while (true) {
                try {
                    os.close();
                    is.close();
                    dos.close();
                    dis.close();
                    socket.close();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendData(byte[] data) {        //메세지를 보내는 메서드
        try {
            dos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
