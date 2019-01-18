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
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.weatherizer.R;
import co.alexdev.weatherizer.component.DaggerWeatherizerAppComponent;
import co.alexdev.weatherizer.component.WeatherizerAppComponent;
import co.alexdev.weatherizer.databinding.ActivityWeatherBinding;
import co.alexdev.weatherizer.di.ContextModule;
import co.alexdev.weatherizer.repo.AppRepository;
import co.alexdev.weatherizer.ui.fragment.HomeFragment;
import co.alexdev.weatherizer.utils.LocationUtils;
import co.alexdev.weatherizer.viewmodel.BaseViewModel;
import co.alexdev.weatherizer.viewmodel.ViewModelFactory;
import timber.log.Timber;

public class WeatherActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Inject
    AppRepository mAppRepository;

    private ActivityWeatherBinding mBinding;
    private BaseViewModel vm;
    private GoogleApiClient mGoogleClient;
    private SearchView mSearchView;
    private static final int PERMISSION_ACCES_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        requestLocationPermission();
        changeFragment(new HomeFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleClient != null) {
            if (!vm.isGoogleClinetConnected(mGoogleClient)) {
                mGoogleClient.connect();
            }
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
        WeatherizerAppComponent component = DaggerWeatherizerAppComponent.builder()
                .contextModule(new ContextModule(this)).build();
        component.inject(this);
        ViewModelFactory viewModelFactory = new ViewModelFactory(mAppRepository);
        vm = ViewModelProviders.of(this, viewModelFactory).get(BaseViewModel.class);
        mGoogleClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
    }

    private void setSearchView(Menu menu) {

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        // Assumes current activity is the searchable activity
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconified(false); // Do not iconify the widget; expand it by default
        mSearchView.setQueryHint(getResources().getString(R.string.search_country));
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCES_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_ACCES_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!vm.isGoogleClinetConnected(mGoogleClient)) {
                        mGoogleClient.connect();
                    }
                    Timber.d("We have permsission");
                } else {
                    Timber.d("We don't have permission.");
                }
        }
    }

    /*Get the user location by latitude and longitude
     * Use helper class Location Utils to decode the locality by latitude and longitude
     * Make a search based on user location */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Timber.d("onConnected called");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient location = LocationServices.getFusedLocationProviderClient(this);
            Timber.d("onConnected permission granted called");

            location.getLastLocation().addOnSuccessListener(location1 -> {
                if (location1 != null) {
                    final double lat = location1.getLatitude();
                    final double lon = location1.getLongitude();

                    final String localityName = LocationUtils.decodeLocation(this, lat, lon);
                    startSearch(localityName);
                }
            });
        }
    }

    private void startSearch(String localityName) {
        /*Set the top search bar to locality name */
        mSearchView.setQuery(localityName, false);
        /*Make a search */
        vm.loadDataForCity(localityName).observe(this, cityResponse -> {
            Timber.d("Response: " + cityResponse);
            Timber.d("Status: " + cityResponse.status);
            Timber.d("Message: " + cityResponse.message);
            if (cityResponse.data != null && cityResponse.data.size() > 0) {
                Timber.d("City: " + cityResponse.data.toString());
            }
        });
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
