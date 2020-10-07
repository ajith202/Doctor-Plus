package com.mptc.doctor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GeoLocationActivity extends Activity {
TextView latitude,longitude;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_geo_location);

	latitude = (TextView) findViewById(R.id.geo_lan);
	longitude= (TextView) findViewById(R.id.geo_log);
	LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	LocationListener ll = new MyLocationListener();
	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
	
	
	}
	class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			Double lon = location.getLongitude();
			Double lat = location.getLatitude();
			longitude.setText(Double.toString(lon));
			latitude.setText(Double.toString(lat));
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "Please Turn on GPS", Toast.LENGTH_LONG).show();
			AlertDialog.Builder gpsDialogBuilder = new AlertDialog.Builder(GeoLocationActivity.this);
			gpsDialogBuilder.setMessage("Please Turn on GPS !");
			gpsDialogBuilder.setPositiveButton("Turn on", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
					intent.putExtra("enabled", true);
					sendBroadcast(intent);
					Toast.makeText(getApplicationContext(), "Turning GPS on....", Toast.LENGTH_LONG).show();
				}
			})
			.setNegativeButton("Cancel", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "You cant use this feature with out GPS", Toast.LENGTH_LONG).show();
					finish();
				}
			});
			AlertDialog gpsAlert = gpsDialogBuilder.create();
			gpsAlert.show();
			
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

}
