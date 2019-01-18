package co.alexdev.weatherizer.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import co.alexdev.weatherizer.BuildConfig;
import co.alexdev.weatherizer.R;
import co.alexdev.weatherizer.databinding.FragmentHomeBinding;
import co.alexdev.weatherizer.utils.Constants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements OnMapReadyCallback {

    private FragmentHomeBinding mBinding;
    private static final String MAPS_KEY = BuildConfig.GOOGLE_MAPS_API_KEY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initView(savedInstanceState, container);
    }

    private View initView(Bundle savedInstanceState, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_home, container, false);
        final View rootView = mBinding.getRoot();

        initMapView(savedInstanceState);
        return rootView;
    }

    private void initMapView(Bundle savedInstanceState) {
        Bundle mapViewBundle = null;

        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Constants.GOOGLE_MAP_VIEW.BUNDLE_KEY);
        }
        mBinding.mapView.onCreate(mapViewBundle);
        mBinding.mapView.getMapAsync(this);
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
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0))).setTitle(getString(R.string.user_location));
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
