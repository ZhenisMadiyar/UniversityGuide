package madiyarzhenis.kz.universityguide.information.details.adapter_video.inflaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
public class CardInflaterVideo implements IAdapterViewInflaterVideo<CardItemDataVideo>
{
	@Override
	public View inflate(final BaseInflaterAdapterVideo<CardItemDataVideo> adapter, final int pos, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.item_video_gallery, parent, false);
			holder = new ViewHolder(convertView);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		final CardItemDataVideo item = adapter.getTItem(pos);
		holder.updateDisplay(item);

		return convertView;
	}

	private class ViewHolder
	{
		private View m_rootView;
		private TextView videoTitle;
		private ImageView imageView;

		public ViewHolder(View rootView)
		{
			m_rootView = rootView;
			videoTitle = (TextView) m_rootView.findViewById(R.id.textViewVideo);
			imageView = (ImageView) m_rootView.findViewById(R.id.imageViewVideo);
			rootView.setTag(this);
		}

		public void updateDisplay(CardItemDataVideo item)
		{
			videoTitle.setText(item.getVideoTitle());
		}
	}
}
