package com.zizun.cs.orm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.orm.converter.ColumnConverter;
import com.zizun.cs.orm.converter.ColumnConverterFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SQLiteUtil
{
  private static final HashMap<Class<?>, SQLiteType> TYPE_MAP = new HashMap() {};
  
  public static <T> List<T> getQueryList(SQLiteDatabase paramSQLiteDatabase, String paramString, Class<T> paramClass)
    throws IllegalAccessException, InstantiationException, ClassNotFoundException
  {
    LogUtils.i(paramString);
    Iterator localIterator = null;
    localObject1 = null;
    for (;;)
    {
      try
      {
        paramSQLiteDatabase = paramSQLiteDatabase.rawQuery(paramString, null);
        paramString = localIterator;
        if (paramSQLiteDatabase != null)
        {
          paramString = localIterator;
          localObject1 = paramSQLiteDatabase;
          if (paramSQLiteDatabase.moveToFirst())
          {
            localObject1 = paramSQLiteDatabase;
            paramString = new ArrayList();
          }
        }
        try
        {
          localObject1 = paramClass.newInstance();
          localIterator = ReflectUtil.getFieldList(paramClass).iterator();
          if (!localIterator.hasNext())
          {
            paramString.add(localObject1);
            boolean bool = paramSQLiteDatabase.moveToNext();
            if (bool) {
              continue;
            }
            if ((paramSQLiteDatabase != null) && (!paramSQLiteDatabase.isClosed()))
            {
              paramSQLiteDatabase.deactivate();
              paramSQLiteDatabase.close();
            }
            return paramString;
          }
          localField = (Field)localIterator.next();
          if (Modifier.isStatic(localField.getModifiers())) {
            continue;
          }
          localField.setAccessible(true);
          Object localObject2 = localField.getType();
          int i = paramSQLiteDatabase.getColumnIndex(localField.getName());
          if (i < 0) {
            continue;
          }
          localObject2 = ColumnConverterFactory.getColumnConverter((Class)localObject2).getFieldValue(paramSQLiteDatabase, i);
          if (localObject2 == null) {
            continue;
          }
          localField.set(localObject1, localObject2);
          continue;
          if (paramSQLiteDatabase == null) {
            continue;
          }
        }
        finally {}
      }
      finally
      {
        Field localField;
        paramSQLiteDatabase = (SQLiteDatabase)localObject1;
        continue;
      }
      if (!paramSQLiteDatabase.isClosed())
      {
        paramSQLiteDatabase.deactivate();
        paramSQLiteDatabase.close();
      }
      throw paramString;
      LogUtils.i(localField.getName() + "不存在！");
    }
  }
  
  public static List<?> getQueryList(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    throws IllegalAccessException, InstantiationException, ClassNotFoundException
  {
    LogUtils.i(paramString1);
    ArrayList localArrayList = new ArrayList();
    SQLiteDatabase localSQLiteDatabase = null;
    for (;;)
    {
      Iterator localIterator;
      try
      {
        paramSQLiteDatabase = paramSQLiteDatabase.rawQuery(paramString1, null);
        localSQLiteDatabase = paramSQLiteDatabase;
        paramString1 = Class.forName(paramString2);
        localSQLiteDatabase = paramSQLiteDatabase;
        boolean bool = paramSQLiteDatabase.moveToNext();
        if (!bool) {
          return localArrayList;
        }
        localSQLiteDatabase = paramSQLiteDatabase;
        paramString2 = paramString1.newInstance();
        localSQLiteDatabase = paramSQLiteDatabase;
        localIterator = ReflectUtil.getFieldList(paramString1).iterator();
        localSQLiteDatabase = paramSQLiteDatabase;
        if (!localIterator.hasNext())
        {
          localSQLiteDatabase = paramSQLiteDatabase;
          localArrayList.add(paramString2);
          continue;
        }
        localSQLiteDatabase = paramSQLiteDatabase;
      }
      finally
      {
        if (localSQLiteDatabase != null) {
          localSQLiteDatabase.close();
        }
      }
      Field localField = (Field)localIterator.next();
      localSQLiteDatabase = paramSQLiteDatabase;
      if (!Modifier.isStatic(localField.getModifiers()))
      {
        localSQLiteDatabase = paramSQLiteDatabase;
        localField.setAccessible(true);
        localSQLiteDatabase = paramSQLiteDatabase;
        Object localObject = localField.getType();
        localSQLiteDatabase = paramSQLiteDatabase;
        int i = paramSQLiteDatabase.getColumnIndex(localField.getName());
        if (i >= 0)
        {
          localSQLiteDatabase = paramSQLiteDatabase;
          localObject = ColumnConverterFactory.getColumnConverter((Class)localObject).getFieldValue(paramSQLiteDatabase, i);
          if (localObject != null)
          {
            localSQLiteDatabase = paramSQLiteDatabase;
            localField.set(paramString2, localObject);
          }
        }
        else
        {
          localSQLiteDatabase = paramSQLiteDatabase;
          LogUtils.i(localField.getName() + "不存在！");
        }
      }
    }
  }
  
  public static StringBuffer initSqlBufferForExist(String paramString, List<String> paramList, List<Object> paramList1)
  {
    paramString = new StringBuffer("Select * From " + paramString + " Where 1=1 ");
    int i = 0;
    if (i >= paramList.size())
    {
      LogUtils.i(paramString.toString());
      return paramString;
    }
    if ((paramList1.get(i) instanceof String)) {
      paramString.append(" and " + (String)paramList.get(i) + " = " + " '" + paramList1.get(i) + "' ");
    }
    for (;;)
    {
      i += 1;
      break;
      if ((paramList1.get(i) instanceof Number)) {
        paramString.append(" and " + (String)paramList.get(i) + " = " + paramList1.get(i));
      }
    }
  }
  
  public static void insertOrUpdateTable(SQLiteDatabase paramSQLiteDatabase, Object paramObject)
  {
    String str = paramObject.getClass().getSimpleName();
    SqlUtil localSqlUtil = new SqlUtil(SqlUtil.SqlType.INSERT, paramObject);
    if (!isTargetExist(paramSQLiteDatabase, initSqlBufferForExist(str, localSqlUtil.getIdNames(), localSqlUtil.getIdValus()).toString()))
    {
      paramSQLiteDatabase.execSQL(localSqlUtil.getSqlBuffer().toString(), localSqlUtil.getParam().toArray());
      return;
    }
    paramObject = new SqlUtil(SqlUtil.SqlType.UPDATE, paramObject);
    paramSQLiteDatabase.execSQL(((SqlUtil)paramObject).getSqlBuffer().toString(), ((SqlUtil)paramObject).getParam().toArray());
  }
  
  public static void insertOrUpdateTable(SQLiteDatabase paramSQLiteDatabase, String paramString, Object paramObject)
  {
    SqlUtil localSqlUtil = new SqlUtil(SqlUtil.SqlType.INSERT, paramObject);
    if (!isTargetExist(paramSQLiteDatabase, initSqlBufferForExist(paramString, localSqlUtil.getIdNames(), localSqlUtil.getIdValus()).toString()))
    {
      paramSQLiteDatabase.execSQL(localSqlUtil.getSqlBuffer().toString(), localSqlUtil.getParam().toArray());
      return;
    }
    paramString = new SqlUtil(SqlUtil.SqlType.UPDATELOCAL, paramObject);
    paramSQLiteDatabase.execSQL(paramString.getUpdateForLocalBeforeSql());
    paramSQLiteDatabase.execSQL(paramString.getSqlBuffer().toString(), paramString.getParam().toArray());
  }
  
  public static void insertOrUpdateTableLocal(SQLiteDatabase paramSQLiteDatabase, Object paramObject)
  {
    String str = paramObject.getClass().getSimpleName();
    SqlUtil localSqlUtil = new SqlUtil(SqlUtil.SqlType.INSERT, paramObject);
    if (!isTargetExist(paramSQLiteDatabase, initSqlBufferForExist(str, localSqlUtil.getIdNames(), localSqlUtil.getIdValus()).toString()))
    {
      paramSQLiteDatabase.execSQL(localSqlUtil.getSqlBuffer().toString(), localSqlUtil.getParam().toArray());
      LogUtils.i(localSqlUtil.getSqlBuffer().toString() + localSqlUtil.getParam().toString());
      return;
    }
    paramObject = new SqlUtil(SqlUtil.SqlType.UPDATELOCAL, paramObject);
    paramSQLiteDatabase.execSQL(((SqlUtil)paramObject).getUpdateForLocalBeforeSql());
    paramSQLiteDatabase.execSQL(((SqlUtil)paramObject).getSqlBuffer().toString(), ((SqlUtil)paramObject).getParam().toArray());
  }
  
  public static boolean isTargetExist(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = null;
    try
    {
      paramSQLiteDatabase = paramSQLiteDatabase.rawQuery(paramString, null);
      localSQLiteDatabase = paramSQLiteDatabase;
      boolean bool = paramSQLiteDatabase.moveToNext();
      if (bool) {
        return true;
      }
    }
    finally
    {
      if (localSQLiteDatabase != null) {
        localSQLiteDatabase.close();
      }
    }
    if (paramSQLiteDatabase != null) {
      paramSQLiteDatabase.close();
    }
    return false;
  }
  
  public <T> SQLiteType getSQliteType(Class<T> paramClass)
  {
    if (TYPE_MAP.containsKey(paramClass)) {
      return (SQLiteType)TYPE_MAP.get(paramClass);
    }
    Log.i("reflect:", "this data type not support yet!" + paramClass.getName());
    return null;
  }
  
  public static enum SQLiteType
  {
    INTEGER,  REAL,  TEXT,  BLOB;
  }
}