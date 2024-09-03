package cn.cmss.dmcertified;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.richinfo.mthttp.Constant;
import com.richinfo.mthttp.MTSDK;


/**
 * @author Bingo
 * @date 2020/11/16
 */
public class SelectActivity extends Activity {
    private static final String TAG = "SelectActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select);
        //initView();
    }

    private void initView() {
        findViewById(R.id.sdk_http).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTestActivity(MTSDK.MODEL_HTTP);
            }
        });

        findViewById(R.id.sdk_lwm2m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTestActivity(MTSDK.MODEL_LWM2M);
            }
        });
    }

    private void startTestActivity(int mode) {
        RadioButton radioButton=findViewById(R.id.jobserverRadio);
        Intent i = new Intent(SelectActivity.this, MainActivity.class);
        i.putExtra(Constant.SERVER_MODEL, mode);
        i.putExtra("HeartChanel",radioButton.isChecked()?MTSDK.HEART_CHANNEL_JOBSERVER:MTSDK.HEART_CHANNEL_ALARM);
        startActivity(i);
        finish();
    }
}
