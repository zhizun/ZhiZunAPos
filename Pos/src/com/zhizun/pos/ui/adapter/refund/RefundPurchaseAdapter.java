package com.zhizun.pos.ui.adapter.refund;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zizun.cs.biz.refund.purchase.RefundPurchaseShoppingCart;
import com.zizun.cs.biz.refund.purchase.RefundablePurchaseSheetItem;
import com.zizun.cs.common.utils.ImgUtil;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;

public class RefundPurchaseAdapter
  extends BaseAdapter
{
  private LayoutInflater mInflater;
  private ArrayList<RefundablePurchaseSheetItem> mList;
  private RefundPurchaseShoppingCart shoppingCart;
  
  public RefundPurchaseAdapter(RefundPurchaseShoppingCart paramRefundPurchaseShoppingCart)
  {
    this.shoppingCart = paramRefundPurchaseShoppingCart;
    this.mList = this.shoppingCart.getShoppingCartItemList();
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
      Object localObject = (RefundablePurchaseSheetItem)this.mList.get(paramInt);
      paramViewGroup.txtName.setText(((RefundablePurchaseSheetItem)localObject).getProductSku_Name());
      paramViewGroup.txtAmount.setText(DataUtil.getFormatString(((RefundablePurchaseSheetItem)localObject).getQuantiyPromotional()));
      paramViewGroup.txtPrice.setText(DataUtil.getFormatString(((RefundablePurchaseSheetItem)localObject).getPricePromotional()));
      paramViewGroup.txtTotalMoney.setText(DataUtil.getFormatString(((RefundablePurchaseSheetItem)localObject).getAmountPromotional()));
      localObject = ((RefundablePurchaseSheetItem)localObject).getProductSku_Image();
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