package com.zizun.cs.activity.manager;

import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.dao.S_ModuleDao;
import com.zizun.cs.biz.enumType.SalesType;
import com.zizun.cs.common.utils.PreferencesUtil;
import com.zizun.cs.define.Module_Code;
import com.zizun.cs.entities.S_Module;
import com.zizun.cs.entities.S_Role;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.api.LocalLoginParam;
import com.zizun.cs.entities.api.LoginResult;
import com.zizun.cs.entities.biz.V_Parameter;
import com.zizun.cs.ui.entity.Module;
import com.zizun.cs.ui.entity.Setting;
import com.zhizun.pos.app.StoreApplication;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserManager {
	private static S_Role currentRole;
	private static ArrayList<Module> listModule;
	private static UserManager mInstance;
	private static PreferencesUtil preferencesUtil;
	private List<S_Module> currentModuleList;
	private Map<String, String> currentParameterMap;
	private String currentPaymentMethod;
	private SalesType currentSalesType;
	private Store currentStore;
	private List<V_Parameter> currentStoreStaffParamerterList;
	private S_User currentUser;
	private List<Store> currentUserStoreList;
	private boolean isCustomerAvailable;
	private boolean isExperienceAccount;
	private boolean isSupplierAvailable;
	private LocalLoginParam localLoginParam;
	private LoginResult loginResult;
	private Setting setting;

	private UserManager() {
		initUser();
	}

	public static void clear() {
		listModule = null;
		currentRole = null;
		mInstance = null;
	}

	public static UserManager getInstance() {
		if (mInstance == null) {
			mInstance = new UserManager();
		}
		return mInstance;
	}

	private void initUser() {
		preferencesUtil = new PreferencesUtil(StoreApplication.getContext());
		getCurrentUser();
		getCurrentStore();
		getCurrentModuleList();
		if (this.currentUser == null) {
			LogUtils.i("currentUser 获取失败");
		}
		if (this.currentStore == null) {
			LogUtils.i("currentStore 获取失败");
		}
		if (this.currentModuleList == null) {
			LogUtils.i("currentModuleList 获取失败");
		}
	}

	public List<S_Module> getCurrentModuleList() {
		if (this.currentModuleList == null) {
			this.currentModuleList = preferencesUtil.getObjectList("CURRENT_MODULES", S_Module.class);
		}
		return this.currentModuleList;
	}

	public Map<String, String> getCurrentParameterMap() {
		if (this.currentParameterMap == null) {
			this.currentParameterMap = ((Map) preferencesUtil.getObject("CURRENT_PARAMERTER_MAP", Map.class));
		}
		return this.currentParameterMap;
	}

	public String getCurrentPaymentMethod() {
		if (this.currentPaymentMethod == null) {
			this.currentPaymentMethod = ((String) preferencesUtil.getObject("CURRENT_PAYMENTMETHOD", String.class));
		}
		return this.currentPaymentMethod;
	}

	public SalesType getCurrentSalesType() {
		if (this.currentSalesType == null) {
			this.currentSalesType = ((SalesType) preferencesUtil.getObject("CURRENT_SALES_TYPE", SalesType.class));
		}
		return this.currentSalesType;
	}

	public Store getCurrentStore() {
		if (this.currentStore == null) {
			this.currentStore = ((Store) preferencesUtil.getObject("CURRENT_STORE", Store.class));
		}
		return this.currentStore;
	}

	public List<V_Parameter> getCurrentStoreStaffParamerterList() {
		if (this.currentStoreStaffParamerterList == null) {
			this.currentStoreStaffParamerterList = preferencesUtil.getObjectList("CURRENT_PARAMERTER_LIST",
					V_Parameter.class);
		}
		return this.currentStoreStaffParamerterList;
	}

	public S_User getCurrentUser() {
		if (this.currentUser == null) {
			this.currentUser = ((S_User) preferencesUtil.getObject("CURRENT_USER", S_User.class));
		}
		return this.currentUser;
	}

	public List<Store> getCurrentUserStoreList() {
		if (this.currentUserStoreList == null) {
			this.currentUserStoreList = preferencesUtil.getObjectList("CURRENT_USER_STORE_LIST", Store.class);
		}
		return this.currentUserStoreList;
	}

	public ArrayList<Module> getListModule() {
		Iterator localIterator;
		if (listModule == null) {
			listModule = ManagerFactory.getS_ModuleDao().getAvailableModule();
			localIterator = listModule.iterator();
		}
		for (;;) {
			if (!localIterator.hasNext()) {
				return listModule;
			}
			Module localModule = (Module) localIterator.next();
			if (localModule.getModule_Code() == Module_Code.Vip) {
				this.isCustomerAvailable = true;
			}
			if (localModule.getModule_Code() == Module_Code.Vendor) {
				this.isSupplierAvailable = true;
			}
		}
	}

	public LocalLoginParam getLocalLoginParam() {
		if ((this.localLoginParam == null) && (this.currentUser != null)) {
			this.localLoginParam = ((LocalLoginParam) new PreferencesUtil(StoreApplication.getContext(), "NAME_LOGIN")
					.getObject(this.currentUser.getUser_Name(), LocalLoginParam.class));
		}
		return this.localLoginParam;
	}

	public LoginResult getLoginResult() {
		if (this.loginResult == null) {
			this.loginResult = ((LoginResult) preferencesUtil.getObject("LOGIN_RESULT", LoginResult.class));
		}
		return this.loginResult;
	}

	public S_Role getS_Role() {
		return currentRole;
	}

	public Setting getSetting() {
		if (this.setting == null) {
			this.setting = ((Setting) preferencesUtil.getObject("USER_SETTING", Setting.class));
		}
		if (this.setting == null) {
			this.setting = new Setting();
			this.setting.setProductImportAlat(true);
			this.setting.setNegativeStockSale(true);
		}
		return this.setting;
	}

	public boolean isCustomerAvailable() {
		return this.isCustomerAvailable;
	}

	public boolean isExperienceAccount() {
		return this.isExperienceAccount;
	}

	public boolean isSupplierAvailable() {
		return this.isSupplierAvailable;
	}

	public void saveSetting() {
		preferencesUtil.saveObject("USER_SETTING", this.setting);
	}

	public void setCurrentModuleList(List<S_Module> paramList) {
		if (paramList != null) {
			preferencesUtil.saveObject("CURRENT_MODULES", paramList);
			LogUtils.i("CURRENT_MODULES 设置");
		}
		this.currentModuleList = paramList;
	}

	public void setCurrentParameterMap(Map<String, String> paramMap) {
		LogUtils.i("CURRENT_PARAMERTER_MAP 设置");
		preferencesUtil.saveObject("CURRENT_PARAMERTER_MAP", paramMap);
		this.currentParameterMap = paramMap;
	}

	public void setCurrentPaymentMethod(String paramString) {
		LogUtils.i("CURRENT_PAYMENTMETHOD 设置");
		preferencesUtil.saveObject("CURRENT_PAYMENTMETHOD", paramString);
		this.currentPaymentMethod = paramString;
	}

	public void setCurrentSalesType(SalesType paramSalesType) {
		LogUtils.i("CURRENT_SALES_TYPE 设置");
		preferencesUtil.saveObject("CURRENT_SALES_TYPE", paramSalesType);
		this.currentSalesType = paramSalesType;
	}

	public void setCurrentStore(Store paramStore) {
		if (paramStore != null) {
			preferencesUtil.saveObject("CURRENT_STORE", paramStore);
			LogUtils.i("CURRENT_STORE 设置");
		}
		this.currentStore = paramStore;
	}

	public void setCurrentStoreStaffParamerterList(List<V_Parameter> paramList) {
		LogUtils.i("CURRENT_PARAMERTER_LIST 设置");
		preferencesUtil.saveObject("CURRENT_PARAMERTER_LIST", paramList);
		this.currentStoreStaffParamerterList = paramList;
	}

	public void setCurrentUser(S_User paramS_User) {
		if (paramS_User != null) {
			preferencesUtil.saveObject("CURRENT_USER", paramS_User);
			LogUtils.i("CURRENT_USER 设置");
		}
		this.currentUser = paramS_User;
	}

	public void setCurrentUserStoreList(List<Store> paramList) {
		if (paramList != null) {
			preferencesUtil.saveObject("CURRENT_USER_STORE_LIST", paramList);
			LogUtils.i("CURRENT_USER_STORE_LIST 设置");
		}
		this.currentUserStoreList = paramList;
	}

	public void setCustomerAvailable(boolean paramBoolean) {
		this.isCustomerAvailable = paramBoolean;
	}

	public void setExperienceAccount(boolean paramBoolean) {
		this.isExperienceAccount = paramBoolean;
	}

	public void setListModule(ArrayList<Module> paramArrayList) {
		listModule = paramArrayList;
	}

	public void setLocalLoginParam(LocalLoginParam paramLocalLoginParam) {
		this.localLoginParam = paramLocalLoginParam;
	}

	public void setLoginResult(LoginResult paramLoginResult) {
		preferencesUtil.saveObject("LOGIN_RESULT", paramLoginResult);
		LogUtils.i("loginResult 设置");
		this.loginResult = paramLoginResult;
	}

	public void setS_Role(S_Role paramS_Role) {
		currentRole = paramS_Role;
	}

	public void setSupplierAvailable(boolean paramBoolean) {
		this.isSupplierAvailable = paramBoolean;
	}
}