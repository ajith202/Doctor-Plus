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

public class BookingsListAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] hospital;
	private final String[] time;
	private final String[] logo;
	private final String[] doctor;
	private final String[] session;
	private final String[] date;

	public BookingsListAdapter(Activity context, String[] hospital,
			String[] logo, String[] doctor, String[] session,String[] time,String[] date) {
		super(context, R.layout.listview_search_result_item, hospital);
		this.context = context;
		this.hospital = hospital;
		this.logo = logo;
		this.time = time;
		this.doctor = doctor;
		this.session = session;
		this.date = date;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.listview_bookings, null,
				true);
		ImageView img_logo = (ImageView) rowView
				.findViewById(R.id.listview_booking_image);
		TextView txt_hospital = (TextView) rowView
				.findViewById(R.id.listview_booking_hospital_name);
		TextView txt_time = (TextView) rowView
				.findViewById(R.id.listview_booking_time);
		TextView txt_date = (TextView) rowView
				.findViewById(R.id.listview_booking_date);
		TextView txt_doctor = (TextView) rowView.findViewById(R.id.listview_booking_doctor);
		txt_hospital.setText(hospital[position]);
		txt_time.setText(time[position]);
		txt_doctor.setText(doctor[position]);
		txt_doctor.setText(date[position]);
		img_logo.setImageBitmap(ImageRound.makeRoundedShape(ImageParse
				.base64ToImage(logo[position])));
		return rowView;
	}

}