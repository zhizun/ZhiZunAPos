package com.zhizun.pos.ui.dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import com.zizun.cs.common.utils.LogUtil;

public abstract class BaseAlertDialog
  extends AlertDialog
{
  protected AlertDialog mAlertDialog;
  protected Context mContext;
  
  public BaseAlertDialog(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  public BaseAlertDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }
  
  public void close()
  {
    try
    {
      this.mAlertDialog.dismiss();
      this.mAlertDialog.cancel();
      return;
    }
    catch (Exception localException)
    {
      LogUtil.logD(localException.toString());
    }
  }
  
  public void open()
  {
    this.mAlertDialog = new AlertDialog.Builder(this.mContext, progress_dialog_theme).create();
    this.mAlertDialog.show();
  }
  
  public void open(boolean paramBoolean)
  {
    this.mAlertDialog = new AlertDialog.Builder(this.mContext, progress_dialog_theme).create();
    this.mAlertDialog.setCancelable(paramBoolean);
    this.mAlertDialog.show();
  }
  
  public void setHeart(int paramInt)
  {
    this.mAlertDialog.setContentView(paramInt);
  }
}