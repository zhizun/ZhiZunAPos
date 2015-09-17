package com.zhizun.pos.ui.adapter.sales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zizun.cs.biz.sale.SalesHistoryItemDetail;
import com.zizun.cs.common.utils.ImgUtil;
import com.zhizun.pos.app.StoreApplication;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;

public class SalesHistoryDetailAdapter
  extends BaseAdapter
{
  private ArrayList<SalesHistoryItemDetail> historyItemDetails;
  private LayoutInflater inflater;
  private Context mContext;
  
  public SalesHistoryDetailAdapter(Context paramContext, ArrayList<SalesHistoryItemDetail> paramArrayList)
  {
    this.mContext = paramContext;
    this.inflater = LayoutInflater.from(StoreApplication.getContext());
    this.historyItemDetails = paramArrayList;
  }
  
  public int getCount()
  {
    if (this.historyItemDetails == null) {
      return 0;
    }
    return this.historyItemDetails.size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.historyItemDetails.get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      paramView = this.inflater.inflate(2130903197, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
    }
    for (;;)
    {
      Object localObject = (SalesHistoryItemDetail)this.historyItemDetails.get(paramInt);
      paramViewGroup.txtGoodsName.setText(((SalesHistoryItemDetail)localObject).getProductSku_Name());
      paramViewGroup.txtAmount.setText(DataUtil.getFormatString(((SalesHistoryItemDetail)localObject).getSD_Quantiy()));
      paramViewGroup.txtUnitPrice.setText(DataUtil.getFormatString(((SalesHistoryItemDetail)localObject).getSD_PricePromotional()));
      paramViewGroup.txtMoney.setText(DataUtil.getFormatString(((SalesHistoryItemDetail)localObject).getAmount()));
      localObject = ((SalesHistoryItemDetail)localObject).getProductSku_Image();
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