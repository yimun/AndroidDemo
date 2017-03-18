package me.yimu.demo.statusbar;

import android.os.Build;

/**
 * Created by linwei on 2017/3/17.
 */

public class DeviceUtils {

    public static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isFlyme4() {
        return isMeizuDevice() && hasKitkat();
    }

    public static boolean isMeizuDevice() {
        return android.os.Build.MANUFACTURER.equalsIgnoreCase("Meizu");
    }

    public static boolean isXiaomiDevice() {
        return android.os.Build.MANUFACTURER.equalsIgnoreCase("Xiaomi");
    }

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUIv6() {
        return isMIUI() && hasKitkat();
    }

    public static boolean isMIUI() {
        final BuildProperties prop = BuildProperties.getDefault();
        return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
    }
}
