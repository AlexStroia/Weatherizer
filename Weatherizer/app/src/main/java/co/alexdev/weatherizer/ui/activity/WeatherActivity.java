package co.alexdev.weatherizer.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;


import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
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

public class WeatherActivity extends AppCompatActivity {

    @Inject
    AppRepository mAppRepository;

    private ActivityWeatherBinding mBinding;

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
        changeFragment(new HomeFragment());
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
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}
