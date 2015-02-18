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

public class InstagramLoginActivity extends Activity implements OnClickListener {

	private InstagramApp mApp;
	private Button btnConnect;
	private InstagramDemoApp instagramDemoApp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instagram_login);

		initComponent();
		if (WebService.isNetworkAvailable(this)) {
			mApp = new InstagramApp(this, ApplicationData.CLIENT_ID,
					ApplicationData.CLIENT_SECRET, ApplicationData.CALLBACK_URL);
			mApp.setListener(listener);
			instagramDemoApp.setInstagramApp(mApp);
			if (mApp.hasAccessToken()) {
				callNextScreen();
			}
		} else {
			Util.displayDialog(this, getString(R.string.common_internet), true);
		}

	}

	private void initComponent() {
		instagramDemoApp = (InstagramDemoApp) getApplicationContext();
		btnConnect = (Button) findViewById(R.id.btnConnect);
		btnConnect.setOnClickListener(this);

	}

	private void callNextScreen() {
		Intent tagImagesActivity = new Intent(this, TagsImagesActivity.class);
//		tagImagesActivity.putExtra(getString(R.string.key_intent_username),
//				mApp.getUserName());
//		tagImagesActivity.putExtra(getString(R.string.key_intent_accesstoken),
//				mApp.getAccessToken());
		startActivity(tagImagesActivity);
		finish();
	}

	OAuthAuthenticationListener listener = new OAuthAuthenticationListener() {

		@Override
		public void onSuccess() {
			callNextScreen();
		}

		@Override
		public void onFail(String error) {
			Toast.makeText(InstagramLoginActivity.this, error,
					Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public void onClick(View v) {

//		if (mApp.hasAccessToken()) {
//			final AlertDialog.Builder builder = new AlertDialog.Builder(
//					InstagramLoginActivity.this);
//			builder.setMessage("Disconnect from Instagram?")
//					.setCancelable(false)
//					.setPositiveButton("Yes",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int id) {
//									mApp.resetAccessToken();
//									btnConnect.setText("Connect");
//								}
//							})
//					.setNegativeButton("No",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int id) {
//									dialog.cancel();
//								}
//							});
//			final AlertDialog alert = builder.create();
//			alert.show();
//		} else {
			mApp.authorize();
//		}

	}
}