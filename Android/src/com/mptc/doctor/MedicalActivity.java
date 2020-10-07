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
public class MedicalActivity extends TabActivity {
	String hos_id, hos_name, hos_email, hos_website, hos_phone, hos_location,
			hos_latitude, hos_longitude,hos_logo,medicalRsesponse;
	ImageView medical_logo;
	TextView hospitl_name, medical_location;
	private TabHost medicalTabHost;
	TabHost.TabSpec doctorTab, contactTab;
	Intent doctor_intent,contact_intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medical);
		Intent intent = getIntent();
		hos_id = intent.getStringExtra("medical_id");
		hospitl_name = (TextView) findViewById(R.id.medical_name);
		medical_location = (TextView) findViewById(R.id.medical_location);
		medical_logo = (ImageView) findViewById(R.id.medical_logo);
		medicalTabHost = getTabHost();
		
		Alerts.progressDialog(MedicalActivity.this, "Loading..");
		medicalThread medicalThread = new medicalThread();
		medicalThread.start();

	}

	protected class medicalThread extends Thread {
		protected final static int HOS_SUCCESS = 1000;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			medicalRsesponse = WebService.getMedicalByID(hos_id);
			responseHandler.sendEmptyMessage(HOS_SUCCESS);
			super.run();
		}
	}

	private Handler responseHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case medicalThread.HOS_SUCCESS: {
				Alerts.progressDialogClose();
				try {
					JSONArray hosJArray = new JSONArray(medicalRsesponse);
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
					medical_location.setText(hos_location);
					medical_logo.setImageBitmap(ImageRound.makeRoundedShape(ImageParse.base64ToImage(hos_logo)));
					doctor_intent = new Intent(getApplicationContext(),
							DoctorsListActivity.class);	
					contact_intent = new Intent(getApplicationContext(),
							ContactViewActivity.class);	
					doctor_intent.putExtra("medical_id", hos_id);
					contact_intent.putExtra("email", hos_email);
					contact_intent.putExtra("website", hos_website);
					contact_intent.putExtra("phone", hos_phone);
					contact_intent.putExtra("location", hos_location);
					contact_intent.putExtra("latitude", hos_latitude);
					contact_intent.putExtra("longitude", hos_longitude);
					contact_intent.putExtra("name", hos_name);
					
//					doctorTab = medicalTabHost.newTabSpec("Doctors")
//							.setIndicator("Doctor's").setContent(doctor_intent);
					contactTab = medicalTabHost.newTabSpec("Contact")
							.setIndicator("Contact").setContent(contact_intent);
//					medicalTabHost.addTab(doctorTab);
					medicalTabHost.addTab(contactTab);
					
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
