package com.demo.instagram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.demo.instagram.common.ZoomableImageView;

/**
 * Purpose:This class is use for display larger image in view and also handle
 * zoom effect.
 * 
 * @author Vandit Patel
 * @version 1.0
 * @date 18/02/15
 */
public class DisplayFullImageActivity extends Activity {

	/**
	 * Declaration of zoomableimageview class to handle zoom feature
	 */
	private ZoomableImageView imageView;
	/**
	 * Url to load image from instagram
	 */
	private String url = "";
	/**
	 * Declaration of instagram demo app.
	 */
	private InstagramDemoApp instagramApp;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_fullimage);
		instagramApp = (InstagramDemoApp) getApplicationContext();
		// Get intent data
		if (getIntent() != null) {
			url = getIntent().getStringExtra(
					getString(R.string.key_intent_highresurl));
		}

		if (url.length() > 0) {

			imageView = (ZoomableImageView) findViewById(R.id.activity_display_fullimage_img_view);
		}
	}

	public class LoadImageTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(DisplayFullImageActivity.this);
			super.onPreExecute();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onCancelled()
		 */
		@Override
		protected void onCancelled() {
			if(isCancelled() && progressDialog != null){
				progressDialog.dismiss();
			}
			super.onCancelled();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected Void doInBackground(Void... params) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result) {
			if(isCancelled()){
				return;
			}
			instagramApp.imageLoader.displayImage(url, imageView,
					instagramApp.getImageOptions());
			if(progressDialog != null && progressDialog.isShowing()){
				progressDialog.dismiss();
			}
			super.onPostExecute(result);
		}
	}
}
