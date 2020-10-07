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
public class ClinicActivity extends TabActivity {
	String hos_id, hos_name, hos_email, hos_website, hos_phone, hos_location,
			hos_latitude, hos_longitude,hos_logo,clinicRsesponse;
	ImageView clinic_logo;
	TextView hospitl_name, clinic_location;
	private TabHost clinicTabHost;
	TabHost.TabSpec doctorTab, contactTab;
	Intent doctor_intent,contact_intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clinic);
		Intent intent = getIntent();
		hos_id = intent.getStringExtra("clinic_id");
		hospitl_name = (TextView) findViewById(R.id.clinic_name);
		clinic_location = (TextView) findViewById(R.id.clinic_location);
		clinic_logo = (ImageView) findViewById(R.id.clinic_logo);
		clinicTabHost = getTabHost();
		
		Alerts.progressDialog(ClinicActivity.this, "Loading..");
		clinicThread clinicThread = new clinicThread();
		clinicThread.start();

	}

	protected class clinicThread extends Thread {
		protected final static int HOS_SUCCESS = 1000;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			clinicRsesponse = WebService.getClinicByID(hos_id);
			responseHandler.sendEmptyMessage(HOS_SUCCESS);
			super.run();
		}
	}

	private Handler responseHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case clinicThread.HOS_SUCCESS: {
				Alerts.progressDialogClose();
				try {
					JSONArray hosJArray = new JSONArray(clinicRsesponse);
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
					clinic_location.setText(hos_location);
					clinic_logo.setImageBitmap(ImageRound.makeRoundedShape(ImageParse.base64ToImage(hos_logo)));
					doctor_intent = new Intent(getApplicationContext(),
							DoctorsListActivity.class);	
					contact_intent = new Intent(getApplicationContext(),
							ContactViewActivity.class);	
					doctor_intent.putExtra("clinic_id", hos_id);
					contact_intent.putExtra("email", hos_email);
					contact_intent.putExtra("website", hos_website);
					contact_intent.putExtra("phone", hos_phone);
					contact_intent.putExtra("location", hos_location);
					contact_intent.putExtra("latitude", hos_latitude);
					contact_intent.putExtra("longitude", hos_longitude);
					contact_intent.putExtra("name", hos_name);
					
//					doctorTab = clinicTabHost.newTabSpec("Doctors")
//							.setIndicator("Doctor's").setContent(doctor_intent);
					contactTab = clinicTabHost.newTabSpec("Contact")
							.setIndicator("Contact").setContent(contact_intent);
//					clinicTabHost.addTab(doctorTab);
					clinicTabHost.addTab(contactTab);
					
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
