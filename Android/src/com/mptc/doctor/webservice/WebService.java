package com.mptc.doctor.webservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class WebService {
	static String response;
//	static String postURL = "http://doctor.codemagos.in/web_service.php";
	static String postURL = "http:// /Doctor+/web_service.php";

	protected final static String startWebservice(List<NameValuePair> params) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(postURL);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					HTTP.UTF_8);
			post.setEntity(entity);
			HttpResponse responsePost = client.execute(post);
			HttpEntity responseEntity = responsePost.getEntity();
			response = EntityUtils.toString(responseEntity);
			response = response.trim();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Catched !!!" + e.getMessage());
		}
		return response;

	}

	public static String userSignup(String name, String email,
			String password, String gender, String dob, String place,
			String city, String state, String district, String phone,
			String avatar) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", ""));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("gender", gender));
		params.add(new BasicNameValuePair("dob", dob));
		params.add(new BasicNameValuePair("place", place));
		params.add(new BasicNameValuePair("city", city));
		params.add(new BasicNameValuePair("district", district));
		params.add(new BasicNameValuePair("state", state));
		params.add(new BasicNameValuePair("phone", phone));
		params.add(new BasicNameValuePair("avatar", avatar));
		return startWebservice(params);
	}

	public static String login(String username, String password) {
		System.out.println("Reached login");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "login"));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		return startWebservice(params);
	}
	public static String searchLocation(String type, String key,Double latitude,double longitude) {
		System.out.println("Reached Search");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "search"));
		params.add(new BasicNameValuePair("type", type));
		params.add(new BasicNameValuePair("key", key));
		params.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
		params.add(new BasicNameValuePair("longitude", Double.toString(longitude)));
		return startWebservice(params);
	}
	public static String getHospitalByID(String hospital_id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "hospital_get_by_id"));
		params.add(new BasicNameValuePair("id", hospital_id));
		return startWebservice(params);
	}
	public static String getBloodByID(String blood_id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "blood_get_by_id"));
		params.add(new BasicNameValuePair("id", blood_id));
		return startWebservice(params);
	}
	public static String getClinicByID(String clinic_id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "clinic_get_by_id"));
		params.add(new BasicNameValuePair("id", clinic_id));
		return startWebservice(params);
	}
	public static String getMedicalByID(String medical_id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "medical_get_by_id"));
		params.add(new BasicNameValuePair("id", medical_id));
		return startWebservice(params);
	}
	public static String getHospitalDoctors(String hospital_id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "hospital_doctor_get"));
		params.add(new BasicNameValuePair("id", hospital_id));
		return startWebservice(params);
	}
	public static String makeAppoinment(String hospital_id,String doctor_id,String user_id,String time,String date) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "booking"));
		params.add(new BasicNameValuePair("hospital_id", hospital_id));
		params.add(new BasicNameValuePair("doctor_id", doctor_id));
		params.add(new BasicNameValuePair("user_id", user_id));
		params.add(new BasicNameValuePair("session", time));
		params.add(new BasicNameValuePair("date", date));
		return startWebservice(params);
	}
	public static String getBookings(String user_id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("action", "get_bookings"));
		params.add(new BasicNameValuePair("user_id", user_id));
		return startWebservice(params);
	}
}
