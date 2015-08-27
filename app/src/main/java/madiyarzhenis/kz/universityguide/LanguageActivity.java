package madiyarzhenis.kz.universityguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

import madiyarzhenis.kz.universityguide.city_list.CityActivity;


public class LanguageActivity extends Activity {
    ListView listView;
    LanguageAdapter adapter;
    String languages[] = {"Kazakh", "Russian", "English"};
    String[] lg = {"kk_KZ","ru_RU", "en_GB"};

    Locale myLocale;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editorLanguage;

    public static final String MyPrefs = "MyPrefs";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_language);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sp = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

        if (!sp.getBoolean("first", false)) {//if true
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first", true);
            editor.apply();
            Log.i("Language", "no choosen");
        }else {
            Log.i("Language", "Choosen");
            startActivity(new Intent(getApplicationContext(), CityActivity.class));
        }

        listView = (ListView) findViewById(R.id.listViewLanguage);
        adapter = new LanguageAdapter(this, languages);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(LanguageActivity.this, CityActivity.class);
                setLocale(lg[position]);
                editorLanguage = sharedPreferences.edit();
                editorLanguage.putString("last_locale", lg[position]);
                editorLanguage.apply();
                startActivity(intent);
            }
        });
    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_language, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class LanguageAdapter extends BaseAdapter {
        String[] languages;
        Activity activity;
        LayoutInflater inflater;
        public LanguageAdapter(Activity activity, String[] languages) {
            this.activity = activity;
            this.languages = languages;
            inflater = LayoutInflater.from(activity);
        }

        @Override
        public int getCount() {
            return languages.length;
        }

        @Override
        public Object getItem(int i) {
            return languages[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder view;
            if (convertView == null) {
                view = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_language, null);
                view.name = (TextView) convertView.findViewById(R.id.textViewLang);
                convertView.setTag(view);
            } else {
                view = (ViewHolder) convertView.getTag();
            }
            view.name.setText(languages[position]);
            return convertView;
        }

        public class ViewHolder {
            public TextView name;
        }
    }
}
