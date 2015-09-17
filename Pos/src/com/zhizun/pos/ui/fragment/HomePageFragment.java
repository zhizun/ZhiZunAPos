package com.zhizun.pos.ui.fragment;

import java.util.List;

import com.zhizun.pos.ui.activity.AccountsActvity;
import com.zhizun.pos.ui.activity.MainActivity;
import com.zhizun.pos.ui.activity.StorageActivity;
import com.zhizun.pos.ui.activity.invent.CheckActivity;
import com.zhizun.pos.ui.activity.owe.OweActivity;
import com.zhizun.pos.ui.activity.purchase.PurchaseChooseGoodsActivity;
import com.zhizun.pos.ui.activity.refund.RefundActivity;
import com.zhizun.pos.ui.activity.sales.SalesProuductSelActivity;
import com.zhizun.pos.ui.activity.scan.CaptureActivity;
import com.zhizun.pos.ui.activity.statistics.StatisticsActivity;
import com.zhizun.pos.ui.adapter.HomePageAdapter;
import com.zhizun.pos.ui.adapter.SimpleTextAdapter;
import com.zhizun.pos.ui.widget.CustomPopupWindow;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.action.SyncUpAndDownAction;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.Store;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class HomePageFragment extends BaseTitleTopBarFragment implements AdapterView.OnItemClickListener,
		BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, View.OnKeyListener,
		TitleTopBar.OnTopBarTitleClickListener, SyncUpAndDownAction.OnSyncUpAndDownActionListener {
	private static final int RESULT_TO_MAINACTIVITY = 10;
	private Context context;
	private EditText mEdtSearch;
	private Intent mIntent;
	private ListView mLsvGoods;
	private CustomPopupWindow mPopStatu;
	private SyncUpAndDownAction syncUpAndDownAction;

	private void setModule() {
		HomePageAdapter localHomePageAdapter = new HomePageAdapter();
		this.mLsvGoods.setAdapter(localHomePageAdapter);
		this.mLsvGoods.setOnItemClickListener(this);
	}

	private void showPopUp(View paramView) {
		paramView = (ListView) ((Activity) this.context).getLayoutInflater().inflate(2130903178, null);
		paramView.setDivider(null);
		paramView.setBackgroundResource(2131427355);
		final List localList = UserManager.getInstance().getCurrentUserStoreList();
		final String[] arrayOfString = new String[UserManager.getInstance().getCurrentUserStoreList().size()];
		int i = 0;
		for (;;) {
			if (i >= localList.size()) {
				paramView.setAdapter(new SimpleTextAdapter(arrayOfString));
				paramView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView,
							int paramAnonymousInt, long paramAnonymousLong) {
						UserManager.getInstance().setCurrentStore((Store) localList.get(paramAnonymousInt));
						paramAnonymousAdapterView = new Intent(HomePageFragment.this.context, MainActivity.class);
						paramAnonymousAdapterView.setFlags(268468224);
						paramAnonymousAdapterView.putExtra("storeName", arrayOfString[paramAnonymousInt]);
						HomePageFragment.this.startActivityForResult(paramAnonymousAdapterView, 10);
						if ((HomePageFragment.this.mPopStatu != null)
								&& (HomePageFragment.this.mPopStatu.isShowing())) {
							HomePageFragment.this.mPopStatu.dismiss();
						}
					}
				});
				this.mPopStatu = new CustomPopupWindow(this.context, paramView,
						((Activity) this.context).getWindowManager().getDefaultDisplay().getWidth());
				this.mPopStatu.setAnimationStyle(2131230759);
				this.mPopStatu.showMenu(this.mTopBar, 0, 0);
				return;
			}
			arrayOfString[i] = ((Store) localList.get(i)).getStore_Name();
			i += 1;
		}
	}

	public void addBody(ViewGroup paramViewGroup) {
		getActivity().getLayoutInflater().inflate(2130903160, paramViewGroup);
		this.mTopBar.setLeftButton(2130837810, this);
		this.mTopBar.setRightButton(2130837545, this);
		this.mTopBar.setTitle(UserManager.getInstance().getCurrentStore().getStore_Name());
		this.mTopBar.setTitleClickListener(this);
		this.mLsvGoods = ((ListView) paramViewGroup.findViewById(2131362497));
		this.mEdtSearch = ((EditText) paramViewGroup.findViewById(2131362496));
		this.mEdtSearch.setOnKeyListener(this);
		setModule();
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		this.context = getActivity();
	}

	public void onDestroy() {
		super.onDestroy();
	}

	public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
		switch (((com.zizun.cs.ui.entity.Module) this.mLsvGoods.getAdapter().getItem(paramInt)).getModule_Code()) {
		default:
			return;
		case Invent:
			this.mIntent = new Intent(getActivity(), PurchaseChooseGoodsActivity.class);
			startActivity(this.mIntent);
			return;
		case Balance:
			this.mIntent = new Intent(getActivity(), SalesProuductSelActivity.class);
			startActivity(this.mIntent);
			return;
		case ProductGroup:
			this.mIntent = new Intent(getActivity(), AccountsActvity.class);
			startActivity(this.mIntent);
			return;
		case Purchase:
			this.mIntent = new Intent(getActivity(), StorageActivity.class);
			startActivity(this.mIntent);
			return;
		case Report:
			this.mIntent = new Intent(getActivity(), StatisticsActivity.class);
			startActivity(this.mIntent);
			return;
		case Return:
			this.mIntent = new Intent(getActivity(), CheckActivity.class);
			startActivity(this.mIntent);
			return;
		case Role:
			this.mIntent = new Intent(getActivity(), RefundActivity.class);
			startActivity(this.mIntent);
			return;
		}
		this.mIntent = new Intent(getActivity(), OweActivity.class);
		startActivity(this.mIntent);
	}

	public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent) {
		switch (paramView.getId()) {
		}
		for (;;) {
			return false;
			if ((paramKeyEvent.getAction() == 1) && (paramInt == 66)) {
				paramView = this.mEdtSearch.getText().toString().trim();
				this.mIntent = new Intent(getActivity(), StorageActivity.class);
				this.mIntent.putExtra("requestCode", 4);
				this.mIntent.putExtra("search_goods", paramView);
				startActivity(this.mIntent);
			}
		}
	}

	public void onSyncUpAndDownFail(String paramString) {
		ToastUtil.toastLong(this.context, paramString);
		this.mTopBar.getLeftButton().setEnabled(true);
		this.syncUpAndDownAction.clearListener();
	}

	public void onSyncUpAndDownSuccess() {
		ToastUtil.toastLong(this.context, "ͬ同步完成");
		this.mTopBar.getLeftButton().setEnabled(true);
		this.syncUpAndDownAction.clearListener();
	}

	public void onTopBarLeftClick() {
		if (UserManager.getInstance().isExperienceAccount()) {
			ToastUtil.toastLong(this.context, "体验账号不能同步数据!");
			return;
		}
		this.syncUpAndDownAction = SyncUpAndDownAction.getInstance();
		this.syncUpAndDownAction.setOnSyncUpAndDownActionListener(this);
		this.syncUpAndDownAction.execute();
		this.mTopBar.getLeftButton().setEnabled(false);
	}

	public void onTopBarRightClick() {
		Intent localIntent = new Intent(getActivity(), CaptureActivity.class);
		localIntent.putExtra(MainActivity.class.getSimpleName(), true);
		startActivity(localIntent);
	}

	public void onTopBarTitleClick() {
		showPopUp(this.mTopBar);
	}
}