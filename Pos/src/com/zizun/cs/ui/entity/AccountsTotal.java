package com.zizun.cs.ui.entity;

public class AccountsTotal
{
  private Accounts CurrentMonthAccounts;
  private Accounts CurrentWeekAccounts;
  private Accounts LastMonthAccounts;
  private Accounts LastWeekAccounts;
  private Accounts todayAccounts;
  private Accounts yesterdayAccounts;
  
  public Accounts getCurrentMonthAccounts()
  {
    return this.CurrentMonthAccounts;
  }
  
  public Accounts getCurrentWeekAccounts()
  {
    return this.CurrentWeekAccounts;
  }
  
  public Accounts getLastMonthAccounts()
  {
    return this.LastMonthAccounts;
  }
  
  public Accounts getLastWeekAccounts()
  {
    return this.LastWeekAccounts;
  }
  
  public Accounts getTodayAccounts()
  {
    return this.todayAccounts;
  }
  
  public Accounts getYesterdayAccounts()
  {
    return this.yesterdayAccounts;
  }
  
  public void setCurrentMonthAccounts(Accounts paramAccounts)
  {
    this.CurrentMonthAccounts = paramAccounts;
  }
  
  public void setCurrentWeekAccounts(Accounts paramAccounts)
  {
    this.CurrentWeekAccounts = paramAccounts;
  }
  
  public void setLastMonthAccounts(Accounts paramAccounts)
  {
    this.LastMonthAccounts = paramAccounts;
  }
  
  public void setLastWeekAccounts(Accounts paramAccounts)
  {
    this.LastWeekAccounts = paramAccounts;
  }
  
  public void setTodayAccounts(Accounts paramAccounts)
  {
    this.todayAccounts = paramAccounts;
  }
  
  public void setYesterdayAccounts(Accounts paramAccounts)
  {
    this.yesterdayAccounts = paramAccounts;
  }
}