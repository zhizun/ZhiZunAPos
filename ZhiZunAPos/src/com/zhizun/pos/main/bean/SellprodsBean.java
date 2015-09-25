package com.zhizun.pos.main.bean;

import com.zhizun.pos.base.BaseBean;

public class SellprodsBean extends BaseBean {

	/**
	 * 建业至尊
	 * 		卖货列表实体类
	 */
	private static final long serialVersionUID = 1L;
	
	private String imageUrl;//图片地址
	private String title;//标题内容
	private String price;//单价
	private String num;//数量
	private String money;//金额
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
	
	

}
