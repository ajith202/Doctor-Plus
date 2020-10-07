package com.mptc.doctor;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;

import com.mptc.doctor.Adapters.DoctorsListAdapter;
import com.mptc.doctor.spstore.SharedPreferenceStore;
import com.mptc.doctor.webservice.WebService;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class DoctorsListActivity extends Activity {
	String HOSPITAL_ID, doctorGetResponse, bookingResponse, user_id, doctor_id,
			book_time, book_date;
	ListView doctorList;
	String[] id, name, qualification, department, image;
	Calendar calendar;
	int cur_year, cur_day, cur_month;
	Dialog bookDialog;
	Spinner book_session;
	EditText input_book_date;
	Button input_book_submit;
	SharedPreferenceStore spStore;
	private String[] arraySpinner;
	final static String SESSION_HINT = "Select a Time Slot form Available Time's";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		spStore = new SharedPreferenceStore(getApplicationContext());
		Intent intent = getIntent();
		HOSPITAL_ID = intent.getStringExtra("hospital_id");
		doctorList = new ListView(getApplicationContext());
		setContentView(doctorList);
		// Alerts.progressDialog(DoctorsListActivity.this, "Loading...");
		calendar = Calendar.getInstance();
		cur_year = calendar.get(Calendar.YEAR);
		cur_month = calendar.get(Calendar.MONTH);
		cur_day = calendar.get(Calendar.DAY_OF_MONTH);
		bookDialog = new Dialog(DoctorsListActivity.this);
		bookDialog.setContentView(R.layout.dialog_booking_check);
		bookDialog.setTitle("Appointment Maker");
		book_session = (Spinner) bookDialog
				.findViewById(R.id.book_check_session);
		input_book_date = (EditText) bookDialog
				.findViewById(R.id.book_check_date);
		input_book_submit = (Button) bookDialog
				.findViewById(R.id.book_check_submit);

		input_book_date.setOnClickListener(chooseDate);
		input_book_submit.setOnClickListener(bookAppointment);
		DoctorThread doctorThread = new DoctorThread();
		doctorThread.start();

		this.arraySpinner = new String[] { SESSION_HINT, "Morning", "Evening" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, arraySpinner);
		book_session.setAdapter(adapter);
	}

	protected OnClickListener chooseDate = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			DatePickerDialog datePic = new DatePickerDialog(
					DoctorsListActivity.this, new OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker arg0, int year,
								int month, int day) {
							// TODO select selected date from datepicker
							if (year > cur_year + 1) {
								Toast.makeText(
										getApplicationContext(),
										"You cant make Appoinment in futre", Toast.LENGTH_LONG)
										.show();
							} else if (year < cur_year) {
								Toast.makeText(
										getApplicationContext(),
										"You cant make appoinment in Past !",
										Toast.LENGTH_LONG).show();
							} else if (day < cur_day || day == cur_day){
								Toast.makeText(
										getApplicationContext(),
										"Appoinent only avail from day after today", Toast.LENGTH_LONG)
										.show();
							}else {
								String selected_date = year + "/"
										+ (month + 1) + "/" + day;
								input_book_date.setText(selected_date);
							}
						}
					}, cur_year, cur_month, cur_day);
			datePic.show();
		}
	};


	protected OnClickListener bookAppointment = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	class DoctorThread extends Thread {
		public static final int DOC_SUCESS = 1000;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			doctorGetResponse = WebService.getHospitalDoctors(HOSPITAL_ID);
			responseHandler.sendEmptyMessage(DOC_SUCESS);
			super.run();
		}
	}

	class BookingThread extends Thread {
		public static final int BOOK_SUCESS = 1001;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			bookingResponse = WebService.makeAppoinment(HOSPITAL_ID, doctor_id,
					user_id, book_time, book_date);
			responseHandler.sendEmptyMessage(BOOK_SUCESS);
			super.run();
		}
	}

	private Handler responseHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DoctorThread.DOC_SUCESS: {
				// Alerts.progressDialogClose();
				if (!doctorGetResponse.equals("null")) {
					try {
						JSONArray jArray = new JSONArray(doctorGetResponse);
						int jsonArrayLength = jArray.length();
						id = new String[jsonArrayLength];
						name = new String[jsonArrayLength];
						qualification = new String[jsonArrayLength];
						department = new String[jsonArrayLength];
						image = new String[jsonArrayLength];
						for (int i = 0; i < jArray.length(); i++) {
							id[i] = jArray.getJSONObject(i).getString("id");
							name[i] = jArray.getJSONObject(i).getString("name")
									+ " ( "
									+ jArray.getJSONObject(i).getString(
											"qualification") + " )";
							qualification[i] = jArray.getJSONObject(i)
									.getString("qualification");
							department[i] = jArray.getJSONObject(i).getString(
									"department");
							image[i] = jArray.getJSONObject(i).getString(
									"photo");
						}
						System.out.println(jsonArrayLength);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					DoctorsListAdapter adapter = new DoctorsListAdapter(
							DoctorsListActivity.this, id, name, qualification,
							department, image);
					doctorList.setAdapter(adapter);
					doctorList
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> adview,
										View arg1, int pos, long arg3) {
									// TODO gets which element in list view is
									// clicked
									doctor_id = id[pos];
									String selected_doctor_name = name[pos];
									makeHospitalAppoinment(); // function
																// to
																// make
																// Appoinment
								}
							});
				} else {
					Toast.makeText(getApplicationContext(), "No Doctors Found",
							Toast.LENGTH_LONG).show();
					doctorList
							.setBackgroundResource(R.drawable.no_doctor_found);
				}
				break;
			}

			case BookingThread.BOOK_SUCESS: {

				Toast.makeText(getApplicationContext(), "Sucesss",
						Toast.LENGTH_LONG).show();
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						DoctorsListActivity.this);
				if (bookingResponse.equals("Apponment Already Taken !")) {
					alertDialogBuilder.setMessage("Apponment Already Taken !");
					alertDialogBuilder.setNegativeButton("okay",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									startActivity(new Intent(
											getApplicationContext(),
											ShowBookings.class));
								}
							});

				} else if (bookingResponse.equals("Not available")) {
					alertDialogBuilder
							.setMessage("Appoinment not available in "
									+ book_time + " Session");
					alertDialogBuilder.setNegativeButton("okay",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
				} else {
					alertDialogBuilder
							.setMessage("Appoinment not available in "
									+ book_time + " Session");
					alertDialogBuilder.setPositiveButton("okay",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									startActivity(new Intent(
											getApplicationContext(),
											ShowBookings.class));
								}
							});
				}
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
				break;
			}
			default:
				break;
			}
		};
	};

	private void makeHospitalAppoinment() {
		bookDialog.show();
		final EditText input_book_date = (EditText) bookDialog
				.findViewById(R.id.book_check_date);
		Button book_submit = (Button) bookDialog
				.findViewById(R.id.book_check_submit);
		book_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				book_time = book_session.getSelectedItem().toString();
				book_date = input_book_date.getText().toString();
				user_id = spStore.getLoginID();
				if (book_time.equals(SESSION_HINT)) {
					Toast.makeText(getApplicationContext(),
							"Plase Select a Time Slot", Toast.LENGTH_LONG)
							.show();
				} else {
					BookingThread bookThread = new BookingThread();
					bookThread.start();
				}
			}
		});
	}
}
