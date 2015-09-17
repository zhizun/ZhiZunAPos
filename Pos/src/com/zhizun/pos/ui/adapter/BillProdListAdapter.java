package com.zhizun.pos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.ui.entity.PurchaseGoods;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;

public class BillProdListAdapter
  extends BaseAdapter
{
  private LayoutInflater inflater;
  private Context mContext;
  private ArrayList<PurchaseGoods> purchaseGoodsList;
  
  public BillProdListAdapter(Context paramContext, ArrayList<PurchaseGoods> paramArrayList)
  {
    this.mContext = paramContext;
    this.inflater = LayoutInflater.from(StoreApplication.getContext());
    this.purchaseGoodsList = paramArrayList;
  }
  
  public int getCount()
  {
    if (this.purchaseGoodsList == null) {
      return 0;
    }
    return this.purchaseGoodsList.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.purchaseGoodsList.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      paramView = this.inflater.inflate(2130903211, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
    }
    for (;;)
    {
      Object localObject = (PurchaseGoods)this.purchaseGoodsList.get(paramInt);
      paramViewGroup.txtGoodsName.setText(((PurchaseGoods)localObject).getProductSku_Name());
      paramViewGroup.txtAmount.setText(DataUtil.getFormatString(((PurchaseGoods)localObject).getOnhand_Quantity()));
      paramViewGroup.txtUnitPrice.setText(DataUtil.getFormatString(((PurchaseGoods)localObject).getPurchase_Price()));
      paramViewGroup.txtMoney.setText(DataUtil.getFormatString(((PurchaseGoods)localObject).getSubTotal()));
      localObject = ((PurchaseGoods)localObject).getProductSku_Image();
      ImgUtil.showImg(paramViewGroup.imgPicture, (String)localObject, 2130837655, 3);
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
    }
  }
  
  private class ViewHolder
  {
    private ImageView imgPicture;
    private TextView txtAmount;
    private TextView txtGoodsName;
    private TextView txtMoney;
    private TextView txtUnitPrice;
    
    public ViewHolder(View paramView)
    {
      this.txtGoodsName = ((TextView)paramView.findViewById(2131362608));
      this.txtAmount = ((TextView)paramView.findViewById(2131362609));
      this.txtUnitPrice = ((TextView)paramView.findViewById(2131362610));
      this.txtMoney = ((TextView)paramView.findViewById(2131362611));
      this.imgPicture = ((ImageView)paramView.findViewById(2131362607));
    }
  }
}