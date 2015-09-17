package com.zhizun.pos.ui.fragment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

public class BaseFragment
  extends Fragment
{
  private ProgressDialog mProDialog;
  
  protected void dismissWaitDialog()
  {
    if (this.mProDialog != null)
    {
      this.mProDialog.dismiss();
      this.mProDialog = null;
    }
  }
  
  protected void showWaitDialog()
  {
    showWaitDialog("");
  }
  
  protected void showWaitDialog(String paramString)
  {
    if (this.mProDialog == null)
    {
      this.mProDialog = new ProgressDialog(getActivity(), 2131230723);
      this.mProDialog.show();
      this.mProDialog.setMessage(paramString);
      this.mProDialog.setCancelable(true);
    }
  }
}