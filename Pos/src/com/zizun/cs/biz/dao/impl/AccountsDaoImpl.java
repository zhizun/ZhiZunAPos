package com.zizun.cs.biz.dao.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.AccountsDao;
import com.zizun.cs.common.utils.LogUtil;
import com.zizun.cs.entities.Store;
import com.zizun.cs.ui.entity.Accounts;
import com.zizun.cs.ui.entity.AccountsTotal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AccountsDaoImpl
  extends BaseDaoImpl
  implements AccountsDao
{
  private static final String SQL_SELECT_ACCOUNTS = "SELECT sum(sd.Amt_Retail + sd.Amt_Wholesale) AS Amt_Sale, SUM(sd.Cost_Retail + sd.Cost_Wholesale)  AS Cost_Sale, SUM(sd.Amt_Retail+sd.Amt_Wholesale-sd.Cost_Retail-sd.Cost_Wholesale) AS Profit_Sale,SUM(sd.Amt_RetailReturn + sd.Amt_WholesaleReturn) AS Amt_SaleReturn, SUM(sd.Amt_Order) AS Amt_Order FROM Stock_Daily AS sd WHERE sd.Store_ID = ?";
  private static final String SQL_SELECT_DEBT = "SELECT SUM(sd.RemainAmount) AS Debt FROM V_HeaderForVIPSettle AS sd WHERE sd.Store_ID = ?";
  private static String currentMonthCondition;
  private static String currentWeekCondition;
  private static String debtCurrentMonthCondition;
  private static String debtCurrentWeekCondition;
  private static String debtLastMonthCondtion;
  private static String debtLastWeekCondition;
  private static String debtTodayCondition;
  private static String debtYesterdayCondition;
  private static AccountsDaoImpl instance;
  private static String lastMonthCondtion;
  private static String lastWeekCondition;
  private static String todayCondition;
  private static String yesterdayCondition;
  
  public static void clear()
  {
    if (instance != null) {
      instance = null;
    }
  }
  
  public static AccountsDaoImpl getInstance(String paramString)
  {
    if (instance == null) {
      instance = new AccountsDaoImpl();
    }
    initTime(paramString);
    return instance;
  }
  
  private static void initTime(String paramString)
  {
    if ((paramString == null) && (paramString.equals(""))) {
      return;
    }
    try
    {
      Object localObject2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
      Object localObject1 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
      localObject2 = ((SimpleDateFormat)localObject2).parse(paramString);
      paramString = Calendar.getInstance();
      paramString.setTime((Date)localObject2);
      String str1 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtYesterdayCondition = " AND sd.TransDate >= '" + new Timestamp(paramString.getTimeInMillis());
      paramString.add(5, -1);
      String str2 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtYesterdayCondition = debtYesterdayCondition + "' AND sd.TransDate < '" + new Timestamp(paramString.getTimeInMillis()) + "'";
      paramString.add(5, 1);
      yesterdayCondition = " AND sd.TransDate >= " + str2 + " AND sd.TransDate < " + str1;
      str1 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtTodayCondition = " AND sd.TransDate >= '" + new Timestamp(paramString.getTimeInMillis());
      paramString.add(5, 1);
      str2 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtTodayCondition = debtTodayCondition + "' AND sd.TransDate < '" + new Timestamp(paramString.getTimeInMillis()) + "'";
      todayCondition = " AND sd.TransDate >= " + str1 + " AND sd.TransDate < " + str2;
      paramString.set(5, 1);
      str1 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtLastMonthCondtion = " AND sd.TransDate >= '" + new Timestamp(paramString.getTimeInMillis());
      paramString.add(2, -1);
      str2 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtLastMonthCondtion = debtLastMonthCondtion + "' AND sd.TransDate < '" + new Timestamp(paramString.getTimeInMillis()) + "'";
      lastMonthCondtion = " AND sd.TransDate >= " + str2 + " AND sd.TransDate < " + str1;
      paramString.add(2, 1);
      str1 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtCurrentMonthCondition = " AND sd.TransDate >= '" + new Timestamp(paramString.getTimeInMillis());
      paramString.add(2, 1);
      str2 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtCurrentMonthCondition = debtCurrentMonthCondition + "' AND sd.TransDate < '" + new Timestamp(paramString.getTimeInMillis()) + "'";
      currentMonthCondition = " AND sd.TransDate >= " + str1 + " AND sd.TransDate < " + str2;
      paramString.add(2, -1);
      paramString.setTime((Date)localObject2);
      paramString.setFirstDayOfWeek(2);
      paramString.set(7, paramString.getFirstDayOfWeek());
      localObject2 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtCurrentWeekCondition = " AND sd.TransDate >= '" + new Timestamp(paramString.getTimeInMillis());
      paramString.add(5, 7);
      str1 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtCurrentWeekCondition = debtCurrentWeekCondition + "' AND sd.TransDate < '" + new Timestamp(paramString.getTimeInMillis()) + "'";
      currentWeekCondition = " AND sd.TransDate >= " + (String)localObject2 + " AND sd.TransDate < " + str1;
      paramString.add(5, -7);
      localObject2 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtLastWeekCondition = " AND sd.TransDate >= '" + new Timestamp(paramString.getTimeInMillis());
      paramString.add(5, -7);
      localObject1 = ((SimpleDateFormat)localObject1).format(paramString.getTime());
      debtLastWeekCondition = debtLastWeekCondition + "' AND sd.TransDate < '" + new Timestamp(paramString.getTimeInMillis()) + "'";
      lastWeekCondition = " AND sd.TransDate >= " + (String)localObject1 + " AND sd.TransDate < " + (String)localObject2;
      return;
    }
    catch (ParseException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public Accounts getAccounts(String paramString1, String paramString2)
  {
    String str = String.valueOf(UserManager.getInstance().getCurrentStore().getStore_ID());
    Accounts localAccounts = new Accounts();
    SQLiteDatabase localSQLiteDatabase = getDatabase();
    try
    {
      paramString1 = localSQLiteDatabase.rawQuery("SELECT sum(sd.Amt_Retail + sd.Amt_Wholesale) AS Amt_Sale, SUM(sd.Cost_Retail + sd.Cost_Wholesale)  AS Cost_Sale, SUM(sd.Amt_Retail+sd.Amt_Wholesale-sd.Cost_Retail-sd.Cost_Wholesale) AS Profit_Sale,SUM(sd.Amt_RetailReturn + sd.Amt_WholesaleReturn) AS Amt_SaleReturn, SUM(sd.Amt_Order) AS Amt_Order FROM Stock_Daily AS sd WHERE sd.Store_ID = ?" + paramString1, new String[] { str });
      paramString2 = localSQLiteDatabase.rawQuery("SELECT SUM(sd.RemainAmount) AS Debt FROM V_HeaderForVIPSettle AS sd WHERE sd.Store_ID = ?" + paramString2, new String[] { str });
      paramString1.moveToNext();
      paramString2.moveToNext();
      localAccounts.setAmt_Order(paramString1.getDouble(paramString1.getColumnIndex("Amt_Order")));
      localAccounts.setAmt_Sale(paramString1.getDouble(paramString1.getColumnIndex("Amt_Sale")));
      localAccounts.setAmt_SaleReturn(paramString1.getDouble(paramString1.getColumnIndex("Amt_SaleReturn")));
      localAccounts.setCost_Sale(paramString1.getDouble(paramString1.getColumnIndex("Cost_Sale")));
      localAccounts.setProfit_Sale(paramString1.getDouble(paramString1.getColumnIndex("Profit_Sale")));
      localAccounts.setDebt(paramString2.getDouble(paramString2.getColumnIndex("Debt")));
      return localAccounts;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return localAccounts;
  }
  
  public AccountsTotal getTotalAccounts()
  {
    AccountsTotal localAccountsTotal = new AccountsTotal();
    localAccountsTotal.setTodayAccounts(getAccounts(todayCondition, debtTodayCondition));
    localAccountsTotal.setYesterdayAccounts(getAccounts(yesterdayCondition, debtYesterdayCondition));
    localAccountsTotal.setCurrentWeekAccounts(getAccounts(currentWeekCondition, debtCurrentWeekCondition));
    localAccountsTotal.setLastWeekAccounts(getAccounts(lastWeekCondition, debtLastWeekCondition));
    localAccountsTotal.setCurrentMonthAccounts(getAccounts(currentMonthCondition, debtCurrentMonthCondition));
    localAccountsTotal.setLastMonthAccounts(getAccounts(lastMonthCondtion, debtLastMonthCondtion));
    LogUtil.logD("昨天" + yesterdayCondition);
    LogUtil.logD("今天" + todayCondition);
    LogUtil.logD("上周" + lastWeekCondition);
    LogUtil.logD("本周" + currentWeekCondition);
    LogUtil.logD("上月" + lastMonthCondtion);
    LogUtil.logD("本月" + currentMonthCondition);
    return localAccountsTotal;
  }
}