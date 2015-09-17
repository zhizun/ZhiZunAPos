package com.zizun.cs.entities.api;

import java.util.List;

public class WXReceiptParam
{
  public String Code;
  public double DisCount;
  public double DisCountMoney;
  public List<ProductItemJsonModel> ItemJsonData;
  public double ItemTotalAct;
  public double ItemTotalCount;
  public double ItemTotalMoney;
  public String MerchantAddress;
  public int MerchantID;
  public String MerchantName;
  public String MerchantPhone;
  public String Payee;
  public String SNUM;
  public String UType;
  
  public WXReceiptParam(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, String paramString5, String paramString6, String paramString7, List<ProductItemJsonModel> paramList)
  {
    this.MerchantID = paramInt;
    this.MerchantName = paramString1;
    this.MerchantAddress = paramString2;
    this.MerchantPhone = paramString3;
    this.SNUM = paramString4;
    this.ItemTotalCount = paramDouble1;
    this.ItemTotalMoney = paramDouble2;
    this.ItemTotalAct = paramDouble3;
    this.DisCount = paramDouble4;
    this.DisCountMoney = paramDouble5;
    this.Payee = paramString5;
    this.UType = paramString6;
    this.Code = paramString7;
    this.ItemJsonData = paramList;
  }
  
  public static class ProductItemJsonModel
  {
    public double Count;
    public double Price;
    public String Product;
  }
}