package com.zizun.cs.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.DbUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.utils.MyDbUtils;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.orm.annotation.ID;
import com.zizun.cs.orm.annotation.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.apache.commons.lang3.reflect.FieldUtils;

public class SqlUtil
{
  private String UpdateForLocalBeforeSql;
  private SqlType currentType;
  List<Field> fields = new Vector();
  private List<String> idNames = new ArrayList();
  private List<Object> idValus = new ArrayList();
  private List<Object> param = new Vector();
  private String sqlBuffer;
  private Object target;
  
  public SqlUtil(SqlType paramSqlType, Object paramObject)
  {
    this.target = paramObject;
    switch (paramSqlType)
    {
    default: 
      return;
    case DELETE: 
      this.currentType = SqlType.INSERT;
      createInsert();
      return;
    case INSERT: 
      this.currentType = SqlType.UPDATE;
      createUpdate();
      return;
    case UPDATE: 
      this.currentType = SqlType.UPDATELOCAL;
      createUpdateForLocal();
      return;
    }
    this.currentType = SqlType.DELETE;
    createDelete();
  }
  
  public SqlUtil(Class<?> paramClass)
  {
    Object localObject = getTableNameForClass(paramClass);
    getFields(paramClass);
    paramClass = new StringBuffer();
    paramClass.append("DELETE FROM ").append((String)localObject).append(" WHERE 1=1 ");
    localObject = this.fields.iterator();
    for (;;)
    {
      if (!((Iterator)localObject).hasNext())
      {
        this.sqlBuffer = paramClass.toString();
        return;
      }
      Field localField = (Field)((Iterator)localObject).next();
      if ((!Modifier.isStatic(localField.getModifiers())) && ((ID)localField.getAnnotation(ID.class) != null)) {
        paramClass.append("AND ").append(localField.getName()).append("=? ");
      }
    }
  }
  
  private void createDelete()
  {
    Object localObject = getTableName();
    getFields(this.target.getClass());
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("DELETE FROM ").append((String)localObject).append(" WHERE 1=1 ");
    localObject = this.fields.iterator();
    for (;;)
    {
      if (!((Iterator)localObject).hasNext())
      {
        this.sqlBuffer = localStringBuffer.toString();
        return;
      }
      Field localField = (Field)((Iterator)localObject).next();
      if (!Modifier.isStatic(localField.getModifiers()))
      {
        initIDNamesAndValues(localField);
        if ((ID)localField.getAnnotation(ID.class) != null)
        {
          localStringBuffer.append("AND ").append(localField.getName()).append("=? ");
          this.param.add(readField(localField));
        }
      }
    }
  }
  
  private void createInsert()
  {
    Object localObject = getTableName();
    getFields(this.target.getClass());
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("INSERT INTO ").append((String)localObject).append("(");
    localObject = this.fields.iterator();
    int i;
    int j;
    if (!((Iterator)localObject).hasNext())
    {
      i = localStringBuffer.length();
      localStringBuffer.delete(i - 1, i).append(")values(");
      j = this.param.size();
      i = 0;
    }
    for (;;)
    {
      if (i >= j)
      {
        localStringBuffer.append(")");
        this.sqlBuffer = localStringBuffer.toString();
        return;
        Field localField = (Field)((Iterator)localObject).next();
        if (Modifier.isStatic(localField.getModifiers())) {
          break;
        }
        initIDNamesAndValues(localField);
        localStringBuffer.append(localField.getName()).append(",");
        this.param.add(readField(localField));
        break;
      }
      if (i != 0) {
        localStringBuffer.append(",");
      }
      localStringBuffer.append("?");
      i += 1;
    }
  }
  
