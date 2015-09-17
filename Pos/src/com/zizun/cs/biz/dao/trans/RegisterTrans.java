package com.zizun.cs.biz.dao.trans;

import com.zizun.cs.entities.api.RegisterResult.RegisterResultData;

public abstract interface RegisterTrans
{
  public abstract boolean doRegisterTransaction(RegisterResult.RegisterResultData paramRegisterResultData);
}