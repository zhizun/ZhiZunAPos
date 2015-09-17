package com.zhizun.pos.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import com.zhizun.pos.R.styleable;

public class SwipeMenuListView
  extends ListView
{
  private View mCurrentItemView;
  private final int mDuration = 100;
  private final int mDurationStep = 10;
  private float mFirstX;
  private float mFirstY;
  private Boolean mIsHorizontal;
  private boolean mIsShown;
  private View mPreItemView;
  private int mRightViewWidth;
  
  public SwipeMenuListView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SwipeMenuListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SwipeMenuListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.swipelistviewstyle);
    this.mRightViewWidth = paramContext.getDimensionPixelSize(0, 100);
    paramContext.recycle();
  }
  
  private void clearPressedState()
  {
    if (this.mCurrentItemView == null) {
      return;
    }
    this.mCurrentItemView.setPressed(false);
    setPressed(false);
    refreshDrawableState();
  }
  
  private void hiddenRight(View paramView)
  {
    if (paramView == null) {
      return;
    }
    Message localMessage = new MoveHandler(null).obtainMessage();
    localMessage.obj = paramView;
    localMessage.arg1 = paramView.getScrollX();
    localMessage.arg2 = 0;
    localMessage.sendToTarget();
    this.mIsShown = false;
  }
  
  private boolean isHitCurItemLeft(float paramFloat)
  {
    return paramFloat < getWidth() - this.mRightViewWidth;
  }
  
  private boolean judgeScrollDirection(float paramFloat1, float paramFloat2)
  {
    if ((Math.abs(paramFloat1) > 30.0F) && (Math.abs(paramFloat1) > Math.abs(paramFloat2) * 2.0F))
    {
      this.mIsHorizontal = Boolean.valueOf(true);
      return true;
    }
    if ((Math.abs(paramFloat2) > 30.0F) && (Math.abs(paramFloat2) > Math.abs(paramFloat1) * 2.0F))
    {
      this.mIsHorizontal = Boolean.valueOf(false);
      return true;
    }
    return false;
  }
  
  private void showRight(View paramView)
  {
    if (paramView == null) {
      return;
    }
    Message localMessage = new MoveHandler(null).obtainMessage();
    localMessage.obj = paramView;
    localMessage.arg1 = paramView.getScrollX();
    localMessage.arg2 = this.mRightViewWidth;
    localMessage.sendToTarget();
    this.mIsShown = true;
  }
  
  public int getRightViewWidth()
  {
    return this.mRightViewWidth;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    switch (paramMotionEvent.getAction())
    {
    }
    for (;;)
    {
      return super.onInterceptTouchEvent(paramMotionEvent);
      this.mIsHorizontal = null;
      this.mFirstX = f1;
      this.mFirstY = f2;
      int i = pointToPosition((int)this.mFirstX, (int)this.mFirstY);
      if (i >= 0)
      {
        View localView = getChildAt(i - getFirstVisiblePosition());
        this.mPreItemView = this.mCurrentItemView;
        this.mCurrentItemView = localView;
        continue;
        float f3 = this.mFirstX;
        float f4 = this.mFirstY;
        if ((Math.abs(f1 - f3) >= 5.0F) && (Math.abs(f2 - f4) >= 5.0F))
        {
          return true;
          if ((this.mIsShown) && ((this.mPreItemView != this.mCurrentItemView) || (isHitCurItemLeft(f1))))
          {
            hiddenRight(this.mPreItemView);
            Log.i("onInterceptTouchEvent", "ACTION_CANCEL");
          }
        }
      }
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool2 = true;
    float f2 = paramMotionEvent.getX();
    float f1 = paramMotionEvent.getY();
    switch (paramMotionEvent.getAction())
    {
    }
    label205:
    do
    {
      for (;;)
      {
        boolean bool1 = super.onTouchEvent(paramMotionEvent);
        do
        {
          do
          {
            do
            {
              return bool1;
              f2 -= this.mFirstX;
              float f3 = this.mFirstY;
              if ((this.mIsHorizontal == null) && (!judgeScrollDirection(f2, f1 - f3))) {
                break;
              }
              if (!this.mIsHorizontal.booleanValue()) {
                break label205;
              }
              if ((this.mIsShown) && (this.mPreItemView != this.mCurrentItemView)) {
                hiddenRight(this.mPreItemView);
              }
              f1 = f2;
              if (this.mIsShown)
              {
                f1 = f2;
                if (this.mPreItemView == this.mCurrentItemView) {
                  f1 = f2 - this.mRightViewWidth;
                }
              }
              bool1 = bool2;
            } while (f1 >= 0.0F);
            bool1 = bool2;
          } while (f1 <= -this.mRightViewWidth);
          bool1 = bool2;
        } while (this.mCurrentItemView == null);
        this.mCurrentItemView.scrollTo((int)-f1, 0);
        return true;
        if (this.mIsShown) {
          hiddenRight(this.mPreItemView);
        }
      }
      clearPressedState();
      if (this.mIsShown)
      {
        Log.i("onTouchEvent", "mIsShown");
        hiddenRight(this.mPreItemView);
      }
    } while ((this.mIsHorizontal == null) || (!this.mIsHorizontal.booleanValue()));
    if (this.mFirstX - f2 > this.mRightViewWidth / 2)
    {
      showRight(this.mCurrentItemView);
      Log.i("onTouchEvent", "showRight(mCurrentItemView)");
      return true;
    }
    hiddenRight(this.mCurrentItemView);
    this.mCurrentItemView = null;
    Log.i("onTouchEvent", "hiddenRight(mCurrentItemView)");
    return true;
  }
  
  public void setHiddenSwipeMenu(Boolean paramBoolean)
  {
    clearPressedState();
    hiddenRight(this.mCurrentItemView);
    this.mCurrentItemView = null;
  }
  
  public void setRightViewWidth(int paramInt)
  {
    this.mRightViewWidth = paramInt;
  }
  
  @SuppressLint({"HandlerLeak"})
  private class MoveHandler
    extends Handler
  {
    int fromX;
    private boolean mIsInAnimation = false;
    int stepX = 0;
    int toX;
    View view;
    
    private MoveHandler() {}
    
    private void animatioOver()
    {
      this.mIsInAnimation = false;
      this.stepX = 0;
    }
    
    public void handleMessage(Message paramMessage)
    {
      int j = 1;
      super.handleMessage(paramMessage);
      if (this.stepX == 0)
      {
        if (this.mIsInAnimation) {
          return;
        }
        this.mIsInAnimation = true;
        this.view = ((View)paramMessage.obj);
        this.fromX = paramMessage.arg1;
        this.toX = paramMessage.arg2;
        this.stepX = ((int)((this.toX - this.fromX) * 10 * 1.0D / 100.0D));
        if ((this.stepX < 0) && (this.stepX > -1)) {}
        for (this.stepX = -1; Math.abs(this.toX - this.fromX) < 10; this.stepX = 1)
        {
          label98:
          this.view.scrollTo(this.toX, 0);
          animatioOver();
          return;
          if ((this.stepX <= 0) || (this.stepX >= 1)) {
            break label98;
          }
        }
      }
      this.fromX += this.stepX;
      int i;
      if (this.stepX > 0)
      {
        i = j;
        if (this.fromX > this.toX) {}
      }
      else if (this.stepX < 0)
      {
        i = j;
        if (this.fromX < this.toX) {}
      }
      else
      {
        i = 0;
      }
      if (i != 0) {
        this.fromX = this.toX;
      }
      this.view.scrollTo(this.fromX, 0);
      SwipeMenuListView.this.invalidate();
      if (i == 0)
      {
        sendEmptyMessageDelayed(0, 10L);
        return;
      }
      animatioOver();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\widget\SwipeMenuListView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */