package com.mptc.doctor;

import org.json.JSONArray;
import org.json.JSONException;
import com.mptc.doctor.Adapters.SearchResultListAdapter;
import com.mptc.doctor.alerts.Alerts;
import com.mptc.doctor.spstore.SharedPreferenceStore;
import com.mptc.doctor.webservice.WebService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class HospitalSearchAvtivity extends Activity {

	ListView search_result_list;
	AutoCompleteTextView input_hospital_place;
	ImageView hospital_search_gps, hospital_search_place;
	SharedPreferenceStore spstore;
	String place_search_key = "", place_search_response;
	double latitude = 0, longitude = 0;
	Boolean got_location = false;
	String[] hospital_names, hospital_ids, hospital_distance, hospital_logos,
			hospital_locations;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital_search);
		spstore = new SharedPreferenceStore(getApplicationContext());
		hospital_search_gps = (ImageView) findViewById(R.id.hospital_search_gps);
		hospital_search_place = (ImageView) findViewById(R.id.hospital_search_place);
		search_result_list = (ListView) findViewById(R.id.hospital_search_results);
		input_hospital_place = (AutoCompleteTextView) findViewById(R.id.hospital_place_input);
		hospital_search_gps.setOnClickListener(searchByLocation);
		hospital_search_place.setOnClickListener(searchByPlace);

	}

	protected OnClickListener searchByLocation = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			got_location = false;
			place_search_key = ""; // clearing the place key variable to get
									// results only from GPS
			LocationManager loc_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			LocationListener loc_listener = new MyLocationListener();
			loc_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
					0, loc_listener);
			Alerts.progressDialog(HospitalSearchAvtivity.this,
					"Fetching Location...!");
			System.out.println("&&&");
		}
	};
	protected OnClickListener searchByPlace = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			place_search_key = input_hospital_place.getText().toString();
			if (!place_search_key.equals("")) {
				Alerts.progressDialog(HospitalSearchAvtivity.this,
						"Searching...!");
				try {
					PlaceGetThread placethread = new PlaceGetThread();
					placethread.start();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(),
							"Network Connection not available",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"Please enter a Place to Search", Toast.LENGTH_LONG)
						.show();
			}
		}
	};

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
			Toast.makeText(getApplicationContext(), "Loging Out..",
					Toast.LENGTH_LONG).show();
			spstore.removeLoginStatus();
			startActivity(new Intent(getApplicationContext(),
					SplashScreenActivity.class));
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	protected class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if (location != null && got_location != true) {
				System.out.println("->in gps");
				got_location = true;
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				System.out.println(latitude + "\n" + longitude);
				Alerts.progressDialogClose();
				Alerts.progressDialog(HospitalSearchAvtivity.this,
						"Fetching Data...!");

				try {
					PlaceGetThread placethread = new PlaceGetThread();
					placethread.start();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(),
							"Network Connection not available",
							Toast.LENGTH_LONG).show();
				}
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText(
					getApplicationContext(),
					"You cant make use of this feature!Please make sure that the GPS is turned on",
					Toast.LENGTH_LONG).show();
			Alerts.progressDialogClose();
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}

	}

	private class PlaceGetThread extends Thread {
		public static final int SEARCH_SUCCESS = 1;

		@Override
		public void run() {
			// TODO thread to get places based on locaton or key (Search)

			System.out.println("->in thred");
			place_search_response = WebService.searchLocation("hospital",
					place_search_key, latitude, longitude);
			responceHandler.sendEmptyMessage(SEARCH_SUCCESS);

			super.run();
		}
	}

	private Handler responceHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PlaceGetThread.SEARCH_SUCCESS: {
				Alerts.progressDialogClose();
				if (!place_search_response.equals("null")) {
					try {

						JSONArray jArray = new JSONArray(place_search_response);
						int jsonArrayLength = jArray.length();
						System.out.println(jsonArrayLength);
						hospital_ids = new String[jsonArrayLength];
						hospital_names = new String[jsonArrayLength];
						hospital_distance = new String[jsonArrayLength];
						hospital_logos = new String[jsonArrayLength];
						hospital_locations = new String[jsonArrayLength];

						for (int i = 0; i < jArray.length(); i++) {
							hospital_ids[i] = jArray.getJSONObject(i)
									.getString("id");
							hospital_names[i] = jArray.getJSONObject(i)
									.getString("name");
							hospital_distance[i] = jArray.getJSONObject(i)
									.getString("distance");
							hospital_logos[i] = jArray.getJSONObject(i)
									.getString("logo");
							hospital_locations[i] = jArray.getJSONObject(i)
									.getString("location");
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						System.out.println("cached - > " + e.getMessage());
						e.printStackTrace();
					}
					SearchResultListAdapter adapter = new SearchResultListAdapter(
							HospitalSearchAvtivity.this, hospital_ids,
							hospital_names, hospital_distance, hospital_logos);
					// search_result_list=(ListView)findViewById(R.id.list);
					search_result_list.setAdapter(adapter);
					search_result_list
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									Intent i = new Intent(
											getApplicationContext(),
											HospitalActivity.class);
									i.putExtra("hospital_id",
											hospital_ids[position]);
									startActivity(i);
									// Toast.makeText(getApplicationContext(),
									// hospital_ids[position],
									// Toast.LENGTH_LONG).show();
								}
							});
				} else {
					Toast.makeText(getApplicationContext(),
							"No Nearby Hospitals", Toast.LENGTH_LONG).show();
				}
				break;
			}

			default:
				break;
			}
		};
	};
}
