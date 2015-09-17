package com.zizun.cs.activity.manager;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zizun.cs.biz.api.AD_headerAPI;
import com.zizun.cs.entities.api.AD_HeaderResult;

public class AD_headerManager {
	public static void GetAD_headerBannerfromAPI(RequestCallBack<String> paramRequestCallBack, String paramString) {
		AD_headerAPI.CreateADheaderBanner(paramRequestCallBack, paramString);
	}

	public static void GetAD_headerfromAPI(RequestCallBack<String> paramRequestCallBack, String paramString) {
		AD_headerAPI.CreateADheader(paramRequestCallBack, paramString);
	}

	public static void GetShareInfofromAPI(RequestCallBack<String> paramRequestCallBack) {
		AD_headerAPI.CreateShareInfo(paramRequestCallBack);
	}

	public static AD_HeaderResult getADheaderBannerFromJsonResult(String paramString) {
		return (AD_HeaderResult) AD_headerAPI.getAPIResultFromJson(paramString, AD_HeaderResult.class);
	}

	public static AD_HeaderResult getADheaderFromJsonResult(String paramString) {
		return (AD_HeaderResult) AD_headerAPI.getAPIResultFromJson(paramString, AD_HeaderResult.class);
	}
}