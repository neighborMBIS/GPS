package wj.filechecktest;

import android.content.ContentValues;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import wj.filechecktest.CSV_Util.RouteStationUtil;
import wj.filechecktest.CSV_Util.RouteUtil;
import wj.filechecktest.CSV_Util.StationUtil;
import wj.filechecktest.Database.DBManager;

public class MainActivity extends AppCompatActivity {

    DBManager db;
    long todayLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = DBManager.getInstance(this);
        setTodayLong();

        checkData();

    }

    private void setTodayLong() {
        TimeZone jst = TimeZone.getTimeZone("JST");
        Calendar cal = Calendar.getInstance(jst);

        String today = String.format("%02d", cal.get(Calendar.YEAR) - 2000)
                + String.format("%02d", (cal.get(Calendar.MONTH) + 1))
                + String.format("%02d", cal.get(Calendar.DATE))
                + String.format("%02d", (cal.get(Calendar.HOUR_OF_DAY)) + 9)
                + String.format("%02d", cal.get(Calendar.MINUTE))
                + String.format("%02d", (cal.get(Calendar.SECOND)));
        todayLong = Long.parseLong(today);
    }

    private void checkData() {
        ArrayList<File> csvFiles = new ArrayList<File>();

        File f = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
        if (!f.exists()) {
            return;
        }
        File[] allFiles = f.listFiles();

        for (File file : allFiles) {
            if (file.getName().endsWith(".csv")) {
                csvFiles.add(file);
                overwriteDB(file);
            }
        }
    }

    public void overwriteDB(File file) {

        try {
            FileReader reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader);

            String[] fileName = file.getName().split("-");

            if(todayLong > Long.parseLong(fileName[0])) {
                if(fileName[1].startsWith("R.")) {
                    db.deleteRoute(null, null);
                    write_R(in);
                } else if(fileName[1].startsWith("S.")) {
                    db.deleteStation(null, null);
                    write_S(in);
                } else if(fileName[1].startsWith("RS.")) {
                    db.deleteRouteStation(null, null);
                    write_RS(in);
                }
                file.delete();
            }

        } catch (IOException e) {
            Toast.makeText(this, "Fail ToT", Toast.LENGTH_SHORT).show();
        }
    }

    private void write_R(BufferedReader in) {

        String line = "";
        try {
            while ((line = in.readLine()) != null) {

                //Split to separate the name from the capital
                String[] rowData = line.split(",");

                //Create a State object for this row's data.
                RouteUtil r = new RouteUtil();
                r.setRoute_id(rowData[0]);
                r.setRoute_name(rowData[1]);
                r.setRoute_form(Integer.parseInt(rowData[2]));
                r.setRoute_type(Integer.parseInt(rowData[3]));
                r.setRoute_first_start_time(Integer.parseInt(rowData[4]));
                r.setRoute_last_start_time(Integer.parseInt(rowData[5]));
                r.setRoute_average_interval(Integer.parseInt(rowData[6]));
                r.setRoute_average_time(Integer.parseInt(rowData[7]));
                r.setRoute_length(Integer.parseInt(rowData[8]));
                r.setRoute_station_num(Integer.parseInt(rowData[9]));
                r.setRoute_start_station(rowData[10]);
                r.setRoute_important_station1(rowData[11]);
                r.setRoute_important_station2(rowData[12]);
                r.setRoute_last_station(rowData[13]);

                addRouteUtil(r);
            }
            Toast.makeText(this, "R ok", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
        }
    }

    private void write_S(BufferedReader in) {
        String line;

        //Read each line
        try {
            while ((line = in.readLine()) != null) {

                //Split to separate the name from the capital
                String[] rowData = line.split(",");

                //Create a State object for this row's data.
                StationUtil s = new StationUtil();
                s.setStation_id(rowData[0]);
                s.setStation_name(rowData[1]);
                s.setStation_type(Integer.parseInt(rowData[2]));
                s.setStation_angle(Integer.parseInt(rowData[3]));
                s.setStation_x(rowData[4]);
                s.setStation_y(rowData[5]);
                s.setStation_arrive_distance(Integer.parseInt(rowData[6]));
                s.setStation_start_distance(Integer.parseInt(rowData[7]));

                addStationUtil(s);
            }
            Toast.makeText(this, "S ok", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
        }
    }

    private void write_RS(BufferedReader in) {
        String line;

        //Read each line
        try {
                while ((line = in.readLine()) != null) {

                    //Split to separate the name from the capital
                    String[] rowData = line.split(",");

                    //Create a State object for this row's data.
                    RouteStationUtil rs = new RouteStationUtil();
                    rs.setRoute_id(rowData[0]);
                    rs.setRoute_form(Integer.parseInt(rowData[1]));
                    rs.setStation_id(rowData[2]);
                    rs.setStation_order(Integer.parseInt(rowData[3]));
                    rs.setStation_distance(Integer.parseInt(rowData[4]));
                    rs.setStation_time(Integer.parseInt(rowData[5]));

                    addRouteStationUtil(rs);
            }
            Toast.makeText(this, "RS ok", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
        }
    }

    private void addRouteUtil(RouteUtil ru) {
//        mDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("route_id", ru.getRoute_id());
        values.put("route_name", ru.getRoute_name());
        values.put("route_form", ru.getRoute_form());
        values.put("route_type", ru.getRoute_type());
        values.put("route_first_start_time", ru.getRoute_first_start_time());
        values.put("route_last_start_time", ru.getRoute_last_start_time());
        values.put("route_average_interval", ru.getRoute_average_interval());
        values.put("route_average_time", ru.getRoute_average_time());
        values.put("route_length", ru.getRoute_length());
        values.put("route_station_num", ru.getRoute_station_num());
        values.put("route_start_station", ru.getRoute_start_station());
        values.put("route_important_station1", ru.getRoute_important_station1());
        values.put("route_important_station2", ru.getRoute_important_station2());
        values.put("route_last_station", ru.getRoute_last_station());

        // Inserting Row
        db.insertRoute(values);
    }

    private void addStationUtil(StationUtil su) {
        ContentValues values = new ContentValues();

        values.put("station_id", su.getStation_id());
        values.put("station_name", su.getStation_name());
        values.put("station_type", su.getStation_type());
        values.put("station_angle", su.getStation_angle());
        values.put("station_x", su.getStation_x());
        values.put("station_y", su.getStation_y());
        values.put("station_arrive_distance", su.getStation_arrive_distance());
        values.put("station_start_distance", su.getStation_start_distance());

        // Inserting Row
        db.insertStation(values);

    }

    private void addRouteStationUtil(RouteStationUtil rsu) {
        ContentValues values = new ContentValues();

        values.put("route_id", rsu.getRoute_id()); // Contact Name
        values.put("route_form", rsu.getRoute_form()); // Contact Name
        values.put("station_id", rsu.getStation_id()); // Contact Name
        values.put("station_order", rsu.getStation_order()); // Contact Name
        values.put("station_distance", rsu.getStation_distance()); // Contact Name
        values.put("station_time", rsu.getStation_time()); // Contact Name

        // Inserting Row
        db.insertRouteStation(values);

    }

}
