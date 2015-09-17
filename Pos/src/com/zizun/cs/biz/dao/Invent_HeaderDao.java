package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.Invent_Header;
import java.util.List;

public abstract interface Invent_HeaderDao
  extends BaseDao
{
  public static final String INVENT_HEADERLIST_SELECT = "SELECT * FROM Invent_Header";
  public static final String INVENT_HEADER_SELECT = "SELECT * FROM Invent_Header where Invent_ID=<?>";
  
  public abstract List<Invent_Header> GetListInventHeaders();
  
  public abstract boolean Insert(Invent_Header paramInvent_Header);
  
  public abstract boolean InsertList(List<Invent_Header> paramList);
}