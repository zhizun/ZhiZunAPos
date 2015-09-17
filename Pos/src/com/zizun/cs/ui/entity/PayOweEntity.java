package com.zizun.cs.ui.entity;

import com.zizun.cs.entities.biz.V_HeaderForPay;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PayOweEntity
{
  private Timestamp payTime;
  private double totalPayMoney;
  private ArrayList<V_HeaderForPay> v_HeaderForPayList;
  
  public Timestamp getPayTime()
  {
    return this.payTime;
  }
  
  public double getTotalPayMoney()
  {
    return this.totalPayMoney;
  }
  
  public ArrayList<V_HeaderForPay> getV_HeaderForPayList()
  {
    return this.v_HeaderForPayList;
  }
  
  public void setPayTime(Timestamp paramTimestamp)
  {
    this.payTime = paramTimestamp;
  }
  
  public void setTotalPayMoney(double paramDouble)
  {
    this.totalPayMoney = paramDouble;
  }
  
  public void setV_HeaderForPayList(ArrayList<V_HeaderForPay> paramArrayList)
  {
    this.v_HeaderForPayList = paramArrayList;
  }
}