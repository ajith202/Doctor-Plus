package com.mptc.doctor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mptc.doctor.alerts.Alerts;
import com.mptc.doctor.animations.Animations;
import com.mptc.doctor.spstore.SharedPreferenceStore;
import com.mptc.doctor.webservice.WebService;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SplashScreenActivity extends Activity {
	LinearLayout splash_layout;
	Button signup_student_btn, login_btn;
	EditText input_username, input_password;
	Animation zoom_in_anim, left_in;
	ImageView splash_logo;
	String login_resoponse, username, password;
	SharedPreferenceStore spStore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash_screen);
		spStore = new SharedPreferenceStore(getApplicationContext());
		splash_layout = (LinearLayout) findViewById(R.id.splash_layout);
		signup_student_btn = (Button) findViewById(R.id.splash_signup_student_btn);
		login_btn = (Button) findViewById(R.id.splash_login_btn);
		splash_logo = (ImageView) findViewById(R.id.splash_logo);
		Animations.setAnimation(getApplicationContext(), splash_layout,
				R.anim.anim_splash_bg_zoom_in);
		Animations.setAnimation(getApplicationContext(), signup_student_btn,
				R.anim.anim_splash_left_in);
		Animations.setAnimation(getApplicationContext(), login_btn,
				R.anim.anim_splash_right_in);
		signup_student_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						RegisterActivity.class));
			}
		});
		login_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final Dialog loginDialog = new Dialog(SplashScreenActivity.this);
				//loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				loginDialog.setTitle("Login");
				loginDialog.setContentView(R.layout.dialog_login);
				input_username = (EditText) loginDialog
						.findViewById(R.id.login_username);
				input_password = (EditText) loginDialog
						.findViewById(R.id.login_password);
				Button login_btn = (Button) loginDialog
						.findViewById(R.id.login_login_btn);
				loginDialog.setCancelable(true);
				login_btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Gets user name and password and push them to
						// server for checking
						username = input_username.getText().toString();
						password = input_password.getText().toString();
						Alerts.progressDialog(SplashScreenActivity.this,
								"Checking.....Please wait !");
						LoginThread login_thread = new LoginThread();
						login_thread.start();
					}
				});

				loginDialog.show();
			}
		});
		String loged_user_id = spStore.getLoginID();
		if (!loged_user_id.equals("")) {
			startActivity(new Intent(getApplicationContext(),
					HomeActivity.class));
			finish();
		}
	}

	private class LoginThread extends Thread {
		public static final int LOG_SUCCESS = 1;

		@Override
		public void run() {
			// TODO call webservice and gets retrn value
			login_resoponse = WebService.login(username, password);
			responseHandler.sendEmptyMessage(LOG_SUCCESS);

		}
	}

	private Handler responseHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LoginThread.LOG_SUCCESS: {
				System.out.println(login_resoponse);
				// Alerts.progressDialogClose();
				if (login_resoponse.equals("error")) {
					Toast.makeText(getApplicationContext(),
							"Login Failed ! Please try agian",
							Toast.LENGTH_LONG).show();
					input_username.setText("");
					input_password.setText("");
				} else {
					try {
						JSONObject Jobj = new JSONObject(login_resoponse);
						String loged_id = Jobj.getString("id");
						String loged_name = Jobj.getString("name");
						spStore.setLogin(loged_id, loged_name);
						startActivity(new Intent(getApplicationContext(),
								HomeActivity.class));
						finish();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Alerts.progressDialogClose();
				break;
			}
			default:
				break;
			}
		};
	};

}
