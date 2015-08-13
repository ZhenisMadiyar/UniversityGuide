package madiyarzhenis.kz.universityguide.list_of_university.adapters.inflaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import madiyarzhenis.kz.universityguide.MyApplication;
import madiyarzhenis.kz.universityguide.R;
import madiyarzhenis.kz.universityguide.list_of_university.adapters.BaseInflaterAdapter;
import madiyarzhenis.kz.universityguide.list_of_university.adapters.CardItemData;
import madiyarzhenis.kz.universityguide.list_of_university.adapters.IAdapterViewInflater;

/**
 * Created with IntelliJ IDEA.
 * User: Justin
 * Date: 10/6/13
 * Time: 12:47 AM
 */
public class CardInflater implements IAdapterViewInflater<CardItemData>
{
	@Override
	public View inflate(final BaseInflaterAdapter<CardItemData> adapter, final int pos, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.list_item_card_university, parent, false);
			holder = new ViewHolder(convertView);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		final CardItemData item = adapter.getTItem(pos);
		holder.updateDisplay(item);

		return convertView;
	}

	private class ViewHolder
	{
		private View m_rootView;
		private TextView name;
		private ImageView imageView;

		public ViewHolder(View rootView)
		{
			m_rootView = rootView;
			name = (TextView) m_rootView.findViewById(R.id.textName);
			imageView = (ImageView) m_rootView.findViewById(R.id.imageViewUniver);
			rootView.setTag(this);
		}

		public void updateDisplay(CardItemData item) {
			name.setText(item.getName());
			Picasso.with(MyApplication.getAppContext())
					.load(item.getImageUrl())
					.into(imageView);

		}
	}
}
