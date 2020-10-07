package com.mptc.doctor.Adapters;

import com.mptc.doctor.R;
import com.mptc.doctor.misc.ImageRound;
import com.mptc.doctor.webservice.ImageParse;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DoctorsListAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] id;
	private final String[] name;
	private final String[] qualification;
	private final String[] department;
	private final String[] image;

	public DoctorsListAdapter(Activity context, String[] id, String[] name,
			String[] qualification, String[] department, String[] image) {
		super(context, R.layout.listview_search_result_item, name);
		this.context = context;
		this.id = id;
		this.name = name;
		this.qualification = qualification;
		this.department = department;
		this.image = image;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.listview_doctor_item, null,
				true);
		TextView txt_name = (TextView) rowView
				.findViewById(R.id.list_doctor_name);
		TextView txt_department = (TextView) rowView
				.findViewById(R.id.list_doctor_department);
		ImageView im_photo = (ImageView) rowView
				.findViewById(R.id.list_doctor_photo);
		txt_name.setText(name[position]);
		txt_department.setText(department[position]);
		im_photo.setImageBitmap(ImageRound.makeRoundedShape(ImageParse
				.base64ToImage(image[position])));
		return rowView;
	}

}