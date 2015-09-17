package com.zizun.cs.biz.dao.impl;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.api.ImgAPI;
import com.zizun.cs.biz.dao.ProductDao;
import com.zizun.cs.biz.dao.condition.ProductCondition;
import com.zizun.cs.common.utils.LogUtil;
import com.zizun.cs.entities.ProductGroup;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.api.ImgProductParam;
import com.zizun.cs.ui.entity.Goods;
import com.zizun.cs.ui.entity.GoodsFilter;
import com.zizun.cs.ui.entity.GoodsStatus;
import java.util.ArrayList;

public class ProductDaoImpl
  extends BaseDaoImpl
  implements ProductDao
{
  private static ProductDaoImpl mInstance;
  private final String SQL_IS_PRODUCT_BARCODE_EXIST = "SELECT ps.ProductSku_ID FROM ProductSku AS ps WHERE ps.ProductSku_BarCode = <ProductSku_BarCode>";
  private final String SQL_IS_PRODUCT_NAME_EXIST = "SELECT ps.ProductSku_ID FROM ProductSku AS ps WHERE ps.ProductSku_Name = <ProductSku_Name>";
  private final String SQL_SELECTSINGLE = "SELECT * FROM V_ProductSkuOnSale AS psos WHERE psos.ProductSku_BarCode = <ProductSku_BarCode> AND psos.Store_ID = <Store_ID> AND psos.PSS_Status = 1";
  private final String SQL_SELECT_ALL_PRODUCT_BY_TABLEVIEW = "SELECT * From V_ProductSkuOnSale as vp WHERE vp.Product_Status = 1 AND vp.ProductSku_Status = 1";
  private final String SQL_SELECT_STOCKIN_ALL_PRODUCT_BY_TABLEVIEW = "SELECT b.* FROM (SELECT psos.Product_ID,psos.ProductSku_ID,psos.ProductSku_Name,psos.ProductSku_Code,psos.ProductSku_BarCode,psos.ProductSku_Spec,psos.PruductSku_Description,psos.ProductSku_Image,psos.ProductSku_Unit,psos.PG_ID,psos.PG_Name,psos.Retail_Price,psos.Fixed_Cost,psos.WholeSale_Price,psos.Purchase_Price,psos.Onhand_Quantity,psos.Qty_Order,psos.Qty_Retail,psos.Qty_Wholesale FROM V_ProductSkuOnSale AS psos WHERE psos.Store_ID =? AND psos.ProductSku_Status = 1 AND psos.PG_ID =? AND psos.PSS_Status IN (1,2)UNION SELECT ps.Product_ID,ps.ProductSku_ID,ps.ProductSku_Name,ps.ProductSku_Code,ps.ProductSku_BarCode,ps.ProductSku_Spec,ps.PruductSku_Description,ps.ProductSku_Image,ps.ProductSku_Unit,ps.PG_ID,ps.PG_Name,ps.Retail_Price,ps.Fixed_Cost,ps.WholeSale_Price,ps.Purchase_Price,0  as 'Onhand_Quantity',0  as 'Qty_Order',0  as  'Qty_Retail',0  as  'Qty_Wholesale'  FROM V_ProductSku AS ps WHERE ps.ProductSku_Status = 1 AND ps.PG_ID = ? AND ps.ProductSku_ID NOT IN ( SELECT a.ProductSku_ID FROM V_ProductSkuOnSale AS a WHERE a.Store_ID = ?))  AS b WHERE b.ProductSku_Name like '%<search>%' OR b.ProductSku_Code LIKE '%<search>%' OR b.ProductSku_BarCode LIKE '%<search>%' ORDER BY b.Onhand_Quantity DESC limit 20 OFFSET ?";
  private Cursor cursor;
  private String sql;
  
  public static void clear()
  {
    mInstance = null;
  }
  
  public static ProductDaoImpl getInstance()
  {
    if (mInstance == null) {
      mInstance = new ProductDaoImpl();
    }
    return mInstance;
  }
  
  private ArrayList<Goods> getProductByCondition(ProductCondition paramProductCondition)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      LogUtil.logD("SELECT * From V_ProductSkuOnSale as vp WHERE vp.Product_Status = 1 AND vp.ProductSku_Status = 1" + paramProductCondition.getCondition());
      paramProductCondition = getDatabase().rawQuery("SELECT * From V_ProductSkuOnSale as vp WHERE vp.Product_Status = 1 AND vp.ProductSku_Status = 1" + paramProductCondition.getCondition(), null);
      for (;;)
      {
        if (!paramProductCondition.moveToNext()) {
          return localArrayList;
        }
        Goods localGoods = new Goods();
        localGoods.setId(paramProductCondition.getLong(paramProductCondition.getColumnIndex("Product_ID")));
        localGoods.setSkuId(paramProductCondition.getLong(paramProductCondition.getColumnIndex("ProductSku_ID")));
        localGoods.setPSP_ID(paramProductCondition.getLong(paramProductCondition.getColumnIndex("PSP_ID")));
        localGoods.setName(paramProductCondition.getString(paramProductCondition.getColumnIndex("ProductSku_Name")));
        localGoods.setNumber(paramProductCondition.getString(paramProductCondition.getColumnIndex("ProductSku_Code")));
        localGoods.setCode(paramProductCondition.getString(paramProductCondition.getColumnIndex("ProductSku_BarCode")));
        localGoods.setSize(paramProductCondition.getString(paramProductCondition.getColumnIndex("ProductSku_Spec")));
        localGoods.setDescribe(paramProductCondition.getString(paramProductCondition.getColumnIndex("PruductSku_Description")));
        localGoods.setPicture(paramProductCondition.getString(paramProductCondition.getColumnIndex("ProductSku_Image")));
        localGoods.setUnit(paramProductCondition.getString(paramProductCondition.getColumnIndex("ProductSku_Unit")));
        Object localObject = new ProductGroup();
        ((ProductGroup)localObject).setPG_ID(paramProductCondition.getLong(paramProductCondition.getColumnIndex("PG_ID")));
        ((ProductGroup)localObject).setPG_Name(paramProductCondition.getString(paramProductCondition.getColumnIndex("PG_Name")));
        localGoods.setGroup((ProductGroup)localObject);
        localGoods.setSellPrice(paramProductCondition.getDouble(paramProductCondition.getColumnIndex("Retail_Price")));
        localGoods.setCostPrice(paramProductCondition.getDouble(paramProductCondition.getColumnIndex("Fixed_Cost")));
        localGoods.setWholesalePrice(paramProductCondition.getDouble(paramProductCondition.getColumnIndex("WholeSale_Price")));
        localGoods.setStockPrice(paramProductCondition.getDouble(paramProductCondition.getColumnIndex("Purchase_Price")));
        localGoods.setAmount(paramProductCondition.getDouble(paramProductCondition.getColumnIndex("Onhand_Quantity")));
        localGoods.setStockAmount(paramProductCondition.getDouble(paramProductCondition.getColumnIndex("Qty_Order")));
        localGoods.setRetailAmount(paramProductCondition.getDouble(paramProductCondition.getColumnIndex("Qty_Retail")));
        localGoods.setWholeSaleAmount(paramProductCondition.getDouble(paramProductCondition.getColumnIndex("Qty_Wholesale")));
        localObject = new GoodsStatus();
        ((GoodsStatus)localObject).setId((byte)paramProductCondition.getInt(paramProductCondition.getColumnIndex("PSS_Status")));
        localGoods.setStatus((GoodsStatus)localObject);
        localArrayList.add(localGoods);
      }
      return null;
    }
    catch (SQLException paramProductCondition)
    {
      paramProductCondition.printStackTrace();
    }
  }
  
  public ArrayList<Goods> getAllProduct(int paramInt)
  {
    GoodsFilter localGoodsFilter = new GoodsFilter();
    localGoodsFilter.setOffset(paramInt);
    localGoodsFilter.setFilterType(0);
    return getAllProduct(localGoodsFilter);
  }
  
  public ArrayList<Goods> getAllProduct(GoodsFilter paramGoodsFilter)
  {
    return getProductByCondition(new ProductCondition(paramGoodsFilter));
  }
  
  public Goods getGood(String paramString)
  {
    long l = UserManager.getInstance().getCurrentStore().getStore_ID();
    paramString = "SELECT * FROM V_ProductSkuOnSale AS psos WHERE psos.ProductSku_BarCode = <ProductSku_BarCode> AND psos.Store_ID = <Store_ID> AND psos.PSS_Status = 1".replace("<ProductSku_BarCode>", "'" + paramString + "'").replace("<Store_ID>", l);
    LogUtils.i("SQL_SELECTSINGLE:" + paramString);
    Cursor localCursor = getDatabase().rawQuery(paramString, null);
    paramString = null;
    if (localCursor.moveToNext())
    {
      paramString = new Goods();
      paramString.setId(localCursor.getLong(localCursor.getColumnIndex("Product_ID")));
      paramString.setSkuId(localCursor.getLong(localCursor.getColumnIndex("ProductSku_ID")));
      paramString.setName(localCursor.getString(localCursor.getColumnIndex("ProductSku_Name")));
      paramString.setNumber(localCursor.getString(localCursor.getColumnIndex("ProductSku_Code")));
      paramString.setCode(localCursor.getString(localCursor.getColumnIndex("ProductSku_BarCode")));
      paramString.setSize(localCursor.getString(localCursor.getColumnIndex("ProductSku_Spec")));
      paramString.setDescribe(localCursor.getString(localCursor.getColumnIndex("PruductSku_Description")));
      paramString.setPicture(localCursor.getString(localCursor.getColumnIndex("ProductSku_Image")));
      paramString.setUnit(localCursor.getString(localCursor.getColumnIndex("ProductSku_Unit")));
      Object localObject = new ProductGroup();
      ((ProductGroup)localObject).setPG_ID(localCursor.getLong(localCursor.getColumnIndex("PG_ID")));
      ((ProductGroup)localObject).setPG_Name(localCursor.getString(localCursor.getColumnIndex("PG_Name")));
      paramString.setGroup((ProductGroup)localObject);
      paramString.setSellPrice(localCursor.getDouble(localCursor.getColumnIndex("Retail_Price")));
      paramString.setCostPrice(localCursor.getDouble(localCursor.getColumnIndex("Fixed_Cost")));
      paramString.setWholesalePrice(localCursor.getDouble(localCursor.getColumnIndex("WholeSale_Price")));
      paramString.setStockPrice(localCursor.getDouble(localCursor.getColumnIndex("Purchase_Price")));
      paramString.setAmount(localCursor.getDouble(localCursor.getColumnIndex("Onhand_Quantity")));
      localObject = new GoodsStatus();
      ((GoodsStatus)localObject).setId((byte)localCursor.getInt(localCursor.getColumnIndex("PSS_Status")));
      paramString.setStatus((GoodsStatus)localObject);
      paramString.setProduct_status(localCursor.getInt(localCursor.getColumnIndex("Product_Status")));
      paramString.setProductSku_status(localCursor.getInt(localCursor.getColumnIndex("ProductSku_Status")));
    }
    return paramString;
  }
  
  public ArrayList<Goods> getStockInAllProduct(GoodsFilter paramGoodsFilter)
  {
    long l = UserManager.getInstance().getCurrentStore().getStore_ID();
    ArrayList localArrayList = new ArrayList();
    label555:
    label1046:
    for (;;)
    {
      try
      {
        if (paramGoodsFilter.getGroup() == 0L)
        {
          if (paramGoodsFilter.getSearch() != null)
          {
            this.cursor = getDatabase().rawQuery("SELECT b.* FROM (SELECT psos.Product_ID,psos.ProductSku_ID,psos.ProductSku_Name,psos.ProductSku_Code,psos.ProductSku_BarCode,psos.ProductSku_Spec,psos.PruductSku_Description,psos.ProductSku_Image,psos.ProductSku_Unit,psos.PG_ID,psos.PG_Name,psos.Retail_Price,psos.Fixed_Cost,psos.WholeSale_Price,psos.Purchase_Price,psos.Onhand_Quantity,psos.Qty_Order,psos.Qty_Retail,psos.Qty_Wholesale FROM V_ProductSkuOnSale AS psos WHERE psos.Store_ID =? AND psos.ProductSku_Status = 1 AND psos.PG_ID =? AND psos.PSS_Status IN (1,2)UNION SELECT ps.Product_ID,ps.ProductSku_ID,ps.ProductSku_Name,ps.ProductSku_Code,ps.ProductSku_BarCode,ps.ProductSku_Spec,ps.PruductSku_Description,ps.ProductSku_Image,ps.ProductSku_Unit,ps.PG_ID,ps.PG_Name,ps.Retail_Price,ps.Fixed_Cost,ps.WholeSale_Price,ps.Purchase_Price,0  as 'Onhand_Quantity',0  as 'Qty_Order',0  as  'Qty_Retail',0  as  'Qty_Wholesale'  FROM V_ProductSku AS ps WHERE ps.ProductSku_Status = 1 AND ps.PG_ID = ? AND ps.ProductSku_ID NOT IN ( SELECT a.ProductSku_ID FROM V_ProductSkuOnSale AS a WHERE a.Store_ID = ?))  AS b WHERE b.ProductSku_Name like '%<search>%' OR b.ProductSku_Code LIKE '%<search>%' OR b.ProductSku_BarCode LIKE '%<search>%' ORDER BY b.Onhand_Quantity DESC limit 20 OFFSET ?".replace(" AND psos.PG_ID =?", "").replace(" AND ps.PG_ID = ?", "").replace("<search>", paramGoodsFilter.getSearch()), new String[] { l, l, paramGoodsFilter.getOffset() });
            LogUtils.i("SQL_SELECT_STOCKIN_ALL_PRODUCT_BY_TABLEVIEW1:SELECT b.* FROM (SELECT psos.Product_ID,psos.ProductSku_ID,psos.ProductSku_Name,psos.ProductSku_Code,psos.ProductSku_BarCode,psos.ProductSku_Spec,psos.PruductSku_Description,psos.ProductSku_Image,psos.ProductSku_Unit,psos.PG_ID,psos.PG_Name,psos.Retail_Price,psos.Fixed_Cost,psos.WholeSale_Price,psos.Purchase_Price,psos.Onhand_Quantity,psos.Qty_Order,psos.Qty_Retail,psos.Qty_Wholesale FROM V_ProductSkuOnSale AS psos WHERE psos.Store_ID =? AND psos.ProductSku_Status = 1 AND psos.PG_ID =? AND psos.PSS_Status IN (1,2)UNION SELECT ps.Product_ID,ps.ProductSku_ID,ps.ProductSku_Name,ps.ProductSku_Code,ps.ProductSku_BarCode,ps.ProductSku_Spec,ps.PruductSku_Description,ps.ProductSku_Image,ps.ProductSku_Unit,ps.PG_ID,ps.PG_Name,ps.Retail_Price,ps.Fixed_Cost,ps.WholeSale_Price,ps.Purchase_Price,0  as 'Onhand_Quantity',0  as 'Qty_Order',0  as  'Qty_Retail',0  as  'Qty_Wholesale'  FROM V_ProductSku AS ps WHERE ps.ProductSku_Status = 1 AND ps.PG_ID = ? AND ps.ProductSku_ID NOT IN ( SELECT a.ProductSku_ID FROM V_ProductSkuOnSale AS a WHERE a.Store_ID = ?))  AS b WHERE b.ProductSku_Name like '%<search>%' OR b.ProductSku_Code LIKE '%<search>%' OR b.ProductSku_BarCode LIKE '%<search>%' ORDER BY b.Onhand_Quantity DESC limit 20 OFFSET ?");
            break label1046;
            if (this.cursor.moveToNext()) {
              break label555;
            }
            LogUtils.i("sql2:" + this.sql);
            return localArrayList;
          }
          this.cursor = getDatabase().rawQuery("SELECT b.* FROM (SELECT psos.Product_ID,psos.ProductSku_ID,psos.ProductSku_Name,psos.ProductSku_Code,psos.ProductSku_BarCode,psos.ProductSku_Spec,psos.PruductSku_Description,psos.ProductSku_Image,psos.ProductSku_Unit,psos.PG_ID,psos.PG_Name,psos.Retail_Price,psos.Fixed_Cost,psos.WholeSale_Price,psos.Purchase_Price,psos.Onhand_Quantity,psos.Qty_Order,psos.Qty_Retail,psos.Qty_Wholesale FROM V_ProductSkuOnSale AS psos WHERE psos.Store_ID =? AND psos.ProductSku_Status = 1 AND psos.PG_ID =? AND psos.PSS_Status IN (1,2)UNION SELECT ps.Product_ID,ps.ProductSku_ID,ps.ProductSku_Name,ps.ProductSku_Code,ps.ProductSku_BarCode,ps.ProductSku_Spec,ps.PruductSku_Description,ps.ProductSku_Image,ps.ProductSku_Unit,ps.PG_ID,ps.PG_Name,ps.Retail_Price,ps.Fixed_Cost,ps.WholeSale_Price,ps.Purchase_Price,0  as 'Onhand_Quantity',0  as 'Qty_Order',0  as  'Qty_Retail',0  as  'Qty_Wholesale'  FROM V_ProductSku AS ps WHERE ps.ProductSku_Status = 1 AND ps.PG_ID = ? AND ps.ProductSku_ID NOT IN ( SELECT a.ProductSku_ID FROM V_ProductSkuOnSale AS a WHERE a.Store_ID = ?))  AS b WHERE b.ProductSku_Name like '%<search>%' OR b.ProductSku_Code LIKE '%<search>%' OR b.ProductSku_BarCode LIKE '%<search>%' ORDER BY b.Onhand_Quantity DESC limit 20 OFFSET ?".replace(" AND psos.PG_ID =?", "").replace(" AND ps.PG_ID = ?", "").replace("WHERE b.ProductSku_Name like '%<search>%' OR b.ProductSku_Code LIKE '%<search>%' OR b.ProductSku_BarCode LIKE '%<search>%' ", ""), new String[] { l, l, paramGoodsFilter.getOffset() });
        }
      }
      catch (SQLException paramGoodsFilter)
      {
        paramGoodsFilter.printStackTrace();
        return null;
      }
      if (paramGoodsFilter.getSearch() != null)
      {
        this.cursor = getDatabase().rawQuery("SELECT b.* FROM (SELECT psos.Product_ID,psos.ProductSku_ID,psos.ProductSku_Name,psos.ProductSku_Code,psos.ProductSku_BarCode,psos.ProductSku_Spec,psos.PruductSku_Description,psos.ProductSku_Image,psos.ProductSku_Unit,psos.PG_ID,psos.PG_Name,psos.Retail_Price,psos.Fixed_Cost,psos.WholeSale_Price,psos.Purchase_Price,psos.Onhand_Quantity,psos.Qty_Order,psos.Qty_Retail,psos.Qty_Wholesale FROM V_ProductSkuOnSale AS psos WHERE psos.Store_ID =? AND psos.ProductSku_Status = 1 AND psos.PG_ID =? AND psos.PSS_Status IN (1,2)UNION SELECT ps.Product_ID,ps.ProductSku_ID,ps.ProductSku_Name,ps.ProductSku_Code,ps.ProductSku_BarCode,ps.ProductSku_Spec,ps.PruductSku_Description,ps.ProductSku_Image,ps.ProductSku_Unit,ps.PG_ID,ps.PG_Name,ps.Retail_Price,ps.Fixed_Cost,ps.WholeSale_Price,ps.Purchase_Price,0  as 'Onhand_Quantity',0  as 'Qty_Order',0  as  'Qty_Retail',0  as  'Qty_Wholesale'  FROM V_ProductSku AS ps WHERE ps.ProductSku_Status = 1 AND ps.PG_ID = ? AND ps.ProductSku_ID NOT IN ( SELECT a.ProductSku_ID FROM V_ProductSkuOnSale AS a WHERE a.Store_ID = ?))  AS b WHERE b.ProductSku_Name like '%<search>%' OR b.ProductSku_Code LIKE '%<search>%' OR b.ProductSku_BarCode LIKE '%<search>%' ORDER BY b.Onhand_Quantity DESC limit 20 OFFSET ?".replace("<search>", paramGoodsFilter.getSearch()), new String[] { l, paramGoodsFilter.getGroup(), paramGoodsFilter.getGroup(), l, paramGoodsFilter.getOffset() });
        LogUtils.i("SQL_SELECT_STOCKIN_ALL_PRODUCT_BY_TABLEVIEW3:SELECT b.* FROM (SELECT psos.Product_ID,psos.ProductSku_ID,psos.ProductSku_Name,psos.ProductSku_Code,psos.ProductSku_BarCode,psos.ProductSku_Spec,psos.PruductSku_Description,psos.ProductSku_Image,psos.ProductSku_Unit,psos.PG_ID,psos.PG_Name,psos.Retail_Price,psos.Fixed_Cost,psos.WholeSale_Price,psos.Purchase_Price,psos.Onhand_Quantity,psos.Qty_Order,psos.Qty_Retail,psos.Qty_Wholesale FROM V_ProductSkuOnSale AS psos WHERE psos.Store_ID =? AND psos.ProductSku_Status = 1 AND psos.PG_ID =? AND psos.PSS_Status IN (1,2)UNION SELECT ps.Product_ID,ps.ProductSku_ID,ps.ProductSku_Name,ps.ProductSku_Code,ps.ProductSku_BarCode,ps.ProductSku_Spec,ps.PruductSku_Description,ps.ProductSku_Image,ps.ProductSku_Unit,ps.PG_ID,ps.PG_Name,ps.Retail_Price,ps.Fixed_Cost,ps.WholeSale_Price,ps.Purchase_Price,0  as 'Onhand_Quantity',0  as 'Qty_Order',0  as  'Qty_Retail',0  as  'Qty_Wholesale'  FROM V_ProductSku AS ps WHERE ps.ProductSku_Status = 1 AND ps.PG_ID = ? AND ps.ProductSku_ID NOT IN ( SELECT a.ProductSku_ID FROM V_ProductSkuOnSale AS a WHERE a.Store_ID = ?))  AS b WHERE b.ProductSku_Name like '%<search>%' OR b.ProductSku_Code LIKE '%<search>%' OR b.ProductSku_BarCode LIKE '%<search>%' ORDER BY b.Onhand_Quantity DESC limit 20 OFFSET ?");
      }
      else
      {
        this.cursor = getDatabase().rawQuery("SELECT b.* FROM (SELECT psos.Product_ID,psos.ProductSku_ID,psos.ProductSku_Name,psos.ProductSku_Code,psos.ProductSku_BarCode,psos.ProductSku_Spec,psos.PruductSku_Description,psos.ProductSku_Image,psos.ProductSku_Unit,psos.PG_ID,psos.PG_Name,psos.Retail_Price,psos.Fixed_Cost,psos.WholeSale_Price,psos.Purchase_Price,psos.Onhand_Quantity,psos.Qty_Order,psos.Qty_Retail,psos.Qty_Wholesale FROM V_ProductSkuOnSale AS psos WHERE psos.Store_ID =? AND psos.ProductSku_Status = 1 AND psos.PG_ID =? AND psos.PSS_Status IN (1,2)UNION SELECT ps.Product_ID,ps.ProductSku_ID,ps.ProductSku_Name,ps.ProductSku_Code,ps.ProductSku_BarCode,ps.ProductSku_Spec,ps.PruductSku_Description,ps.ProductSku_Image,ps.ProductSku_Unit,ps.PG_ID,ps.PG_Name,ps.Retail_Price,ps.Fixed_Cost,ps.WholeSale_Price,ps.Purchase_Price,0  as 'Onhand_Quantity',0  as 'Qty_Order',0  as  'Qty_Retail',0  as  'Qty_Wholesale'  FROM V_ProductSku AS ps WHERE ps.ProductSku_Status = 1 AND ps.PG_ID = ? AND ps.ProductSku_ID NOT IN ( SELECT a.ProductSku_ID FROM V_ProductSkuOnSale AS a WHERE a.Store_ID = ?))  AS b WHERE b.ProductSku_Name like '%<search>%' OR b.ProductSku_Code LIKE '%<search>%' OR b.ProductSku_BarCode LIKE '%<search>%' ORDER BY b.Onhand_Quantity DESC limit 20 OFFSET ?".replace("WHERE b.ProductSku_Name like '%<search>%' OR b.ProductSku_Code LIKE '%<search>%' OR b.ProductSku_BarCode LIKE '%<search>%' ", ""), new String[] { l, paramGoodsFilter.getGroup(), paramGoodsFilter.getGroup(), l, paramGoodsFilter.getOffset() });
        LogUtils.i("SQL_SELECT_STOCKIN_ALL_PRODUCT_BY_TABLEVIEW4:SELECT b.* FROM (SELECT psos.Product_ID,psos.ProductSku_ID,psos.ProductSku_Name,psos.ProductSku_Code,psos.ProductSku_BarCode,psos.ProductSku_Spec,psos.PruductSku_Description,psos.ProductSku_Image,psos.ProductSku_Unit,psos.PG_ID,psos.PG_Name,psos.Retail_Price,psos.Fixed_Cost,psos.WholeSale_Price,psos.Purchase_Price,psos.Onhand_Quantity,psos.Qty_Order,psos.Qty_Retail,psos.Qty_Wholesale FROM V_ProductSkuOnSale AS psos WHERE psos.Store_ID =? AND psos.ProductSku_Status = 1 AND psos.PG_ID =? AND psos.PSS_Status IN (1,2)UNION SELECT ps.Product_ID,ps.ProductSku_ID,ps.ProductSku_Name,ps.ProductSku_Code,ps.ProductSku_BarCode,ps.ProductSku_Spec,ps.PruductSku_Description,ps.ProductSku_Image,ps.ProductSku_Unit,ps.PG_ID,ps.PG_Name,ps.Retail_Price,ps.Fixed_Cost,ps.WholeSale_Price,ps.Purchase_Price,0  as 'Onhand_Quantity',0  as 'Qty_Order',0  as  'Qty_Retail',0  as  'Qty_Wholesale'  FROM V_ProductSku AS ps WHERE ps.ProductSku_Status = 1 AND ps.PG_ID = ? AND ps.ProductSku_ID NOT IN ( SELECT a.ProductSku_ID FROM V_ProductSkuOnSale AS a WHERE a.Store_ID = ?))  AS b WHERE b.ProductSku_Name like '%<search>%' OR b.ProductSku_Code LIKE '%<search>%' OR b.ProductSku_BarCode LIKE '%<search>%' ORDER BY b.Onhand_Quantity DESC limit 20 OFFSET ?");
        break label1046;
        paramGoodsFilter = new Goods();
        paramGoodsFilter.setId(this.cursor.getLong(this.cursor.getColumnIndex("Product_ID")));
        paramGoodsFilter.setSkuId(this.cursor.getLong(this.cursor.getColumnIndex("ProductSku_ID")));
        paramGoodsFilter.setName(this.cursor.getString(this.cursor.getColumnIndex("ProductSku_Name")));
        paramGoodsFilter.setNumber(this.cursor.getString(this.cursor.getColumnIndex("ProductSku_Code")));
        paramGoodsFilter.setCode(this.cursor.getString(this.cursor.getColumnIndex("ProductSku_BarCode")));
        paramGoodsFilter.setSize(this.cursor.getString(this.cursor.getColumnIndex("ProductSku_Spec")));
        paramGoodsFilter.setDescribe(this.cursor.getString(this.cursor.getColumnIndex("PruductSku_Description")));
        paramGoodsFilter.setPicture(this.cursor.getString(this.cursor.getColumnIndex("ProductSku_Image")));
        paramGoodsFilter.setUnit(this.cursor.getString(this.cursor.getColumnIndex("ProductSku_Unit")));
        ProductGroup localProductGroup = new ProductGroup();
        localProductGroup.setPG_ID(this.cursor.getLong(this.cursor.getColumnIndex("PG_ID")));
        localProductGroup.setPG_Name(this.cursor.getString(this.cursor.getColumnIndex("PG_Name")));
        paramGoodsFilter.setGroup(localProductGroup);
        paramGoodsFilter.setSellPrice(this.cursor.getDouble(this.cursor.getColumnIndex("Retail_Price")));
        paramGoodsFilter.setCostPrice(this.cursor.getDouble(this.cursor.getColumnIndex("Fixed_Cost")));
        paramGoodsFilter.setWholesalePrice(this.cursor.getDouble(this.cursor.getColumnIndex("WholeSale_Price")));
        paramGoodsFilter.setStockPrice(this.cursor.getDouble(this.cursor.getColumnIndex("Purchase_Price")));
        paramGoodsFilter.setAmount(this.cursor.getDouble(this.cursor.getColumnIndex("Onhand_Quantity")));
        paramGoodsFilter.setStockAmount(this.cursor.getDouble(this.cursor.getColumnIndex("Qty_Order")));
        paramGoodsFilter.setRetailAmount(this.cursor.getDouble(this.cursor.getColumnIndex("Qty_Retail")));
        paramGoodsFilter.setWholeSaleAmount(this.cursor.getDouble(this.cursor.getColumnIndex("Qty_Wholesale")));
        localArrayList.add(paramGoodsFilter);
      }
    }
  }
  
  public boolean isProductBarCodeExist(String paramString)
  {
    return getDatabase().rawQuery("SELECT ps.ProductSku_ID FROM ProductSku AS ps WHERE ps.ProductSku_BarCode = <ProductSku_BarCode>".replace("<ProductSku_BarCode>", "'" + paramString + "'"), null).moveToNext();
  }
  
  public boolean isProductNameExist(String paramString)
  {
    return getDatabase().rawQuery("SELECT ps.ProductSku_ID FROM ProductSku AS ps WHERE ps.ProductSku_Name = <ProductSku_Name>".replace("<ProductSku_Name>", "'" + paramString + "'"), null).moveToNext();
  }
  
  public void updateProductImg(ImgProductParam paramImgProductParam, RequestCallBack<String> paramRequestCallBack)
  {
    ImgAPI.uploadProductImg(paramImgProductParam, paramRequestCallBack);
  }
}