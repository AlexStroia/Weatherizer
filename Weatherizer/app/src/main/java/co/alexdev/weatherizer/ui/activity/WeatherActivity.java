package co.alexdev.weatherizer.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import javax.inject.Inject;
import androidx.appcompat.app.AppCompatActivity;
import co.alexdev.weatherizer.R;
import co.alexdev.weatherizer.component.DaggerWeatherizerAppComponent;
import co.alexdev.weatherizer.component.WeatherizerAppComponent;
import co.alexdev.weatherizer.repo.Repository;
import timber.log.Timber;

public class WeatherActivity extends AppCompatActivity {

    @Inject
    Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        WeatherizerAppComponent component = DaggerWeatherizerAppComponent.builder().build();
        component.inject(this);


        mRepository.loadDataForCity("PLM");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnu_app, menu);

        setSearchView(menu);
        return true;
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
                Timber.d(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Timber.d(s);
                return true;
            }
        });
    }
}
