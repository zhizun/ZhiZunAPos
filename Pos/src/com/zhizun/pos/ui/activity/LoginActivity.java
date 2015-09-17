package com.zhizun.pos.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.impl.S_MerchantDaoImpl;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zizun.cs.entities.S_Merchant;
import com.zizun.cs.entities.api.LocalLoginParam;
import com.zhizun.pos.service.ExpLoginService;
import com.zhizun.pos.service.ExpLoginService.OnExpLoginListener;
import com.zhizun.pos.service.LoginService;
import com.zhizun.pos.service.LoginService.OnLoginListener;
import com.zhizun.pos.service.SyncService;
import com.zhizun.pos.xujie.NetUtil;

public class LoginActivity
  extends BaseActivity
  implements View.OnClickListener, LoginService.OnLoginListener, ExpLoginService.OnExpLoginListener
{
  private Context context;
  private LocalLoginParam localLoginParam;
  private TextView mBtnExperience;
  private TextView mBtnFindPwd;
  private Button mBtnLogin;
  private EditText mEdtPwd;
  private EditText mEdtUserName;
  private ImageView mIvHead;
  private UserInputInfo userInputInfo;
  
  private void expLogin()
  {
    ExpLoginService.setOnExpLoginListener(this);
    this.mBtnLogin.setEnabled(false);
    startService(new Intent(this.context, ExpLoginService.class));
  }
  
  private void initData()
  {
    this.context = this;
    this.localLoginParam = UserManager.getInstance().getLocalLoginParam();
    if (this.localLoginParam != null)
    {
      this.mEdtUserName.setText(this.localLoginParam.userName);
      this.mEdtPwd.setText(this.localLoginParam.userPswd);
      loadBitmap();
      login();
    }
  }
  
  private boolean initUserInputInfo()
  {
    String str1 = getEdtText(this.mEdtUserName);
    String str2 = getEdtText(this.mEdtPwd);
    if ((str1 == null) || (TextUtils.isEmpty(str1)))
    {
      ToastUtil.toastShort(this.context, "用户名为空");
      return false;
    }
    if ((str2 == null) || (TextUtils.isEmpty(str2)))
    {
      ToastUtil.toastShort(this.context, "密码为空");
      return false;
    }
    this.userInputInfo = new UserInputInfo();
    this.userInputInfo.userName = str1;
    this.userInputInfo.userPassWord = str2;
    return true;
  }
  
  private void initView()
  {
    this.mEdtUserName = ((EditText)findViewById(activity_login_edtUserName));
    this.mBtnLogin = ((Button)findViewById(activity_login_btnLogin));
    this.mBtnExperience = ((TextView)findViewById(activity_login_txtExperience));
    this.mEdtPwd = ((EditText)findViewById(activity_login_edtPwd));
    this.mBtnFindPwd = ((TextView)findViewById(activity_login_txtFindPwd));
    this.mIvHead = ((ImageView)findViewById(activity_login_imgPortrait));
    this.mBtnLogin.setOnClickListener(this);
    this.mBtnExperience.setOnClickListener(this);
    this.mBtnFindPwd.setOnClickListener(this);
  }
  
  private void loadBitmap()
  {
    this.context = this;
    String str = new S_MerchantDaoImpl().getMerchant().getMerchant_Image();
    if (!TextUtils.isEmpty(str))
    {
      if (NetUtil.checkNet(this))
      {
        str = ImgUtil.getImgPathFromSev(str);
        ImgUtil.showBig(this.mIvHead, str);
        return;
      }
      this.mIvHead.setImageResource(login_portrait_default);
      return;
    }
    this.mIvHead.setImageResource(login_portrait_default);
  }
  
  private void login()
  {
    if (initUserInputInfo())
    {
      this.mBtnLogin.setEnabled(false);
      Intent localIntent = new Intent(this.context, LoginService.class);
      localIntent.putExtra("userName", this.userInputInfo.userName);
      localIntent.putExtra("userPassword", this.userInputInfo.userPassWord);
      startService(localIntent);
    }
  }
  
  public void onBaseSyncFail(String paramString)
  {
    this.mBtnLogin.setEnabled(true);
  }
  
  public void onBaseSyncSuccess()
  {
    this.mBtnLogin.setEnabled(true);
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case activity_login_btnLogin: 
      login();
      return;
    case activity_login_txtExperience: 
      expLogin();
      return;
    }
    startActivity(new Intent(this.context, FindPwdActivity.class));
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(activity_login);
    initView();
    initData();
    LoginService.setOnLoginListener(this);
  }
  
  protected void onDestroy()
  {
    LoginService.clearOnLoginListener();
    ExpLoginService.clearOnExpLoginListener();
    super.onDestroy();
  }
  
  public void onExpLoginFail(String paramString) {}
  
  public void onExpLoginSuccess()
  {
    ToastUtil.toastLong(this.context, "亲，你当前使用的体验账号，数据不能同步到服务器!");
    SyncService.stopService(this);
    this.mBtnLogin.setEnabled(true);
  }
  
  public void onLoginFail(String paramString)
  {
    ToastUtil.toastLong(this.context, paramString);
    this.mBtnLogin.setEnabled(true);
  }
  
  public void onLoginSuccess()
  {
    this.mBtnLogin.setEnabled(true);
  }
  
  protected void onRestart()
  {
    super.onRestart();
  }
  
  class UserInputInfo
  {
    public String userName;
    public String userPassWord;
    
    UserInputInfo() {}
  }
}