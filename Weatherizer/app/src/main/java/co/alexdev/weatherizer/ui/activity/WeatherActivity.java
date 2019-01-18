package co.alexdev.weatherizer.ui.activity;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import co.alexdev.weatherizer.R;
import co.alexdev.weatherizer.component.DaggerWeatherizerAppComponent;
import co.alexdev.weatherizer.component.WeatherizerAppComponent;
import co.alexdev.weatherizer.databinding.ActivityWeatherBinding;
import co.alexdev.weatherizer.module.ContextModule;
import co.alexdev.weatherizer.repo.AppRepository;
import co.alexdev.weatherizer.ui.fragment.HomeFragment;
import timber.log.Timber;

public class WeatherActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Inject
    AppRepository mAppRepository;

    private ActivityWeatherBinding mBinding;
    private GoogleApiClient mGoogleClient;
    private static final int PERMISSION_ACCES_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        mAppRepository.loadDataForCity("London").observe(this, cityResponse -> {
            Timber.d("Response: " + cityResponse);
            Timber.d("Status: " + cityResponse.status);
            Timber.d("Message: " + cityResponse.message);
            if (cityResponse.data != null && cityResponse.data.size() > 0) {
                Timber.d("City: " + cityResponse.data.toString());
            }
        });

        requestLocationPermission();
        changeFragment(new HomeFragment());
    }

    /*Battery management*/
    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleClient != null) {
            mGoogleClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleClient.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnu_app, menu);

        setSearchView(menu);
        return true;
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        mGoogleClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
        WeatherizerAppComponent component = DaggerWeatherizerAppComponent.builder()
                .contextModule(new ContextModule(this)).build();
        component.inject(this);
    }

    private void setSearchView(Menu menu) {

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false); // Do not iconify the widget; expand it by default
        searchView.setQueryHint(getResources().getString(R.string.search_country));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Timber.d(s);
                return true;
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_ACCES_COARSE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_ACCES_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Timber.d("We have permsission");
                } else {
                    Timber.d("We don't have permission.");
                }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Timber.d("onConnected called");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient location = LocationServices.getFusedLocationProviderClient(this);
            //https://stackoverflow.com/questions/29796436/why-is-fusedlocationapi-getlastlocation-null?rq=1
            Timber.d("onConnected permission granted called");

            location.getLastLocation().addOnSuccessListener(location1 -> {
                if (location1 != null) {
                    double lat = location1.getLatitude();
                    double lon = location1.getLongitude();
                    Timber.d("User location lat: " + lat + " lon: " + lon);
                }
            });
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Timber.d("Connection has been suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.d("Connection has failed");
    }
}
