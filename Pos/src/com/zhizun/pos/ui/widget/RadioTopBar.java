package com.zhizun.pos.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RadioTopBar
  extends BaseTopBar
  implements RadioGroup.OnCheckedChangeListener
{
  private OnTopBarOptionChangeListener mOnTopBarOptionChangeListener;
  private RadioButton radioLeftButton;
  private RadioButton radioRightButton;
  
  public RadioTopBar(Context paramContext)
  {
    super(paramContext);
    init();
  }
  
  public RadioTopBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  private void init()
  {
    setCenterBody((LinearLayout)LayoutInflater.from(getContext()).inflate(2130903263, null));
    ((RadioGroup)findViewById(2131362801)).setOnCheckedChangeListener(this);
    this.radioLeftButton = ((RadioButton)findViewById(2131362802));
    this.radioRightButton = ((RadioButton)findViewById(2131362803));
  }
  
  public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt)
  {
    switch (paramInt)
    {
    }
    do
    {
      do
      {
        return;
      } while (this.mOnTopBarOptionChangeListener == null);
      this.mOnTopBarOptionChangeListener.onTopBarLeftOptionSelect();
      return;
    } while (this.mOnTopBarOptionChangeListener == null);
    this.mOnTopBarOptionChangeListener.onTopBarRightOptionSelect();
  }
  
  public void setLeftOptionText(int paramInt)
  {
    this.radioLeftButton.setText(paramInt);
  }
  
  public void setOnOptionChangeListener(OnTopBarOptionChangeListener paramOnTopBarOptionChangeListener)
  {
    this.mOnTopBarOptionChangeListener = paramOnTopBarOptionChangeListener;
  }
  
  public void setRightOptionText(int paramInt)
  {
    this.radioRightButton.setText(paramInt);
  }
  
  public static abstract interface OnTopBarOptionChangeListener
  {
    public abstract void onTopBarLeftOptionSelect();
    
    public abstract void onTopBarRightOptionSelect();
  }
}