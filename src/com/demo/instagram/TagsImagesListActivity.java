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
import com.demo.instagram.dragdrop.DynamicGridView;
import com.demo.instagram.model.ImagesDataModel;
import com.demo.instagram.webservice.WSGetImagesData;
import com.demo.instagram.webservice.WebService;

/**
 * Purpose:This class is use for display images in to grid from instagram which
 * has #Selfie tag.
 * 
 * @author Vandit Patel
 * @version 1.0
 * @date 18/02/15
 */
public class TagsImagesListActivity extends Activity implements
		OnItemClickListener, OnClickListener, OnItemLongClickListener,
		OnScrollListener {

	/**
	 * Arraylist of images
	 */
	private ArrayList<ImagesDataModel> imageDataList;
	/**
	 * Gridview android component to show images
	 */
	private DynamicGridView gridView;
	/**
	 * AsyncTask for query instagram api to retrieve #selfie images
	 */
	private RequestImagesTask requestImageTask;
	/**
	 * Show from which instagram user loggedin
	 */
	private TextView txtConnectedAs;
	/**
	 * To disconnect from instagram
	 */
	private TextView txtDisconnect;
	/**
	 * InstagramDemoApp Declaration
	 */
	private InstagramDemoApp instagramDemoApp;
	/**
	 * Adapter to display images in to gridview
	 */
	private DynamicGridViewAdapter dynamicGridViewAdapter;
	/**
	 * Flag to indicate load more feature
	 */
	private boolean isLoadMore = false;
	/**
	 * Find nextMaxTagId to retrieve next page images from instagram
	 */
	private String nextMaxTagId = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle) Called first when
	 * activity call.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_images_list);
		initComponent();
		callImagesDataTask();
	}

	/**
	 * Initialize android UI Components and assign listners.
	 */
	private void initComponent() {
		instagramDemoApp = (InstagramDemoApp) getApplicationContext();
		gridView = (DynamicGridView) findViewById(R.id.activity_tag_images_list_gridview);
		txtConnectedAs = (TextView) findViewById(R.id.activity_tag_images_list_txt_title);
		txtDisconnect = (TextView) findViewById(R.id.activity_tag_images_list_txt_disconnect);
		gridView.setOnItemLongClickListener(this);
		gridView.setOnItemClickListener(this);
		gridView.setOnScrollListener(this);
		txtDisconnect.setOnClickListener(this);
		txtConnectedAs.setText(getString(R.string.connectedas, instagramDemoApp
				.getInstagramApp().getUserName()));
		imageDataList = new ArrayList<ImagesDataModel>();
	}

	/**
	 * Check network connection and call RequestImagesTask for load images
	 */
	private void callImagesDataTask() {
		if (WebService.isNetworkAvailable(this)) {
			requestImageTask = new RequestImagesTask(this);
			requestImageTask.execute();
		} else {
			Util.displayDialog(this, getString(R.string.common_internet), true);
		}
	}

	private class RequestImagesTask extends AsyncTask<Void, Void, Void> {

		/**
		 * Url to specify for retrieve images from Instagram
		 */
		private String url;
		/**
		 * Context of current instance
		 */
		private Context mContext;
		/**
		 * Instance for webservice call and handling
		 */
		private WSGetImagesData wsGetImagesData;
		/**
		 * Declaration of progressdialog
		 */
		private ProgressDialog progressDialog;

		/**
		 * Constructor of RequestImagesTask
		 * 
		 * @param c
		 */
		public RequestImagesTask(Context c) {
			super();
			this.url = c.getString(R.string.tag_api_url, "selfie",
					instagramDemoApp.getInstagramApp().getAccessToken(),
					nextMaxTagId);
			// this.url = c.getString(R.string.tag_api_url, "selfie",
			// accessToken);
			this.mContext = c;
			// Instance for handling webservice call
			wsGetImagesData = new WSGetImagesData(mContext);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Display ProgressDialog
			progressDialog = ProgressDialog.show(mContext, "",
					getString(R.string.common_loading), true, true);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onCancelled()
		 */
		@Override
		protected void onCancelled() {
			super.onCancelled();
			if (!isLoadMore) {
				cancel(true);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
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
							// dynamicGridViewAdapter.add(imageDataList);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long) Handle click event of views
	 */
	@Override
	public void onItemClick(AdapterView<?> adapterView, View v, int position,
			long id) {
		ImagesDataModel imagesDataModel = (ImagesDataModel) adapterView
				.getItemAtPosition(position);
		Intent i = new Intent(TagsImagesListActivity.this,
				DisplayFullImageActivity.class);
		i.putExtra(getString(R.string.key_intent_highresurl),
				imagesDataModel.getHighResImageUrl());
		startActivity(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_tag_images_list_txt_disconnect:
			if (instagramDemoApp.getInstagramApp().hasAccessToken()) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						TagsImagesListActivity.this);
				builder.setMessage("Disconnect from Instagram?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										instagramDemoApp.getInstagramApp()
												.resetAccessToken();
										Intent i = new Intent(
												TagsImagesListActivity.this,
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android
	 * .widget.AdapterView, android.view.View, int, long) Handle Long click of
	 * view
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		gridView.startEditMode();
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android
	 * .widget.AbsListView, int)
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.
	 * AbsListView, int, int, int) Check scroll position and handle load more
	 * feature.
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		int lastInScreen = firstVisibleItem + visibleItemCount;
		if (WebService.isNetworkAvailable(TagsImagesListActivity.this)) {
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