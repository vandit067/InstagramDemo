package com.demo.instagram.dragdrop;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

/**
 * Author: alex askerov
 * Date: 9/7/13
 * Time: 10:49 PM
 */
public abstract class BaseDynamicGridAdapter extends AbstractDynamicGridAdapter {
    private Context mContext;

//    private ArrayList<Object> moduleNamesList = new ArrayList<Object>();
//    private ArrayList<Object> moduleImagesList = new ArrayList<Object>();
//    private ArrayList<Object> moduleDisplayNamesList = new ArrayList<Object>();
    private ArrayList<Object> moduleNamesList = new ArrayList<Object>();
    private int mColumnCount;

    protected BaseDynamicGridAdapter(Context context, int columnCount) {
        this.mContext = context;
        this.mColumnCount = columnCount;
    }

    public BaseDynamicGridAdapter(Context context, List<?> moduleNames, int columnCount) {
        mContext = context;
        mColumnCount = columnCount;
        init(moduleNames);
    }

    private void init(List<?> items) {
        addAllStableId(items);
        this.moduleNamesList.addAll(items);
       
    }


    public void set(List<?> items) {
        clear();
        init(items);
        notifyDataSetChanged();
    }

    public void clear() {
        clearStableIdMap();
        moduleNamesList.clear();
        notifyDataSetChanged();
    }

    public void add(Object item) {
        addStableId(item);
        moduleNamesList.add(item);
        notifyDataSetChanged();
    }


    public void add(List<?> items) {
        addAllStableId(items);
        this.moduleNamesList.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(Object item) {
        moduleNamesList.remove(item);
        removeStableID(item);
        notifyDataSetChanged();
    }

    public void notifyData(){
    	notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return moduleNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return moduleNamesList.get(position);
    }

    @Override
    public int getColumnCount() {
        return mColumnCount;
    }

    public void setColumnCount(int columnCount) {
        this.mColumnCount = columnCount;
        notifyDataSetChanged();
    }

    @Override
    public void reorderItems(int originalPosition, int newPosition) {
        DynamicGridUtils.reorder(moduleNamesList, originalPosition, newPosition);
        notifyDataSetChanged();
    }

    public List getItems() {
        return moduleNamesList;
    }

    protected Context getContext() {
        return mContext;
    }
}
