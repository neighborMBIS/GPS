package neighbor.com.mbis.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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
import java.util.Iterator;
import java.util.TimeZone;

import neighbor.com.mbis.Function.Func;
import neighbor.com.mbis.MapUtil.Form.Form_Body_ArriveStation;
import neighbor.com.mbis.MapUtil.Form.Form_Body_EndDrive;
import neighbor.com.mbis.MapUtil.Form.Form_Body_StartDrive;
import neighbor.com.mbis.MapUtil.Form.Form_Body_StartStation;
import neighbor.com.mbis.MapUtil.Form.Form_Body_Default;
import neighbor.com.mbis.MapUtil.Form.Form_Header;
import neighbor.com.mbis.MapUtil.MapVal;
import neighbor.com.mbis.MapUtil.OP_code;
import neighbor.com.mbis.MapUtil.SendData;
import neighbor.com.mbis.R;
import neighbor.com.mbis.MapUtil.ReferenceUtil;
import neighbor.com.mbis.Function.FileManage;
import neighbor.com.mbis.googlemap.AddMarker;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gmap;
    private MapView mapView;
    AddMarker maddmarker;
    FileManage eventFileManage;
    FileManage realFileManage;
    Marker busMarker;

    ReferenceUtil ref = ReferenceUtil.getInstance();

    TextView currentlatView, currentlonView, eventtextView, realtimetextView, realBearing;
    ScrollView eventscroll, realscroll;

    PolylineOptions rectOptions;




    Form_Header hd = Form_Header.getInstance();
    Form_Body_Default bd = Form_Body_Default.getInstance();
    Form_Body_ArriveStation bas = Form_Body_ArriveStation.getInstance();
    Form_Body_StartStation bss = Form_Body_StartStation.getInstance();
    Form_Body_StartDrive bsd = Form_Body_StartDrive.getInstance();
    Form_Body_EndDrive bed = Form_Body_EndDrive.getInstance();
    MapVal mv = MapVal.getInstance();
    OP_code op_code;

    static boolean mflag = false;
    final int DETECTRANGE = 30;
    static int stationBuf = -1;
    static int arriveStationTurn = 0;

    static byte[] op;
    static int sr_cnt;

    //통신 변수들
    Socket socket;
    final String IP = "127.0.0.1"; //genymotion host
    final int PORT = 12345; // port number

    private InputStream is;
    private OutputStream os;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Thread thread;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //이벤트 발생할 때 데이터 전송하려면 이벤트 발생하는곳에 사용 : sendData(byte[]배열);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
