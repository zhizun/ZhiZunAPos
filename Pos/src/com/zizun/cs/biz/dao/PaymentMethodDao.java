package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.PaymentMethod;
import java.util.List;

public abstract interface PaymentMethodDao
{
  public abstract List<PaymentMethod> getAllPaymentMethod();
  
  public abstract boolean updatePaymentMethod(long paramLong1, long paramLong2);
}