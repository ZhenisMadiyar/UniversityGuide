/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package madiyarzhenis.kz.universityguide.information.details;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.google.gson.Gson;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import madiyarzhenis.kz.universityguide.BaseActivity;
import madiyarzhenis.kz.universityguide.R;
import madiyarzhenis.kz.universityguide.SlidingTabLayout;

/**
 * <p>Another implementation of FlexibleImage pattern + ViewPager.</p>
 * <p/>
 * <p>This is a completely different approach comparing to FlexibleImageWithViewPager2Activity.
 * <p/>
 * <p>Descriptions of this pattern:</p>
 * <ul>
 * <li>When the current tab is changed, tabs will be translated in Y-axis
 * using scrollY of the new page's Fragment.</li>
 * <li>The parent Activity and children Fragments strongly depend on each other,
 * so if you need to use this pattern, maybe you should extract some interfaces from them.<br>
 * (This is just an example, so we won't do it here.)</li>
 * <li>The parent Activity and children Fragments communicate bidirectionally:
 * the parent Activity will update the Fragment's state when the tab is changed,
 * and Fragments will tell the parent Activity to update the tab's translationY.</li>
 * </ul>
 * <p/>
 * <p>SlidingTabLayout and SlidingTabStrip are from google/iosched:<br>
 * https://github.com/google/iosched</p>
 */
public class FlexibleSpaceWithImageWithViewPagerTabActivity extends BaseActivity {

