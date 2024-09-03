package cn.cmss.dmcertified;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import com.richinfo.mthttp.HttpRequestAgent;
import com.richinfo.mthttp.HttpRequestCallback;
import com.richinfo.mthttp.utils.SPUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.richinfo.mthttp.Constant;
import com.richinfo.mthttp.MTSDK;

import org.jetbrains.annotations.NotNull;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.opengl.GLES20;

import androidx.annotation.RequiresApi;

public class DmApplication extends Application {
    private static Context context;
    private long initTime = 0;
    private long networkAvilableTime = -1;
    private Timer timer = null;
    private TimerTask task = null;
    private int currentyear = 2023;
    private static final String TAG = "DMApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.i(TAG, "Application begain");
        //MTSDK.getInstance().init(DmApplication.this,MTSDK.MODEL_HTTP);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                MTSDK.getInstance().init(DmApplication.this, MTSDK.MODEL_LWM2M);

                File fileDir = getFilesDir();
                Log.i(TAG, "whx getFilesDir(): "+fileDir);
                File file = new File(fileDir, "dm.txt");
                if (file.exists()) {
                    MTSDK.getInstance().setUrl("coap://b.fxltsbl.com:5683");
                    MTSDK.setDebugMode(true);
                    Log.i(TAG, "dm  exists file: ");
                } else {
                    //商用平台
                    MTSDK.getInstance().setUrl("coap://m.fxltsbl.com:5683");
                    MTSDK.setDebugMode(false);
                    Log.i(TAG, "dm  file not exit: ");
                }
                MTSDK.setHeartChanel(MTSDK.HEART_CHANNEL_JOBSERVER);
                //MTSDK.setHeartChanel(MTSDK.HEART_CHANNEL_JOBSERVER);
                setDeviceManagerData();
                MTSDK.getInstance().startClient();
                Log.e(TAG, "run currentyear: " + currentyear);
            }
        }, 75000);//3秒后执行Runnable中的run方法
    }


    public void setProxy() {
        MTSDK.getInstance().setHttpRequestAgent(new HttpRequestAgent() {
            @Override
            public void request(String protocol, String url, String params, HttpRequestCallback callback) {
                /**
                 * @param protocol 协议 post或get
                 * @param url      请求地址
                 * @param params   请求参数
                 * @param callback 接口回调
                 */
                RequestBody body = RequestBody.create(params, MediaType.get("application/json"));
                Request request = new Request.Builder().url(url).post(body).build();
                new OkHttpClient.Builder().build().newCall(request)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                callback.onFailure(e.getMessage());
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                callback.onResponse(response.code(), response.body().string());
                            }
                        });
            }
        });

    }

    public void setDeviceManagerData() {

        SDKDeviceManagerTest sdkDeviceManagerTest = new SDKDeviceManagerTest();
//        sdkDeviceManagerTest.setContext(context);
//
//        MTSDK.getInstance().setDeviceManager(sdkDeviceManagerTest);

//        String deviceInfoJson;
//        //注入配置
        // deviceInfoJson = SPUtils.getTestDeviceInfo();
//        SDKDeviceManagerTest sdkDeviceManagerTest = new SDKDeviceManagerTest();
//        if (!TextUtils.isEmpty(deviceInfoJson)) {
//            sdkDeviceManagerTest = new Gson().fromJson(deviceInfoJson, SDKDeviceManagerTest.class);
//        }
        //SPUtils.setTestSwitch(true);
        sdkDeviceManagerTest.setContext(this);
        //sdkDeviceManagerTest.getAppInfo();
        sdkDeviceManagerTest.getMac();
        sdkDeviceManagerTest.getCpu();
        sdkDeviceManagerTest.getRouterMac();
        MTSDK.getInstance().setDeviceManager(sdkDeviceManagerTest);
    }

    public boolean systemTestMode() {

        String value = "unKnown";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, "ro.dm.modeTest", "unknown"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.i(TAG, "systemTestMode: " + value);
            if (value.equals("test")) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean systemdebugMode() {

        String value = "unKnown";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, "ro.dm.modeDebug", "unknown"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.i(TAG, "systemTestMode: " + value);
            if (value.equals("test")) {
                return true;
            } else {
                return false;
            }
        }
    }
}
