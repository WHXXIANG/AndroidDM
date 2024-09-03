package cn.cmss.dmcertified;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.richinfo.mthttp.Constant;
import com.richinfo.mthttp.HttpRequestAgent;
import com.richinfo.mthttp.HttpRequestCallback;
import com.richinfo.mthttp.MTSDK;
import com.richinfo.mthttp.utils.SPUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import android.app.Activity;
public class MainActivity extends Activity{
    private static final String TAG = "DMApplication";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("debug")) {
            String FILENAME = "dm.txt";
            String string = "hello world!";
            Log.d(TAG, "whx debug_button click ");
            FileOutputStream fos = null;
            try {
                fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                fos.write(string.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if((intent != null && intent.hasExtra("release"))){
            File fileDir = getFilesDir();
            Log.d(TAG, "whx getFilesDir(): "+fileDir);
            File file = new File(fileDir, "dm.txt");
            if (file.exists()) {
                Log.d(TAG, "whx realse_button delete file: ");
                file.delete();
            } else {
                Log.d(TAG, "whx realse_button file not exit: ");
            }
        }
        finish();
    }
}

