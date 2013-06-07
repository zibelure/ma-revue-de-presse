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
import fr.zibelure.marevuedepresse.model.Reponse;
import fr.zibelure.mesnouvelles.R;

public class ListeReponsesAdapter extends ArrayAdapter<Reponse> {

	Context context;

	public ListeReponsesAdapter(Context context, int resourceId,
			List<Reponse> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imageView;
		TextView txtTitle;
		TextView txtDesc;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Reponse reponse = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.activity_list_item, null);
			holder = new ViewHolder();
			holder.txtDesc = (TextView) convertView.findViewById(R.id.description);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.titre);
			holder.imageView = (ImageView) convertView.findViewById(R.id.img);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.txtDesc.setText(Html.fromHtml(reponse.getContenu()));
		holder.txtTitle.setText(Html.fromHtml(reponse.getTitre()));
		holder.imageView.setImageBitmap(reponse.getImage());
		
		/*
		if(reponse.getLienImage() != null) {			
			Uri imageURI = Uri.parse(reponse.getLienImage());
			holder.imageView.setImageURI(imageURI);
		}
		*/

		return convertView;
	}
}
