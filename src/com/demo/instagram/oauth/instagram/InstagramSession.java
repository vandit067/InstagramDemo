package com.demo.instagram.oauth.instagram;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.Context;

/**
 * Purpose:This class is manage access token and user name. Uses shared
 * preferences to store access token and user name.
 * 
 * @author Vandit Patel
 * @version 1.0
 * @date 18/02/15
 */
public class InstagramSession {

	private SharedPreferences sharedPref;
	private Editor editor;

	private final String SHARED = "Instagram_Preferences";
	private final String API_USERNAME = "username";
	private final String API_ID = "id";
	private final String API_NAME = "name";
	private final String API_ACCESS_TOKEN = "access_token";

	public InstagramSession(Context context) {
		sharedPref = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
		editor = sharedPref.edit();
	}

	/**
	 * It store the access token in sharedpreference storage.
	 * 
	 * @param accessToken
	 * @param expireToken
	 * @param expiresIn
	 * @param username
	 */
	public void storeAccessToken(String accessToken, String id,
			String username, String name) {
		editor.putString(API_ID, id);
		editor.putString(API_NAME, name);
		editor.putString(API_ACCESS_TOKEN, accessToken);
		editor.putString(API_USERNAME, username);
		editor.commit();
	}

	/**
	 * Store accesstoken
	 * 
	 * @param accessToken
	 */
	public void storeAccessToken(String accessToken) {
		editor.putString(API_ACCESS_TOKEN, accessToken);
		editor.commit();
	}

	/**
	 * Reset access token and user name
	 */
	public void resetAccessToken() {
		editor.putString(API_ID, null);
		editor.putString(API_NAME, null);
		editor.putString(API_ACCESS_TOKEN, null);
		editor.putString(API_USERNAME, null);
		editor.commit();
	}

	/**
	 * Get user name
	 * 
	 * @return User name
	 */
	public String getUsername() {
		return sharedPref.getString(API_USERNAME, null);
	}

	/**
	 * Retrieve id of user.
	 * 
	 * @return String
	 */
	public String getId() {
		return sharedPref.getString(API_ID, null);
	}

	/**
	 * Retrive name of user
	 * 
	 * @return String
	 */
	public String getName() {
		return sharedPref.getString(API_NAME, null);
	}

	/**
	 * Get access token
	 * 
	 * @return Access token
	 */
	public String getAccessToken() {
		return sharedPref.getString(API_ACCESS_TOKEN, null);
	}

}