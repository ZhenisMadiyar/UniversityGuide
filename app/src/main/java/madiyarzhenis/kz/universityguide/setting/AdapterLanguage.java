package madiyarzhenis.kz.universityguide.setting;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import madiyarzhenis.kz.universityguide.R;

/**
 * Created by madiyar on 11/20/14.
 */
public class AdapterLanguage extends BaseAdapter {
    Activity activity;
    String lang2[];
    int img[];
    public AdapterLanguage(Activity activity, String[] lang2, int[] images) {
        this.activity = activity;
        this.lang2 = lang2;
        this.img = images;
    }

    @Override
    public int getCount() {
        return lang2.length;
    }

    @Override
    public Object getItem(int position) {
        return lang2[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null){
            view = View.inflate(activity,
                    R.layout.row_item_language, null);
            holder = new ViewHolder();

            holder.text = (TextView) view.findViewById(R.id.textViewLang);
            holder.imageView = (ImageView) view.findViewById(R.id.imageView);

            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        holder.text.setText(lang2[position]);
        holder.imageView.setBackgroundResource(img[position]);
        return view;
    }

    private static class ViewHolder {
        TextView text;
        ImageView imageView;
    }
}
