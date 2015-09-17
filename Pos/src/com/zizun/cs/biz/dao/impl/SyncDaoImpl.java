package com.zizun.cs.biz.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.biz.dao.SyncDao;
import com.zizun.cs.entities.sync.ParamTable;
import java.sql.Timestamp;
import java.util.List;

public class SyncDaoImpl
  extends BaseDaoImpl
  implements SyncDao
{
  public SyncDaoImpl() {}
  
  public SyncDaoImpl(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    LogUtils.i(paramInt);
  }
  
  /* Error */
  private List<ParamTable> getSyncParamTableDataAtOnState(List<com.zizun.cs.entities.S_Sync_Upload> paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new 38	java/util/ArrayList
    //   5: dup
    //   6: invokespecial 39	java/util/ArrayList:<init>	()V
    //   9: astore_3
    //   10: aload_1
    //   11: invokeinterface 45 1 0
    //   16: astore_1
    //   17: aload_1
    //   18: invokeinterface 51 1 0
    //   23: istore_2
    //   24: iload_2
    //   25: ifne +7 -> 32
    //   28: aload_0
    //   29: monitorexit
    //   30: aload_3
    //   31: areturn
    //   32: aload_1
    //   33: invokeinterface 55 1 0
    //   38: checkcast 57	com/zizun/cs/entities/S_Sync_Upload
    //   41: invokevirtual 60	com/zizun/cs/entities/S_Sync_Upload:getTable_Name	()Ljava/lang/String;
    //   44: astore 4
    //   46: aload_0
    //   47: aload 4
    //   49: invokespecial 64	com/zizun/cs/biz/dao/impl/SyncDaoImpl:initTableDataAtOnState	(Ljava/lang/String;)Z
    //   52: pop
    //   53: aload_0
    //   54: aload 4
    //   56: invokespecial 68	com/zizun/cs/biz/dao/impl/SyncDaoImpl:getSyncTableDataAtOnState	(Ljava/lang/String;)Ljava/util/List;
    //   59: astore 5
    //   61: aload 5
    //   63: ifnull -46 -> 17
    //   66: aload 5
    //   68: invokeinterface 72 1 0
    //   73: ifle -56 -> 17
    //   76: aload_3
    //   77: aload_0
    //   78: aload 4
    //   80: aload 5
    //   82: invokespecial 75	com/zizun/cs/biz/dao/impl/SyncDaoImpl:initTableDataAtOnState	(Ljava/lang/String;Ljava/util/List;)Lcom/zizun/cs/entities/sync/ParamTable;
    //   85: invokeinterface 79 2 0
    //   90: pop
    //   91: goto -74 -> 17
    //   94: astore_1
    //   95: aload_0
    //   96: monitorexit
    //   97: aload_1
    //   98: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	99	0	this	SyncDaoImpl
    //   0	99	1	paramList	List<com.zizun.cs.entities.S_Sync_Upload>
    //   23	2	2	bool	boolean
    //   9	68	3	localArrayList	java.util.ArrayList
    //   44	35	4	str	String
    //   59	22	5	localList	List
    // Exception table:
    //   from	to	target	type
    //   2	17	94	finally
    //   17	24	94	finally
    //   32	61	94	finally
    //   66	91	94	finally
  }
  
  /* Error */
  private List<?> getSyncTableDataAtOnState(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aconst_null
    //   3: astore_2
    //   4: aload_0
    //   5: invokevirtual 91	com/zizun/cs/biz/dao/impl/SyncDaoImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   8: astore_3
    //   9: new 16	java/lang/StringBuilder
    //   12: dup
    //   13: ldc 93
    //   15: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   18: aload_1
    //   19: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   22: ldc 99
    //   24: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: iconst_2
    //   28: invokevirtual 102	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   31: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   34: astore 4
    //   36: aload_3
    //   37: aload 4
    //   39: aload_1
    //   40: invokestatic 108	com/zizun/cs/orm/TableUtil:getEntityClassName	(Ljava/lang/String;)Ljava/lang/String;
    //   43: invokestatic 114	com/zizun/cs/orm/SQLiteUtil:getQueryList	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/String;)Ljava/util/List;
    //   46: astore_1
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_1
    //   50: areturn
    //   51: astore_1
    //   52: aload_1
    //   53: invokevirtual 117	java/lang/IllegalAccessException:printStackTrace	()V
    //   56: aload_2
    //   57: astore_1
    //   58: goto -11 -> 47
    //   61: astore_1
    //   62: aload_0
    //   63: monitorexit
    //   64: aload_1
    //   65: athrow
    //   66: astore_1
    //   67: aload_1
    //   68: invokevirtual 118	java/lang/InstantiationException:printStackTrace	()V
    //   71: aload_2
    //   72: astore_1
    //   73: goto -26 -> 47
    //   76: astore_1
    //   77: aload_1
    //   78: invokevirtual 119	java/lang/ClassNotFoundException:printStackTrace	()V
    //   81: aload_2
    //   82: astore_1
    //   83: goto -36 -> 47
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	86	0	this	SyncDaoImpl
    //   0	86	1	paramString	String
    //   3	79	2	localObject	Object
    //   8	29	3	localSQLiteDatabase	SQLiteDatabase
    //   34	4	4	str	String
    // Exception table:
    //   from	to	target	type
    //   36	47	51	java/lang/IllegalAccessException
    //   4	36	61	finally
    //   36	47	61	finally
    //   52	56	61	finally
    //   67	71	61	finally
    //   77	81	61	finally
    //   36	47	66	java/lang/InstantiationException
    //   36	47	76	java/lang/ClassNotFoundException
  }
  
  /* Error */
  private List<com.zizun.cs.entities.S_Sync_Upload> getSyncTableNameDataAtOnState()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual 91	com/zizun/cs/biz/dao/impl/SyncDaoImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   6: astore 4
    //   8: new 38	java/util/ArrayList
    //   11: dup
    //   12: invokespecial 39	java/util/ArrayList:<init>	()V
    //   15: astore 5
    //   17: ldc 57
    //   19: invokestatic 130	com/lidroid/xutils/db/sqlite/Selector:from	(Ljava/lang/Class;)Lcom/lidroid/xutils/db/sqlite/Selector;
    //   22: ldc -124
    //   24: ldc -122
    //   26: iconst_2
    //   27: invokestatic 139	java/lang/Byte:valueOf	(B)Ljava/lang/Byte;
    //   30: invokevirtual 143	com/lidroid/xutils/db/sqlite/Selector:where	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)Lcom/lidroid/xutils/db/sqlite/Selector;
    //   33: invokevirtual 144	com/lidroid/xutils/db/sqlite/Selector:toString	()Ljava/lang/String;
    //   36: astore 6
    //   38: aconst_null
    //   39: astore_3
    //   40: aconst_null
    //   41: astore_2
    //   42: aload 4
    //   44: aload 6
    //   46: aconst_null
    //   47: invokevirtual 150	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   50: astore 4
    //   52: aload 4
    //   54: astore_2
    //   55: aload 4
    //   57: astore_3
    //   58: aload 4
    //   60: invokeinterface 155 1 0
    //   65: istore_1
    //   66: iload_1
    //   67: ifne +20 -> 87
    //   70: aload 4
    //   72: ifnull +10 -> 82
    //   75: aload 4
    //   77: invokeinterface 158 1 0
    //   82: aload_0
    //   83: monitorexit
    //   84: aload 5
    //   86: areturn
    //   87: aload 4
    //   89: astore_2
    //   90: aload 4
    //   92: astore_3
    //   93: new 57	com/zizun/cs/entities/S_Sync_Upload
    //   96: dup
    //   97: invokespecial 159	com/zizun/cs/entities/S_Sync_Upload:<init>	()V
    //   100: astore 6
    //   102: aload 4
    //   104: astore_2
    //   105: aload 4
    //   107: astore_3
    //   108: aload 6
    //   110: aload 4
    //   112: aload 4
    //   114: ldc -95
    //   116: invokeinterface 165 2 0
    //   121: invokeinterface 168 2 0
    //   126: invokevirtual 171	com/zizun/cs/entities/S_Sync_Upload:setTable_Name	(Ljava/lang/String;)V
    //   129: aload 4
    //   131: astore_2
    //   132: aload 4
    //   134: astore_3
    //   135: aload 6
    //   137: iconst_2
    //   138: invokevirtual 175	com/zizun/cs/entities/S_Sync_Upload:setSync_Status	(B)V
    //   141: aload 4
    //   143: astore_2
    //   144: aload 4
    //   146: astore_3
    //   147: aload 5
    //   149: aload 6
    //   151: invokeinterface 79 2 0
    //   156: pop
    //   157: goto -105 -> 52
    //   160: astore 4
    //   162: aload_2
    //   163: astore_3
    //   164: aload 4
    //   166: invokevirtual 176	java/lang/Exception:printStackTrace	()V
    //   169: aload_2
    //   170: ifnull -88 -> 82
    //   173: aload_2
    //   174: invokeinterface 158 1 0
    //   179: goto -97 -> 82
    //   182: astore_2
    //   183: aload_0
    //   184: monitorexit
    //   185: aload_2
    //   186: athrow
    //   187: astore_2
    //   188: aload_3
    //   189: ifnull +9 -> 198
    //   192: aload_3
    //   193: invokeinterface 158 1 0
    //   198: aload_2
    //   199: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	200	0	this	SyncDaoImpl
    //   65	2	1	bool	boolean
    //   41	133	2	localObject1	Object
    //   182	4	2	localObject2	Object
    //   187	12	2	localObject3	Object
    //   39	154	3	localObject4	Object
    //   6	139	4	localObject5	Object
    //   160	5	4	localException	Exception
    //   15	133	5	localArrayList	java.util.ArrayList
    //   36	114	6	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   42	52	160	java/lang/Exception
    //   58	66	160	java/lang/Exception
    //   93	102	160	java/lang/Exception
    //   108	129	160	java/lang/Exception
    //   135	141	160	java/lang/Exception
    //   147	157	160	java/lang/Exception
    //   2	38	182	finally
    //   75	82	182	finally
    //   173	179	182	finally
    //   192	198	182	finally
    //   198	200	182	finally
    //   42	52	187	finally
    //   58	66	187	finally
    //   93	102	187	finally
    //   108	129	187	finally
    //   135	141	187	finally
    //   147	157	187	finally
    //   164	169	187	finally
  }
  
  private boolean initSyncTableNameAtOnState()
  {
    boolean bool2 = true;
    for (;;)
    {
      try
      {
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        LogUtils.i("SELECT * FROM S_Sync_Upload  WHERE Sync_Status = 1");
        Object localObject4 = null;
        Object localObject1 = null;
        localSQLiteDatabase.beginTransaction();
        try
        {
          Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM S_Sync_Upload  WHERE Sync_Status = 1", null);
          if (localCursor != null)
          {
            localObject1 = localCursor;
            localObject4 = localCursor;
            if (localCursor.moveToFirst())
            {
              localObject1 = localCursor;
              localObject4 = localCursor;
              String str1 = localCursor.getString(localCursor.getColumnIndex("Table_Name"));
              localObject1 = localCursor;
              localObject4 = localCursor;
              Object localObject5 = "SELECT * FROM S_Sync_Upload  WHERE  Table_Name = '" + str1 + "' " + " AND Sync_Status = " + 2;
              localObject1 = localCursor;
              localObject4 = localCursor;
              LogUtils.i((String)localObject5);
              localObject1 = localCursor;
              localObject4 = localCursor;
              localObject5 = localSQLiteDatabase.rawQuery((String)localObject5, null);
              localObject1 = localCursor;
              localObject4 = localCursor;
              if (!((Cursor)localObject5).moveToNext())
              {
                localObject1 = localCursor;
                localObject4 = localCursor;
                str1 = "UPDATE S_Sync_Upload SET Sync_Status = 2 WHERE  Table_Name = '" + str1 + "' " + " AND Sync_Status = " + 1;
                localObject1 = localCursor;
                localObject4 = localCursor;
                LogUtils.i(str1);
                localObject1 = localCursor;
                localObject4 = localCursor;
                localSQLiteDatabase.execSQL(str1);
                localObject1 = localCursor;
                localObject4 = localCursor;
                if (localCursor.moveToNext()) {
                  continue;
                }
                localObject1 = localCursor;
                localObject4 = localCursor;
                localSQLiteDatabase.setTransactionSuccessful();
                localSQLiteDatabase.endTransaction();
                bool1 = bool2;
                if (localCursor != null)
                {
                  localCursor.close();
                  bool1 = bool2;
                }
                return bool1;
              }
              localObject1 = localCursor;
              localObject4 = localCursor;
              String str2 = "Delete FROM S_Sync_Upload  WHERE Table_Name = '" + str1 + "' " + " AND Sync_Status = " + 2;
              localObject1 = localCursor;
              localObject4 = localCursor;
              LogUtils.i(str2);
              localObject1 = localCursor;
              localObject4 = localCursor;
              localSQLiteDatabase.execSQL(str2);
              continue;
            }
          }
          localObject3 = finally;
        }
        catch (SQLException localSQLException)
        {
          localObject4 = localObject1;
          localSQLException.printStackTrace();
          localSQLiteDatabase.endTransaction();
          if (localObject1 != null)
          {
            ((Cursor)localObject1).close();
            break label430;
            localSQLiteDatabase.endTransaction();
            if (localSQLException == null) {
              break label435;
            }
            localSQLException.close();
          }
        }
        finally
        {
          localSQLiteDatabase.endTransaction();
          if (localObject4 != null) {
            ((Cursor)localObject4).close();
          }
        }
        bool1 = false;
      }
      finally {}
      label430:
      continue;
      label435:
      boolean bool1 = false;
    }
  }
  
  /* Error */
  private boolean initTableDataAtCompleteState(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual 91	com/zizun/cs/biz/dao/impl/SyncDaoImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   6: astore 5
    //   8: new 16	java/lang/StringBuilder
    //   11: dup
    //   12: ldc 93
    //   14: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   17: aload_1
    //   18: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   21: ldc 99
    //   23: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: iconst_2
    //   27: invokevirtual 102	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   30: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   33: astore 4
    //   35: aconst_null
    //   36: astore_3
    //   37: aconst_null
    //   38: astore_2
    //   39: aload 5
    //   41: invokevirtual 185	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   44: aload 5
    //   46: aload 4
    //   48: aconst_null
    //   49: invokevirtual 150	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   52: astore 4
    //   54: aload 4
    //   56: astore_2
    //   57: aload 4
    //   59: astore_3
    //   60: aload 4
    //   62: invokeinterface 155 1 0
    //   67: ifne +35 -> 102
    //   70: aload 4
    //   72: astore_2
    //   73: aload 4
    //   75: astore_3
    //   76: aload 5
    //   78: invokevirtual 202	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   81: aload 5
    //   83: invokevirtual 205	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   86: aload 4
    //   88: ifnull +10 -> 98
    //   91: aload 4
    //   93: invokeinterface 158 1 0
    //   98: aload_0
    //   99: monitorexit
    //   100: iconst_0
    //   101: ireturn
    //   102: aload 4
    //   104: astore_2
    //   105: aload 4
    //   107: astore_3
    //   108: new 16	java/lang/StringBuilder
    //   111: dup
    //   112: ldc -45
    //   114: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   117: aload_1
    //   118: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: ldc -43
    //   123: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: iconst_3
    //   127: invokevirtual 102	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   130: ldc 99
    //   132: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: iconst_2
    //   136: invokevirtual 102	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   139: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   142: astore 6
    //   144: aload 4
    //   146: astore_2
    //   147: aload 4
    //   149: astore_3
    //   150: aload 6
    //   152: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   155: aload 4
    //   157: astore_2
    //   158: aload 4
    //   160: astore_3
    //   161: aload 5
    //   163: aload 6
    //   165: invokevirtual 199	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   168: goto -114 -> 54
    //   171: astore_1
    //   172: aload_2
    //   173: astore_3
    //   174: aload_1
    //   175: invokevirtual 208	android/database/SQLException:printStackTrace	()V
    //   178: aload 5
    //   180: invokevirtual 205	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   183: aload_2
    //   184: ifnull -86 -> 98
    //   187: aload_2
    //   188: invokeinterface 158 1 0
    //   193: goto -95 -> 98
    //   196: astore_1
    //   197: aload_0
    //   198: monitorexit
    //   199: aload_1
    //   200: athrow
    //   201: astore_1
    //   202: aload 5
    //   204: invokevirtual 205	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   207: aload_3
    //   208: ifnull +9 -> 217
    //   211: aload_3
    //   212: invokeinterface 158 1 0
    //   217: aload_1
    //   218: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	219	0	this	SyncDaoImpl
    //   0	219	1	paramString	String
    //   38	150	2	localObject1	Object
    //   36	176	3	localObject2	Object
    //   33	126	4	localObject3	Object
    //   6	197	5	localSQLiteDatabase	SQLiteDatabase
    //   142	22	6	str	String
    // Exception table:
    //   from	to	target	type
    //   44	54	171	android/database/SQLException
    //   60	70	171	android/database/SQLException
    //   76	81	171	android/database/SQLException
    //   108	144	171	android/database/SQLException
    //   150	155	171	android/database/SQLException
    //   161	168	171	android/database/SQLException
    //   2	35	196	finally
    //   39	44	196	finally
    //   81	86	196	finally
    //   91	98	196	finally
    //   178	183	196	finally
    //   187	193	196	finally
    //   202	207	196	finally
    //   211	217	196	finally
    //   217	219	196	finally
    //   44	54	201	finally
    //   60	70	201	finally
    //   76	81	201	finally
    //   108	144	201	finally
    //   150	155	201	finally
    //   161	168	201	finally
    //   174	178	201	finally
  }
  
  /* Error */
  private ParamTable initTableDataAtOnState(String paramString, List<Object> paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aconst_null
    //   3: astore 4
    //   5: aload 4
    //   7: astore_3
    //   8: aload_1
    //   9: invokestatic 219	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   12: ifne +40 -> 52
    //   15: aload 4
    //   17: astore_3
    //   18: aload_2
    //   19: ifnull +33 -> 52
    //   22: aload 4
    //   24: astore_3
    //   25: aload_2
    //   26: invokeinterface 72 1 0
    //   31: ifle +21 -> 52
    //   34: new 221	com/zizun/cs/entities/sync/ParamTable
    //   37: dup
    //   38: invokespecial 222	com/zizun/cs/entities/sync/ParamTable:<init>	()V
    //   41: astore_3
    //   42: aload_3
    //   43: aload_1
    //   44: putfield 226	com/zizun/cs/entities/sync/ParamTable:TableName	Ljava/lang/String;
    //   47: aload_3
    //   48: aload_2
    //   49: putfield 230	com/zizun/cs/entities/sync/ParamTable:Items	Ljava/util/List;
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_3
    //   55: areturn
    //   56: astore_1
    //   57: aload_0
    //   58: monitorexit
    //   59: aload_1
    //   60: athrow
    //   61: astore_1
    //   62: goto -5 -> 57
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	65	0	this	SyncDaoImpl
    //   0	65	1	paramString	String
    //   0	65	2	paramList	List<Object>
    //   7	48	3	localObject1	Object
    //   3	20	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   8	15	56	finally
    //   25	42	56	finally
    //   42	52	61	finally
  }
  
  /* Error */
  private boolean initTableDataAtOnState(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual 91	com/zizun/cs/biz/dao/impl/SyncDaoImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   6: astore 5
    //   8: new 16	java/lang/StringBuilder
    //   11: dup
    //   12: ldc 93
    //   14: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   17: aload_1
    //   18: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   21: ldc 99
    //   23: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: iconst_1
    //   27: invokevirtual 102	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   30: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   33: astore 4
    //   35: aconst_null
    //   36: astore_3
    //   37: aconst_null
    //   38: astore_2
    //   39: aload 5
    //   41: invokevirtual 185	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   44: aload 5
    //   46: aload 4
    //   48: aconst_null
    //   49: invokevirtual 150	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   52: astore 4
    //   54: aload 4
    //   56: astore_2
    //   57: aload 4
    //   59: astore_3
    //   60: aload 4
    //   62: invokeinterface 155 1 0
    //   67: ifne +35 -> 102
    //   70: aload 4
    //   72: astore_2
    //   73: aload 4
    //   75: astore_3
    //   76: aload 5
    //   78: invokevirtual 202	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   81: aload 5
    //   83: invokevirtual 205	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   86: aload 4
    //   88: ifnull +10 -> 98
    //   91: aload 4
    //   93: invokeinterface 158 1 0
    //   98: aload_0
    //   99: monitorexit
    //   100: iconst_0
    //   101: ireturn
    //   102: aload 4
    //   104: astore_2
    //   105: aload 4
    //   107: astore_3
    //   108: new 16	java/lang/StringBuilder
    //   111: dup
    //   112: ldc -45
    //   114: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   117: aload_1
    //   118: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: ldc -43
    //   123: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: iconst_2
    //   127: invokevirtual 102	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   130: ldc 99
    //   132: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: iconst_1
    //   136: invokevirtual 102	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   139: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   142: astore 6
    //   144: aload 4
    //   146: astore_2
    //   147: aload 4
    //   149: astore_3
    //   150: aload 6
    //   152: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   155: aload 4
    //   157: astore_2
    //   158: aload 4
    //   160: astore_3
    //   161: aload 5
    //   163: aload 6
    //   165: invokevirtual 199	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   168: goto -114 -> 54
    //   171: astore_1
    //   172: aload_2
    //   173: astore_3
    //   174: aload_1
    //   175: invokevirtual 208	android/database/SQLException:printStackTrace	()V
    //   178: aload 5
    //   180: invokevirtual 205	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   183: aload_2
    //   184: ifnull -86 -> 98
    //   187: aload_2
    //   188: invokeinterface 158 1 0
    //   193: goto -95 -> 98
    //   196: astore_1
    //   197: aload_0
    //   198: monitorexit
    //   199: aload_1
    //   200: athrow
    //   201: astore_1
    //   202: aload 5
    //   204: invokevirtual 205	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   207: aload_3
    //   208: ifnull +9 -> 217
    //   211: aload_3
    //   212: invokeinterface 158 1 0
    //   217: aload_1
    //   218: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	219	0	this	SyncDaoImpl
    //   0	219	1	paramString	String
    //   38	150	2	localObject1	Object
    //   36	176	3	localObject2	Object
    //   33	126	4	localObject3	Object
    //   6	197	5	localSQLiteDatabase	SQLiteDatabase
    //   142	22	6	str	String
    // Exception table:
    //   from	to	target	type
    //   44	54	171	android/database/SQLException
    //   60	70	171	android/database/SQLException
    //   76	81	171	android/database/SQLException
    //   108	144	171	android/database/SQLException
    //   150	155	171	android/database/SQLException
    //   161	168	171	android/database/SQLException
    //   2	35	196	finally
    //   39	44	196	finally
    //   81	86	196	finally
    //   91	98	196	finally
    //   178	183	196	finally
    //   187	193	196	finally
    //   202	207	196	finally
    //   211	217	196	finally
    //   217	219	196	finally
    //   44	54	201	finally
    //   60	70	201	finally
    //   76	81	201	finally
    //   108	144	201	finally
    //   150	155	201	finally
    //   161	168	201	finally
    //   174	178	201	finally
  }
  
  private void updateTableDataAtCompleteState()
  {
    try
    {
      updateTableDataAtCompleteState(getSyncTableNameDataAtOnState());
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  private void updateTableDataAtCompleteState(List<com.zizun.cs.entities.S_Sync_Upload> paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokeinterface 45 1 0
    //   8: astore_1
    //   9: aload_1
    //   10: invokeinterface 51 1 0
    //   15: istore_2
    //   16: iload_2
    //   17: ifne +6 -> 23
    //   20: aload_0
    //   21: monitorexit
    //   22: return
    //   23: aload_0
    //   24: aload_1
    //   25: invokeinterface 55 1 0
    //   30: checkcast 57	com/zizun/cs/entities/S_Sync_Upload
    //   33: invokevirtual 60	com/zizun/cs/entities/S_Sync_Upload:getTable_Name	()Ljava/lang/String;
    //   36: invokespecial 239	com/zizun/cs/biz/dao/impl/SyncDaoImpl:initTableDataAtCompleteState	(Ljava/lang/String;)Z
    //   39: pop
    //   40: goto -31 -> 9
    //   43: astore_1
    //   44: aload_0
    //   45: monitorexit
    //   46: aload_1
    //   47: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	48	0	this	SyncDaoImpl
    //   0	48	1	paramList	List<com.zizun.cs.entities.S_Sync_Upload>
    //   15	2	2	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	9	43	finally
    //   9	16	43	finally
    //   23	40	43	finally
  }
  
  /* Error */
  public Timestamp getLastBaseDataDownloadTime()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual 91	com/zizun/cs/biz/dao/impl/SyncDaoImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   6: astore 6
    //   8: aconst_null
    //   9: astore 4
    //   11: aconst_null
    //   12: astore_2
    //   13: ldc -12
    //   15: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   18: aconst_null
    //   19: astore 5
    //   21: aconst_null
    //   22: astore_3
    //   23: aload 6
    //   25: ldc -12
    //   27: aconst_null
    //   28: invokevirtual 150	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   31: astore 6
    //   33: aload 6
    //   35: astore_3
    //   36: aload_2
    //   37: astore 4
    //   39: aload 6
    //   41: astore 5
    //   43: aload 6
    //   45: invokeinterface 155 1 0
    //   50: istore_1
    //   51: iload_1
    //   52: ifne +39 -> 91
    //   55: aload_2
    //   56: astore 5
    //   58: aload 6
    //   60: ifnull +13 -> 73
    //   63: aload 6
    //   65: invokeinterface 158 1 0
    //   70: aload_2
    //   71: astore 5
    //   73: aload 5
    //   75: astore_2
    //   76: aload 5
    //   78: ifnonnull +9 -> 87
    //   81: ldc -10
    //   83: invokestatic 252	com/zizun/cs/common/utils/DateTimeUtil:getTimestampFromString	(Ljava/lang/String;)Ljava/sql/Timestamp;
    //   86: astore_2
    //   87: aload_0
    //   88: monitorexit
    //   89: aload_2
    //   90: areturn
    //   91: aload 6
    //   93: astore_3
    //   94: aload_2
    //   95: astore 4
    //   97: aload 6
    //   99: astore 5
    //   101: aload 6
    //   103: aload 6
    //   105: ldc -2
    //   107: invokeinterface 165 2 0
    //   112: invokeinterface 168 2 0
    //   117: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   120: aload 6
    //   122: astore_3
    //   123: aload_2
    //   124: astore 4
    //   126: aload 6
    //   128: astore 5
    //   130: aload 6
    //   132: aload 6
    //   134: ldc -2
    //   136: invokeinterface 165 2 0
    //   141: invokeinterface 168 2 0
    //   146: invokestatic 252	com/zizun/cs/common/utils/DateTimeUtil:getTimestampFromString	(Ljava/lang/String;)Ljava/sql/Timestamp;
    //   149: astore_2
    //   150: goto -117 -> 33
    //   153: astore_2
    //   154: aload_3
    //   155: astore 5
    //   157: aload_2
    //   158: invokevirtual 208	android/database/SQLException:printStackTrace	()V
    //   161: aload 4
    //   163: astore 5
    //   165: aload_3
    //   166: ifnull -93 -> 73
    //   169: aload_3
    //   170: invokeinterface 158 1 0
    //   175: aload 4
    //   177: astore 5
    //   179: goto -106 -> 73
    //   182: astore_2
    //   183: aload_0
    //   184: monitorexit
    //   185: aload_2
    //   186: athrow
    //   187: astore_2
    //   188: aload 5
    //   190: ifnull +10 -> 200
    //   193: aload 5
    //   195: invokeinterface 158 1 0
    //   200: aload_2
    //   201: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	202	0	this	SyncDaoImpl
    //   50	2	1	bool	boolean
    //   12	138	2	localObject1	Object
    //   153	5	2	localSQLException	SQLException
    //   182	4	2	localObject2	Object
    //   187	14	2	localObject3	Object
    //   22	148	3	localObject4	Object
    //   9	167	4	localObject5	Object
    //   19	175	5	localObject6	Object
    //   6	127	6	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   23	33	153	android/database/SQLException
    //   43	51	153	android/database/SQLException
    //   101	120	153	android/database/SQLException
    //   130	150	153	android/database/SQLException
    //   2	8	182	finally
    //   13	18	182	finally
    //   63	70	182	finally
    //   81	87	182	finally
    //   169	175	182	finally
    //   193	200	182	finally
    //   200	202	182	finally
    //   23	33	187	finally
    //   43	51	187	finally
    //   101	120	187	finally
    //   130	150	187	finally
    //   157	161	187	finally
  }
  
  /* Error */
  public Timestamp getLastDownloadTime()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual 91	com/zizun/cs/biz/dao/impl/SyncDaoImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   6: astore 6
    //   8: aconst_null
    //   9: astore 4
    //   11: aconst_null
    //   12: astore_2
    //   13: ldc_w 257
    //   16: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   19: aconst_null
    //   20: astore 5
    //   22: aconst_null
    //   23: astore_3
    //   24: aload 6
    //   26: ldc_w 257
    //   29: aconst_null
    //   30: invokevirtual 150	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   33: astore 6
    //   35: aload 6
    //   37: astore_3
    //   38: aload_2
    //   39: astore 4
    //   41: aload 6
    //   43: astore 5
    //   45: aload 6
    //   47: invokeinterface 155 1 0
    //   52: istore_1
    //   53: iload_1
    //   54: ifne +39 -> 93
    //   57: aload_2
    //   58: astore 5
    //   60: aload 6
    //   62: ifnull +13 -> 75
    //   65: aload 6
    //   67: invokeinterface 158 1 0
    //   72: aload_2
    //   73: astore 5
    //   75: aload 5
    //   77: astore_2
    //   78: aload 5
    //   80: ifnonnull +9 -> 89
    //   83: ldc -10
    //   85: invokestatic 252	com/zizun/cs/common/utils/DateTimeUtil:getTimestampFromString	(Ljava/lang/String;)Ljava/sql/Timestamp;
    //   88: astore_2
    //   89: aload_0
    //   90: monitorexit
    //   91: aload_2
    //   92: areturn
    //   93: aload 6
    //   95: astore_3
    //   96: aload_2
    //   97: astore 4
    //   99: aload 6
    //   101: astore 5
    //   103: aload 6
    //   105: aload 6
    //   107: ldc -2
    //   109: invokeinterface 165 2 0
    //   114: invokeinterface 168 2 0
    //   119: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   122: aload 6
    //   124: astore_3
    //   125: aload_2
    //   126: astore 4
    //   128: aload 6
    //   130: astore 5
    //   132: aload 6
    //   134: aload 6
    //   136: ldc -2
    //   138: invokeinterface 165 2 0
    //   143: invokeinterface 168 2 0
    //   148: invokestatic 252	com/zizun/cs/common/utils/DateTimeUtil:getTimestampFromString	(Ljava/lang/String;)Ljava/sql/Timestamp;
    //   151: astore_2
    //   152: goto -117 -> 35
    //   155: astore_2
    //   156: aload_3
    //   157: astore 5
    //   159: aload_2
    //   160: invokevirtual 208	android/database/SQLException:printStackTrace	()V
    //   163: aload 4
    //   165: astore 5
    //   167: aload_3
    //   168: ifnull -93 -> 75
    //   171: aload_3
    //   172: invokeinterface 158 1 0
    //   177: aload 4
    //   179: astore 5
    //   181: goto -106 -> 75
    //   184: astore_2
    //   185: aload_0
    //   186: monitorexit
    //   187: aload_2
    //   188: athrow
    //   189: astore_2
    //   190: aload 5
    //   192: ifnull +10 -> 202
    //   195: aload 5
    //   197: invokeinterface 158 1 0
    //   202: aload_2
    //   203: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	204	0	this	SyncDaoImpl
    //   52	2	1	bool	boolean
    //   12	140	2	localObject1	Object
    //   155	5	2	localSQLException	SQLException
    //   184	4	2	localObject2	Object
    //   189	14	2	localObject3	Object
    //   23	149	3	localObject4	Object
    //   9	169	4	localObject5	Object
    //   20	176	5	localObject6	Object
    //   6	129	6	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   24	35	155	android/database/SQLException
    //   45	53	155	android/database/SQLException
    //   103	122	155	android/database/SQLException
    //   132	152	155	android/database/SQLException
    //   2	8	184	finally
    //   13	19	184	finally
    //   65	72	184	finally
    //   83	89	184	finally
    //   171	177	184	finally
    //   195	202	184	finally
    //   202	204	184	finally
    //   24	35	189	finally
    //   45	53	189	finally
    //   103	122	189	finally
    //   132	152	189	finally
    //   159	163	189	finally
  }
  
  /* Error */
  public Timestamp getLastUploadTime()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual 91	com/zizun/cs/biz/dao/impl/SyncDaoImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   6: astore 6
    //   8: aconst_null
    //   9: astore 4
    //   11: aconst_null
    //   12: astore_2
    //   13: ldc_w 260
    //   16: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   19: aconst_null
    //   20: astore 5
    //   22: aconst_null
    //   23: astore_3
    //   24: aload 6
    //   26: ldc_w 260
    //   29: aconst_null
    //   30: invokevirtual 150	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   33: astore 6
    //   35: aload 6
    //   37: astore_3
    //   38: aload_2
    //   39: astore 4
    //   41: aload 6
    //   43: astore 5
    //   45: aload 6
    //   47: invokeinterface 155 1 0
    //   52: istore_1
    //   53: iload_1
    //   54: ifne +39 -> 93
    //   57: aload_2
    //   58: astore 5
    //   60: aload 6
    //   62: ifnull +13 -> 75
    //   65: aload 6
    //   67: invokeinterface 158 1 0
    //   72: aload_2
    //   73: astore 5
    //   75: aload 5
    //   77: astore_2
    //   78: aload 5
    //   80: ifnonnull +9 -> 89
    //   83: ldc -10
    //   85: invokestatic 252	com/zizun/cs/common/utils/DateTimeUtil:getTimestampFromString	(Ljava/lang/String;)Ljava/sql/Timestamp;
    //   88: astore_2
    //   89: aload_0
    //   90: monitorexit
    //   91: aload_2
    //   92: areturn
    //   93: aload 6
    //   95: astore_3
    //   96: aload_2
    //   97: astore 4
    //   99: aload 6
    //   101: astore 5
    //   103: aload 6
    //   105: aload 6
    //   107: ldc -2
    //   109: invokeinterface 165 2 0
    //   114: invokeinterface 168 2 0
    //   119: invokestatic 252	com/zizun/cs/common/utils/DateTimeUtil:getTimestampFromString	(Ljava/lang/String;)Ljava/sql/Timestamp;
    //   122: astore_2
    //   123: goto -88 -> 35
    //   126: astore_2
    //   127: aload_3
    //   128: astore 5
    //   130: aload_2
    //   131: invokevirtual 208	android/database/SQLException:printStackTrace	()V
    //   134: aload 4
    //   136: astore 5
    //   138: aload_3
    //   139: ifnull -64 -> 75
    //   142: aload_3
    //   143: invokeinterface 158 1 0
    //   148: aload 4
    //   150: astore 5
    //   152: goto -77 -> 75
    //   155: astore_2
    //   156: aload_0
    //   157: monitorexit
    //   158: aload_2
    //   159: athrow
    //   160: astore_2
    //   161: aload 5
    //   163: ifnull +10 -> 173
    //   166: aload 5
    //   168: invokeinterface 158 1 0
    //   173: aload_2
    //   174: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	175	0	this	SyncDaoImpl
    //   52	2	1	bool	boolean
    //   12	111	2	localObject1	Object
    //   126	5	2	localSQLException	SQLException
    //   155	4	2	localObject2	Object
    //   160	14	2	localObject3	Object
    //   23	120	3	localObject4	Object
    //   9	140	4	localObject5	Object
    //   20	147	5	localObject6	Object
    //   6	100	6	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   24	35	126	android/database/SQLException
    //   45	53	126	android/database/SQLException
    //   103	123	126	android/database/SQLException
    //   2	8	155	finally
    //   13	19	155	finally
    //   65	72	155	finally
    //   83	89	155	finally
    //   142	148	155	finally
    //   166	173	155	finally
    //   173	175	155	finally
    //   24	35	160	finally
    //   45	53	160	finally
    //   103	123	160	finally
    //   130	134	160	finally
  }
  
  public List<ParamTable> initSyncUploadData()
  {
    Object localObject3 = null;
    try
    {
      initSyncTableNameAtOnState();
      List localList = getSyncTableNameDataAtOnState();
      Object localObject1 = localObject3;
      if (localList != null)
      {
        localObject1 = localObject3;
        if (localList.size() > 0) {
          localObject1 = getSyncParamTableDataAtOnState(localList);
        }
      }
      return (List<ParamTable>)localObject1;
    }
    finally {}
  }
  
  /* Error */
  public boolean updateBaseDataDownloadTime(Timestamp paramTimestamp)
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_3
    //   2: aload_0
    //   3: monitorenter
    //   4: lconst_0
    //   5: lstore 4
    //   7: aload_0
    //   8: invokevirtual 91	com/zizun/cs/biz/dao/impl/SyncDaoImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   11: astore 9
    //   13: aconst_null
    //   14: astore 7
    //   16: aconst_null
    //   17: astore 6
    //   19: aload 9
    //   21: ldc_w 270
    //   24: aconst_null
    //   25: invokevirtual 150	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   28: astore 8
    //   30: aload 8
    //   32: astore 6
    //   34: aload 8
    //   36: astore 7
    //   38: ldc_w 270
    //   41: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   44: aload 8
    //   46: astore 6
    //   48: aload 8
    //   50: astore 7
    //   52: aload 8
    //   54: invokeinterface 155 1 0
    //   59: ifne +129 -> 188
    //   62: aload 8
    //   64: astore 6
    //   66: aload 8
    //   68: astore 7
    //   70: ldc_w 272
    //   73: ldc_w 274
    //   76: new 16	java/lang/StringBuilder
    //   79: dup
    //   80: lload 4
    //   82: invokestatic 277	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   85: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   88: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   91: invokevirtual 281	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   94: astore 10
    //   96: aload 8
    //   98: astore 6
    //   100: aload 8
    //   102: astore 7
    //   104: aload 10
    //   106: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   109: aload 8
    //   111: astore 6
    //   113: aload 8
    //   115: astore 7
    //   117: new 16	java/lang/StringBuilder
    //   120: dup
    //   121: aload 10
    //   123: invokestatic 284	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   126: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   129: ldc_w 286
    //   132: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   135: aload_1
    //   136: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   139: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   142: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   145: aload 8
    //   147: astore 6
    //   149: aload 8
    //   151: astore 7
    //   153: aload 9
    //   155: aload 10
    //   157: iconst_1
    //   158: anewarray 291	java/lang/Object
    //   161: dup
    //   162: iconst_0
    //   163: aload_1
    //   164: aastore
    //   165: invokevirtual 294	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   168: iload_3
    //   169: istore_2
    //   170: aload 8
    //   172: ifnull +12 -> 184
    //   175: aload 8
    //   177: invokeinterface 158 1 0
    //   182: iload_3
    //   183: istore_2
    //   184: aload_0
    //   185: monitorexit
    //   186: iload_2
    //   187: ireturn
    //   188: aload 8
    //   190: astore 6
    //   192: aload 8
    //   194: astore 7
    //   196: aload 8
    //   198: aload 8
    //   200: ldc_w 296
    //   203: invokeinterface 165 2 0
    //   208: invokeinterface 300 2 0
    //   213: lstore 4
    //   215: goto -171 -> 44
    //   218: astore_1
    //   219: aload 6
    //   221: astore 7
    //   223: aload_1
    //   224: invokevirtual 208	android/database/SQLException:printStackTrace	()V
    //   227: aload 6
    //   229: ifnull +20 -> 249
    //   232: aload 6
    //   234: invokeinterface 158 1 0
    //   239: iload_3
    //   240: istore_2
    //   241: goto -57 -> 184
    //   244: astore_1
    //   245: aload_0
    //   246: monitorexit
    //   247: aload_1
    //   248: athrow
    //   249: iconst_0
    //   250: istore_2
    //   251: goto -67 -> 184
    //   254: astore_1
    //   255: aload 7
    //   257: ifnull +15 -> 272
    //   260: aload 7
    //   262: invokeinterface 158 1 0
    //   267: iload_3
    //   268: istore_2
    //   269: goto -85 -> 184
    //   272: aload_1
    //   273: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	274	0	this	SyncDaoImpl
    //   0	274	1	paramTimestamp	Timestamp
    //   169	100	2	bool1	boolean
    //   1	267	3	bool2	boolean
    //   5	209	4	l	long
    //   17	216	6	localObject1	Object
    //   14	247	7	localObject2	Object
    //   28	171	8	localCursor	Cursor
    //   11	143	9	localSQLiteDatabase	SQLiteDatabase
    //   94	62	10	str	String
    // Exception table:
    //   from	to	target	type
    //   19	30	218	android/database/SQLException
    //   38	44	218	android/database/SQLException
    //   52	62	218	android/database/SQLException
    //   70	96	218	android/database/SQLException
    //   104	109	218	android/database/SQLException
    //   117	145	218	android/database/SQLException
    //   153	168	218	android/database/SQLException
    //   196	215	218	android/database/SQLException
    //   7	13	244	finally
    //   175	182	244	finally
    //   232	239	244	finally
    //   260	267	244	finally
    //   272	274	244	finally
    //   19	30	254	finally
    //   38	44	254	finally
    //   52	62	254	finally
    //   70	96	254	finally
    //   104	109	254	finally
    //   117	145	254	finally
    //   153	168	254	finally
    //   196	215	254	finally
    //   223	227	254	finally
  }
  
  public boolean updateDownloadTime(Timestamp paramTimestamp)
  {
    boolean bool2 = true;
    for (;;)
    {
      try
      {
        LogUtils.i("SELECT sssp.Parameter_Value FROM S_StoreStaff_Parameter AS sssp INNER JOIN S_Parameter AS sp  ON sp.Parameter_ID = sssp.Parameter_ID WHERE sp.Parameter_Code = 'LastDownloadTime'");
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        long l = 0L;
        Object localObject2 = null;
        Object localObject1 = null;
        try
        {
          Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT SSP_ID FROM S_StoreStaff_Parameter AS sssp INNER JOIN S_Parameter AS sp  ON sp.Parameter_ID = sssp.Parameter_ID WHERE sp.Parameter_Code = 'LastDownloadTime'", null);
          localObject1 = localCursor;
          localObject2 = localCursor;
          if (!localCursor.moveToNext())
          {
            localObject1 = localCursor;
            localObject2 = localCursor;
            String str = "UPDATE S_StoreStaff_Parameter SET Parameter_Value = ? WHERE SSP_ID = <>".replace("<>", l);
            localObject1 = localCursor;
            localObject2 = localCursor;
            LogUtils.i(str + " " + paramTimestamp);
            localObject1 = localCursor;
            localObject2 = localCursor;
            localSQLiteDatabase.execSQL(str, new Object[] { paramTimestamp });
            bool1 = bool2;
            if (localCursor != null)
            {
              localCursor.close();
              bool1 = bool2;
            }
            return bool1;
          }
          localObject1 = localCursor;
          localObject2 = localCursor;
          l = localCursor.getLong(localCursor.getColumnIndex("SSP_ID"));
          continue;
          paramTimestamp = finally;
        }
        catch (SQLException paramTimestamp)
        {
          localObject2 = localObject1;
          paramTimestamp.printStackTrace();
        }
        finally
        {
          if (localObject2 != null) {
            ((Cursor)localObject2).close();
          }
        }
        boolean bool1 = false;
      }
      finally {}
    }
  }
  
  /* Error */
  public boolean updateSyncDownLoadDataToLocal(com.zizun.cs.entities.sync.DownLoadResultData paramDownLoadResultData)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: getfield 316	com/zizun/cs/entities/sync/DownLoadResultData:Return_Data	Ljava/util/List;
    //   6: astore_1
    //   7: aload_0
    //   8: invokevirtual 91	com/zizun/cs/biz/dao/impl/SyncDaoImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   11: astore 28
    //   13: aload_1
    //   14: invokeinterface 45 1 0
    //   19: astore 29
    //   21: aload 29
    //   23: invokeinterface 51 1 0
    //   28: istore_3
    //   29: iload_3
    //   30: ifne +7 -> 37
    //   33: aload_0
    //   34: monitorexit
    //   35: iconst_0
    //   36: ireturn
    //   37: aload 29
    //   39: invokeinterface 55 1 0
    //   44: checkcast 221	com/zizun/cs/entities/sync/ParamTable
    //   47: astore 30
    //   49: aload 30
    //   51: getfield 226	com/zizun/cs/entities/sync/ParamTable:TableName	Ljava/lang/String;
    //   54: invokestatic 108	com/zizun/cs/orm/TableUtil:getEntityClassName	(Ljava/lang/String;)Ljava/lang/String;
    //   57: astore 31
    //   59: aload 31
    //   61: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   64: aload 30
    //   66: getfield 230	com/zizun/cs/entities/sync/ParamTable:Items	Ljava/util/List;
    //   69: invokeinterface 45 1 0
    //   74: astore 32
    //   76: aload 32
    //   78: invokeinterface 51 1 0
    //   83: ifeq -62 -> 21
    //   86: aload 32
    //   88: invokeinterface 55 1 0
    //   93: astore 33
    //   95: aconst_null
    //   96: astore 23
    //   98: aconst_null
    //   99: astore 24
    //   101: aconst_null
    //   102: astore 25
    //   104: aconst_null
    //   105: astore 26
    //   107: aconst_null
    //   108: astore 27
    //   110: aconst_null
    //   111: astore 6
    //   113: aconst_null
    //   114: astore 5
    //   116: aconst_null
    //   117: astore 17
    //   119: aconst_null
    //   120: astore 18
    //   122: aconst_null
    //   123: astore 19
    //   125: aconst_null
    //   126: astore 20
    //   128: aconst_null
    //   129: astore 21
    //   131: aconst_null
    //   132: astore 22
    //   134: aload 6
    //   136: astore 7
    //   138: aload 5
    //   140: astore 8
    //   142: aload 23
    //   144: astore 9
    //   146: aload 17
    //   148: astore 10
    //   150: aload 24
    //   152: astore 11
    //   154: aload 18
    //   156: astore 12
    //   158: aload 25
    //   160: astore 13
    //   162: aload 19
    //   164: astore 14
    //   166: aload 26
    //   168: astore 15
    //   170: aload 20
    //   172: astore 16
    //   174: aload 27
    //   176: astore_1
    //   177: aload 21
    //   179: astore 4
    //   181: aload 31
    //   183: invokestatic 322	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   186: astore 34
    //   188: aload 6
    //   190: astore 7
    //   192: aload 5
    //   194: astore 8
    //   196: aload 23
    //   198: astore 9
    //   200: aload 17
    //   202: astore 10
    //   204: aload 24
    //   206: astore 11
    //   208: aload 18
    //   210: astore 12
    //   212: aload 25
    //   214: astore 13
    //   216: aload 19
    //   218: astore 14
    //   220: aload 26
    //   222: astore 15
    //   224: aload 20
    //   226: astore 16
    //   228: aload 27
    //   230: astore_1
    //   231: aload 21
    //   233: astore 4
    //   235: aload 33
    //   237: invokestatic 327	com/zizun/cs/common/utils/JsonUtil:toJson	(Ljava/lang/Object;)Ljava/lang/String;
    //   240: aload 34
    //   242: invokestatic 331	com/zizun/cs/common/utils/JsonUtil:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   245: astore 34
    //   247: aload 6
    //   249: astore 7
    //   251: aload 5
    //   253: astore 8
    //   255: aload 23
    //   257: astore 9
    //   259: aload 17
    //   261: astore 10
    //   263: aload 24
    //   265: astore 11
    //   267: aload 18
    //   269: astore 12
    //   271: aload 25
    //   273: astore 13
    //   275: aload 19
    //   277: astore 14
    //   279: aload 26
    //   281: astore 15
    //   283: aload 20
    //   285: astore 16
    //   287: aload 27
    //   289: astore_1
    //   290: aload 21
    //   292: astore 4
    //   294: new 333	com/zizun/cs/orm/SqlUtil
    //   297: dup
    //   298: getstatic 339	com/zizun/cs/orm/SqlUtil$SqlType:UPDATE	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   301: aload 34
    //   303: invokespecial 342	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   306: astore 33
    //   308: aload 6
    //   310: astore 7
    //   312: aload 5
    //   314: astore 8
    //   316: aload 23
    //   318: astore 9
    //   320: aload 17
    //   322: astore 10
    //   324: aload 24
    //   326: astore 11
    //   328: aload 18
    //   330: astore 12
    //   332: aload 25
    //   334: astore 13
    //   336: aload 19
    //   338: astore 14
    //   340: aload 26
    //   342: astore 15
    //   344: aload 20
    //   346: astore 16
    //   348: aload 27
    //   350: astore_1
    //   351: aload 21
    //   353: astore 4
    //   355: new 344	java/lang/StringBuffer
    //   358: dup
    //   359: new 16	java/lang/StringBuilder
    //   362: dup
    //   363: ldc_w 346
    //   366: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   369: aload 30
    //   371: getfield 226	com/zizun/cs/entities/sync/ParamTable:TableName	Ljava/lang/String;
    //   374: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   377: ldc_w 348
    //   380: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   383: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   386: invokespecial 349	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   389: astore 35
    //   391: aload 6
    //   393: astore 7
    //   395: aload 5
    //   397: astore 8
    //   399: aload 23
    //   401: astore 9
    //   403: aload 17
    //   405: astore 10
    //   407: aload 24
    //   409: astore 11
    //   411: aload 18
    //   413: astore 12
    //   415: aload 25
    //   417: astore 13
    //   419: aload 19
    //   421: astore 14
    //   423: aload 26
    //   425: astore 15
    //   427: aload 20
    //   429: astore 16
    //   431: aload 27
    //   433: astore_1
    //   434: aload 21
    //   436: astore 4
    //   438: aload 33
    //   440: invokevirtual 352	com/zizun/cs/orm/SqlUtil:getIdNames	()Ljava/util/List;
    //   443: astore 36
    //   445: aload 6
    //   447: astore 7
    //   449: aload 5
    //   451: astore 8
    //   453: aload 23
    //   455: astore 9
    //   457: aload 17
    //   459: astore 10
    //   461: aload 24
    //   463: astore 11
    //   465: aload 18
    //   467: astore 12
    //   469: aload 25
    //   471: astore 13
    //   473: aload 19
    //   475: astore 14
    //   477: aload 26
    //   479: astore 15
    //   481: aload 20
    //   483: astore 16
    //   485: aload 27
    //   487: astore_1
    //   488: aload 21
    //   490: astore 4
    //   492: aload 33
    //   494: invokevirtual 355	com/zizun/cs/orm/SqlUtil:getIdValus	()Ljava/util/List;
    //   497: astore 37
    //   499: iconst_0
    //   500: istore_2
    //   501: aload 6
    //   503: astore 7
    //   505: aload 5
    //   507: astore 8
    //   509: aload 23
    //   511: astore 9
    //   513: aload 17
    //   515: astore 10
    //   517: aload 24
    //   519: astore 11
    //   521: aload 18
    //   523: astore 12
    //   525: aload 25
    //   527: astore 13
    //   529: aload 19
    //   531: astore 14
    //   533: aload 26
    //   535: astore 15
    //   537: aload 20
    //   539: astore 16
    //   541: aload 27
    //   543: astore_1
    //   544: aload 21
    //   546: astore 4
    //   548: iload_2
    //   549: aload 36
    //   551: invokeinterface 72 1 0
    //   556: if_icmplt +565 -> 1121
    //   559: aload 6
    //   561: astore 7
    //   563: aload 5
    //   565: astore 8
    //   567: aload 23
    //   569: astore 9
    //   571: aload 17
    //   573: astore 10
    //   575: aload 24
    //   577: astore 11
    //   579: aload 18
    //   581: astore 12
    //   583: aload 25
    //   585: astore 13
    //   587: aload 19
    //   589: astore 14
    //   591: aload 26
    //   593: astore 15
    //   595: aload 20
    //   597: astore 16
    //   599: aload 27
    //   601: astore_1
    //   602: aload 21
    //   604: astore 4
    //   606: aload 35
    //   608: invokevirtual 356	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   611: astore 36
    //   613: aload 6
    //   615: astore 7
    //   617: aload 5
    //   619: astore 8
    //   621: aload 23
    //   623: astore 9
    //   625: aload 17
    //   627: astore 10
    //   629: aload 24
    //   631: astore 11
    //   633: aload 18
    //   635: astore 12
    //   637: aload 25
    //   639: astore 13
    //   641: aload 19
    //   643: astore 14
    //   645: aload 26
    //   647: astore 15
    //   649: aload 20
    //   651: astore 16
    //   653: aload 27
    //   655: astore_1
    //   656: aload 21
    //   658: astore 4
    //   660: aload 36
    //   662: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   665: aload 6
    //   667: astore 7
    //   669: aload 5
    //   671: astore 8
    //   673: aload 23
    //   675: astore 9
    //   677: aload 17
    //   679: astore 10
    //   681: aload 24
    //   683: astore 11
    //   685: aload 18
    //   687: astore 12
    //   689: aload 25
    //   691: astore 13
    //   693: aload 19
    //   695: astore 14
    //   697: aload 26
    //   699: astore 15
    //   701: aload 20
    //   703: astore 16
    //   705: aload 27
    //   707: astore_1
    //   708: aload 21
    //   710: astore 4
    //   712: aload 28
    //   714: aload 36
    //   716: aconst_null
    //   717: invokevirtual 150	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   720: astore 6
    //   722: aload 6
    //   724: astore 7
    //   726: aload 5
    //   728: astore 8
    //   730: aload 6
    //   732: astore 9
    //   734: aload 17
    //   736: astore 10
    //   738: aload 6
    //   740: astore 11
    //   742: aload 18
    //   744: astore 12
    //   746: aload 6
    //   748: astore 13
    //   750: aload 19
    //   752: astore 14
    //   754: aload 6
    //   756: astore 15
    //   758: aload 20
    //   760: astore 16
    //   762: aload 6
    //   764: astore_1
    //   765: aload 21
    //   767: astore 4
    //   769: aload 6
    //   771: invokeinterface 155 1 0
    //   776: ifne +719 -> 1495
    //   779: aload 6
    //   781: astore 7
    //   783: aload 5
    //   785: astore 8
    //   787: aload 6
    //   789: astore 9
    //   791: aload 17
    //   793: astore 10
    //   795: aload 6
    //   797: astore 11
    //   799: aload 18
    //   801: astore 12
    //   803: aload 6
    //   805: astore 13
    //   807: aload 19
    //   809: astore 14
    //   811: aload 6
    //   813: astore 15
    //   815: aload 20
    //   817: astore 16
    //   819: aload 6
    //   821: astore_1
    //   822: aload 21
    //   824: astore 4
    //   826: new 333	com/zizun/cs/orm/SqlUtil
    //   829: dup
    //   830: getstatic 359	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   833: aload 34
    //   835: invokespecial 342	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   838: astore 24
    //   840: aload 6
    //   842: astore 7
    //   844: aload 5
    //   846: astore 8
    //   848: aload 6
    //   850: astore 9
    //   852: aload 17
    //   854: astore 10
    //   856: aload 6
    //   858: astore 11
    //   860: aload 18
    //   862: astore 12
    //   864: aload 6
    //   866: astore 13
    //   868: aload 19
    //   870: astore 14
    //   872: aload 6
    //   874: astore 15
    //   876: aload 20
    //   878: astore 16
    //   880: aload 6
    //   882: astore_1
    //   883: aload 21
    //   885: astore 4
    //   887: aload 24
    //   889: invokevirtual 362	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   892: invokevirtual 363	java/lang/String:toString	()Ljava/lang/String;
    //   895: astore 23
    //   897: aload 6
    //   899: astore 7
    //   901: aload 5
    //   903: astore 8
    //   905: aload 6
    //   907: astore 9
    //   909: aload 17
    //   911: astore 10
    //   913: aload 6
    //   915: astore 11
    //   917: aload 18
    //   919: astore 12
    //   921: aload 6
    //   923: astore 13
    //   925: aload 19
    //   927: astore 14
    //   929: aload 6
    //   931: astore 15
    //   933: aload 20
    //   935: astore 16
    //   937: aload 6
    //   939: astore_1
    //   940: aload 21
    //   942: astore 4
    //   944: aload 24
    //   946: invokevirtual 366	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   949: invokeinterface 370 1 0
    //   954: astore 24
    //   956: aload 6
    //   958: astore 7
    //   960: aload 5
    //   962: astore 8
    //   964: aload 6
    //   966: astore 9
    //   968: aload 17
    //   970: astore 10
    //   972: aload 6
    //   974: astore 11
    //   976: aload 18
    //   978: astore 12
    //   980: aload 6
    //   982: astore 13
    //   984: aload 19
    //   986: astore 14
    //   988: aload 6
    //   990: astore 15
    //   992: aload 20
    //   994: astore 16
    //   996: aload 6
    //   998: astore_1
    //   999: aload 21
    //   1001: astore 4
    //   1003: new 16	java/lang/StringBuilder
    //   1006: dup
    //   1007: aload 23
    //   1009: invokestatic 284	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   1012: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1015: aload 24
    //   1017: invokestatic 376	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
    //   1020: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1023: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1026: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   1029: aload 6
    //   1031: astore 7
    //   1033: aload 5
    //   1035: astore 8
    //   1037: aload 6
    //   1039: astore 9
    //   1041: aload 17
    //   1043: astore 10
    //   1045: aload 6
    //   1047: astore 11
    //   1049: aload 18
    //   1051: astore 12
    //   1053: aload 6
    //   1055: astore 13
    //   1057: aload 19
    //   1059: astore 14
    //   1061: aload 6
    //   1063: astore 15
    //   1065: aload 20
    //   1067: astore 16
    //   1069: aload 6
    //   1071: astore_1
    //   1072: aload 21
    //   1074: astore 4
    //   1076: aload 28
    //   1078: aload 23
    //   1080: aload 24
    //   1082: invokevirtual 294	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1085: aload 22
    //   1087: astore 5
    //   1089: aload 6
    //   1091: ifnull +10 -> 1101
    //   1094: aload 6
    //   1096: invokeinterface 158 1 0
    //   1101: aload 5
    //   1103: ifnull -1027 -> 76
    //   1106: aload 5
    //   1108: invokeinterface 158 1 0
    //   1113: goto -1037 -> 76
    //   1116: astore_1
    //   1117: aload_0
    //   1118: monitorexit
    //   1119: aload_1
    //   1120: athrow
    //   1121: aload 6
    //   1123: astore 7
    //   1125: aload 5
    //   1127: astore 8
    //   1129: aload 23
    //   1131: astore 9
    //   1133: aload 17
    //   1135: astore 10
    //   1137: aload 24
    //   1139: astore 11
    //   1141: aload 18
    //   1143: astore 12
    //   1145: aload 25
    //   1147: astore 13
    //   1149: aload 19
    //   1151: astore 14
    //   1153: aload 26
    //   1155: astore 15
    //   1157: aload 20
    //   1159: astore 16
    //   1161: aload 27
    //   1163: astore_1
    //   1164: aload 21
    //   1166: astore 4
    //   1168: aload 37
    //   1170: iload_2
    //   1171: invokeinterface 380 2 0
    //   1176: instanceof 18
    //   1179: ifeq +114 -> 1293
    //   1182: aload 6
    //   1184: astore 7
    //   1186: aload 5
    //   1188: astore 8
    //   1190: aload 23
    //   1192: astore 9
    //   1194: aload 17
    //   1196: astore 10
    //   1198: aload 24
    //   1200: astore 11
    //   1202: aload 18
    //   1204: astore 12
    //   1206: aload 25
    //   1208: astore 13
    //   1210: aload 19
    //   1212: astore 14
    //   1214: aload 26
    //   1216: astore 15
    //   1218: aload 20
    //   1220: astore 16
    //   1222: aload 27
    //   1224: astore_1
    //   1225: aload 21
    //   1227: astore 4
    //   1229: aload 35
    //   1231: new 16	java/lang/StringBuilder
    //   1234: dup
    //   1235: ldc_w 382
    //   1238: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1241: aload 36
    //   1243: iload_2
    //   1244: invokeinterface 380 2 0
    //   1249: checkcast 18	java/lang/String
    //   1252: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1255: ldc_w 384
    //   1258: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1261: ldc_w 386
    //   1264: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1267: aload 37
    //   1269: iload_2
    //   1270: invokeinterface 380 2 0
    //   1275: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1278: ldc -64
    //   1280: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1283: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1286: invokevirtual 389	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1289: pop
    //   1290: goto +976 -> 2266
    //   1293: aload 6
    //   1295: astore 7
    //   1297: aload 5
    //   1299: astore 8
    //   1301: aload 23
    //   1303: astore 9
    //   1305: aload 17
    //   1307: astore 10
    //   1309: aload 24
    //   1311: astore 11
    //   1313: aload 18
    //   1315: astore 12
    //   1317: aload 25
    //   1319: astore 13
    //   1321: aload 19
    //   1323: astore 14
    //   1325: aload 26
    //   1327: astore 15
    //   1329: aload 20
    //   1331: astore 16
    //   1333: aload 27
    //   1335: astore_1
    //   1336: aload 21
    //   1338: astore 4
    //   1340: aload 37
    //   1342: iload_2
    //   1343: invokeinterface 380 2 0
    //   1348: instanceof 391
    //   1351: ifeq +915 -> 2266
    //   1354: aload 6
    //   1356: astore 7
    //   1358: aload 5
    //   1360: astore 8
    //   1362: aload 23
    //   1364: astore 9
    //   1366: aload 17
    //   1368: astore 10
    //   1370: aload 24
    //   1372: astore 11
    //   1374: aload 18
    //   1376: astore 12
    //   1378: aload 25
    //   1380: astore 13
    //   1382: aload 19
    //   1384: astore 14
    //   1386: aload 26
    //   1388: astore 15
    //   1390: aload 20
    //   1392: astore 16
    //   1394: aload 27
    //   1396: astore_1
    //   1397: aload 21
    //   1399: astore 4
    //   1401: aload 35
    //   1403: new 16	java/lang/StringBuilder
    //   1406: dup
    //   1407: ldc_w 382
    //   1410: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1413: aload 36
    //   1415: iload_2
    //   1416: invokeinterface 380 2 0
    //   1421: checkcast 18	java/lang/String
    //   1424: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1427: ldc_w 384
    //   1430: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1433: aload 37
    //   1435: iload_2
    //   1436: invokeinterface 380 2 0
    //   1441: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1444: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1447: invokevirtual 389	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1450: pop
    //   1451: goto +815 -> 2266
    //   1454: astore 5
    //   1456: aload 7
    //   1458: astore_1
    //   1459: aload 8
    //   1461: astore 4
    //   1463: aload 5
    //   1465: invokevirtual 119	java/lang/ClassNotFoundException:printStackTrace	()V
    //   1468: aload 7
    //   1470: ifnull +10 -> 1480
    //   1473: aload 7
    //   1475: invokeinterface 158 1 0
    //   1480: aload 8
    //   1482: ifnull -1406 -> 76
    //   1485: aload 8
    //   1487: invokeinterface 158 1 0
    //   1492: goto -1416 -> 76
    //   1495: aload 6
    //   1497: astore 7
    //   1499: aload 5
    //   1501: astore 8
    //   1503: aload 6
    //   1505: astore 9
    //   1507: aload 17
    //   1509: astore 10
    //   1511: aload 6
    //   1513: astore 11
    //   1515: aload 18
    //   1517: astore 12
    //   1519: aload 6
    //   1521: astore 13
    //   1523: aload 19
    //   1525: astore 14
    //   1527: aload 6
    //   1529: astore 15
    //   1531: aload 20
    //   1533: astore 16
    //   1535: aload 6
    //   1537: astore_1
    //   1538: aload 21
    //   1540: astore 4
    //   1542: aload 35
    //   1544: ldc_w 393
    //   1547: invokevirtual 389	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1550: pop
    //   1551: aload 6
    //   1553: astore 7
    //   1555: aload 5
    //   1557: astore 8
    //   1559: aload 6
    //   1561: astore 9
    //   1563: aload 17
    //   1565: astore 10
    //   1567: aload 6
    //   1569: astore 11
    //   1571: aload 18
    //   1573: astore 12
    //   1575: aload 6
    //   1577: astore 13
    //   1579: aload 19
    //   1581: astore 14
    //   1583: aload 6
    //   1585: astore 15
    //   1587: aload 20
    //   1589: astore 16
    //   1591: aload 6
    //   1593: astore_1
    //   1594: aload 21
    //   1596: astore 4
    //   1598: aload 35
    //   1600: invokevirtual 356	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1603: astore 22
    //   1605: aload 6
    //   1607: astore 7
    //   1609: aload 5
    //   1611: astore 8
    //   1613: aload 6
    //   1615: astore 9
    //   1617: aload 17
    //   1619: astore 10
    //   1621: aload 6
    //   1623: astore 11
    //   1625: aload 18
    //   1627: astore 12
    //   1629: aload 6
    //   1631: astore 13
    //   1633: aload 19
    //   1635: astore 14
    //   1637: aload 6
    //   1639: astore 15
    //   1641: aload 20
    //   1643: astore 16
    //   1645: aload 6
    //   1647: astore_1
    //   1648: aload 21
    //   1650: astore 4
    //   1652: aload 22
    //   1654: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   1657: aload 6
    //   1659: astore 7
    //   1661: aload 5
    //   1663: astore 8
    //   1665: aload 6
    //   1667: astore 9
    //   1669: aload 17
    //   1671: astore 10
    //   1673: aload 6
    //   1675: astore 11
    //   1677: aload 18
    //   1679: astore 12
    //   1681: aload 6
    //   1683: astore 13
    //   1685: aload 19
    //   1687: astore 14
    //   1689: aload 6
    //   1691: astore 15
    //   1693: aload 20
    //   1695: astore 16
    //   1697: aload 6
    //   1699: astore_1
    //   1700: aload 21
    //   1702: astore 4
    //   1704: aload 28
    //   1706: aload 22
    //   1708: aconst_null
    //   1709: invokevirtual 150	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   1712: astore 5
    //   1714: aload 6
    //   1716: astore 7
    //   1718: aload 5
    //   1720: astore 8
    //   1722: aload 6
    //   1724: astore 9
    //   1726: aload 5
    //   1728: astore 10
    //   1730: aload 6
    //   1732: astore 11
    //   1734: aload 5
    //   1736: astore 12
    //   1738: aload 6
    //   1740: astore 13
    //   1742: aload 5
    //   1744: astore 14
    //   1746: aload 6
    //   1748: astore 15
    //   1750: aload 5
    //   1752: astore 16
    //   1754: aload 6
    //   1756: astore_1
    //   1757: aload 5
    //   1759: astore 4
    //   1761: aload 5
    //   1763: invokeinterface 155 1 0
    //   1768: ifne +292 -> 2060
    //   1771: aload 6
    //   1773: astore 7
    //   1775: aload 5
    //   1777: astore 8
    //   1779: aload 6
    //   1781: astore 9
    //   1783: aload 5
    //   1785: astore 10
    //   1787: aload 6
    //   1789: astore 11
    //   1791: aload 5
    //   1793: astore 12
    //   1795: aload 6
    //   1797: astore 13
    //   1799: aload 5
    //   1801: astore 14
    //   1803: aload 6
    //   1805: astore 15
    //   1807: aload 5
    //   1809: astore 16
    //   1811: aload 6
    //   1813: astore_1
    //   1814: aload 5
    //   1816: astore 4
    //   1818: aload 33
    //   1820: invokevirtual 362	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   1823: invokevirtual 363	java/lang/String:toString	()Ljava/lang/String;
    //   1826: astore 17
    //   1828: aload 6
    //   1830: astore 7
    //   1832: aload 5
    //   1834: astore 8
    //   1836: aload 6
    //   1838: astore 9
    //   1840: aload 5
    //   1842: astore 10
    //   1844: aload 6
    //   1846: astore 11
    //   1848: aload 5
    //   1850: astore 12
    //   1852: aload 6
    //   1854: astore 13
    //   1856: aload 5
    //   1858: astore 14
    //   1860: aload 6
    //   1862: astore 15
    //   1864: aload 5
    //   1866: astore 16
    //   1868: aload 6
    //   1870: astore_1
    //   1871: aload 5
    //   1873: astore 4
    //   1875: aload 33
    //   1877: invokevirtual 366	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   1880: invokeinterface 370 1 0
    //   1885: astore 18
    //   1887: aload 6
    //   1889: astore 7
    //   1891: aload 5
    //   1893: astore 8
    //   1895: aload 6
    //   1897: astore 9
    //   1899: aload 5
    //   1901: astore 10
    //   1903: aload 6
    //   1905: astore 11
    //   1907: aload 5
    //   1909: astore 12
    //   1911: aload 6
    //   1913: astore 13
    //   1915: aload 5
    //   1917: astore 14
    //   1919: aload 6
    //   1921: astore 15
    //   1923: aload 5
    //   1925: astore 16
    //   1927: aload 6
    //   1929: astore_1
    //   1930: aload 5
    //   1932: astore 4
    //   1934: new 16	java/lang/StringBuilder
    //   1937: dup
    //   1938: aload 17
    //   1940: invokestatic 284	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   1943: invokespecial 25	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1946: aload 18
    //   1948: invokestatic 376	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
    //   1951: invokevirtual 289	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1954: invokevirtual 29	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1957: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   1960: aload 6
    //   1962: astore 7
    //   1964: aload 5
    //   1966: astore 8
    //   1968: aload 6
    //   1970: astore 9
    //   1972: aload 5
    //   1974: astore 10
    //   1976: aload 6
    //   1978: astore 11
    //   1980: aload 5
    //   1982: astore 12
    //   1984: aload 6
    //   1986: astore 13
    //   1988: aload 5
    //   1990: astore 14
    //   1992: aload 6
    //   1994: astore 15
    //   1996: aload 5
    //   1998: astore 16
    //   2000: aload 6
    //   2002: astore_1
    //   2003: aload 5
    //   2005: astore 4
    //   2007: aload 28
    //   2009: aload 17
    //   2011: aload 18
    //   2013: invokevirtual 294	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   2016: goto -927 -> 1089
    //   2019: astore 5
    //   2021: aload 9
    //   2023: astore_1
    //   2024: aload 10
    //   2026: astore 4
    //   2028: aload 5
    //   2030: invokevirtual 394	com/google/gson/JsonSyntaxException:printStackTrace	()V
    //   2033: aload 9
    //   2035: ifnull +10 -> 2045
    //   2038: aload 9
    //   2040: invokeinterface 158 1 0
    //   2045: aload 10
    //   2047: ifnull -1971 -> 76
    //   2050: aload 10
    //   2052: invokeinterface 158 1 0
    //   2057: goto -1981 -> 76
    //   2060: aload 6
    //   2062: astore 7
    //   2064: aload 5
    //   2066: astore 8
    //   2068: aload 6
    //   2070: astore 9
    //   2072: aload 5
    //   2074: astore 10
    //   2076: aload 6
    //   2078: astore 11
    //   2080: aload 5
    //   2082: astore 12
    //   2084: aload 6
    //   2086: astore 13
    //   2088: aload 5
    //   2090: astore 14
    //   2092: aload 6
    //   2094: astore 15
    //   2096: aload 5
    //   2098: astore 16
    //   2100: aload 6
    //   2102: astore_1
    //   2103: aload 5
    //   2105: astore 4
    //   2107: ldc_w 396
    //   2110: invokestatic 34	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   2113: goto -1024 -> 1089
    //   2116: astore 5
    //   2118: aload 11
    //   2120: astore_1
    //   2121: aload 12
    //   2123: astore 4
    //   2125: aload 5
    //   2127: invokevirtual 397	java/lang/SecurityException:printStackTrace	()V
    //   2130: aload 11
    //   2132: ifnull +10 -> 2142
    //   2135: aload 11
    //   2137: invokeinterface 158 1 0
    //   2142: aload 12
    //   2144: ifnull -2068 -> 76
    //   2147: aload 12
    //   2149: invokeinterface 158 1 0
    //   2154: goto -2078 -> 76
    //   2157: astore 5
    //   2159: aload 13
    //   2161: astore_1
    //   2162: aload 14
    //   2164: astore 4
    //   2166: aload 5
    //   2168: invokevirtual 398	java/lang/IllegalArgumentException:printStackTrace	()V
    //   2171: aload 13
    //   2173: ifnull +10 -> 2183
    //   2176: aload 13
    //   2178: invokeinterface 158 1 0
    //   2183: aload 14
    //   2185: ifnull -2109 -> 76
    //   2188: aload 14
    //   2190: invokeinterface 158 1 0
    //   2195: goto -2119 -> 76
    //   2198: astore 5
    //   2200: aload 15
    //   2202: astore_1
    //   2203: aload 16
    //   2205: astore 4
    //   2207: aload 5
    //   2209: invokevirtual 208	android/database/SQLException:printStackTrace	()V
    //   2212: aload 15
    //   2214: ifnull +10 -> 2224
    //   2217: aload 15
    //   2219: invokeinterface 158 1 0
    //   2224: aload 16
    //   2226: ifnull -2150 -> 76
    //   2229: aload 16
    //   2231: invokeinterface 158 1 0
    //   2236: goto -2160 -> 76
    //   2239: astore 5
    //   2241: aload_1
    //   2242: ifnull +9 -> 2251
    //   2245: aload_1
    //   2246: invokeinterface 158 1 0
    //   2251: aload 4
    //   2253: ifnull +10 -> 2263
    //   2256: aload 4
    //   2258: invokeinterface 158 1 0
    //   2263: aload 5
    //   2265: athrow
    //   2266: iload_2
    //   2267: iconst_1
    //   2268: iadd
    //   2269: istore_2
    //   2270: goto -1769 -> 501
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	2273	0	this	SyncDaoImpl
    //   0	2273	1	paramDownLoadResultData	com.zizun.cs.entities.sync.DownLoadResultData
    //   500	1770	2	i	int
    //   28	2	3	bool	boolean
    //   179	2078	4	localObject1	Object
    //   114	1245	5	localObject2	Object
    //   1454	208	5	localClassNotFoundException	ClassNotFoundException
    //   1712	292	5	localCursor1	Cursor
    //   2019	85	5	localJsonSyntaxException	com.google.gson.JsonSyntaxException
    //   2116	10	5	localSecurityException	SecurityException
    //   2157	10	5	localIllegalArgumentException	IllegalArgumentException
    //   2198	10	5	localSQLException	SQLException
    //   2239	25	5	localObject3	Object
    //   111	1990	6	localCursor2	Cursor
    //   136	1927	7	localCursor3	Cursor
    //   140	1927	8	localObject4	Object
    //   144	1927	9	localObject5	Object
    //   148	1927	10	localObject6	Object
    //   152	1984	11	localObject7	Object
    //   156	1992	12	localObject8	Object
    //   160	2017	13	localObject9	Object
    //   164	2025	14	localObject10	Object
    //   168	2050	15	localObject11	Object
    //   172	2058	16	localObject12	Object
    //   117	1893	17	str1	String
    //   120	1892	18	arrayOfObject	Object[]
    //   123	1563	19	localObject13	Object
    //   126	1568	20	localObject14	Object
    //   129	1572	21	localObject15	Object
    //   132	1575	22	str2	String
    //   96	1267	23	str3	String
    //   99	1272	24	localObject16	Object
    //   102	1277	25	localObject17	Object
    //   105	1282	26	localObject18	Object
    //   108	1287	27	localObject19	Object
    //   11	1997	28	localSQLiteDatabase	SQLiteDatabase
    //   19	19	29	localIterator1	java.util.Iterator
    //   47	323	30	localParamTable	ParamTable
    //   57	125	31	str4	String
    //   74	13	32	localIterator2	java.util.Iterator
    //   93	1783	33	localObject20	Object
    //   186	648	34	localObject21	Object
    //   389	1210	35	localStringBuffer	StringBuffer
    //   443	971	36	localObject22	Object
    //   497	937	37	localList	List
    // Exception table:
    //   from	to	target	type
    //   2	21	1116	finally
    //   21	29	1116	finally
    //   37	76	1116	finally
    //   76	95	1116	finally
    //   1094	1101	1116	finally
    //   1106	1113	1116	finally
    //   1473	1480	1116	finally
    //   1485	1492	1116	finally
    //   2038	2045	1116	finally
    //   2050	2057	1116	finally
    //   2135	2142	1116	finally
    //   2147	2154	1116	finally
    //   2176	2183	1116	finally
    //   2188	2195	1116	finally
    //   2217	2224	1116	finally
    //   2229	2236	1116	finally
    //   2245	2251	1116	finally
    //   2256	2263	1116	finally
    //   2263	2266	1116	finally
    //   181	188	1454	java/lang/ClassNotFoundException
    //   235	247	1454	java/lang/ClassNotFoundException
    //   294	308	1454	java/lang/ClassNotFoundException
    //   355	391	1454	java/lang/ClassNotFoundException
    //   438	445	1454	java/lang/ClassNotFoundException
    //   492	499	1454	java/lang/ClassNotFoundException
    //   548	559	1454	java/lang/ClassNotFoundException
    //   606	613	1454	java/lang/ClassNotFoundException
    //   660	665	1454	java/lang/ClassNotFoundException
    //   712	722	1454	java/lang/ClassNotFoundException
    //   769	779	1454	java/lang/ClassNotFoundException
    //   826	840	1454	java/lang/ClassNotFoundException
    //   887	897	1454	java/lang/ClassNotFoundException
    //   944	956	1454	java/lang/ClassNotFoundException
    //   1003	1029	1454	java/lang/ClassNotFoundException
    //   1076	1085	1454	java/lang/ClassNotFoundException
    //   1168	1182	1454	java/lang/ClassNotFoundException
    //   1229	1290	1454	java/lang/ClassNotFoundException
    //   1340	1354	1454	java/lang/ClassNotFoundException
    //   1401	1451	1454	java/lang/ClassNotFoundException
    //   1542	1551	1454	java/lang/ClassNotFoundException
    //   1598	1605	1454	java/lang/ClassNotFoundException
    //   1652	1657	1454	java/lang/ClassNotFoundException
    //   1704	1714	1454	java/lang/ClassNotFoundException
    //   1761	1771	1454	java/lang/ClassNotFoundException
    //   1818	1828	1454	java/lang/ClassNotFoundException
    //   1875	1887	1454	java/lang/ClassNotFoundException
    //   1934	1960	1454	java/lang/ClassNotFoundException
    //   2007	2016	1454	java/lang/ClassNotFoundException
    //   2107	2113	1454	java/lang/ClassNotFoundException
    //   181	188	2019	com/google/gson/JsonSyntaxException
    //   235	247	2019	com/google/gson/JsonSyntaxException
    //   294	308	2019	com/google/gson/JsonSyntaxException
    //   355	391	2019	com/google/gson/JsonSyntaxException
    //   438	445	2019	com/google/gson/JsonSyntaxException
    //   492	499	2019	com/google/gson/JsonSyntaxException
    //   548	559	2019	com/google/gson/JsonSyntaxException
    //   606	613	2019	com/google/gson/JsonSyntaxException
    //   660	665	2019	com/google/gson/JsonSyntaxException
    //   712	722	2019	com/google/gson/JsonSyntaxException
    //   769	779	2019	com/google/gson/JsonSyntaxException
    //   826	840	2019	com/google/gson/JsonSyntaxException
    //   887	897	2019	com/google/gson/JsonSyntaxException
    //   944	956	2019	com/google/gson/JsonSyntaxException
    //   1003	1029	2019	com/google/gson/JsonSyntaxException
    //   1076	1085	2019	com/google/gson/JsonSyntaxException
    //   1168	1182	2019	com/google/gson/JsonSyntaxException
    //   1229	1290	2019	com/google/gson/JsonSyntaxException
    //   1340	1354	2019	com/google/gson/JsonSyntaxException
    //   1401	1451	2019	com/google/gson/JsonSyntaxException
    //   1542	1551	2019	com/google/gson/JsonSyntaxException
    //   1598	1605	2019	com/google/gson/JsonSyntaxException
    //   1652	1657	2019	com/google/gson/JsonSyntaxException
    //   1704	1714	2019	com/google/gson/JsonSyntaxException
    //   1761	1771	2019	com/google/gson/JsonSyntaxException
    //   1818	1828	2019	com/google/gson/JsonSyntaxException
    //   1875	1887	2019	com/google/gson/JsonSyntaxException
    //   1934	1960	2019	com/google/gson/JsonSyntaxException
    //   2007	2016	2019	com/google/gson/JsonSyntaxException
    //   2107	2113	2019	com/google/gson/JsonSyntaxException
    //   181	188	2116	java/lang/SecurityException
    //   235	247	2116	java/lang/SecurityException
    //   294	308	2116	java/lang/SecurityException
    //   355	391	2116	java/lang/SecurityException
    //   438	445	2116	java/lang/SecurityException
    //   492	499	2116	java/lang/SecurityException
    //   548	559	2116	java/lang/SecurityException
    //   606	613	2116	java/lang/SecurityException
    //   660	665	2116	java/lang/SecurityException
    //   712	722	2116	java/lang/SecurityException
    //   769	779	2116	java/lang/SecurityException
    //   826	840	2116	java/lang/SecurityException
    //   887	897	2116	java/lang/SecurityException
    //   944	956	2116	java/lang/SecurityException
    //   1003	1029	2116	java/lang/SecurityException
    //   1076	1085	2116	java/lang/SecurityException
    //   1168	1182	2116	java/lang/SecurityException
    //   1229	1290	2116	java/lang/SecurityException
    //   1340	1354	2116	java/lang/SecurityException
    //   1401	1451	2116	java/lang/SecurityException
    //   1542	1551	2116	java/lang/SecurityException
    //   1598	1605	2116	java/lang/SecurityException
    //   1652	1657	2116	java/lang/SecurityException
    //   1704	1714	2116	java/lang/SecurityException
    //   1761	1771	2116	java/lang/SecurityException
    //   1818	1828	2116	java/lang/SecurityException
    //   1875	1887	2116	java/lang/SecurityException
    //   1934	1960	2116	java/lang/SecurityException
    //   2007	2016	2116	java/lang/SecurityException
    //   2107	2113	2116	java/lang/SecurityException
    //   181	188	2157	java/lang/IllegalArgumentException
    //   235	247	2157	java/lang/IllegalArgumentException
    //   294	308	2157	java/lang/IllegalArgumentException
    //   355	391	2157	java/lang/IllegalArgumentException
    //   438	445	2157	java/lang/IllegalArgumentException
    //   492	499	2157	java/lang/IllegalArgumentException
    //   548	559	2157	java/lang/IllegalArgumentException
    //   606	613	2157	java/lang/IllegalArgumentException
    //   660	665	2157	java/lang/IllegalArgumentException
    //   712	722	2157	java/lang/IllegalArgumentException
    //   769	779	2157	java/lang/IllegalArgumentException
    //   826	840	2157	java/lang/IllegalArgumentException
    //   887	897	2157	java/lang/IllegalArgumentException
    //   944	956	2157	java/lang/IllegalArgumentException
    //   1003	1029	2157	java/lang/IllegalArgumentException
    //   1076	1085	2157	java/lang/IllegalArgumentException
    //   1168	1182	2157	java/lang/IllegalArgumentException
    //   1229	1290	2157	java/lang/IllegalArgumentException
    //   1340	1354	2157	java/lang/IllegalArgumentException
    //   1401	1451	2157	java/lang/IllegalArgumentException
    //   1542	1551	2157	java/lang/IllegalArgumentException
    //   1598	1605	2157	java/lang/IllegalArgumentException
    //   1652	1657	2157	java/lang/IllegalArgumentException
    //   1704	1714	2157	java/lang/IllegalArgumentException
    //   1761	1771	2157	java/lang/IllegalArgumentException
    //   1818	1828	2157	java/lang/IllegalArgumentException
    //   1875	1887	2157	java/lang/IllegalArgumentException
    //   1934	1960	2157	java/lang/IllegalArgumentException
    //   2007	2016	2157	java/lang/IllegalArgumentException
    //   2107	2113	2157	java/lang/IllegalArgumentException
    //   181	188	2198	android/database/SQLException
    //   235	247	2198	android/database/SQLException
    //   294	308	2198	android/database/SQLException
    //   355	391	2198	android/database/SQLException
    //   438	445	2198	android/database/SQLException
    //   492	499	2198	android/database/SQLException
    //   548	559	2198	android/database/SQLException
    //   606	613	2198	android/database/SQLException
    //   660	665	2198	android/database/SQLException
    //   712	722	2198	android/database/SQLException
    //   769	779	2198	android/database/SQLException
    //   826	840	2198	android/database/SQLException
    //   887	897	2198	android/database/SQLException
    //   944	956	2198	android/database/SQLException
    //   1003	1029	2198	android/database/SQLException
    //   1076	1085	2198	android/database/SQLException
    //   1168	1182	2198	android/database/SQLException
    //   1229	1290	2198	android/database/SQLException
    //   1340	1354	2198	android/database/SQLException
    //   1401	1451	2198	android/database/SQLException
    //   1542	1551	2198	android/database/SQLException
    //   1598	1605	2198	android/database/SQLException
    //   1652	1657	2198	android/database/SQLException
    //   1704	1714	2198	android/database/SQLException
    //   1761	1771	2198	android/database/SQLException
    //   1818	1828	2198	android/database/SQLException
    //   1875	1887	2198	android/database/SQLException
    //   1934	1960	2198	android/database/SQLException
    //   2007	2016	2198	android/database/SQLException
    //   2107	2113	2198	android/database/SQLException
    //   181	188	2239	finally
    //   235	247	2239	finally
    //   294	308	2239	finally
    //   355	391	2239	finally
    //   438	445	2239	finally
    //   492	499	2239	finally
    //   548	559	2239	finally
    //   606	613	2239	finally
    //   660	665	2239	finally
    //   712	722	2239	finally
    //   769	779	2239	finally
    //   826	840	2239	finally
    //   887	897	2239	finally
    //   944	956	2239	finally
    //   1003	1029	2239	finally
    //   1076	1085	2239	finally
    //   1168	1182	2239	finally
    //   1229	1290	2239	finally
    //   1340	1354	2239	finally
    //   1401	1451	2239	finally
    //   1463	1468	2239	finally
    //   1542	1551	2239	finally
    //   1598	1605	2239	finally
    //   1652	1657	2239	finally
    //   1704	1714	2239	finally
    //   1761	1771	2239	finally
    //   1818	1828	2239	finally
    //   1875	1887	2239	finally
    //   1934	1960	2239	finally
    //   2007	2016	2239	finally
    //   2028	2033	2239	finally
    //   2107	2113	2239	finally
    //   2125	2130	2239	finally
    //   2166	2171	2239	finally
    //   2207	2212	2239	finally
  }
  
  public boolean updateSyncUploadCompleteState()
  {
    try
    {
      updateTableDataAtCompleteState();
      getDatabase().execSQL("DELETE FROM S_Sync_Upload WHERE Sync_Status = 2");
      return true;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean updateUploadTime(Timestamp paramTimestamp)
  {
    boolean bool2 = true;
    for (;;)
    {
      try
      {
        LogUtils.i("SELECT SSP_ID FROM S_StoreStaff_Parameter AS sssp INNER JOIN S_Parameter AS sp  ON sp.Parameter_ID = sssp.Parameter_ID WHERE sp.Parameter_Code = 'LastUploadTime'");
        SQLiteDatabase localSQLiteDatabase = getDatabase();
        long l = 0L;
        Object localObject2 = null;
        Object localObject1 = null;
        try
        {
          Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT SSP_ID FROM S_StoreStaff_Parameter AS sssp INNER JOIN S_Parameter AS sp  ON sp.Parameter_ID = sssp.Parameter_ID WHERE sp.Parameter_Code = 'LastUploadTime'", null);
          localObject1 = localCursor;
          localObject2 = localCursor;
          if (!localCursor.moveToNext())
          {
            localObject1 = localCursor;
            localObject2 = localCursor;
            String str = " UPDATE S_StoreStaff_Parameter SET Parameter_Value = ? WHERE SSP_ID = <>".replace("<>", l);
            localObject1 = localCursor;
            localObject2 = localCursor;
            LogUtils.i(str + " " + paramTimestamp);
            localObject1 = localCursor;
            localObject2 = localCursor;
            localSQLiteDatabase.execSQL(str, new Object[] { paramTimestamp });
            bool1 = bool2;
            if (localCursor != null)
            {
              localCursor.close();
              bool1 = bool2;
            }
            return bool1;
          }
          localObject1 = localCursor;
          localObject2 = localCursor;
          l = localCursor.getLong(localCursor.getColumnIndex("SSP_ID"));
          continue;
          paramTimestamp = finally;
        }
        catch (SQLException paramTimestamp)
        {
          localObject2 = localObject1;
          paramTimestamp.printStackTrace();
        }
        finally
        {
          if (localObject2 != null) {
            ((Cursor)localObject2).close();
          }
        }
        boolean bool1 = false;
      }
      finally {}
    }
  }
}