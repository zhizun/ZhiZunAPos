package com.zhizun.pos.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import android.widget.ScrollView;

public class InterceptListView
  extends ListView
{
  private int maxHeight;
  private ScrollView parentScrollView;
  
  public InterceptListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void setParentScrollAble(boolean paramBoolean)
  {
    ScrollView localScrollView = this.parentScrollView;
    if (paramBoolean) {}
    for (paramBoolean = false;; paramBoolean = true)
    {
      localScrollView.requestDisallowInterceptTouchEvent(paramBoolean);
      return;
    }
  }
  
  public int getMaxHeight()
  {
    return this.maxHeight;
  }
  
  public ScrollView getParentScrollView()
  {
    return this.parentScrollView;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    }
    for (;;)
    {
      return super.onInterceptTouchEvent(paramMotionEvent);
      setParentScrollAble(false);
      continue;
      setParentScrollAble(true);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.maxHeight > -1) {
      paramInt2 = View.MeasureSpec.makeMeasureSpec(this.maxHeight, Integer.MIN_VALUE);
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setMaxHeight(int paramInt)
  {
    this.maxHeight = paramInt;
  }
  
  public void setParentScrollView(ScrollView paramScrollView)
  {
    this.parentScrollView = paramScrollView;
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\yunmendian\pos\ui\widget\InterceptListView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */