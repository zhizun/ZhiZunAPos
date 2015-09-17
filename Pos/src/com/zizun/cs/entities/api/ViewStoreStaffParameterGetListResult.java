package com.zizun.cs.entities.api;

import java.sql.Timestamp;
import java.util.List;

public class ViewStoreStaffParameterGetListResult
  extends BaseResult
{
  private static final long serialVersionUID = 2869630241110006302L;
  public List<ViewStoreStaffParameter> Data;
  
  public static class ViewStoreStaffParameter
  {
    private Timestamp Create_DT;
    private int Create_ID;
    private String Default_Value;
    private int Merchant_ID;
    private Timestamp Modify_DT;
    private int Modify_ID;
    private String Module_Code;
    private int Module_ID;
    private String Parameter_Code;
    private String Parameter_Desccription;
    private int Parameter_ID;
    private String Parameter_Name;
    private String Parameter_Value;
    private long Role_ID;
    private long SSP_ID;
    private int Staff_ID;
    private long Store_ID;
    private Timestamp Sync_DT;
    private int Sync_DataStatus;
    private int Sync_DataType;
    private int Sync_Status;
    
    public Timestamp getCreate_DT()
    {
      return this.Create_DT;
    }
    
    public int getCreate_ID()
    {
      return this.Create_ID;
    }
    
    public String getDefault_Value()
    {
      return this.Default_Value;
    }
    
    public int getMerchant_ID()
    {
      return this.Merchant_ID;
    }
    
    public Timestamp getModify_DT()
    {
      return this.Modify_DT;
    }
    
    public int getModify_ID()
    {
      return this.Modify_ID;
    }
    
    public String getModule_Code()
    {
      return this.Module_Code;
    }
    
    public int getModule_ID()
    {
      return this.Module_ID;
    }
    
    public String getParameter_Code()
    {
      return this.Parameter_Code;
    }
    
    public String getParameter_Desccription()
    {
      return this.Parameter_Desccription;
    }
    
    public int getParameter_ID()
    {
      return this.Parameter_ID;
    }
    
    public String getParameter_Name()
    {
      return this.Parameter_Name;
    }
    
    public String getParameter_Value()
    {
      return this.Parameter_Value;
    }
    
    public long getRole_ID()
    {
      return this.Role_ID;
    }
    
    public long getSSP_ID()
    {
      return this.SSP_ID;
    }
    
    public int getStaff_ID()
    {
      return this.Staff_ID;
    }
    
    public long getStore_ID()
    {
      return this.Store_ID;
    }
    
    public Timestamp getSync_DT()
    {
      return this.Sync_DT;
    }
    
    public int getSync_DataStatus()
    {
      return this.Sync_DataStatus;
    }
    
    public int getSync_DataType()
    {
      return this.Sync_DataType;
    }
    
    public int getSync_Status()
    {
      return this.Sync_Status;
    }
    
    public void setCreate_DT(Timestamp paramTimestamp)
    {
      this.Create_DT = paramTimestamp;
    }
    
    public void setCreate_ID(int paramInt)
    {
      this.Create_ID = paramInt;
    }
    
    public void setDefault_Value(String paramString)
    {
      this.Default_Value = paramString;
    }
    
    public void setMerchant_ID(int paramInt)
    {
      this.Merchant_ID = paramInt;
    }
    
    public void setModify_DT(Timestamp paramTimestamp)
    {
      this.Modify_DT = paramTimestamp;
    }
    
    public void setModify_ID(int paramInt)
    {
      this.Modify_ID = paramInt;
    }
    
    public void setModule_Code(String paramString)
    {
      this.Module_Code = paramString;
    }
    
    public void setModule_ID(int paramInt)
    {
      this.Module_ID = paramInt;
    }
    
    public void setParameter_Code(String paramString)
    {
      this.Parameter_Code = paramString;
    }
    
    public void setParameter_Desccription(String paramString)
    {
      this.Parameter_Desccription = paramString;
    }
    
    public void setParameter_ID(int paramInt)
    {
      this.Parameter_ID = paramInt;
    }
    
    public void setParameter_Name(String paramString)
    {
      this.Parameter_Name = paramString;
    }
    
    public void setParameter_Value(String paramString)
    {
      this.Parameter_Value = paramString;
    }
    
    public void setRole_ID(long paramLong)
    {
      this.Role_ID = paramLong;
    }
    
    public void setSSP_ID(long paramLong)
    {
      this.SSP_ID = paramLong;
    }
    
    public void setStaff_ID(int paramInt)
    {
      this.Staff_ID = paramInt;
    }
    
    public void setStore_ID(long paramLong)
    {
      this.Store_ID = paramLong;
    }
    
    public void setSync_DT(Timestamp paramTimestamp)
    {
      this.Sync_DT = paramTimestamp;
    }
    
    public void setSync_DataStatus(int paramInt)
    {
      this.Sync_DataStatus = paramInt;
    }
    
    public void setSync_DataType(int paramInt)
    {
      this.Sync_DataType = paramInt;
    }
    
    public void setSync_Status(int paramInt)
    {
      this.Sync_Status = paramInt;
    }
  }
}