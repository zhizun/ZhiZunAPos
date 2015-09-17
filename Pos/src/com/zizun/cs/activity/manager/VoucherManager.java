package com.zizun.cs.activity.manager;

import com.zizun.cs.biz.dao.VoucherDao;
import com.zizun.cs.biz.dao.impl.VoucherDaoImpl;
import com.zizun.cs.biz.dao.trans.VoucherTrans;
import com.zizun.cs.biz.dao.trans.impl.VoucherTransImpl;
import com.zizun.cs.entities.biz.Expense;
import com.zizun.cs.entities.biz.V_HeaderForPay;
import com.zizun.cs.entities.biz.V_VoucherDetail;
import com.zizun.cs.entities.biz.V_VoucherHeader;
import com.zizun.cs.ui.entity.PayOweEntity;
import java.util.ArrayList;

public class VoucherManager
{
  public static boolean ReceiveOwe(PayOweEntity paramPayOweEntity)
  {
    return new VoucherTransImpl().ReceiveOweTransaction(paramPayOweEntity);
  }
  
  public static boolean deleteVoucher(long paramLong)
  {
    return new VoucherTransImpl().deleteVouchTransaction(paramLong);
  }
  
  public static ArrayList<V_VoucherHeader> getAllPayVoucher()
  {
    return new VoucherDaoImpl().getAllPayVoucher();
  }
  
  public static ArrayList<V_VoucherHeader> getAllReceiveVoucher()
  {
    return new VoucherDaoImpl().getAllReceiveVoucher();
  }
  
  public static ArrayList<V_VoucherDetail> getAllVoucherDetail(long paramLong)
  {
    return new VoucherDaoImpl().getAllVoucherDetail(paramLong);
  }
  
  public static ArrayList<V_HeaderForPay> getOwePayList()
  {
    return new VoucherDaoImpl().getAllOwePay();
  }
  
  public static ArrayList<V_HeaderForPay> getOweReceiveList()
  {
    return new VoucherDaoImpl().getAllOweReceive();
  }
  
  public static boolean payOwe(PayOweEntity paramPayOweEntity)
  {
    return new VoucherTransImpl().payOweTransaction(paramPayOweEntity);
  }
  
  public boolean createExpense(Expense paramExpense, boolean paramBoolean)
  {
    return new VoucherTransImpl().CreateExpenseTransaction(paramExpense, paramBoolean);
  }
}