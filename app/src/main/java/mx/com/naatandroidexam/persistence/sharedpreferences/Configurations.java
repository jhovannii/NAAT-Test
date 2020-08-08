package mx.com.naatandroidexam.persistence.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import mx.com.naatandroidexam.model.LoginResponse;

public class Configurations {
    public static void rememberUser(Context context, LoginResponse body) {
        SharedPreferences sharedPref = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefPrimeracceso = context.getSharedPreferences("PRIMERA_VEZ", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        SharedPreferences.Editor editorPrimerAcceso = sharedPrefPrimeracceso.edit();

        editor.putString("access_token", body.getAccess_token());
        editor.putString("token_type", body.getToken_type());
        editor.putString("refresh_token", body.getRefresh_token());
        editor.putString("scope", body.getScope());
        editor.putString("jti", body.getJti());
        editor.putLong("expires_in", body.getExpires_in());

        editorPrimerAcceso.putBoolean("primera_vez", false);
        editorPrimerAcceso.apply();
        editor.apply();
    }

    public static void deleteRememberUser(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefPrimeracceso = context.getSharedPreferences("PRIMERA_VEZ", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        SharedPreferences.Editor editorPrimerAcceso = sharedPrefPrimeracceso.edit();

        editor.putString("access_token", "");
        editor.putString("token_type", "");
        editor.putString("refresh_token", "");
        editor.putString("scope", "");
        editor.putString("jti", "");
        editor.putLong("expires_in", 0);

        editorPrimerAcceso.putBoolean("primera_vez", true);
        editorPrimerAcceso.apply();
        editor.apply();
    }

    public static String[] getRememberedUser(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        return new String[]{
                sharedPref.getString("access_token", ""),
                sharedPref.getString("token_type", ""),
                sharedPref.getString("refresh_token", ""),
                sharedPref.getString("scope", ""),
                sharedPref.getString("jti", ""),
                String.valueOf(sharedPref.getLong("expires_in", 0))
        };
    }
}
