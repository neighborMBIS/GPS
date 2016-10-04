package wj.timertest;

import android.app.Notification;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 2016-09-21.
 */
public class BusLocationInfoTimer extends Thread {

    Message msg;
    Handler mHandler;
    private boolean isPlay = false;

    public BusLocationInfoTimer(Handler mHandler) {
        isPlay = true;
        this.mHandler = mHandler;
    }
    public void isThreadState(boolean isPlay) {
        this.isPlay = isPlay;
    }

    public void startThread() {
        isPlay = true;
    }

    public void stopThread() {
        isPlay = false;
    }
    @Override
    public void run() {
        super.run();

        while (isPlay) {
            MessagePosition.count--;
            if(MessagePosition.count > 0) {
                msg = Message.obtain();
                msg.what = MessagePosition.TIME_CHANGE;
                mHandler.sendMessage(msg);
            } else {
                msg = Message.obtain();
                msg.what = MessagePosition.SEND_BUS_LOCATION_INFO;
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
