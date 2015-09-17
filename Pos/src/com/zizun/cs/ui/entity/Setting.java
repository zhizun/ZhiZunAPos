package com.zizun.cs.ui.entity;

public class Setting
{
  private boolean NegativeStockSale;
  private double StockQtyAlert;
  private boolean isAutoSync;
  private boolean isProductImportAlat;
  private int printModelType;
  
  public int getPrintModelType()
  {
    return this.printModelType;
  }
  
  public double getStockQtyAlert()
  {
    return this.StockQtyAlert;
  }
  
  public boolean isAutoSync()
  {
    return this.isAutoSync;
  }
  
  public boolean isNegativeStockSale()
  {
    return this.NegativeStockSale;
  }
  
  public boolean isProductImportAlat()
  {
    return this.isProductImportAlat;
  }
  
  public void setAutoSync(boolean paramBoolean)
  {
    this.isAutoSync = paramBoolean;
  }
  
  public void setNegativeStockSale(boolean paramBoolean)
  {
    this.NegativeStockSale = paramBoolean;
  }
  
  public void setPrintModelType(int paramInt)
  {
    this.printModelType = paramInt;
  }
  
  public void setProductImportAlat(boolean paramBoolean)
  {
    this.isProductImportAlat = paramBoolean;
  }
  
  public void setStockQtyAlert(double paramDouble)
  {
    this.StockQtyAlert = paramDouble;
  }
}