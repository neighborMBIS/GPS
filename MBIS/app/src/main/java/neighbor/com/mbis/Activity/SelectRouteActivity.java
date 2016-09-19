package neighbor.com.mbis.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import neighbor.com.mbis.CSV_Util.RouteStationUtil;
import neighbor.com.mbis.Database.DBManager;
import neighbor.com.mbis.MapUtil.Value.RouteBuffer;
import neighbor.com.mbis.R;

public class SelectRouteActivity extends AppCompatActivity {

    ListView mList;
    DBManager db;
    TextView tv;
    SimpleCursorAdapter scAdapter;
    RouteBuffer rBuf = RouteBuffer.getInstance();

    SharedPreferences pref;
    private static final String MY_DB="my_db";
    private static String HasVisited = "hasVisited";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mList = (ListView)findViewById(R.id.mList);
        tv = (TextView) findViewById(R.id.text);

        db = DBManager.getInstance(this);
        isHasVisited(this);

//        cAdapter = new MyCursorAdapter(this, dbHelper.query(new String[]{"route_id"}, null, null, null, null, null));

        scAdapter = new SimpleCursorAdapter(
                this,
                R.layout.item,
                db.queryRoute(new String[]{"_id", "id", "route_id", "direction"}, null, null, null, null, null),
                new String[]{"route_id", "direction"},
                new int[]{R.id.busNum, R.id.busDivision}
        );

        mList.setAdapter(scAdapter);


        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor c = (Cursor)mList.getItemAtPosition(position);

                String text = c.getString(1).toString();
                rBuf.setRouteID(Long.parseLong(text));
                rBuf.setRouteName(c.getString(2).toString());
                String dir = c.getString(3).toString();
                rBuf.setDirection(Integer.parseInt(dir));

                Intent i = new Intent(getApplicationContext(), RouteStationActivity.class);
                i.putExtra("routeInfo", text);
                startActivity(i);
            }
        });
    }

    private void addRouteList() {
        try {
            InputStream is = this.getAssets().open("my_route.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "euc-kr"));

            String line;

            //Read each line
            while ((line = reader.readLine()) != null) {

                //Split to separate the name from the capital
                String[] rowData = line.split(",");

                //Create a State object for this row's data.
                neighbor.com.mbis.CSV_Util.RouteUtil r = new neighbor.com.mbis.CSV_Util.RouteUtil();
                r.setId(rowData[0]);
                r.setRoute_id(rowData[1]);
                r.setSt_sta_id(rowData[3]);
                r.setEd_sta_id(rowData[4]);
                r.setCompany_nm(rowData[9]);
                r.setAdmin_nm(rowData[10]);
                r.setCompany_id(rowData[11]);
                r.setDirection(rowData[14]);

                addRouteUtil(r);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Fail ToT", Toast.LENGTH_SHORT).show();
        }
    }

    private void addRouteUtil(neighbor.com.mbis.CSV_Util.RouteUtil ru) {
//        mDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", ru.getId()); // Contact Name
        values.put("route_id", ru.getRoute_id());
        values.put("st_sta_id", ru.getSt_sta_id());
        values.put("ed_sta_id", ru.getEd_sta_id());
        values.put("company_nm", ru.getCompany_nm());
        values.put("admin_nm", ru.getAdmin_nm());
        values.put("company_id", ru.getCompany_id());
        values.put("direction", ru.getDirection());

        // Inserting Row
        db.insertRoute(values);
    }

    private void addStationList() {
        try {
            InputStream is = this.getAssets().open("my_station.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "euc-kr"));

            String line;

            //Read each line
            while ((line = reader.readLine()) != null) {

                //Split to separate the name from the capital
                String[] rowData = line.split(",");

                //Create a State object for this row's data.
                neighbor.com.mbis.CSV_Util.StationUtil s = new neighbor.com.mbis.CSV_Util.StationUtil();
                s.setStation_id(rowData[0]);
                s.setStation_nm(rowData[1]);
                s.setAdmin_nm(rowData[4]);
                s.setSido_cd(rowData[5]);
                s.setLocalcoordinatesX(rowData[8]);
                s.setLocalcoordinatesY(rowData[9]);
                s.setStation_division(rowData[10]);

                addStationUtil(s);

            }
        } catch (IOException e) {
            Toast.makeText(this, "Fail ToT", Toast.LENGTH_SHORT).show();
        }

    }

    private void addStationUtil(neighbor.com.mbis.CSV_Util.StationUtil su) {
        ContentValues values = new ContentValues();

        values.put("station_id", su.getStation_id()); // Contact Name
        values.put("station_nm", su.getStation_nm());
        values.put("admin_nm", su.getAdmin_nm());
        values.put("sido_cd", su.getSido_cd());
        values.put("x", su.getLocalcoordinatesX());
        values.put("y", su.getLocalcoordinatesY());
        values.put("station_division", su.getStation_division());

        // Inserting Row
        db.insertStation(values);

    }

    private void addRouteStationList() {
        try {
            InputStream is = this.getAssets().open("my_route_station.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "euc-kr"));

            String line;

            //Read each line
            while ((line = reader.readLine()) != null) {

                //Split to separate the name from the capital
                String[] rowData = line.split(",");

                //Create a State object for this row's data.
                RouteStationUtil rs = new RouteStationUtil();
                rs.setRoute_id(rowData[0]);
                rs.setStation_order(Integer.parseInt(rowData[1]));
                rs.setStation_id(rowData[2]);
                rs.setLink_order(rowData[3]);
                rs.setDirection(rowData[4]);
                rs.setRemark(rowData[5]);


                addRouteStationUtil(rs);

            }
        } catch (IOException e) {
            Toast.makeText(this, "Fail ToT", Toast.LENGTH_SHORT).show();
        }


    }

    private void addRouteStationUtil(RouteStationUtil rsu) {
        ContentValues values = new ContentValues();

        values.put("route_id", rsu.getRoute_id()); // Contact Name
        values.put("station_order", rsu.getStation_order());
        values.put("station_id", rsu.getStation_id());
        values.put("link_order", rsu.getLink_order());
        values.put("direction", rsu.getDirection());
        values.put("remark", rsu.getRemark());

        // Inserting Row
        db.insertRouteStation(values);

    }

    public void isHasVisited(ContextWrapper cw){
        pref = cw.getSharedPreferences(MY_DB, Context.MODE_PRIVATE);

        boolean hasVisited = pref.getBoolean(HasVisited, false);
        if(!hasVisited){
            SharedPreferences.Editor e = pref.edit();
            e.putBoolean(HasVisited, true);
            e.commit();

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                int permissionResult = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionResult == PackageManager.PERMISSION_DENIED) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                        dialog.setTitle("권한이 필요합니다.")
                                .setMessage("이 기능을 사용하기 위해서는 단말기의 \"GPS, Storage\" 권한이 필요합니다. 계속하시겠습니까?")
                                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                                        }

                                    }
                                })
                                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getApplicationContext(), "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create()
                                .show();
                    }

                    //최초로 권한을 요청할 때
                    else {
                        // CALL_PHONE 권한을 Android OS 에 요청한다.
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                    }
                }
            }


            Toast.makeText(this, "잠시만 기다려 주세요. Database 를 확인하는 중입니다.", Toast.LENGTH_SHORT).show();

            addRouteList();
            addStationList();
            addRouteStationList();
        }
    }

}
