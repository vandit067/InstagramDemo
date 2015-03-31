package com.demo.instagram.model;

/**
 * Purpose:This class is define as a model for images information
 * 
 * @author Vandit Patel
 * @version 1.0
 * @date 18/02/15
 */
public class ImagesDataModel {

	/**
	 * Specify for small images resolution url for image
	 */
	private String lowResImageUrl = "";
	/**
	 * Specify for thumbnail images resolution url for image
	 */
	private String thumbnailImageUrl = "";
	/**
	 * Specify for high images resolution url for image
	 */
	private String highResImageUrl = "";

	/**
	 * @return lowResImageUrl
	 */
	public String getLowResImageUrl() {
		return lowResImageUrl;
	}

	/**
	 * @param lowResImageUrl
	 */
	public void setLowResImageUrl(String lowResImageUrl) {
		this.lowResImageUrl = lowResImageUrl;
	}

	/**
	 * @return thumbnailImageUrl
	 */
	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}

	/**
	 * @param thumbnailImageUrl
	 */
	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}

	/**
	 * @return highResImageUrl
	 */
	public String getHighResImageUrl() {
		return highResImageUrl;
	}

	/**
	 * @param highResImageUrl
	 */
	public void setHighResImageUrl(String highResImageUrl) {
		this.highResImageUrl = highResImageUrl;
	}

}
