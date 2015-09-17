package com.zizun.cs.ui.entity;

public class Accounts
{
  private double Amt_Order;
  private double Amt_Sale;
  private double Amt_SaleReturn;
  private double Cost_Sale;
  private double Debt;
  private double Profit_Sale;
  
  public double getAmt_Order()
  {
    return this.Amt_Order;
  }
  
  public double getAmt_Sale()
  {
    return this.Amt_Sale;
  }
  
  public double getAmt_SaleReturn()
  {
    return this.Amt_SaleReturn;
  }
  
  public double getCost_Sale()
  {
    return this.Cost_Sale;
  }
  
  public double getDebt()
  {
    return this.Debt;
  }
  
  public double getProfit_Sale()
  {
    return this.Profit_Sale;
  }
  
  public void setAmt_Order(double paramDouble)
  {
    this.Amt_Order = paramDouble;
  }
  
  public void setAmt_Sale(double paramDouble)
  {
    this.Amt_Sale = paramDouble;
  }
  
  public void setAmt_SaleReturn(double paramDouble)
  {
    this.Amt_SaleReturn = paramDouble;
  }
  
  public void setCost_Sale(double paramDouble)
  {
    this.Cost_Sale = paramDouble;
  }
  
  public void setDebt(double paramDouble)
  {
    this.Debt = paramDouble;
  }
  
  public void setProfit_Sale(double paramDouble)
  {
    this.Profit_Sale = paramDouble;
  }
}