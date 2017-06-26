package me.yimu.demo.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by linwei on 2017/6/23.
 */

public class CustomViewGroup extends LinearLayout {

    static String TAG = "ViewGroup";

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Utils.logEvent(event, TAG + " onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Utils.logEvent(ev, TAG + " dispatchTouchEvent");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Utils.logEvent(ev, TAG + " onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
