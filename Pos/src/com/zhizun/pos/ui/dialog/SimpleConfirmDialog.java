package com.zhizun.pos.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SimpleConfirmDialog
  extends DialogFragment
  implements View.OnClickListener
{
  private Button mBtnOk;
  private int mButtonName = -1;
  private OnConfirmListener mOnConfirmListener;
  private int mTitle;
  private TextView mTxtTitle;
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    }
    if (this.mOnConfirmListener != null) {
      this.mOnConfirmListener.onConfirm();
    }
    dismiss();
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    setStyle(0, 2131230723);
    return super.onCreateDialog(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903139, null);
    this.mTxtTitle = ((TextView)paramLayoutInflater.findViewById(2131362444));
    this.mBtnOk = ((Button)paramLayoutInflater.findViewById(2131362445));
    this.mTxtTitle.setText(this.mTitle);
    if (this.mButtonName != -1) {
      this.mBtnOk.setText(this.mButtonName);
    }
    this.mBtnOk.setOnClickListener(this);
    return paramLayoutInflater;
  }
  
  public void setButtonName(int paramInt)
  {
    this.mButtonName = paramInt;
  }
  
  public void setOnConfirmListener(OnConfirmListener paramOnConfirmListener)
  {
    this.mOnConfirmListener = paramOnConfirmListener;
  }
  
  public void setTitle(int paramInt)
  {
    this.mTitle = paramInt;
  }
  
  public static abstract interface OnConfirmListener
  {
    public abstract void onConfirm();
  }
}