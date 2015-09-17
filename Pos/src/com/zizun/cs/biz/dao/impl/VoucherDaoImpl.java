package com.zizun.cs.biz.dao.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.VoucherDao;
import com.zizun.cs.common.utils.JsonUtil;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.biz.V_HeaderForPay;
import com.zizun.cs.entities.biz.V_VoucherDetail;
import com.zizun.cs.entities.biz.V_VoucherHeader;
import java.util.ArrayList;

public class VoucherDaoImpl
  extends BaseDaoImpl
  implements VoucherDao
{
  private static final String SQL_SELECT_ALL_OWEPAY = "SELECT * FROM V_HeaderForPay AS hfp where Store_ID = ?";
  private static final String SQL_SELECT_ALL_OWERECEIVE = "SELECT * FROM V_HeaderForReceive AS hfr  where Store_ID = ?";
  private static final String SQL_SELECT_ALL_VOUCHER = "SELECT * FROM V_VoucherHeader AS vh WHERE vh.VH_PR = ? AND vh.VH_StoreID = ? And vh.VD_TransType in (1,2,3,4,5,6) ORDER BY  VH.VH_ID DESC ";
  private static final String SQL_SELECT_ALL_VOUCHER_DETAIL = "SELECT * FROM V_VoucherDetail AS vd WHERE vd.VH_ID = ?";
  
  private ArrayList<V_VoucherHeader> getAllVoucher(String paramString)
  {
    localArrayList = new ArrayList();
    try
    {
      paramString = getDatabase().rawQuery("SELECT * FROM V_VoucherHeader AS vh WHERE vh.VH_PR = ? AND vh.VH_StoreID = ? And vh.VD_TransType in (1,2,3,4,5,6) ORDER BY  VH.VH_ID DESC ", new String[] { paramString, String.valueOf(UserManager.getInstance().getCurrentStore().getStore_ID()) });
      for (;;)
      {
        if (!paramString.moveToNext()) {
          return localArrayList;
        }
        V_VoucherHeader localV_VoucherHeader = new V_VoucherHeader();
        localV_VoucherHeader.setMerchant_ID(paramString.getInt(paramString.getColumnIndex("Merchant_ID")));
        localV_VoucherHeader.setVH_StoreID(paramString.getLong(paramString.getColumnIndex("VH_StoreID")));
        localV_VoucherHeader.setVH_Type((byte)paramString.getInt(paramString.getColumnIndex("VH_Type")));
        localV_VoucherHeader.setVH_PR((byte)paramString.getInt(paramString.getColumnIndex("VH_PR")));
        localV_VoucherHeader.setVH_ID(paramString.getLong(paramString.getColumnIndex("VH_ID")));
        localV_VoucherHeader.setVH_Number(paramString.getString(paramString.getColumnIndex("VH_Number")));
        localV_VoucherHeader.setVH_TransDate(paramString.getString(paramString.getColumnIndex("VH_TransDate")));
        localV_VoucherHeader.setVH_Status((byte)paramString.getInt(paramString.getColumnIndex("VH_Status")));
        localV_VoucherHeader.setSupplier_ID(paramString.getLong(paramString.getColumnIndex("Supplier_ID")));
        localV_VoucherHeader.setVIP_ID(paramString.getLong(paramString.getColumnIndex("VIP_ID")));
        localV_VoucherHeader.setVD_TransType((byte)paramString.getInt(paramString.getColumnIndex("VD_TransType")));
        localV_VoucherHeader.setRelatedName(paramString.getString(paramString.getColumnIndex("RelatedName")));
        localArrayList.add(localV_VoucherHeader);
        LogUtils.i(JsonUtil.toJson(localArrayList));
      }
      return localArrayList;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public ArrayList<V_HeaderForPay> getAllOwePay()
  {
    localArrayList = new ArrayList();
    try
    {
      long l = UserManager.getInstance().getCurrentStore().getStore_ID();
      Cursor localCursor = getDatabase().rawQuery("SELECT * FROM V_HeaderForPay AS hfp where Store_ID = ?", new String[] { String.valueOf(l) });
      for (;;)
      {
        if (!localCursor.moveToNext()) {
          return localArrayList;
        }
        V_HeaderForPay localV_HeaderForPay = new V_HeaderForPay();
        localV_HeaderForPay.setMerchant_ID(localCursor.getInt(localCursor.getColumnIndex("Merchant_ID")));
        localV_HeaderForPay.setRelatedName(localCursor.getString(localCursor.getColumnIndex("RelatedName")));
        localV_HeaderForPay.setRemainAmount(localCursor.getDouble(localCursor.getColumnIndex("RemainAmount")));
        localV_HeaderForPay.setPlanAmount(localCursor.getDouble(localCursor.getColumnIndex("PlanAmount")));
        localV_HeaderForPay.setActualAmount(localCursor.getDouble(localCursor.getColumnIndex("ActualAmount")));
        localV_HeaderForPay.setStore_ID(localCursor.getLong(localCursor.getColumnIndex("Store_ID")));
        localV_HeaderForPay.setSupplier_ID(localCursor.getLong(localCursor.getColumnIndex("Supplier_ID")));
        localV_HeaderForPay.setTransID(localCursor.getLong(localCursor.getColumnIndex("TransID")));
        localV_HeaderForPay.setTransNumber(localCursor.getString(localCursor.getColumnIndex("TransNumber")));
        localV_HeaderForPay.setTransType(localCursor.getString(localCursor.getColumnIndex("TransType")));
        localV_HeaderForPay.setVIP_ID(localCursor.getLong(localCursor.getColumnIndex("VIP_ID")));
        localArrayList.add(localV_HeaderForPay);
        LogUtils.i(JsonUtil.toJson(localArrayList));
      }
      return localArrayList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public ArrayList<V_HeaderForPay> getAllOweReceive()
  {
    localArrayList = new ArrayList();
    try
    {
      long l = UserManager.getInstance().getCurrentStore().getStore_ID();
      Cursor localCursor = getDatabase().rawQuery("SELECT * FROM V_HeaderForReceive AS hfr  where Store_ID = ?", new String[] { String.valueOf(l) });
      for (;;)
      {
        if (!localCursor.moveToNext()) {
          return localArrayList;
        }
        V_HeaderForPay localV_HeaderForPay = new V_HeaderForPay();
        localV_HeaderForPay.setMerchant_ID(localCursor.getInt(localCursor.getColumnIndex("Merchant_ID")));
        localV_HeaderForPay.setRelatedName(localCursor.getString(localCursor.getColumnIndex("RelatedName")));
        localV_HeaderForPay.setRemainAmount(localCursor.getDouble(localCursor.getColumnIndex("RemainAmount")));
        localV_HeaderForPay.setPlanAmount(localCursor.getDouble(localCursor.getColumnIndex("PlanAmount")));
        localV_HeaderForPay.setActualAmount(localCursor.getDouble(localCursor.getColumnIndex("ActualAmount")));
        localV_HeaderForPay.setStore_ID(localCursor.getLong(localCursor.getColumnIndex("Store_ID")));
        localV_HeaderForPay.setSupplier_ID(localCursor.getLong(localCursor.getColumnIndex("Supplier_ID")));
        localV_HeaderForPay.setTransID(localCursor.getLong(localCursor.getColumnIndex("TransID")));
        localV_HeaderForPay.setTransNumber(localCursor.getString(localCursor.getColumnIndex("TransNumber")));
        localV_HeaderForPay.setTransType(localCursor.getString(localCursor.getColumnIndex("TransType")));
        localV_HeaderForPay.setVIP_ID(localCursor.getLong(localCursor.getColumnIndex("VIP_ID")));
        localArrayList.add(localV_HeaderForPay);
        LogUtils.i(JsonUtil.toJson(localArrayList));
      }
      return localArrayList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public ArrayList<V_VoucherHeader> getAllPayVoucher()
  {
    return getAllVoucher("1");
  }
  
  public ArrayList<V_VoucherHeader> getAllReceiveVoucher()
  {
    return getAllVoucher("2");
  }
  
  public ArrayList<V_VoucherDetail> getAllVoucherDetail(long paramLong)
  {
    localArrayList = new ArrayList();
    try
    {
      Cursor localCursor = getDatabase().rawQuery("SELECT * FROM V_VoucherDetail AS vd WHERE vd.VH_ID = ?", new String[] { String.valueOf(paramLong) });
      for (;;)
      {
        if (!localCursor.moveToNext()) {
          return localArrayList;
        }
        V_VoucherDetail localV_VoucherDetail = new V_VoucherDetail();
        localV_VoucherDetail.setMerchant_ID(localCursor.getInt(localCursor.getColumnIndex("Merchant_ID")));
        localV_VoucherDetail.setVH_StoreID(localCursor.getLong(localCursor.getColumnIndex("VH_StoreID")));
        localV_VoucherDetail.setVH_ID(localCursor.getLong(localCursor.getColumnIndex("VH_ID")));
        localV_VoucherDetail.setTransNumber(localCursor.getString(localCursor.getColumnIndex("TransNumber")));
        localV_VoucherDetail.setTransType((byte)localCursor.getInt(localCursor.getColumnIndex("TransType")));
        localV_VoucherDetail.setVD_TransAmount(localCursor.getDouble(localCursor.getColumnIndex("VD_TransAmount")));
        localV_VoucherDetail.setVD_TransID(localCursor.getLong(localCursor.getColumnIndex("VD_TransID")));
        localArrayList.add(localV_VoucherDetail);
        LogUtils.i(JsonUtil.toJson(localArrayList));
      }
      return localArrayList;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}