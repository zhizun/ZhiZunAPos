package com.zhizun.pos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.common.utils.ImgUtil;
import com.zizun.cs.ui.entity.Goods;
import com.zizun.cs.ui.entity.Setting;
import com.zhizun.pos.util.DataUtil;
import java.util.ArrayList;

public class SwipeGoodsAdapter
  extends BaseAdapter
{
  private Context mContext;
  private LayoutInflater mInflater;
  private ArrayList<Goods> mList;
  private OnGroupListener mOnSlideListener;
  
  public SwipeGoodsAdapter(Context paramContext, ArrayList<Goods> paramArrayList)
  {
    this.mContext = paramContext;
    this.mList = paramArrayList;
    this.mInflater = LayoutInflater.from(paramContext);
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
  
  public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    Goods localGoods;
    if (paramView == null)
    {
      paramView = this.mInflater.inflate(2130903234, null);
      paramViewGroup = new ViewHolder(paramView);
      paramView.setTag(paramViewGroup);
      localGoods = (Goods)this.mList.get(paramInt);
      if ((UserManager.getInstance().getSetting() == null) || (UserManager.getInstance().getSetting().getStockQtyAlert() < localGoods.getAmount())) {
        break label168;
      }
      paramViewGroup.imgAlarm.setVisibility(0);
    }
    for (;;)
    {
      String str = localGoods.getPicture();
      ImgUtil.showImg(paramViewGroup.imgPic, str, 2130837655, 3);
      paramViewGroup.txtName.setText(localGoods.getName());
      paramViewGroup.txtPrice.setText(DataUtil.getFormatString(localGoods.getSellPrice()));
      paramViewGroup.txtAmount.setText(DataUtil.getFormatString(localGoods.getAmount()));
      paramViewGroup.relDelete.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (SwipeGoodsAdapter.this.mOnSlideListener != null) {
            SwipeGoodsAdapter.this.mOnSlideListener.onDelete((Goods)SwipeGoodsAdapter.this.mList.get(paramInt));
          }
        }
      });
      return paramView;
      paramViewGroup = (ViewHolder)paramView.getTag();
      break;
      label168:
      paramViewGroup.imgAlarm.setVisibility(8);
    }
  }
  
  public void setOnGroupListener(OnGroupListener paramOnGroupListener)
  {
    this.mOnSlideListener = paramOnGroupListener;
  }
  
  public static abstract interface OnGroupListener
  {
    public abstract void onDelete(Goods paramGoods);
  }
  
  private class ViewHolder
  {
    private ImageView imgAlarm;
    private ImageView imgPic;
    private RelativeLayout relDelete;
    private TextView txtAmount;
    private TextView txtName;
    private TextView txtPrice;
    
    public ViewHolder(View paramView)
    {
      this.imgPic = ((ImageView)paramView.findViewById(2131362547));
      this.imgAlarm = ((ImageView)paramView.findViewById(2131362546));
      this.txtName = ((TextView)paramView.findViewById(2131362545));
      this.txtPrice = ((TextView)paramView.findViewById(2131362549));
      this.txtAmount = ((TextView)paramView.findViewById(2131362551));
      this.relDelete = ((RelativeLayout)paramView.findViewById(2131362489));
    }
  }
}