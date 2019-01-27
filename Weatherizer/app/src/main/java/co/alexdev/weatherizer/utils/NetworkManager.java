package co.alexdev.weatherizer.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

import co.alexdev.weatherizer.scope.WeatherizerAppScope;

@WeatherizerAppScope
public class NetworkManager {

    private Context mContext;

    @Inject
    public NetworkManager(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isNetworkActive() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
