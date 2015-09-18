package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

/**
 * 点评模版实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class RemarkTeplate extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = RemarkTeplate.class.getName();

	private String item;// 内容
	private String remarkItemId;// ID
	private float rating;// ID

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getRemarkItemId() {
		return remarkItemId;
	}

	public void setRemarkItemId(String remarkItemId) {
		this.remarkItemId = remarkItemId;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String toString() {
		return remarkItemId + "@" + rating;
	}
}
