package madiyarzhenis.kz.universityguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import madiyarzhenis.kz.universityguide.list_of_university.UniversityActivity;

/**
 * Created by Zhenis Madiyar on 7/26/2015.
 */
public class CityActivity extends ActionBarActivity{
    ListView listView;
    CityAdapter adapter;
    String[] cityList = {"Almaty", "Astana", "Taraz", "Kyzylorda", "Atyrau", "Aktau", "Karaganda"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new CityAdapter(this, cityList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CityActivity.this, UniversityActivity.class);
                intent.putExtra("name", cityList[i]);
                startActivity(intent);
            }
        });
    }

    private class CityAdapter extends BaseAdapter{
        Activity activity;
        String[] cityList;
        LayoutInflater inflater;
        public CityAdapter(Activity activity, String[] cityList) {
            this.activity = activity;
            this.cityList = cityList;
            inflater = LayoutInflater.from(activity);
        }

        @Override
        public int getCount() {
            return cityList.length;
        }

        @Override
        public Object getItem(int i) {
            return cityList[i];
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
                convertView = inflater.inflate(R.layout.item_city, null);
                view.name = (TextView) convertView.findViewById(R.id.textViewCityName);
                view.imageView = (ImageView) convertView.findViewById(R.id.imageViewCityImage);
                convertView.setTag(view);
            } else {
                view = (ViewHolder) convertView.getTag();
            }
            view.name.setText(cityList[position]);
            return convertView;
        }

        public class ViewHolder {
            public TextView name;
            ImageView imageView;
        }
    }
}
