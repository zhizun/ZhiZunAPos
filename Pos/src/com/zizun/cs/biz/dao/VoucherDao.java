package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.biz.V_HeaderForPay;
import com.zizun.cs.entities.biz.V_VoucherDetail;
import com.zizun.cs.entities.biz.V_VoucherHeader;
import java.util.ArrayList;

public abstract interface VoucherDao
{
  public abstract ArrayList<V_HeaderForPay> getAllOwePay();
  
  public abstract ArrayList<V_HeaderForPay> getAllOweReceive();
  
  public abstract ArrayList<V_VoucherHeader> getAllPayVoucher();
  
  public abstract ArrayList<V_VoucherHeader> getAllReceiveVoucher();
  
  public abstract ArrayList<V_VoucherDetail> getAllVoucherDetail(long paramLong);
}