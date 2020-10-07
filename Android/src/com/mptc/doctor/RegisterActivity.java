package com.mptc.doctor;

import java.util.ArrayList;
import java.util.Calendar;

import com.mptc.doctor.alerts.Alerts;
import com.mptc.doctor.validations.FormValidation;
import com.mptc.doctor.webservice.ImageParse;
import com.mptc.doctor.webservice.WebService;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	EditText input_name, input_email, input_password, input_re_password,
			input_phone, input_place, input_city, input_state, input_district,
			input_dob;
	ImageView input_avatar;
	Spinner input_blood;
	RadioGroup input_gender;
	Button input_submit;
	String name, email, password, re_password, phone, place, city, avatar,
			state, gender, district, dob;
	final String BLOOD_HIND = "Select Blood Group";
	final int SELECT_PHOTO = 100, CROP_PHOTO = 1001;
	String signupResponse, encodedAvatar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		input_name = (EditText) findViewById(R.id.reg_name);
		input_email = (EditText) findViewById(R.id.reg_email);
		input_password = (EditText) findViewById(R.id.reg_password);
		input_re_password = (EditText) findViewById(R.id.reg_re_password);
		input_phone = (EditText) findViewById(R.id.reg_phone);
		input_place = (EditText) findViewById(R.id.reg_place);
		input_city = (EditText) findViewById(R.id.reg_city);
		input_avatar = (ImageView) findViewById(R.id.reg_avatar);
		input_state = (EditText) findViewById(R.id.reg_state);
		input_district = (EditText) findViewById(R.id.reg_district);
		input_dob = (EditText) findViewById(R.id.reg_dob);
		input_blood = (Spinner) findViewById(R.id.reg_blood);
		input_gender = (RadioGroup) findViewById(R.id.reg_gender);
		input_avatar.setOnClickListener(chooseAvatar);
		input_submit = (Button) findViewById(R.id.reg_submit);
		ArrayAdapter<String> blood_adapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.spinner_simple_layout);
		blood_adapter.setDropDownViewResource(R.layout.spinner_simple_layout);
		blood_adapter.add(BLOOD_HIND);
		blood_adapter.add("A+");
		blood_adapter.add("A-");
		blood_adapter.add("B+");
		blood_adapter.add("B-");
		blood_adapter.add("AB+");
		blood_adapter.add("AB-");
		blood_adapter.add("O+");
		blood_adapter.add("O-");
		input_blood.setAdapter(blood_adapter);
		input_dob.setOnFocusChangeListener(datePic);
		input_submit.setOnClickListener(submitForm);
	}

	public OnFocusChangeListener datePic = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(hasFocus){
			final Calendar cal = Calendar.getInstance();
			final int day = cal.get(Calendar.DAY_OF_MONTH);
			final int month = cal.get(Calendar.MONTH);
			final int year = cal.get(Calendar.YEAR);
			DatePickerDialog dateDialog = new DatePickerDialog(
					RegisterActivity.this, new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int cur_year,
								int cur_month, int cur_day) {
							// TODO Auto-generated method stub
							String date_of_birth = cur_day + "/"
									+ (cur_month + 1) + "/" + cur_year;
							input_dob.setText(date_of_birth);
						}
					}, year, month, day);
			dateDialog.show();
			}
		}

	};
	public OnClickListener submitForm = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			name = input_name.getText().toString();
			email = input_email.getText().toString();
			password = input_password.getText().toString();
			re_password = input_re_password.getText().toString();
			phone = input_phone.getText().toString();
			place = input_place.getText().toString();
			city = input_city.getText().toString();
			state = input_state.getText().toString();
			district = input_district.getText().toString();
			dob = input_dob.getText().toString();
			gender = ((RadioButton) findViewById(input_gender
					.getCheckedRadioButtonId())).getText().toString();
			
			String avatarDelete = avatar;
			encodedAvatar = ImageParse.imageToBase64(avatar);
			Alerts.progressDialog(RegisterActivity.this,
					"Signing up....plase wait");
			
			FormValidation fVaild = new FormValidation(getApplicationContext());
			
			SignupThread sign_thread = new SignupThread();
			sign_thread.start();
		}
	};
	public OnClickListener chooseAvatar = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent avatar_choose_intent = new Intent(Intent.ACTION_PICK);
			avatar_choose_intent.setType("image/*");
			startActivityForResult(avatar_choose_intent, SELECT_PHOTO);
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent returnData) {
		if (resultCode == RESULT_OK && returnData != null) {
			switch (requestCode) {
			case SELECT_PHOTO: {
				Uri selectedImage = returnData.getData();
				Intent crop_photo = new Intent("com.android.camera.action.CROP");
				crop_photo.setData(selectedImage);
				startActivityForResult(crop_photo, CROP_PHOTO);
				break;
			}
			case CROP_PHOTO: {
				Uri croped_photo = returnData.getData();
				String[] filePathColoumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(croped_photo,
						filePathColoumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColoumn[0]);
				avatar = cursor.getString(columnIndex);
				cursor.close();
				input_avatar.setImageBitmap(BitmapFactory.decodeFile(avatar));
				break;
			}

			default:
				break;
			}
		}
	};
	
	
	
	private class SignupThread extends Thread {
		public static final int SIGN_SUCCESS = 1;

		@Override
		public void run() {
			// Thread for Signup
			try {
				signupResponse = WebService.userSignup(name, email, password, gender, dob, place, city, state, district, phone, encodedAvatar);
				responseHandler.sendEmptyMessage(SIGN_SUCCESS);
			} catch (Exception e) {
				// TODO: handle exception
			}
			super.run();
		}
	}

	private Handler responseHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SignupThread.SIGN_SUCCESS:
				Alerts.progressDialogClose();
				Toast.makeText(getApplicationContext(), signupResponse,
						Toast.LENGTH_LONG).show();
				break;

			default:
				break;
			}
		};
	};
}
