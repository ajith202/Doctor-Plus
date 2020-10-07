package com.mptc.doctor.alerts;


import com.mptc.doctor.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class Alerts {
	static Dialog progressDialog;
	static String verify_code = "";
	public static void progressDialog(Context context, String message) {
		progressDialog = new Dialog(context);
		progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progressDialog.setContentView(R.layout.dialog_custom_progress);
		progressDialog.setCancelable(false);
		TextView msg = (TextView) progressDialog.findViewById(R.id.progress_dialog_message);
		msg.setText(message);
		progressDialog.show();
	}
	

	public static void progressDialogClose() {
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
	

	
	
	
}
