package es.vicentepf.enjoyyourtime;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Vicente_2 on 14/02/2016.
 */
public class Preferences extends PreferenceActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
