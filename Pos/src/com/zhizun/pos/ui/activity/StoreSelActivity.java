package com.zhizun.pos.ui.activity;

import java.util.List;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zizun.cs.activity.manager.AD_headerManager;
import com.zizun.cs.activity.manager.AppManager;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.common.utils.LogUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.api.AD_HeaderResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StoreSelActivity extends Activity implements AdapterView.OnItemClickListener {
	private static final int RESULT_TO_MAINACTIVITY = 10;
	private Context context;
	private ListView lv_stores;
	private String[] storeNames;
	private List<Store> stores;

	private void initData() {
		this.context = this;
		this.stores = UserManager.getInstance().getCurrentUserStoreList();
		int i = this.stores.size() - 1;
		if (i < 0) {
			this.storeNames = new String[this.stores.size()];
			i = 0;
		}
		for (;;) {
			if (i >= this.stores.size()) {
				return;
				if (((Store) this.stores.get(i)).getStore_Status() == 2) {
					this.stores.remove(i);
				}
				i -= 1;
				break;
			}
			this.storeNames[i] = ((Store) this.stores.get(i)).getStore_Name();
			i += 1;
		}
	}

	private void initView() {
		this.lv_stores = ((ListView) findViewById(2131362252));
		this.lv_stores.setOnItemClickListener(this);
	}

	private void jumpMain() {
		startActivity(new Intent(this.context, MainActivity.class));
		finish();
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(2130903105);
		initData();
		initView();
		this.lv_stores.setAdapter(new ArrayAdapter(this, 17367046, this.storeNames));
	}

	public void onItemClick(AdapterView<?> paramAdapterView, View paramView, final int paramInt, long paramLong) {
		UserManager.getInstance().setCurrentStore((Store) this.stores.get(paramInt));
		paramAdapterView = AppManager.getInstance().getAppVersionName(this.context);
		AD_headerManager.GetAD_headerfromAPI(new RequestCallBack() {
			private AD_HeaderResult result;

			public void onFailure(HttpException paramAnonymousHttpException, String paramAnonymousString) {
				StoreSelActivity.this.jumpMain();
			}

			public void onSuccess(ResponseInfo<String> paramAnonymousResponseInfo) {
				this.result = AD_headerManager.getADheaderFromJsonResult((String) paramAnonymousResponseInfo.result);
				LogUtil.logD((String) paramAnonymousResponseInfo.result);
				if (this.result.Code == 200) {
					if (this.result.Data.AH_IsShow == 1) {
						paramAnonymousResponseInfo = new Intent(StoreSelActivity.this.context, AD_HeaderActivity.class);
						paramAnonymousResponseInfo.putExtra("storeName", StoreSelActivity.this.storeNames[paramInt]);
						StoreSelActivity.this.startActivityForResult(paramAnonymousResponseInfo, 10);
						return;
					}
					StoreSelActivity.this.jumpMain();
					return;
				}
				if (this.result.Data.AH_IsShow == 1) {
					ToastUtil.toastShort(StoreSelActivity.this, "网络获取失败！");
				}
				StoreSelActivity.this.jumpMain();
			}
		}, paramAdapterView);
	}
}