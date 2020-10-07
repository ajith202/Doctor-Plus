package com.mptc.doctor.Adapters;

import com.mptc.doctor.R;
import com.mptc.doctor.misc.ImageRound;
import com.mptc.doctor.webservice.ImageParse;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultListAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] ids;
	private final String[] names;
	private final String[] distance;
	private final String[] logo;

	public SearchResultListAdapter(Activity context, String[] ids,String[] names,String[] distance,String[] logo) {
		super(context, R.layout.listview_search_result_item, names);
		this.context = context;
		this.ids = ids;
		this.names = names;
		this.distance = distance;
		this.logo = logo;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.listview_search_result_item, null, true);
		TextView txt_name = (TextView) rowView.findViewById(R.id.search_result_name);
		TextView txt_distance = (TextView) rowView.findViewById(R.id.search_result_distance);
		ImageView im_logo = (ImageView) rowView.findViewById(R.id.search_result_logo);
		txt_name.setText(names[position]);
		txt_distance.setText((Math.round(Float.parseFloat(distance[position])))+"km");
		im_logo.setImageBitmap(ImageRound.makeRoundedShape(ImageParse
				.base64ToImage(logo[position])));
		return rowView;
	}
}