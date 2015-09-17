package com.zizun.cs.biz.dao;

import com.zizun.cs.ui.entity.AccountsTotal;

public abstract interface AccountsDao
{
  public abstract AccountsTotal getTotalAccounts();
}