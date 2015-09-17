package com.zhizun.pos.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import com.zizun.cs.common.utils.ToastUtil;
import com.zhizun.pos.util.TextViewUtil;

public class EditCartItemDialog
  extends DialogFragment
  implements View.OnClickListener
{
  private EditText mEdtPrice;
  private EditText mEdtQuantity;
  private OnGoodsEditListener mStatusListener;
  private double price;
  private double quantity;
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    }
    String str;
    do
    {
      do
      {
        return;
        dismiss();
      } while (this.mStatusListener == null);
      paramView = TextViewUtil.getTextString(this.mEdtPrice);
      str = TextViewUtil.getTextString(this.mEdtQuantity);
    } while ((TextUtils.isEmpty(paramView)) || (TextUtils.isEmpty(str)));
    this.mStatusListener.onItemChange(Double.valueOf(paramView).doubleValue(), Double.valueOf(str).doubleValue());
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    setStyle(0, 2131230723);
    return super.onCreateDialog(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = paramLayoutInflater.inflate(2130903142, null);
    this.mEdtPrice = ((EditText)paramLayoutInflater.findViewById(2131362452));
    this.mEdtQuantity = ((EditText)paramLayoutInflater.findViewById(2131362453));
    this.mEdtPrice.setText(this.price);
    this.mEdtQuantity.setText(this.quantity);
    this.mEdtPrice.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable) {}
      
      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
      
      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        paramAnonymousInt1 = EditCartItemDialog.this.mEdtPrice.getText().toString().indexOf(".");
        if ((paramAnonymousInt1 != -1) && (EditCartItemDialog.this.mEdtPrice.getText().toString().substring(paramAnonymousInt1).length() > 3))
        {
          ToastUtil.toastShort(EditCartItemDialog.this.getActivity(), "只能输入两位小数");
          EditCartItemDialog.this.mEdtPrice.setText(EditCartItemDialog.this.mEdtPrice.getText().toString().substring(0, paramAnonymousInt1 + 3));
          EditCartItemDialog.this.mEdtPrice.setSelection(EditCartItemDialog.this.mEdtPrice.getText().toString().length());
        }
      }
    });
    paramLayoutInflater.findViewById(2131362451).setOnClickListener(this);
    return paramLayoutInflater;
  }
  
  public void setOnGoodsEditListener(OnGoodsEditListener paramOnGoodsEditListener)
  {
    this.mStatusListener = paramOnGoodsEditListener;
  }
  
  public void setPrice(double paramDouble)
  {
    this.price = paramDouble;
  }
  
  public void setQuantity(double paramDouble)
  {
    this.quantity = paramDouble;
  }
  
  public static abstract interface OnGoodsEditListener
  {
    public abstract void onItemChange(double paramDouble1, double paramDouble2);
  }
}