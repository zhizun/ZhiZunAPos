package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.Invent_Detail;
import java.util.List;

public abstract interface Invent_DetailDao
  extends BaseDao
{
  public abstract List<Invent_Detail> GetAllInventDetails();
  
  public abstract boolean Insert(Invent_Detail paramInvent_Detail);
  
  public abstract boolean InsertList(List<Invent_Detail> paramList);
}