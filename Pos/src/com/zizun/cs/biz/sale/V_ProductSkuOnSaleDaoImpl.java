package com.zizun.cs.biz.sale;

import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.dao.impl.BaseDaoImpl;
import com.zizun.cs.biz.enumType.SalesType;
import com.zizun.cs.orm.SQLiteUtil;
import java.util.ArrayList;

public class V_ProductSkuOnSaleDaoImpl
  extends BaseDaoImpl
  implements V_ProductSkuOnSaleDao
{
  public static final String SQL_SELCECT_ALL_SALES_PRODUCTS = "SELECT  * From V_ProductSkuOnSale as vp WHERE vp.Product_Status = 1 AND  vp.ProductSku_Status = 1 AND  vp.PSS_Status = 1 AND  vp.Store_ID = <Store_ID> ORDER BY  vp.ProductSku_ID DESC  limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>";
  public static final String SQL_SELCECT_SALES_PRODUCT_BY_BARCODE = "SELECT  * From V_ProductSkuOnSale as vp WHERE vp.Product_Status = 1 AND vp.ProductSku_Status = 1 AND vp.Store_ID = <Store_ID> AND vp.ProductSku_BarCode = <ProductSku_BarCode>";
  
  private String getSqlOfSelcectAllProductSkuOnSale()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" SELECT  * From V_ProductSkuOnSale as vp").append(" WHERE vp.Product_Status = 1").append(" AND  vp.ProductSku_Status = 1").append(" AND  vp.PSS_Status = 1").append(" AND  vp.Store_ID = <Store_ID>").append(" ORDER BY  vp.ProductSku_ID DESC ").append(" limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>");
    return localStringBuilder.toString();
  }
  
  public ArrayList<V_ProductSkuOnSale> getAllProductSkuOnSale(int paramInt1, int paramInt2)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject = "SELECT  * From V_ProductSkuOnSale as vp WHERE vp.Product_Status = 1 AND  vp.ProductSku_Status = 1 AND  vp.PSS_Status = 1 AND  vp.Store_ID = <Store_ID> ORDER BY  vp.ProductSku_ID DESC  limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>".replace("<Store_ID>", getStoreID()).replace("<PAGE_SIZE>", paramInt1).replace("<INDEX>", paramInt2);
    LogUtils.d((String)localObject);
    try
    {
      localObject = (ArrayList)SQLiteUtil.getQueryList(getDatabase(), (String)localObject, V_ProductSkuOnSale.class);
      return (ArrayList<V_ProductSkuOnSale>)localObject;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return localArrayList;
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
      return localArrayList;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    return localArrayList;
  }
  
  public ArrayList<V_ProductSkuOnSale> getAllProductSkuOnSale(int paramInt1, int paramInt2, String paramString, long paramLong, SalesType paramSalesType)
  {
    ArrayList localArrayList = new ArrayList();
    String str = "";
    if (paramSalesType == SalesType.SALE)
    {
      str = " ORDER BY  vp.Qty_Retail DESC ";
      if (paramLong <= 0L) {
        break label133;
      }
    }
    label133:
    for (paramString = "SELECT  * From V_ProductSkuOnSale as vp WHERE 1 = 1  AND vp.Product_Status = 1 AND vp.ProductSku_Status = 1 AND vp.PSS_Status = 1 AND vp.Store_ID = " + getStoreID() + " AND vp.PG_ID = " + paramLong + paramString + str + " limit " + paramInt1 + " OFFSET ( " + paramInt2 + " - 1 )* " + paramInt1;; paramString = "SELECT  * From V_ProductSkuOnSale as vp WHERE 1 = 1  AND vp.Product_Status = 1 AND vp.ProductSku_Status = 1 AND vp.PSS_Status = 1 AND vp.Store_ID = " + getStoreID() + paramString + str + " limit " + paramInt1 + " OFFSET ( " + paramInt2 + " - 1 )* " + paramInt1)
    {
      LogUtils.d(paramString);
      try
      {
        paramString = (ArrayList)SQLiteUtil.getQueryList(getDatabase(), paramString, V_ProductSkuOnSale.class);
        return paramString;
      }
      catch (IllegalAccessException paramString)
      {
        paramString.printStackTrace();
        return localArrayList;
      }
      catch (InstantiationException paramString)
      {
        paramString.printStackTrace();
        return localArrayList;
      }
      catch (ClassNotFoundException paramString)
      {
        paramString.printStackTrace();
      }
      if (paramSalesType != SalesType.WHOLESALE) {
        break;
      }
      str = " ORDER BY  vp.Qty_Wholesale DESC ";
      break;
    }
    return localArrayList;
  }
  
  public ArrayList<V_ProductSkuOnSale> getAllProductSkuOnSaleOrderBySalesType(SalesType paramSalesType, String paramString, int paramInt1, int paramInt2)
  {
    ArrayList localArrayList = new ArrayList();
    String str = null;
    if (paramSalesType == SalesType.SALE) {
      str = "SELECT  * From V_ProductSkuOnSale as vp WHERE 1 = 1  AND vp.Product_Status = 1 AND vp.ProductSku_Status = 1 AND vp.PSS_Status = 1 AND vp.Store_ID = " + getStoreID() + paramString + " ORDER BY  vp.Qty_Retail DESC " + " limit " + paramInt1 + " OFFSET ( " + paramInt2 + " - 1 )* " + paramInt1;
    }
    for (;;)
    {
      LogUtils.d(str);
      try
      {
        paramSalesType = (ArrayList)SQLiteUtil.getQueryList(getDatabase(), str, V_ProductSkuOnSale.class);
        return paramSalesType;
      }
      catch (IllegalAccessException paramSalesType)
      {
        paramSalesType.printStackTrace();
        return localArrayList;
      }
      catch (InstantiationException paramSalesType)
      {
        paramSalesType.printStackTrace();
        return localArrayList;
      }
      catch (ClassNotFoundException paramSalesType)
      {
        paramSalesType.printStackTrace();
      }
      if (paramSalesType == SalesType.WHOLESALE) {
        str = "SELECT  * From V_ProductSkuOnSale as vp WHERE 1 = 1  AND vp.Product_Status = 1 AND vp.ProductSku_Status = 1 AND vp.PSS_Status = 1 AND vp.Store_ID = " + getStoreID() + paramString + " ORDER BY  vp.Qty_Wholesale DESC " + " limit " + paramInt1 + " OFFSET ( " + paramInt2 + " - 1 )* " + paramInt1;
      }
    }
    return localArrayList;
  }
  
  public V_ProductSkuOnSale getProductOnSaleByProductBarcode(String paramString)
  {
    Object localObject = null;
    paramString = "SELECT  * From V_ProductSkuOnSale as vp WHERE vp.Product_Status = 1 AND vp.ProductSku_Status = 1 AND vp.Store_ID = <Store_ID> AND vp.ProductSku_BarCode = <ProductSku_BarCode>".replace("<Store_ID>", getStoreID()).replace("<ProductSku_BarCode>", " '" + paramString + "'");
    LogUtils.d(paramString);
    try
    {
      ArrayList localArrayList = (ArrayList)SQLiteUtil.getQueryList(getDatabase(), paramString, V_ProductSkuOnSale.class);
      paramString = (String)localObject;
      if (localArrayList != null)
      {
        paramString = (String)localObject;
        if (localArrayList.size() > 0) {
          paramString = (V_ProductSkuOnSale)localArrayList.get(0);
        }
      }
      return paramString;
    }
    catch (IllegalAccessException paramString)
    {
      paramString.printStackTrace();
      return null;
    }
    catch (InstantiationException paramString)
    {
      paramString.printStackTrace();
      return null;
    }
    catch (ClassNotFoundException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
}