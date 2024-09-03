package cn.cmss.dmcertified;

import android.content.Context;

/**
 * Created by Administrator on 2017/1/18.
 */

public class TestSPUtils {
    private static final String TAG = "SPUtils";

    private static final String FILENAME="test";
    private static final String LAST_APP_INFO_REPORT_TIME="lastAppInfoReportTime";

    public static long getLastAppInfoReportTime(Context context){
        return SharedPreferencesUtils.getValueInPrivateMode(context,FILENAME,LAST_APP_INFO_REPORT_TIME,0L);
    }

    public static void setLastAppInfoReportTime(Context context,long time){
        SharedPreferencesUtils.setValueInPrivateMode(context,LAST_APP_INFO_REPORT_TIME,time,FILENAME);
    }

}
