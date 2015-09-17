package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.biz.V_Parameter;
import java.util.List;

public abstract interface StoreStaffParameterDao
{
  public abstract List<V_Parameter> getAllParameter();
}