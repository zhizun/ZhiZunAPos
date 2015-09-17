package com.zhizun.pos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.ui.entity.PurchaseGoods;
import com.zhizun.pos.ui.activity.purchase.PurchaseCart;
import com.zhizun.pos.ui.activity.purchase.PurchasesManager;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;

public class PurchaseCartAdapter
  extends BaseAdapter
{
  private Context mContext;
  private LayoutInflater mInflater;
  private OnGoodsChangeListener mOnGoodsChangeListener;
  private OnGoodsChangeListenerAddOrSub mOnGoodsChangeListenerAddOrSub;
  private OnGoodsStockPriceChangeListener mOnGoodsStockPriceChangeListener;
  private PurchaseCart purchaseCart;
  
  public PurchaseCartAdapter(Context paramContext, PurchaseCart paramPurchaseCart)
  {
    this.purchaseCart = paramPurchaseCart;
    this.mContext = paramContext;
    this.mInflater = LayoutInflater.from(this.mContext);
  }
  
  private void addChooseAmount(PurchaseGoods paramPurchaseGoods)
  {
    PurchasesManager.getPurchaseCart().addAmount(paramPurchaseGoods);
    notifyDataSetChanged();
    if (this.mOnGoodsChangeListenerAddOrSub != null) {
      this.mOnGoodsChangeListenerAddOrSub.onGoodsChangeAddOrSub(PurchasesManager.getPurchaseCart().getTotalCount(), PurchasesManager.getPurchaseCart().getTotalMoney());
    }
  }
  
  private void subChooseAmount(PurchaseGoods paramPurchaseGoods)
  {
    if (paramPurchaseGoods.getChooseAmount() != 0.0D)
    {
      PurchasesManager.getPurchaseCart().subAmount(paramPurchaseGoods);
      notifyDataSetChanged();
      if (this.mOnGoodsChangeListenerAddOrSub != null) {
        this.mOnGoodsChangeListenerAddOrSub.onGoodsChangeAddOrSub(PurchasesManager.getPurchaseCart().getTotalCount(), PurchasesManager.getPurchaseCart().getTotalMoney());
      }
      if (paramPurchaseGoods.getChooseAmount() == 0.0D)
      {
        PurchasesManager.getPurchaseCart().getGoods().remove(paramPurchaseGoods);
        notifyDataSetChanged();
      }
    }
  }
  
  public int getCount()
  {
    if (this.purchaseCart.getGoods() == null) {
      return 0;
    }
    return this.purchaseCart.getGoods().size();
  }
  
  public Object getItem(int paramInt)
  {
    return this.purchaseCart.getGoods().get(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903128, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
    }
    for (;;)
    {
      final PurchaseGoods localPurchaseGoods = (PurchaseGoods)PurchasesManager.getPurchaseCart().getAllPurchaseGoods().get(paramInt);
      String str = localPurchaseGoods.getProductSku_Image();
      ImgUtil.showImg(paramViewGroup.imgPic, str, 2130837655, 3);
      paramViewGroup.txtName.setText(localPurchaseGoods.getProductSku_Name());
      paramViewGroup.txtPrice.setText(DataUtil.getFormatString(localPurchaseGoods.getPurchase_Price()));
      paramViewGroup.txtAmount.setText(DataUtil.getFormatString(localPurchaseGoods.getChooseAmount()));
      paramViewGroup.imgSub.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          PurchaseCartAdapter.this.subChooseAmount(localPurchaseGoods);
        }
      });
      paramViewGroup.imgAdd.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          PurchaseCartAdapter.this.addChooseAmount(localPurchaseGoods);
        }
      });
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
    }
  }
  
  public void setOnGoodsChangeListener(OnGoodsChangeListener paramOnGoodsChangeListener)
  {
    this.mOnGoodsChangeListener = paramOnGoodsChangeListener;
  }
  
  public void setOnGoodsChangeListenerAddOrSub(OnGoodsChangeListenerAddOrSub paramOnGoodsChangeListenerAddOrSub)
  {
    this.mOnGoodsChangeListenerAddOrSub = paramOnGoodsChangeListenerAddOrSub;
  }
  
  public void setOnGoodsStockPriceChangeListener(OnGoodsStockPriceChangeListener paramOnGoodsStockPriceChangeListener)
  {
    this.mOnGoodsStockPriceChangeListener = paramOnGoodsStockPriceChangeListener;
  }
  
  public static abstract interface OnGoodsChangeListener
  {
    public abstract void onGoodsChange(double paramDouble1, double paramDouble2);
  }
  
  public static abstract interface OnGoodsChangeListenerAddOrSub
  {
    public abstract void onGoodsChangeAddOrSub(double paramDouble1, double paramDouble2);
  }
  
  public static abstract interface OnGoodsStockPriceChangeListener
  {
    public abstract void onPriceChange(double paramDouble1, double paramDouble2);
  }
  
  private class ViewHolder
  {
    private ImageView imgAdd;
    private ImageView imgPic;
    private ImageView imgSub;
    private TextView txtAmount;
    private TextView txtName;
    private TextView txtPrice;
    
    public ViewHolder(View paramView)
    {
      this.imgPic = ((ImageView)paramView.findViewById(2131362403));
      this.txtName = ((TextView)paramView.findViewById(2131362404));
      this.txtPrice = ((TextView)paramView.findViewById(2131362405));
      this.txtAmount = ((TextView)paramView.findViewById(2131362407));
      this.imgSub = ((ImageView)paramView.findViewById(2131362406));
      this.imgAdd = ((ImageView)paramView.findViewById(2131362408));
    }
  }
}