package mx.com.naatandroidexam.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static boolean hayConexionInternet(Context contexto) {
        ConnectivityManager conMgr = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        if (conMgr != null) info = conMgr.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.isAvailable();
    }

    public static String getHora() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    public static String getFecha() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}
