package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.text.InputFilter.LengthFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ch.epw.utils.DateUtil;
import com.ch.epw.utils.Options;
import com.ch.epw.utils.URLs;
import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.NewComments;
import com.zhizun.pos.bean.NewCommonComments;
import com.zhizun.pos.bean.RecevieMyInvitation;
import com.zhizun.pos.bean.UserRole;

/**
 * 最新 通用 回复 ListViewAdapter
 * 
 * @param <index2>
 */
public class ListViewNewCommonCommentsAdapter extends MyBaseAdapter {

	public static final String TAG = ListViewNewCommonCommentsAdapter.class
			.getName();
	private Context context;// 运行上下文
	private List<NewCommonComments> listItems; // 数据集合

	public ListViewNewCommonCommentsAdapter(Context context,
			List<NewCommonComments> listItems) {
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
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.list_newcomments_item,
					null);

			holder.iv_list_newcomments_item_logo = (ImageView) convertView
					.findViewById(R.id.iv_list_newcomments_item_logo);

			holder.tv_list_newcomments_item_name = (TextView) convertView
					.findViewById(R.id.tv_list_newcomments_item_name);

			holder.tv_list_newcomments_item_repeatcontent = (TextView) convertView
					.findViewById(R.id.tv_list_newcomments_item_repeatcontent);
			holder.iv_list_newcomments_item_imagecontent = (ImageView) convertView
					.findViewById(R.id.iv_list_newcomments_item_imagecontent);

			holder.tv_list_list_newcomments_item_date = (TextView) convertView
					.findViewById(R.id.tv_list_list_newcomments_item_date);

			holder.iv_list_newcomments_item_textcontent = (TextView) convertView
					.findViewById(R.id.iv_list_newcomments_item_textcontent);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		NewCommonComments newComments = listItems.get(position);

		showPicture(newComments.getUserPhoto(),
				holder.iv_list_newcomments_item_logo, options);

		if (null != newComments.getUserAppe()
				&& !newComments.getUserAppe().equals("")) {
			holder.tv_list_newcomments_item_name.setText(newComments
					.getUserAppe());
		} else {
			holder.tv_list_newcomments_item_name.setText("");
		}
		if (null != newComments.getContent()
				&& !newComments.getContent().equals("")) {
			
			if (newComments.getContent().length()>25) {
				String shows=newComments.getContent().substring(0, 25)+"...";
				holder.tv_list_newcomments_item_repeatcontent.setText(shows);
			}else {
				holder.tv_list_newcomments_item_repeatcontent.setText(newComments
						.getContent());
			}
		} else {
			holder.tv_list_newcomments_item_repeatcontent.setText("");
		}
		if (null != newComments.getCommentTime()
				&& !newComments.getCommentTime().equals("")) {
			holder.tv_list_list_newcomments_item_date.setText(
					DateUtil.getSimpleChineseDateTimeWithoutSec(newComments.getCommentTime(), 
							"yyyy-MM-dd HH:mm:ss"));
		} else {
			holder.tv_list_list_newcomments_item_date.setText("");
		}

		if (null != newComments.getPhotoList()
				&& newComments.getPhotoList().size() > 0) {
			holder.iv_list_newcomments_item_textcontent
					.setVisibility(View.GONE);
			holder.iv_list_newcomments_item_imagecontent
					.setVisibility(View.VISIBLE);
			showPicture(newComments.getPhotoList().get(0).getPath()+"/"+newComments.getPhotoList().get(0).getSaveName(),
					holder.iv_list_newcomments_item_imagecontent, options);

		} else {
			holder.iv_list_newcomments_item_imagecontent
					.setVisibility(View.GONE);
			holder.iv_list_newcomments_item_textcontent
					.setVisibility(View.VISIBLE);
			holder.iv_list_newcomments_item_textcontent.setText(newComments
					.getRefContent());
		}

		return convertView;
	}

	static class ViewHolder {
		ImageView iv_list_newcomments_item_logo;// logo
		TextView tv_list_newcomments_item_name;//
		TextView tv_list_newcomments_item_repeatcontent;//

		ImageView iv_list_newcomments_item_imagecontent;//
		TextView tv_list_list_newcomments_item_date;//
		TextView iv_list_newcomments_item_textcontent;//
	}

}
