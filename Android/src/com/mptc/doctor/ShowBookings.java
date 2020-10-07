package com.mptc.doctor;

import org.json.JSONArray;

import com.mptc.doctor.Adapters.BookingsListAdapter;
import com.mptc.doctor.spstore.SharedPreferenceStore;
import com.mptc.doctor.webservice.WebService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ShowBookings extends Activity {
	SharedPreferenceStore spstore;
	ListView bookingsList;
	String bookingsGetResponce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		spstore = new SharedPreferenceStore(getApplicationContext());
		bookingsList = new ListView(getApplicationContext());
		setContentView(bookingsList);

		BookingGetThread bookGet = new BookingGetThread();
		bookGet.start();
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
		String[] hospital_logo, hospital_name, doctor_name, book_session,
				book_time, book_date, book_id;

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BookingGetThread.BOOK_GET_SUCCESS: {
				// Alerts.progressDialogClose();
				if (!bookingsGetResponce.equals("null")) {
					try {
						JSONArray bookingsArray = new JSONArray(
								bookingsGetResponce);
						int bookings_array_lenth = bookingsArray.length();
						hospital_logo = new String[bookings_array_lenth];
						hospital_name = new String[bookings_array_lenth];
						doctor_name = new String[bookings_array_lenth];
						book_session = new String[bookings_array_lenth];
						book_time = new String[bookings_array_lenth];
						book_date = new String[bookings_array_lenth];
						book_id = new String[bookings_array_lenth];
						for (int i = 0; i < bookings_array_lenth; i++) {
							hospital_logo[i] = bookingsArray.getJSONObject(i)
									.getString("hospital_logo");
							hospital_name[i] = bookingsArray.getJSONObject(i)
									.getString("hospital_name");
							doctor_name[i] = bookingsArray.getJSONObject(i)
									.getString("doctor_name");
							book_session[i] = bookingsArray.getJSONObject(i)
									.getString("session");
							book_time[i] = bookingsArray.getJSONObject(i)
									.getString("time");
							book_date[i] = bookingsArray.getJSONObject(i)
									.getString("date");
							book_id[i] = bookingsArray.getJSONObject(i)
									.getString("id");
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					BookingsListAdapter adapter = new BookingsListAdapter(
							ShowBookings.this, hospital_name, hospital_logo,
							doctor_name, book_session, book_time, book_date);
					// search_result_list=(ListView)findViewById(R.id.list);
					bookingsList.setAdapter(adapter);
					bookingsList
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									Intent i = new Intent(
											getApplicationContext(),
											BookShowActivity.class);
									i.putExtra("hospital_logo",
											hospital_logo[position]);
									i.putExtra("hospital_name",
											hospital_name[position]);
									i.putExtra("doctor_name",
											doctor_name[position]);
									i.putExtra("doctor_name",
											doctor_name[position]);
									i.putExtra("book_id", book_id[position]);
									i.putExtra("book_time", book_time[position]);
									i.putExtra("book_date", book_date[position]);
									i.putExtra("book_session",
											book_session[position]);
									startActivity(i);
									// Toast.makeText(getApplicationContext(),
									// hospital_ids[position],
									// Toast.LENGTH_LONG).show();
								}
							});

				} else {
					Toast.makeText(getApplicationContext(),
							"No Bookings Found", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),
							HomeActivity.class));
					finish();
				}

				break;
			}

			default:
				break;
			}
		};
	};

}
