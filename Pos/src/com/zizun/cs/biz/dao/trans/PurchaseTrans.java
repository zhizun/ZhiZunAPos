package com.zizun.cs.biz.dao.trans;

import com.zizun.cs.entities.PO_Detail;
import com.zizun.cs.entities.PO_Header;
import com.zizun.cs.entities.TransactionLog;
import com.zizun.cs.ui.entity.History;
import com.zizun.cs.ui.entity.PurchaseGoods;
import java.util.ArrayList;

public abstract interface PurchaseTrans
{
  public abstract boolean deleteBillTrans(History paramHistory, ArrayList<PurchaseGoods> paramArrayList);
  
  public abstract boolean hasPurchaseReturn(long paramLong);
  
  public abstract boolean hasPurchaseVoucher(long paramLong);
  
  public abstract boolean purchaseTrans(PO_Header paramPO_Header, PO_Detail[] paramArrayOfPO_Detail, TransactionLog[] paramArrayOfTransactionLog);
}