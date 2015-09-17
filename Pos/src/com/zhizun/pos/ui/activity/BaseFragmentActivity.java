package com.zhizun.pos.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.ui.dialog.WaitProgressDialog;

public class BaseFragmentActivity
  extends FragmentActivity
{
  private WaitProgressDialog mProDialog;
  
  protected void dismissWaitDialog()
  {
    if (this.mProDialog != null)
    {
      this.mProDialog.close();
      this.mProDialog = null;
    }
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 0)
    {
      View localView = getCurrentFocus();
      if (isShouldHideInput(localView, paramMotionEvent))
      {
        InputMethodManager localInputMethodManager = (InputMethodManager)getSystemService("input_method");
        if (localInputMethodManager != null) {
          localInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 0);
        }
      }
      return super.dispatchTouchEvent(paramMotionEvent);
    }
    if (getWindow().superDispatchTouchEvent(paramMotionEvent)) {
      return true;
    }
    return onTouchEvent(paramMotionEvent);
  }
  
  protected String getEdtText(TextView paramTextView)
  {
    return paramTextView.getText().toString().trim();
  }
  
  public boolean isShouldHideInput(View paramView, MotionEvent paramMotionEvent)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    if ((paramView != null) && ((paramView instanceof EditText)))
    {
      int[] arrayOfInt = new int[2];
      paramView.getLocationInWindow(arrayOfInt);
      i = arrayOfInt[0];
      j = arrayOfInt[1];
      k = paramView.getHeight();
      m = paramView.getWidth();
    }
    return (paramMotionEvent.getX() <= i) || (paramMotionEvent.getX() >= i + m) || (paramMotionEvent.getY() <= j) || (paramMotionEvent.getY() >= j + k);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    StoreApplication.addActivity(this);
  }
  
  protected void showWaitDialog()
  {
    showWaitDialog("");
  }
  
  protected void showWaitDialog(String paramString)
  {
    if (this.mProDialog == null) {
      this.mProDialog = new WaitProgressDialog(this);
    }
    this.mProDialog.open();
    this.mProDialog.setMessage(paramString);
    this.mProDialog.setCancelable(true);
  }
}