package com.newc.greenchange;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnection {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static void connectionErrorMessage(Context context){
        String connectionErrorMessage="Vui lòng kiểm tra kết nối mạng của bạn!";
        Toast.makeText(context, connectionErrorMessage, Toast.LENGTH_SHORT).show();
    }
}
