package com.zizun.cs.biz.dao;

import android.content.Context;
import com.yumendian.cs.entities.S_Merchant;

public abstract interface S_MerchantDao
  extends BaseDao
{
  public abstract boolean delete(S_Merchant paramS_Merchant);
  
  public abstract S_Merchant getMerchant();
  
  public abstract boolean insert(S_Merchant paramS_Merchant);
  
  public abstract boolean update(Context paramContext, S_Merchant paramS_Merchant);
  
  public abstract boolean update(S_Merchant paramS_Merchant);
}