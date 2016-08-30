package neighbor.com.csvtosqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import neighbor.com.csvtosqlite.DBManager.RouteDBManager;

public class SelectRouteActivity extends AppCompatActivity {

    ListView mList;
    RouteDBManager rDB;
    TextView tv;
    SimpleCursorAdapter scAdapter;
    ArrayList<String> arr = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mList = (ListView)findViewById(R.id.mList);
        tv = (TextView) findViewById(R.id.text);

        rDB = RouteDBManager.getInstance(this);

//        cAdapter = new MyCursorAdapter(this, dbHelper.query(new String[]{"route_id"}, null, null, null, null, null));

        scAdapter = new SimpleCursorAdapter(
                this,
                R.layout.item,
                rDB.query(new String[]{"_id", "id", "route_id"}, null, null, null, null, null),
                new String[]{"route_id"},
                new int[]{R.id.busNum}
        );

        mList.setAdapter(scAdapter);


        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor c = (Cursor)mList.getItemAtPosition(position);
                String text = c.getString(1).toString();
                Intent i = new Intent(getApplicationContext(), RouteStationActivity.class);
                i.putExtra("routeInfo", text);
                startActivity(i);
                finish();
            }
        });
    }


}
