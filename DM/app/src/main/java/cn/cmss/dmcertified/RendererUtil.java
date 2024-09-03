package cn.cmss.dmcertified;
import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class RendererUtil {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN) // API level 16及更高版本支持该功能
    public static String getGPUModel(Context context) {
        //Context context = null; // 这里需要传入上下文对象

        if (context != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            try {
                Class<?> c = Class.forName("android.view.HardwareRenderer");

                Object renderer = c.getMethod("getInstance").invoke(null);
                return (String) c.getMethod("getName", int.class).invoke(renderer, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "无法获取";
    }
}