package com.zizun.cs.biz.dao.trans;

import com.zizun.cs.entities.api.LoginResult.LoginResultData;

public abstract interface LoginTrans
{
  public abstract boolean doLoginTransaction(LoginResult.LoginResultData paramLoginResultData);
}