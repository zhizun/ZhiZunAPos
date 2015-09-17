package com.zhizun.pos.ui.activity.sales;

import java.io.Serializable;
import java.util.ArrayList;

import com.lidroid.xutils.util.LogUtils;
import com.zhizun.pos.ui.activity.CreateGoodsActivity;
import com.zhizun.pos.ui.activity.MainActivity;
import com.zhizun.pos.ui.activity.scan.CaptureActivity;
import com.zhizun.pos.ui.adapter.SimpleTextAdapter;
import com.zhizun.pos.ui.widget.CustomPopupWindow;
import com.zhizun.pos.ui.widget.TitleTopBar;
import com.zhizun.pos.util.DataUtil;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.common.utils.ExecutorUtils;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.ui.entity.Goods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SalesProuductSelActivity extends BaseTitleTopBarActivity
		implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarFrontRightClickListener,
		BaseTopBar.OnTopBarRightClickListener, View.OnClickListener, AdapterView.OnItemClickListener,
		AbsListView.OnScrollListener, GroupSeleteView.GroupChangeListener, TitleTopBar.OnTopBarTitleClickListener,
		SaveSalesDocumentAction.OnSaveSalesDocumentActionListener, View.OnKeyListener {
	private static final int ADD_SALES_GOODS_LIST = 2;
	private static final int GET_DEFULT_PAYMENT_METHOD_COMPLETE = 18;
	private static final int GET_SALES_GOODS_LIST_COMPLETE = 1;
	private static final int INTENT_REQUEST_CODE_BARCODE = 4099;
	private static final int PageSize = 10;
	private static final int SALES_DOCUMENT_SAVE_SUCCESS = 17;
	private SalesProductsAdapter adapter;
	private String condition = "";
	private Context context;
	private long groupID = 0L;
	Handler handler = new Handler(new Handler.Callback() {
		public boolean handleMessage(Message paramAnonymousMessage) {
			switch (paramAnonymousMessage.what) {
			}
			for (;;) {
				return false;
				long l = ((Long) paramAnonymousMessage.obj).longValue();
				SalesProuductSelActivity.this.pay(l);
				continue;
				SalesProuductSelActivity.this.mBtnReceivePay.setEnabled(true);
				SalesProuductSelActivity.this.clearViewData();
				SalesProuductSelActivity.this.sendWXReceipt();
				continue;
				paramAnonymousMessage = (ArrayList) paramAnonymousMessage.obj;
				if ((paramAnonymousMessage == null) || (paramAnonymousMessage.size() == 0)) {
					SalesProuductSelActivity.this.mListGoods = new ArrayList();
					ToastUtil.toastShort(SalesProuductSelActivity.this, 2131165317);
				}
				for (;;) {
					SalesProuductSelActivity.this.setAdapter();
					SalesProuductSelActivity.this.resetCondition();
					SalesProuductSelActivity.this.dismissWaitDialog();
					break;
					SalesProuductSelActivity.this.mListGoods = paramAnonymousMessage;
				}
				paramAnonymousMessage = (ArrayList) paramAnonymousMessage.obj;
				if (paramAnonymousMessage != null) {
					SalesProuductSelActivity.this.mListGoods.addAll(paramAnonymousMessage);
					SalesProuductSelActivity.this.adapter.refresh();
				}
				SalesProuductSelActivity.this.dismissWaitDialog();
			}
		}
	});
	private Button mBtnReceivePay;
	private EditText mEdtSearch;
	private GroupSeleteView mGroupView;
	private ImageView mImgScan;
	private Intent mIntent;
	private ArrayList<V_ProductSkuOnSale> mListGoods;
	private ListView mLsvGoods;
	private CustomPopupWindow mPopStatu;
	private int mRefundIndex = 0;
	private TextView mTxtTotalAmount;
	private TextView mTxtTotalMoney;
	private int pageIndex = 1;
	private SalesShoppingCart salesShoppingCart;
	private SalesType salesType;
	private long transID = -1L;
	private int visibleLastIndex = 0;

	private void clearViewData() {
		this.mTxtTotalAmount.setText("0");
		this.mTxtTotalMoney.setText("0");
		SalesManager.clearData();
	}

	private void getAllProductOnSale() {
		ExecutorUtils.EXECUTOR.execute(new Runnable() {
			public void run() {
				if (SalesProuductSelActivity.this.groupID == 0L) {
					SalesManager.getAllProduct(SalesProuductSelActivity.this.salesType,
							SalesProuductSelActivity.this.condition, 10, 1);
				}
				ArrayList localArrayList = SalesManager.getAllProduct(10, 1, SalesProuductSelActivity.this.condition,
						SalesProuductSelActivity.this.groupID, SalesProuductSelActivity.this.salesType);
				SalesProuductSelActivity.this.handler.obtainMessage(1, localArrayList).sendToTarget();
			}
		});
	}

	private void getAllSalesProduct() {
		showWaitDialog();
		getAllProductOnSale();
	}

	private void getPaymentMethod() {
		ExecutorUtils.getDBExecutor().execute(new Runnable() {
			public void run() {
				Object localObject = SalesManager.getInstance().getPaymentMethodDao();
				localObject = (PaymentMethod) SalesManager.getInstance()
						.getPaymentMethodListOutUIThread((PaymentMethodDao) localObject).get(0);
				SalesProuductSelActivity.this.handler
						.obtainMessage(18, Long.valueOf(((PaymentMethod) localObject).getPaymentMethod_ID()))
						.sendToTarget();
			}
		});
	}

	private boolean hasShoppingCartItem() {
		ArrayList localArrayList = this.salesShoppingCart.getSaleShoppingCartItemList();
		if ((localArrayList == null) || (localArrayList.size() == 0)) {
			ToastUtil.toastLong(this.context, "请先选择商品");
			return false;
		}
		return true;
	}

	private void init() {
		String str = getIntent().getStringExtra("logid");
		if (!TextUtils.isEmpty(str)) {
			this.salesShoppingCart.setSO_Remark(str);
			getIntent().removeExtra("logid");
		}
		getAllSalesProduct();
	}

	private void initBaseData() {
		this.context = this;
		initSalesShoppingCart();
		this.visibleLastIndex = 0;
		this.mRefundIndex = 0;
		this.pageIndex = 1;
	}

	private void initSalesShoppingCart() {
		this.salesShoppingCart = SalesManager.getInstance().getSalesShoppingCart();
		if (this.salesShoppingCart == null) {
			this.salesType = UserManager.getInstance().getCurrentSalesType();
			if (this.salesType == null) {
				this.salesType = SalesType.SALE;
			}
			this.salesShoppingCart = new SalesShoppingCart(this.salesType);
			SalesManager.setSalesShoppingCart(this.salesShoppingCart);
		}
		for (;;) {
			initSalesType(this.salesType);
			initShoppingCartBarView();
			return;
			this.salesType = SalesManager.getInstance().getSalesShoppingCart().getSalesType();
		}
	}

	private void initSalesType(SalesType paramSalesType) {
		switch (paramSalesType) {
		default:
			return;
		case WHOLESALE:
			this.mTopBar.setTitle(2131165356);
			return;
		}
		this.mTopBar.setTitle(2131165358);
	}

	private void initShoppingCartBarView() {
		this.mTxtTotalAmount.setText(DataUtil.getFormatString(this.salesShoppingCart.getTotalCount()));
		this.mTxtTotalMoney.setText(DataUtil.getFormatString(this.salesShoppingCart.getTotalMoney()));
	}

	private void pay(long paramLong) {
		SaveSalesDocumentAction localSaveSalesDocumentAction = new SaveSalesDocumentAction(this.salesShoppingCart,
				paramLong);
		localSaveSalesDocumentAction.setOnSaveSalesDocumentActionListener(this);
		localSaveSalesDocumentAction.execute();
	}

	private void receivePay() {
		if (!hasShoppingCartItem()) {
			return;
		}
		this.mBtnReceivePay.setEnabled(false);
		getPaymentMethod();
	}

	private void resetCondition() {
		String str = this.mEdtSearch.getText().toString().trim();
		this.condition = (" And vp.ProductSku_Name like '%" + str + "%'");
	}

	private void resetGoods() {
		this.pageIndex = 1;
		this.mRefundIndex = 0;
		getAllSalesProduct();
	}

	private void sendWXReceipt() {
		if (this.transID > 0L) {
			this.mIntent = new Intent(this.context, ReceiptPreViewActivity.class);
			this.mIntent.putExtra(Long.class.getSimpleName(), this.transID);
			startActivity(this.mIntent);
			this.transID = -1L;
		}
	}

	private void setAdapter() {
		this.adapter = new SalesProductsAdapter(this, this.mListGoods, this.salesType);
		this.mLsvGoods.setAdapter(this.adapter);
	}

	private void setSalesType(SalesType paramSalesType) {
		this.salesType = paramSalesType;
		this.salesShoppingCart.setSalesType(paramSalesType);
		UserManager.getInstance().setCurrentSalesType(paramSalesType);
		getAllSalesProduct();
	}

	private void showFilter() {
		ListView localListView = (ListView) getLayoutInflater().inflate(2130903178, null);
		localListView.setDivider(null);
		localListView.setBackgroundResource(2131427355);
		localListView.setAdapter(new SimpleTextAdapter(ResUtil.getStringArrays(2131296256)));
		localListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView,
					int paramAnonymousInt, long paramAnonymousLong) {
				switch (paramAnonymousInt) {
				}
				for (;;) {
					if ((SalesProuductSelActivity.this.mPopStatu != null)
							&& (SalesProuductSelActivity.this.mPopStatu.isShowing())) {
						SalesProuductSelActivity.this.mPopStatu.dismiss();
					}
					return;
					paramAnonymousAdapterView = new Intent(SalesProuductSelActivity.this.context,
							CreateGoodsActivity.class);
					SalesProuductSelActivity.this.startActivityForResult(paramAnonymousAdapterView, 0);
					continue;
					SalesProuductSelActivity.this.mIntent = new Intent(SalesProuductSelActivity.this,
							SalesHistoryActivity.class);
					SalesProuductSelActivity.this.startActivity(SalesProuductSelActivity.this.mIntent);
				}
			}
		});
		int i = (int) ResUtil.getDimens(2131099679);
		this.mPopStatu = new CustomPopupWindow(this, localListView, i);
		this.mPopStatu.setAnimationStyle(2131230758);
		this.mPopStatu.showMenu(this.mTopBar, getWindowManager().getDefaultDisplay().getWidth() - i, 0);
	}

	private void showPopUp(View paramView) {
		paramView = (ListView) ((Activity) this.context).getLayoutInflater().inflate(2130903178, null);
		paramView.setDivider(null);
		paramView.setBackgroundResource(2131427355);
		final String[] arrayOfString = new String[2];
		arrayOfString[0] = ResUtil.getString(2131165356);
		arrayOfString[1] = ResUtil.getString(2131165358);
		paramView.setAdapter(new SimpleTextAdapter(arrayOfString));
		paramView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView,
					int paramAnonymousInt, long paramAnonymousLong) {
				if (arrayOfString[paramAnonymousInt].endsWith(ResUtil.getString(2131165356))) {
					SalesProuductSelActivity.this.mTopBar.setTitle(2131165356);
					SalesProuductSelActivity.this.setSalesType(SalesType.SALE);
				}
				for (;;) {
					if ((SalesProuductSelActivity.this.mPopStatu != null)
							&& (SalesProuductSelActivity.this.mPopStatu.isShowing())) {
						SalesProuductSelActivity.this.mPopStatu.dismiss();
					}
					return;
					if (arrayOfString[paramAnonymousInt].endsWith(ResUtil.getString(2131165358))) {
						SalesProuductSelActivity.this.mTopBar.setTitle(2131165358);
						SalesProuductSelActivity.this.setSalesType(SalesType.WHOLESALE);
					}
				}
			}
		});
		this.mPopStatu = new CustomPopupWindow(this.context, paramView,
				((Activity) this.context).getWindowManager().getDefaultDisplay().getWidth());
		this.mPopStatu.setAnimationStyle(2131230759);
		this.mPopStatu.showMenu(this.mTopBar, 0, 0);
	}

	public void addBody(ViewGroup paramViewGroup) {
		getLayoutInflater().inflate(2130903091, paramViewGroup);
		this.mTopBar.setTitle(2131165356);
		this.mTopBar.setTitleClickListener(this);
		this.mTopBar.setLeftButton(2130837526, this);
		this.mTopBar.setFrontRightButton(2130837545, this);
		this.mTopBar.setRightButton(2130837525, this);
		this.mLsvGoods = ((ListView) paramViewGroup.findViewById(2131362508));
		this.mLsvGoods.setOnItemClickListener(this);
		this.mLsvGoods.setOnScrollListener(this);
		this.mGroupView = ((GroupSeleteView) paramViewGroup.findViewById(2131362249));
		this.mGroupView.setOnGroupChangeListener(this);
		this.mTxtTotalAmount = ((TextView) paramViewGroup.findViewById(2131361846));
		this.mTxtTotalMoney = ((TextView) paramViewGroup.findViewById(2131361845));
		findViewById(2131361844).setOnClickListener(this);
		findViewById(2131362584).setOnClickListener(this);
		this.mBtnReceivePay = ((Button) findViewById(2131362589));
		this.mBtnReceivePay.setOnClickListener(this);
		this.mImgScan = ((ImageView) paramViewGroup.findViewById(2131362248));
		this.mImgScan.setVisibility(8);
		this.mEdtSearch = ((EditText) paramViewGroup.findViewById(2131361842));
		this.mEdtSearch.setOnKeyListener(this);
		initBaseData();
		init();
	}

	protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
		super.onActivityResult(paramInt1, paramInt2, paramIntent);
		Bundle localBundle;
		Object localObject;
		switch (paramInt1) {
		default:
			this.mGroupView.refreshGroup();
			localBundle = null;
			localObject = localBundle;
			if (paramIntent != null) {
				localObject = localBundle;
				if (paramIntent.getExtras() != null) {
					localObject = (ArrayList) paramIntent.getExtras().getSerializable(Goods.class.getSimpleName());
				}
			}
			switch (paramInt2) {
			}
			break;
		}
		do {
			return;
		} while (paramIntent == null);
		paramIntent = paramIntent.getStringExtra(String.class.getSimpleName());
		this.condition = ("  AND vp.ProductSku_BarCode = " + paramIntent);
		resetGoods();
		return;
		paramIntent = new Intent();
		if ((localObject != null) && (((ArrayList) localObject).size() != 0)) {
			localBundle = new Bundle();
			localBundle.putSerializable(Goods.class.getSimpleName(), (Serializable) localObject);
			paramIntent.putExtras(localBundle);
		}
		setResult(0, paramIntent);
	}

	public void onBackPressed() {
		clearViewData();
		finish();
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		}
		do {
			return;
			this.mIntent = new Intent(this.context, SalesShoppingCartActivity.class);
			startActivity(this.mIntent);
			return;
			this.mIntent = new Intent(this.context, SalesActivity.class);
			startActivity(this.mIntent);
			return;
			this.mIntent = new Intent(this, CaptureActivity.class);
			this.mIntent.putExtra(BusinessType.class.getSimpleName(), BusinessType.SEARCH);
			this.mIntent.putExtra(MainActivity.class.getSimpleName(), false);
			startActivityForResult(this.mIntent, 4099);
			return;
			receivePay();
			return;
		} while (!hasShoppingCartItem());
		this.mIntent = new Intent(this.context, SalesActivity.class);
		startActivity(this.mIntent);
	}

	public void onGroupChange(long paramLong) {
		this.groupID = paramLong;
		resetGoods();
	}

	public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
		paramAdapterView = (V_ProductSkuOnSale) this.mListGoods.get(paramInt);
		if ((UserManager.getInstance().getSetting() != null)
				&& (!UserManager.getInstance().getSetting().isNegativeStockSale())
				&& (paramAdapterView.getOnhand_Quantity() - paramAdapterView.getQuantiy() <= 0.0D)) {
			ToastUtil.toastShort(this, 2131165319);
			return;
		}
		paramAdapterView.initShoppingCartItem(this.salesType);
		this.salesShoppingCart.addShoppingCartItem(paramAdapterView);
		initShoppingCartBarView();
	}

	public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent) {
		switch (paramView.getId()) {
		}
		for (;;) {
			return false;
			if ((paramKeyEvent.getAction() == 1) && (paramInt == 66)) {
				resetCondition();
				resetGoods();
			}
		}
	}

	protected void onRestart() {
		LogUtils.i("onRestart");
		initBaseData();
		init();
		super.onRestart();
	}

	public void onSaveSalesDocumentFail(String paramString) {
	}

	public void onSaveSalesDocumentSuccess(long paramLong) {
		this.transID = paramLong;
		if (paramLong > 0L) {
			this.handler.obtainMessage(17).sendToTarget();
		}
	}

	public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {
		this.visibleLastIndex = (paramInt1 + paramInt2 - 1);
	}

	public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
		int i = this.adapter.getCount();
		if ((paramInt == 0) && (this.visibleLastIndex == i - 1)) {
			this.mRefundIndex = (this.pageIndex * 10);
			if (this.adapter.getCount() >= this.mRefundIndex) {
				showWaitDialog();
				ExecutorUtils.EXECUTOR.execute(new Runnable() {
					public void run() {
						Object localObject = SalesProuductSelActivity.this;
						int i = ((SalesProuductSelActivity) localObject).pageIndex + 1;
						((SalesProuductSelActivity) localObject).pageIndex = i;
						localObject = SalesManager.getAllProduct(10, i, SalesProuductSelActivity.this.condition,
								SalesProuductSelActivity.this.groupID, SalesProuductSelActivity.this.salesType);
						SalesProuductSelActivity.this.handler.obtainMessage(2, localObject).sendToTarget();
					}
				});
			}
		} else {
			return;
		}
		dismissWaitDialog();
		ToastUtil.toastShort(this, 2131165317);
	}

	public void onTopBarFrontRightClick() {
		LogUtils.i("扫描");
		this.mIntent = new Intent(this, CaptureActivity.class);
		this.mIntent.putExtra(MainActivity.class.getSimpleName(), false);
		this.mIntent.putExtra(BusinessType.class.getSimpleName(), BusinessType.SALL);
		startActivity(this.mIntent);
	}

	public void onTopBarLeftClick() {
		clearViewData();
		finish();
	}

	public void onTopBarRightClick() {
		showFilter();
	}

	public void onTopBarTitleClick() {
		showPopUp(this.mTopBar);
	}
}