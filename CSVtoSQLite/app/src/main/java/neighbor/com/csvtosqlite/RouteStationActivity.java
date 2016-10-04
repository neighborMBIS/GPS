package neighbor.com.csvtosqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import neighbor.com.csvtosqlite.DBManager.StationDBManager;
import neighbor.com.csvtosqlite.DBManager.RouteStationDBManager;

public class RouteStationActivity extends AppCompatActivity {

    TextView tv;

    String key;
    RouteStationDBManager rsDB = RouteStationDBManager.getInstance(this);
    StationDBManager sDB = StationDBManager.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_station);

        tv = (TextView) findViewById(R.id.myLog);

        Intent intent = getIntent();
        key = intent.getStringExtra("routeInfo");

        Cursor c = rsDB.query(new String[]{"station_id"}, "route_id=?", new String[]{key}, null, null, null);
//        Cursor c = rsDB.myQuery(key);


        if(c != null) {
            while(c.moveToNext()) {
                String sid = c.getString(0);
                Cursor cs = sDB.query(
                        new String[]{"station_id", "station_nm", "admin_nm", "sido_cd", "x", "y"},
                        "station_id=?",
                        new String[]{sid},
                        null,
                        null,
                        null
                );
                if(cs != null) {
                    while(cs.moveToNext()) {
                        String idx[] = new String[6];
                        for(int i=0 ; i<6 ; i++) {
                            idx[i] = cs.getString(i);
                        }
                        tv.append("[ 0 : " + idx[0] + " ] ");
                        tv.append("[ 1 : " + idx[1] + " ] ");
                        tv.append("[ 2 : " + idx[2] + " ] ");
                        tv.append("[ 3 : " + idx[3] + " ] ");
                        tv.append("[ 4 : " + idx[4] + " ] ");
                        tv.append("[ 5 : " + idx[5] + " ] ");
                        tv.append("\n\n");
                    }
                }


            }
            c.close();
        }


    }
}
