package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ch.epw.utils.Options;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Photo;

/**
 * GrideView 图片Adapter
 */
public class GridViewImagesAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<Photo> listItems; // 数据集合

	public GridViewImagesAdapter(Context context, List<Photo> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		options = Options.getListOptions();
	}

	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.list_image_gridview,
					null);

			holder.iv_list_image_gridview = (ImageView) convertView
					.findViewById(R.id.iv_list_image_gridview);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Photo photo = listItems.get(position);
		showPicture(photo.getPath() + "/"
				+ photo.getSaveName(), holder.iv_list_image_gridview, options);
		return convertView;
	}

	static class ViewHolder {
		ImageView iv_list_image_gridview;//
	}

}
