package com.zhizun.pos.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import com.lidroid.xutils.util.LogUtils;
import com.zhizun.pos.ui.activity.receipt.ReceiptPreViewActivity;

public class WXReceiptSendDialog
  extends DialogFragment
  implements View.OnClickListener
{
  private OnWXReceiptSendEditFinishListener mEditListener;
  private EditText mEdtContent;
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131362474: 
      if (this.mEditListener != null)
      {
        paramView = this.mEdtContent.getText().toString().trim();
        if (!TextUtils.isEmpty(paramView)) {
          this.mEditListener.onSendFinish(paramView);
        }
      }
      dismiss();
      return;
    }
    if ((this.mEditListener != null) && (!TextUtils.isEmpty(this.mEdtContent.getText().toString().trim()))) {
      this.mEditListener.onSendCancel();
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
    paramLayoutInflater = paramLayoutInflater.inflate(2130903154, null);
    this.mEdtContent = ((EditText)paramLayoutInflater.findViewById(2131362447));
    paramLayoutInflater.findViewById(2131362474).setOnClickListener(this);
    paramLayoutInflater.findViewById(2131362473).setOnClickListener(this);
    return paramLayoutInflater;
  }
  
  public void onDetach()
  {
    LogUtils.i("onDestroyView");
    ReceiptPreViewActivity localReceiptPreViewActivity = (ReceiptPreViewActivity)getActivity();
    localReceiptPreViewActivity.printData();
    localReceiptPreViewActivity.finish();
    super.onDetach();
  }
  
  public void setOnWXReceiptSendEditFinishDialogListener(OnWXReceiptSendEditFinishListener paramOnWXReceiptSendEditFinishListener)
  {
    this.mEditListener = paramOnWXReceiptSendEditFinishListener;
  }
  
  public static abstract interface OnWXReceiptSendEditFinishListener
  {
    public abstract void onSendCancel();
    
    public abstract void onSendFinish(String paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\dialog\WXReceiptSendDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */