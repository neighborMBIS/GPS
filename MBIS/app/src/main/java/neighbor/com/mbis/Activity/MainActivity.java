package neighbor.com.mbis.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import neighbor.com.mbis.CSV_Util.RouteStationUtil;
import neighbor.com.mbis.CSV_Util.RouteUtil;
import neighbor.com.mbis.CSV_Util.StationUtil;
import neighbor.com.mbis.DBManager.RouteDBManager;
import neighbor.com.mbis.DBManager.RouteStationDBManager;
import neighbor.com.mbis.DBManager.StationDBManager;
import neighbor.com.mbis.R;

public class MainActivity extends AppCompatActivity {


    RouteDBManager rDB;
    RouteStationDBManager rsDB;
    StationDBManager sDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        rDB = RouteDBManager.getInstance(this);
        rsDB = RouteStationDBManager.getInstance(this);
        sDB = StationDBManager.getInstance(this);

//        rDB.delete(null, null);
//        rsDB.delete(null, null);
//        sDB.delete(null, null);
//
//        addRouteList();
//        addStationList();
//        addRouteStationList();

        startActivity(new Intent(getApplicationContext(), SelectRouteActivity.class));
        finish();
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
                RouteUtil r = new RouteUtil();
                r.setId(rowData[0]);
                r.setRoute_id(rowData[1]);
                r.setSt_sta_id(rowData[3]);
                r.setEd_sta_id(rowData[4]);
                r.setCompany_nm(rowData[9]);
                r.setAdmin_nm(rowData[10]);
                r.setCompany_id(rowData[11]);

                addRouteUtil(r);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Fail ToT", Toast.LENGTH_SHORT).show();
        }
    }

    private void addRouteUtil(RouteUtil ru) {
//        mDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id", ru.getId()); // Contact Name
        values.put("route_id", ru.getRoute_id());
        values.put("st_sta_id", ru.getSt_sta_id());
        values.put("ed_sta_id", ru.getEd_sta_id());
        values.put("company_nm", ru.getCompany_nm());
        values.put("admin_nm", ru.getAdmin_nm());
        values.put("company_id", ru.getCompany_id());

        // Inserting Row
        rDB.insert(values);
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
                StationUtil s = new StationUtil();
                s.setStation_id(rowData[0]);
                s.setStation_nm(rowData[1]);
                s.setAdmin_nm(rowData[4]);
                s.setSido_cd(rowData[5]);
                s.setLocalcoordinatesX(rowData[8]);
                s.setLocalcoordinatesY(rowData[9]);

                addStationUtil(s);

            }
        } catch (IOException e) {
            Toast.makeText(this, "Fail ToT", Toast.LENGTH_SHORT).show();
        }

    }

    private void addStationUtil(StationUtil su) {
        ContentValues values = new ContentValues();

        values.put("station_id", su.getStation_id()); // Contact Name
        values.put("station_nm", su.getStation_nm());
        values.put("admin_nm", su.getAdmin_nm());
        values.put("sido_cd", su.getSido_cd());
        values.put("x", su.getLocalcoordinatesX());
        values.put("y", su.getLocalcoordinatesY());

        // Inserting Row
        sDB.insert(values);

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
                rs.setStation_order(rowData[1]);
                rs.setStation_id(rowData[2]);
                rs.setLink_order(rowData[3]);
                rs.setRemark(rowData[4]);

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
        values.put("remark", rsu.getRemark());

        // Inserting Row
        rsDB.insert(values);

    }

}
