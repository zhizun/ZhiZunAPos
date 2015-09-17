package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.VIP;
import java.util.ArrayList;
import java.util.List;

public abstract interface VIPDao
  extends BaseDao
{
  public static final String VIP_DELETE = "DELETE FROM VIP WHERE VIP_ID=?";
  public static final String VIP_INSERT = "INSERT INTO VIP(Merchant_ID,VIP_ID,VIP_Type,VIPGroup_ID,VIP_Code,VIP_Name,VIP_Phone,VIP_Contact,VIP_Mobile,VIP_FAX,VIP_Email,VIP_Zip,VIP_Address,VIP_WX,VIP_QQ,VIP_Remark,IS_MerchantVIP,Supplier_ID,VIP_Status)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  public static final String VIP_SELECT = "SELECT * FROM VIP WHERE VIP_STATUS=1 ORDER BY VIP_ID DESC";
  
  public abstract List<VIP> getAllVips(int paramInt);
  
  public abstract ArrayList<VIP> getFuzzyVIP(String paramString);
  
  public abstract List<VIP> getListVips();
  
  public abstract VIP getSingleVip(int paramInt1, int paramInt2);
  
  public abstract VIP getVIPByID(long paramLong);
  
  public abstract VIP getVipName(String paramString);
  
  public abstract VIP getVipNameCancle(String paramString);
  
  public abstract boolean insert(VIP paramVIP);
  
  public abstract boolean insertList(List<VIP> paramList);
  
  public abstract boolean update(VIP paramVIP);
}