package co.alexdev.weatherizer.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;

import java.io.IOException;
import java.util.List;


public class LocationUtils {

    private static Geocoder mGeocoder;

    public static String decodeLocation(Context context, double latitude, double longitude) {
        String localityName = "";
        mGeocoder = new Geocoder(context);
        List<Address> addressList;
        try {
            addressList = mGeocoder.getFromLocation(latitude, longitude, 1);
            if (addressList.size() > 0) {
                localityName = addressList.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localityName;
    }
}
