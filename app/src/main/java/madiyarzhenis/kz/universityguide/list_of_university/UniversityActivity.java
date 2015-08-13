package madiyarzhenis.kz.universityguide.list_of_university;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import madiyarzhenis.kz.universityguide.R;
import madiyarzhenis.kz.universityguide.city_list.CityActivity;
import madiyarzhenis.kz.universityguide.information.details.FlexibleSpaceWithImageWithViewPagerTabActivity;
import madiyarzhenis.kz.universityguide.list_of_university.adapters.BaseInflaterAdapter;
import madiyarzhenis.kz.universityguide.list_of_university.adapters.CardItemData;
import madiyarzhenis.kz.universityguide.list_of_university.adapters.inflaters.CardInflater;
import madiyarzhenis.kz.universityguide.models.University;

/**
 * Created by Zhenis Madiyar on 7/26/2015.
 */
public class UniversityActivity extends ActionBarActivity {
    ListView listViewUniversity;
    ViewPager listViewTop;
    ViewPagerAdapter adapterView;
//    UniversityAdapter adapter;
    TopUniversityAdapter adapterTop;
    ArrayList<University> arrayList;

    String[] top = {"KBTU", "SDU", "KIMEP", "KazGU", "AES"};
    int img[] = {R.drawable.kbtu, R.drawable.kbtu, R.drawable.kbtu, R.drawable.kbtu, R.drawable.kbtu};
    String[] university = {"KazNTU", "SDU", "AGU", "KazGU", "ZhenPI", "SDU", "AGU", "KazGU", "ZhenPI"};
    HashMap<String, Object> parameter;
    Gson gson;
    BaseInflaterAdapter<CardItemData> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);
        final Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listViewUniversity = (ListView) findViewById(R.id.listViewUniversity);
        listViewTop = (ViewPager) findViewById(R.id.HorizontalListViewTop);

        listViewUniversity.addHeaderView(new View(this));
        listViewUniversity.addFooterView(new View(this));

//        adapter = new UniversityAdapter(UniversityActivity.this, university);


        gson = new Gson();
        parameter = new HashMap<String, Object>();
        final String objectId = intent.getStringExtra(CityActivity.TAG_OBJECT_ID);
        parameter.put("city_id", objectId);
        adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater());
        ParseCloud.callFunctionInBackground("universities", parameter, new FunctionCallback<Object>() {
            public void done(Object response, ParseException e) {
                arrayList = new ArrayList<>();
                if (e != null) {
                    Log.i("E", "error");
                    Log.e("Exception", e.toString());
                } else {
                    String json = gson.toJson(response);
                    if (json.equals("[]")) {
                        Log.i("json", "null");
                    } else {
                        Log.i("JSON_UNIVER", json);
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
                            Log.i("image, name, object", imageUrl + "," + name + "," + objectUrl);
                            CardItemData data = new CardItemData(name, objectUrl, imageUrl);
                            adapter.addItem(data, false);
                        }
                        listViewUniversity.setAdapter(adapter);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        listViewUniversity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(UniversityActivity.this, FlexibleSpaceWithImageWithViewPagerTabActivity.class);
                intent1.putExtra("nameVUZ", university[i]);
                startActivity(intent1);
            }
        });
        adapterView = new ViewPagerAdapter(UniversityActivity.this, top, img);
        // Binds the Adapter to the ViewPager
        listViewTop.setAdapter(adapterView);
//        adapterTop = new TopUniversityAdapter(UniversityActivity.this, top);
//        listViewTop.setAdapter(adapterTop);
//        listViewTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(), "CLICKED", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public class ViewPagerAdapter extends PagerAdapter {
        // Declare Variables
        Context context;
        String[] nameVuz;
        int[] img;
        LayoutInflater inflater;

        public ViewPagerAdapter(Context context, String[] rank,
                                int[] flag) {
            this.context = context;
            this.nameVuz = rank;
            this.img = flag;
        }

        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // Declare Variables
            TextView name;
            ImageView imgflag;

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.item_university_top, container, false);

            // Locate the TextViews in viewpager_item.xml
            name = (TextView) itemView.findViewById(R.id.textViewTop);

            // Capture position and set to the TextViews
            name.setText(nameVuz[position]);

            // Locate the ImageView in viewpager_item.xml
            imgflag = (ImageView) itemView.findViewById(R.id.imageViewTop);
            // Capture position and set to the ImageView
            imgflag.setImageResource(img[position]);

            // Add viewpager_item.xml to ViewPager
            ((ViewPager) container).addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((RelativeLayout) object);

        }
    }
    private class TopUniversityAdapter extends BaseAdapter{
        Activity activity;
        String[] top;
        LayoutInflater inflater;
        public TopUniversityAdapter(Activity activity, String[] university) {
            this.activity = activity;
            this.top = university;
            inflater = LayoutInflater.from(activity);
        }

        @Override
        public int getCount() {
            return top.length;
        }

        @Override
        public Object getItem(int i) {
            return top[i];
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
                convertView = inflater.inflate(R.layout.item_university_top, null);
                view.name = (TextView) convertView.findViewById(R.id.textViewTop);
                view.imageView = (ImageView) convertView.findViewById(R.id.imageViewTop);
                convertView.setTag(view);
            } else {
                view = (ViewHolder) convertView.getTag();
            }
            view.name.setText(top[position]);
            return convertView;
        }

        public class ViewHolder {
            public TextView name;
            ImageView imageView;
        }
    }
}
