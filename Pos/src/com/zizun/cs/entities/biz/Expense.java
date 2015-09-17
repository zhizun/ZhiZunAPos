package com.zizun.cs.entities.biz;

import com.zizun.cs.entities.BalanceItem;
import java.io.Serializable;

public class Expense
  implements Serializable
{
  public static final String SELECT_VOUCHERHEADER = "select * from VoucherHeaderwhere VH_ID=<VH_ID>";
  private static final long serialVersionUID = 765273905241759602L;
  private double PayAmount;
  private Associate associate;
  private String costTime;
  private boolean curtime;
  private BalanceItem item;
  
  public Associate getAssociate()
  {
    return this.associate;
  }
  
  public String getCostTime()
  {
    return this.costTime;
  }
  
  public BalanceItem getItem()
  {
    return this.item;
  }
  
  public double getPayAmount()
  {
    return this.PayAmount;
  }
  
  public boolean isCurtime()
  {
    return this.curtime;
  }
  
  public void setAssociate(Associate paramAssociate)
  {
    this.associate = paramAssociate;
  }
  
  public void setCostTime(String paramString)
  {
    this.costTime = paramString;
  }
  
  public void setCurtime(boolean paramBoolean)
  {
    this.curtime = paramBoolean;
  }
  
  public void setItem(BalanceItem paramBalanceItem)
  {
    this.item = paramBalanceItem;
  }
  
  public void setPayAmount(double paramDouble)
  {
    this.PayAmount = paramDouble;
  }
}