package com.zhizun.pos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import com.zizun.cs.biz.dao.utils.SyncTools;
import com.zizun.cs.biz.dao.utils.SyncTools.LastDo;
import com.zizun.cs.common.utils.ResUtil;
import com.zizun.cs.common.utils.ToastUtil;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.ui.fragment.AssociateFragment;
import com.zhizun.pos.ui.fragment.HomePageFragment;
import com.zhizun.pos.ui.fragment.PersonalFragment;
import com.zhizun.pos.ui.widget.HopeFragmentTabHost;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity
  extends FragmentActivity
{
  private int isCancle;
  private boolean isExit = false;
  private Class[] mListFragment = { HomePageFragment.class, AssociateFragment.class, PersonalFragment.class };
  private HopeFragmentTabHost mTabHost = null;
  
  private void exitByTwoClick()
  {
    if (!this.isExit)
    {
      ToastUtil.toastLong(this, "再按一次退出云门店");
      new Timer().schedule(new MyTimerTask(null), 2000L);
      this.isExit = true;
      return;
    }
    SyncTools.getInstance(this).checkSync(new SyncTools.LastDo()
    {
      public void doInLast() {}
    });
  }
  
  private View getIndicator(int paramInt)
  {
    View localView = getLayoutInflater().inflate(2130903260, null);
    ((ImageView)localView.findViewById(2131362793)).setImageResource(ResUtil.getDrawableId(String.format("btn_indicator_%d", new Object[] { Integer.valueOf(paramInt) })));
    ((TextView)localView.findViewById(2131362794)).setText(ResUtil.getStringId(String.format("indicator_%d", new Object[] { Integer.valueOf(paramInt) })));
    return localView;
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
  
  public boolean isShouldHideInput(View paramView, MotionEvent paramMotionEvent)
  {
    int i;
    int j;
    int k;
    int m;
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
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    Object localObject = getSupportFragmentManager().getFragments();
    if (localObject != null) {
      localObject = ((List)localObject).iterator();
    }
    for (;;)
    {
      if (!((Iterator)localObject).hasNext()) {
        return;
      }
      Fragment localFragment = (Fragment)((Iterator)localObject).next();
      if (localFragment != null) {
        localFragment.onActivityResult(paramInt1, paramInt2, paramIntent);
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903073);
    this.mTabHost = ((HopeFragmentTabHost)findViewById(16908306));
    this.mTabHost.setup(this, getSupportFragmentManager(), 2131362016);
    int i = 0;
    for (;;)
    {
      if (i >= this.mListFragment.length)
      {
        getIntent().getStringExtra("storeName");
        return;
      }
      this.mTabHost.addTab(this.mTabHost.newTabSpec(this.mListFragment[i].getSimpleName()).setIndicator(getIndicator(i)), this.mListFragment[i], null);
      i += 1;
    }
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.mTabHost = null;
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4) {
      exitByTwoClick();
    }
    return false;
  }
  
  private class MyTimerTask
    extends TimerTask
  {
    private MyTimerTask() {}
    
    public void run()
    {
      MainActivity.this.isExit = false;
    }
  }
}