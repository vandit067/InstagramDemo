package com.demo.instagram.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demo.instagram.InstagramDemoApp;
import com.demo.instagram.R;
import com.demo.instagram.dragdrop.BaseDynamicGridAdapter;
import com.demo.instagram.model.ImagesDataModel;

public class DynamicGridViewAdapter extends BaseDynamicGridAdapter {
	private InstagramDemoApp instagramApp;

	public DynamicGridViewAdapter(Context context,
			List<ImagesDataModel> moduleDataList, int columnCount) {
		super(context, moduleDataList, columnCount);
		instagramApp = (InstagramDemoApp) context.getApplicationContext();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CheeseViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.row_image, null);
			holder = new CheeseViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (CheeseViewHolder) convertView.getTag();
		}
		holder.build((ImagesDataModel) getItem(position));
		return convertView;
	}

	private class CheeseViewHolder {
		private ImageView imgView;

		private CheeseViewHolder(View view) {
			imgView = (ImageView) view.findViewById(R.id.row_img_image);
		}

		void build(ImagesDataModel model) {
			instagramApp.imageLoader.displayImage(model.getLowResImageUrl(),
					imgView, instagramApp.getImageOptions());
			// imgView.setImageResource(Util.getResID(context,
			// model.getLowResImageUrl()));
		}
	}

}