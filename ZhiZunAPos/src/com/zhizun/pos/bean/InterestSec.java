package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;

/**
 * 宝宝兴趣爱好 分类下面的具体爱好 实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class InterestSec extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = InterestSec.class.getName();

	private String itemName;//
	private String itemId;//
	private Boolean isSelected;//

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
