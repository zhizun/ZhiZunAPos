package com.zhizun.pos.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.zhizun.pos.util.ResUtil;

public class TitleTopBar
  extends BaseTopBar
{
  private TextView mTxtTitle;
  
  public TitleTopBar(Context paramContext)
  {
    super(paramContext);
    init();
  }
  
  public TitleTopBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  private void init()
  {
    this.mTxtTitle = new TextView(getContext());
    this.mTxtTitle.setTextColor(ResUtil.getColor(2131427328));
    this.mTxtTitle.setTextSize(0, ResUtil.getDimens(2131099654));
    setCenterBody(this.mTxtTitle);
  }
  
  public void setTitle(int paramInt)
  {
    this.mTxtTitle.setText(paramInt);
  }
  
  public void setTitle(String paramString)
  {
    this.mTxtTitle.setText(paramString);
  }
  
  public void setTitleClickListener(final OnTopBarTitleClickListener paramOnTopBarTitleClickListener)
  {
    if (paramOnTopBarTitleClickListener != null)
    {
      Drawable localDrawable = getResources().getDrawable(2130837628);
      int i = (int)ResUtil.getDimens(2131099685);
      localDrawable.setBounds(0, 0, i, i);
      this.mTxtTitle.setCompoundDrawables(null, null, localDrawable, null);
      this.mTxtTitle.setEllipsize(TextUtils.TruncateAt.END);
      this.mTxtTitle.setSingleLine(true);
      this.mTxtTitle.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          paramOnTopBarTitleClickListener.onTopBarTitleClick();
        }
      });
    }
  }
  
  public static abstract interface OnTopBarTitleClickListener
  {
    public abstract void onTopBarTitleClick();
  }
}