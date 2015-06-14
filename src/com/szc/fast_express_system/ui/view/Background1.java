package com.szc.fast_express_system.ui.view;


import android.graphics.drawable.GradientDrawable;



public class Background1 extends GradientDrawable{

	public Background1(){
		super(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0xFFededed,0xFFededed,0xFFededed});
		init();
	}
	
	public void init(){
		this.setCornerRadii(new float[]{0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f});
	}
	
}
