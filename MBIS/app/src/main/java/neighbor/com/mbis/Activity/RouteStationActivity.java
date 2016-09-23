package neighbor.com.mbis.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import neighbor.com.mbis.Database.DBManager;
import neighbor.com.mbis.MapUtil.Value.RouteBuffer;
import neighbor.com.mbis.MapUtil.Value.StationSubBuffer_1;
import neighbor.com.mbis.MapUtil.Value.StationSubBuffer_2;
import neighbor.com.mbis.R;
import neighbor.com.mbis.MapUtil.Value.StationBuffer;

public class RouteStationActivity extends AppCompatActivity {

    TextView tv;

    String key;
    DBManager db = DBManager.getInstance(this);

    StationBuffer sBuf = StationBuffer.getInstance();
    StationSubBuffer_1 ssBuf = StationSubBuffer_1.getInstance();
    StationSubBuffer_2 sssBuf = StationSubBuffer_2.getInstance();

    RouteBuffer rBuf = RouteBuffer.getInstance();

    String[] routeStationDB = new String[]{"station_id", "station_order", "direction", "remark"};
    String[] stationDB = new String[]{"station_id", "station_nm", "admin_nm", "sido_cd", "x", "y", "station_division"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_station);

        tv = (TextView) findViewById(R.id.myLog);

        Intent intent = getIntent();
        key = intent.getStringExtra("routeInfo");

        Cursor c = db.queryRouteStation(
                routeStationDB,
                "route_id=? and direction=?",
                new String[]{key, Integer.toString(rBuf.getDirection())},
                null,
                null,
                "station_order"
        );
        int k = 0;
        if (rBuf.getDirection() == 1) {
            k = 2;
        } else if (rBuf.getDirection() == 2) {
            k = 1;
        }
        Cursor cc = db.queryRouteStation(
                routeStationDB,
                "route_id=? and direction=?",
                new String[]{key, Integer.toString(k)},
                null,
                null,
                "station_order"
        );

//        Cursor c = rsDB.myQuery(key);


        if (c != null) {
            while (c.moveToNext()) {
                String sid = c.getString(0);
                sBuf.addStationOrder(Integer.parseInt(c.getString(1)));
                sBuf.addRemark(Integer.parseInt(c.getString(3)));
                ssBuf.addStationOrder(Integer.parseInt(c.getString(1)));
                ssBuf.addRemark(Integer.parseInt(c.getString(3)));

                Cursor cs = db.queryStation(
                        stationDB ,
                        "station_id=?" ,
                        new String[]{sid} ,
                        null ,
                        null ,
                        null
                );
                if (cs != null) {
                    while (cs.moveToNext()) {
                        String idx[] = new String[stationDB.length];
                        for (int i = 0; i < idx.length; i++) {
                            idx[i] = cs.getString(i);
                        }
                        tv.append("[ 0 : " + idx[0] + " ] ");
                        tv.append("[ 1 : " + idx[1] + " ] ");
                        tv.append("[ 2 : " + idx[2] + " ] ");
                        tv.append("[ 3 : " + idx[3] + " ] ");
                        tv.append("[ 4 : " + idx[4] + " ] ");
                        tv.append("[ 5 : " + idx[5] + " ] ");
                        tv.append("[ 6 : " + idx[6] + " ] ");
                        tv.append("\n\n");

                        sBuf.getReferenceLatPosition().add(Double.parseDouble(idx[5]));
                        sBuf.getReferenceLngPosition().add(Double.parseDouble(idx[4]));
                        sBuf.getReferenceStationId().add(Long.parseLong(idx[0]));
                        sBuf.getStationDivision().add(Integer.parseInt(idx[6]));
                        sBuf.getReferenceStationName().add(idx[1]);


                        ssBuf.getReferenceLatPosition().add(Double.parseDouble(idx[5]));
                        ssBuf.getReferenceLngPosition().add(Double.parseDouble(idx[4]));
                        ssBuf.getReferenceStationId().add(Long.parseLong(idx[0]));
                        ssBuf.getStationDivision().add(Integer.parseInt(idx[6]));
                        ssBuf.getReferenceStationName().add(idx[1]);
                    }
                }


            }
            c.close();
        }

        if (cc != null) {
            while (cc.moveToNext()) {
                String sid = cc.getString(0);
                sssBuf.addStationOrder(Integer.parseInt(cc.getString(1)));
                sssBuf.addRemark(Integer.parseInt(cc.getString(3)));

                Cursor cs = db.queryStation(
                        stationDB,
                        "station_id=?",
                        new String[]{sid},
                        null,
                        null,
                        null
                );
                if (cs != null) {
                    while (cs.moveToNext()) {
                        String idx[] = new String[stationDB.length];
                        for (int i = 0; i < idx.length; i++) {
                            idx[i] = cs.getString(i);
                        }
                        tv.append("[ 0 : " + idx[0] + " ] ");
                        tv.append("[ 1 : " + idx[1] + " ] ");
                        tv.append("[ 2 : " + idx[2] + " ] ");
                        tv.append("[ 3 : " + idx[3] + " ] ");
                        tv.append("[ 4 : " + idx[4] + " ] ");
                        tv.append("[ 5 : " + idx[5] + " ] ");
                        tv.append("[ 6 : " + idx[6] + " ] ");
                        tv.append("\n\n");

                        sssBuf.getReferenceLatPosition().add(Double.parseDouble(idx[5]));
                        sssBuf.getReferenceLngPosition().add(Double.parseDouble(idx[4]));
                        sssBuf.getReferenceStationId().add(Long.parseLong(idx[0]));
                        sssBuf.getStationDivision().add(Integer.parseInt(idx[6]));

                        sssBuf.getReferenceStationName().add(idx[1]);
                    }
                }


            }
            cc.close();
        }
    }

    public void goMap(View v) {
        switch (v.getId()) {
            case R.id.goMap:
                startActivity(new Intent(this, MapActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        sBuf.getReferenceStationId().clear();
        sBuf.getReferenceLatPosition().clear();
        sBuf.getReferenceLngPosition().clear();
        sBuf.getStationOrder().clear();
        sBuf.getReferenceStationName().clear();

        ssBuf.getReferenceStationId().clear();
        ssBuf.getReferenceLatPosition().clear();
        ssBuf.getReferenceLngPosition().clear();
        ssBuf.getStationOrder().clear();
        ssBuf.getReferenceStationName().clear();

        sssBuf.getReferenceStationId().clear();
        sssBuf.getReferenceLatPosition().clear();
        sssBuf.getReferenceLngPosition().clear();
        sssBuf.getStationOrder().clear();
        sssBuf.getReferenceStationName().clear();

    }
}
