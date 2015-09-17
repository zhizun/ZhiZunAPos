package com.zizun.cs.biz.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.PurchasesDao;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.common.utils.LogUtil;
import com.zizun.cs.entities.PO_Header;
import com.zizun.cs.entities.ProductSku;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.biz.Associate;
import com.zizun.cs.ui.entity.History;
import com.zizun.cs.ui.entity.PurchaseGoods;
import java.util.ArrayList;

public class PurchaseDaoImpl
  extends BaseDaoImpl
  implements PurchasesDao
{
  static final String COMMON_PURCHASE_PRODUCT_BY_GROUPID = "SELECT b.*FROM (SELECT  psos.Product_ID, psos.ProductSku_ID, psos.ProductSku_Name, psos.ProductSku_Code, psos.ProductSku_BarCode, psos.ProductSku_Spec, psos.PruductSku_Description, psos.ProductSku_Image, psos.ProductSku_Unit, psos.PG_ID, psos.PG_Name, psos.Retail_Price, psos.Fixed_Cost, psos.WholeSale_Price, psos.Purchase_Price, psos.Onhand_Quantity,  psos.Qty_Order,  psos.Qty_Retail,  psos.Qty_Wholesale  FROM V_ProductSkuOnSale AS psos  WHERE psos.Store_ID = <门店ID>  AND psos.ProductSku_Status = 1  AND psos.PG_ID = <分类ID>  AND psos.PSS_Status IN (1,2)  UNION SELECT  ps.Product_ID, ps.ProductSku_ID, ps.ProductSku_Name, ps.ProductSku_Code, ps.ProductSku_BarCode, ps.ProductSku_Spec, ps.PruductSku_Description, ps.ProductSku_Image, ps.ProductSku_Unit, ps.PG_ID, ps.PG_Name, ps.Retail_Price, ps.Fixed_Cost, ps.WholeSale_Price, ps.Purchase_Price, 0  as 'Onhand_Quantity',  0  as 'Qty_Order',  0  as 'Qty_Retail',  0  as 'Qty_Wholesale'  FROM V_ProductSku AS ps  WHERE   ps.ProductSku_Status = 1  and ps.ProductSku_ID  NOT IN (SELECT a.ProductSku_ID   FROM V_ProductSkuOnSale AS a  WHERE a.Store_ID = <门店ID> ))  AS b  WHERE b.PG_ID = <分类ID> ORDER BY b.Qty_Order DESC limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>";
  static final String COMMON_PURCHASE_PRODUCT_BY_STOREID = "SELECT b.*FROM (SELECT  psos.Product_ID, psos.ProductSku_ID, psos.ProductSku_Name, psos.ProductSku_Code, psos.ProductSku_BarCode, psos.ProductSku_Spec, psos.PruductSku_Description, psos.ProductSku_Image, psos.ProductSku_Unit, psos.PG_ID, psos.PG_Name, psos.Retail_Price, psos.Fixed_Cost, psos.WholeSale_Price, psos.Purchase_Price, psos.Onhand_Quantity,  psos.Qty_Order,  psos.Qty_Retail,  psos.Qty_Wholesale  FROM V_ProductSkuOnSale AS psos  WHERE psos.Store_ID = <门店ID>  AND psos.ProductSku_Status = 1  AND psos.PSS_Status IN (1,2)  UNION SELECT  ps.Product_ID, ps.ProductSku_ID, ps.ProductSku_Name, ps.ProductSku_Code, ps.ProductSku_BarCode, ps.ProductSku_Spec, ps.PruductSku_Description, ps.ProductSku_Image, ps.ProductSku_Unit, ps.PG_ID, ps.PG_Name, ps.Retail_Price, ps.Fixed_Cost, ps.WholeSale_Price, ps.Purchase_Price, 0  as 'Onhand_Quantity',  0  as 'Qty_Order',  0  as 'Qty_Retail',  0  as 'Qty_Wholesale'  FROM V_ProductSku AS ps  WHERE 1 = 1  AND ps.ProductSku_Status = 1  and ps.ProductSku_ID NOT IN (SELECT a.ProductSku_ID   FROM V_ProductSkuOnSale AS a  WHERE    a.Store_ID = <门店ID> ))  AS b  ORDER BY b.Qty_Order DESC limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>";
  public static final String GET_PURCHASEBILL_BY_PO_ID = "select * from  V_POHeader AS vp WHERE vp.PO_ID = <采购单号>";
  static final String GET_PURCHASE_PRODUCT_BY_STOREID_AND_OTHER_CONDTION = "SELECT b.*FROM (SELECT  psos.Product_ID, psos.ProductSku_ID, psos.ProductSku_Name, psos.ProductSku_Code, psos.ProductSku_BarCode, psos.ProductSku_Spec, psos.PruductSku_Description, psos.ProductSku_Image, psos.ProductSku_Unit, psos.PG_ID, psos.PG_Name, psos.Retail_Price, psos.Fixed_Cost, psos.WholeSale_Price, psos.Purchase_Price, psos.Onhand_Quantity  FROM V_ProductSkuOnSale AS psos  WHERE psos.Store_ID = <门店ID>  AND psos.ProductSku_Status = 1  AND psos.PSS_Status IN (1,2)  UNION SELECT  ps.Product_ID, ps.ProductSku_ID, ps.ProductSku_Name, ps.ProductSku_Code, ps.ProductSku_BarCode, ps.ProductSku_Spec, ps.PruductSku_Description, ps.ProductSku_Image, ps.ProductSku_Unit, ps.PG_ID, ps.PG_Name, ps.Retail_Price, ps.Fixed_Cost, ps.WholeSale_Price, ps.Purchase_Price, 0  as 'Onhand_Quantity'  FROM V_ProductSku AS ps  WHERE    ps.ProductSku_Status = 1  and ps.ProductSku_ID  NOT IN (SELECT a.ProductSku_ID  FROM V_ProductSkuOnSale AS a  WHERE     a.Store_ID = <门店ID>   ))  AS b  where b.ProductSku_Name like '%<产品名称>%'OR b.ProductSku_Code like '%<产品名称>%'OR b.ProductSku_BarCode like '%<产品名称>%' ORDER BY b.ProductSku_ID DESC limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>";
  static final String INSERT_TO_PO_DETAIL = "INSERT INTO PO_Detail(Merchant_ID,PD_ID,PO_ID,ProductSku_ID,PD_Quantiy,PD_PurchasePrice,PD_CostPrice,PD_RetailPrice,Create_DT,Modify_DT) VALUES (< Merchant_ID >,< PD_ID >,< PO_ID >,< ProductSku_ID >,< PD_Quantiy >,< PD_PurchasePrice >,< PD_CostPrice >,< PD_RetailPrice >,< Create_ID >,< Modify_ID >)";
  static final String INSERT_TO_PO_HEADER = "insert into PO_HEADER (Merchant_ID,PO_ID,PO_Number,PO_SupplierID,PO_StoreID,PO_Date,PO_Staff,PO_Status,PO_ProductAmount,PO_PlanAmount,PO_ActualAmount,IS_Settle,Create_ID,Modify_ID) values (< Merchant_ID >,< PO_ID >,< PO_Number >,< PO_SupplierID >,< PO_StoreID >,< PO_Date >,< PO_Staff >,1,< PO_ProductAmount >,< PO_PlanAmount >,< PO_ActualAmount >,< IS_Settle >,< Create_ID >,< Modify_ID >)";
  static final String SELECT_ALL_HISTORY = "SELECT ph.PO_ID,ph.PO_Number,ph.PO_Status,ph.PO_PlanAmount,ph.PO_ActualAmount,s.Supplier_Name,s.Supplier_Contact FROM PO_Header AS ph INNER JOIN Supplier AS s ON ph.PO_SupplierID = s.Supplier_ID WHERE ph.PO_Status = 1 And ph. PO_StoreID = <门店ID> ORDER BY ph.PO_Number DESC  limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>";
  static final String SELECT_ALL_HISTORY_VIEW = "SELECT * FROM V_POHeader AS vp WHERE vp. Store_ID = <门店ID> ORDER BY vp.PO_Number DESC limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>";
  static final String SELECT_PRODUCT_BUY_LIST = "SELECT ps.ProductSku_Name,pd.ProductSku_ID,pd.PD_CostPrice,pd.PD_RetailPrice,ps.ProductSku_Image,pd.PD_Quantiy,pd.PD_PurchasePrice,pd.PD_Quantiy*pd.PD_PurchasePrice AS 'SubTotal' FROM PO_Detail AS pd INNER JOIN  ProductSku AS ps ON ps.ProductSku_ID = pd.ProductSku_ID WHERE pd.PO_ID = <采购单ID> ORDER BY ps.ProductSku_Name";
  static final String SELECT_PURCHASEGOODS_BY_BARCODE = "SELECT b.*FROM (SELECT  psos.Product_ID, psos.ProductSku_ID, psos.ProductSku_Name, psos.ProductSku_Code, psos.ProductSku_BarCode, psos.ProductSku_Spec, psos.PruductSku_Description, psos.ProductSku_Image, psos.ProductSku_Unit, psos.PG_ID, psos.PG_Name, psos.Retail_Price, psos.Fixed_Cost, psos.WholeSale_Price, psos.Purchase_Price, psos.Onhand_Quantity,  psos.Qty_Order,  psos.Qty_Retail,  psos.Qty_Wholesale  FROM V_ProductSkuOnSale AS psos  WHERE psos.Store_ID = <门店ID>  AND psos.ProductSku_Status = 1  AND psos.PSS_Status IN (1,2)  UNION SELECT  ps.Product_ID, ps.ProductSku_ID, ps.ProductSku_Name, ps.ProductSku_Code, ps.ProductSku_BarCode, ps.ProductSku_Spec, ps.PruductSku_Description, ps.ProductSku_Image, ps.ProductSku_Unit, ps.PG_ID, ps.PG_Name, ps.Retail_Price, ps.Fixed_Cost, ps.WholeSale_Price, ps.Purchase_Price, 0  as 'Onhand_Quantity',  0  as 'Qty_Order',  0  as 'Qty_Retail',  0  as 'Qty_Wholesale'  FROM V_ProductSku AS ps  WHERE    ps.ProductSku_Status = 1  and ps.ProductSku_ID  NOT IN (SELECT a.ProductSku_ID  FROM V_ProductSkuOnSale AS a  WHERE    a.Store_ID = <门店ID> ))  AS b  WHERE b.ProductSku_BarCode = <产品条形码> ";
  static final String UPDATE_BILL_STATUS = "UPDATE PO_Header SET PO_Status = 5,Modify_DT = ?,Modify_ID = ?,Sync_DataType = 2,Sync_Status = 1 WHERE PO_ID = ?";
  static final String UPDATE_SYNC_STATUS = "UPDATE PO_Header SET  Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1 ELSE 2 END WHERE PO_ID = ?";
  
  public PurchaseDaoImpl(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }
  
  public ArrayList<History> getAllHistory(long paramLong, int paramInt1, int paramInt2)
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = getDatabase().rawQuery("SELECT * FROM V_POHeader AS vp WHERE vp. Store_ID = <门店ID> ORDER BY vp.PO_Number DESC limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>".replace("<门店ID>", String.valueOf(UserManager.getInstance().getCurrentStore().getStore_ID())).replace("<PAGE_SIZE>", String.valueOf(paramInt1)).replace("<INDEX>", String.valueOf(paramInt2)), null);
    for (;;)
    {
      if (!localCursor.moveToNext()) {
        return localArrayList;
      }
      History localHistory = new History();
      Associate localAssociate = new Associate();
      PO_Header localPO_Header = new PO_Header();
      localPO_Header.setPO_ID(localCursor.getLong(localCursor.getColumnIndex("PO_ID")));
      localPO_Header.setPO_Date(DateTimeUtil.getTimestampFromString(localCursor.getString(localCursor.getColumnIndex("PO_Date"))));
      localPO_Header.setPO_Number(localCursor.getString(localCursor.getColumnIndex("PO_Number")));
      localPO_Header.setPO_Status((byte)localCursor.getInt(localCursor.getColumnIndex("PO_Status")));
      localPO_Header.setPO_PlanAmount(localCursor.getDouble(localCursor.getColumnIndex("PO_PlanAmount")));
      localPO_Header.setPO_ActualAmount(localCursor.getDouble(localCursor.getColumnIndex("PO_ActualAmount")));
      localAssociate.setCompany(localCursor.getString(localCursor.getColumnIndex("Supplier_Name")));
      localAssociate.setName(localCursor.getString(localCursor.getColumnIndex("Supplier_Contact")));
      localHistory.setPo_Header(localPO_Header);
      localHistory.setAssociate(localAssociate);
      localArrayList.add(localHistory);
    }
  }
  
  public ArrayList<ProductSku> getProductInStorage(long paramLong, int paramInt1, int paramInt2)
    throws IllegalAccessException, InstantiationException, ClassNotFoundException
  {
    return null;
  }
  
  public History getPurchaseBillbyPo_Id(long paramLong)
  {
    History localHistory = null;
    Associate localAssociate = new Associate();
    PO_Header localPO_Header = new PO_Header();
    Object localObject = getDatabase();
    String str = "select * from  V_POHeader AS vp WHERE vp.PO_ID = <采购单号>".replace("<采购单号>", paramLong);
    LogUtil.logD(str);
    localObject = ((SQLiteDatabase)localObject).rawQuery(str, null);
    for (;;)
    {
      if (!((Cursor)localObject).moveToNext()) {
        return localHistory;
      }
      localHistory = new History();
      localHistory.setBillNum(((Cursor)localObject).getString(((Cursor)localObject).getColumnIndex("PO_Number")));
      localHistory.setDate(((Cursor)localObject).getString(((Cursor)localObject).getColumnIndex("PO_Date")));
      localAssociate.setCompany(((Cursor)localObject).getString(((Cursor)localObject).getColumnIndex("Supplier_Name")));
      localAssociate.setName(((Cursor)localObject).getString(((Cursor)localObject).getColumnIndex("Supplier_Contact")));
      localHistory.setAssociate(localAssociate);
      localPO_Header.setPO_ActualAmount(((Cursor)localObject).getDouble(((Cursor)localObject).getColumnIndex("PO_ActualAmount")));
      localPO_Header.setPO_PlanAmount(((Cursor)localObject).getDouble(((Cursor)localObject).getColumnIndex("PO_PlanAmount")));
      localHistory.setPo_Header(localPO_Header);
    }
  }
  
  public PurchaseGoods getPurchaseGoodsByBarCode(String paramString)
  {
    Object localObject = null;
    paramString = "SELECT b.*FROM (SELECT  psos.Product_ID, psos.ProductSku_ID, psos.ProductSku_Name, psos.ProductSku_Code, psos.ProductSku_BarCode, psos.ProductSku_Spec, psos.PruductSku_Description, psos.ProductSku_Image, psos.ProductSku_Unit, psos.PG_ID, psos.PG_Name, psos.Retail_Price, psos.Fixed_Cost, psos.WholeSale_Price, psos.Purchase_Price, psos.Onhand_Quantity,  psos.Qty_Order,  psos.Qty_Retail,  psos.Qty_Wholesale  FROM V_ProductSkuOnSale AS psos  WHERE psos.Store_ID = <门店ID>  AND psos.ProductSku_Status = 1  AND psos.PSS_Status IN (1,2)  UNION SELECT  ps.Product_ID, ps.ProductSku_ID, ps.ProductSku_Name, ps.ProductSku_Code, ps.ProductSku_BarCode, ps.ProductSku_Spec, ps.PruductSku_Description, ps.ProductSku_Image, ps.ProductSku_Unit, ps.PG_ID, ps.PG_Name, ps.Retail_Price, ps.Fixed_Cost, ps.WholeSale_Price, ps.Purchase_Price, 0  as 'Onhand_Quantity',  0  as 'Qty_Order',  0  as 'Qty_Retail',  0  as 'Qty_Wholesale'  FROM V_ProductSku AS ps  WHERE    ps.ProductSku_Status = 1  and ps.ProductSku_ID  NOT IN (SELECT a.ProductSku_ID  FROM V_ProductSkuOnSale AS a  WHERE    a.Store_ID = <门店ID> ))  AS b  WHERE b.ProductSku_BarCode = <产品条形码> ".replace("<门店ID>", getStoreID()).replace("<产品条形码>", "'" + paramString + "'");
    LogUtils.i(paramString);
    Cursor localCursor = getDatabase().rawQuery(paramString, null);
    paramString = (String)localObject;
    for (;;)
    {
      if (!localCursor.moveToNext()) {
        return paramString;
      }
      paramString = new PurchaseGoods();
      paramString.setProductSku_Name(localCursor.getString(localCursor.getColumnIndex("ProductSku_Name")));
      paramString.setProductSku_Image(localCursor.getString(localCursor.getColumnIndex("ProductSku_Image")));
      paramString.setPurchase_Price(localCursor.getDouble(localCursor.getColumnIndex("Purchase_Price")));
      paramString.setFixed_Cost(localCursor.getDouble(localCursor.getColumnIndex("Fixed_Cost")));
      paramString.setProductSku_ID(localCursor.getLong(localCursor.getColumnIndex("ProductSku_ID")));
      paramString.setRetail_Price(localCursor.getLong(localCursor.getColumnIndex("Retail_Price")));
      paramString.setOnhand_Quantity(localCursor.getInt(localCursor.getColumnIndex("Onhand_Quantity")));
      paramString.setQty_Order(localCursor.getDouble(localCursor.getColumnIndex("Qty_Order")));
    }
  }
  
  public ArrayList<PurchaseGoods> getPurchaseGoodsByLikeCondtion(long paramLong, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    paramString = getDatabase().rawQuery("SELECT b.*FROM (SELECT  psos.Product_ID, psos.ProductSku_ID, psos.ProductSku_Name, psos.ProductSku_Code, psos.ProductSku_BarCode, psos.ProductSku_Spec, psos.PruductSku_Description, psos.ProductSku_Image,psos.ProductSku_Unit, psos.PG_ID,psos.PG_Name,psos.Retail_Price,psos.Fixed_Cost, psos.WholeSale_Price, psos.Purchase_Price, psos.Onhand_Quantity,  psos.Qty_Order,  psos.Qty_Retail,  psos.Qty_Wholesale  FROM V_ProductSkuOnSale AS psos  WHERE psos.Store_ID =  " + paramLong + " AND psos.ProductSku_Status = 1 " + " AND psos.PSS_Status IN (1,2) " + " UNION SELECT " + " ps.Product_ID," + " ps.ProductSku_ID," + " ps.ProductSku_Name," + " ps.ProductSku_Code," + " ps.ProductSku_BarCode," + " ps.ProductSku_Spec," + " ps.PruductSku_Description," + " ps.ProductSku_Image," + " ps.ProductSku_Unit," + " ps.PG_ID," + " ps.PG_Name," + " ps.Retail_Price," + " ps.Fixed_Cost," + " ps.WholeSale_Price," + " ps.Purchase_Price," + " 0  as 'Onhand_Quantity', " + " 0  as 'Qty_Order', " + " 0  as 'Qty_Retail', " + " 0  as 'Qty_Wholesale' " + " FROM V_ProductSku AS ps " + " WHERE  " + "  ps.ProductSku_Status = 1 " + " and ps.ProductSku_ID  NOT IN (SELECT a.ProductSku_ID " + " FROM V_ProductSkuOnSale AS a " + " WHERE  " + "  a.Store_ID = " + paramLong + " ))  AS b where b.ProductSku_Name like '%" + paramString + "%' ORDER BY b.Qty_Order DESC", null);
    for (;;)
    {
      if (!paramString.moveToNext())
      {
        LogUtils.d(localArrayList.toString());
        return localArrayList;
      }
      PurchaseGoods localPurchaseGoods = new PurchaseGoods();
      localPurchaseGoods.setProduct_ID(paramString.getLong(paramString.getColumnIndex("Product_ID")));
      localPurchaseGoods.setPG_ID(paramString.getLong(paramString.getColumnIndex("PG_ID")));
      localPurchaseGoods.setProductSku_ID(paramString.getLong(paramString.getColumnIndex("ProductSku_ID")));
      localPurchaseGoods.setProductSku_Name(paramString.getString(paramString.getColumnIndex("ProductSku_Name")));
      localPurchaseGoods.setProductSku_BarCode(paramString.getString(paramString.getColumnIndex("ProductSku_BarCode")));
      localPurchaseGoods.setProductSku_Code(paramString.getString(paramString.getColumnIndex("ProductSku_Code")));
      localPurchaseGoods.setProductSku_Image(paramString.getString(paramString.getColumnIndex("ProductSku_Image")));
      localPurchaseGoods.setProductSku_ID(paramString.getLong(paramString.getColumnIndex("ProductSku_ID")));
      localPurchaseGoods.setProductSku_Spec(paramString.getString(paramString.getColumnIndex("ProductSku_Spec")));
      localPurchaseGoods.setProductSku_Unit(paramString.getString(paramString.getColumnIndex("ProductSku_Unit")));
      localPurchaseGoods.setRetail_Price(paramString.getDouble(paramString.getColumnIndex("Retail_Price")));
      localPurchaseGoods.setFixed_Cost(paramString.getDouble(paramString.getColumnIndex("Fixed_Cost")));
      localPurchaseGoods.setWholeSale_Price(paramString.getDouble(paramString.getColumnIndex("WholeSale_Price")));
      localPurchaseGoods.setPurchase_Price(paramString.getDouble(paramString.getColumnIndex("Purchase_Price")));
      localPurchaseGoods.setOnhand_Quantity(paramString.getDouble(paramString.getColumnIndex("Onhand_Quantity")));
      localPurchaseGoods.setQty_Order(paramString.getDouble(paramString.getColumnIndex("Qty_Order")));
      localArrayList.add(localPurchaseGoods);
    }
  }
  
  public ArrayList<PurchaseGoods> getPurchaseGoodsByPG_ID(long paramLong, int paramInt1, int paramInt2)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject2 = getDatabase();
    Object localObject1;
    if (paramLong == 0L)
    {
      localObject1 = "SELECT b.*FROM (SELECT  psos.Product_ID, psos.ProductSku_ID, psos.ProductSku_Name, psos.ProductSku_Code, psos.ProductSku_BarCode, psos.ProductSku_Spec, psos.PruductSku_Description, psos.ProductSku_Image, psos.ProductSku_Unit, psos.PG_ID, psos.PG_Name, psos.Retail_Price, psos.Fixed_Cost, psos.WholeSale_Price, psos.Purchase_Price, psos.Onhand_Quantity,  psos.Qty_Order,  psos.Qty_Retail,  psos.Qty_Wholesale  FROM V_ProductSkuOnSale AS psos  WHERE psos.Store_ID = <门店ID>  AND psos.ProductSku_Status = 1  AND psos.PG_ID = <分类ID>  AND psos.PSS_Status IN (1,2)  UNION SELECT  ps.Product_ID, ps.ProductSku_ID, ps.ProductSku_Name, ps.ProductSku_Code, ps.ProductSku_BarCode, ps.ProductSku_Spec, ps.PruductSku_Description, ps.ProductSku_Image, ps.ProductSku_Unit, ps.PG_ID, ps.PG_Name, ps.Retail_Price, ps.Fixed_Cost, ps.WholeSale_Price, ps.Purchase_Price, 0  as 'Onhand_Quantity',  0  as 'Qty_Order',  0  as 'Qty_Retail',  0  as 'Qty_Wholesale'  FROM V_ProductSku AS ps  WHERE   ps.ProductSku_Status = 1  and ps.ProductSku_ID  NOT IN (SELECT a.ProductSku_ID   FROM V_ProductSkuOnSale AS a  WHERE a.Store_ID = <门店ID> ))  AS b  WHERE b.PG_ID = <分类ID> ORDER BY b.Qty_Order DESC limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>".replace("<门店ID>", String.valueOf(UserManager.getInstance().getCurrentStore().getStore_ID())).replace(" AND psos.PG_ID = <分类ID> ", "").replace(" WHERE b.PG_ID = <分类ID>", "").replace("<PAGE_SIZE>", String.valueOf(paramInt1)).replace("<INDEX>", String.valueOf(paramInt2));
      LogUtils.i("sql=" + (String)localObject1);
      localObject1 = ((SQLiteDatabase)localObject2).rawQuery((String)localObject1, null);
    }
    for (;;)
    {
      if (!((Cursor)localObject1).moveToNext())
      {
        LogUtils.d(localArrayList.toString());
        return localArrayList;
        localObject1 = "SELECT b.*FROM (SELECT  psos.Product_ID, psos.ProductSku_ID, psos.ProductSku_Name, psos.ProductSku_Code, psos.ProductSku_BarCode, psos.ProductSku_Spec, psos.PruductSku_Description, psos.ProductSku_Image, psos.ProductSku_Unit, psos.PG_ID, psos.PG_Name, psos.Retail_Price, psos.Fixed_Cost, psos.WholeSale_Price, psos.Purchase_Price, psos.Onhand_Quantity,  psos.Qty_Order,  psos.Qty_Retail,  psos.Qty_Wholesale  FROM V_ProductSkuOnSale AS psos  WHERE psos.Store_ID = <门店ID>  AND psos.ProductSku_Status = 1  AND psos.PG_ID = <分类ID>  AND psos.PSS_Status IN (1,2)  UNION SELECT  ps.Product_ID, ps.ProductSku_ID, ps.ProductSku_Name, ps.ProductSku_Code, ps.ProductSku_BarCode, ps.ProductSku_Spec, ps.PruductSku_Description, ps.ProductSku_Image, ps.ProductSku_Unit, ps.PG_ID, ps.PG_Name, ps.Retail_Price, ps.Fixed_Cost, ps.WholeSale_Price, ps.Purchase_Price, 0  as 'Onhand_Quantity',  0  as 'Qty_Order',  0  as 'Qty_Retail',  0  as 'Qty_Wholesale'  FROM V_ProductSku AS ps  WHERE   ps.ProductSku_Status = 1  and ps.ProductSku_ID  NOT IN (SELECT a.ProductSku_ID   FROM V_ProductSkuOnSale AS a  WHERE a.Store_ID = <门店ID> ))  AS b  WHERE b.PG_ID = <分类ID> ORDER BY b.Qty_Order DESC limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>".replace("<门店ID>", String.valueOf(UserManager.getInstance().getCurrentStore().getStore_ID())).replace("<分类ID>", String.valueOf(paramLong)).replace("<PAGE_SIZE>", String.valueOf(paramInt1)).replace("<INDEX>", String.valueOf(paramInt2));
        break;
      }
      localObject2 = new PurchaseGoods();
      ((PurchaseGoods)localObject2).setProduct_ID(((Cursor)localObject1).getLong(((Cursor)localObject1).getColumnIndex("Product_ID")));
      ((PurchaseGoods)localObject2).setPG_ID(((Cursor)localObject1).getLong(((Cursor)localObject1).getColumnIndex("PG_ID")));
      ((PurchaseGoods)localObject2).setProductSku_ID(((Cursor)localObject1).getLong(((Cursor)localObject1).getColumnIndex("ProductSku_ID")));
      ((PurchaseGoods)localObject2).setProductSku_Name(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_Name")));
      ((PurchaseGoods)localObject2).setProductSku_BarCode(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_BarCode")));
      ((PurchaseGoods)localObject2).setProductSku_Code(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_Code")));
      ((PurchaseGoods)localObject2).setProductSku_Image(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_Image")));
      ((PurchaseGoods)localObject2).setProductSku_ID(((Cursor)localObject1).getLong(((Cursor)localObject1).getColumnIndex("ProductSku_ID")));
      ((PurchaseGoods)localObject2).setProductSku_Spec(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_Spec")));
      ((PurchaseGoods)localObject2).setProductSku_Unit(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_Unit")));
      ((PurchaseGoods)localObject2).setRetail_Price(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("Retail_Price")));
      ((PurchaseGoods)localObject2).setFixed_Cost(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("Fixed_Cost")));
      ((PurchaseGoods)localObject2).setWholeSale_Price(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("WholeSale_Price")));
      ((PurchaseGoods)localObject2).setPurchase_Price(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("Purchase_Price")));
      ((PurchaseGoods)localObject2).setOnhand_Quantity(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("Onhand_Quantity")));
      ((PurchaseGoods)localObject2).setQty_Order(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("Qty_Order")));
      localArrayList.add(localObject2);
    }
  }
  
  public ArrayList<PurchaseGoods> getPurchaseGoodsInStorage(long paramLong, int paramInt1, int paramInt2)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject1 = getDatabase();
    Object localObject2 = "SELECT b.*FROM (SELECT  psos.Product_ID, psos.ProductSku_ID, psos.ProductSku_Name, psos.ProductSku_Code, psos.ProductSku_BarCode, psos.ProductSku_Spec, psos.PruductSku_Description, psos.ProductSku_Image, psos.ProductSku_Unit, psos.PG_ID, psos.PG_Name, psos.Retail_Price, psos.Fixed_Cost, psos.WholeSale_Price, psos.Purchase_Price, psos.Onhand_Quantity,  psos.Qty_Order,  psos.Qty_Retail,  psos.Qty_Wholesale  FROM V_ProductSkuOnSale AS psos  WHERE psos.Store_ID = <门店ID>  AND psos.ProductSku_Status = 1  AND psos.PSS_Status IN (1,2)  UNION SELECT  ps.Product_ID, ps.ProductSku_ID, ps.ProductSku_Name, ps.ProductSku_Code, ps.ProductSku_BarCode, ps.ProductSku_Spec, ps.PruductSku_Description, ps.ProductSku_Image, ps.ProductSku_Unit, ps.PG_ID, ps.PG_Name, ps.Retail_Price, ps.Fixed_Cost, ps.WholeSale_Price, ps.Purchase_Price, 0  as 'Onhand_Quantity',  0  as 'Qty_Order',  0  as 'Qty_Retail',  0  as 'Qty_Wholesale'  FROM V_ProductSku AS ps  WHERE 1 = 1  AND ps.ProductSku_Status = 1  and ps.ProductSku_ID NOT IN (SELECT a.ProductSku_ID   FROM V_ProductSkuOnSale AS a  WHERE    a.Store_ID = <门店ID> ))  AS b  ORDER BY b.Qty_Order DESC limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>".replace("<门店ID>", String.valueOf(UserManager.getInstance().getCurrentStore().getStore_ID())).replace("<PAGE_SIZE>", String.valueOf(paramInt1)).replace("<INDEX>", String.valueOf(paramInt2));
    LogUtils.i("sql=" + (String)localObject2);
    localObject1 = ((SQLiteDatabase)localObject1).rawQuery((String)localObject2, null);
    for (;;)
    {
      if (!((Cursor)localObject1).moveToNext())
      {
        LogUtils.d(localArrayList.toString());
        return localArrayList;
      }
      localObject2 = new PurchaseGoods();
      ((PurchaseGoods)localObject2).setProduct_ID(((Cursor)localObject1).getLong(((Cursor)localObject1).getColumnIndex("Product_ID")));
      ((PurchaseGoods)localObject2).setPG_ID(((Cursor)localObject1).getLong(((Cursor)localObject1).getColumnIndex("PG_ID")));
      ((PurchaseGoods)localObject2).setProductSku_ID(((Cursor)localObject1).getLong(((Cursor)localObject1).getColumnIndex("ProductSku_ID")));
      ((PurchaseGoods)localObject2).setProductSku_Name(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_Name")));
      ((PurchaseGoods)localObject2).setProductSku_BarCode(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_BarCode")));
      ((PurchaseGoods)localObject2).setProductSku_Code(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_Code")));
      ((PurchaseGoods)localObject2).setProductSku_Image(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_Image")));
      ((PurchaseGoods)localObject2).setProductSku_ID(((Cursor)localObject1).getLong(((Cursor)localObject1).getColumnIndex("ProductSku_ID")));
      ((PurchaseGoods)localObject2).setProductSku_Spec(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_Spec")));
      ((PurchaseGoods)localObject2).setProductSku_Unit(((Cursor)localObject1).getString(((Cursor)localObject1).getColumnIndex("ProductSku_Unit")));
      ((PurchaseGoods)localObject2).setRetail_Price(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("Retail_Price")));
      ((PurchaseGoods)localObject2).setFixed_Cost(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("Fixed_Cost")));
      ((PurchaseGoods)localObject2).setWholeSale_Price(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("WholeSale_Price")));
      ((PurchaseGoods)localObject2).setPurchase_Price(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("Purchase_Price")));
      ((PurchaseGoods)localObject2).setOnhand_Quantity(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("Onhand_Quantity")));
      ((PurchaseGoods)localObject2).setQty_Order(((Cursor)localObject1).getDouble(((Cursor)localObject1).getColumnIndex("Qty_Order")));
      localArrayList.add(localObject2);
    }
  }
  
  public ArrayList<PurchaseGoods> getPurchaseGoodsList(long paramLong)
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = getDatabase().rawQuery("SELECT ps.ProductSku_Name,pd.ProductSku_ID,pd.PD_CostPrice,pd.PD_RetailPrice,ps.ProductSku_Image,pd.PD_Quantiy,pd.PD_PurchasePrice,pd.PD_Quantiy*pd.PD_PurchasePrice AS 'SubTotal' FROM PO_Detail AS pd INNER JOIN  ProductSku AS ps ON ps.ProductSku_ID = pd.ProductSku_ID WHERE pd.PO_ID = <采购单ID> ORDER BY ps.ProductSku_Name".replace("<采购单ID>", String.valueOf(paramLong)), null);
    for (;;)
    {
      if (!localCursor.moveToNext()) {
        return localArrayList;
      }
      PurchaseGoods localPurchaseGoods = new PurchaseGoods();
      localPurchaseGoods.setProductSku_Name(localCursor.getString(localCursor.getColumnIndex("ProductSku_Name")));
      localPurchaseGoods.setProductSku_Image(localCursor.getString(localCursor.getColumnIndex("ProductSku_Image")));
      localPurchaseGoods.setPurchase_Price(localCursor.getDouble(localCursor.getColumnIndex("PD_PurchasePrice")));
      localPurchaseGoods.setFixed_Cost(localCursor.getDouble(localCursor.getColumnIndex("PD_CostPrice")));
      localPurchaseGoods.setProductSku_ID(localCursor.getLong(localCursor.getColumnIndex("ProductSku_ID")));
      localPurchaseGoods.setRetail_Price(localCursor.getLong(localCursor.getColumnIndex("PD_RetailPrice")));
      localPurchaseGoods.setOnhand_Quantity(localCursor.getDouble(localCursor.getColumnIndex("PD_Quantiy")));
      localPurchaseGoods.setSubTotal(localCursor.getDouble(localCursor.getColumnIndex("SubTotal")));
      localArrayList.add(localPurchaseGoods);
    }
  }
  
  public boolean updateBillStatus(long paramLong)
  {
    SQLiteDatabase localSQLiteDatabase = getDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      localSQLiteDatabase.execSQL("UPDATE PO_Header SET  Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1 ELSE 2 END WHERE PO_ID = ?", new Object[] { Long.valueOf(paramLong) });
      localSQLiteDatabase.execSQL("UPDATE PO_Header SET PO_Status = 5,Modify_DT = ?,Modify_ID = ?,Sync_DataType = 2,Sync_Status = 1 WHERE PO_ID = ?", new Object[] { DateTimeUtil.getCurrentTime(), Integer.valueOf(UserManager.getInstance().getCurrentUser().getUser_ID()), Long.valueOf(paramLong) });
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      return true;
    }
    catch (Exception localException)
    {
      localException = localException;
      localSQLiteDatabase.endTransaction();
      return false;
    }
    finally
    {
      localObject = finally;
      localSQLiteDatabase.endTransaction();
      throw ((Throwable)localObject);
    }
  }
}