package madiyarzhenis.kz.universityguide.setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Locale;

import madiyarzhenis.kz.universityguide.R;
import madiyarzhenis.kz.universityguide.city_list.CityActivity;

/**
 * Created by madiyar on 11/20/14.
 */
public class Language extends ActionBarActivity {
    ListView listVieww;
    String lang2[] = {"English", "Қазақша", "Русский"};
    String[] lg = {"en_GB", "kk_KZ", "ru_RU"};
    int images[] = {R.drawable.england, R.drawable.kazakh, R.drawable.russia};
    AdapterLanguage adapter;
    Locale myLocale;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        getSupportActionBar().setTitle(R.string.language);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        listVieww = (ListView) findViewById(R.id.list_view_lang);
        adapter = new AdapterLanguage(this, lang2, images);
        listVieww.setAdapter(adapter);

        listVieww.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setLocale(lg[position]);
                editor = sharedPreferences.edit();
                editor.putString("last_locale", lg[position]);
                editor.apply();
                restartActivity();
            }
        });
    }

    private void restartActivity() {
        Intent mStartActivity = new Intent(Language.this, CityActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(Language.this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)Language.this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }
    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
