package com.zizun.cs.biz.dao.trans;

import com.zizun.cs.entities.biz.Expense;
import com.zizun.cs.ui.entity.PayOweEntity;

public abstract interface VoucherTrans
{
  public abstract boolean CreateExpenseTransaction(Expense paramExpense, boolean paramBoolean);
  
  public abstract boolean ReceiveOweTransaction(PayOweEntity paramPayOweEntity);
  
  public abstract boolean deleteVouchTransaction(long paramLong);
  
  public abstract boolean payOweTransaction(PayOweEntity paramPayOweEntity);
}