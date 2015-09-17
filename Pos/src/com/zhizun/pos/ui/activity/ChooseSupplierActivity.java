package com.zhizun.pos.ui.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.zizun.cs.entities.biz.Associate;
import com.zizun.cs.ui.entity.Supplier;
import com.zhizun.pos.ui.activity.associate.CreateAssociateActivity;
import com.zhizun.pos.ui.adapter.AssociateAdapter;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarLeftClickListener;
import com.zhizun.pos.ui.widget.BaseTopBar.OnTopBarRightClickListener;
import com.zhizun.pos.ui.widget.TitleTopBar;
import java.io.Serializable;
import java.util.ArrayList;

public class ChooseSupplierActivity
  extends BaseTitleTopBarActivity
  implements BaseTopBar.OnTopBarLeftClickListener, BaseTopBar.OnTopBarRightClickListener, AdapterView.OnItemClickListener
{
  private AssociateAdapter mAssociateAdapter;
  private ArrayList<Associate> mListAssociate;
  private ListView mLsvSupplier;
  
  private void simulateData()
  {
    this.mListAssociate = new ArrayList();
    int i = 0;
    for (;;)
    {
      if (i >= 20)
      {
        this.mAssociateAdapter = new AssociateAdapter(this.mListAssociate);
        this.mLsvSupplier.setAdapter(this.mAssociateAdapter);
        this.mLsvSupplier.setOnItemClickListener(this);
        return;
      }
      Associate localAssociate = new Associate();
      localAssociate.setCompany("盛世贸易有限公司");
      localAssociate.setName("李老板");
      localAssociate.setPhone("135223434");
      this.mListAssociate.add(localAssociate);
      i += 1;
    }
  }
  
  public void addBody(ViewGroup paramViewGroup)
  {
    getLayoutInflater().inflate(2130903185, paramViewGroup);
    this.mTopBar.setTitle(2131165305);
    this.mTopBar.setLeftButton(2130837526, this);
    this.mTopBar.setRightButton(2130837525, this);
    this.mLsvSupplier = ((ListView)findViewById(2131362591));
    simulateData();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    paramAdapterView = getIntent();
    paramAdapterView.putExtra(Supplier.class.getSimpleName(), (Serializable)this.mListAssociate.get(paramInt));
    setResult(-1, paramAdapterView);
    finish();
  }
  
  public void onTopBarLeftClick()
  {
    finish();
  }
  
  public void onTopBarRightClick()
  {
    startActivityForResult(new Intent(this, CreateAssociateActivity.class), 0);
  }
}