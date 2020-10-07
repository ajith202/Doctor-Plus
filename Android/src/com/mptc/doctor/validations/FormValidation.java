package com.mptc.doctor.validations;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;
public class FormValidation{
	
		static Context context;

		public FormValidation(Context con) {
			// TODO Auto-generated constructor stub
			context = con;
		}

		static String PASS_STRENGTH;

		public Boolean isEmpty(String str) {
			if (str.length() != 0) {
				return false;
			} else {
				return true;
			}
		}
	public boolean isEmpty(String[] fields){
		int emptyFields = 0;
		for (int i = 0; i < fields.length; i++) {
			if (isEmpty(fields[i])) {
				emptyFields++;
			}
		}
		if(emptyFields != 0)
			return true;
		else
			return false;
		
	}
		public boolean isPasswordMatch(EditText password,
				EditText re_password) {
			System.out.println(password.getText().toString());
			System.out.println(re_password.getText().toString());
			if (password.getText().toString()
					.equals(re_password.getText().toString())) {
				if (password.length() < 5) {
					Toast.makeText(context, "Please Use a Strong password",
							Toast.LENGTH_LONG).show();
					password.setText("");
					re_password.setText("");
					return false;
				} else {
					return true;
				}
			} else {
				Toast.makeText(context, "Passwords Are not Maching",
						Toast.LENGTH_LONG).show();
				password.setText("");
				re_password.setText("");
				return false;
			}
		}

		public boolean isEmailValid(String email) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			CharSequence inputStr = email;
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(inputStr);
			if (matcher.matches()) {
				return true;
			}
			Toast.makeText(context, "Please enter a valid email address like yourname@yourdomain.com", 2000).show();
			return false;
		}
		public boolean isPhoneValid(String phone){
			if(phone.length() < 10){
			Toast.makeText(context, "Please enter a valid phone number", 2000).show();
				return false;
			}
			return true;
		}
	}


