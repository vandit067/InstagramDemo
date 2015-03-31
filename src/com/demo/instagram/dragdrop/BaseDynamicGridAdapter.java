package com.demo.instagram.dragdrop;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

/**
 * Purpose:This class is cusomize class for gridview to reorder grid items.
 * 
 * @author Vandit Patel
 * @version 1.0
 * @date 18/02/15
 */
public abstract class BaseDynamicGridAdapter extends AbstractDynamicGridAdapter {
	private Context mContext;
	private ArrayList<Object> imagesList = new ArrayList<Object>();
	private int mColumnCount;

	/**
	 * @param context
	 * @param columnCount
	 */
	protected BaseDynamicGridAdapter(Context context, int columnCount) {
		this.mContext = context;
		this.mColumnCount = columnCount;
	}

	/**
	 * @param context
	 * @param moduleNames
	 * @param columnCount
	 */
	public BaseDynamicGridAdapter(Context context, List<?> moduleNames,
			int columnCount) {
		mContext = context;
		mColumnCount = columnCount;
		init(moduleNames);
	}

	/**
	 * @param items
	 */
	private void init(List<?> items) {
		addAllStableId(items);
		this.imagesList.addAll(items);

	}

	/**
	 * @param items
	 */
	public void set(List<?> items) {
		clear();
		init(items);
		notifyDataSetChanged();
	}

	/**
	 * Clear all images from list and notify adapter
	 */
	public void clear() {
		clearStableIdMap();
		imagesList.clear();
		notifyDataSetChanged();
	}

	/**
	 * Add images in to list
	 * 
	 * @param item
	 */
	public void add(Object item) {
		addStableId(item);
		imagesList.add(item);
		notifyDataSetChanged();
	}

	/**
	 * Add list in to images list.
	 * 
	 * @param items
	 */
	public void add(List<?> items) {
		addAllStableId(items);
		this.imagesList.addAll(items);
		notifyDataSetChanged();
	}

	/**
	 * Remove item from list
	 * 
	 * @param item
	 */
	public void remove(Object item) {
		imagesList.remove(item);
		removeStableID(item);
		notifyDataSetChanged();
	}

	/**
	 * Notify adapter to refresh views.
	 */
	public void notifyData() {
		notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return imagesList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return imagesList.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.demo.instagram.dragdrop.AbstractDynamicGridAdapter#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return mColumnCount;
	}

	/**
	 * Set number of columns to display in gridview
	 * 
	 * @param columnCount
	 */
	public void setColumnCount(int columnCount) {
		this.mColumnCount = columnCount;
		notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.demo.instagram.dragdrop.AbstractDynamicGridAdapter#reorderItems(int,
	 * int)
	 */
	@Override
	public void reorderItems(int originalPosition, int newPosition) {
		DynamicGridUtils.reorder(imagesList, originalPosition, newPosition);
		notifyDataSetChanged();
	}

	/**
	 * Return list instance.
	 * 
	 * @return imagesList
	 */
	public List<Object> getItems() {
		return imagesList;
	}

	/**
	 * Return current instance.
	 * 
	 * @return mContext
	 */
	protected Context getContext() {
		return mContext;
	}
}
