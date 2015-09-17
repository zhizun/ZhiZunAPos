package com.zhizun.pos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.ui.entity.PurchaseGoods;
import com.zizun.cs.ui.entity.Setting;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;

public class PurchaseGoodsAdapter
  extends BaseAdapter
{
  private Context context;
  private LayoutInflater mInflater;
  private ArrayList<PurchaseGoods> mList;
  
  public PurchaseGoodsAdapter(Context paramContext, ArrayList<PurchaseGoods> paramArrayList)
  {
    this.context = paramContext;
    this.mList = paramArrayList;
    this.mInflater = LayoutInflater.from(StoreApplication.getContext());
  }
  
  public int getCount()
  {
    if (this.mList == null) {
      return 0;
    }
    return this.mList.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.mList.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    PurchaseGoods localPurchaseGoods;
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903170, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
      localPurchaseGoods = (PurchaseGoods)this.mList.get(paramInt);
      if ((UserManager.getInstance().getSetting() == null) || (UserManager.getInstance().getSetting().getStockQtyAlert() < localPurchaseGoods.getOnhand_Quantity())) {
        break label170;
      }
      paramViewGroup.imgAlarm.setVisibility(0);
    }
    for (;;)
    {
      String str = localPurchaseGoods.getProductSku_Image();
      ImgUtil.showImg(paramViewGroup.imgPic, str, 2130837655, 3);
      paramViewGroup.txtName.setText(localPurchaseGoods.getProductSku_Name());
      paramViewGroup.txtPrice.setText(DataUtil.getFormatString(localPurchaseGoods.getPurchase_Price()));
      paramViewGroup.txtAmount.setText(DataUtil.getFormatString(localPurchaseGoods.getQty_Order()));
      paramViewGroup.txtpriceName.setText("采购价");
      paramViewGroup.txtTagAmount.setText("进货量");
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
      break;
      label170:
      paramViewGroup.imgAlarm.setVisibility(8);
    }
  }
  
  private class ViewHolder
  {
    private ImageView imgAlarm;
    private ImageView imgPic;
    private TextView txtAmount;
    private TextView txtName;
    private TextView txtPrice;
    private TextView txtTagAmount;
    private TextView txtpriceName;
    
    public ViewHolder(View paramView)
    {
      this.imgPic = ((ImageView)paramView.findViewById(2131362547));
      this.imgAlarm = ((ImageView)paramView.findViewById(2131362546));
      this.txtName = ((TextView)paramView.findViewById(2131362545));
      this.txtPrice = ((TextView)paramView.findViewById(2131362549));
      this.txtAmount = ((TextView)paramView.findViewById(2131362551));
      this.txtpriceName = ((TextView)paramView.findViewById(2131362548));
      this.txtTagAmount = ((TextView)paramView.findViewById(2131362550));
    }
  }
}