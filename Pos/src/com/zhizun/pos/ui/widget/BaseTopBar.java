package com.zhizun.pos.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseTopBar
  extends FrameLayout
  implements View.OnClickListener
{
  private OnTopBarBehindLeftClickListener mBehindLeftClickListener;
  private OnTopBarFrontRightClickListener mFrontRightListener;
  private ImageView mImgLeftBtn;
  private ImageView mImgLeftBtn2;
  private ImageView mImgRightBtn1;
  private ImageView mImgRightBtn2;
  private OnTopBarLeftClickListener mLeftListener;
  private LinearLayout mLinCenter;
  private OnTopBarRightClickListener mRightListener;
  private TextView mTxtLeft;
  private TextView mTxtRight;
  private ViewGroup mVigLeft;
  private ViewGroup mVigRight;
  
  public BaseTopBar(Context paramContext)
  {
    super(paramContext);
    init();
  }
  
  public BaseTopBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  private void init()
  {
    LayoutInflater.from(getContext()).inflate(2130903264, this);
    this.mVigLeft = ((ViewGroup)findViewById(2131362804));
    this.mVigRight = ((ViewGroup)findViewById(2131362808));
    this.mLinCenter = ((LinearLayout)findViewById(2131362812));
    this.mTxtLeft = ((TextView)findViewById(2131362806));
    this.mTxtRight = ((TextView)findViewById(2131362810));
    this.mImgLeftBtn = ((ImageView)findViewById(2131362805));
    this.mImgLeftBtn2 = ((ImageView)findViewById(2131362807));
    this.mImgRightBtn1 = ((ImageView)findViewById(2131362811));
    this.mImgRightBtn2 = ((ImageView)findViewById(2131362809));
  }
  
  public void dismisLeft()
  {
    this.mVigLeft.setVisibility(8);
  }
  
  public void dismisRight()
  {
    this.mVigRight.setVisibility(8);
    this.mImgRightBtn1.setVisibility(8);
  }
  
  public void dismisRightFront()
  {
    if (this.mImgRightBtn1 != null) {
      this.mImgRightBtn1.setVisibility(8);
    }
  }
  
  public ImageView getBehindLeftButton()
  {
    return this.mImgLeftBtn2;
  }
  
  public ImageView getFrontRightButton()
  {
    return this.mImgRightBtn1;
  }
  
  public ImageView getLeftButton()
  {
    return this.mImgLeftBtn;
  }
  
  public ImageView getRightButton()
  {
    return this.mImgRightBtn2;
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case 2131362808: 
    default: 
      return;
    case 2131362805: 
    case 2131362806: 
      this.mLeftListener.onTopBarLeftClick();
      return;
    case 2131362807: 
      this.mBehindLeftClickListener.onTopBarBehindLeftClick();
      return;
    case 2131362811: 
      this.mFrontRightListener.onTopBarFrontRightClick();
      return;
    }
    this.mRightListener.onTopBarRightClick();
  }
  
  public void setBehindLeftButton(int paramInt)
  {
    this.mImgLeftBtn2.setBackgroundResource(paramInt);
  }
  
  public void setBehindLeftButton(int paramInt, OnTopBarBehindLeftClickListener paramOnTopBarBehindLeftClickListener)
  {
    setBehindLeftButton(paramInt);
    this.mBehindLeftClickListener = paramOnTopBarBehindLeftClickListener;
    this.mImgLeftBtn2.setOnClickListener(this);
  }
  
  public void setCenterBody(View paramView)
  {
    this.mLinCenter.addView(paramView);
  }
  
  public void setFrontRightButton(int paramInt)
  {
    this.mImgRightBtn1.setBackgroundResource(paramInt);
  }
  
  public void setFrontRightButton(int paramInt, OnTopBarFrontRightClickListener paramOnTopBarFrontRightClickListener)
  {
    setFrontRightButton(paramInt);
    this.mFrontRightListener = paramOnTopBarFrontRightClickListener;
    this.mImgRightBtn1.setOnClickListener(this);
  }
  
  public void setLeftButton(int paramInt)
  {
    this.mImgLeftBtn.setBackgroundResource(paramInt);
  }
  
  public void setLeftButton(int paramInt, OnTopBarLeftClickListener paramOnTopBarLeftClickListener)
  {
    setLeftButton(paramInt);
    this.mLeftListener = paramOnTopBarLeftClickListener;
    this.mImgLeftBtn.setOnClickListener(this);
  }
  
  public void setLeftText(int paramInt)
  {
    this.mTxtLeft.setText(paramInt);
  }
  
  public void setLeftText(int paramInt, OnTopBarLeftClickListener paramOnTopBarLeftClickListener)
  {
    setLeftText(paramInt);
    this.mLeftListener = paramOnTopBarLeftClickListener;
    this.mTxtLeft.setOnClickListener(this);
  }
  
  public void setRightButton(int paramInt)
  {
    this.mImgRightBtn2.setBackgroundResource(paramInt);
  }
  
  public void setRightButton(int paramInt, OnTopBarRightClickListener paramOnTopBarRightClickListener)
  {
    setRightButton(paramInt);
    this.mRightListener = paramOnTopBarRightClickListener;
    this.mImgRightBtn2.setOnClickListener(this);
  }
  
  public void setRightText(int paramInt)
  {
    this.mTxtRight.setText(paramInt);
  }
  
  public void setRightText(int paramInt, OnTopBarRightClickListener paramOnTopBarRightClickListener)
  {
    setRightText(paramInt);
    this.mRightListener = paramOnTopBarRightClickListener;
    this.mTxtRight.setOnClickListener(this);
  }
  
  public void showLeft()
  {
    this.mVigLeft.setVisibility(0);
  }
  
  public void showRight()
  {
    this.mVigRight.setVisibility(0);
    this.mImgRightBtn1.setVisibility(0);
  }
  
  public static abstract interface OnTopBarBehindLeftClickListener
  {
    public abstract void onTopBarBehindLeftClick();
  }
  
  public static abstract interface OnTopBarFrontRightClickListener
  {
    public abstract void onTopBarFrontRightClick();
  }
  
  public static abstract interface OnTopBarLeftClickListener
  {
    public abstract void onTopBarLeftClick();
  }
  
  public static abstract interface OnTopBarRightClickListener
  {
    public abstract void onTopBarRightClick();
  }
}