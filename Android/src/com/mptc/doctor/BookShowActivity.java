package com.mptc.doctor;

import com.mptc.doctor.misc.ImageRound;
import com.mptc.doctor.webservice.ImageParse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BookShowActivity extends Activity {
	Intent hospitalData;
	String hospital_name, doctor_name, book_date, book_time, book_id,book_session,
			hospital_logo;
	TextView input_hospital, input_doctor, input_time, input_date, input_id;
	ImageView logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_show);
		hospitalData = getIntent();
		hospital_name = hospitalData.getStringExtra("hospital_name");
		book_session = hospitalData.getStringExtra("book_session");
		hospital_logo = hospitalData.getStringExtra("hospital_logo");
		doctor_name = hospitalData.getStringExtra("doctor_name");
		book_date = hospitalData.getStringExtra("book_date");
		book_time = hospitalData.getStringExtra("book_time");
		book_id = hospitalData.getStringExtra("book_id");
		logo = (ImageView) findViewById(R.id.booking_show_logo);
		input_hospital = (TextView) findViewById(R.id.book_show_hospital);
		input_doctor = (TextView) findViewById(R.id.book_show_doctor);
		input_time = (TextView) findViewById(R.id.book_show_time);
		input_date = (TextView) findViewById(R.id.book_show_date);
		input_id = (TextView) findViewById(R.id.book_show_id);
		input_id.setText("ID : #" + book_id);
		input_hospital.setText(hospital_name);
		input_doctor.setText("Doctor : "+doctor_name);
		input_date.setText("Date : "+book_date);
		input_time.setText("Time :"+ book_time+"( "+book_session+" )");
		logo.setImageBitmap(ImageRound.makeRoundedShape(ImageParse
				.base64ToImage(hospital_logo)));
	}
}
