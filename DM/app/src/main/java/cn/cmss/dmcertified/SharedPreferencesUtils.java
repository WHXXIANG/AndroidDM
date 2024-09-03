package cn.cmss.dmcertified;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesUtils {


    private SharedPreferencesUtils() {
    }

    /**
     * 获取SharedPreferences
     *
     * @param name
     * @param mode
     * @return
     */
    public static SharedPreferences getSharedPreferences(Context context , String name, int mode) {
        return context.getSharedPreferences(name, mode);
    }

    public static void clearSharedPreferences(Context context , String name, int mode) {
        getSharedPreferences(context , name, mode).edit().clear().apply();
    }

    public static void clearSharedPreferences(Context context , String name, int mode, String key) {

        getSharedPreferences(context , name, mode).edit().remove(key).apply();
    }

    public static void setValueInPrivateMode(Context context , String key, String value,
                                             String sharedPreferencesName) {
        getSharedPreferences(context , sharedPreferencesName, Context.MODE_PRIVATE)
                .edit().putString(key, value).apply();
    }

    public static void setValueInPrivateMode(Context context , String key, int value,
                                             String sharedPreferencesName) {
        getSharedPreferences(context , sharedPreferencesName, Context.MODE_PRIVATE)
                .edit().putInt(key, value).apply();
    }

    public static void setValueInPrivateMode(Context context , String key, long value,
                                             String sharedPreferencesName) {
        getSharedPreferences(context , sharedPreferencesName, Context.MODE_PRIVATE)
                .edit().putLong(key, value).apply();
    }

    public static void setValueInPrivateMode(Context context , String key, float value,
                                             String sharedPreferencesName) {
        getSharedPreferences(context , sharedPreferencesName, Context.MODE_PRIVATE)
                .edit().putFloat(key, value).apply();
    }

    public static void setValueInPrivateMode(Context context , String key, boolean value,
                                             String sharedPreferencesName) {
        getSharedPreferences(context , sharedPreferencesName, Context.MODE_PRIVATE)
                .edit().putBoolean(key, value).apply();
    }

    public static String getValueInPrivateMode(Context context , String sharedPreferencesName,
                                               String key, String defaultValue) {
        return getSharedPreferences(context , sharedPreferencesName, Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    public static int getValueInPrivateMode(Context context , String sharedPreferencesName,
                                            String key, int defaultValue) {
        return getSharedPreferences(context , sharedPreferencesName, Context.MODE_PRIVATE)
                .getInt(key, defaultValue);
    }

    public static long getValueInPrivateMode(Context context , String sharedPreferencesName,
                                             String key, long defaultValue) {
        return getSharedPreferences(context , sharedPreferencesName, Context.MODE_PRIVATE)
                .getLong(key, defaultValue);
    }

    public static float getValueInPrivateMode(Context context , String sharedPreferencesName,
                                              String key, float defaultValue) {
        return getSharedPreferences(context , sharedPreferencesName, Context.MODE_PRIVATE)
                .getFloat(key, defaultValue);
    }

    public static boolean getValueInPrivateMode(Context context , String sharedPreferencesName,
                                                String key, boolean defaultValue) {
        return getSharedPreferences(context , sharedPreferencesName, Context.MODE_PRIVATE)
                .getBoolean(key, defaultValue);
    }

    public static void removeValueInPrivateMode(Context context, String sharedPreferencesName, String key) {
        getSharedPreferences(context , sharedPreferencesName, Context.MODE_PRIVATE).edit().remove(key);
    }

}
