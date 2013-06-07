package fr.zibelure.marevuedepresse.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.zibelure.marevuedepresse.model.Sujet;
import fr.zibelure.mesnouvelles.R;

public class ListeSujetsAdapter extends ArrayAdapter<Sujet> {

	Context context;

	public ListeSujetsAdapter(Context context, int resourceId,
			List<Sujet> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imageView;
		TextView txtTitle;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Sujet theme = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.activity_list_item, null);
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView.findViewById(R.id.titre);
			holder.imageView = (ImageView) convertView.findViewById(R.id.img);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.txtTitle.setText(Html.fromHtml(theme.getTitre()));
		holder.imageView.setImageBitmap(theme.getImage());
		
		/*
		if(reponse.getLienImage() != null) {			
			Uri imageURI = Uri.parse(reponse.getLienImage());
			holder.imageView.setImageURI(imageURI);
		}
		*/

		return convertView;
	}
}