//        connectServer();

        TimeZone jst = TimeZone.getTimeZone("JST");
        Calendar cal = Calendar.getInstance(jst);

        String fileName = String.format("%02d", cal.get(Calendar.YEAR) - 2000) + String.format("%02d", (cal.get(Calendar.MONTH) + 1)) + String.format("%02d", cal.get(Calendar.DATE));

        eventFileManage = new FileManage(fileName);
        realFileManage = new FileManage("real");


        checkGpsService();
        getItem(savedInstanceState);
        setLog();
    }

    private boolean checkGpsService() {

        String gps = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

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
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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

    public void setLog() {

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final GpsStatus gpsStatus = locationManager.getGpsStatus(null);

        // GPS 프로바이더 사용가능여부
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                addUtilDefault(location, gpsStatus);

                for (int i = 0; i < ref.getDistance().size(); i++) {

                    if (ref.getDistance().get(i) < DETECTRANGE && i == ref.getReferenceLatPosition().size() - 1 && !mflag) {
                        //마지막 역 도착 운행종료
                        stationBuf = i;
                        op = new byte[]{0x31};

                        addUtilEndDrive();

                        setHeader();
                        setBody_Default();
                        setBody_EndDrive();
                        op_code = new OP_code(op);

                        writeLogFile();

                        mflag = true;
                    } else if (ref.getDistance().get(i) < DETECTRANGE && !mflag) {
                        //역에 도착했을 때
                        stationBuf = i;
                        op = new byte[]{0x21};

//
                        addUtilArriveStation();

                        setHeader();
                        setBody_Default();
                        setBody_ArriveStation();
                        op_code = new OP_code(op);

                        writeLogFile();

                        mflag = true;
                    }
                }
                //운행 시작 전
                if (stationBuf == -1) {
                    return;
                } else if (ref.getDistance().get(stationBuf) >= DETECTRANGE && mflag) {

                    if (stationBuf == 0) {
                        //출발지점이 A라면 A가 차고지가 되어 운행시작을 알림
                        op = new byte[]{0x15};

                        addUtilStartDrive();

                        setHeader();
                        setBody_Default();
                        setBody_StartDrive();
                        op_code = new OP_code(op);

                        writeLogFile();
                    } else if (stationBuf == ref.getReferenceLatPosition().size() - 1) {
                        //출발지점이 마지막 역이라면 출발 없음
                        return;
                    } else {
                        //출발지점이 A가 아니라면 그냥 해당 역에서 출발한 것을 알림
                        op = new byte[]{0x22};

                        addUtilStartStation();

                        setHeader();
                        setBody_Default();
                        setBody_StartStation();
                        op_code = new OP_code(op);

                        writeLogFile();
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

//    //서버와 연결하는 메소드
//    public void connectServer() {
//        new Thread() {
//            public void run() {
//                try {
//                    socket = new Socket(IP, PORT);
//                    Log.d("[Client]", " Server connected !!");
//
//                    //자바에서 한거랑 똑같으니 참고하기
//                    is = socket.getInputStream();
//                    dis = new DataInputStream(is);
//                    os = socket.getOutputStream();
//                    dos = new DataOutputStream(os);
//
//                    thread = new Thread(new ReceiveMsg());
//                    thread.setDaemon(true);
//                    thread.start();
//
//                } catch (Exception e) {
//                    run();
//                    Log.d("[ChatActivity]", " connectServer() Exception !!");
//                }
//            }
//        }.start();
//
//    }
//
//    String recv = "";
//
//    // 내부클래스로 서버에서 받은 메세지를 처리
//    class ReceiveMsg implements Runnable {
//
//        @SuppressWarnings("null")
//        @Override
//        public synchronized void run() {
//            while (true) {
//                try {
//                    //바이트 크기는 넉넉하게 잡아서 할 것.
//                    //가변적으로 못바꾸니 넉넉하게 잡고 알아서 fix 하기
//                    byte[] bb = new byte[10];
//
//                    dis.read(bb);
//                    for (int i = 0; i < bb.length; i++) {
//                        recv = recv + String.format("02X ", bb[i]);
//                    }
//                    mHandler.sendEmptyMessage(1);
//                } catch (IOException e) {
//                    //e.printStackTrace();
//                    //status = false;
//                    try {
//                        os.close();
//                        is.close();
//                        dos.close();
//                        dis.close();
//                        socket.close();
//                        break;
//                    } catch (IOException e1) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//
//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 1) {
//                //스레드에서 view를 바꾸지 못하게 했다.
//                //그러므로 핸들러를 이용하여 View에 변화를 줄 수 있다.
////                tv.append("\n" + recv);
//                recv = "";
//            }
//        }
//    };
//
//    public void sendData(byte[] data) {        //메세지를 보내는 메서드
//        try {
//            if (socket != null) {
//                dos.write(data);
//                Log.d("[sendData]", " send byte Data !!");
//            } else {
//                Log.d("[sendData]", " Failed send byte Data null !!");
//            }
//        } catch (IOException e) {
//            Log.d("[sendData]", " Failed send byte Data !!");
//            connectServer();
//        }
//    }

    public void setHeader() {
        hd.setVersion(mv.getVersion());
        hd.setSr_cnt(mv.getSr_cnt());
        hd.setDeviceID(mv.getDeviceID());
        hd.setLocalCode(mv.getLocalCode());
        hd.setDataLength(mv.getDataLength());
    }
    public void setBody_Default() {
        bd.setSendDate(mv.getSendYear(), mv.getSendMonth(), mv.getSendDay());
        bd.setEventDate(mv.getEventYear(), mv.getEventMonth(), mv.getEventDay());
        bd.setSendTime(mv.getSendHour(), mv.getSendMin(), mv.getSendSec());
        bd.setEventTime(mv.getEventHour(), mv.getEventMin(), mv.getEventSec());
        bd.setRouteInfo(mv.getRouteID(), mv.getRouteNum(), mv.getRouteForm(), mv.getRouteDivision());
        bd.setGpsInfo(mv.getLocationX(), mv.getLocationY(), mv.getBearing(), mv.getSpeed());
        bd.setDeviceState(0);

    }
    public void setBody_ArriveStation() {
        bas.setStationId(mv.getArriveStationID());
        bas.setStationTurn(mv.getArriveStationTurn());
        bas.setAdjacentTravelTime(mv.getAdjacentTravelTime());
        bas.setReservation(mv.getReservation());
        arriveStationTurn++;
    }
    public void setBody_StartStation() {
        bss.setStationId(mv.getArriveStationID());
        bss.setStationTurn(mv.getArriveStationTurn());
        bss.setDriveTurn(mv.getDriveTurn());
        bss.setServiceTime(mv.getServiceTime());
        bss.setAdjacentTravelTime(mv.getAdjacentTravelTime());
        bss.setReservation(mv.getReservation());
        arriveStationTurn++;
    }
    public void setBody_StartDrive() {
        bsd.setDriveDivision(mv.getDriveDivision());
        bsd.setReservation(mv.getReservation());
    }
    public void setBody_EndDrive() {
        bed.setDriveDate(mv.getDriveDate());
        bed.setStartTime(mv.getDriveStartTime());
        bed.setStationId(mv.getArriveStationID());
        bed.setStationTurn(mv.getArriveStationTurn());
        bed.setDriveTurn(mv.getDriveTurn());
        bed.setDetectStationNum(mv.getDetectStationNum());
        bed.setUndetectStationNum(mv.getUndetectStationNum());
        bed.setDetectCrossRoadNum(mv.getDetectCrossRoadNum());
        bed.setUndetectCrossRoadNum(mv.getUndetectCrossRoadNum());
        bed.setReservation(mv.getReservation());
    }

    public void addUtilDefault(Location location, GpsStatus gpsStatus) {
        TimeZone jst = TimeZone.getTimeZone("JST");
        Calendar cal = Calendar.getInstance(jst);

        double latD = location.getLatitude();
        double lngD = location.getLongitude();

        ref.getDistance().clear();
        for (int i = 0; i < ref.getReferenceLatPosition().size(); i++) {
            ref.addDistance(Func.getDistance(latD, lngD, ref.getReferenceLatPosition().get(i), ref.getReferenceLngPosition().get(i)));
        }

        currentlatView.setText("위도 : " + String.format("%.5f", latD));
        currentlonView.setText("경도 :" + String.format("%.5f", lngD));
        realtimetextView.append("\nLat : " + latD + "\nLng : " + lngD + "\n----------------------------------------");

        //날짜시간날짜시간
        mv.setSendYear(cal.get(Calendar.YEAR) - 2000);
        mv.setSendMonth(cal.get(Calendar.MONTH) + 1);
        mv.setSendDay(cal.get(Calendar.DATE));
        mv.setSendHour((cal.get(Calendar.HOUR_OF_DAY)) + 9);
        mv.setSendMin(cal.get(Calendar.MINUTE));
        mv.setSendSec(cal.get(Calendar.SECOND));
        mv.setEventYear(cal.get(Calendar.YEAR) - 2000);
        mv.setEventMonth(cal.get(Calendar.MONTH) + 1);
        mv.setEventDay(cal.get(Calendar.DATE));
        mv.setEventHour((cal.get(Calendar.HOUR_OF_DAY)) + 9);
        mv.setEventMin(cal.get(Calendar.MINUTE));
        mv.setEventSec(cal.get(Calendar.SECOND));

        //노선정보
        mv.setRouteID(ref.getRouteID());
        mv.setRouteNum(ref.getRouteName());
        mv.setRouteForm("1");
        mv.setRouteDivision("00");

        //GPS정보
        double bufX = location.getLatitude() *100000;
        double bufY = location.getLongitude() *100000;
        mv.setLocationX((int)bufX);
        mv.setLocationY((int)bufY);
        mv.setBearing((int) location.getBearing());
        mv.setSpeed((int) location.getSpeed());

        //기기상태
        int i = 0;
        final Iterator<GpsSatellite> iter = gpsStatus.getSatellites().iterator();

        while (iter.hasNext()) {
            GpsSatellite satellite = iter.next();
            if (satellite.usedInFix()) {
                i++;
            }
        }
        if (i >= 3) {
            mv.setGpsState(0);
        } else mv.setGpsState(128);
    }

    public void addUtilArriveStation() {
        mv.setArriveTimeBuf(mv.getSendHour()*3600 + mv.getSendMin()*60 + mv.getSendSec());
        mv.setArriveStationID(ref.getRefernceUniqueNum().get(stationBuf));
        mv.setArriveStationTurn(arriveStationTurn);
        mv.setAdjacentTravelTime(mv.getArriveTimeBuf() - mv.getStartTimeBuf());
        mv.setReservation(2);
    }
    public void addUtilStartStation() {
        mv.setStartTimeBuf(mv.getSendHour()*3600 + mv.getSendMin()*60 + mv.getSendSec());
        mv.setArriveStationID(ref.getRefernceUniqueNum().get(stationBuf));
        mv.setArriveStationTurn(arriveStationTurn);
        mv.setDriveTurn(3);
        mv.setServiceTime(4);
        mv.setAdjacentTravelTime(mv.getArriveTimeBuf() - mv.getStartTimeBuf());
        mv.setReservation(2);
    }
    public void addUtilStartDrive() {
        mv.setDriveDate(String.format("%02d", mv.getSendYear()) + String.format("%02d", mv.getSendMonth()) + String.format("%02d", mv.getSendDay()));
        mv.setDriveStartTime(String.format("%02d", mv.getSendHour()) + String.format("%02d", mv.getSendMin()) + String.format("%02d", mv.getSendSec()));
        mv.setDriveDivision(0);
        mv.setReservation(3);
    }
    public void addUtilEndDrive() {
        mv.setArriveStationID(ref.getRefernceUniqueNum().get(stationBuf));
        mv.setArriveStationTurn(arriveStationTurn);
        mv.setDriveTurn(65535);
        mv.setDetectStationNum(56797);
        mv.setUndetectStationNum(61166);
        mv.setDetectCrossRoadNum(52428);
        mv.setUndetectCrossRoadNum(43981);
        mv.setReservation(43690);
    }

    public void writeLogFile() {
        String dd = "";
        for (int j = 0; j < SendData.sendData.length; j++) {
            eventtextView.append(String.format("%02X ", SendData.sendData[j]));
            dd = dd + String.format("%02X ", SendData.sendData[j]);
        }
        eventFileManage.saveData("\n(" + mv.getSendYear()  + ":" + mv.getSendMonth() + ":" + mv.getSendDay() +
                " - " + mv.getSendHour() + ":" + mv.getSendMin() + ":" + mv.getSendSec() +
                ")\n[SEND:" + SendData.sendData.length + "] - "  + dd);

        eventtextView.append("\n");
        eventscroll.fullScroll(View.FOCUS_DOWN);
        sr_cnt++;
    }
}
