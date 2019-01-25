package co.alexdev.weatherizer.ui.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import co.alexdev.weatherizer.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_settings_screen, rootKey);
    }
}
