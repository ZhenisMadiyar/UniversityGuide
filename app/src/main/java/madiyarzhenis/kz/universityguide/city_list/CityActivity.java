package madiyarzhenis.kz.universityguide.city_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import madiyarzhenis.kz.universityguide.R;
import madiyarzhenis.kz.universityguide.list_of_university.UniversityActivity;
import madiyarzhenis.kz.universityguide.models.City;

/**
 * Created by Zhenis Madiyar on 7/26/2015.
 */
public class CityActivity extends ActionBarActivity{
    ListView listView;
    CityAdapter adapter;
    String[] cityList = {"Almaty", "Astana", "Taraz", "Kyzylorda", "Atyrau", "Aktau", "Karaganda"};
    Gson gson;
    ArrayList<City> arrayList;
    Map<String, Object> parameter;
    public static String TAG_OBJECT_ID = "objectId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        arrayList = new ArrayList<>();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.listView);
        gson = new Gson();
        parameter = new HashMap<>();
        if (isOnline()) {
            ParseCloud.callFunctionInBackground("city", parameter, new FunctionCallback<Object>() {
                public void done(Object response, ParseException e) {
                    arrayList.clear();
                    if (e != null) {
                        Log.i("E", "error");
                        Log.e("Exception",e.toString());
                    } else {
                        String json = gson.toJson(response);
                        if (json.equals("[]")) {
                            Log.i("json", "null");
                        } else {
                            Log.i("JSON_CITY", json);
                        }
                        try {
                            JSONArray jsonArray = new JSONArray(json);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                JSONObject estimatedData = jsonObject.getJSONObject("estimatedData");
                                JSONObject jsonImage = estimatedData.getJSONObject("image");
                                String imageUrl = jsonImage.getString("url");
                                String name = estimatedData.getString("name");
                                String objectUrl = jsonObject.getString("objectId");
                                Log.i("image, name, object", imageUrl+","+name+","+objectUrl);
                                arrayList.add(new City(name, imageUrl, objectUrl));
                            }
                            adapter = new CityAdapter(CityActivity.this, arrayList);
                            listView.setAdapter(adapter);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CityActivity.this, UniversityActivity.class);
                intent.putExtra("name", cityList[i]);
                intent.putExtra(TAG_OBJECT_ID, arrayList.get(i).getObjectId());
                startActivity(intent);
            }
        });
    }

    public class CityAdapter extends BaseAdapter{
        Context activity;
        LayoutInflater inflater;
        ArrayList<City> cityList;
        public CityAdapter(Activity activity, ArrayList<City> cityList) {
            this.activity = activity;
            this.cityList = cityList;
            inflater = LayoutInflater.from(activity);
        }

        @Override
        public int getCount() {
            return cityList.size();
        }

        @Override
        public Object getItem(int i) {
            return cityList.get(i);
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
            Log.i("ImageUrl", cityList.get(position).getImageUrl());
            view.name.setText(cityList.get(position).getName());
            Picasso.with(activity)
                    .load(cityList.get(position).getImageUrl())
                    .into(view.imageView);
            return convertView;
        }

        public class ViewHolder {
            public TextView name;
            ImageView imageView;
        }
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
