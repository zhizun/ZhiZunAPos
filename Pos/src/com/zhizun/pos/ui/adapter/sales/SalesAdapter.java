package com.zhizun.pos.ui.adapter.sales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.zizun.cs.biz.sale.SalesShoppingCart;
import com.zizun.cs.biz.sale.V_ProductSkuOnSale;
import com.zizun.cs.common.utils.ImgUtil;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;

public class SalesAdapter
  extends BaseAdapter
{
  private Context mContext;
  private LayoutInflater mInflater;
  private ArrayList<V_ProductSkuOnSale> mList;
  private RadioButton mRabRetail;
  private SalesShoppingCart salesShoppingCart;
  
  public SalesAdapter(Context paramContext, SalesShoppingCart paramSalesShoppingCart, RadioButton paramRadioButton)
  {
    this.mContext = paramContext;
    this.salesShoppingCart = paramSalesShoppingCart;
    this.mList = paramSalesShoppingCart.getSaleShoppingCartItemList();
    this.mRabRetail = paramRadioButton;
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
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903200, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
    }
    for (;;)
    {
      Object localObject = (V_ProductSkuOnSale)this.mList.get(paramInt);
      paramViewGroup.txtName.setText(((V_ProductSkuOnSale)localObject).getProductSku_Name());
      paramViewGroup.txtAmount.setText(DataUtil.getFormatString(((V_ProductSkuOnSale)localObject).getQuantiy()));
      double d = ((V_ProductSkuOnSale)localObject).getPricePromotional();
      paramViewGroup.txtPrice.setText(DataUtil.getFormatString(d));
      paramViewGroup.txtTotalMoney.setText(DataUtil.getFormatString(((V_ProductSkuOnSale)localObject).getAmountPromotional()));
      localObject = ((V_ProductSkuOnSale)localObject).getProductSku_Image();
      ImgUtil.showImg(paramViewGroup.imgPic, (String)localObject, 2130837655, 3);
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
    }
  }
  
  private class ViewHolder
  {
    private ImageView imgPic;
    private TextView txtAmount;
    private TextView txtName;
    private TextView txtPrice;
    private TextView txtTotalMoney;
    
    public ViewHolder(View paramView)
    {
      this.imgPic = ((ImageView)paramView.findViewById(2131362628));
      this.txtName = ((TextView)paramView.findViewById(2131362629));
      this.txtPrice = ((TextView)paramView.findViewById(2131362630));
      this.txtAmount = ((TextView)paramView.findViewById(2131362631));
      this.txtTotalMoney = ((TextView)paramView.findViewById(2131362632));
    }
  }
}