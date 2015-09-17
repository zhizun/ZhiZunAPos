package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.Store;
import java.util.List;

public abstract interface StoreDao
  extends BaseDao
{
  public static final String SELECT_STORE_BY_USER_ID = "SELECT s.*  FROM StoreStaff AS ss INNER JOIN store AS s ON s.Merchant_ID = ss.Merchant_ID AND  s.Store_ID = ss.Store_ID INNER JOIN staff AS s1 ON s1.Merchant_ID = ss.Merchant_ID AND s1.Staff_ID = ss.Staff_ID\t WHERE ss.Staff_ID = <用户ID> AND ss.StoreStaff_Status = 1 AND s1.Staff_Status = 1 AND s.Store_Status =1\t";
  public static final String STORE_DELETE = "Update Store SET Store_Code = ?, Store_Name = ?, Store_Address = ?, Store_Phone = ?, Store_Email = ?, Store_Zip = ?, Store_Remark = ?, Store_Status = ?,, Create_DT = ?, Modify_DT = ?, Create_ID = ?, Modify_ID = ?, Sync_DT = ?, Sync_DataType = ?, Sync_DataStatus = ?, Sync_Status = ? WHERE 1=1 AND Merchant_ID = ? AND Store_ID = ? ";
  public static final String STORE_INSERT = "INSERT INTO Store (Merchant_ID, Store_ID, Store_Code, Store_Name, Store_Address, Store_Phone, Store_Email, Store_Zip, Store_Remark, Store_Status, Create_DT, Modify_DT, Create_ID, Modify_ID, Sync_DT, Sync_DataType, Sync_DataStatus, Sync_Status ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  public static final String STORE_UPDATE = "Update Store SET Store_Code = ?, Store_Name = ?, Store_Address = ?, Store_Phone = ?, Store_Email = ?, Store_Zip = ?, Store_Remark = ?, Store_Status = ?,, Create_DT = ?, Modify_DT = ?, Create_ID = ?, Modify_ID = ?, Sync_DT = ?, Sync_DataType = ?, Sync_DataStatus = ?, Sync_Status = ? WHERE 1=1 AND Merchant_ID = ? AND Store_ID = ? ";
  
  public abstract boolean delete(Store paramStore);
  
  public abstract List<Store> getAllStores(int paramInt);
  
  public abstract boolean insert(Store paramStore);
  
  public abstract boolean update(Store paramStore);
}