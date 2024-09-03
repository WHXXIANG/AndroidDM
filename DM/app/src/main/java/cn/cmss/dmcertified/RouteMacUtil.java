package cn.cmss.dmcertified;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//初始化天气数据
public class RouteMacUtil {
    private  final  String  TAG = "DMApplication";


    /**
     * 获取路由器MAC地址
     *
     * @return
     */
    public String getGatewayMac() {
        String str = "";
        try {
            str = getMacFromFile(getGateWay());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * @param str 根据ip获取到对应mac地址信息
     * @return
     */
    public String getMacFromFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        List a = readFile("/proc/net/arp");
        if (a != null && a.size() > 1) {
            for (int i = 1; i < a.size(); i++) {
                List arrayList = new ArrayList();
                String[] split = ((String) a.get(i)).split(" ");
                int i2 = 0;
                while (i2 < split.length) {
                    if (split[i2] != null && split[i2].length() > 0) {
                        arrayList.add(split[i2]);
                    }
                    i2++;
                }
                if (arrayList.size() > 4 && ((String) arrayList.get(0)).equalsIgnoreCase(str)) {
                    return ((String) arrayList.get(3)).toUpperCase();
                }
            }
        }
        return "";
    }

    private String getGateWay() {
        String[] arr;
        String[] arr1;
        String[] arr2;
        try {
            Process process = Runtime.getRuntime().exec("ip route list table 0");
            String data = null;
            BufferedReader ie = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String string = in.readLine();
            Log.d(TAG, "string route list: "+string);
            arr = string.split("\\s+");
            Log.d(TAG, " string route list: "+arr[2]);
            if(isIPv4Address(arr[2])){
                Log.d(TAG, " string route list isIPv4Address");
                return arr[2];
            }else {
                String string1 = in.readLine();
                Log.d(TAG, " string route string1: "+string1);
                arr1 = string1.split("\\s+");
                if(isIPv4Address(arr1[2])) {
                    return arr1[2];
                }else{
                    String string2 = in.readLine();
                    Log.d(TAG, " string route string2: "+string2);
                    arr2 = string2.split("\\s+");
                    return arr2[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static List<String> readFile(String str) {
        IOException e;
        Throwable th;
        File file = new File(str);
        List<String> arrayList = new ArrayList();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    arrayList.add(readLine);
                } catch (IOException e2) {
                    e = e2;
                }
            }
            bufferedReader.close();
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException e3) {
            e = e3;
            bufferedReader = null;
            try {
                e.printStackTrace();
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e4) {
                    }
                }
                return arrayList;
            } catch (Throwable th2) {
                th = th2;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e5) {
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return arrayList;
    }

    public static boolean isIPv4Address(String ip) {
        String regex = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public String getWlan0GateWay() {
        String[] arr;
        String  wlanMac="";
        boolean foundWlan0 = false;
        try {
            Process process = Runtime.getRuntime().exec("ip addr");
            String data = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String stringLine = "";
            // 逐行读取输出
            while ((stringLine = in.readLine()) != null) {
                Log.d(TAG, " string ip addr stringLine: "+stringLine);
                if (stringLine.contains("wlan0")) {
                    foundWlan0 = true; // 找到 wlan0
                }else if (foundWlan0) {
                    // wlan0 的下一行
                    arr = stringLine.trim().split("\\s+");
                    wlanMac = arr[1].toUpperCase();
                    Log.i(TAG, "string ip addr stringLine: "+wlanMac);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "return wlanMac: "+wlanMac);
        return wlanMac;
    }
}