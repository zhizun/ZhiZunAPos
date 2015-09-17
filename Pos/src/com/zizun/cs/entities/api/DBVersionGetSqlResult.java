package com.zizun.cs.entities.api;

import java.sql.Timestamp;
import java.util.List;

public class DBVersionGetSqlResult
  extends BaseResult
{
  private static final long serialVersionUID = -3768401342642242588L;
  public getDBVersionSqlResultData Data;
  
  public static class Reg_DBVersion
  {
    private int IsActive;
    private Timestamp ReleaseDate;
    private String ReleaseNote;
    private String SqlScript;
    private int VersionNumber;
    
    public int getIsActive()
    {
      return this.IsActive;
    }
    
    public Timestamp getReleaseDate()
    {
      return this.ReleaseDate;
    }
    
    public String getReleaseNote()
    {
      return this.ReleaseNote;
    }
    
    public String getSqlScript()
    {
      return this.SqlScript;
    }
    
    public int getVersionNumber()
    {
      return this.VersionNumber;
    }
    
    public void setIsActive(int paramInt)
    {
      this.IsActive = paramInt;
    }
    
    public void setReleaseDate(Timestamp paramTimestamp)
    {
      this.ReleaseDate = paramTimestamp;
    }
    
    public void setReleaseNote(String paramString)
    {
      this.ReleaseNote = paramString;
    }
    
    public void setSqlScript(String paramString)
    {
      this.SqlScript = paramString;
    }
    
    public void setVersionNumber(int paramInt)
    {
      this.VersionNumber = paramInt;
    }
  }
  
  public static class getDBVersionSqlResultData
  {
    private List<DBVersionGetSqlResult.Reg_DBVersion> SqlData;
    private int Ver;
    
    public List<DBVersionGetSqlResult.Reg_DBVersion> getSqlData()
    {
      return this.SqlData;
    }
    
    public int getVer()
    {
      return this.Ver;
    }
    
    public void setSqlData(List<DBVersionGetSqlResult.Reg_DBVersion> paramList)
    {
      this.SqlData = paramList;
    }
    
    public void setVer(int paramInt)
    {
      this.Ver = paramInt;
    }
  }
}