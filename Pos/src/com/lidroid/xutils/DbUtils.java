package com.lidroid.xutils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.lidroid.xutils.db.sqlite.CursorUtils;
import com.lidroid.xutils.db.sqlite.CursorUtils.FindCacheSequence;
import com.lidroid.xutils.db.sqlite.DbModelSelector;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.SqlInfo;
import com.lidroid.xutils.db.sqlite.SqlInfoBuilder;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.db.table.Id;
import com.lidroid.xutils.db.table.Table;
import com.lidroid.xutils.db.table.TableUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.IOUtils;
import com.lidroid.xutils.util.LogUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DbUtils
{
  private static HashMap<String, DbUtils> daoMap = new HashMap();
  private boolean allowTransaction = false;
  private DaoConfig daoConfig;
  private SQLiteDatabase database;
  private boolean debug = false;
  private final FindTempCache findTempCache = new FindTempCache(null);
  private Lock writeLock = new ReentrantLock();
  private volatile boolean writeLocked = false;
  
  private DbUtils(DaoConfig paramDaoConfig)
  {
    if (paramDaoConfig == null) {
      throw new IllegalArgumentException("daoConfig may not be null");
    }
    this.database = createDatabase(paramDaoConfig);
    this.daoConfig = paramDaoConfig;
  }
  
  private void beginTransaction()
  {
    if (this.allowTransaction)
    {
      this.database.beginTransaction();
      return;
    }
    this.writeLock.lock();
    this.writeLocked = true;
  }
  
  public static DbUtils create(Context paramContext)
  {
    return getInstance(new DaoConfig(paramContext));
  }
  
  public static DbUtils create(Context paramContext, String paramString)
  {
    paramContext = new DaoConfig(paramContext);
    paramContext.setDbName(paramString);
    return getInstance(paramContext);
  }
  
  public static DbUtils create(Context paramContext, String paramString, int paramInt, DbUpgradeListener paramDbUpgradeListener)
  {
    paramContext = new DaoConfig(paramContext);
    paramContext.setDbName(paramString);
    paramContext.setDbVersion(paramInt);
    paramContext.setDbUpgradeListener(paramDbUpgradeListener);
    return getInstance(paramContext);
  }
  
  public static DbUtils create(Context paramContext, String paramString1, String paramString2)
  {
    paramContext = new DaoConfig(paramContext);
    paramContext.setDbDir(paramString1);
    paramContext.setDbName(paramString2);
    return getInstance(paramContext);
  }
  
  public static DbUtils create(Context paramContext, String paramString1, String paramString2, int paramInt, DbUpgradeListener paramDbUpgradeListener)
  {
    paramContext = new DaoConfig(paramContext);
    paramContext.setDbDir(paramString1);
    paramContext.setDbName(paramString2);
    paramContext.setDbVersion(paramInt);
    paramContext.setDbUpgradeListener(paramDbUpgradeListener);
    return getInstance(paramContext);
  }
  
  public static DbUtils create(DaoConfig paramDaoConfig)
  {
    return getInstance(paramDaoConfig);
  }
  
  private SQLiteDatabase createDatabase(DaoConfig paramDaoConfig)
  {
    SQLiteDatabase localSQLiteDatabase = null;
    String str = paramDaoConfig.getDbDir();
    if (!TextUtils.isEmpty(str))
    {
      File localFile = new File(str);
      if ((localFile.exists()) || (localFile.mkdirs())) {
        localSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(new File(str, paramDaoConfig.getDbName()), null);
      }
      return localSQLiteDatabase;
    }
    return paramDaoConfig.getContext().openOrCreateDatabase(paramDaoConfig.getDbName(), 0, null);
  }
  
  private void debugSql(String paramString)
  {
    if (this.debug) {
      LogUtils.d(paramString);
    }
  }
  
  private void endTransaction()
  {
    if (this.allowTransaction) {
      this.database.endTransaction();
    }
    if (this.writeLocked)
    {
      this.writeLock.unlock();
      this.writeLocked = false;
    }
  }
  
  private static DbUtils getInstance(DaoConfig paramDaoConfig)
  {
    for (;;)
    {
      try
      {
        DbUtils localDbUtils = (DbUtils)daoMap.get(paramDaoConfig.getDbName());
        if (localDbUtils == null)
        {
          localDbUtils = new DbUtils(paramDaoConfig);
          daoMap.put(paramDaoConfig.getDbName(), localDbUtils);
          SQLiteDatabase localSQLiteDatabase = localDbUtils.database;
          int i = localSQLiteDatabase.getVersion();
          int j = paramDaoConfig.getDbVersion();
          if (i != j)
          {
            if (i != 0)
            {
              paramDaoConfig = paramDaoConfig.getDbUpgradeListener();
              if (paramDaoConfig != null) {
                paramDaoConfig.onUpgrade(localDbUtils, i, j);
              }
            }
            else
            {
              localSQLiteDatabase.setVersion(j);
            }
          }
          else {
            return localDbUtils;
          }
        }
        else
        {
          localDbUtils.daoConfig = paramDaoConfig;
          continue;
        }
        try
        {
          localDbUtils.dropDb();
        }
        catch (DbException paramDaoConfig)
        {
          LogUtils.e(paramDaoConfig.getMessage(), paramDaoConfig);
        }
      }
      finally {}
    }
  }
  
  private long getLastAutoIncrementId(String paramString)
    throws DbException
  {
    long l1 = -1L;
    paramString = execQuery("SELECT seq FROM sqlite_sequence WHERE name='" + paramString + "'");
    long l2 = l1;
    if (paramString != null) {}
    try
    {
      if (paramString.moveToNext()) {
        l1 = paramString.getLong(0);
      }
      IOUtils.closeQuietly(paramString);
      l2 = l1;
      return l2;
    }
    catch (Throwable localThrowable)
    {
      throw new DbException(localThrowable);
    }
    finally
    {
      IOUtils.closeQuietly(paramString);
    }
  }
  
  private boolean saveBindingIdWithoutTransaction(Object paramObject)
    throws DbException
  {
    Table localTable = Table.get(this, paramObject.getClass());
    Id localId = localTable.id;
    if (localId.isAutoIncrement())
    {
      execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(this, paramObject));
      long l = getLastAutoIncrementId(localTable.tableName);
      if (l == -1L) {
        return false;
      }
      localId.setAutoIncrementId(paramObject, l);
      return true;
    }
    execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(this, paramObject));
    return true;
  }
  
  private void saveOrUpdateWithoutTransaction(Object paramObject)
    throws DbException
  {
    Id localId = Table.get(this, paramObject.getClass()).id;
    if (localId.isAutoIncrement())
    {
      if (localId.getColumnValue(paramObject) != null)
      {
        execNonQuery(SqlInfoBuilder.buildUpdateSqlInfo(this, paramObject, new String[0]));
        return;
      }
      saveBindingIdWithoutTransaction(paramObject);
      return;
    }
    execNonQuery(SqlInfoBuilder.buildReplaceSqlInfo(this, paramObject));
  }
  
  private void setTransactionSuccessful()
  {
    if (this.allowTransaction) {
      this.database.setTransactionSuccessful();
    }
  }
  
  public void close()
  {
    String str = this.daoConfig.getDbName();
    if (daoMap.containsKey(str))
    {
      daoMap.remove(str);
      this.database.close();
    }
  }
  
  public DbUtils configAllowTransaction(boolean paramBoolean)
  {
    this.allowTransaction = paramBoolean;
    return this;
  }
  
  public DbUtils configDebug(boolean paramBoolean)
  {
    this.debug = paramBoolean;
    return this;
  }
  
  public long count(Selector paramSelector)
    throws DbException
  {
    Object localObject = paramSelector.getEntityType();
    if (!tableIsExist((Class)localObject)) {
      return 0L;
    }
    localObject = Table.get(this, (Class)localObject);
    return findDbModelFirst(paramSelector.select(new String[] { "count(" + ((Table)localObject).id.getColumnName() + ") as count" })).getLong("count");
  }
  
  public long count(Class<?> paramClass)
    throws DbException
  {
    return count(Selector.from(paramClass));
  }
  
  public void createTableIfNotExist(Class<?> paramClass)
    throws DbException
  {
    if (!tableIsExist(paramClass))
    {
      execNonQuery(SqlInfoBuilder.buildCreateTableSqlInfo(this, paramClass));
      paramClass = TableUtils.getExecAfterTableCreated(paramClass);
      if (!TextUtils.isEmpty(paramClass)) {
        execNonQuery(paramClass);
      }
    }
  }
  
  public void delete(Class<?> paramClass, WhereBuilder paramWhereBuilder)
    throws DbException
  {
    if (!tableIsExist(paramClass)) {
      return;
    }
    try
    {
      beginTransaction();
      execNonQuery(SqlInfoBuilder.buildDeleteSqlInfo(this, paramClass, paramWhereBuilder));
      setTransactionSuccessful();
      return;
    }
    finally
    {
      endTransaction();
    }
  }
  
  public void delete(Object paramObject)
    throws DbException
  {
    if (!tableIsExist(paramObject.getClass())) {
      return;
    }
    try
    {
      beginTransaction();
      execNonQuery(SqlInfoBuilder.buildDeleteSqlInfo(this, paramObject));
      setTransactionSuccessful();
      return;
    }
    finally
    {
      endTransaction();
    }
  }
  
  public void deleteAll(Class<?> paramClass)
    throws DbException
  {
    delete(paramClass, null);
  }
  
  /* Error */
  public void deleteAll(List<?> paramList)
    throws DbException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +29 -> 30
    //   4: aload_1
    //   5: invokeinterface 389 1 0
    //   10: ifeq +20 -> 30
    //   13: aload_0
    //   14: aload_1
    //   15: iconst_0
    //   16: invokeinterface 392 2 0
    //   21: invokevirtual 248	java/lang/Object:getClass	()Ljava/lang/Class;
    //   24: invokevirtual 324	com/lidroid/xutils/DbUtils:tableIsExist	(Ljava/lang/Class;)Z
    //   27: ifne +4 -> 31
    //   30: return
    //   31: aload_0
    //   32: invokespecial 371	com/lidroid/xutils/DbUtils:beginTransaction	()V
    //   35: aload_1
    //   36: invokeinterface 396 1 0
    //   41: astore_1
    //   42: aload_1
    //   43: invokeinterface 401 1 0
    //   48: ifne +12 -> 60
    //   51: aload_0
    //   52: invokespecial 376	com/lidroid/xutils/DbUtils:setTransactionSuccessful	()V
    //   55: aload_0
    //   56: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   59: return
    //   60: aload_0
    //   61: aload_0
    //   62: aload_1
    //   63: invokeinterface 405 1 0
    //   68: invokestatic 380	com/lidroid/xutils/db/sqlite/SqlInfoBuilder:buildDeleteSqlInfo	(Lcom/lidroid/xutils/DbUtils;Ljava/lang/Object;)Lcom/lidroid/xutils/db/sqlite/SqlInfo;
    //   71: invokevirtual 272	com/lidroid/xutils/DbUtils:execNonQuery	(Lcom/lidroid/xutils/db/sqlite/SqlInfo;)V
    //   74: goto -32 -> 42
    //   77: astore_1
    //   78: aload_0
    //   79: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   82: aload_1
    //   83: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	84	0	this	DbUtils
    //   0	84	1	paramList	List<?>
    // Exception table:
    //   from	to	target	type
    //   31	42	77	finally
    //   42	55	77	finally
    //   60	74	77	finally
  }
  
  public void deleteById(Class<?> paramClass, Object paramObject)
    throws DbException
  {
    if (!tableIsExist(paramClass)) {
      return;
    }
    try
    {
      beginTransaction();
      execNonQuery(SqlInfoBuilder.buildDeleteSqlInfo(this, paramClass, paramObject));
      setTransactionSuccessful();
      return;
    }
    finally
    {
      endTransaction();
    }
  }
  
  /* Error */
  public void dropDb()
    throws DbException
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc_w 414
    //   4: invokevirtual 223	com/lidroid/xutils/DbUtils:execQuery	(Ljava/lang/String;)Landroid/database/Cursor;
    //   7: astore_2
    //   8: aload_2
    //   9: ifnull +18 -> 27
    //   12: aload_2
    //   13: invokeinterface 228 1 0
    //   18: istore_1
    //   19: iload_1
    //   20: ifne +8 -> 28
    //   23: aload_2
    //   24: invokestatic 238	com/lidroid/xutils/util/IOUtils:closeQuietly	(Landroid/database/Cursor;)V
    //   27: return
    //   28: aload_2
    //   29: iconst_0
    //   30: invokeinterface 418 2 0
    //   35: astore_3
    //   36: aload_0
    //   37: new 207	java/lang/StringBuilder
    //   40: dup
    //   41: ldc_w 420
    //   44: invokespecial 210	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   47: aload_3
    //   48: invokevirtual 214	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: invokevirtual 219	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   54: invokevirtual 367	com/lidroid/xutils/DbUtils:execNonQuery	(Ljava/lang/String;)V
    //   57: aload_0
    //   58: aload_3
    //   59: invokestatic 423	com/lidroid/xutils/db/table/Table:remove	(Lcom/lidroid/xutils/DbUtils;Ljava/lang/String;)V
    //   62: goto -50 -> 12
    //   65: astore_3
    //   66: aload_3
    //   67: invokevirtual 424	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   70: aload_3
    //   71: invokestatic 199	com/lidroid/xutils/util/LogUtils:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   74: goto -62 -> 12
    //   77: astore_3
    //   78: new 161	com/lidroid/xutils/exception/DbException
    //   81: dup
    //   82: aload_3
    //   83: invokespecial 241	com/lidroid/xutils/exception/DbException:<init>	(Ljava/lang/Throwable;)V
    //   86: athrow
    //   87: astore_3
    //   88: aload_2
    //   89: invokestatic 238	com/lidroid/xutils/util/IOUtils:closeQuietly	(Landroid/database/Cursor;)V
    //   92: aload_3
    //   93: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	94	0	this	DbUtils
    //   18	2	1	bool	boolean
    //   7	82	2	localCursor	Cursor
    //   35	24	3	str	String
    //   65	6	3	localThrowable1	Throwable
    //   77	6	3	localThrowable2	Throwable
    //   87	6	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   28	62	65	java/lang/Throwable
    //   12	19	77	java/lang/Throwable
    //   66	74	77	java/lang/Throwable
    //   12	19	87	finally
    //   28	62	87	finally
    //   66	74	87	finally
    //   78	87	87	finally
  }
  
  public void dropTable(Class<?> paramClass)
    throws DbException
  {
    if (!tableIsExist(paramClass)) {
      return;
    }
    String str = TableUtils.getTableName(paramClass);
    execNonQuery("DROP TABLE " + str);
    Table.remove(this, paramClass);
  }
  
  public void execNonQuery(SqlInfo paramSqlInfo)
    throws DbException
  {
    debugSql(paramSqlInfo.getSql());
    try
    {
      if (paramSqlInfo.getBindArgs() != null)
      {
        this.database.execSQL(paramSqlInfo.getSql(), paramSqlInfo.getBindArgsAsArray());
        return;
      }
      this.database.execSQL(paramSqlInfo.getSql());
      return;
    }
    catch (Throwable paramSqlInfo)
    {
      throw new DbException(paramSqlInfo);
    }
  }
  
  public void execNonQuery(String paramString)
    throws DbException
  {
    debugSql(paramString);
    try
    {
      this.database.execSQL(paramString);
      return;
    }
    catch (Throwable paramString)
    {
      throw new DbException(paramString);
    }
  }
  
  public Cursor execQuery(SqlInfo paramSqlInfo)
    throws DbException
  {
    debugSql(paramSqlInfo.getSql());
    try
    {
      paramSqlInfo = this.database.rawQuery(paramSqlInfo.getSql(), paramSqlInfo.getBindArgsAsStrArray());
      return paramSqlInfo;
    }
    catch (Throwable paramSqlInfo)
    {
      throw new DbException(paramSqlInfo);
    }
  }
  
  public Cursor execQuery(String paramString)
    throws DbException
  {
    debugSql(paramString);
    try
    {
      paramString = this.database.rawQuery(paramString, null);
      return paramString;
    }
    catch (Throwable paramString)
    {
      throw new DbException(paramString);
    }
  }
  
  /* Error */
  public <T> List<T> findAll(Selector paramSelector)
    throws DbException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 320	com/lidroid/xutils/db/sqlite/Selector:getEntityType	()Ljava/lang/Class;
    //   5: invokevirtual 324	com/lidroid/xutils/DbUtils:tableIsExist	(Ljava/lang/Class;)Z
    //   8: ifne +5 -> 13
    //   11: aconst_null
    //   12: areturn
    //   13: aload_1
    //   14: invokevirtual 464	com/lidroid/xutils/db/sqlite/Selector:toString	()Ljava/lang/String;
    //   17: astore 5
    //   19: invokestatic 470	com/lidroid/xutils/db/sqlite/CursorUtils$FindCacheSequence:getSeq	()J
    //   22: lstore_2
    //   23: aload_0
    //   24: getfield 56	com/lidroid/xutils/DbUtils:findTempCache	Lcom/lidroid/xutils/DbUtils$FindTempCache;
    //   27: lload_2
    //   28: invokevirtual 474	com/lidroid/xutils/DbUtils$FindTempCache:setSeq	(J)V
    //   31: aload_0
    //   32: getfield 56	com/lidroid/xutils/DbUtils:findTempCache	Lcom/lidroid/xutils/DbUtils$FindTempCache;
    //   35: aload 5
    //   37: invokevirtual 477	com/lidroid/xutils/DbUtils$FindTempCache:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   40: astore 4
    //   42: aload 4
    //   44: ifnull +9 -> 53
    //   47: aload 4
    //   49: checkcast 386	java/util/List
    //   52: areturn
    //   53: new 479	java/util/ArrayList
    //   56: dup
    //   57: invokespecial 480	java/util/ArrayList:<init>	()V
    //   60: astore 6
    //   62: aload_0
    //   63: aload 5
    //   65: invokevirtual 223	com/lidroid/xutils/DbUtils:execQuery	(Ljava/lang/String;)Landroid/database/Cursor;
    //   68: astore 4
    //   70: aload 4
    //   72: ifnull +29 -> 101
    //   75: aload 4
    //   77: invokeinterface 228 1 0
    //   82: ifne +22 -> 104
    //   85: aload_0
    //   86: getfield 56	com/lidroid/xutils/DbUtils:findTempCache	Lcom/lidroid/xutils/DbUtils$FindTempCache;
    //   89: aload 5
    //   91: aload 6
    //   93: invokevirtual 483	com/lidroid/xutils/DbUtils$FindTempCache:put	(Ljava/lang/String;Ljava/lang/Object;)V
    //   96: aload 4
    //   98: invokestatic 238	com/lidroid/xutils/util/IOUtils:closeQuietly	(Landroid/database/Cursor;)V
    //   101: aload 6
    //   103: areturn
    //   104: aload 6
    //   106: aload_0
    //   107: aload 4
    //   109: aload_1
    //   110: invokevirtual 320	com/lidroid/xutils/db/sqlite/Selector:getEntityType	()Ljava/lang/Class;
    //   113: lload_2
    //   114: invokestatic 489	com/lidroid/xutils/db/sqlite/CursorUtils:getEntity	(Lcom/lidroid/xutils/DbUtils;Landroid/database/Cursor;Ljava/lang/Class;J)Ljava/lang/Object;
    //   117: invokeinterface 492 2 0
    //   122: pop
    //   123: goto -48 -> 75
    //   126: astore_1
    //   127: new 161	com/lidroid/xutils/exception/DbException
    //   130: dup
    //   131: aload_1
    //   132: invokespecial 241	com/lidroid/xutils/exception/DbException:<init>	(Ljava/lang/Throwable;)V
    //   135: athrow
    //   136: astore_1
    //   137: aload 4
    //   139: invokestatic 238	com/lidroid/xutils/util/IOUtils:closeQuietly	(Landroid/database/Cursor;)V
    //   142: aload_1
    //   143: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	144	0	this	DbUtils
    //   0	144	1	paramSelector	Selector
    //   22	92	2	l	long
    //   40	98	4	localObject	Object
    //   17	73	5	str	String
    //   60	45	6	localArrayList	java.util.ArrayList
    // Exception table:
    //   from	to	target	type
    //   75	96	126	java/lang/Throwable
    //   104	123	126	java/lang/Throwable
    //   75	96	136	finally
    //   104	123	136	finally
    //   127	136	136	finally
  }
  
  public <T> List<T> findAll(Class<T> paramClass)
    throws DbException
  {
    return findAll(Selector.from(paramClass));
  }
  
  public <T> T findById(Class<T> paramClass, Object paramObject)
    throws DbException
  {
    if (!tableIsExist(paramClass)) {
      paramObject = null;
    }
    Object localObject;
    String str;
    long l;
    do
    {
      return (T)paramObject;
      localObject = Table.get(this, paramClass);
      str = Selector.from(paramClass).where(((Table)localObject).id.getColumnName(), "=", paramObject).limit(1).toString();
      l = CursorUtils.FindCacheSequence.getSeq();
      this.findTempCache.setSeq(l);
      localObject = this.findTempCache.get(str);
      paramObject = localObject;
    } while (localObject != null);
    paramObject = execQuery(str);
    if (paramObject != null)
    {
      try
      {
        if (((Cursor)paramObject).moveToNext())
        {
          paramClass = CursorUtils.getEntity(this, (Cursor)paramObject, paramClass, l);
          this.findTempCache.put(str, paramClass);
          return paramClass;
        }
      }
      catch (Throwable paramClass)
      {
        throw new DbException(paramClass);
      }
      finally
      {
        IOUtils.closeQuietly((Cursor)paramObject);
      }
      IOUtils.closeQuietly((Cursor)paramObject);
    }
    return null;
  }
  
  /* Error */
  public List<DbModel> findDbModelAll(DbModelSelector paramDbModelSelector)
    throws DbException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 515	com/lidroid/xutils/db/sqlite/DbModelSelector:getEntityType	()Ljava/lang/Class;
    //   5: invokevirtual 324	com/lidroid/xutils/DbUtils:tableIsExist	(Ljava/lang/Class;)Z
    //   8: ifne +7 -> 15
    //   11: aconst_null
    //   12: astore_1
    //   13: aload_1
    //   14: areturn
    //   15: new 479	java/util/ArrayList
    //   18: dup
    //   19: invokespecial 480	java/util/ArrayList:<init>	()V
    //   22: astore_3
    //   23: aload_0
    //   24: aload_1
    //   25: invokevirtual 516	com/lidroid/xutils/db/sqlite/DbModelSelector:toString	()Ljava/lang/String;
    //   28: invokevirtual 223	com/lidroid/xutils/DbUtils:execQuery	(Ljava/lang/String;)Landroid/database/Cursor;
    //   31: astore 4
    //   33: aload_3
    //   34: astore_1
    //   35: aload 4
    //   37: ifnull -24 -> 13
    //   40: aload 4
    //   42: invokeinterface 228 1 0
    //   47: istore_2
    //   48: iload_2
    //   49: ifne +10 -> 59
    //   52: aload 4
    //   54: invokestatic 238	com/lidroid/xutils/util/IOUtils:closeQuietly	(Landroid/database/Cursor;)V
    //   57: aload_3
    //   58: areturn
    //   59: aload_3
    //   60: aload 4
    //   62: invokestatic 520	com/lidroid/xutils/db/sqlite/CursorUtils:getDbModel	(Landroid/database/Cursor;)Lcom/lidroid/xutils/db/table/DbModel;
    //   65: invokeinterface 492 2 0
    //   70: pop
    //   71: goto -31 -> 40
    //   74: astore_1
    //   75: new 161	com/lidroid/xutils/exception/DbException
    //   78: dup
    //   79: aload_1
    //   80: invokespecial 241	com/lidroid/xutils/exception/DbException:<init>	(Ljava/lang/Throwable;)V
    //   83: athrow
    //   84: astore_1
    //   85: aload 4
    //   87: invokestatic 238	com/lidroid/xutils/util/IOUtils:closeQuietly	(Landroid/database/Cursor;)V
    //   90: aload_1
    //   91: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	92	0	this	DbUtils
    //   0	92	1	paramDbModelSelector	DbModelSelector
    //   47	2	2	bool	boolean
    //   22	38	3	localArrayList	java.util.ArrayList
    //   31	55	4	localCursor	Cursor
    // Exception table:
    //   from	to	target	type
    //   40	48	74	java/lang/Throwable
    //   59	71	74	java/lang/Throwable
    //   40	48	84	finally
    //   59	71	84	finally
    //   75	84	84	finally
  }
  
  /* Error */
  public List<DbModel> findDbModelAll(SqlInfo paramSqlInfo)
    throws DbException
  {
    // Byte code:
    //   0: new 479	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 480	java/util/ArrayList:<init>	()V
    //   7: astore_3
    //   8: aload_0
    //   9: aload_1
    //   10: invokevirtual 524	com/lidroid/xutils/DbUtils:execQuery	(Lcom/lidroid/xutils/db/sqlite/SqlInfo;)Landroid/database/Cursor;
    //   13: astore_1
    //   14: aload_1
    //   15: ifnull +18 -> 33
    //   18: aload_1
    //   19: invokeinterface 228 1 0
    //   24: istore_2
    //   25: iload_2
    //   26: ifne +9 -> 35
    //   29: aload_1
    //   30: invokestatic 238	com/lidroid/xutils/util/IOUtils:closeQuietly	(Landroid/database/Cursor;)V
    //   33: aload_3
    //   34: areturn
    //   35: aload_3
    //   36: aload_1
    //   37: invokestatic 520	com/lidroid/xutils/db/sqlite/CursorUtils:getDbModel	(Landroid/database/Cursor;)Lcom/lidroid/xutils/db/table/DbModel;
    //   40: invokeinterface 492 2 0
    //   45: pop
    //   46: goto -28 -> 18
    //   49: astore_3
    //   50: new 161	com/lidroid/xutils/exception/DbException
    //   53: dup
    //   54: aload_3
    //   55: invokespecial 241	com/lidroid/xutils/exception/DbException:<init>	(Ljava/lang/Throwable;)V
    //   58: athrow
    //   59: astore_3
    //   60: aload_1
    //   61: invokestatic 238	com/lidroid/xutils/util/IOUtils:closeQuietly	(Landroid/database/Cursor;)V
    //   64: aload_3
    //   65: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	66	0	this	DbUtils
    //   0	66	1	paramSqlInfo	SqlInfo
    //   24	2	2	bool	boolean
    //   7	29	3	localArrayList	java.util.ArrayList
    //   49	6	3	localThrowable	Throwable
    //   59	6	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   18	25	49	java/lang/Throwable
    //   35	46	49	java/lang/Throwable
    //   18	25	59	finally
    //   35	46	59	finally
    //   50	59	59	finally
  }
  
  public DbModel findDbModelFirst(DbModelSelector paramDbModelSelector)
    throws DbException
  {
    if (!tableIsExist(paramDbModelSelector.getEntityType())) {}
    do
    {
      return null;
      paramDbModelSelector = execQuery(paramDbModelSelector.limit(1).toString());
    } while (paramDbModelSelector == null);
    try
    {
      if (paramDbModelSelector.moveToNext())
      {
        DbModel localDbModel = CursorUtils.getDbModel(paramDbModelSelector);
        return localDbModel;
      }
    }
    catch (Throwable localThrowable)
    {
      throw new DbException(localThrowable);
    }
    finally
    {
      IOUtils.closeQuietly(paramDbModelSelector);
    }
    IOUtils.closeQuietly(paramDbModelSelector);
    return null;
  }
  
  public DbModel findDbModelFirst(SqlInfo paramSqlInfo)
    throws DbException
  {
    paramSqlInfo = execQuery(paramSqlInfo);
    if (paramSqlInfo != null)
    {
      try
      {
        if (paramSqlInfo.moveToNext())
        {
          DbModel localDbModel = CursorUtils.getDbModel(paramSqlInfo);
          return localDbModel;
        }
      }
      catch (Throwable localThrowable)
      {
        throw new DbException(localThrowable);
      }
      finally
      {
        IOUtils.closeQuietly(paramSqlInfo);
      }
      IOUtils.closeQuietly(paramSqlInfo);
    }
    return null;
  }
  
  public <T> T findFirst(Selector paramSelector)
    throws DbException
  {
    if (!tableIsExist(paramSelector.getEntityType())) {
      localObject1 = null;
    }
    String str;
    long l;
    Object localObject2;
    do
    {
      return (T)localObject1;
      str = paramSelector.limit(1).toString();
      l = CursorUtils.FindCacheSequence.getSeq();
      this.findTempCache.setSeq(l);
      localObject2 = this.findTempCache.get(str);
      localObject1 = localObject2;
    } while (localObject2 != null);
    Object localObject1 = execQuery(str);
    if (localObject1 != null)
    {
      try
      {
        if (((Cursor)localObject1).moveToNext())
        {
          paramSelector = CursorUtils.getEntity(this, (Cursor)localObject1, paramSelector.getEntityType(), l);
          this.findTempCache.put(str, paramSelector);
          return paramSelector;
        }
      }
      catch (Throwable paramSelector)
      {
        throw new DbException(paramSelector);
      }
      finally
      {
        IOUtils.closeQuietly((Cursor)localObject1);
      }
      IOUtils.closeQuietly((Cursor)localObject1);
    }
    return null;
  }
  
  public <T> T findFirst(Class<T> paramClass)
    throws DbException
  {
    return (T)findFirst(Selector.from(paramClass));
  }
  
  public DaoConfig getDaoConfig()
  {
    return this.daoConfig;
  }
  
  public SQLiteDatabase getDatabase()
  {
    return this.database;
  }
  
  public void replace(Object paramObject)
    throws DbException
  {
    try
    {
      beginTransaction();
      createTableIfNotExist(paramObject.getClass());
      execNonQuery(SqlInfoBuilder.buildReplaceSqlInfo(this, paramObject));
      setTransactionSuccessful();
      return;
    }
    finally
    {
      endTransaction();
    }
  }
  
  /* Error */
  public void replaceAll(List<?> paramList)
    throws DbException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +12 -> 13
    //   4: aload_1
    //   5: invokeinterface 389 1 0
    //   10: ifne +4 -> 14
    //   13: return
    //   14: aload_0
    //   15: invokespecial 371	com/lidroid/xutils/DbUtils:beginTransaction	()V
    //   18: aload_0
    //   19: aload_1
    //   20: iconst_0
    //   21: invokeinterface 392 2 0
    //   26: invokevirtual 248	java/lang/Object:getClass	()Ljava/lang/Class;
    //   29: invokevirtual 543	com/lidroid/xutils/DbUtils:createTableIfNotExist	(Ljava/lang/Class;)V
    //   32: aload_1
    //   33: invokeinterface 396 1 0
    //   38: astore_1
    //   39: aload_1
    //   40: invokeinterface 401 1 0
    //   45: ifne +12 -> 57
    //   48: aload_0
    //   49: invokespecial 376	com/lidroid/xutils/DbUtils:setTransactionSuccessful	()V
    //   52: aload_0
    //   53: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   56: return
    //   57: aload_0
    //   58: aload_0
    //   59: aload_1
    //   60: invokeinterface 405 1 0
    //   65: invokestatic 298	com/lidroid/xutils/db/sqlite/SqlInfoBuilder:buildReplaceSqlInfo	(Lcom/lidroid/xutils/DbUtils;Ljava/lang/Object;)Lcom/lidroid/xutils/db/sqlite/SqlInfo;
    //   68: invokevirtual 272	com/lidroid/xutils/DbUtils:execNonQuery	(Lcom/lidroid/xutils/db/sqlite/SqlInfo;)V
    //   71: goto -32 -> 39
    //   74: astore_1
    //   75: aload_0
    //   76: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   79: aload_1
    //   80: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	81	0	this	DbUtils
    //   0	81	1	paramList	List<?>
    // Exception table:
    //   from	to	target	type
    //   14	39	74	finally
    //   39	52	74	finally
    //   57	71	74	finally
  }
  
  public void save(Object paramObject)
    throws DbException
  {
    try
    {
      beginTransaction();
      createTableIfNotExist(paramObject.getClass());
      execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(this, paramObject));
      setTransactionSuccessful();
      return;
    }
    finally
    {
      endTransaction();
    }
  }
  
  /* Error */
  public void saveAll(List<?> paramList)
    throws DbException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +12 -> 13
    //   4: aload_1
    //   5: invokeinterface 389 1 0
    //   10: ifne +4 -> 14
    //   13: return
    //   14: aload_0
    //   15: invokespecial 371	com/lidroid/xutils/DbUtils:beginTransaction	()V
    //   18: aload_0
    //   19: aload_1
    //   20: iconst_0
    //   21: invokeinterface 392 2 0
    //   26: invokevirtual 248	java/lang/Object:getClass	()Ljava/lang/Class;
    //   29: invokevirtual 543	com/lidroid/xutils/DbUtils:createTableIfNotExist	(Ljava/lang/Class;)V
    //   32: aload_1
    //   33: invokeinterface 396 1 0
    //   38: astore_1
    //   39: aload_1
    //   40: invokeinterface 401 1 0
    //   45: ifne +12 -> 57
    //   48: aload_0
    //   49: invokespecial 376	com/lidroid/xutils/DbUtils:setTransactionSuccessful	()V
    //   52: aload_0
    //   53: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   56: return
    //   57: aload_0
    //   58: aload_0
    //   59: aload_1
    //   60: invokeinterface 405 1 0
    //   65: invokestatic 268	com/lidroid/xutils/db/sqlite/SqlInfoBuilder:buildInsertSqlInfo	(Lcom/lidroid/xutils/DbUtils;Ljava/lang/Object;)Lcom/lidroid/xutils/db/sqlite/SqlInfo;
    //   68: invokevirtual 272	com/lidroid/xutils/DbUtils:execNonQuery	(Lcom/lidroid/xutils/db/sqlite/SqlInfo;)V
    //   71: goto -32 -> 39
    //   74: astore_1
    //   75: aload_0
    //   76: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   79: aload_1
    //   80: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	81	0	this	DbUtils
    //   0	81	1	paramList	List<?>
    // Exception table:
    //   from	to	target	type
    //   14	39	74	finally
    //   39	52	74	finally
    //   57	71	74	finally
  }
  
  public boolean saveBindingId(Object paramObject)
    throws DbException
  {
    try
    {
      beginTransaction();
      createTableIfNotExist(paramObject.getClass());
      boolean bool = saveBindingIdWithoutTransaction(paramObject);
      setTransactionSuccessful();
      return bool;
    }
    finally
    {
      endTransaction();
    }
  }
  
  public void saveBindingIdAll(List<?> paramList)
    throws DbException
  {
    if ((paramList == null) || (paramList.size() == 0)) {
      return;
    }
    try
    {
      beginTransaction();
      createTableIfNotExist(paramList.get(0).getClass());
      paramList = paramList.iterator();
      do
      {
        if (!paramList.hasNext())
        {
          setTransactionSuccessful();
          return;
        }
      } while (saveBindingIdWithoutTransaction(paramList.next()));
      throw new DbException("saveBindingId error, transaction will not commit!");
    }
    finally
    {
      endTransaction();
    }
  }
  
  public void saveOrUpdate(Object paramObject)
    throws DbException
  {
    try
    {
      beginTransaction();
      createTableIfNotExist(paramObject.getClass());
      saveOrUpdateWithoutTransaction(paramObject);
      setTransactionSuccessful();
      return;
    }
    finally
    {
      endTransaction();
    }
  }
  
  /* Error */
  public void saveOrUpdateAll(List<?> paramList)
    throws DbException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +12 -> 13
    //   4: aload_1
    //   5: invokeinterface 389 1 0
    //   10: ifne +4 -> 14
    //   13: return
    //   14: aload_0
    //   15: invokespecial 371	com/lidroid/xutils/DbUtils:beginTransaction	()V
    //   18: aload_0
    //   19: aload_1
    //   20: iconst_0
    //   21: invokeinterface 392 2 0
    //   26: invokevirtual 248	java/lang/Object:getClass	()Ljava/lang/Class;
    //   29: invokevirtual 543	com/lidroid/xutils/DbUtils:createTableIfNotExist	(Ljava/lang/Class;)V
    //   32: aload_1
    //   33: invokeinterface 396 1 0
    //   38: astore_1
    //   39: aload_1
    //   40: invokeinterface 401 1 0
    //   45: ifne +12 -> 57
    //   48: aload_0
    //   49: invokespecial 376	com/lidroid/xutils/DbUtils:setTransactionSuccessful	()V
    //   52: aload_0
    //   53: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   56: return
    //   57: aload_0
    //   58: aload_1
    //   59: invokeinterface 405 1 0
    //   64: invokespecial 554	com/lidroid/xutils/DbUtils:saveOrUpdateWithoutTransaction	(Ljava/lang/Object;)V
    //   67: goto -28 -> 39
    //   70: astore_1
    //   71: aload_0
    //   72: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   75: aload_1
    //   76: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	77	0	this	DbUtils
    //   0	77	1	paramList	List<?>
    // Exception table:
    //   from	to	target	type
    //   14	39	70	finally
    //   39	52	70	finally
    //   57	67	70	finally
  }
  
  public boolean tableIsExist(Class<?> paramClass)
    throws DbException
  {
    Table localTable = Table.get(this, paramClass);
    if (localTable.isCheckedDatabase()) {
      return true;
    }
    paramClass = execQuery("SELECT COUNT(*) AS c FROM sqlite_master WHERE type='table' AND name='" + localTable.tableName + "'");
    if (paramClass != null)
    {
      try
      {
        if ((paramClass.moveToNext()) && (paramClass.getInt(0) > 0))
        {
          localTable.setCheckedDatabase(true);
          return true;
        }
      }
      catch (Throwable localThrowable)
      {
        throw new DbException(localThrowable);
      }
      finally
      {
        IOUtils.closeQuietly(paramClass);
      }
      IOUtils.closeQuietly(paramClass);
    }
    return false;
  }
  
  public void update(Object paramObject, WhereBuilder paramWhereBuilder, String... paramVarArgs)
    throws DbException
  {
    if (!tableIsExist(paramObject.getClass())) {
      return;
    }
    try
    {
      beginTransaction();
      execNonQuery(SqlInfoBuilder.buildUpdateSqlInfo(this, paramObject, paramWhereBuilder, paramVarArgs));
      setTransactionSuccessful();
      return;
    }
    finally
    {
      endTransaction();
    }
  }
  
  public void update(Object paramObject, String... paramVarArgs)
    throws DbException
  {
    if (!tableIsExist(paramObject.getClass())) {
      return;
    }
    try
    {
      beginTransaction();
      execNonQuery(SqlInfoBuilder.buildUpdateSqlInfo(this, paramObject, paramVarArgs));
      setTransactionSuccessful();
      return;
    }
    finally
    {
      endTransaction();
    }
  }
  
  /* Error */
  public void updateAll(List<?> paramList, WhereBuilder paramWhereBuilder, String... paramVarArgs)
    throws DbException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +29 -> 30
    //   4: aload_1
    //   5: invokeinterface 389 1 0
    //   10: ifeq +20 -> 30
    //   13: aload_0
    //   14: aload_1
    //   15: iconst_0
    //   16: invokeinterface 392 2 0
    //   21: invokevirtual 248	java/lang/Object:getClass	()Ljava/lang/Class;
    //   24: invokevirtual 324	com/lidroid/xutils/DbUtils:tableIsExist	(Ljava/lang/Class;)Z
    //   27: ifne +4 -> 31
    //   30: return
    //   31: aload_0
    //   32: invokespecial 371	com/lidroid/xutils/DbUtils:beginTransaction	()V
    //   35: aload_1
    //   36: invokeinterface 396 1 0
    //   41: astore_1
    //   42: aload_1
    //   43: invokeinterface 401 1 0
    //   48: ifne +12 -> 60
    //   51: aload_0
    //   52: invokespecial 376	com/lidroid/xutils/DbUtils:setTransactionSuccessful	()V
    //   55: aload_0
    //   56: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   59: return
    //   60: aload_0
    //   61: aload_0
    //   62: aload_1
    //   63: invokeinterface 405 1 0
    //   68: aload_2
    //   69: aload_3
    //   70: invokestatic 574	com/lidroid/xutils/db/sqlite/SqlInfoBuilder:buildUpdateSqlInfo	(Lcom/lidroid/xutils/DbUtils;Ljava/lang/Object;Lcom/lidroid/xutils/db/sqlite/WhereBuilder;[Ljava/lang/String;)Lcom/lidroid/xutils/db/sqlite/SqlInfo;
    //   73: invokevirtual 272	com/lidroid/xutils/DbUtils:execNonQuery	(Lcom/lidroid/xutils/db/sqlite/SqlInfo;)V
    //   76: goto -34 -> 42
    //   79: astore_1
    //   80: aload_0
    //   81: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   84: aload_1
    //   85: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	86	0	this	DbUtils
    //   0	86	1	paramList	List<?>
    //   0	86	2	paramWhereBuilder	WhereBuilder
    //   0	86	3	paramVarArgs	String[]
    // Exception table:
    //   from	to	target	type
    //   31	42	79	finally
    //   42	55	79	finally
    //   60	76	79	finally
  }
  
  /* Error */
  public void updateAll(List<?> paramList, String... paramVarArgs)
    throws DbException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +29 -> 30
    //   4: aload_1
    //   5: invokeinterface 389 1 0
    //   10: ifeq +20 -> 30
    //   13: aload_0
    //   14: aload_1
    //   15: iconst_0
    //   16: invokeinterface 392 2 0
    //   21: invokevirtual 248	java/lang/Object:getClass	()Ljava/lang/Class;
    //   24: invokevirtual 324	com/lidroid/xutils/DbUtils:tableIsExist	(Ljava/lang/Class;)Z
    //   27: ifne +4 -> 31
    //   30: return
    //   31: aload_0
    //   32: invokespecial 371	com/lidroid/xutils/DbUtils:beginTransaction	()V
    //   35: aload_1
    //   36: invokeinterface 396 1 0
    //   41: astore_1
    //   42: aload_1
    //   43: invokeinterface 401 1 0
    //   48: ifne +12 -> 60
    //   51: aload_0
    //   52: invokespecial 376	com/lidroid/xutils/DbUtils:setTransactionSuccessful	()V
    //   55: aload_0
    //   56: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   59: return
    //   60: aload_0
    //   61: aload_0
    //   62: aload_1
    //   63: invokeinterface 405 1 0
    //   68: aload_2
    //   69: invokestatic 293	com/lidroid/xutils/db/sqlite/SqlInfoBuilder:buildUpdateSqlInfo	(Lcom/lidroid/xutils/DbUtils;Ljava/lang/Object;[Ljava/lang/String;)Lcom/lidroid/xutils/db/sqlite/SqlInfo;
    //   72: invokevirtual 272	com/lidroid/xutils/DbUtils:execNonQuery	(Lcom/lidroid/xutils/db/sqlite/SqlInfo;)V
    //   75: goto -33 -> 42
    //   78: astore_1
    //   79: aload_0
    //   80: invokespecial 377	com/lidroid/xutils/DbUtils:endTransaction	()V
    //   83: aload_1
    //   84: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	85	0	this	DbUtils
    //   0	85	1	paramList	List<?>
    //   0	85	2	paramVarArgs	String[]
    // Exception table:
    //   from	to	target	type
    //   31	42	78	finally
    //   42	55	78	finally
    //   60	75	78	finally
  }
  
  public static class DaoConfig
  {
    private Context context;
    private String dbDir;
    private String dbName = "xUtils.db";
    private DbUtils.DbUpgradeListener dbUpgradeListener;
    private int dbVersion = 1;
    
    public DaoConfig(Context paramContext)
    {
      this.context = paramContext.getApplicationContext();
    }
    
    public Context getContext()
    {
      return this.context;
    }
    
    public String getDbDir()
    {
      return this.dbDir;
    }
    
    public String getDbName()
    {
      return this.dbName;
    }
    
    public DbUtils.DbUpgradeListener getDbUpgradeListener()
    {
      return this.dbUpgradeListener;
    }
    
    public int getDbVersion()
    {
      return this.dbVersion;
    }
    
    public void setDbDir(String paramString)
    {
      this.dbDir = paramString;
    }
    
    public void setDbName(String paramString)
    {
      if (!TextUtils.isEmpty(paramString)) {
        this.dbName = paramString;
      }
    }
    
    public void setDbUpgradeListener(DbUtils.DbUpgradeListener paramDbUpgradeListener)
    {
      this.dbUpgradeListener = paramDbUpgradeListener;
    }
    
    public void setDbVersion(int paramInt)
    {
      this.dbVersion = paramInt;
    }
  }
  
  public static abstract interface DbUpgradeListener
  {
    public abstract void onUpgrade(DbUtils paramDbUtils, int paramInt1, int paramInt2);
  }
  
  private class FindTempCache
  {
    private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap();
    private long seq = 0L;
    
    private FindTempCache() {}
    
    public Object get(String paramString)
    {
      return this.cache.get(paramString);
    }
    
    public void put(String paramString, Object paramObject)
    {
      if ((paramString != null) && (paramObject != null)) {
        this.cache.put(paramString, paramObject);
      }
    }
    
    public void setSeq(long paramLong)
    {
      if (this.seq != paramLong)
      {
        this.cache.clear();
        this.seq = paramLong;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\lidroid\xutils\DbUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */