package com.zhizun.pos.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.zizun.cs.activity.manager.ProductManager;
import com.zizun.cs.entities.ProductGroup;
import com.zhizun.pos.R.styleable;
import com.zhizun.pos.ui.adapter.GroupSelectAdapter;
import java.util.ArrayList;

public class GroupSeleteView
  extends ListView
  implements AdapterView.OnItemClickListener
{
  private GroupSelectAdapter mGroupAdapter;
  private GroupChangeListener mGroupChangeListener;
  private ArrayList<ProductGroup> mListGroup;
  private int mRankName;
  
  public GroupSeleteView(Context paramContext)
  {
    super(paramContext);
    init();
  }
  
  public GroupSeleteView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.product_group_selector);
    this.mRankName = paramContext.getResourceId(0, 2131165372);
    paramContext.recycle();
    init();
  }
  
  private void init()
  {
    setOnItemClickListener(this);
    refreshGroup();
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mGroupAdapter.setSelect(paramInt);
    if (this.mGroupChangeListener != null) {
      this.mGroupChangeListener.onGroupChange(this.mGroupAdapter.getSelectGroup());
    }
  }
  
  public void refreshGroup()
  {
    new GetAllGroupAsync(null).execute(new Void[0]);
  }
  
  public void setOnGroupChangeListener(GroupChangeListener paramGroupChangeListener)
  {
    this.mGroupChangeListener = paramGroupChangeListener;
  }
  
  public void setRankName(int paramInt)
  {
    this.mRankName = paramInt;
  }
  
  private class GetAllGroupAsync
    extends AsyncTask<Void, Void, Void>
  {
    private GetAllGroupAsync() {}
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      GroupSeleteView.this.mListGroup = ProductManager.getAllPrimaryGroups();
      return null;
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      GroupSeleteView.this.mGroupAdapter = new GroupSelectAdapter(GroupSeleteView.this.getContext(), GroupSeleteView.this.mListGroup, GroupSeleteView.this.mRankName);
      GroupSeleteView.this.setAdapter(GroupSeleteView.this.mGroupAdapter);
      super.onPostExecute(paramVoid);
    }
    
    protected void onPreExecute()
    {
      super.onPreExecute();
    }
  }
  
  public static abstract interface GroupChangeListener
  {
    public abstract void onGroupChange(long paramLong);
  }
}