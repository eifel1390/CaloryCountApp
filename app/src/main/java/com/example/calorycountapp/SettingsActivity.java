package com.example.calorycountapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.calorycountapp.Database.DB;
import com.example.calorycountapp.Database.IntroDataSharedPreference;
import com.example.calorycountapp.Database.MediumCaloriesPreferences;
import com.example.calorycountapp.Database.NumberCaloryPreferences;
import com.example.calorycountapp.View.MainActivity;
import com.example.calorycountapp.View.ResultScreenActivity;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        Preference pref = findPreference("clear_history");
        pref.setOnPreferenceClickListener(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(listener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

            if(key.equals("screen_gender")){
                String gender = sp.getString("screen_gender", "");
                IntroDataSharedPreference.setUserGender(SettingsActivity.this,Integer.parseInt(gender));
                setIntentToResultScreen();
            }
            if(key.equals("enterAge")){
                int age = Integer.parseInt(sp.getString("enterAge", ""));
                IntroDataSharedPreference.setUserAge(SettingsActivity.this,age);
                setIntentToResultScreen();
            }
            if(key.equals("enterWeight")){
                int weight = Integer.parseInt(sp.getString("enterWeight", ""));
                IntroDataSharedPreference.setUserWeight(SettingsActivity.this,weight);
                setIntentToResultScreen();
            }
            if(key.equals("enterHeight")){
                int height = Integer.parseInt(sp.getString("enterHeight", ""));
                IntroDataSharedPreference.setUserHeight(SettingsActivity.this,height);
                setIntentToResultScreen();
            }
            if(key.equals("list")){
                String target = sp.getString("list", "не выбрано");
                IntroDataSharedPreference.setUserTarget(SettingsActivity.this,Integer.parseInt(target));
                setIntentToResultScreen();
            }

            if(key.equals("enterCaloryLimit")){
                int userCalory = Integer.parseInt(sp.getString("enterCaloryLimit", ""));
                NumberCaloryPreferences.setLimitCalory(SettingsActivity.this,userCalory);
            }



        }
    };

    public void setIntentToResultScreen(){
        Intent intent = new Intent(SettingsActivity.this, ResultScreenActivity.class);
        startActivity(intent);
    }


    /*public void clearPreviousInput(){
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
    }*/


    @Override
    public boolean onPreferenceClick(Preference preference) {
        NumberCaloryPreferences.setLimitCalory(getApplication(), 0);
        DeleteHistoryTask task = new DeleteHistoryTask();
        task.execute();
        NumberCaloryPreferences.setLimitCalory(this, 0);
        MediumCaloriesPreferences.setHistorySize(this, 0);
        NumberCaloryPreferences.setConstantCalory(this, 0);

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
