package com.demo.instagram.webservice;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.demo.instagram.R;
import com.demo.instagram.model.ImagesDataModel;
/**
 * Purpose:This class is handle input request and parse the data in JSON format.
 * 
 * @author Vandit Patel
 * @version 1.0
 * @date 18/02/15
 */
public class WSGetImagesData {

	/**
	 * Specify context
	 */
	private Context context;
	/**
	 * Store message
	 */
	private String message = "";
	/**
	 * Check get the response or not
	 */
	private boolean isSuccess = false;
	/**
	 * Store next max tag id to fetch next page.
	 */
	private String nextMaxTagId = "";

	public WSGetImagesData(Context context) {
		this.context = context;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * Public method to call from AsyncTask to execute instagram request.
	 * 
	 * @param context
	 * @param url
	 * @return {@link ArrayList}
	 * @throws Exception
	 */
	public ArrayList<ImagesDataModel> executeService(Context context,
			String url, ArrayList<ImagesDataModel> imagesList) throws Exception {
		return parseResponse(WebService.getData(context, url), imagesList);
	}

	/**
	 * Parse the response and store data in to model
	 * @param json
	 * @param imagesDataList
	 * @return ArrayList<ImagesDataModel>
	 */
	private ArrayList<ImagesDataModel> parseResponse(final String json,
			ArrayList<ImagesDataModel> imagesDataList) {
		if (json != null && !json.trim().equals("")) {
			try {
				isSuccess = true;
				final JSONObject jsonObject = new JSONObject(json);
				nextMaxTagId = jsonObject.optJSONObject("pagination")
						.getString("next_max_tag_id");
				JSONArray instagramDataList = jsonObject.optJSONArray("data");
				if (instagramDataList != null && instagramDataList.length() > 0) {
					ImagesDataModel model;
					for (int i = 0; i < instagramDataList.length(); i++) {
						JSONObject imageResDataObject = instagramDataList
								.getJSONObject(i);
						model = new ImagesDataModel();
						model.setThumbnailImageUrl(imageResDataObject
								.optJSONObject("images")
								.optJSONObject("thumbnail").optString("url"));
						model.setLowResImageUrl(imageResDataObject
								.optJSONObject("images")
								.optJSONObject("low_resolution")
								.optString("url"));
						model.setHighResImageUrl(imageResDataObject
								.optJSONObject("images")
								.optJSONObject("standard_resolution")
								.optString("url"));
						imagesDataList.add(model);
					}

					imagesDataList.trimToSize();
				}

			} catch (Exception e) {
				isSuccess = false;
				message = context.getString(R.string.common_error);
				e.printStackTrace();
			}
		}
		return imagesDataList;
	}

	/**
	 * @return nextMaxTagId
	 */
	public String getNextMaxTagId() {
		return nextMaxTagId;
	}

	/**
	 * @param nextMaxTagId
	 */
	public void setNextMaxTagId(String nextMaxTagId) {
		this.nextMaxTagId = nextMaxTagId;
	}
}
