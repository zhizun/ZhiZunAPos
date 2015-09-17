package com.zhizun.pos.ui.adapter.sales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.zizun.cs.biz.sale.SalesShoppingCart;
import com.zizun.cs.biz.sale.V_ProductSkuOnSale;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.common.utils.NumUtil;
import com.zhizun.pos.ui.activity.sales.SalesShoppingCartActivity;
import com.zhizun.pos.ui.dialog.EditCartItemDialog;
import com.zhizun.pos.ui.dialog.EditCartItemDialog.OnGoodsEditListener;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;

public class SalesShoppingCartAdapter
  extends BaseAdapter
{
  private Context mContext;
  private LayoutInflater mInflater;
  private ArrayList<V_ProductSkuOnSale> mList;
  private OnGoodsChangeListener mOnGoodsChangeListener;
  private SalesShoppingCart salesShoppingCart;
  
  public SalesShoppingCartAdapter(Context paramContext, SalesShoppingCart paramSalesShoppingCart)
  {
    this.salesShoppingCart = paramSalesShoppingCart;
    this.mList = paramSalesShoppingCart.getSaleShoppingCartItemList();
    this.mContext = paramContext;
    this.mInflater = LayoutInflater.from(this.mContext);
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
  
  public View getView(int paramInt, View paramView, final ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903202, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
    }
    for (;;)
    {
      final V_ProductSkuOnSale localV_ProductSkuOnSale = (V_ProductSkuOnSale)this.mList.get(paramInt);
      String str = localV_ProductSkuOnSale.getProductSku_Image();
      ImgUtil.showImg(paramViewGroup.imgPic, str, 2130837655, 3);
      paramViewGroup.txtName.setText(localV_ProductSkuOnSale.getProductSku_Name());
      paramViewGroup.txtPrice.setText(DataUtil.getFormatString(localV_ProductSkuOnSale.getPricePromotional()));
      paramViewGroup.txtQuantiy.setText(DataUtil.getFormatString(localV_ProductSkuOnSale.getQuantiy()));
      paramViewGroup.imgSub.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          localV_ProductSkuOnSale.reduceCount();
          if (SalesShoppingCartAdapter.this.mOnGoodsChangeListener != null) {
            SalesShoppingCartAdapter.this.mOnGoodsChangeListener.onGoodsChange(SalesShoppingCartAdapter.this.salesShoppingCart.getTotalCount(), SalesShoppingCartAdapter.this.salesShoppingCart.getTotalMoney());
          }
          if (localV_ProductSkuOnSale.getQuantiy() == 0.0D)
          {
            SalesShoppingCartAdapter.this.mList.remove(localV_ProductSkuOnSale);
            SalesShoppingCartAdapter.this.notifyDataSetChanged();
          }
          SalesShoppingCartAdapter.ViewHolder.access$3(paramViewGroup).setText(DataUtil.getFormatString(localV_ProductSkuOnSale.getQuantiy()));
        }
      });
      paramViewGroup.imgAdd.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          localV_ProductSkuOnSale.increaseCount();
          if (SalesShoppingCartAdapter.this.mOnGoodsChangeListener != null) {
            SalesShoppingCartAdapter.this.mOnGoodsChangeListener.onGoodsChange(SalesShoppingCartAdapter.this.salesShoppingCart.getTotalCount(), SalesShoppingCartAdapter.this.salesShoppingCart.getTotalMoney());
          }
          SalesShoppingCartAdapter.ViewHolder.access$3(paramViewGroup).setText(DataUtil.getFormatString(localV_ProductSkuOnSale.getQuantiy()));
        }
      });
      paramViewGroup.txtQuantiy.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          paramAnonymousView = new EditCartItemDialog();
          paramAnonymousView.setPrice(localV_ProductSkuOnSale.getPricePromotional());
          paramAnonymousView.setQuantity(localV_ProductSkuOnSale.getQuantiy());
          paramAnonymousView.setOnGoodsEditListener(new EditCartItemDialog.OnGoodsEditListener()
          {
            public void onItemChange(double paramAnonymous2Double1, double paramAnonymous2Double2)
            {
              if (SalesShoppingCartAdapter.this.mOnGoodsChangeListener != null)
              {
                this.val$shoppingCartItem.setPricePromotional(NumUtil.getNum(paramAnonymous2Double1));
                this.val$shoppingCartItem.setQuantiy(NumUtil.getNum(paramAnonymous2Double2));
                SalesShoppingCartAdapter.this.mOnGoodsChangeListener.onGoodsChange(SalesShoppingCartAdapter.this.salesShoppingCart.getTotalCount(), SalesShoppingCartAdapter.this.salesShoppingCart.getTotalMoney());
              }
              if (paramAnonymous2Double2 == 0.0D) {
                SalesShoppingCartAdapter.this.mList.remove(this.val$shoppingCartItem);
              }
              SalesShoppingCartAdapter.this.notifyDataSetChanged();
            }
          });
          paramAnonymousView.show(((SalesShoppingCartActivity)SalesShoppingCartAdapter.this.mContext).getSupportFragmentManager(), EditCartItemDialog.class.getSimpleName());
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
  
  public static abstract interface OnGoodsChangeListener
  {
    public abstract void onGoodsChange(double paramDouble1, double paramDouble2);
  }
  
  private class ViewHolder
  {
    private ImageView imgAdd;
    private ImageView imgPic;
    private ImageView imgSub;
    private TextView txtName;
    private TextView txtPrice;
    private TextView txtQuantiy;
    
    public ViewHolder(View paramView)
    {
      this.imgPic = ((ImageView)paramView.findViewById(2131362403));
      this.txtName = ((TextView)paramView.findViewById(2131362404));
      this.txtPrice = ((TextView)paramView.findViewById(2131362405));
      this.txtQuantiy = ((TextView)paramView.findViewById(2131362407));
      this.imgSub = ((ImageView)paramView.findViewById(2131362406));
      this.imgAdd = ((ImageView)paramView.findViewById(2131362408));
    }
  }
}