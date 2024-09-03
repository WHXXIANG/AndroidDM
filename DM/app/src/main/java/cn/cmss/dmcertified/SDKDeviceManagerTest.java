package cn.cmss.dmcertified;

import android.app.usage.StorageStatsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.dmyk.android.mt.IDeviceForMt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Map;

/**
 * 写死的测试值
 */
public class SDKDeviceManagerTest implements IDeviceForMt {

    Context context;
    private static final String TAG = "DMApplication";
    /**
     * 卡1 终端唯一标识
     */
    String imei1 = "119005643070069";
    /**
     * 卡2 终端唯一标识
     */
    String imei2 = "***";
    /**
     * 卡1 imsi
     */
    String imsi = "***";
    /**
     * 卡2 imsi
     */
    String imsi2 = "***";
    /**
     * 厂商标识
     */
    String appKey = "M100000243";
    /**
     * mac地址
     */
    String mac = "01:02:03:04:05:06";
    /**
     * rom信息
     */
    String rom = "8GB";
    /**
     * ram信息
     */
    String ram = "1GB";
    /**
     * cpu信息
     */
    String cpu = "MT6896Z";
    /**
     * 操作系统版本号
     */
    String sysVersion = Build.VERSION.SDK_INT + "";
    /**
     * volte开关状态 0:开，1:关，-1:未知
     */
    String volte = "-1";
    /**
     * 当前网络类型  WI-FI（4G、5G等）
     */
    String netType = "4G";
    /**
     * 手机号
     */
    String phoneNumber = "***";
    /**
     * 路由Mac地址
     */
    String routerMac = "D0:DA:D7:EC:1B:C8";
    /**
     * 设备唯一标识
     */
    String sn = "119005643070069";
    /**
     * 品牌
     */
    String brand = "xiaomi";
    /**
     * 型号
     */
    String model = "P30";
    /**
     * GPU信息
     */
    String gpu = "***";
    /**
     * 主板信息
     */
    String board = "***";//Build.BOARD;
    /**
     * 屏幕分辨率
     */
    String resolution = "1920*1080";
    /**
     * 模板id
     */
    String templateId = "TY000022";
    /**
     * 蓝牙Mac地址
     */
    String bluetoothMac = "D0:DA:D7:F1:2E:28";
    /**
     * 固件版本号
     */
    String softwareVer = "1";
    /**
     * softwareName
     */
    String softwareName = "V2238B";
    /**
     * 电池最大容量
     */
    String batteryCapacity = "800mah";

    /**
     * 当前电池容量
     */
    String batteryCapacityCurr = "400mah";

    /**
     * 屏幕尺寸
     */
    String screenSize = "1.5英寸";
    /**
     * 网络状态 1:连接，0:未连接
     */
    String networkStatus = "***";
    /**
     * 佩戴状态 1:佩戴，0:未佩戴
     */
    String wearingStatus = "-1";
    /**
     * App信息
     */
    String appInfo = "unknown|unknown|unknown|unknown\r\n";

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public String getImei1() {
        String imei1 = "1190056430700600";
        try{
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get",String.class,String.class);
            imei1 = (String)(get.invoke(c,"ro.cmei","unknown"));
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            Log.i(TAG, " value  imei1: "+imei1);
            return imei1;
        }
    }

    @Override
    public String getImei2() {
        Log.i(TAG, " getImei2  imei2: "+imei2);
        return imei2;
    }

    @Override
    public String getImsi() {
        Log.i(TAG, " getImsi  imsi: "+imsi);
        return imsi;
    }

    @Override
    public String getImsi2() {
        Log.i(TAG, " getImsi2  imei2: "+imsi2);
        return imsi2;
    }

    public void setImsi2(String imsi2) {
        this.imsi2 = imsi2;
    }

    @Override
    public String getAppKey() {
        return appKey;
    }

