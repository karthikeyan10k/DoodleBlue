package com.example.doodleblue;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Constants {


    private static Constants mConstants;
   public static final String DEVICE_REGISTRATION_URL = "https:";

  //  public static final String DEVICE_REGISTRATION_URL = "http://rlco.ridsys.in";

    //    public static final String LOGIN_BASE_URL = "http:192.168.70.223:8080";
//public static final String DEVICE_REGISTRATION_URL = " http://192.168.70.150";

    private static final String BASE_URL_KEY = "base_url";

    public static Constants getInstance() {

        if (mConstants == null) {
            mConstants = new Constants();
        }
        return mConstants;
    }

    public String getBASE_URL(Context context) {

        SharedPreferences sP = PreferenceManager.getDefaultSharedPreferences(context);
        String str = sP.getString(BASE_URL_KEY, "");
        return str;
    }

    public void setBASE_URL(String baseUrl, Context context) {

        SharedPreferences sP = PreferenceManager.getDefaultSharedPreferences(context);
        String str = sP.getString(BASE_URL_KEY, "");
        if (!str.equals("") || !str.equals(baseUrl)) {
            SharedPreferences.Editor edit = sP.edit();
            edit.putString(BASE_URL_KEY, baseUrl);
            edit.apply();
        }
    }
}
