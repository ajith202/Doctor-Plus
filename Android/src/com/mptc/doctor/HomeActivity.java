package com.mptc.doctor;


import org.json.JSONArray;
import org.json.JSONException;

import com.mptc.doctor.Adapters.SearchResultListAdapter;
import com.mptc.doctor.alerts.Alerts;
import com.mptc.doctor.misc.ImageRound;
import com.mptc.doctor.spstore.SharedPreferenceStore;
import com.mptc.doctor.webservice.ImageParse;
import com.mptc.doctor.webservice.WebService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	ImageView hospital,blood_bank,medial_shop,clinic,booking_image;
	String bookingsGetResponce;
	SharedPreferenceStore spstore;
	TextView booking_name,booking_time,booking_doctor,home_bookings_count;
	LinearLayout show_bookings,bookings_wraper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		spstore = new SharedPreferenceStore(getApplicationContext());
		hospital = (ImageView) findViewById(R.id.home_hospital_icon);
		clinic = (ImageView) findViewById(R.id.home_clinic_icon);
		blood_bank = (ImageView) findViewById(R.id.home_bloodbank_icon);
		medial_shop = (ImageView) findViewById(R.id.home_medicalshop_icon);
		booking_image = (ImageView) findViewById(R.id.home_booking_image);
		booking_name = (TextView) findViewById(R.id.home_booking_name);
		booking_doctor = (TextView) findViewById(R.id.home_booking_doctor);
		booking_time = (TextView) findViewById(R.id.home_booking_time);
		show_bookings = (LinearLayout) findViewById(R.id.home_show_bookings);
		home_bookings_count = (TextView) findViewById(R.id.home_bookings_count);
		bookings_wraper = (LinearLayout) findViewById(R.id.home_bookings_wraper);
		hospital.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),HospitalSearchAvtivity.class));
			}
		});
		clinic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),ClinicSearchAvtivity.class));
			}
		});
		blood_bank.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),BloodSearchAvtivity.class));
			}
		});
		medial_shop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),MedicalSearchAvtivity.class));
			}
		});
		show_bookings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),ShowBookings.class));
				finish();
			}
		});
		
		BookingGetThread bookGet = new BookingGetThread();
		bookGet.start();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		MenuInflater menu_inflator = getMenuInflater();
		menu_inflator.inflate(R.menu.menu_settings, menu);

		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_logout:
			Toast.makeText(getApplicationContext(),"Loging Out..",Toast.LENGTH_LONG).show();
			spstore.removeLoginStatus();
			startActivity(new Intent(getApplicationContext(),SplashScreenActivity.class));
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private class BookingGetThread extends Thread {
		public static final int BOOK_GET_SUCCESS = 1;

		@Override
		public void run() {
			// TODO thread to get places based on locaton or key (Search)

			System.out.println("->in thred");
			bookingsGetResponce = WebService.getBookings(spstore.getLoginID());
			responceHandler.sendEmptyMessage(BOOK_GET_SUCCESS);

			super.run();
		}
	}

	private Handler responceHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BookingGetThread.BOOK_GET_SUCCESS: {
//				Alerts.progressDialogClose();
				if(!bookingsGetResponce.equals("null")){
					try {
						JSONArray bookingsArray = new JSONArray(bookingsGetResponce);
						int bookings_array_lenth = bookingsArray.length();
						home_bookings_count.setText(bookings_array_lenth-1+" More");
						booking_image.setImageBitmap(ImageRound.makeRoundedShape(ImageParse.base64ToImage(bookingsArray.getJSONObject(0).getString("hospital_logo"))));
						booking_name.setText(bookingsArray.getJSONObject(0).getString("hospital_name"));
						booking_time.setText(bookingsArray.getJSONObject(0).getString("session")+" "+bookingsArray.getJSONObject(0).getString("time"));
						booking_doctor.setText(bookingsArray.getJSONObject(0).getString("doctor_name"));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}else{
					bookings_wraper.setVisibility(LinearLayout.INVISIBLE);
					home_bookings_count.setText("0 Bookings");
				}
				break;
			}

			default:
				break;
			}
		};
	};

	
}
