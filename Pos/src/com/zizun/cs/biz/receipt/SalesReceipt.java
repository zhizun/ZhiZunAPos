package com.zizun.cs.biz.receipt;

import com.zizun.cs.biz.bloothprint.PrintManager;
import com.zizun.cs.biz.bloothprint.PrintModel;
import com.zizun.cs.biz.sale.V_TransactionDetail;
import com.zizun.cs.biz.sale.V_TransactionHeader;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SalesReceipt
  implements Serializable
{
  private static final long serialVersionUID = -1753072670016489216L;
  List<V_TransactionDetail> detailList;
  V_TransactionHeader header;
  
  public List<V_TransactionDetail> getDetailList()
  {
    return this.detailList;
  }
  
  public V_TransactionHeader getHeader()
  {
    return this.header;
  }
  
  public void printOutUIThread(OutputStream paramOutputStream, int paramInt)
  {
    paramOutputStream = new PrintModel(paramInt, this.header, this.detailList).getPrintData().iterator();
    for (;;)
    {
      if (!paramOutputStream.hasNext()) {
        return;
      }
      PrintManager.writeToStreamOutUIThread((String)paramOutputStream.next());
    }
  }
  
  public void setDetailList(List<V_TransactionDetail> paramList)
  {
    this.detailList = paramList;
  }
  
  public void setHeader(V_TransactionHeader paramV_TransactionHeader)
  {
    this.header = paramV_TransactionHeader;
  }
}