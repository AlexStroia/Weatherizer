package co.alexdev.weatherizer.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.weatherizer.R;
import co.alexdev.weatherizer.adapter.WeatherDetailAdapter;
import co.alexdev.weatherizer.di.component.DaggerWeatherizerAppComponent;
import co.alexdev.weatherizer.di.component.WeatherizerAppComponent;
import co.alexdev.weatherizer.databinding.FragmentHomeBinding;
import co.alexdev.weatherizer.di.module.ContextModule;
import co.alexdev.weatherizer.model.weather.Main;
import co.alexdev.weatherizer.repo.AppRepository;
import co.alexdev.weatherizer.ui.activity.WeatherActivity;
import co.alexdev.weatherizer.utils.Constants;
import co.alexdev.weatherizer.utils.PrefJobUtils;
import co.alexdev.weatherizer.viewmodel.SharedViewModel;
import co.alexdev.weatherizer.viewmodel.factory.ViewModelFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment implements OnMapReadyCallback {

    private FragmentHomeBinding mBinding;
    private SharedViewModel vm;
    private WeatherDetailAdapter mAdapter;
    private double lat;
    private double lon;

    @Inject
    AppRepository repository;

    WeatherizerAppComponent mComponent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initView(savedInstanceState, container);
    }

    private View initView(Bundle savedInstanceState, ViewGroup container) {
        ViewModelFactory factory = new ViewModelFactory(repository);
        mComponent = DaggerWeatherizerAppComponent.builder()
                .contextModule(new ContextModule(this.getActivity())).build();
        mComponent.inject((WeatherActivity) this.getActivity());

        vm = ViewModelProviders.of(this.getActivity(), factory).get(SharedViewModel.class);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_home, container, false);
        mAdapter = new WeatherDetailAdapter(getResources(), mComponent.getStringUtils());

        Bundle args = getArguments();
        String latLngKey = Constants.BUNDLE_KEYS.LAT_LNG_KEY;
        if (args != null && args.containsKey(latLngKey)) {
            double[] coordonatesArray = args.getDoubleArray(Constants.BUNDLE_KEYS.LAT_LNG_KEY);

            lat = coordonatesArray[0];
            lon = coordonatesArray[1];
        }

        final View rootView = mBinding.getRoot();
        initMapView(savedInstanceState);
        setRecycler();
        getWeatherForFiveDays();
        return rootView;
    }

    private void initMapView(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;

        /*Mapview requires a bundle with the MapView Bundle SDK */
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Constants.GOOGLE_MAP_VIEW.BUNDLE_KEY);
        }
        mBinding.mapView.onCreate(mapViewBundle);
        mBinding.mapView.getMapAsync(this);
    }

    /*Mains holds the detail for the weather like temp max, temp min*/
    private void getWeatherForFiveDays() {
        vm.getWeatherForFiveDays().observe(this, mains -> {
            initRecycler(mains);
            mComponent.getPrefUtils().setWeatherIcon(mains.get(0).getIcon_id());
        });
    }

    private void initRecycler(@Nullable List<Main> mains) {
        mAdapter.setMain(mains);
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    private void setRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        mBinding.recyclerView.addItemDecoration(dividerItemDecoration);
        mBinding.recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        /**Important, GOOGLE MAP VIEW REQUIRES A BUNDLE where you pass the GOOGLE MAP SDK*/
        Bundle mapViewBundle = outState.getBundle(Constants.GOOGLE_MAP_VIEW.BUNDLE_KEY);

        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(Constants.GOOGLE_MAP_VIEW.BUNDLE_KEY, mapViewBundle);
        }

        mBinding.mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 10));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon))).setTitle(getString(R.string.user_location));
    }

    @Override
    public void onStart() {
        super.onStart();
        mBinding.mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBinding.mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBinding.mapView.onLowMemory();
    }
}
