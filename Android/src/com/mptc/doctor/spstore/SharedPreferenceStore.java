package com.mptc.doctor.spstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceStore {
	public final static String SHARED_PREFS = "PreferenceStore";
	private Editor edit;
	private SharedPreferences settings;
	public SharedPreferenceStore(Context context){
		settings = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
	}
	public void setLogin(String id,String name){
		edit = settings.edit();
		edit.putString("LOG_ID", id);
		edit.putString("LOG_NAME", name);
		edit.commit();
	}
	public String getLoginID(){
		return settings.getString("LOG_ID","");
	}
	public String getLoginName(){
		return settings.getString("LOG_NAME","");
	}
	public void removeLoginStatus(){
		edit = settings.edit();
		edit.putString("LOG_ID", "");
		edit.putString("LOG_NAME", "");
		edit.commit();
	}

}
