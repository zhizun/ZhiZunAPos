package com.zhizun.pos.ui.adapter.refund;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zizun.cs.biz.refund.sales.RefundSalesShoppingCart;
import com.zizun.cs.biz.refund.sales.RefundableSalesSheetItem;
import com.zizun.cs.common.utils.ImgUtil;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;

public class RefundSalesAdapter
  extends BaseAdapter
{
  private LayoutInflater mInflater;
  private ArrayList<RefundableSalesSheetItem> mList;
  private RefundSalesShoppingCart salesDocument;
  
  public RefundSalesAdapter(RefundSalesShoppingCart paramRefundSalesShoppingCart)
  {
    this.salesDocument = paramRefundSalesShoppingCart;
    this.mList = this.salesDocument.getSaleShoppingCartItemList();
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
      Object localObject = (RefundableSalesSheetItem)this.mList.get(paramInt);
      paramViewGroup.txtName.setText(((RefundableSalesSheetItem)localObject).getProductSku_Name());
      paramViewGroup.txtAmount.setText(DataUtil.getFormatString(((RefundableSalesSheetItem)localObject).getQuantiyPromotional()));
      paramViewGroup.txtPrice.setText(DataUtil.getFormatString(((RefundableSalesSheetItem)localObject).getPricePromotional()));
      paramViewGroup.txtTotalMoney.setText(DataUtil.getFormatString(((RefundableSalesSheetItem)localObject).getAmountPromotional()));
      localObject = ((RefundableSalesSheetItem)localObject).getProductSku_Image();
      if (TextUtils.isEmpty((CharSequence)localObject)) {
        break;
      }
      ImgUtil.show(paramViewGroup.imgPic, ImgUtil.getImgPathFromSev((String)localObject));
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
    }
    paramViewGroup.imgPic.setImageResource(2130837655);
    return paramView;
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