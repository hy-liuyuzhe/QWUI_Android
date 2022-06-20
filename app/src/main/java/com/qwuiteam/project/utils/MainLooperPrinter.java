package com.qwuiteam.project.utils;

import android.os.SystemClock;
import android.util.Log;
import android.util.Printer;

public class MainLooperPrinter implements Printer {

    private boolean mStartedPrinting;
    private long mStartTimeMillis;
    private long mStartThreadTimeMillis;
    private long mBlockThresholdMillis = 500;

    @Override
    public void println(String x) {
        Log.d("liuyuzhe", "println: 收到消息"+x);
        if (!mStartedPrinting) {
            mStartTimeMillis = System.currentTimeMillis();
            mStartThreadTimeMillis = SystemClock.currentThreadTimeMillis();
            mStartedPrinting = true;
        } else {
            final long endTime = System.currentTimeMillis();
            mStartedPrinting = false;
            Log.d("liuyuzhe", "use time: " + (endTime - mStartTimeMillis));
            if (isBlock(endTime)) {
                notifyBlockEvent(endTime);
            }
        }
    }

    private void notifyBlockEvent(long endTime) {
        Log.d("liuyuzhe", "notifyBlockEvent: ");
    }

    private boolean isBlock(long endTime) {
        return endTime - mStartTimeMillis > mBlockThresholdMillis;
    }
}
