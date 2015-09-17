package com.zizun.cs.biz.dao.trans.impl;

import com.zizun.cs.biz.dao.trans.RegisterTrans;

public class RegisterTransaction
  extends BaseTransaction
  implements RegisterTrans
{
  private static RegisterTransaction instance;
  
  public static void clear()
  {
    if (instance != null) {
      instance = null;
    }
  }
  
  public static RegisterTransaction getInstance()
  {
    if (instance == null) {
      instance = new RegisterTransaction();
    }
    return instance;
  }
  
  /* Error */
  public boolean doRegisterTransaction(com.yumendian.cs.entities.api.RegisterResult.RegisterResultData paramRegisterResultData)
  {
    // Byte code:
    //   0: new 25	com/yumendian/cs/orm/SqlUtil
    //   3: dup
    //   4: getstatic 31	com/yumendian/cs/orm/SqlUtil$SqlType:INSERT	Lcom/yumendian/cs/orm/SqlUtil$SqlType;
    //   7: aload_1
    //   8: getfield 37	com/yumendian/cs/entities/api/RegisterResult$RegisterResultData:S_User	Lcom/yumendian/cs/entities/S_User;
    //   11: invokespecial 40	com/yumendian/cs/orm/SqlUtil:<init>	(Lcom/yumendian/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   14: astore_3
    //   15: new 25	com/yumendian/cs/orm/SqlUtil
    //   18: dup
    //   19: getstatic 31	com/yumendian/cs/orm/SqlUtil$SqlType:INSERT	Lcom/yumendian/cs/orm/SqlUtil$SqlType;
    //   22: aload_1
    //   23: getfield 44	com/yumendian/cs/entities/api/RegisterResult$RegisterResultData:Store	Ljava/util/List;
    //   26: iconst_0
    //   27: invokeinterface 50 2 0
    //   32: invokespecial 40	com/yumendian/cs/orm/SqlUtil:<init>	(Lcom/yumendian/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   35: astore 4
    //   37: new 25	com/yumendian/cs/orm/SqlUtil
    //   40: dup
    //   41: getstatic 31	com/yumendian/cs/orm/SqlUtil$SqlType:INSERT	Lcom/yumendian/cs/orm/SqlUtil$SqlType;
    //   44: aload_1
    //   45: getfield 54	com/yumendian/cs/entities/api/RegisterResult$RegisterResultData:S_Merchant	Lcom/yumendian/cs/entities/S_Merchant;
    //   48: invokespecial 40	com/yumendian/cs/orm/SqlUtil:<init>	(Lcom/yumendian/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   51: astore 5
    //   53: aload_1
    //   54: getfield 57	com/yumendian/cs/entities/api/RegisterResult$RegisterResultData:S_Module	Ljava/util/List;
    //   57: invokeinterface 61 1 0
    //   62: anewarray 25	com/yumendian/cs/orm/SqlUtil
    //   65: astore 6
    //   67: aload_1
    //   68: getfield 64	com/yumendian/cs/entities/api/RegisterResult$RegisterResultData:StoreStaff	Ljava/util/List;
    //   71: invokeinterface 61 1 0
    //   76: anewarray 25	com/yumendian/cs/orm/SqlUtil
    //   79: astore 7
    //   81: iconst_0
    //   82: istore_2
    //   83: iload_2
    //   84: aload 6
    //   86: arraylength
    //   87: if_icmplt +118 -> 205
    //   90: iconst_0
    //   91: istore_2
    //   92: iload_2
    //   93: aload 7
    //   95: arraylength
    //   96: if_icmplt +140 -> 236
    //   99: aload_0
    //   100: invokevirtual 68	com/yumendian/cs/biz/dao/trans/impl/RegisterTransaction:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   103: astore_1
    //   104: aload_1
    //   105: invokevirtual 73	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   108: aload_1
    //   109: aload_3
    //   110: invokevirtual 77	com/yumendian/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   113: invokevirtual 82	java/lang/String:toString	()Ljava/lang/String;
    //   116: aload_3
    //   117: invokevirtual 86	com/yumendian/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   120: invokeinterface 90 1 0
    //   125: invokevirtual 94	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   128: aload_1
    //   129: aload 4
    //   131: invokevirtual 77	com/yumendian/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   134: invokevirtual 82	java/lang/String:toString	()Ljava/lang/String;
    //   137: aload 4
    //   139: invokevirtual 86	com/yumendian/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   142: invokeinterface 90 1 0
    //   147: invokevirtual 94	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   150: aload_1
    //   151: aload 5
    //   153: invokevirtual 77	com/yumendian/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   156: invokevirtual 82	java/lang/String:toString	()Ljava/lang/String;
    //   159: aload 5
    //   161: invokevirtual 86	com/yumendian/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   164: invokeinterface 90 1 0
    //   169: invokevirtual 94	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   172: iconst_0
    //   173: istore_2
    //   174: iload_2
    //   175: aload 6
    //   177: arraylength
    //   178: if_icmplt +89 -> 267
    //   181: iconst_0
    //   182: istore_2
    //   183: iload_2
    //   184: aload 7
    //   186: arraylength
    //   187: if_icmplt +113 -> 300
    //   190: aload_1
    //   191: invokevirtual 97	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   194: ldc 99
    //   196: invokestatic 105	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   199: aload_1
    //   200: invokevirtual 108	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   203: iconst_1
    //   204: ireturn
    //   205: aload 6
    //   207: iload_2
    //   208: new 25	com/yumendian/cs/orm/SqlUtil
    //   211: dup
    //   212: getstatic 31	com/yumendian/cs/orm/SqlUtil$SqlType:INSERT	Lcom/yumendian/cs/orm/SqlUtil$SqlType;
    //   215: aload_1
    //   216: getfield 57	com/yumendian/cs/entities/api/RegisterResult$RegisterResultData:S_Module	Ljava/util/List;
    //   219: iload_2
    //   220: invokeinterface 50 2 0
    //   225: invokespecial 40	com/yumendian/cs/orm/SqlUtil:<init>	(Lcom/yumendian/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   228: aastore
    //   229: iload_2
    //   230: iconst_1
    //   231: iadd
    //   232: istore_2
    //   233: goto -150 -> 83
    //   236: aload 7
    //   238: iload_2
    //   239: new 25	com/yumendian/cs/orm/SqlUtil
    //   242: dup
    //   243: getstatic 31	com/yumendian/cs/orm/SqlUtil$SqlType:INSERT	Lcom/yumendian/cs/orm/SqlUtil$SqlType;
    //   246: aload_1
    //   247: getfield 64	com/yumendian/cs/entities/api/RegisterResult$RegisterResultData:StoreStaff	Ljava/util/List;
    //   250: iload_2
    //   251: invokeinterface 50 2 0
    //   256: invokespecial 40	com/yumendian/cs/orm/SqlUtil:<init>	(Lcom/yumendian/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   259: aastore
    //   260: iload_2
    //   261: iconst_1
    //   262: iadd
    //   263: istore_2
    //   264: goto -172 -> 92
    //   267: aload_1
    //   268: aload 6
    //   270: iload_2
    //   271: aaload
    //   272: invokevirtual 77	com/yumendian/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   275: invokevirtual 82	java/lang/String:toString	()Ljava/lang/String;
    //   278: aload 6
    //   280: iload_2
    //   281: aaload
    //   282: invokevirtual 86	com/yumendian/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   285: invokeinterface 90 1 0
    //   290: invokevirtual 94	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   293: iload_2
    //   294: iconst_1
    //   295: iadd
    //   296: istore_2
    //   297: goto -123 -> 174
    //   300: aload_1
    //   301: aload 7
    //   303: iload_2
    //   304: aaload
    //   305: invokevirtual 77	com/yumendian/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   308: invokevirtual 82	java/lang/String:toString	()Ljava/lang/String;
    //   311: aload 7
    //   313: iload_2
    //   314: aaload
    //   315: invokevirtual 86	com/yumendian/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   318: invokeinterface 90 1 0
    //   323: invokevirtual 94	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   326: iload_2
    //   327: iconst_1
    //   328: iadd
    //   329: istore_2
    //   330: goto -147 -> 183
    //   333: astore_3
    //   334: aload_3
    //   335: invokevirtual 111	android/database/SQLException:printStackTrace	()V
    //   338: aload_1
    //   339: invokevirtual 108	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   342: iconst_0
    //   343: ireturn
    //   344: astore_3
    //   345: aload_1
    //   346: invokevirtual 108	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   349: aload_3
    //   350: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	351	0	this	RegisterTransaction
    //   0	351	1	paramRegisterResultData	com.yumendian.cs.entities.api.RegisterResult.RegisterResultData
    //   82	248	2	i	int
    //   14	103	3	localSqlUtil1	com.yumendian.cs.orm.SqlUtil
    //   333	2	3	localSQLException	android.database.SQLException
    //   344	6	3	localObject	Object
    //   35	103	4	localSqlUtil2	com.yumendian.cs.orm.SqlUtil
    //   51	109	5	localSqlUtil3	com.yumendian.cs.orm.SqlUtil
    //   65	214	6	arrayOfSqlUtil1	com.yumendian.cs.orm.SqlUtil[]
    //   79	233	7	arrayOfSqlUtil2	com.yumendian.cs.orm.SqlUtil[]
    // Exception table:
    //   from	to	target	type
    //   108	172	333	android/database/SQLException
    //   174	181	333	android/database/SQLException
    //   183	199	333	android/database/SQLException
    //   267	293	333	android/database/SQLException
    //   300	326	333	android/database/SQLException
    //   108	172	344	finally
    //   174	181	344	finally
    //   183	199	344	finally
    //   267	293	344	finally
    //   300	326	344	finally
    //   334	338	344	finally
  }
}