  private void createUpdate()
  {
    Object localObject = getTableName();
    getFields(this.target.getClass());
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("UPDATE ").append((String)localObject).append(" SET ");
    localObject = new StringBuffer();
    Vector localVector = new Vector();
    ((StringBuffer)localObject).append(" WHERE 1=1 ");
    Iterator localIterator = this.fields.iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        this.param.addAll(localVector);
        if (localStringBuffer.lastIndexOf(",") > 0) {
          localStringBuffer.deleteCharAt(localStringBuffer.lastIndexOf(","));
        }
        localStringBuffer.append((StringBuffer)localObject);
        this.sqlBuffer = localStringBuffer.toString();
        return;
      }
      Field localField = (Field)localIterator.next();
      if (!Modifier.isStatic(localField.getModifiers()))
      {
        initIDNamesAndValues(localField);
        if ((ID)localField.getAnnotation(ID.class) == null)
        {
          localStringBuffer.append(localField.getName()).append("=? ,");
          this.param.add(readField(localField));
        }
        else
        {
          ((StringBuffer)localObject).append("AND ").append(localField.getName()).append("=? ");
          localVector.add(readField(localField));
        }
      }
    }
  }
  
  private void createUpdateForLocal()
  {
    String str = getTableName();
    getFields(this.target.getClass());
    StringBuffer localStringBuffer1 = new StringBuffer();
    localStringBuffer1.append("UPDATE ").append(str).append(" SET ");
    StringBuffer localStringBuffer2 = new StringBuffer();
    StringBuffer localStringBuffer3 = new StringBuffer(" WHERE 1=1 ");
    Vector localVector = new Vector();
    localStringBuffer2.append(" WHERE 1=1 ");
    Iterator localIterator = this.fields.iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        this.param.addAll(localVector);
        if (localStringBuffer1.lastIndexOf(",") > 0) {
          localStringBuffer1.deleteCharAt(localStringBuffer1.lastIndexOf(","));
        }
        localStringBuffer1.append(localStringBuffer2);
        this.UpdateForLocalBeforeSql = ("UPDATE " + str + " SET  Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1" + " ELSE 2" + " END" + localStringBuffer3);
        this.sqlBuffer = localStringBuffer1.toString();
        return;
      }
      Field localField = (Field)localIterator.next();
      if ((!Modifier.isStatic(localField.getModifiers())) && (!localField.getName().equals("Sync_DataStatus")))
      {
        initIDNamesAndValues(localField);
        if ((ID)localField.getAnnotation(ID.class) == null)
        {
          localStringBuffer1.append(localField.getName()).append("=? ,");
          this.param.add(readField(localField));
        }
        else
        {
          localStringBuffer2.append(" AND ").append(localField.getName()).append("=? ");
          localVector.add(readField(localField));
          localStringBuffer3.append(" AND ").append(localField.getName()).append("=").append(readField(localField));
        }
      }
    }
  }
  
  private String getTableName()
  {
    return getTableNameForClass(this.target.getClass());
  }
  
  private String getTableNameForClass(Class<?> paramClass)
  {
    Object localObject = (Table)paramClass.getAnnotation(Table.class);
    if (localObject != null)
    {
      String str = ((Table)localObject).name();
      localObject = str;
      if ("".equalsIgnoreCase(str)) {
        localObject = paramClass.getSimpleName();
      }
      return (String)localObject;
    }
    return paramClass.getSimpleName();
  }
  
  private void initIDNamesAndValues(Field paramField)
  {
    if ((ID)paramField.getAnnotation(ID.class) != null)
    {
      this.idNames.add(paramField.getName());
      this.idValus.add(readField(paramField));
    }
  }
  
  public static void insert(Context paramContext, Object paramObject)
  {
    S_User localS_User = UserManager.getInstance().getCurrentUser();
    if (localS_User != null)
    {
      paramContext = MyDbUtils.getDbUtils(paramContext, localS_User.getMerchant_ID()).getDatabase();
      paramObject = new SqlUtil(SqlType.INSERT, paramObject);
      paramContext.execSQL(((SqlUtil)paramObject).getSqlBuffer(), ((SqlUtil)paramObject).getParam().toArray());
    }
  }
  
  protected void getFields(Class<?> paramClass)
  {
    if (Object.class.equals(paramClass)) {
      return;
    }
    Field[] arrayOfField = paramClass.getDeclaredFields();
    int j = arrayOfField.length;
    int i = 0;
    for (;;)
    {
      if (i >= j)
      {
        getFields(paramClass.getSuperclass());
        return;
      }
      Field localField = arrayOfField[i];
      this.fields.add(localField);
      i += 1;
    }
  }
  
  public List<String> getIdNames()
  {
    return this.idNames;
  }
  
  public List<Object> getIdValus()
  {
    return this.idValus;
  }
  
  public List<Object> getParam()
  {
    return this.param;
  }
  
  public String getSqlBuffer()
  {
    return this.sqlBuffer;
  }
  
  public String getUpdateForLocalBeforeSql()
  {
    return this.UpdateForLocalBeforeSql;
  }
  
  protected Object readField(Field paramField)
  {
    try
    {
      paramField = FieldUtils.readField(paramField, this.target, true);
      return paramField;
    }
    catch (Exception paramField)
    {
      throw new RuntimeException(this.currentType.name(), paramField);
    }
  }
  
  public static enum SqlType
  {
    INSERT,  UPDATE,  UPDATELOCAL,  DELETE;
  }
}