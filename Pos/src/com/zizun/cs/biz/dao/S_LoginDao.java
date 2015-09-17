package com.zizun.cs.biz.dao;

import com.zizun.cs.entities.S_Login;

public abstract interface S_LoginDao
  extends BaseDao
{
  public abstract boolean insert(S_Login paramS_Login);
}