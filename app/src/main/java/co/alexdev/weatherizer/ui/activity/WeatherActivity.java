package co.alexdev.weatherizer.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.weatherizer.R;
import co.alexdev.weatherizer.di.component.DaggerWeatherizerAppComponent;
import co.alexdev.weatherizer.di.component.WeatherizerAppComponent;
import co.alexdev.weatherizer.di.module.ContextModule;
import co.alexdev.weatherizer.model.weather.City;
import co.alexdev.weatherizer.repo.AppRepository;
import co.alexdev.weatherizer.ui.fragment.HistoryFragmentListener;
import co.alexdev.weatherizer.ui.fragment.HomeFragment;
import co.alexdev.weatherizer.ui.fragment.SettingsFragment;
import co.alexdev.weatherizer.utils.Constants;
import co.alexdev.weatherizer.utils.Listener;
import co.alexdev.weatherizer.viewmodel.SharedViewModel;
import co.alexdev.weatherizer.viewmodel.factory.ViewModelFactory;
import timber.log.Timber;

import static co.alexdev.weatherizer.utils.Constants.LOCATION_KEYS.REQUEST_LOCATION_DIALOG;
import static co.alexdev.weatherizer.utils.Constants.PERMISSION_KEYS.PERMISSION_ACCES_FINE_LOCATION;

