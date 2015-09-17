package com.zizun.cs.activity.manager;

import com.zizun.cs.biz.dao.Invent_DetailDao;
import com.zizun.cs.biz.dao.Invent_HeaderDao;
import com.zizun.cs.biz.dao.impl.Invent_DetailDaoImpl;
import com.zizun.cs.biz.dao.impl.Invent_HeaderDaoImpl;
import com.zizun.cs.biz.dao.impl.TransactionLogDaoImpl;
import com.zizun.cs.biz.dao.trans.impl.InventTransImpl;
import com.zizun.cs.entities.Invent_Detail;
import com.zizun.cs.entities.Invent_Header;
import com.zizun.cs.entities.TransactionLog;
import com.zizun.cs.ui.entity.CheckHistory;
import com.zizun.cs.ui.entity.Invent;
import java.util.List;

public class InventManager
{
  public static void InsertInventDetailOutUIThread(Invent_Detail paramInvent_Detail)
  {
    new Invent_DetailDaoImpl().Insert(paramInvent_Detail);
  }
  
  public static void InsertInventHeaderOutUIThread(Invent_Header paramInvent_Header)
  {
    new Invent_HeaderDaoImpl().Insert(paramInvent_Header);
  }
  
  public static void InsertTransactionLogOutUIThread(TransactionLog paramTransactionLog)
  {
    new TransactionLogDaoImpl().insert(paramTransactionLog);
  }
  
  public static boolean createInvert(Invent paramInvent)
  {
    return new InventTransImpl().createInvent(paramInvent);
  }
  
  public List<CheckHistory> GetInventHistoryOutUIThread(int paramInt1, int paramInt2)
  {
    return new InventTransImpl().getCheckHistory(paramInt1, paramInt2);
  }
  
  public void InsertInventDetailListOutUIThread(int paramInt, Invent_DetailDao paramInvent_DetailDao, List<Invent_Detail> paramList)
  {
    paramInvent_DetailDao.InsertList(paramList);
  }
  
  public void InsertInventHeaderListOutUIThread(int paramInt, Invent_HeaderDao paramInvent_HeaderDao, List<Invent_Header> paramList)
  {
    paramInvent_HeaderDao.InsertList(paramList);
  }
}