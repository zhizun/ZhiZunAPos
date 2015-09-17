package com.zizun.cs.ui.entity;

public class Invent
{
  private boolean IsCurtime;
  private double Onhand_Scan;
  private Goods goods;
  private String time;
  
  public Goods getGoods()
  {
    return this.goods;
  }
  
  public double getOnhand_Scan()
  {
    return this.Onhand_Scan;
  }
  
  public String getTime()
  {
    return this.time;
  }
  
  public boolean isIsCurtime()
  {
    return this.IsCurtime;
  }
  
  public void setGoods(Goods paramGoods)
  {
    this.goods = paramGoods;
  }
  
  public void setIsCurtime(boolean paramBoolean)
  {
    this.IsCurtime = paramBoolean;
  }
  
  public void setOnhand_Scan(double paramDouble)
  {
    this.Onhand_Scan = paramDouble;
  }
  
  public void setTime(String paramString)
  {
    this.time = paramString;
  }
}
