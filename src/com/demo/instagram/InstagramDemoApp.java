package com.demo.instagram;

import android.app.Application;

import com.demo.instagram.oauth.instagram.InstagramApp;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Purpose:This class is use for handle common data.
 * 
 * @author Vandit Patel
 * @version 1.0
 * @date 18/02/15
 */
public class InstagramDemoApp extends Application {

	public ImageLoader imageLoader;
	public DisplayImageOptions imageOptions;
	private InstagramApp instagramApp;

	/**
	 * Called when application start 
	 ***/
	@Override
	public void onCreate() {
		super.onCreate();
		// Configure Universal Image Loader
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).threadPoolSize(5).threadPriority(Thread.MIN_PRIORITY + 2).memoryCacheSize(2500000).memoryCache(new FIFOLimitedMemoryCache(2400000)).denyCacheImageMultipleSizesInMemory()
				.denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new HashCodeFileNameGenerator()).build();
		// Initialize ImageLoader 
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}
	
	public InstagramApp getInstagramApp() {
		return instagramApp;
	}

	public void setInstagramApp(InstagramApp instagramApp) {
		this.instagramApp = instagramApp;
	}

	/**
	 *  Retrieve instance of imageOptions for thumb images
	 **/
	public DisplayImageOptions getImageOptions() {
		if (imageOptions == null)
			imageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.instagram).showImageForEmptyUri(R.drawable.instagram).showImageOnFail(R.drawable.instagram).cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY).build();

		return imageOptions;
	}

	/**
	 *  Call when application is close
	 **/
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}