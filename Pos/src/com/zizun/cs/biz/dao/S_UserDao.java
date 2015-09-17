package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.api.LocalLoginParam;

public abstract interface S_UserDao
  extends BaseDao
{
  public static final String LOCAL_LOGIN_SELECT = "SELECT count(*) FROM S_User WHERE 1=1 AND User_Name = ? AND User_Password = ? ";
  public static final String S_USER_DELETE = "Update S_User SET INDUSTRY_NAME = ?, INDUSTRY_DESCRIPTION = ?, INDUSTRY_STATUS = ? , Create_DT = ?, Modify_DT = ?, Create_ID = ?, Modify_ID = ?, Sync_DT = ?, Sync_DataType = ?, Sync_DataStatus = ?, Sync_Status = ? WHERE INDUSTRY_ID = ? ";
  public static final String S_USER_INSERT = "INSERT INTO S_User (User_ID, Merchant_ID, User_Name, User_Password, User_Mobile, User_Email, User_Status, Create_DT, Modify_DT, Create_ID, Modify_ID, Sync_DT, Sync_DataType, Sync_DataStatus, Sync_Status ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  public static final String S_USER_UPDATE = "Update S_User SET Industry_Name = ?, Industry_Description = ?, Industry_Status = ? , Create_DT = ?, Modify_DT = ?, Create_ID = ?, Modify_ID = ?, Sync_DT = ?, Sync_DataType = ?, Sync_DataStatus = ?, Sync_Status = ? WHERE Industry_ID = ? ";
  
  public abstract boolean delete(S_User paramS_User);
  
  public abstract boolean insert(S_User paramS_User);
  
  public abstract S_User localLogin(LocalLoginParam paramLocalLoginParam);
  
  public abstract boolean update(S_User paramS_User);
}