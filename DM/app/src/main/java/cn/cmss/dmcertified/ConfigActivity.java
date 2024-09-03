package cn.cmss.dmcertified;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.richinfo.mthttp.Constant;
import com.richinfo.mthttp.MTSDK;
import com.richinfo.mthttp.entity.FieldConfigEntity;
import com.richinfo.mthttp.entity.RuleConfigEntity;
import com.richinfo.mthttp.utils.SPUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Bingo
 * @date 2020/11/16
 */
public class ConfigActivity extends Activity {


    LinearLayout ll;
    FieldConfigEntity fieldConfigEntity;
    RuleConfigEntity ruleConfigEntity;
    SDKDeviceManagerTest sdkDeviceManagerTest;
    int netMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        netMode=getIntent().getIntExtra(Constant.SERVER_MODEL, MTSDK.MODEL_LWM2M);
        ll=findViewById(R.id.config_ll);
        //覆盖服务端配置
        Button button=findViewById(R.id.cover_server_btn);
        boolean b=Boolean.parseBoolean(SPUtils.getTestSwitch());
        if (b){
            button.setText("使用本地测试数");
        }else{
            button.setText("使用服务端数据");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b=Boolean.parseBoolean(SPUtils.getTestSwitch());
                if (b){
                    button.setText("使用服务端数据");
                    SPUtils.setTestSwitch(false);
                }else {
                    button.setText("使用本地测试数");
                    SPUtils.setTestSwitch(true);
                }
            }
        });

        //配置文件读写
        setConfigTitle("设备信息配置");
        String json= SPUtils.getTestDeviceInfo();
        if (TextUtils.isEmpty(json)&&!"null".equals(json)){
            sdkDeviceManagerTest=new SDKDeviceManagerTest();
        }else {
            sdkDeviceManagerTest=new Gson().fromJson(json,SDKDeviceManagerTest.class);
        }
        sdkDeviceManagerTest.setContext(this);
        sdkDeviceManagerTest.getAppInfo();
        getDeviceConfig(sdkDeviceManagerTest);
        setConfigTitle("服务器URL配置");
        LinearLayout urlLl=new LinearLayout(this);
        ll.addView(urlLl);
        EditText urlEdt=new EditText(this);
        urlLl.addView(urlEdt);
        if (netMode== MTSDK.MODEL_HTTP){
            urlEdt.setText(SPUtils.getHttpUrl());
        }else {
            urlEdt.setText(SPUtils.getLwm2mUrl());
        }
        urlEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String t=s.toString();

                if (netMode== MTSDK.MODEL_HTTP){
                    MTSDK.getInstance().setUrl(t);
                }else {
                    MTSDK.getInstance().setUrl(t);
                }
            }
        });
        setConfigTitle("采集信息配置");
        String str1= SPUtils.getFieldConfig();
        if (!TextUtils.isEmpty(str1)&&!"null".equals(str1)){
            fieldConfigEntity=new Gson().fromJson(str1,FieldConfigEntity.class);
        }else {
            fieldConfigEntity=new FieldConfigEntity();
        }
        getDeviceConfig(fieldConfigEntity);
        setConfigTitle("规则信息配置（分钟）");
        String str2= SPUtils.getRuleConfig();
        ruleConfigEntity=new Gson().fromJson(str2,RuleConfigEntity.class);
        getDeviceConfig(ruleConfigEntity);
//        setConfigTitle("计时器设置(秒)");
//        LinearLayout linearLayout=new LinearLayout(this);
//        TextView textView=new TextView(this);
//        textView.setText("最大限制之后的计时器时间");
//        linearLayout.addView(textView);
//        EditText editText=new EditText(this);
//        editText.setText(SPUtils.getTestCountDownTime());
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String t=s.toString();
//                try{
//                    if (TextUtils.isEmpty(s)||"0".equals(t)){
//                        SPUtils.setTestCountDownTime(24*60*60);
//                    }else {
//                        SPUtils.setTestCountDownTime(Integer.parseInt(t));
//                    }
//                }catch (Exception e){
//                    Toast.makeText(ConfigActivity.this,"类型错误",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        linearLayout.addView(editText);
//        ll.addView(linearLayout);
    }

    @Override
    protected void onDestroy() {
        Gson gson=new Gson();
        SPUtils.setFieldConfig(gson.toJson(fieldConfigEntity));
        SPUtils.setRuleConfig(gson.toJson(ruleConfigEntity));
        sdkDeviceManagerTest.setContext(null);
        SPUtils.setTestDeviceInfo(gson.toJson(sdkDeviceManagerTest));
        Toast.makeText(this,"数据保存成功",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    private void setConfigTitle(String title){
        TextView textView=new TextView(this);
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(26);
        ll.addView(textView);
    }


    private void getDeviceConfig(Object data){
        try{
            //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
            Field[] fields = data.getClass().getDeclaredFields();
            for (Field field : fields) {
                try{
                    //设置允许通过反射访问私有变量
                    field.setAccessible(true);
                    //获取字段的值
                    String name=field.getName();
                    if ("instance".equals(name)||"context".equals(name)||"this$0".equals(name)||"toString".equals(name)){
                        continue;
                    }
                    String value = field.get(data).toString();
                    LinearLayout linearLayout=new LinearLayout(this);
                    linearLayout.setPadding(60,0,60,0);
                    TextView nameTv=new TextView(this);
                    nameTv.setText(name+"： ");
                    EditText editText=new EditText(this);
                    editText.setText(value);
                    editText.addTextChangedListener(new MyEditText(name,data));
                    linearLayout.addView(nameTv);
                    linearLayout.addView(editText);
                    ll.addView(linearLayout);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private class MyEditText implements TextWatcher{

        String name;
        Object o;

        public MyEditText(String name,Object o){
            this.name=name;
            this.o=o;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try{
                char[] chars = name.toCharArray();
                chars[0] = (char)(chars[0] - 32);
                String invokeMethodName = "set" + new String(chars);
                Method method=o.getClass().getMethod(invokeMethodName,String.class);
                method.invoke(o,s.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}
