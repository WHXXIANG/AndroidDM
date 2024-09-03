package cn.cmss.dmcertified;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class StartslefBroadcastReceiver extends BroadcastReceiver {
    static final String action_boot="android.intent.action.BOOT_COMPLETED";
    private static final String TAG = "DMApplication";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "自启动进入");
        if(intent.getAction().equals(action_boot)){
            Intent mBootIntent = new Intent(context,MainActivity.class);
            mBootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mBootIntent);
        }
    }

}