    protected static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private ViewPager mPager;
    private NavigationAdapter mPagerAdapter;
    private SlidingTabLayout mSlidingTabLayout;
    private int mFlexibleSpaceHeight;
    private int mTabHeight;
    ImageView imageView;
    Map<String, Object> parameter;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexiblespacewithimagewithviewpagertab);

        Intent intent = getIntent();
        mPager = (ViewPager) findViewById(R.id.pager);
        imageView = (ImageView) findViewById(R.id.image);
        mFlexibleSpaceHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mTabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(intent.getStringExtra("name"));
        gson = new Gson();
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        parameter = new HashMap<String, Object>();
        parameter.put("university_id", intent.getStringExtra("objectId"));
        Log.i("objectId", intent.getStringExtra("objectId"));
        ParseCloud.callFunctionInBackground("university", parameter, new FunctionCallback<Object>() {
            public void done(Object response, ParseException e) {
//                arrayList = new ArrayList<>();
//                objectIDArray.clear();
                if (e != null) {
                    Log.i("E", "error");
                    Log.e("Exception", e.toString());
                } else {
                    String json = gson.toJson(response);
                    if (json.equals("[]")) {
                        Log.i("json", "null");
                        Log.i("json_null", json);
                    } else {
                        Log.i("JSON_UNIVERSITY_ABOUT", json);
                    }
                    try {
                        JSONArray jsonArray = new JSONArray(json);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        JSONObject estimatedData = jsonObject.getJSONObject("estimatedData");
                        JSONObject jsonImage = estimatedData.getJSONObject("image");
                        String imageUrl = jsonImage.getString("url");
                        String aboutUs = estimatedData.getString("about_us");
                        String magistratura = estimatedData.getString("magistratura");
                        String dormitory = estimatedData.getString("dormitory");
                        String abiturient = estimatedData.getString("abiturient");
                        String photo_gallery = estimatedData.getString("photo_gallery");
                        String video_gallery = estimatedData.getString("video_gallery");
                        String faculty = estimatedData.getString("faculty");
                        String map = estimatedData.getString("map");
                        String phone_number = estimatedData.getString("phone_number");
                        mPagerAdapter = new NavigationAdapter(getSupportFragmentManager(), aboutUs, abiturient, magistratura, dormitory
                                , photo_gallery, video_gallery, faculty, map, phone_number);
                        mPager.setAdapter(mPagerAdapter);
                        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
                        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.accent));
                        mSlidingTabLayout.setDistributeEvenly(true);
                        mSlidingTabLayout.setViewPager(mPager);
                        Picasso.with(FlexibleSpaceWithImageWithViewPagerTabActivity.this)
                                .load(imageUrl)
                                .into(imageView);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

//        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        Log.i("COLOR=", getDominantColor1(bitmap) + "");


        // Initialize the first Fragment's state when layout is completed.
        ScrollUtils.addOnGlobalLayoutListener(mSlidingTabLayout, new Runnable() {
            @Override
            public void run() {
                translateTab(0, false);
            }
        });
    }

    /**
     * Called by children Fragments when their scrollY are changed.
     * They all call this method even when they are inactive
     * but this Activity should listen only the active child,
     * so each Fragments will pass themselves for Activity to check if they are active.
     *
     * @param scrollY scroll position of Scrollable
     * @param s       caller Scrollable view
     */
    public void onScrollChanged(int scrollY, Scrollable s) {
        FlexibleSpaceWithImageBaseFragment fragment =
                (FlexibleSpaceWithImageBaseFragment) mPagerAdapter.getItemAt(mPager.getCurrentItem());
        if (fragment == null) {
            return;
        }
        View view = fragment.getView();
        if (view == null) {
            return;
        }
        Scrollable scrollable = (Scrollable) view.findViewById(R.id.scroll);
        if (scrollable == null) {
            return;
        }
        if (scrollable == s) {
            // This method is called by not only the current fragment but also other fragments
            // when their scrollY is changed.
            // So we need to check the caller(S) is the current fragment.
            int adjustedScrollY = Math.min(scrollY, mFlexibleSpaceHeight - mTabHeight);
            translateTab(adjustedScrollY, false);
            propagateScroll(adjustedScrollY);
        }
    }

    private void translateTab(int scrollY, boolean animated) {
        int flexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        View imageView = findViewById(R.id.image);
        View overlayView = findViewById(R.id.overlay);
        TextView titleView = (TextView) findViewById(R.id.title);

        // Translate overlay and image
        float flexibleRange = flexibleSpaceImageHeight - getActionBarSize();
        int minOverlayTransitionY = tabHeight - overlayView.getHeight();
        ViewHelper.setTranslationY(overlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(imageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        ViewHelper.setAlpha(overlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY - tabHeight) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        setPivotXToTitle(titleView);
        ViewHelper.setPivotY(titleView, 0);
        ViewHelper.setScaleX(titleView, scale);
        ViewHelper.setScaleY(titleView, scale);

        // Translate title text
        int maxTitleTranslationY = flexibleSpaceImageHeight - tabHeight - getActionBarSize();
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(titleView, titleTranslationY);

        // If tabs are moving, cancel it to start a new animation.
        ViewPropertyAnimator.animate(mSlidingTabLayout).cancel();
        // Tabs will move between the top of the screen to the bottom of the image.
        float translationY = ScrollUtils.getFloat(-scrollY + mFlexibleSpaceHeight - mTabHeight, 0, mFlexibleSpaceHeight - mTabHeight);
        if (animated) {
            // Animation will be invoked only when the current tab is changed.
            ViewPropertyAnimator.animate(mSlidingTabLayout)
                    .translationY(translationY)
                    .setDuration(200)
                    .start();
        } else {
            // When Fragments' scroll, translate tabs immediately (without animation).
            ViewHelper.setTranslationY(mSlidingTabLayout, translationY);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle(View view) {
        final TextView mTitleView = (TextView) view.findViewById(R.id.title);
        Configuration config = getResources().getConfiguration();
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(mTitleView, view.findViewById(android.R.id.content).getWidth());
        } else {
            ViewHelper.setPivotX(mTitleView, 0);
        }
    }

    private void propagateScroll(int scrollY) {
        // Set scrollY for the fragments that are not created yet
        mPagerAdapter.setScrollY(scrollY);

        // Set scrollY for the active fragments
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            // Skip current item
            if (i == mPager.getCurrentItem()) {
                continue;
            }

            // Skip destroyed or not created item
            FlexibleSpaceWithImageBaseFragment f =
                    (FlexibleSpaceWithImageBaseFragment) mPagerAdapter.getItemAt(i);
            if (f == null) {
                continue;
            }

            View view = f.getView();
            if (view == null) {
                continue;
            }
            f.setScrollY(scrollY, mFlexibleSpaceHeight);
            f.updateFlexibleSpace(scrollY);
        }
    }

    /**
     * This adapter provides three types of fragments as an example.
     * {@linkplain #createItem(int)} should be modified if you use this example for your app.
     */
    private class NavigationAdapter extends CacheFragmentStatePagerAdapter {

        private final String[] TITLES = new String[]{getString(R.string.o_nas), getString(R.string.facultet),
                getString(R.string.bachelor), getString(R.string.magistratura),
                getString(R.string.dormitory), getString(R.string.video_gallery),
                getString(R.string.photo_gallery), getString(R.string.price)};

        String aboutUs;
        String abiturient;
        String magistratura;
        String dormitory;
        String photo_gallery;
        String video_gallery;
        String faculty;
        String map;
        String phone_number;
        private int mScrollY;
        String[] photoUrls;
        String[] videoIds;

        public NavigationAdapter(FragmentManager fm, String aboutUs, String abiturient, String magistratura, String dormitory,
                                 String photo_gallery, String video_gallery, String faculty, String map, String phone_number) {
            super(fm);

            this.aboutUs = aboutUs;
            this.abiturient = abiturient;
            this.magistratura = magistratura;
            this.dormitory = dormitory;
            this.photoUrls = photo_gallery.split(",");
            this.videoIds = video_gallery.split(",");
            this.faculty = faculty;
            this.map = map;
            this.phone_number = phone_number;
            Log.i("ABOUT", aboutUs);
        }

        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }

        @Override
        protected Fragment createItem(int position) {
            FlexibleSpaceWithImageBaseFragment f;
            switch (position) {
                case 0: {//about us
                    f = new AboutUniversity(aboutUs);
                    break;
                }
                case 1: {//faculty
                    f = new Faculty(faculty);
                    break;
                }
                case 2: {//bakalavr
                    f = new Bachelor(abiturient);
                    break;
                }
                case 3: {//magistratura
                    f = new Magistratura(magistratura);
                    break;
                }
                case 4: {//dormitory
                    f = new Dormitory(dormitory);
                    break;
                }
                case 5: {//video gallery
                    f = new VideoFragment();
                    break;
                }
                case 6: {//photo_gallery
                    f = new PhotoGallery();
                    break;
                }
                case 7: {//price
                    f = new PriceList();
                    break;
                }
                case 8:
                default: {//contact
                    f = new Contact(map);
                    break;
                }
            }
            f.setArguments(mScrollY);
            return f;
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }

    public int getDominantColor1(Bitmap bitmap) {

        if (bitmap == null)
            throw new NullPointerException();

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int size = width * height;
        int pixels[] = new int[size];

        Bitmap bitmap2 = bitmap.copy(Bitmap.Config.ARGB_4444, false);

        bitmap2.getPixels(pixels, 0, width, 0, 0, width, height);

        final List<HashMap<Integer, Integer>> colorMap = new ArrayList<HashMap<Integer, Integer>>();
        colorMap.add(new HashMap<Integer, Integer>());
        colorMap.add(new HashMap<Integer, Integer>());
        colorMap.add(new HashMap<Integer, Integer>());

        int color = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        Integer rC, gC, bC;
        for (int i = 0; i < pixels.length; i++) {
            color = pixels[i];

            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);

            rC = colorMap.get(0).get(r);
            if (rC == null)
                rC = 0;
            colorMap.get(0).put(r, ++rC);

            gC = colorMap.get(1).get(g);
            if (gC == null)
                gC = 0;
            colorMap.get(1).put(g, ++gC);

            bC = colorMap.get(2).get(b);
            if (bC == null)
                bC = 0;
            colorMap.get(2).put(b, ++bC);
        }

        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            int max = 0;
            int val = 0;
            for (Map.Entry<Integer, Integer> entry : colorMap.get(i).entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    val = entry.getKey();
                }
            }
            rgb[i] = val;
        }

        int dominantColor = Color.rgb(rgb[0], rgb[1], rgb[2]);

        return dominantColor;
    }
}
