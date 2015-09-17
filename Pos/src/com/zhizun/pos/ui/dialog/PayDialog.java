package com.zhizun.pos.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PayDialog
  extends DialogFragment
  implements View.OnClickListener
{
  private OnCreateViewListener createViewListener;
  private String mContentText;
  private OnPayFinishDialogListener mEditListener;
  private ImageView mImageView;
  private TextView mTvContent;
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    }
    if (this.mEditListener != null)
    {
      paramView = this.mTvContent.getText().toString().trim();
      this.mEditListener.onPayFinish(paramView);
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
    paramLayoutInflater = paramLayoutInflater.inflate(2130903153, null);
    this.mImageView = ((ImageView)paramLayoutInflater.findViewById(2131362471));
    this.mTvContent = ((TextView)paramLayoutInflater.findViewById(2131362472));
    this.mTvContent.setText(this.mContentText);
    paramLayoutInflater.findViewById(2131362448).setOnClickListener(this);
    if (this.createViewListener != null) {
      this.createViewListener.onCreateView(this.mImageView);
    }
    return paramLayoutInflater;
  }
  
  public void setContentText(String paramString)
  {
    this.mContentText = paramString;
  }
  
  public void setOnCreateViewListener(OnCreateViewListener paramOnCreateViewListener)
  {
    this.createViewListener = paramOnCreateViewListener;
  }
  
  public void setOnPayFinishDialogListener(OnPayFinishDialogListener paramOnPayFinishDialogListener)
  {
    this.mEditListener = paramOnPayFinishDialogListener;
  }
  
  public static abstract interface OnCreateViewListener
  {
    public abstract void onCreateView(ImageView paramImageView);
  }
  
  public static abstract interface OnPayFinishDialogListener
  {
    public abstract void onPayFinish(String paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\dialog\PayDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */