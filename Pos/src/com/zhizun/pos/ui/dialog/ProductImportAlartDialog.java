package com.zhizun.pos.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ProductImportAlartDialog
  extends DialogFragment
  implements View.OnClickListener
{
  private OnAlartEditListener mAlartListener;
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    }
    do
    {
      do
      {
        return;
        dismiss();
      } while (this.mAlartListener == null);
      this.mAlartListener.onConfire();
      return;
      dismiss();
    } while (this.mAlartListener == null);
    this.mAlartListener.onAlart();
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    setStyle(0, 2131230723);
    return super.onCreateDialog(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903148, null);
    paramLayoutInflater.findViewById(2131362461).setOnClickListener(this);
    paramLayoutInflater.findViewById(2131362462).setOnClickListener(this);
    return paramLayoutInflater;
  }
  
  public void setOnAlartListener(OnAlartEditListener paramOnAlartEditListener)
  {
    this.mAlartListener = paramOnAlartEditListener;
  }
  
  public static abstract interface OnAlartEditListener
  {
    public abstract void onAlart();
    
    public abstract void onConfire();
  }
}