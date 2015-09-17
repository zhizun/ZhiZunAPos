package com.zizun.cs.ui.entity;

import com.zizun.cs.common.utils.ResUtil;
import java.io.Serializable;

public class GoodsStatus
  implements Serializable
{
  private static final long serialVersionUID = 1834188825422789203L;
  private final byte STATUE_DISABLED = 3;
  private final byte STATUE_SHELVES = 1;
  private final byte STATUE_UNSHELVES = 2;
  private byte id;
  private String name;
  
  public byte getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setId(byte paramByte)
  {
    this.id = paramByte;
    if (paramByte == 1)
    {
      this.name = ResUtil.getString(ResUtil.getStringId("shelves"));
      return;
    }
    if (paramByte == 2)
    {
      this.name = ResUtil.getString(ResUtil.getStringId("unshelves"));
      return;
    }
    this.name = ResUtil.getString(ResUtil.getStringId("permanent_shelves"));
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
}