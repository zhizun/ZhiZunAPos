package com.zhizun.pos.bean;

import com.ch.epw.utils.DateTools;
import com.zhizun.pos.base.BaseBean;
	/**
	 * 推荐有奖 弹出详细列表信息
	 */
public class MyRecommendationPicker extends BaseBean {

	private static final long serialVersionUID = 1L;
	private String referralPhone;//领取人
	private String referralTime;//领取时间
	private String isVerify;//是否成交
	private String xcode;//验证码
	public String getReferralPhone() {
		return referralPhone;
	}
	public void setReferralPhone(String referralPhone) {
		this.referralPhone = referralPhone;
	}
	public String getReferralTime() {
		return referralTime;
	}
	public String getReferralTimeForDisplay() {
		return DateTools.formatDate(this.referralTime, "yyyy-MM-dd HH:mm");
	}
	public void setReferralTime(String referralTime) {
		this.referralTime = referralTime;
	}
	public String getIsVerify() {
		return isVerify;
	}
	public void setIsVerify(String isVerify) {
		this.isVerify = isVerify;
	}
	public String getXcode() {
		return xcode;
	}
	public void setXcode(String xcode) {
		this.xcode = xcode;
	}
	
}
