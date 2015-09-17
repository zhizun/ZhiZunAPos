package com.zizun.cs.biz.dao.trans;

import com.zizun.cs.ui.entity.CheckHistory;
import com.zizun.cs.ui.entity.Invent;
import java.util.List;

public abstract interface InventTrans
{
  public abstract boolean createInvent(Invent paramInvent);
  
  public abstract List<CheckHistory> getCheckHistory(int paramInt1, int paramInt2);
}