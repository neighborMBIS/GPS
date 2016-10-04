package neighbor.com.test321;

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
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;

import neighbor.com.test321.CSV_Util.ReferenceUtil;
import neighbor.com.test321.Function.Func;
import neighbor.com.test321.Util.Form_Body_ArriveStation;
import neighbor.com.test321.Util.Form_Body_default;
import neighbor.com.test321.Util.Form_Header;
import neighbor.com.test321.Util.OP_code;
import neighbor.com.test321.Util.SendData;

public class MapActivity extends AppCompatActivity {
    //beforeToAfter[0] : 출->도, 도->출
    //beforeToAfter[1] : 출->도

    ReferenceUtil ref = ReferenceUtil.getInstance();
    OP_code op_code;

    TextView currentlatView, currentlonView, eventtextView, realtimetextView, realBearing;
    ScrollView eventscroll, realscroll;

    static boolean mflag = false;
    final int DETECTRANGE = 100;
    static int stationBuf = -1;
    //이벤트 발생할 때 데이터 전송하려면 이벤트 발생하는곳에 사용 : sendData(byte[]배열);

    Form_Header hd;
    Form_Body_default bd;
    Form_Body_ArriveStation bas;


    static byte[] op;
    int version=1, sr_cnt=0, localCode=0, dataLength=0;
    long deviceID=0;

    int sendYear, sendMonth, sendDay, sendHour, sendMin, sendSec,
            eventYear, eventMonth, eventDay, eventHour, eventMin, eventSec,
            locationX, locationY, bearing, speed, gpsState;
    long routeID;
    String routeNum, routeForm, routeDivision;

    long arriveStationID;
    int arriveStationTurn, adjacentTravelTime, reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        hd = Form_Header.getInstance();
        bd = Form_Body_default.getInstance();
        bas = Form_Body_ArriveStation.getInstance();


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
                eventtextView.append("\n");

                addUtil(location, gpsStatus);
                setHeader();
                setBody_Default();

                for (int i = 0; i < ref.getDistance().size(); i++) {

                    if (ref.getDistance().get(i) < DETECTRANGE && i == ref.getReferenceLatPosition().size() - 1 && !mflag) {
                        //마지막 역 도착 운행종료
                    } else if (ref.getDistance().get(i) < DETECTRANGE && !mflag) {
                        //역에 도착했을 때
                        op = new byte[]{0x21};
                        addUtilArriveStation();
                        setBody_ArriveStation();
                        op_code = OP_code.getInstance(op);

                        for (int j = 0; j < SendData.sendData.length ; j++) {
                            eventtextView.append(String.format("0x%02X ", SendData.sendData[j]));
                        }
                        mflag = true;
                    }
                }
                //운행 시작 전
                if (stationBuf == -1) {
                    return;
                } else if (ref.getDistance().get(stationBuf) >= DETECTRANGE && mflag) {

                    if (stationBuf == 0) {
                        //출발지점이 A라면 A가 차고지가 되어 운행시작을 알림
                    }
                    else if (stationBuf == ref.getReferenceLatPosition().size() - 1) {
                        //출발지점이 마지막 역이라면 출발 없음
                        return;
                    }
                    else {
                        //출발지점이 A가 아니라면 그냥 해당 역에서 출발한 것을 알림
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

    public void setHeader() {
        hd.setVersion(version);
        hd.setSr_cnt(sr_cnt);
        hd.setDeviceID(deviceID);
        hd.setLocalCode(localCode);
        hd.setDataLength(dataLength);
    }
    public void setBody_Default() {
        bd.setSendDate(sendYear, sendMonth, sendDay);
        bd.setSendTime(sendHour, sendMin, sendSec);
        bd.setEventDate(eventYear, eventMonth, eventDay);
        bd.setEventTime(eventHour, eventMin, eventSec);
        bd.setRouteInfo(routeID, routeNum, routeForm, routeDivision);
        bd.setGpsInfo(locationX, locationY, bearing, speed);
        bd.setDeviceState(0);
    }
    public void setBody_ArriveStation() {
        bas.setStationId(arriveStationID);
        bas.setStationTurn(arriveStationTurn);
        bas.setAdjacentTravelTime(adjacentTravelTime);
        bas.setReservation(reservation);
    }

    public void addUtil(Location location, GpsStatus gpsStatus) {
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

        //날짜시간날짜시간
        sendYear = eventYear = cal.get(Calendar.YEAR) -2000;
        sendMonth = eventMonth = cal.get(Calendar.MONTH) +1;
        sendDay = eventDay = cal.get(Calendar.DATE);
        sendHour = eventHour = (cal.get(Calendar.HOUR_OF_DAY)) + 9;
        sendMin = eventMin = cal.get(Calendar.MINUTE);
        sendSec = eventSec = cal.get(Calendar.SECOND);

        //노선정보
        routeID = 1L;
        routeNum = "111-11";
        routeForm = " ";
        routeDivision = "00";

        //GPS정보
        double bufX = location.getLatitude() *100000;
        double bufY = location.getLongitude() *100000;
        locationX = (int)bufX;
        locationY = (int)bufY;
        bearing = (int)location.getBearing();
        speed = (int)location.getSpeed();

        //기기상태
        int i = 0;
        final Iterator<GpsSatellite> iter = gpsStatus.getSatellites().iterator();

        while(iter.hasNext()) {
            GpsSatellite satellite = iter.next();
            if(satellite.usedInFix()) {
                i++;
            }
        }
        if(i>=3) {
            gpsState = 0;
        } else gpsState = 128;
    }

    public void addUtilArriveStation() {
        arriveStationID = 10101010;
        arriveStationTurn = 1;
        adjacentTravelTime = 10;
        reservation = 1;
    }
}