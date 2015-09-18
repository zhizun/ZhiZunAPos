package com.zhizun.pos.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.zhizun.pos.R;
import com.zhizun.pos.base.MyBaseAdapter;
import com.zhizun.pos.bean.Rating;

/**
 * 星星ListViewAdapter
 */
public class ListViewRatingAdapter extends MyBaseAdapter {

	private Context context;// 运行上下文
	private List<Rating> listItems; // 数据集合
	private int ratingBarItemLayoutId;// 评分项布局ID

	OnRatingItemChangedListener onRatingItemChangedListener;

	public ListViewRatingAdapter(Context context, List<Rating> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		ratingBarItemLayoutId = R.layout.list_remark_js_item_rating_listviewitem;
	}
	
	public ListViewRatingAdapter(Context context, int ratingBarItemLayoutId, List<Rating> listItems) {
		super();
		this.context = context;
		this.listItems = listItems;
		this.ratingBarItemLayoutId = ratingBarItemLayoutId;
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = View.inflate(context, ratingBarItemLayoutId
					, null);
			holder.tv_list_star_listview_item_title = (TextView) convertView
					.findViewById(R.id.tv_list_star_listview_item_title);
			holder.rb_list_star_listview_item_ratingbar = (RatingBar) convertView
					.findViewById(R.id.rb_list_star_listview_item_ratingbar);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Rating ratingItem = listItems.get(position);
		holder.tv_list_star_listview_item_title.setText(ratingItem
				.getRemarkItemName());
		holder.rb_list_star_listview_item_ratingbar.setRating(Float
				.parseFloat(ratingItem.getRating()));

		// ratingBar是否只作为显示，如果不是只做显示，绑定change事件
		if (!holder.rb_list_star_listview_item_ratingbar.isIndicator()) {
			holder.rb_list_star_listview_item_ratingbar
					.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

						@Override
						public void onRatingChanged(RatingBar ratingBar,
								float rating, boolean fromUser) {
							if (fromUser) {
								ratingItem.setRating(String.valueOf(rating));
							}
							if (onRatingItemChangedListener != null) {
								onRatingItemChangedListener
										.OnRatingItemChanged(ratingItem);
							}
						}
					});
		}

		return convertView;
	}

	static class ViewHolder {
		TextView tv_list_star_listview_item_title;// 评分标题
		RatingBar rb_list_star_listview_item_ratingbar;// 星星
	}
	

	public interface OnRatingItemChangedListener {
		public void OnRatingItemChanged(Rating rating);
	}

	public OnRatingItemChangedListener getOnRatingItemChangedListener() {
		return onRatingItemChangedListener;
	}

	public void setOnRatingItemChangedListener(
			OnRatingItemChangedListener onRatingItemChangedListener) {
		this.onRatingItemChangedListener = onRatingItemChangedListener;
	}
}
