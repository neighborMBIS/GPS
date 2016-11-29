package wj.animationtest;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Double> distance = new ArrayList<Double>();
    Buf b = Buf.getInstance();

    private EditText location;
    private ListView stationList;
    private Button plusBtn, arriveBtn, startBtn;
    private TextView startDisplay, arriveDisplay;
    private ImageView busImage;
    private ViewGroup lockImage;

    ArrayList<String> arrBuf = new ArrayList<String>();

    int busPosition = -1;
    int selectionPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defaultInfo();

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                busPosition++;
                location.setText(Integer.toString(busPosition));
                selectionPosition = getMinDistancePosition(busPosition);
                int buf = 0;
                if (selectionPosition < 0) {
                    buf = selectionPosition;
                    selectionPosition = 0;
                }
                stationList.setSelection(selectionPosition+2);
            }
        });
        arriveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockImage.setBackgroundResource(R.drawable.focus_arrive);
                busImage.setVisibility(View.VISIBLE);
                arriveDisplay.setVisibility(View.VISIBLE);
                startDisplay.setVisibility(View.INVISIBLE);
            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lockImage.setBackgroundResource(R.drawable.focus_start);
                busImage.setVisibility(View.VISIBLE);
                arriveDisplay.setVisibility(View.INVISIBLE);
                startDisplay.setVisibility(View.VISIBLE);
            }
        });
    }
    private void defaultInfo() {
        location = (EditText) findViewById(R.id.location);
        stationList = (ListView) findViewById(R.id.stationList);
        plusBtn = (Button) findViewById(R.id.plusButton);
        arriveBtn = (Button) findViewById(R.id.arriveBtn);
        startBtn = (Button) findViewById(R.id.startBtn);
        busImage = (ImageView) findViewById(R.id.busImage);
        startDisplay = (TextView) findViewById(R.id.startDisplay);
        arriveDisplay = (TextView) findViewById(R.id.arriveDisplay);
        lockImage = (ViewGroup) findViewById(R.id.lockImage);


        arrBuf.add("");
        arrBuf.add("");
        arrBuf.add("");
        arrBuf.add("");
        arrBuf.add("");
        for(int i=0 ; i<b.station.size() ; i++) {
            arrBuf.add(Integer.toString(b.station.get(b.station.size() -1 -i)));
        }
        arrBuf.add("");
        MyArrayAdapter mAdapter = new MyArrayAdapter(this, R.layout.map_item, arrBuf);
        stationList.setAdapter(mAdapter);
        stationList.setClickable(false);
        stationList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int act = motionEvent.getAction();
                switch (act & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        motionEvent.setAction(MotionEvent.ACTION_CANCEL);
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                    case MotionEvent.ACTION_POINTER_UP:

                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        stationList.setSelection(arrBuf.size()-1);

    }

    private int getMinDistancePosition(int position) {
        double Min = Double.MAX_VALUE;
        int minPosition = -1;

        for (int i = 0; i < arrBuf.size(); i++) {
            if(arrBuf.get(i)!="") {
                distance.add(i, (double) Math.abs(Integer.parseInt(arrBuf.get(i)) - position));
                if (distance.get(i) < Min) {
                    Min = distance.get(i) + 5;
                    minPosition = i;
                }
            }
        }

        return minPosition;
    }
}
