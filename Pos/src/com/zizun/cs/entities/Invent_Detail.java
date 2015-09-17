package com.zizun.cs.entities;

import com.zizun.cs.orm.annotation.ID;
import com.zizun.cs.orm.annotation.Table;

@Table(name="Invent_Detail")
public class Invent_Detail
  extends EntitySync
{
  private static final long serialVersionUID = -244568604601004850L;
  @ID
  private long ID_ID;
  private long Invent_ID;
  private int Merchant_ID;
  private double Onhand_Disk;
  private double Onhand_Scan;
  private long ProductSku_ID;
  private double Quantity_Adjust;
  private String Remark;
  private int Sequence_ID;
  private double Unit_Cost;
  private double Unit_Price;
  
  public long getID_ID()
  {
    return this.ID_ID;
  }
  
  public long getInvent_ID()
  {
    return this.Invent_ID;
  }
  
  public int getMerchant_ID()
  {
    return this.Merchant_ID;
  }
  
  public double getOnhand_Disk()
  {
    return this.Onhand_Disk;
  }
  
  public double getOnhand_Scan()
  {
    return this.Onhand_Scan;
  }
  
  public long getProductSku_ID()
  {
    return this.ProductSku_ID;
  }
  
  public double getQuantity_Adjust()
  {
    return this.Quantity_Adjust;
  }
  
  public String getRemark()
  {
    return this.Remark;
  }
  
  public int getSequence_ID()
  {
    return this.Sequence_ID;
  }
  
  public double getUnit_Cost()
  {
    return this.Unit_Cost;
  }
  
  public double getUnit_Price()
  {
    return this.Unit_Price;
  }
  
  public void setID_ID(long paramLong)
  {
    this.ID_ID = paramLong;
  }
  
  public void setInvent_ID(long paramLong)
  {
    this.Invent_ID = paramLong;
  }
  
  public void setMerchant_ID(int paramInt)
  {
    this.Merchant_ID = paramInt;
  }
  
  public void setOnhand_Disk(double paramDouble)
  {
    this.Onhand_Disk = paramDouble;
  }
  
  public void setOnhand_Scan(double paramDouble)
  {
    this.Onhand_Scan = paramDouble;
  }
  
  public void setProductSku_ID(long paramLong)
  {
    this.ProductSku_ID = paramLong;
  }
  
  public void setQuantity_Adjust(double paramDouble)
  {
    this.Quantity_Adjust = paramDouble;
  }
  
  public void setRemark(String paramString)
  {
    this.Remark = paramString;
  }
  
  public void setSequence_ID(int paramInt)
  {
    this.Sequence_ID = paramInt;
  }
  
  public void setUnit_Cost(double paramDouble)
  {
    this.Unit_Cost = paramDouble;
  }
  
  public void setUnit_Price(double paramDouble)
  {
    this.Unit_Price = paramDouble;
  }
}