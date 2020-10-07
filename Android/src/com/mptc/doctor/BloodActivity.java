package com.mptc.doctor;

import org.json.JSONArray;
import org.json.JSONException;

import com.mptc.doctor.alerts.Alerts;
import com.mptc.doctor.misc.ImageRound;
import com.mptc.doctor.webservice.ImageParse;
import com.mptc.doctor.webservice.WebService;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class BloodActivity extends TabActivity {
	String hos_id, hos_name, hos_email, hos_website, hos_phone, hos_location,
			hos_latitude, hos_longitude,hos_logo,bloodRsesponse;
	ImageView blood_logo;
	TextView hospitl_name, blood_location;
	private TabHost bloodTabHost;
	TabHost.TabSpec doctorTab, contactTab;
	Intent doctor_intent,contact_intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blood);
		Intent intent = getIntent();
		hos_id = intent.getStringExtra("blood_id");
		hospitl_name = (TextView) findViewById(R.id.blood_name);
		blood_location = (TextView) findViewById(R.id.blood_location);
		blood_logo = (ImageView) findViewById(R.id.blood_logo);
		bloodTabHost = getTabHost();
		
		Alerts.progressDialog(BloodActivity.this, "Loading..");
		bloodThread bloodThread = new bloodThread();
		bloodThread.start();

	}

	protected class bloodThread extends Thread {
		protected final static int HOS_SUCCESS = 1000;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			bloodRsesponse = WebService.getBloodByID(hos_id);
			responseHandler.sendEmptyMessage(HOS_SUCCESS);
			super.run();
		}
	}

	private Handler responseHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case bloodThread.HOS_SUCCESS: {
				Alerts.progressDialogClose();
				try {
					JSONArray hosJArray = new JSONArray(bloodRsesponse);
					hos_name = hosJArray.getJSONObject(0).getString("name");
					hos_email = hosJArray.getJSONObject(0).getString("email");
					hos_website = hosJArray.getJSONObject(0).getString("website");
					hos_website = hosJArray.getJSONObject(0).getString("website");
					hos_phone = hosJArray.getJSONObject(0).getString("phone");
					hos_location = hosJArray.getJSONObject(0).getString("location");
					hos_latitude= hosJArray.getJSONObject(0).getString("latitude");
					hos_longitude= hosJArray.getJSONObject(0).getString("longitude");
					hos_logo= hosJArray.getJSONObject(0).getString("logo");
					
					hospitl_name.setText(hos_name);
					blood_location.setText(hos_location);
					blood_logo.setImageBitmap(ImageRound.makeRoundedShape(ImageParse.base64ToImage(hos_logo)));
					doctor_intent = new Intent(getApplicationContext(),
							DoctorsListActivity.class);	
					contact_intent = new Intent(getApplicationContext(),
							ContactViewActivity.class);	
					doctor_intent.putExtra("blood_id", hos_id);
					contact_intent.putExtra("email", hos_email);
					contact_intent.putExtra("website", hos_website);
					contact_intent.putExtra("phone", hos_phone);
					contact_intent.putExtra("location", hos_location);
					contact_intent.putExtra("latitude", hos_latitude);
					contact_intent.putExtra("longitude", hos_longitude);
					contact_intent.putExtra("name", hos_name);
					
//					doctorTab = bloodTabHost.newTabSpec("Doctors")
//							.setIndicator("Doctor's").setContent(doctor_intent);
					contactTab = bloodTabHost.newTabSpec("Contact")
							.setIndicator("Contact").setContent(contact_intent);
//					bloodTabHost.addTab(doctorTab);
					bloodTabHost.addTab(contactTab);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			default:
				break;
			}
		};
	};

}
