package madiyarzhenis.kz.universityguide.details.adapter_video;

import android.view.View;
import android.view.ViewGroup;

public interface IAdapterViewInflaterVideo<T>
{
	public View inflate(BaseInflaterAdapterVideo<T> adapter, int pos, View convertView, ViewGroup parent);
}
