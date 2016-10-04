package wj.networktest;

import android.os.CountDownTimer;
import android.os.Handler;

/**
 * Created by user on 2016-09-29.
 */

public class SocketReadTimeout extends CountDownTimer {
    Handler mHandler;
    public SocketReadTimeout(long millisInFuture, long countDownInterval, Handler mHandler) {
        super(millisInFuture, countDownInterval);
        this.mHandler = mHandler;

    }

    @Override
    public void onTick(long l) {
    }

    @Override
    public void onFinish() {
        mHandler.sendEmptyMessage(HandlerPosition.READ_TIMEOUT_ERROR);
    }
}
