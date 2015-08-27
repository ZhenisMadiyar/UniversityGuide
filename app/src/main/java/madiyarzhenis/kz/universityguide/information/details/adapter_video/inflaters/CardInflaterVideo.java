package madiyarzhenis.kz.universityguide.information.details.adapter_video.inflaters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import madiyarzhenis.kz.universityguide.MyApplication;
import madiyarzhenis.kz.universityguide.R;
import madiyarzhenis.kz.universityguide.information.details.adapter_video.BaseInflaterAdapterVideo;
import madiyarzhenis.kz.universityguide.information.details.adapter_video.CardItemDataVideo;
import madiyarzhenis.kz.universityguide.information.details.adapter_video.IAdapterViewInflaterVideo;

/**
 * Created with IntelliJ IDEA.
 * User: Justin
 * Date: 10/6/13
 * Time: 12:47 AM
 */
public class CardInflaterVideo implements IAdapterViewInflaterVideo<CardItemDataVideo> {
    @Override
    public View inflate(final BaseInflaterAdapterVideo<CardItemDataVideo> adapter, final int pos, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_video_gallery, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CardItemDataVideo item = adapter.getTItem(pos);
        holder.updateDisplay(item);

        return convertView;
    }

    private class ViewHolder {
        private View m_rootView;
        private TextView videoTitleV;
        private ImageView imageView;

        public ViewHolder(View rootView) {
            m_rootView = rootView;
            imageView = (ImageView) m_rootView.findViewById(R.id.imageViewVideo);
            videoTitleV = (TextView) m_rootView.findViewById(R.id.title_video);
            rootView.setTag(this);
        }

        public void updateDisplay(CardItemDataVideo item) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.get("http://www.youtube.com/oembed?url=http://www.youtube.com/watch?v=" + item.getVideoId() + "&format=json",
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            String str = new String(bytes);
                            Log.i("JSON", str);
                            try {
                                JSONObject jsonObject = new JSONObject(str);
                                String videoTitle = jsonObject.getString("title");
                                String thubnail = jsonObject.getString("thumbnail_url");

                                videoTitleV.setText(videoTitle);
                                Picasso.with(MyApplication.getAppContext())
                                        .load(thubnail)
                                        .into(imageView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        }
                    });
        }
    }
}
