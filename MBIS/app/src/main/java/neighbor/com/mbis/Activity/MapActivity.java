package neighbor.com.mbis.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.TimeZone;

import neighbor.com.mbis.Packet.B_0x15;
import neighbor.com.mbis.Packet.B_0x21;
import neighbor.com.mbis.Packet.B_0x22;
import neighbor.com.mbis.Packet.B_0x31;
import neighbor.com.mbis.Packet.Packet;
import neighbor.com.mbis.R;
import neighbor.com.mbis.Util.BasicUtil;
import neighbor.com.mbis.Util.ReferenceUtil;
import neighbor.com.mbis.Util.U_0x15;
import neighbor.com.mbis.Util.U_0x21;
import neighbor.com.mbis.Util.U_0x22;
import neighbor.com.mbis.Util.U_0x31;
import neighbor.com.mbis.function.FileManage;
import neighbor.com.mbis.function.GPS_Info;
import neighbor.com.mbis.function.TransByte;
import neighbor.com.mbis.googlemap.AddMarker;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    byte[] version = {0x01};
    byte[] opcode;
    byte[] sr_cnt = {0x02, 0x02};
    byte[] deviceID = {0x03, 0x03};
    byte[] datalen = {0x04,0x04,0x04,0x04};

    int bufTime[] = {0,0,0};
    //beforeToAfter[0] : 출->도, 도->출
    //beforeToAfter[1] : 출->도
    int beforeToAfter[] = {0,0};
    int stopCount = 0;
    String today="000000";
    String startTime="000000";

    private GoogleMap gmap;
    private MapView mapView;
    AddMarker maddmarker;
    FileManage eventFileManage;
    FileManage realFileManage;
    Marker busMarker;

    ReferenceUtil ref = ReferenceUtil.getInstance();

    GPS_Info gps_info = GPS_Info.getInstance();

    BasicUtil basicUtil = BasicUtil.getInstance();
    U_0x15 u_0x15 = U_0x15.getInstance();
    U_0x21 u_0x21 = U_0x21.getInstance();
    U_0x22 u_0x22 = U_0x22.getInstance();
    U_0x31 u_0x31 = U_0x31.getInstance();

    TransByte tb = TransByte.getInstance();

    TextView currentlatView, currentlonView, eventtextView, realtimetextView, realBearing;
    ScrollView eventscroll, realscroll;

    PolylineOptions rectOptions;

    double disValue[] = {0, 1, 2, 3, 4};
    Packet p;


    static boolean mflag = false;
    final int DETECTRANGE = 100;
    static int stationBuf = -1;

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
        setContentView(R.layout.activity_map);
        connectServer();

        TimeZone jst = TimeZone.getTimeZone("JST");
        Calendar cal = Calendar.getInstance(jst);

        String fileName =  String.format("%02d", cal.get(Calendar.YEAR) - 2000) +  String.format("%02d", (cal.get(Calendar.MONTH) + 1)) +  String.format("%02d", cal.get(Calendar.DATE));

        eventFileManage = new FileManage(fileName);
        realFileManage = new FileManage("real");


        checkGpsService();
        getItem(savedInstanceState);
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

    public void getItem(Bundle savedInstanceState) {

        currentlatView = (TextView) findViewById(R.id.curlat);
        currentlonView = (TextView) findViewById(R.id.curlon);
        eventtextView = (TextView) findViewById(R.id.eventtext);
        realtimetextView = (TextView) findViewById(R.id.realtimetext);
        realBearing = (TextView) findViewById(R.id.bearing);
        eventscroll = (ScrollView) findViewById(R.id.eventscroll);
        realscroll = (ScrollView) findViewById(R.id.realscroll);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
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
            public void onLocationChanged(Location location) { //시간알아오기
                TimeZone jst = TimeZone.getTimeZone("JST");
                Calendar cal = Calendar.getInstance(jst);
                //위치 정보를 가져올 수 있는 메소드
                //위치 이동이나 시간 경과등으로 인해 호출된다.
                //최신 위치는 location 파라메터가 가지고 있으니 최신 위치를 가져오려면 location 파라메터를 이용하면 됨
                //즉 처음의 위치를 알고싶으면 처음의 값을 저장 해 주고 비교하면 됨.


                double latD = location.getLatitude();
                double lngD = location.getLongitude();

                currentlatView.setText("위도 :" + String.format("%.5f", latD));
                currentlonView.setText("경도 :" + String.format("%.5f", lngD));
                realBearing.setText("방위각 :" + location.getBearing());


                //5개의 지정된 장소와 거리를 비교 후 disValue에 저장한다.
                //만약 우리가 임의로 결정한 x,y좌표가 아닌 실제 Location 객체와 비교한다면
                //location.distanceTo() 메소드를 사용하여 비교하는 것이 바람직하다.
                for (int i = 0; i < 5; i++) {
                    disValue[i] = gps_info.getDistance(latD, lngD, ref.getReferenceLatPosition().get(i), ref.getReferenceLngPosition().get(i));
                }

                //가장 가까운 역과의 거리
                double minDistance = getMin(disValue);

                //가장 가까운 역의 이름
                String minReferenceName;

                int YEAR = cal.get(Calendar.YEAR) - 2000;
                int MONTH = (cal.get(Calendar.MONTH) + 1);
                int DAY = cal.get(Calendar.DATE);
                int HOUR = (cal.get(Calendar.HOUR_OF_DAY)) + 9;
                int MINNTE = cal.get(Calendar.MINUTE);
                int SECOND = cal.get(Calendar.SECOND);

                //시간정보
                String ymd = String.format("%02d", YEAR) +
                        String.format("%02d", MONTH) +
                        String.format("%02d", DAY);

                String hms = String.format("%02d", HOUR) +
                        String.format("%02d", MINNTE) +
                        String.format("%02d", SECOND);

                //GPS 정보
                double latbuf = location.getLatitude() * 100000;
                double lngbuf = location.getLongitude() * 100000;
                int lat = (int)latbuf;
                int lng = (int)lngbuf;

                int bearing = (int) location.getBearing();
                int speed = (int) location.getSpeed();

                //노선정보
                String[] station;
                byte[] routeForm = gps_info.getDirection(bearing);
                int turn = 0;
                if(routeForm.equals(0x01)) {
                    turn = stationBuf;
                } else if(routeForm.equals(0x02)) {
                    turn = (ref.getRefernceUniqueNum().size()-1)-stationBuf;
                }
                String routeDivision = "aa";

                //기기상태
                String diviceState = "b";

                realtimetextView.append("\nLat : " + latD + "\nLng : " + lngD+"\n----------------------------------------");
                realFileManage.saveData("Lat : " + latD + "\nLng : " + lngD+"\n----------------------------------------\n");


                //어느 역에 도착했는지 확인 후 메세지
                for (int i = 0; i < disValue.length; i++) {
                    if (minDistance == disValue[i]) {
                        minReferenceName = ref.getRouteName().toString();
                    }

                    if(disValue[i] < DETECTRANGE && i==ref.getReferenceLatPosition().size()-1 && !mflag) {
                        //마지막 역 도착 운행종료
                        stationBuf = i;
                        station = ref.getRouteName().split("-");

                        beforeToAfter[0] = (HOUR - bufTime[0])*3600 + (MINNTE - bufTime[1])*60 + (SECOND - bufTime[2]);
                        beforeToAfter[1] = (HOUR - bufTime[0])*3600 + (MINNTE - bufTime[1])*60 + (SECOND - bufTime[2]);

                        opcode = new byte[]{0x31};
                        setUtil(ymd, hms, ymd, hms, station[0], station[1], routeForm, routeDivision, lat, lng, bearing, speed, diviceState);
                        set0x31(today, startTime, ref.getRefernceUniqueNum().get(stationBuf), turn, stationBuf ,stopCount);

                        p = Packet.getInstance();
                        p.setHeader(version, opcode, sr_cnt, deviceID, datalen);

                        byte[] bd = new B_0x31(basicUtil.getSendDate(), basicUtil.getSendTime(), basicUtil.getEventDate(), basicUtil.getEventTime(), basicUtil.getRouteInfo(), basicUtil.getGPSInfo(), basicUtil.getDiviceState())
                                .addEtcBody(u_0x31.getDriveDate(), u_0x31.getStartTime(), u_0x31.getStationID(), u_0x31.getStationNum(), u_0x31.getDriveNum(), u_0x31.getEventNum());
                        byte[] data = p.addBodyPacket(bd);
                        eventtextView.append("\n(" + HOUR + ":" + MINNTE + ":" + SECOND + ")\n[SEND:" + data.length + "] - " );

                        String dd = "";
                        for (int j = 0; j < data.length; j++) {
//                            System.out.format("Send Data : (0x%02X)",data[i] ); //array
                            eventtextView.append(String.format("0x%02X ", data[j]));
                            dd = dd + String.format("%02X ", data[j]);
                        }
                        eventFileManage.saveData("\n(" + HOUR + ":" + MINNTE + ":" + SECOND + ")\n[SEND:" + data.length + "] - "  + dd);

                        eventscroll.fullScroll(View.FOCUS_DOWN);
                        sendData(data);
                        mflag=true;


                    } else if (disValue[i] < DETECTRANGE && !mflag) {
                        //역에 도착했을 때
                        stationBuf = i;
                        station = ref.getRouteName().split("-");

                        beforeToAfter[0] = (HOUR - bufTime[0])*3600 + (MINNTE - bufTime[1])*60 + (SECOND - bufTime[2]);
                        beforeToAfter[1] = (HOUR - bufTime[0])*3600 + (MINNTE - bufTime[1])*60 + (SECOND - bufTime[2]);

                        opcode = new byte[]{0x21};
                        setUtil(ymd, hms, ymd, hms, station[0], station[1], routeForm, routeDivision, lat, lng, bearing, speed, diviceState);

                        set0x21(ref.getRefernceUniqueNum().get(stationBuf), turn, beforeToAfter[0], 1516);

                        p = Packet.getInstance();
                        p.setHeader(version, opcode, sr_cnt, deviceID, datalen);

                        byte[] bd = new B_0x21(basicUtil.getSendDate(), basicUtil.getSendTime(), basicUtil.getEventDate(), basicUtil.getEventTime(), basicUtil.getRouteInfo(), basicUtil.getGPSInfo(), basicUtil.getDiviceState())
                                .addEtcBody(u_0x21.getStationID(), u_0x21.getStationNum(), u_0x21.getBeforeStationToAfterStationSec(), u_0x21.getEtc());
                        byte[] data = p.addBodyPacket(bd);                        eventtextView.append("\n(" + HOUR + ":" + MINNTE + ":" + SECOND + ")\n[SEND:" + data.length + "] - " );

                        String dd = "";
                        for (int j = 0; j < data.length; j++) {
//                            System.out.format("Send Data : (0x%02X)",data[i] ); //array
                            eventtextView.append(String.format("0x%02X ", data[j]));
                            dd = dd + String.format("%02X ", data[j]);
                        }
                        eventFileManage.saveData("\n(" + HOUR + ":" + MINNTE + ":" + SECOND + ")\n[SEND:" + data.length + "] - "  + dd);

                        eventscroll.fullScroll(View.FOCUS_DOWN);
                        sendData(data);


                        bufTime[0] = HOUR;
                        bufTime[1] = MINNTE;
                        bufTime[2] = SECOND;
                        mflag = true;
                        stopCount++;
                    }
                }
                //운행 시작 전
                if (stationBuf == -1) {
                    return;
                } else if (disValue[stationBuf] >= DETECTRANGE && mflag) {

                    if (stationBuf == 0) {
                        //출발지점이 A라면 A가 차고지가 되어 운행시작을 알림
                        station = ref.getRouteName().split("-");
                        opcode = new byte[]{0x15};

                        bufTime[0] = HOUR;
                        bufTime[1] = MINNTE;
                        bufTime[2] = SECOND;

                        today = ymd;
                        startTime = hms;

                        setUtil(ymd, hms, ymd, hms, station[0], station[1], routeForm, routeDivision, lat, lng, bearing, speed, diviceState);
                        set0x15(21, 222324);

                        p = Packet.getInstance();
                        p.setHeader(version, opcode, sr_cnt, deviceID, datalen);

                        byte[] data = p.addBodyPacket(
                                new B_0x15(basicUtil.getSendDate(), basicUtil.getSendTime(), basicUtil.getEventDate(), basicUtil.getEventTime(), basicUtil.getRouteInfo(), basicUtil.getGPSInfo(), basicUtil.getDiviceState())
                                .addEtcBody(u_0x15.getDriverDivision(), u_0x15.getEtc()));                        eventtextView.append("\n(" + HOUR + ":" + MINNTE + ":" + SECOND + ")\n[SEND:" + data.length + "] - " );

                        String dd = "";
                        for (int j = 0; j < data.length; j++) {
//                            System.out.format("Send Data : (0x%02X)",data[i] ); //array
                            eventtextView.append(String.format("0x%02X ", data[j]));
                            dd = dd + String.format("%02X ", data[j]);
                        }
                        eventFileManage.saveData("\n(" + HOUR + ":" + MINNTE + ":" + SECOND + ")\n[SEND:" + data.length + "] - "  + dd);

                        eventscroll.fullScroll(View.FOCUS_DOWN);
                        sendData(data);

                    }
                    //출발지점이 마지막 역이라면 출발 없음
                    else if(stationBuf==ref.getReferenceLatPosition().size()-1) {
                        return;
                    }
                    //출발지점이 A가 아니라면 그냥 해당 역에서 출발한 것을 알림
                    else {
                        station = ref.getRouteName().split("-");
                        opcode = new byte[]{0x22};
                        beforeToAfter[0] = (HOUR - bufTime[0])*3600 + (MINNTE - bufTime[1])*60 + (SECOND - bufTime[2]);

                        setUtil(ymd, hms, ymd, hms, station[0], station[1], routeForm, routeDivision, lat, lng, bearing, speed, diviceState);
                        set0x22(ref.getRefernceUniqueNum().get(stationBuf),stationBuf,stationBuf,beforeToAfter[0],beforeToAfter[1],1010);

                        p = Packet.getInstance();
                        p.setHeader(version, opcode, sr_cnt, deviceID, datalen);

                        byte[] data = p.addBodyPacket(
                                new B_0x22(basicUtil.getSendDate(), basicUtil.getSendTime(), basicUtil.getEventDate(), basicUtil.getEventTime(), basicUtil.getRouteInfo(), basicUtil.getGPSInfo(), basicUtil.getDiviceState())
                                        .addEtcBody(u_0x22.getStationID(), u_0x22.getStationNum(), u_0x22.getDriveNum(), u_0x22.getServiceTime(), u_0x22.getBeforeToAfterSec(), u_0x22.getEtc()));
                        eventtextView.append("\n(" + HOUR + ":" + MINNTE + ":" + SECOND + ")\n[SEND:" + data.length + "] - " );

                        String dd = "";
                        for (int j = 0; j < data.length; j++) {
//                            System.out.format("Send Data : (0x%02X)",data[i] ); //array
                            eventtextView.append(String.format("0x%02X ", data[j]));
                            dd = dd + String.format("%02X ", data[j]);
                        }
                        eventFileManage.saveData("\n(" + HOUR + ":" + MINNTE + ":" + SECOND + ")\n[SEND:" + data.length + "] - "  + dd);

                        eventscroll.fullScroll(View.FOCUS_DOWN);

                        sendData(data);
                    }
                    mflag = false;
                }
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
        try {
            os.close();
            is.close();
            dos.close();
            dis.close();
            socket.close();
        } catch (IOException e) {
        }
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
        for (int i = 0; i < ref.getReferenceLatPosition().size(); i++) {
            rectOptions.add(new LatLng(ref.getReferenceLatPosition().get(i), ref.getReferenceLngPosition().get(i)));
        }


        Polyline polyline = gmap.addPolyline(rectOptions);

        for (int i = 0; i < ref.getReferenceLatPosition().size(); i++) {
            busMarker = maddmarker.getMark(ref.getReferenceLatPosition().get(i), ref.getReferenceLngPosition().get(i), getApplicationContext());

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

                    thread = new Thread(new ReceiveMsg());
                    thread.setDaemon(true);
                    thread.start();

                } catch (Exception e) {
                    run();
                    Log.d("[ChatActivity]", " connectServer() Exception !!");
                }
            }
        }.start();

    }

    String recv="";
    // 내부클래스로 서버에서 받은 메세지를 처리
    class ReceiveMsg implements Runnable {

        @SuppressWarnings("null")
        @Override
        public synchronized void run() {
            while (true) {
                try {
                    //바이트 크기는 넉넉하게 잡아서 할 것.
                    //가변적으로 못바꾸니 넉넉하게 잡고 알아서 fix 하기
                    byte[] bb = new byte[10];

                    dis.read(bb);
                    for(int i=0 ; i<bb.length ; i++) {
                        recv = recv + String.format("02X ", bb[i]);
                    }
                    mHandler.sendEmptyMessage(1);
                } catch (IOException e) {
                    //e.printStackTrace();
                    //status = false;
                    try {
                        os.close();
                        is.close();
                        dos.close();
                        dis.close();
                        socket.close();
                        break;
                    } catch (IOException e1) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1) {
                //스레드에서 view를 바꾸지 못하게 했다.
                //그러므로 핸들러를 이용하여 View에 변화를 줄 수 있다.
//                tv.append("\n" + recv);
                recv = "";
            }
        }
    };

    public void sendData(byte[] data) {        //메세지를 보내는 메서드
        try {
            if (socket != null) {
                dos.write(data);
                Log.d("[sendData]", " send byte Data !!");
            } else {
                Log.d("[sendData]", " Failed send byte Data null !!");
            }
        } catch (IOException e) {
            Log.d("[sendData]", " Failed send byte Data !!");
            connectServer();
        }
    }

    private void setUtil(String ymd, String hms, String ymd2, String hms2, String station0,
                    String station1, byte[] routeForm, String routeDivision, int lat, int lng,
                    int bearing, int speed, String diviceState) {
        basicUtil.setSendDate(ymd);
        basicUtil.setSendTime(hms);
        basicUtil.setEventDate(ymd2);
        basicUtil.setEventTime(hms2);
        basicUtil.setRouteInfo(station0, station1, routeForm, routeDivision);
        basicUtil.setGPSInfo(lat, lng, bearing, speed);
        basicUtil.setDiviceState(diviceState);
    }
    public void set0x15(int driverDivision, int etc){
        u_0x15.setDriverDivision(driverDivision);
        u_0x15.setEtc(etc);
    }
    public void set0x21(int id, int num, int sec, int etc){
        u_0x21.setStationID(id);
        u_0x21.setStationNum(num);
        u_0x21.setBeforeStationToAfterStationSec(sec);
        u_0x21.setEtc(etc);
    }
    public void set0x22(int stationID, int stationNum, int driveNum, int serviceTime, int beforeToAfterSec, int etc){
        u_0x22.setStationID(stationID);
        u_0x22.setStationNum(stationNum);
        u_0x22.setDriveNum(driveNum);
        u_0x22.setServiceTime(serviceTime);
        u_0x22.setBeforeToAfterSec(beforeToAfterSec);
        u_0x22.setEtc(etc);
    }
    public void set0x31(String driveDate, String startTime, int stationID, int stationNum, int driveNum, int eventNum) {
        u_0x31.setDriveDate(driveDate);
        u_0x31.setStartTime(startTime);
        u_0x31.setStationID(stationID);
        u_0x31.setStationNum(stationNum);
        u_0x31.setDriveNum(driveNum);
        u_0x31.setEventNum(eventNum);
    }

}
