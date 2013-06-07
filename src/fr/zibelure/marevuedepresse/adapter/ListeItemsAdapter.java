package fr.zibelure.marevuedepresse.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.zibelure.marevuedepresse.model.Item;
import fr.zibelure.mesnouvelles.R;

public class ListeItemsAdapter extends ArrayAdapter<Item> {

	Context context;

	public ListeItemsAdapter(Context context, int resourceId,
			List<Item> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imageView;
		TextView txtTitle;
		TextView txtDesc;
		TextView txtDateEtSources;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Item item = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.activity_list_item, null);
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView.findViewById(R.id.titre);
			holder.txtDesc = (TextView) convertView.findViewById(R.id.description);
			holder.imageView = (ImageView) convertView.findViewById(R.id.img);
			holder.txtDateEtSources = (TextView) convertView.findViewById(R.id.dateEtSources);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();


		if(item.getTitre() != null) {
			holder.txtTitle.setText(Html.fromHtml(item.getTitre()));
		}
		if(item.getContenu() != null) {
			holder.txtDesc.setText(Html.fromHtml(item.getContenu()));
		}
		StringBuilder dateEtSources = new StringBuilder();
		if(item.getDatePublication() != null) {
			dateEtSources.append(Html.fromHtml(item.getDatePublication().toString()));
		}
		if(item.getSource() != null) {
			if(dateEtSources.length()>0) dateEtSources.append(" ");
			dateEtSources.append("par ");
			dateEtSources.append((Html.fromHtml(item.getSource())));
		}
		if(item.getFournisseur() != null) {
			if(dateEtSources.length()>0) dateEtSources.append(" ");
			dateEtSources.append("via ");
			dateEtSources.append((Html.fromHtml(item.getFournisseur())));
		}
		holder.txtDateEtSources.setText(dateEtSources.toString());
		holder.imageView.setImageBitmap(item.getImage());
		
		/*
		if(reponse.getLienImage() != null) {			
			Uri imageURI = Uri.parse(reponse.getLienImage());
			holder.imageView.setImageURI(imageURI);
		}
		*/

		return convertView;
	}
}
