package cn.cmss.dmcertified;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BatteryUtils {
    public static double getBatteryCapacity(Context context) {
        Object powerProfile = null;
        double batteryCapacity = 0;

        try {
            powerProfile = Class.forName("com.android.internal.os.PowerProfile")
                    .getConstructor(Context.class)
                    .newInstance(context);
            batteryCapacity = (double) Class.forName("com.android.internal.os.PowerProfile")
                    .getMethod("getBatteryCapacity")
                    .invoke(powerProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return batteryCapacity;
    }

    public static int getCurrentBatteryCapacity(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int batteryCapacity = (int) ((float) level / scale * 100);
        return batteryCapacity;
    }
}