public class WeatherActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        Listener.OnStartSearchListener,
        Listener.OnLocationRequestFinishedListener {

    @Inject
    AppRepository mAppRepository;

    private SharedViewModel vm;
    private GoogleApiClient mGoogleClient;
    private SearchView mSearchView;
    private Bundle args;
    private DrawerLayout mDrawerLayout;
    private LocationSettingsRequest.Builder builder;
    NavigationView mNavView;

    private WeatherizerAppComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setToolbar();
        setNavigationView();

        initView();
        requestLocationPermission();

        boolean allowNotification = mComponent.getPrefUtils().isAllowNotificationON();
        if (allowNotification) {
            mComponent.getReminderUtils().scheduleTheJob();
        }
    }

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
        args = new Bundle();
        mComponent = DaggerWeatherizerAppComponent.builder()
                .contextModule(new ContextModule(this)).build();
        mComponent.inject(this);
        ViewModelFactory viewModelFactory = new ViewModelFactory(mAppRepository);
        vm = ViewModelProviders.of(this, viewModelFactory).get(SharedViewModel.class);
        mGoogleClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
    }

    private void setNavigationView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mNavView = findViewById(R.id.nav_view);
        mNavView.getMenu().getItem(0).setChecked(true);

        mNavView.setNavigationItemSelectedListener(
                menuItem -> {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    mDrawerLayout.closeDrawers();

                    switch (menuItem.getItemId()) {

                        case R.id.nav_settings:
                            changeFragment(new SettingsFragment());
                            return true;

                        case R.id.nav_history:
                            changeFragment(new HistoryFragmentListener());
                            return true;

                        case R.id.nav_home:
                            changeFragment(new HomeFragment());

                            return true;

                    }
                    return true;
                });
    }

    /*Set the application to use the custom toolbar where on it we will add the navigation drawer
     * We make a custom toolbar because android does not support to add a menu over the original toolbar*/
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    private void setSearchView(Menu menu) {

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        // Assumes current activity is the searchable activity
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconified(false); // Do not iconify the widget; expand it by default
        mSearchView.setQueryHint(getResources().getString(R.string.search_country));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String localityName) {
                /*Start a search based on the text of the query*/
                startSearch(localityName);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!(fragment instanceof HomeFragment)) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }

    private void requestLocationPermission() {
        mComponent.getPermissionUtils().requestLocationPermission();
    }

    /*Perform a search based on the quest of the query*/
    private void startSearch(String localityName) {
        /*Set the top search bar to locality name */
        mSearchView.setQuery(localityName, false);
        mSearchView.clearFocus();

        setLayout(localityName);
    }

    /*If there is no network, get the locality which is on the searchbar SET
     * If is the first time when user opens the app, load last latitude and longitude from the GPS
     * ELSE
     * If there is internet perform the desired requests with internet */
    private void setLayout(String localityName) {
        Timber.d("Network state: " + mComponent.getNetworkManager().isNetworkActive());
        if (!mComponent.getNetworkManager().isNetworkActive()) {
            Timber.d("Setting layout ");
            loadLastLocationDataFromGPS();
            getSingleLocality(localityName);
            /*Change the fragment marker to the desired position */
            checkIfCoordinatesAreSet();
        } else {
            /*Make a search */
            getAllCities(localityName);
            getSingleLocality(localityName);
            getLocalityCoordinates();
            /*Change the fragment marker to the desired position */
            checkIfCoordinatesAreSet();
        }
    }

    private void loadLastLocationDataFromGPS() {
        double lon = mComponent.getPrefUtils().getLonCoordinate();
        double lat = mComponent.getPrefUtils().getLatCoordinate();
        vm.setCoordonates(lat, lon);
    }

    private void checkIfCoordinatesAreSet() {
        HomeFragment homeFragment = new HomeFragment();
        vm.areCoordinatesSet().observe(this, isAvailable -> {
            Timber.d("Is available: " + isAvailable);
            if (isAvailable) {
                args.putDoubleArray(Constants.BUNDLE_KEYS.LAT_LNG_KEY, vm.getCoordonatesArray());
                homeFragment.setArguments(args);
                changeFragment(homeFragment);
            }
        });
    }

    private void getAllCities(String localityName) {
        vm.loadAllCities(localityName).observe(this, cityResponse -> {
            Timber.d("Response: " + cityResponse);
            Timber.d("Status: " + cityResponse.status);
            Timber.d("Message: " + cityResponse.message);
            if (cityResponse.data != null && cityResponse.data.size() > 0) {
                Timber.d("City: " + cityResponse.data.toString());
            }
        });
    }

    private void getSingleLocality(String localityName) {
        LiveData<City> getCityLiveData = vm.getCity(localityName);
        getCityLiveData.observe(this, city -> {
            if (city != null) {
                getCityLiveData.removeObservers(this);
                vm.setCityId(city.getId());
                Timber.d("City from db: " + city.toString());
            }
        });
    }

    /*If the user enters the app for the first time and if there is no network available set the coordonates to latest set*/
    public void getLocalityCoordinates() {
        vm.getCoordinate().observe(this, coordinates -> {
            vm.setCoordonates(coordinates.getLat(), coordinates.getLon());
        });
    }

    /*Open the navigation view when the home button is pressed */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_ACCES_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mGoogleClient != null) {
                        mGoogleClient.connect();
                    }
                    mComponent.getLocationManager().createLocationRequest(this);
                    finish();
                    startActivity(getIntent());
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
        mComponent.getLocationManager().createLocationRequest(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient location = LocationServices.getFusedLocationProviderClient(this);
            Timber.d("OnConnected called ");
            location.getLastLocation().addOnSuccessListener(location1 -> {
                Timber.d("getLastLocation called");
                if (location1 != null) {
                    final double lat = location1.getLatitude();
                    final double lon = location1.getLongitude();
                    Timber.d("LAT: " + lat + " LON" + lon);

                    mComponent.getPrefUtils().setCoordinate(lat, lon);
                    final String localityName = mComponent.getLocationUtils().decodeLocation(this, lat, lon);

                    startSearch(localityName);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.LOCATION_KEYS.REQUEST_LOCATION_DIALOG) {
            Timber.d("Google client has been reconnected");

            if (resultCode == RESULT_OK) {
                mGoogleClient.connect();
                Timber.d("Google client has been reconnected");
                recreate();
            }
            mGoogleClient.connect();
        }
    }

    /*Called when an item is clicked from History*/
    @Override
    public void onStartSearch(String localityName) {
        startSearch(localityName);
        mNavView.getMenu().getItem(0).setChecked(true);
        new Handler().postDelayed(() -> {
            mSearchView.clearFocus();
            mSearchView.setQuery(localityName, false);
        }, 250);
    }

    @Override
    public void onLocationRequestFinished() {
        mGoogleClient.connect();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient location = LocationServices.getFusedLocationProviderClient(this);
            Timber.d("OnConnected called ");
            location.getLastLocation().addOnSuccessListener(location1 -> {
                Timber.d("getLastLocation called");
                if (location1 != null) {
                    final double lat = location1.getLatitude();
                    final double lon = location1.getLongitude();
                    Timber.d("LAT: " + lat + " LON" + lon);

                    mComponent.getPrefUtils().setCoordinate(lat, lon);
                    final String localityName = mComponent.getLocationUtils().decodeLocation(this, lat, lon);

                    startSearch(localityName);
                }
            });
            // mComponent.getLocationManager().createLocationRequest(this);
        }
    }
}