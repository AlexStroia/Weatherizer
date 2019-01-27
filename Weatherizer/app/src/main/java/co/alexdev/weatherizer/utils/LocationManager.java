package co.alexdev.weatherizer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;


import javax.inject.Inject;

import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import timber.log.Timber;

import static co.alexdev.weatherizer.utils.Constants.LOCATION_KEYS.REQUEST_LOCATION_DIALOG;


/**
 * Documentation to read:
 * https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
 */

@WeatherizerAppScope
public class LocationManager extends Activity {

    private LocationSettingsRequest.Builder builder;
    private Context mContext;
    private LocationRequest locationRequest;
    private Listener.OnLocationRequestFinishedListener mListener;

    @Inject
    public LocationManager(Context context) {
        mContext = context;
    }

    public void createLocationRequest(Listener.OnLocationRequestFinishedListener mListener) {
        this.mListener = mListener;
        Timber.d("CreateLocationRequest called");
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);

        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        scheduleTask();
    }

    /*Create the client which checks wether current location settings are satisfied: */
    private Task<LocationSettingsResponse> buildResult() {
        return LocationServices.getSettingsClient(mContext).checkLocationSettings(builder.build());
    }

    /*
    When the Task completes, the client can check the location settings by looking at the status code from the LocationSettingsResponse object.
    The client can also retrieve the current state of the relevant location settings by calling getLocationSettingsStates():
    */
    private void scheduleTask() {
        Timber.d("Scheduling a task called");
        if (buildResult() != null) {
            buildResult().addOnCompleteListener(task -> {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            ResolvableApiException resolvable = (ResolvableApiException) exception;
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            try {
                                resolvable.startResolutionForResult(
                                        (Activity) mContext,
                                        REQUEST_LOCATION_DIALOG);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Timber.d("SETTINGS_CHANGE_UNVAILABLE CALLED");
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });
            mListener.onLocationRequestFinished();
        }
    }
}
