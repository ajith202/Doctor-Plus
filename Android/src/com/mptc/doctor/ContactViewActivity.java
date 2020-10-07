package com.mptc.doctor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactViewActivity extends Activity {
	LinearLayout phone_con, email_con, web_con, location_con, phone_img_con,
			web_img_con, email_img_con, location_img_con;
	TextView contact_website, contact_phone, contact_email, contact_location;
	String name, phone, website, location, latitude, longitude, email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent dataIntent = getIntent();
		setContentView(R.layout.activity_contact_view);
		phone_con = (LinearLayout) findViewById(R.id.contact_phone_con);
		web_con = (LinearLayout) findViewById(R.id.contact_web_con);
		location_con = (LinearLayout) findViewById(R.id.contact_location_con);
		email_con = (LinearLayout) findViewById(R.id.contact_email_con);
		phone_img_con = (LinearLayout) findViewById(R.id.contact_phone_img_con);
		email_img_con = (LinearLayout) findViewById(R.id.contact_email_img_con);
		web_img_con = (LinearLayout) findViewById(R.id.contact_web_img_con);
		location_img_con = (LinearLayout) findViewById(R.id.contact_location_img_con);
		contact_website = (TextView) findViewById(R.id.contact_web);
		contact_email = (TextView) findViewById(R.id.contact_email);
		contact_phone = (TextView) findViewById(R.id.contact_phone);
		contact_location = (TextView) findViewById(R.id.contact_location);
		name = dataIntent.getStringExtra("name");
		phone = dataIntent.getStringExtra("phone");
		email = dataIntent.getStringExtra("email");
		website = dataIntent.getStringExtra("website");
		location = dataIntent.getStringExtra("location");
		latitude = dataIntent.getStringExtra("latitude");
		longitude = dataIntent.getStringExtra("longitude");
		contact_phone.setText(phone);
		contact_location.setText(location);
		contact_email.setText(email);
		contact_website.setText(website);
		
		phone_con.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:" + phone));
				startActivity(callIntent);
			}
		});
		web_con.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(website));
				startActivity(webIntent);
			}
		});
		location_con.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent geoIntent = new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("geo:0,0?q="+latitude+","+longitude+"("+name+")"));
				startActivity(geoIntent);
			}
		});
		email_con.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
			            "mailto",email, null));
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
			startActivity(Intent.createChooser(emailIntent, "Send email..."));
			}
		});
	}
}
