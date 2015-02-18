package com.demo.instagram;

import android.app.Activity;
import android.os.Bundle;

import com.demo.instagram.common.ZoomableImageView;

public class ImageActivity extends Activity {

	private ZoomableImageView imageView;
	private String url = "";
	private InstagramDemoApp instagramApp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image);
		instagramApp = (InstagramDemoApp) getApplicationContext();
		// Get intent data
		if (getIntent() != null) {
			url = getIntent().getStringExtra(
					getString(R.string.key_intent_highresurl));
		}

		if (url.length() > 0) {

			imageView = (ZoomableImageView) findViewById(R.id.activity_full_image_img_view);
			instagramApp.imageLoader.displayImage(url,
					imageView, instagramApp.getImageOptions());
		} 
	}
}
