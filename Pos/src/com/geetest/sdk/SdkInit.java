package com.geetest.sdk;

import android.content.Context;

public class SdkInit
{
  private String captcha_id = "";
  private String challenge_id = "";
  private Context context;
  
  public String getCaptcha_id()
  {
    return this.captcha_id;
  }
  
  public String getChallenge_id()
  {
    return this.challenge_id;
  }
  
  public Context getContext()
  {
    return this.context;
  }
  
  public void setCaptcha_id(String paramString)
  {
    this.captcha_id = paramString;
  }
  
  public void setChallenge_id(String paramString)
  {
    this.challenge_id = paramString;
  }
  
  public void setContext(Context paramContext)
  {
    this.context = paramContext;
  }
}