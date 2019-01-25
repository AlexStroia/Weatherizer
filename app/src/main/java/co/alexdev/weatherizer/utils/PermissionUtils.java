package co.alexdev.weatherizer.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import javax.inject.Inject;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;

import static co.alexdev.weatherizer.utils.Constants.PERMISSION_KEYS.PERMISSION_ACCES_FINE_LOCATION;

@WeatherizerAppScope
public class PermissionUtils {

    private Context context;

    @Inject
    public PermissionUtils(Context context) {
        this.context = context;
    }

    public void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCES_FINE_LOCATION);
        }
    }
}
