package madiyarzhenis.kz.universityguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class LanguageActivity extends ActionBarActivity {
    ListView listView;
    LanguageAdapter adapter;
    String languages[] = {"Kazakh", "Russian", "English"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        listView = (ListView) findViewById(R.id.listViewLanguage);
        adapter = new LanguageAdapter(this, languages);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
                intent.putExtra("lang", languages[i]);
                startActivity(intent);
            }
        });
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
