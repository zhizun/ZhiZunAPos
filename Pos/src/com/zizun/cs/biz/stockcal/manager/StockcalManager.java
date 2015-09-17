package com.zizun.cs.biz.stockcal.manager;

import com.zizun.cs.biz.dao.trans.impl.BaseTransaction;
import com.zizun.cs.biz.dao.trans.impl.StockTransImpl;
import com.zizun.cs.biz.stockcal.dao.impl.StockcalDaoImpl;
import com.zizun.cs.entities.Stock_Daily;
import com.zizun.cs.entities.Stock_Now;
import java.util.List;

public class StockcalManager
  extends BaseTransaction
{
  private static StockcalManager mInstance;
  
  public static StockcalManager getInstance()
  {
    if (mInstance == null) {
      mInstance = new StockcalManager();
    }
    return mInstance;
  }
  
  public void calStock()
  {
    new StockTransImpl().calStock();
  }
  
  public Stock_Daily getStock_Daily(long paramLong1, long paramLong2, int paramInt)
  {
    calStock();
    return (Stock_Daily)new StockcalDaoImpl().getStock_DailyList(paramLong1, paramLong2, paramInt).get(0);
  }
  
  public Stock_Now getStock_Now(long paramLong1, long paramLong2)
  {
    calStock();
    return (Stock_Now)new StockcalDaoImpl().getStock_NowList(paramLong1, paramLong2).get(0);
  }
}