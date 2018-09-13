package com.example.calorycountapp.View;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.example.calorycountapp.Database.DB;
import com.example.calorycountapp.Database.MediumCaloriesPreferences;
import com.example.calorycountapp.Database.NumberCaloryPreferences;
import com.example.calorycountapp.R;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        clearPreviousInput();
        Preference pref = findPreference("clear_history");
        pref.setOnPreferenceClickListener(this);

    }


    public void clearPreviousInput(){
        ListPreference genderPref = (ListPreference) findPreference("screen_gender");
        EditTextPreference agePref = (EditTextPreference)findPreference("enterAge");
        EditTextPreference weightPref = (EditTextPreference)findPreference("enterWeight");
        EditTextPreference heightPref = (EditTextPreference)findPreference("enterHeight");
        ListPreference purposePref = (ListPreference) findPreference("list");
        ListPreference languagePref = (ListPreference) findPreference("languages_list");
        EditTextPreference enterCaloryPref = (EditTextPreference) findPreference("enterCaloryLimit");
        genderPref.setValue("");
        agePref.setText("");
        weightPref.setText("");
        heightPref.setText("");
        purposePref.setValue("");
        languagePref.setValue("");
        enterCaloryPref.setText("");
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        NumberCaloryPreferences.setLimitCalory(getApplication(), 0);
        DeleteHistoryTask task = new DeleteHistoryTask();
        task.execute();
        NumberCaloryPreferences.setLimitCalory(this,0);
        MediumCaloriesPreferences.setHistorySize(this,0);
        NumberCaloryPreferences.setConstantCalory(this,0);

        return true;
    }

    class DeleteHistoryTask extends AsyncTask<Void, Void, Void> {

        DB model = new DB(getApplication());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            model.open();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(model.getAllDataFromHistoryDatabase()!=null) {
                model.deleteAllFromHistoryDatabase();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            model.close();
        }
    }
}
