package com.zizun.cs.ui.entity;

public class GoodsFilter
  extends BaseEntity
{
  private static final long serialVersionUID = -8053137689741010150L;
  private String barCode;
  private int filterType;
  private long group;
  private boolean isRetailOrder;
  private boolean isStockOrder;
  private boolean isWholeSaleOrder;
  private int offset;
  private String search;
  
  public String getBarCode()
  {
    return this.barCode;
  }
  
  public int getFilterType()
  {
    return this.filterType;
  }
  
  public long getGroup()
  {
    return this.group;
  }
  
  public int getOffset()
  {
    return this.offset;
  }
  
  public String getSearch()
  {
    return this.search;
  }
  
  public boolean isRetailOrder()
  {
    return this.isRetailOrder;
  }
  
  public boolean isStockOrder()
  {
    return this.isStockOrder;
  }
  
  public boolean isWholeSaleOrder()
  {
    return this.isWholeSaleOrder;
  }
  
  public void setBarCode(String paramString)
  {
    this.barCode = paramString;
  }
  
  public void setFilterType(int paramInt)
  {
    this.filterType = paramInt;
  }
  
  public void setGroup(long paramLong)
  {
    this.group = paramLong;
  }
  
  public void setOffset(int paramInt)
  {
    this.offset = paramInt;
  }
  
  public void setRetailOrder()
  {
    this.isRetailOrder = true;
  }
  
  public void setSearch(String paramString)
  {
    this.search = paramString;
  }
  
  public void setStockOrder()
  {
    this.isStockOrder = true;
  }
  
  public void setWholeSaleOrder()
  {
    this.isWholeSaleOrder = true;
  }
}
