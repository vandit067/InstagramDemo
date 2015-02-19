package com.demo.instagram;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

import com.demo.instagram.adapter.DynamicGridViewAdapter;
import com.demo.instagram.common.Util;
import com.demo.instagram.dragdrop.BaseDynamicGridAdapter;
import com.demo.instagram.dragdrop.DynamicGridView;
import com.demo.instagram.model.ImagesDataModel;
import com.demo.instagram.webservice.WSGetImagesData;
import com.demo.instagram.webservice.WebService;

public class TagsImagesActivity extends Activity implements
		OnItemClickListener, OnClickListener, OnItemLongClickListener,
		OnScrollListener {

	private ArrayList<ImagesDataModel> imageDataList;
	private DynamicGridView gridView;
	private RequestImagesTask requestImageTask;
	private TextView txtTitle;
	// private String accessToken = "";
	private TextView txtDisconnect;
	private InstagramDemoApp instagramDemoApp;
	private DynamicGridViewAdapter dynamicGridViewAdapter;
	private boolean isLoadMore = false;
	private String nextMaxTagId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_images);
		initComponent();
		callImagesDataTask();
	}

	private void callImagesDataTask() {
		if (WebService.isNetworkAvailable(this)) {
			requestImageTask = new RequestImagesTask(this);
			requestImageTask.execute();
		} else {
			Util.displayDialog(this, getString(R.string.common_internet), true);
		}
	}

	private void initComponent() {
		instagramDemoApp = (InstagramDemoApp) getApplicationContext();
		gridView = (DynamicGridView) findViewById(R.id.activity_tag_images_gridview);
		txtTitle = (TextView) findViewById(R.id.activity_tag_images_txt_title);
		txtDisconnect = (TextView) findViewById(R.id.activity_tag_images_txt_disconnect);
		gridView.setOnItemLongClickListener(this);
		gridView.setOnItemClickListener(this);
		gridView.setOnScrollListener(this);
		txtDisconnect.setOnClickListener(this);
		txtTitle.setText(getString(R.string.connectedas, instagramDemoApp
				.getInstagramApp().getUserName()));
		imageDataList = new ArrayList<ImagesDataModel>();
		// if (getIntent() != null) {
		// txtTitle.setText(getString(R.string.connectedas, getIntent()
		// .getStringExtra(getString(R.string.key_intent_username))));
		// accessToken = getIntent().getStringExtra(
		// getString(R.string.key_intent_accesstoken));
		// }
	}

	private class RequestImagesTask extends AsyncTask<Void, Void, Void> {
		private String url;
		private Context mContext;
		private WSGetImagesData wsGetImagesData;
		private ProgressDialog progressDialog;

		public RequestImagesTask(Context c) {
			super();
			this.url = c.getString(R.string.tag_api_url, "selfie",
					instagramDemoApp.getInstagramApp().getAccessToken(),
					nextMaxTagId);
			// this.url = c.getString(R.string.tag_api_url, "selfie",
			// accessToken);
			this.mContext = c;
			wsGetImagesData = new WSGetImagesData(mContext);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(mContext, "",
					getString(R.string.common_loading), true, true);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			if (!isLoadMore) {
				cancel(true);
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				isLoadMore = true;
				imageDataList = wsGetImagesData.executeService(mContext, url,
						imageDataList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void unused) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			if (!isCancelled() && !isFinishing()) {
				nextMaxTagId = wsGetImagesData.getNextMaxTagId();
				if (wsGetImagesData.isSuccess() && imageDataList != null
						&& imageDataList.size() > 0) {
					// gridView.setAdapter(new ImagesAdapter(mContext,
					// imageDataList));
					if (imageDataList != null && imageDataList.size() > 0) {
						if (dynamicGridViewAdapter != null) {
//							dynamicGridViewAdapter.add(imageDataList);
							dynamicGridViewAdapter.set(imageDataList);
						} else {
							dynamicGridViewAdapter = new DynamicGridViewAdapter(
									mContext, imageDataList, 2);
							gridView.setAdapter(dynamicGridViewAdapter);
						}
						isLoadMore = false;
					} else {
						if (dynamicGridViewAdapter != null) {
							dynamicGridViewAdapter.notifyDataSetChanged();
						}
					}
					// gridView.setAdapter(new DynamicGridViewAdapter(mContext,
					// imageDataList,2));
				}
				
				
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int position,
			long id) {
		ImagesDataModel imagesDataModel = (ImagesDataModel) adapterView
				.getItemAtPosition(position);
		Intent i = new Intent(TagsImagesActivity.this, ImageActivity.class);
		i.putExtra(getString(R.string.key_intent_highresurl),
				imagesDataModel.getHighResImageUrl());
		startActivity(i);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_tag_images_txt_disconnect:
			if (instagramDemoApp.getInstagramApp().hasAccessToken()) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						TagsImagesActivity.this);
				builder.setMessage("Disconnect from Instagram?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										instagramDemoApp.getInstagramApp()
												.resetAccessToken();
										Intent i = new Intent(
												TagsImagesActivity.this,
												InstagramLoginActivity.class);
										startActivity(i);
										finish();
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				final AlertDialog alert = builder.create();
				alert.show();
			}
			break;

		default:
			break;
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		gridView.startEditMode();
		return false;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		int lastInScreen = firstVisibleItem + visibleItemCount;
		if (WebService.isNetworkAvailable(TagsImagesActivity.this)) {
			if ((lastInScreen == totalItemCount) && !(isLoadMore)) {
				if (imageDataList != null && !nextMaxTagId.equals("")) {
					if (requestImageTask != null
							&& (requestImageTask.getStatus() == AsyncTask.Status.FINISHED)) {
						// if (imageDataList.size() < totalRecords) {
						callImagesDataTask();
					}
					// }
				}
			}
		}
	};

}