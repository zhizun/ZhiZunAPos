package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.List;

import com.zhizun.pos.base.BaseBean;

/**
 * 宝宝兴趣 实体 创建人：李林中 创建日期：2014-12-4 上午11:56:10 作用： 修改
 * =================================================== 修改人 修改日期 原因(描述)
 * ===================================================
 */

public class InterestPri extends BaseBean {
	private static final long serialVersionUID = 1L;
	private static final String TAG = InterestPri.class.getName();

	private String catName;//
	private String catId;// 、
	List<InterestSec> interestSecList = new ArrayList<InterestSec>();
	
	
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public List<InterestSec> getInterestSecList() {
		return interestSecList;
	}
	public void setInterestSecList(List<InterestSec> interestSecList) {
		this.interestSecList = interestSecList;
	}
	
}
