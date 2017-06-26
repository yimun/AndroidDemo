package me.yimu.demo.touch;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by linwei on 2017/6/24.
 */

public class Utils {

    public static void logEvent(MotionEvent event, String tag) {
        Log.d(tag, String.format("Action=[%s], rawX=%d, rawY=%d",
                getEventType(event), (int) event.getRawX(), (int) event.getRawY()));
    }

    public static String getEventType(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            default:
                return "ACTION_OTHER";
        }
    }
}
