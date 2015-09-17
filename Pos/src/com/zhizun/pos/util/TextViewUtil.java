package com.zhizun.pos.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.common.utils.ToastUtil;
import com.zhizun.pos.app.StoreApplication;

public class TextViewUtil
{
  public static void addInputLimitTwoDecimal(EditText paramEditText)
  {
    paramEditText.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable) {}
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        paramAnonymousInt1 = TextViewUtil.this.getText().toString().indexOf(".");
        if ((paramAnonymousInt1 != -1) && (TextViewUtil.this.getText().toString().substring(paramAnonymousInt1).length() > 3))
        {
          ToastUtil.toastShort(StoreApplication.getContext(), 2131165326);
          TextViewUtil.this.setText(TextViewUtil.this.getText().toString().substring(0, paramAnonymousInt1 + 3));
          TextViewUtil.this.setSelection(TextViewUtil.this.getText().toString().length());
        }
      }
    });
  }
  
  public static void defendSoftInput(EditText paramEditText)
  {
    paramEditText.setFocusable(false);
    paramEditText.setFocusableInTouchMode(false);
    paramEditText.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        LogUtils.i("editText click");
        TextViewUtil.this.setFocusable(false);
        TextViewUtil.this.setFocusableInTouchMode(false);
      }
    });
    paramEditText.setOnLongClickListener(new View.OnLongClickListener()
    {
      public boolean onLongClick(View paramAnonymousView)
      {
        TextViewUtil.this.setFocusableInTouchMode(true);
        TextViewUtil.this.setFocusable(true);
        TextViewUtil.this.requestFocus();
        return false;
      }
    });
  }
  
  public static Double getTextPrice(TextView paramTextView)
  {
    paramTextView = paramTextView.getText().toString().trim().replace(",", "");
    if (TextUtils.isEmpty(paramTextView)) {
      return Double.valueOf(0.0D);
    }
    return Double.valueOf(paramTextView);
  }
  
  public static String getTextString(TextView paramTextView)
  {
    return paramTextView.getText().toString().trim();
  }
  
  public static String trimPriceComma(String paramString)
  {
    return new String(paramString).replaceAll(",", "");
  }
}