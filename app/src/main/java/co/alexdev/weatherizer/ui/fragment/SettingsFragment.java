package co.alexdev.weatherizer.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import co.alexdev.weatherizer.R;
import co.alexdev.weatherizer.di.component.DaggerWeatherizerAppComponent;
import co.alexdev.weatherizer.di.component.WeatherizerAppComponent;
import co.alexdev.weatherizer.di.module.ContextModule;
import timber.log.Timber;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences mSharedPreferences;
    private WeatherizerAppComponent mComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*This way we can acces our item in the menu*/
        setHasOptionsMenu(true);
        mComponent = DaggerWeatherizerAppComponent.builder()
                .contextModule(new ContextModule(this.getActivity()))
                .build();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_settings_screen);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerSharedPreferences();
    }

    @Override
    public void onStop() {
        super.onStop();

        unregisterSharedPreferences();
    }

    /*Find the item in the menu and set its visibility to false*/
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchItem.setVisible(false);
    }

    private void registerSharedPreferences() {
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void unregisterSharedPreferences() {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        boolean receiveNotifications = sharedPreferences.getBoolean(getString(R.string.pref_allow_notification), false);
        Timber.d("IsLocationAllowed: " + receiveNotifications);
    }
}
