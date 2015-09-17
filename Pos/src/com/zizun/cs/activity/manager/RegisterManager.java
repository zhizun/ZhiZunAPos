package com.zizun.cs.activity.manager;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zizun.cs.R.string;
import com.zizun.cs.biz.api.ImgAPI;
import com.zizun.cs.biz.api.IndustryAPI;
import com.zizun.cs.biz.api.SMSAPI;
import com.zizun.cs.biz.api.UserAPI;
import com.zizun.cs.biz.dao.trans.RegisterTrans;
import com.zizun.cs.biz.dao.utils.MyDbUtils;
import com.zizun.cs.common.utils.FileUtil;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.entities.S_Merchant;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.api.ImgParam;
import com.zizun.cs.entities.api.ImgResult;
import com.zizun.cs.entities.api.IndustryGetResult;
import com.zizun.cs.entities.api.LocalLoginParam;
import com.zizun.cs.entities.api.LoginResult;
import com.zizun.cs.entities.api.LoginResult.LoginResultData;
import com.zizun.cs.entities.api.RegisterParam;
import com.zizun.cs.entities.api.RegisterResult;
import com.zizun.cs.entities.api.RegisterResult.RegisterResultData;
import com.zizun.cs.entities.api.SMSFindPswdParam;
import com.zizun.cs.entities.api.SMSRegisterParam;
import com.zizun.cs.entities.api.SMSResult;
import com.zizun.cs.entities.api.UserNameCheckParam;
import com.zizun.cs.entities.api.UserNameCheckResult;
import com.zhizun.pos.app.StoreApplication;
import java.io.IOException;
import java.util.List;

public class RegisterManager
{
  private Context context = StoreApplication.getContext();
  
  public static void closeExistDatabase() {}
  
  public void CopyDBToSDCardOutUIThread(int paramInt)
  {
    try
    {
      FileUtil.CopyDbToSdcard(this.context, MyDbUtils.getDBName(), MyDbUtils.DB_PATH + paramInt + "/");
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public void checkUserName(UserNameCheckParam paramUserNameCheckParam, RequestCallBack<String> paramRequestCallBack)
  {
    UserAPI.CheckUserName(paramUserNameCheckParam, paramRequestCallBack);
  }
  
  public void doRegister(RegisterParam paramRegisterParam, RequestCallBack<String> paramRequestCallBack)
  {
    UserAPI.register(paramRegisterParam, paramRequestCallBack);
  }
  
  public void doRegister(String paramString1, String paramString2, String paramString3, RequestCallBack<String> paramRequestCallBack)
  {
    RegisterParam localRegisterParam = new RegisterParam();
    localRegisterParam.setUser_Name(paramString1);
    localRegisterParam.setUser_Password(paramString2);
    localRegisterParam.setUser_Mobile(paramString1);
    localRegisterParam.setStore_Name(ResUtil.getString(R.string.default_store_name));
    localRegisterParam.setUser_Email("");
    localRegisterParam.setTerminal_Type(1);
    localRegisterParam.setTerminal_Version("1.0");
    localRegisterParam.setIndustry_ID(0);
    localRegisterParam.setRecommend_Info(paramString3);
    localRegisterParam.setMobileID(((TelephonyManager)StoreApplication.getContext().getSystemService("phone")).getDeviceId());
    UserAPI.register(localRegisterParam, paramRequestCallBack);
  }
  
  public void getIndustry(RequestCallBack<String> paramRequestCallBack)
  {
    IndustryAPI.getIndustryFromAPI(paramRequestCallBack);
  }
  
  public IndustryGetResult getIndustryResultFromJson(String paramString)
  {
    return (IndustryGetResult)IndustryAPI.getAPIResultFromJson(paramString, IndustryGetResult.class);
  }
  
  public RegisterResult getRegisterResultFromJson(String paramString)
  {
    return (RegisterResult)UserAPI.getAPIResultFromJson(paramString, RegisterResult.class);
  }
  
  public SMSResult getSendRegSMSResultFromJson(String paramString)
  {
    return (SMSResult)SMSAPI.getAPIResultFromJson(paramString, SMSResult.class);
  }
  
  public ImgResult getUploadImgResultFromJson(String paramString)
  {
    return (ImgResult)ImgAPI.getAPIResultFromJson(paramString, ImgResult.class);
  }
  
  public UserNameCheckResult getUserNameCheckResultFromJson(String paramString)
  {
    return (UserNameCheckResult)UserAPI.getAPIResultFromJson(paramString, UserNameCheckResult.class);
  }
  
  public boolean handleRegisterResult(RegisterResult paramRegisterResult, String paramString)
  {
    ManagerFactory.reSet();
    S_User localS_User = paramRegisterResult.Data.S_User;
    localS_User.setUser_Password(paramString);
    UserManager.getInstance().setCurrentUser(localS_User);
    UserManager.getInstance().getCurrentUser().setMerchant_ID(paramRegisterResult.Data.S_Merchant.getMerchant_ID());
    UserManager.getInstance().setCurrentStore((Store)paramRegisterResult.Data.Store.get(0));
    UserManager.getInstance().setCurrentModuleList(paramRegisterResult.Data.S_Module);
    UserManager.getInstance().setCurrentUserStoreList(paramRegisterResult.Data.Store);
    CopyDBToSDCardOutUIThread(localS_User.getMerchant_ID());
    paramString = new LocalLoginParam(localS_User.getMerchant_ID(), localS_User.getUser_Name(), localS_User.getUser_Password());
    new LoginManager(StoreApplication.getContext()).saveLocalLoginParamToPreference(paramString);
    paramString = new LoginResult();
    paramString.getClass();
    LoginResult.LoginResultData localLoginResultData = new LoginResult.LoginResultData(paramString);
    localLoginResultData.setS_Merchant(paramRegisterResult.Data.S_Merchant);
    localLoginResultData.setS_Module(paramRegisterResult.Data.S_Module);
    localLoginResultData.setS_User(localS_User);
    localLoginResultData.setStore(paramRegisterResult.Data.Store);
    paramString.setData(localLoginResultData);
    UserManager.getInstance().setLoginResult(paramString);
    return ManagerFactory.getRegisterTrans().doRegisterTransaction(paramRegisterResult.Data);
  }
  
  public boolean saveLoginResultOnLineOutUIThread(RegisterResult.RegisterResultData paramRegisterResultData)
  {
    return ManagerFactory.getRegisterTrans().doRegisterTransaction(paramRegisterResultData);
  }
  
  public void sendFindPswdSMS(SMSFindPswdParam paramSMSFindPswdParam, RequestCallBack<String> paramRequestCallBack)
  {
    SMSAPI.sendFindPswdSMS(paramSMSFindPswdParam, paramRequestCallBack);
  }
  
  public void sendRegisterSMS(SMSRegisterParam paramSMSRegisterParam, RequestCallBack<String> paramRequestCallBack)
  {
    SMSAPI.sendRegisterSMS(paramSMSRegisterParam, paramRequestCallBack);
  }
  
  public void uploadMerchantImg(ImgParam paramImgParam, RequestCallBack<String> paramRequestCallBack)
  {
    ImgAPI.uploadMerchantImg(paramImgParam, paramRequestCallBack);
  }
}