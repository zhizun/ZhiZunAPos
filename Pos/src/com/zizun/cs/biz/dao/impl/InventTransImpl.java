package com.zizun.cs.biz.dao.impl;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.InventManager;
import com.zizun.cs.biz.dao.trans.InventTrans;
import com.zizun.cs.biz.stockcal.manager.StockcalManager;
import com.zizun.cs.entities.Invent_Detail;
import com.zizun.cs.entities.Invent_Header;
import com.zizun.cs.entities.TransactionLog;
import com.zizun.cs.entities.biz.CreateInventEntity;
import com.zizun.cs.ui.entity.Invent;

public class InventTransImpl
  extends BaseTransaction
  implements InventTrans
{
  public static final String INVENT_DETAIL_SELECT = "SELECT ih.Invent_ID,ih.Invent_Number, ih.Invent_Date,ps.ProductSku_Name,ps.ProductSku_Image,id.Unit_Price,id.Unit_Price*id.Onhand_Scan AS Amount,id.Onhand_Disk,id.Onhand_Scan,id.Quantity_Adjust FROM Invent_Header AS ih INNER JOIN Invent_Detail AS id ON id.Merchant_ID = ih.Merchant_ID AND id.Invent_ID = ih.Invent_ID INNER JOIN ProductSku AS ps ON ps.Merchant_ID = id.Merchant_ID AND ps.ProductSku_ID = id.ProductSku_ID Where ih.Invent_StoreID = ? ORDER BY ih.Invent_ID DESC limit <PAGE_SIZE> OFFSET ( <INDEX> - 1 )* <PAGE_SIZE>";
  
  public boolean createInvent(Invent paramInvent)
  {
    paramInvent = new CreateInventEntity(paramInvent);
    Invent_Header localInvent_Header = paramInvent.getHeader();
    Invent_Detail localInvent_Detail = paramInvent.getDetail();
    TransactionLog localTransactionLog = paramInvent.getTLog();
    paramInvent = getDatabase();
    paramInvent.beginTransaction();
    try
    {
      InventManager.InsertInventHeaderOutUIThread(localInvent_Header);
      InventManager.InsertInventDetailOutUIThread(localInvent_Detail);
      if (localTransactionLog != null) {
        InventManager.InsertTransactionLogOutUIThread(localTransactionLog);
      }
      paramInvent.setTransactionSuccessful();
      LogUtils.i("盘点事务创建成功!");
      return true;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      return false;
    }
    finally
    {
      paramInvent.endTransaction();
      StockcalManager.getInstance().calStock();
    }
  }
  
  /* Error */
  public java.util.List<com.zizun.cs.ui.entity.CheckHistory> getCheckHistory(int paramInt1, int paramInt2)
  {
    // Byte code:
    //   0: new 91	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 92	java/util/ArrayList:<init>	()V
    //   7: astore 10
    //   9: aconst_null
    //   10: astore 9
    //   12: aconst_null
    //   13: astore 8
    //   15: aload 8
    //   17: astore 7
    //   19: aload 9
    //   21: astore 6
    //   23: invokestatic 97	com/zizun/cs/activity/manager/UserManager:getInstance	()Lcom/zizun/cs/activity/manager/UserManager;
    //   26: invokevirtual 101	com/zizun/cs/activity/manager/UserManager:getCurrentStore	()Lcom/zizun/cs/entities/Store;
    //   29: invokevirtual 107	com/zizun/cs/entities/Store:getStore_ID	()J
    //   32: lstore_3
    //   33: aload 8
    //   35: astore 7
    //   37: aload 9
    //   39: astore 6
    //   41: aload_0
    //   42: invokevirtual 40	com/zizun/cs/biz/dao/trans/impl/InventTransImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   45: ldc 10
    //   47: ldc 109
    //   49: new 111	java/lang/StringBuilder
    //   52: dup
    //   53: iload_1
    //   54: invokestatic 117	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   57: invokespecial 119	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   60: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   63: invokevirtual 127	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   66: ldc -127
    //   68: new 111	java/lang/StringBuilder
    //   71: dup
    //   72: iload_2
    //   73: invokestatic 117	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   76: invokespecial 119	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   79: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   82: invokevirtual 127	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   85: iconst_1
    //   86: anewarray 113	java/lang/String
    //   89: dup
    //   90: iconst_0
    //   91: lload_3
    //   92: invokestatic 132	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   95: aastore
    //   96: invokevirtual 136	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   99: astore 8
    //   101: aload 8
    //   103: astore 7
    //   105: aload 8
    //   107: astore 6
    //   109: ldc 10
    //   111: invokestatic 70	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   114: aload 8
    //   116: astore 7
    //   118: aload 8
    //   120: astore 6
    //   122: aload 8
    //   124: invokeinterface 142 1 0
    //   129: istore 5
    //   131: iload 5
    //   133: ifne +18 -> 151
    //   136: aload 8
    //   138: ifnull +10 -> 148
    //   141: aload 8
    //   143: invokeinterface 145 1 0
    //   148: aload 10
    //   150: areturn
    //   151: aload 8
    //   153: astore 7
    //   155: aload 8
    //   157: astore 6
    //   159: new 147	com/zizun/cs/ui/entity/CheckHistory
    //   162: dup
    //   163: invokespecial 148	com/zizun/cs/ui/entity/CheckHistory:<init>	()V
    //   166: astore 9
    //   168: aload 8
    //   170: astore 7
    //   172: aload 8
    //   174: astore 6
    //   176: aload 9
    //   178: aload 8
    //   180: aload 8
    //   182: ldc -106
    //   184: invokeinterface 154 2 0
    //   189: invokeinterface 158 2 0
    //   194: invokevirtual 162	com/zizun/cs/ui/entity/CheckHistory:setAccountAmount	(D)V
    //   197: aload 8
    //   199: astore 7
    //   201: aload 8
    //   203: astore 6
    //   205: aload 9
    //   207: aload 8
    //   209: aload 8
    //   211: ldc -92
    //   213: invokeinterface 154 2 0
    //   218: invokeinterface 158 2 0
    //   223: invokevirtual 167	com/zizun/cs/ui/entity/CheckHistory:setNum	(D)V
    //   226: aload 8
    //   228: astore 7
    //   230: aload 8
    //   232: astore 6
    //   234: aload 9
    //   236: aload 8
    //   238: aload 8
    //   240: ldc -87
    //   242: invokeinterface 154 2 0
    //   247: invokeinterface 172 2 0
    //   252: invokevirtual 175	com/zizun/cs/ui/entity/CheckHistory:setBillNum	(Ljava/lang/String;)V
    //   255: aload 8
    //   257: astore 7
    //   259: aload 8
    //   261: astore 6
    //   263: aload 9
    //   265: aload 8
    //   267: aload 8
    //   269: ldc -79
    //   271: invokeinterface 154 2 0
    //   276: invokeinterface 158 2 0
    //   281: invokevirtual 180	com/zizun/cs/ui/entity/CheckHistory:setCheckAmount	(D)V
    //   284: aload 8
    //   286: astore 7
    //   288: aload 8
    //   290: astore 6
    //   292: aload 9
    //   294: aload 8
    //   296: aload 8
    //   298: ldc -74
    //   300: invokeinterface 154 2 0
    //   305: invokeinterface 172 2 0
    //   310: invokevirtual 185	com/zizun/cs/ui/entity/CheckHistory:setDate	(Ljava/lang/String;)V
    //   313: aload 8
    //   315: astore 7
    //   317: aload 8
    //   319: astore 6
    //   321: aload 9
    //   323: aload 8
    //   325: aload 8
    //   327: ldc -69
    //   329: invokeinterface 154 2 0
    //   334: invokeinterface 172 2 0
    //   339: invokevirtual 190	com/zizun/cs/ui/entity/CheckHistory:setGoodsName	(Ljava/lang/String;)V
    //   342: aload 8
    //   344: astore 7
    //   346: aload 8
    //   348: astore 6
    //   350: aload 9
    //   352: aload 8
    //   354: aload 8
    //   356: ldc -64
    //   358: invokeinterface 154 2 0
    //   363: invokeinterface 196 2 0
    //   368: invokevirtual 200	com/zizun/cs/ui/entity/CheckHistory:setId	(J)V
    //   371: aload 8
    //   373: astore 7
    //   375: aload 8
    //   377: astore 6
    //   379: aload 9
    //   381: aload 8
    //   383: aload 8
    //   385: ldc -54
    //   387: invokeinterface 154 2 0
    //   392: invokeinterface 158 2 0
    //   397: invokevirtual 205	com/zizun/cs/ui/entity/CheckHistory:setPrice	(D)V
    //   400: aload 8
    //   402: astore 7
    //   404: aload 8
    //   406: astore 6
    //   408: aload 9
    //   410: aload 8
    //   412: aload 8
    //   414: ldc -49
    //   416: invokeinterface 154 2 0
    //   421: invokeinterface 172 2 0
    //   426: invokevirtual 210	com/zizun/cs/ui/entity/CheckHistory:setPicture	(Ljava/lang/String;)V
    //   429: aload 8
    //   431: astore 7
    //   433: aload 8
    //   435: astore 6
    //   437: aload 9
    //   439: aload 8
    //   441: aload 8
    //   443: ldc -44
    //   445: invokeinterface 154 2 0
    //   450: invokeinterface 158 2 0
    //   455: invokevirtual 215	com/zizun/cs/ui/entity/CheckHistory:setAmount	(D)V
    //   458: aload 8
    //   460: astore 7
    //   462: aload 8
    //   464: astore 6
    //   466: aload 10
    //   468: aload 9
    //   470: invokeinterface 221 2 0
    //   475: pop
    //   476: goto -362 -> 114
    //   479: astore 8
    //   481: aload 7
    //   483: astore 6
    //   485: aload 8
    //   487: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   490: aload 7
    //   492: ifnull +10 -> 502
    //   495: aload 7
    //   497: invokeinterface 145 1 0
    //   502: aconst_null
    //   503: areturn
    //   504: astore 7
    //   506: aload 6
    //   508: ifnull +10 -> 518
    //   511: aload 6
    //   513: invokeinterface 145 1 0
    //   518: aload 7
    //   520: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	521	0	this	InventTransImpl
    //   0	521	1	paramInt1	int
    //   0	521	2	paramInt2	int
    //   32	60	3	l	long
    //   129	3	5	bool	boolean
    //   21	491	6	localObject1	Object
    //   17	479	7	localCursor1	android.database.Cursor
    //   504	15	7	localObject2	Object
    //   13	450	8	localCursor2	android.database.Cursor
    //   479	7	8	localException	Exception
    //   10	459	9	localCheckHistory	com.zizun.cs.ui.entity.CheckHistory
    //   7	460	10	localArrayList	java.util.ArrayList
    // Exception table:
    //   from	to	target	type
    //   23	33	479	java/lang/Exception
    //   41	101	479	java/lang/Exception
    //   109	114	479	java/lang/Exception
    //   122	131	479	java/lang/Exception
    //   159	168	479	java/lang/Exception
    //   176	197	479	java/lang/Exception
    //   205	226	479	java/lang/Exception
    //   234	255	479	java/lang/Exception
    //   263	284	479	java/lang/Exception
    //   292	313	479	java/lang/Exception
    //   321	342	479	java/lang/Exception
    //   350	371	479	java/lang/Exception
    //   379	400	479	java/lang/Exception
    //   408	429	479	java/lang/Exception
    //   437	458	479	java/lang/Exception
    //   466	476	479	java/lang/Exception
    //   23	33	504	finally
    //   41	101	504	finally
    //   109	114	504	finally
    //   122	131	504	finally
    //   159	168	504	finally
    //   176	197	504	finally
    //   205	226	504	finally
    //   234	255	504	finally
    //   263	284	504	finally
    //   292	313	504	finally
    //   321	342	504	finally
    //   350	371	504	finally
    //   379	400	504	finally
    //   408	429	504	finally
    //   437	458	504	finally
    //   466	476	504	finally
    //   485	490	504	finally
  }
}