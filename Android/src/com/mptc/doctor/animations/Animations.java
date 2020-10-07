package com.mptc.doctor.animations;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Animations {
	static Animation anim;
	public static void setAnimation(Context context,View view,int animId){
	anim = AnimationUtils.loadAnimation(context, animId);
	view.startAnimation(anim);
//	return anim;
	}
	
	
	
}
