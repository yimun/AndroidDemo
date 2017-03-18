package me.yimu.demo.statusbar;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by linwei on 2017/3/17.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setStatusBarDarkIcon(boolean dark) {
        if (DeviceUtils.isMIUIv6()) {
            StatusBarUtils.MIUISetStatusBarLightMode(getWindow(), dark);
        } else if (DeviceUtils.isFlyme4()) {
            StatusBarUtils.FlymeSetStatusBarLightMode(getWindow(), dark);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }
}
