package com.demo.instagram.model;

public class ImagesDataModel {

	private String lowResImageUrl = "";
	private String thumbnailImageUrl = "";
	private String highResImageUrl = "";

	public String getLowResImageUrl() {
		return lowResImageUrl;
	}

	public void setLowResImageUrl(String lowResImageUrl) {
		this.lowResImageUrl = lowResImageUrl;
	}

	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}

	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}

	public String getHighResImageUrl() {
		return highResImageUrl;
	}

	public void setHighResImageUrl(String highResImageUrl) {
		this.highResImageUrl = highResImageUrl;
	}

}
