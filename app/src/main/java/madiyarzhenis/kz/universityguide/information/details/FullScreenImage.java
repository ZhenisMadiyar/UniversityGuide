package madiyarzhenis.kz.universityguide.information.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

import madiyarzhenis.kz.universityguide.R;


/**
 * Created by Admin on 26.06.2015.
 */
public class FullScreenImage extends Activity {
    private ViewPager mViewPager;
    CustomPagerAdapter mCustomPagerAdapter;
    String[] photo;
    int pos;
//    ProgressBar dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen_view);
        Intent intent = getIntent();
        photo = intent.getStringArrayExtra("urlArray");
        Log.i("ArrayLength", photo.length+"");
        pos = intent.getIntExtra("position", 0);
        mCustomPagerAdapter = new CustomPagerAdapter(this, photo);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(pos);
    }
    class CustomPagerAdapter extends PagerAdapter {
        Context mContext;
        String[] photo;
        LayoutInflater mLayoutInflater;
        public CustomPagerAdapter(Context context, String[] photo) {
            mContext = context;
            this.photo = photo;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return photo.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
//            dialog = (ProgressBar) itemView.findViewById(R.id.progressBar);
//            dialog.setVisibility(View.INVISIBLE);
//            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            TouchImageView imageView = (TouchImageView) itemView.findViewById(R.id.imageView);
            final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarInformationPhoto);
//            ImageLoader.getInstance().displayImage(photo.get(position), imageView, options, animateFirstListener);
//            new DownloadImageTask((TouchImageView) itemView.findViewById(R.id.imageView)).execute(photo.get(position));
            Picasso.with(FullScreenImage.this)
                    .load(photo[position])
                    .into(imageView, new Callback() {

                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
            imageView.setMaxZoom(4f);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bmImage.setImageBitmap(result);
//            dialog.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