    @Override
    public String getMac() {
        mac = "02:00:00:00:00:02";
        StringBuilder buf = new StringBuilder();
        NetworkInterface networkInterface = null;
        try{
            if("WIFI" == getNetType1()){
                networkInterface = NetworkInterface.getByName("wlan0");
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //return wifiInfo.getMacAddress()
                Log.i(TAG, "getByName is getMacAddress: "+wifiInfo.getMacAddress());
            }else if("Ethernet"== getNetType1()){
                networkInterface = NetworkInterface.getByName("eth0");
            }else{
                Log.i(TAG, "networkInterface is getNetType1 Mobile");
                RouteMacUtil mRouteMacUtil = new RouteMacUtil();
                mac = mRouteMacUtil.getWlan0GateWay();
                return mac;
            }

            if(networkInterface == null){
                Log.i(TAG, "networkInterface is null: ");
                return mac;
            }

            byte[] addr = networkInterface.getHardwareAddress();
            if(addr == null){
                Log.i(TAG, "addr is null: ");
                return mac;
            }
            for (byte b: addr){
                buf.append(String.format("%02X:",b));
            }
            if(buf.length() >0){
                buf.deleteCharAt(buf.length() -1);
                mac = buf.toString();
            }
        }catch (SocketException e){
            e.printStackTrace();
            return mac;
        }
        Log.i(TAG, "macAddress: "+mac);
        return mac;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String getRom() {
        StorageStatsManager storageStatsManager = (StorageStatsManager)context.getSystemService(Context.STORAGE_STATS_SERVICE);
        long size=0;
        try {
            size = storageStatsManager.getTotalBytes(StorageManager.UUID_DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }
        Log.i(TAG, "getRom is size: "+size);
        long GB = 1024*1024*1024;
        final long[] deviceRomMap={2*GB, 4*GB,8*GB,16*GB,32*GB,64*GB,128*GB,256*GB,512*GB,1024*GB,2048*GB};
        String[] displaySize = {"2GB","4GB","8GB","16GB","32GB","64GB","128GB","256GB","512GB","1024GB","2048GB"};
        int i;
        for(i=0;i<deviceRomMap.length;i++){
            if(size <=deviceRomMap[i]){
                break;
            }
            if(i==deviceRomMap.length){
                i--;
            }
        }
        //rom =displaySize[i];
        rom="***";
        return rom;
    }

    @Override
    public String getRam() {
        String path = "/proc/meminfo";
        String ramMemorySize = null;
        int totalRom=0;
        try{
            FileReader fileReader = new FileReader(path);
            BufferedReader br =new BufferedReader(fileReader,4096);
            ramMemorySize = br.readLine().split("\\s+")[1];
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(ramMemorySize != null){
            totalRom = (int) Math.ceil((new Float(Float.valueOf(ramMemorySize)/(1024*1024)).doubleValue()));
        }
        //ram = totalRom+"GB";
        ram="***";
        Log.i(TAG, "getRam is size: "+ram);
        return ram;
    }

    @Override
    public String getCpu() {
        String cpuString = "/proc/cpuinfo";
        String stringTemp="";
        String cpuName="";
        String hardwareString = "Hardware";
        try{
            FileReader fileReader = new FileReader(cpuString);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((stringTemp = bufferedReader.readLine()) !=null){
                if(TextUtils.isEmpty(stringTemp)){
                    continue;
                }
                String[] arrayOfString = stringTemp.split(":\\s+",2);
                if(hardwareString.equals(arrayOfString[0].trim())){
                    cpuName = arrayOfString[1];
                }
            }
            bufferedReader.close();
            fileReader.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        //cpu = cpuName;
        //1030
        //cpu = "ARM Amlogic S905L3A";

        //清水河
        cpu = "***";
        Log.i(TAG, "getCpu is cpu: "+cpu);
        return cpu;
    }

    @Override
    public String getSysVersion() {
        //sysVersion = "Android "+android.os.Build.VERSION.RELEASE+".0";
        sysVersion="***";
        return sysVersion;
    }

    @Override
    public String getSoftwareVer() {
        String value ="unKnown";
        try{
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get",String.class,String.class);
            value = (String)(get.invoke(c,"ro.build.display.id","unknown"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            softwareVer = value;
            Log.i(TAG, "getSoftwareName: "+softwareVer);
            return softwareVer;
        }
    }

    @Override
    public String getSoftwareName() {
        softwareName ="***";
        return softwareName;
        /*try{
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get",String.class,String.class);
            value = (String)(get.invoke(c,"ro.h3c.cmcc_device_software_name","***"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            softwareName = value;
            Log.i(TAG, "getSoftwareName: "+softwareName);
            return softwareName;
        }*/
    }

    @Override
    public String getVolte() {
        return volte;
    }

    @Override
    public String getNetType() {
        netType = "***";
        return netType;
    }

    public String getNetType1() {
        int networkflag = IntenetUtil.getNetworkState(context);
        if(networkflag == 1){
            netType = "WIFI";
        }else if(networkflag ==7){
            netType = "Ethernet";
        }else if(networkflag ==2){
            netType = "2G";
        }else if(networkflag ==3){
            netType = "3G";
        }else if(networkflag ==4){
            netType = "4G";
        }else if(networkflag ==5){
            netType = "5G";
        }else if(networkflag ==6){
            netType = "Mobile";
        }
        Log.i(TAG, "getNetType: "+netType);
        return netType;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getBluetoothMac() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null || !bluetoothAdapter.isEnabled())
        {
            bluetoothMac="***";
        }
        else{
            bluetoothMac =bluetoothAdapter.getAddress();
        }
        Log.i(TAG, "getBluetoothMac: "+bluetoothMac);
        return bluetoothMac;
    }

    @Override
    public String getRouterMac() {
//        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        if(wifiManager.isWifiEnabled()){
//            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//            String netMac = wifiInfo.getBSSID();
//            if(netMac != null){
//                routerMac = netMac;
//                Log.i(TAG, "getRouterMac: "+routerMac);
//                return routerMac;
//            }else{
//                //RouteMacUtil
//            }
//        }
        RouteMacUtil mRouteMacUtil = new RouteMacUtil();
        routerMac = mRouteMacUtil.getGatewayMac();
        if(routerMac.equals("")) {
            return "***";
        }else
            return routerMac;
    }

    @Override
    public String getSn() {
        String value = "";
        try{
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get",String.class,String.class);
            value = (String)(get.invoke(c,"ro.sn","unknown"));
            if(value.equals("unknown")){
                value = (String)(get.invoke(c,"ro.serialno","unknown"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(value.isEmpty()){
                sn = getImei1();
                Log.i(TAG, " value null getSn: "+sn);
                return sn;
            }
            sn =value;
            Log.i(TAG, " value  getSn: "+sn);
            return sn;
        }


    }

    @Override
    public String getBrand() {
        brand = android.os.Build.BRAND;
        return brand;
    }

    @Override
    public String getModel() {
        model = android.os.Build.MODEL;
        return model;
    }

    @Override
    public String getGpu() {
        return gpu;
    }

    @Override
    public String getBoard() {
        return board;
    }

    @Override
    public String getResolution() {
        if(getScreenSize() == "***"){
            resolution= "***";
            return resolution;
        }else {
            WindowManager windowManager = (WindowManager) (context
                    .getSystemService(Context.WINDOW_SERVICE));
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(metrics);

            //DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int screenWidth = metrics.widthPixels;
            int screenHeight = metrics.heightPixels;
            resolution = "" + screenWidth + "*" + screenHeight;
            return resolution;
        }
    }


    @Override
    public String getTemplateId() {
        return templateId;
    }

    @Override
    public String getBatteryCapacity() {
        batteryCapacity = "***";
        return batteryCapacity;
        /*double matteryCapacity = BatteryUtils.getBatteryCapacity(context);
        if(matteryCapacity == -1){
            batteryCapacity = "***";
            return batteryCapacity;
        }else {
            batteryCapacity = String.valueOf(matteryCapacity) + "mah";
            return batteryCapacity;
        }*/
    }

    @Override
    public String getBatteryCapacityCurr() {
//        if(getBatteryCapacity() == "***"){
//            batteryCapacityCurr= "***";
//            return batteryCapacityCurr;
//        }else {
//            double matteryCapacity = BatteryUtils.getCurrentBatteryCapacity(context);
//            batteryCapacityCurr = String.valueOf(matteryCapacity) + "mah";
//            return batteryCapacityCurr;
//        }
        batteryCapacityCurr= "***";
        return batteryCapacityCurr;
    }

    public void setBatteryCapacity(String batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public String getScreenSize() {
        screenSize= "***";
        try{
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get",String.class,String.class);
            screenSize = (String)(get.invoke(c,"ro.build.screensize","unknown"));
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(screenSize.equals("-1")){
                return screenSize= "***";
            }
            screenSize= screenSize+"英寸";
            return screenSize;
        }
//            //ro.build.screensize
//            //DisplayMetrics dm = context.getResources().getDisplayMetrics();
//            WindowManager windowManager = (WindowManager) (context
//                    .getSystemService(Context.WINDOW_SERVICE));
//            DisplayMetrics metrics = new DisplayMetrics();
//            windowManager.getDefaultDisplay().getRealMetrics(metrics);
//
//
//            int width = metrics.widthPixels;
//            int height = metrics.heightPixels;
//            double wi = (double) width / (double) metrics.xdpi;
//            double hi = (double) height / (double) metrics.ydpi;
//            double x = Math.pow(wi, 2);
//            double y = Math.pow(hi, 2);
//            double screenInches = Math.sqrt(x + y);
//
//            if (screenInches < 14.00 && screenInches > 13.50){
//                //screenSize = String.format("%.2f", screenInches)+"英寸";
//                screenSize = "14英寸";
//            }else{
//                screenSize = String.format("%.2f", screenInches)+"英寸";
//            }
//            return screenSize;


    }

    @Override
    public String getNetworkStatus() {
        return networkStatus;
    }

    @Override
    public String getWearingStatus() {
        return wearingStatus;
    }

    //获取两次时间间隔内的  应用名称|应用包名|使用时长|启动次数  多个应用\r\n进行分割
    //默认值为 unknown|unknown|unknown|unknown\r\n
    //以下为示例
    @Override
    public String getAppInfo() {
        Map<String, UsageStats> map = getUsageStatsList(context);
        if (map != null && !map.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            PackageManager packageManager = context.getPackageManager();
            boolean isException = false;
            for (UsageStats u : map.values()) {
                //包名
                String packageName = u.getPackageName();
                //应用名称
                String appName = "unknow";
                if (u != null) {
                    ApplicationInfo appi = null;
                    try {
                        appi = packageManager.getApplicationInfo(packageName, 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        //MTLog.e(TAG , ");;
                        e.printStackTrace();
                    }
                    if (appi != null) {
                        String realAppName = appi.loadLabel(packageManager).toString();
                        appName = TextUtils.isEmpty(realAppName) ? packageName : realAppName;
                    }
                }
                //使用时长

                //使用次数
                int launchCount = 0;
                try {
                    Field field = u.getClass().getDeclaredField("mLaunchCount");
                    launchCount = field.getInt(u);
                } catch (Exception e) {
                    if (!isException) {
                        isException = true;
                        Log.d(getClass().getSimpleName(), "反射获取次数失败");
                    }
                }
                stringBuffer.append(appName).append("|").append(packageName).append("|").append(u.getTotalTimeInForeground()).append("|").append(launchCount).append("\r\n");
                //限制长度
                if (stringBuffer.toString().length() > 4000) {
                    break;
                }
            }
            Log.d(getClass().getSimpleName(), "获取到数据->" + stringBuffer.toString());
            appInfo = stringBuffer.toString();
        }
        return appInfo;
    }

    public Map<String, UsageStats> getUsageStatsList(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        long startTime = TestSPUtils.getLastAppInfoReportTime(context);
        long endTime = System.currentTimeMillis();
//        final List<UsageStats> queryUsageStats=usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
        Map<String, UsageStats> stringUsageStatsMap = usm.queryAndAggregateUsageStats(startTime, endTime);
        Log.d(getClass().getSimpleName(), "获取到的应用数据：" + stringUsageStatsMap.size());
        TestSPUtils.setLastAppInfoReportTime(context, endTime);
        return stringUsageStatsMap;
    }

    public void setAppInfo(String appInfo) {
        this.appInfo = appInfo;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public void setSoftwareVer(String softwareVer) {
        this.softwareVer = softwareVer;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public void setNetworkStatus(String networkStatus) {
        this.networkStatus = networkStatus;
    }

    public void setWearingStatus(String wearingStatus) {
        this.wearingStatus = wearingStatus;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }


    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public void setVolte(String volte) {
        this.volte = volte;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRouterMac(String routerMac) {
        this.routerMac = routerMac;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }


    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }


    @Override
    public String toString() {
        return "SDKDeviceManagerTest{" +
                "context=" + context +
                ", imei1='" + imei1 + '\'' +
                ", imei2='" + imei2 + '\'' +
                ", imsi='" + imsi + '\'' +
                ", imsi2='" + imsi2 + '\'' +
                ", appKey='" + appKey + '\'' +
                ", mac='" + mac + '\'' +
                ", rom='" + rom + '\'' +
                ", ram='" + ram + '\'' +
                ", cpu='" + cpu + '\'' +
                ", sysVersion='" + sysVersion + '\'' +
                ", volte='" + volte + '\'' +
                ", netType='" + netType + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", routerMac='" + routerMac + '\'' +
                ", sn='" + sn + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", gpu='" + gpu + '\'' +
                ", board='" + board + '\'' +
                ", resolution='" + resolution + '\'' +
                ", templateId='" + templateId + '\'' +
                ", bluetoothMac='" + bluetoothMac + '\'' +
                ", softwareVer='" + softwareVer + '\'' +
                ", softwareName='" + softwareName + '\'' +
                ", batteryCapacity='" + batteryCapacity + '\'' +
                ", screenSize='" + screenSize + '\'' +
                ", networkStatus='" + networkStatus + '\'' +
                ", wearingStatus='" + wearingStatus + '\'' +
                ", appInfo='" + appInfo + '\'' +
                '}';
    }

}
