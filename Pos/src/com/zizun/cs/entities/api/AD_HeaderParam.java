package com.zizun.cs.entities.api;

import java.io.Serializable;

public class AD_HeaderParam
  implements Serializable
{
  private static final long serialVersionUID = 8327903431602049711L;
  public String AH_BackURL;
  public String AH_Browser_BackURL;
  public String AH_Code;
  public String AH_Desc;
  public String AH_EndDate;
  public int AH_ID;
  public String AH_Image;
  public int AH_IsOpenWX;
  public int AH_IsParameter;
  public int AH_IsShow;
  public String AH_OtherUrl;
  public String AH_ShowTime;
  public String AH_StartDate;
  public String AH_Thumbnail;
  public String AH_Title;
  public String AH_URL;
  public String AH_VersionNumber;
  
  public AD_HeaderParam(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt2, int paramInt3, String paramString7, int paramInt4, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13)
  {
    this.AH_ID = paramInt1;
    this.AH_Code = paramString1;
    this.AH_URL = paramString2;
    this.AH_OtherUrl = paramString3;
    this.AH_StartDate = paramString4;
    this.AH_EndDate = paramString5;
    this.AH_Image = paramString6;
    this.AH_IsShow = paramInt2;
    this.AH_IsOpenWX = paramInt3;
    this.AH_ShowTime = paramString7;
    this.AH_IsParameter = paramInt4;
    this.AH_BackURL = paramString8;
    this.AH_Thumbnail = paramString9;
    this.AH_Title = paramString10;
    this.AH_Desc = paramString11;
    this.AH_Browser_BackURL = paramString12;
    this.AH_VersionNumber = paramString13;
  }
}