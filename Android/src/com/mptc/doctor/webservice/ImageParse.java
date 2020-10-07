package com.mptc.doctor.webservice;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ImageParse {
	public static String imageToBase64(String imagePath){
		
		Bitmap bm = BitmapFactory.decodeFile(imagePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100,baos);
		byte [] imageByteArray =baos.toByteArray();
		String base64image = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
		//System.out.println(base64image);
		return base64image;
	}
public static Bitmap base64ToImage(String base64string){
		
	byte[] decodedString = Base64.decode(base64string, Base64.NO_WRAP);
	Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
	return decodedByte;
	}
}
