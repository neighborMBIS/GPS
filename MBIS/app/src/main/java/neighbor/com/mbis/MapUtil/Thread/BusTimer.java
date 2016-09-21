package neighbor.com.mbis.MapUtil.Thread;

import android.os.Handler;
import android.os.Message;

import neighbor.com.mbis.MapUtil.HandlerPosition;
import neighbor.com.mbis.MapUtil.Value.LogicBuffer;

/**
 * Created by user on 2016-09-21.
 */
public class BusTimer extends Thread {

    Message msg;
    Handler mHandler;

    public BusTimer(Handler mHandler) {
        this.mHandler = mHandler;
    }
    @Override
    public void run() {
        super.run();

        while (!Thread.currentThread().isInterrupted()) {
            LogicBuffer.countBy_30sec--;
            if(LogicBuffer.countBy_30sec > 0) {
                msg = Message.obtain();
                msg.what = HandlerPosition.TIME_CHANGE;
                mHandler.sendMessage(msg);
            } else {
                msg = Message.obtain();
                msg.what = HandlerPosition.SEND_BUS_LOCATION_INFO;
                mHandler.sendMessage(msg);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
