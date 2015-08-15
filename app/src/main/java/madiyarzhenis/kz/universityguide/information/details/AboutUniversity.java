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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.google.gson.Gson;

import java.util.Map;

import madiyarzhenis.kz.universityguide.R;

public class AboutUniversity extends FlexibleSpaceWithImageBaseFragment<ObservableScrollView> {
    Map<String, Object> parameter;
    Gson gson;
    String about_us;
    TextView textView;
    public AboutUniversity(String aboutUs) {
        this.about_us = aboutUs;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_university, container, false);

        final ObservableScrollView scrollView = (ObservableScrollView) view.findViewById(R.id.scroll);
        gson = new Gson();
        textView = (TextView) view.findViewById(R.id.textViewAbout);
        textView.setText(about_us);
        // TouchInterceptionViewGroup should be a parent view other than ViewPager.
        // This is a workaround for the issue #117:
        // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
        scrollView.setTouchInterceptionViewGroup((ViewGroup) view.findViewById(R.id.fragment_root));
//
//        parameter = new HashMap<String, Object>();
//        parameter.put("university_id", objectId);
//        ParseCloud.callFunctionInBackground("university", parameter, new FunctionCallback<Object>() {
//            public void done(Object response, ParseException e) {
////                arrayList = new ArrayList<>();
////                objectIDArray.clear();
//                if (e != null) {
//                    Log.i("E", "error");
//                    Log.e("Exception", e.toString());
//                } else {
//                    String json = gson.toJson(response);
//                    if (json.equals("[]")) {
//                        Log.i("json", "null");
//                        Log.i("json_null", json);
//                    } else {
//                        Log.i("JSON_UNIVERSITY_ABOUT", json);
//                    }
////                    try {
////                        JSONArray jsonArray = new JSONArray(json);
////                        for (int i = 0; i < jsonArray.length(); i++) {
////                            JSONObject jsonObject = jsonArray.getJSONObject(i);
////                            JSONObject estimatedData = jsonObject.getJSONObject("estimatedData");
////                            JSONObject jsonImage = estimatedData.getJSONObject("image");
////                            String imageUrl = jsonImage.getString("url");
////                            String name = estimatedData.getString("name");
////                            String objectUrl = jsonObject.getString("objectId");
////                            Log.i("image, name, object", imageUrl + "," + name + "," + objectUrl);
////                            data = new CardItemData(name, objectUrl, imageUrl);
////                            objectIDArray.add(objectUrl);
////                            nameArray.add(name);
////                            adapter.addItem(data, false);
////                        }
////                        listViewUniversity.setAdapter(adapter);
////                    } catch (JSONException e1) {
////                        e1.printStackTrace();
////                    }
//                }
//            }
//        });

        // Scroll to the specified offset after layout
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_SCROLL_Y)) {
            final int scrollY = args.getInt(ARG_SCROLL_Y, 0);
            ScrollUtils.addOnGlobalLayoutListener(scrollView, new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0, scrollY);
                }
            });
            updateFlexibleSpace(scrollY, view);
        } else {
            updateFlexibleSpace(0, view);
        }

        scrollView.setScrollViewCallbacks(this);

        return view;
    }

    @Override
    protected void updateFlexibleSpace(int scrollY) {
        // Sometimes scrollable.getCurrentScrollY() and the real scrollY has different values.
        // As a workaround, we should call scrollVerticallyTo() to make sure that they match.
        Scrollable s = getScrollable();
        s.scrollVerticallyTo(scrollY);

        // If scrollable.getCurrentScrollY() and the real scrollY has the same values,
        // calling scrollVerticallyTo() won't invoke scroll (or onScrollChanged()), so we call it here.
        // Calling this twice is not a problem as long as updateFlexibleSpace(int, View) has idempotence.
        updateFlexibleSpace(scrollY, getView());
    }

    @Override
    protected void updateFlexibleSpace(int scrollY, View view) {
        ObservableScrollView scrollView = (ObservableScrollView) view.findViewById(R.id.scroll);

        // Also pass this event to parent Activity
        FlexibleSpaceWithImageWithViewPagerTabActivity parentActivity =
                (FlexibleSpaceWithImageWithViewPagerTabActivity) getActivity();
        if (parentActivity != null) {
            parentActivity.onScrollChanged(scrollY, scrollView);
        }
    }
}
