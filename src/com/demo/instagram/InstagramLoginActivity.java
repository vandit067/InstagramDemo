package com.demo.instagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.demo.instagram.common.ApplicationData;
import com.demo.instagram.common.Util;
import com.demo.instagram.oauth.instagram.InstagramApp;
import com.demo.instagram.oauth.instagram.InstagramApp.OAuthAuthenticationListener;
import com.demo.instagram.webservice.WebService;

/**
 * Purpose:This class is use for login to application with instagram API call.
 * 
 * @author Vandit Patel
 * @version 1.0
 * @date 18/02/15
 */
public class InstagramLoginActivity extends Activity implements OnClickListener {

	private InstagramApp mApp;
	private Button btnConnect;
	private InstagramDemoApp instagramDemoApp;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instagram_login);

		initComponent();
		/**
		 * Check network is available or not if it's available than call to
		 * instagram API and retrive access token.
		 **/
		if (WebService.isNetworkAvailable(this)) {
			mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
					ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);
			mApp.setListener(listener);
			instagramDemoApp.setInstagramApp(mApp);
			// check accesstoken available or not. if available than call next
			// screen.
			if (mApp.hasAccessToken()) {
				callNextScreen();
			}
		} else {
			Util.displayDialog(this, getString(R.string.common_internet), true);
		}

	}

	/**
	 * Initialize the android components and assign listners to components.
	 */
	private void initComponent() {
		instagramDemoApp = (InstagramDemoApp) getApplicationContext();
		btnConnect = (Button) findViewById(R.id.activity_instagram_login_btn_connect);
		btnConnect.setOnClickListener(this);

	}

	/**
	 * If access token available than call tagImagesActivity class and finish
	 * current activity.
	 */
	private void callNextScreen() {
		Intent tagImagesActivity = new Intent(this, TagsImagesListActivity.class);
		// tagImagesActivity.putExtra(getString(R.string.key_intent_username),
		// mApp.getUserName());
		// tagImagesActivity.putExtra(getString(R.string.key_intent_accesstoken),
		// mApp.getAccessToken());
		startActivity(tagImagesActivity);
		finish();
	}

	/**
	 * Listner to check authentication is success or fail. on success call to
	 * next screen and on fail show toast message
	 **/
	OAuthAuthenticationListener listener = new OAuthAuthenticationListener() {

		@Override
		public void onSuccess() {
			// call next screen once authentication done
			callNextScreen();
		}

		@Override
		public void onFail(String error) {
			// display error in toast once authentication failure
			Toast.makeText(InstagramLoginActivity.this, error,
					Toast.LENGTH_SHORT).show();
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View) Handle
	 * click event of views
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.activity_instagram_login_btn_connect)
			mApp.authorize();

	}
}