package tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class network {
    //判断是否联网
    private static boolean checkConnectNetwork(Context context) {

        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conn.getActiveNetworkInfo();
        if (net != null && net.isConnected()) {
            return true;
        }
        return false;
    }
}
