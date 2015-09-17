package com.zizun.cs.biz.dao.trans.impl;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.impl.S_Sync_UploadDaoImpl;
import com.zizun.cs.biz.dao.impl.VoucherDetailDaoImpl;
import com.zizun.cs.biz.dao.impl.VoucherHeaderDaoImpl;
import com.zizun.cs.biz.dao.trans.VoucherTrans;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.VoucherDetail;
import com.zizun.cs.entities.VoucherHeader;
import com.zizun.cs.entities.biz.CreateExpenseEntity;
import com.zizun.cs.entities.biz.Expense;
import com.zizun.cs.orm.SqlUtil;
import com.zizun.cs.orm.SqlUtil.SqlType;
import java.sql.Timestamp;

public class VoucherTransImpl
  extends BaseTransaction
  implements VoucherTrans
{
  private static final String SQL_DELETE_VOUCHER = "UPDATE VoucherHeader SET VH_Status = 2,Modify_DT = ?,Modify_ID = ?, Sync_DataType = 2,Sync_Status = 1 WHERE VH_ID = ?";
  
  public boolean CreateExpenseTransaction(Expense paramExpense, boolean paramBoolean)
  {
    Object localObject = new CreateExpenseEntity(paramExpense, paramBoolean);
    paramExpense = ((CreateExpenseEntity)localObject).getVoucherHeader();
    localObject = ((CreateExpenseEntity)localObject).getVoucherDetail();
    new VoucherDetailDaoImpl().InsertVoucherDetail((VoucherDetail)localObject);
    new VoucherHeaderDaoImpl().InsertVoucherHeader(paramExpense);
    return true;
  }
  
  /* Error */
  public boolean ReceiveOweTransaction(com.zizun.cs.ui.entity.PayOweEntity paramPayOweEntity)
  {
    // Byte code:
    //   0: new 50	com/zizun/cs/entities/biz/VoucherReceiveEntity
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 53	com/zizun/cs/entities/biz/VoucherReceiveEntity:<init>	(Lcom/zizun/cs/ui/entity/PayOweEntity;)V
    //   8: astore_1
    //   9: aload_1
    //   10: invokevirtual 54	com/zizun/cs/entities/biz/VoucherReceiveEntity:getVoucherHeader	()Lcom/zizun/cs/entities/VoucherHeader;
    //   13: astore_3
    //   14: new 56	com/zizun/cs/orm/SqlUtil
    //   17: dup
    //   18: getstatic 62	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   21: aload_3
    //   22: invokespecial 65	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   25: astore_3
    //   26: new 67	java/lang/StringBuilder
    //   29: dup
    //   30: ldc 69
    //   32: invokespecial 72	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   35: aload_3
    //   36: invokevirtual 76	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   39: invokevirtual 81	java/lang/String:toString	()Ljava/lang/String;
    //   42: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: aload_3
    //   46: invokevirtual 89	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   49: invokevirtual 92	java/lang/Object:toString	()Ljava/lang/String;
    //   52: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: invokevirtual 93	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   58: invokestatic 98	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   61: aload_1
    //   62: invokevirtual 102	com/zizun/cs/entities/biz/VoucherReceiveEntity:getVoucherDetailList	()Ljava/util/ArrayList;
    //   65: astore_1
    //   66: new 104	java/util/ArrayList
    //   69: dup
    //   70: invokespecial 105	java/util/ArrayList:<init>	()V
    //   73: astore 4
    //   75: iconst_0
    //   76: istore_2
    //   77: iload_2
    //   78: aload_1
    //   79: invokevirtual 109	java/util/ArrayList:size	()I
    //   82: if_icmplt +100 -> 182
    //   85: aload_0
    //   86: invokevirtual 113	com/zizun/cs/biz/dao/trans/impl/VoucherTransImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   89: astore_1
    //   90: aload_1
    //   91: invokevirtual 118	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   94: aload_1
    //   95: aload_3
    //   96: invokevirtual 76	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   99: invokevirtual 81	java/lang/String:toString	()Ljava/lang/String;
    //   102: aload_3
    //   103: invokevirtual 89	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   106: invokeinterface 124 1 0
    //   111: invokevirtual 128	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   114: new 130	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl
    //   117: dup
    //   118: invokespecial 131	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:<init>	()V
    //   121: astore_3
    //   122: aload_3
    //   123: new 133	com/zizun/cs/entities/S_Sync_Upload
    //   126: dup
    //   127: ldc -121
    //   129: invokevirtual 140	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   132: invokespecial 141	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   135: invokevirtual 145	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   138: pop
    //   139: iconst_0
    //   140: istore_2
    //   141: iload_2
    //   142: aload 4
    //   144: invokevirtual 109	java/util/ArrayList:size	()I
    //   147: if_icmplt +63 -> 210
    //   150: aload_3
    //   151: new 133	com/zizun/cs/entities/S_Sync_Upload
    //   154: dup
    //   155: ldc -109
    //   157: invokevirtual 140	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   160: invokespecial 141	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   163: invokevirtual 145	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   166: pop
    //   167: aload_1
    //   168: invokevirtual 150	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   171: ldc -104
    //   173: invokestatic 98	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   176: aload_1
    //   177: invokevirtual 155	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   180: iconst_1
    //   181: ireturn
    //   182: aload 4
    //   184: new 56	com/zizun/cs/orm/SqlUtil
    //   187: dup
    //   188: getstatic 62	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   191: aload_1
    //   192: iload_2
    //   193: invokevirtual 159	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   196: invokespecial 65	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   199: invokevirtual 163	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   202: pop
    //   203: iload_2
    //   204: iconst_1
    //   205: iadd
    //   206: istore_2
    //   207: goto -130 -> 77
    //   210: aload_1
    //   211: aload 4
    //   213: iload_2
    //   214: invokevirtual 159	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   217: checkcast 56	com/zizun/cs/orm/SqlUtil
    //   220: invokevirtual 76	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   223: invokevirtual 81	java/lang/String:toString	()Ljava/lang/String;
    //   226: aload 4
    //   228: iload_2
    //   229: invokevirtual 159	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   232: checkcast 56	com/zizun/cs/orm/SqlUtil
    //   235: invokevirtual 89	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   238: invokeinterface 124 1 0
    //   243: invokevirtual 128	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   246: iload_2
    //   247: iconst_1
    //   248: iadd
    //   249: istore_2
    //   250: goto -109 -> 141
    //   253: astore_3
    //   254: aload_3
    //   255: invokevirtual 166	android/database/SQLException:printStackTrace	()V
    //   258: aload_1
    //   259: invokevirtual 155	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   262: iconst_0
    //   263: ireturn
    //   264: astore_3
    //   265: aload_1
    //   266: invokevirtual 155	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   269: aload_3
    //   270: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	271	0	this	VoucherTransImpl
    //   0	271	1	paramPayOweEntity	com.zizun.cs.ui.entity.PayOweEntity
    //   76	174	2	i	int
    //   13	138	3	localObject1	Object
    //   253	2	3	localSQLException	SQLException
    //   264	6	3	localObject2	Object
    //   73	154	4	localArrayList	java.util.ArrayList
    // Exception table:
    //   from	to	target	type
    //   94	139	253	android/database/SQLException
    //   141	176	253	android/database/SQLException
    //   210	246	253	android/database/SQLException
    //   94	139	264	finally
    //   141	176	264	finally
    //   210	246	264	finally
    //   254	258	264	finally
  }
  
  public boolean deleteVouchTransaction(long paramLong)
  {
    Timestamp localTimestamp = DateTimeUtil.getCurrentTime();
    int i = UserManager.getInstance().getCurrentUser().getUser_ID();
    SQLiteDatabase localSQLiteDatabase = getDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      VoucherHeader localVoucherHeader = new VoucherHeader();
      localVoucherHeader.setVH_ID(paramLong);
      localSQLiteDatabase.execSQL(new SqlUtil(SqlUtil.SqlType.UPDATELOCAL, localVoucherHeader).getUpdateForLocalBeforeSql());
      localSQLiteDatabase.execSQL("UPDATE VoucherHeader SET VH_Status = 2,Modify_DT = ?,Modify_ID = ?, Sync_DataType = 2,Sync_Status = 1 WHERE VH_ID = ?", new Object[] { localTimestamp, Integer.valueOf(i), Long.valueOf(paramLong) });
      new S_Sync_UploadDaoImpl().insert(new S_Sync_Upload(VoucherHeader.class.getSimpleName()));
      localSQLiteDatabase.setTransactionSuccessful();
      LogUtils.i("交易凭证数据表删除事务成功!");
      return true;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      return false;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }
  
  /* Error */
  public boolean payOweTransaction(com.zizun.cs.ui.entity.PayOweEntity paramPayOweEntity)
  {
    // Byte code:
    //   0: new 218	com/zizun/cs/entities/biz/VoucherPayEntity
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 219	com/zizun/cs/entities/biz/VoucherPayEntity:<init>	(Lcom/zizun/cs/ui/entity/PayOweEntity;)V
    //   8: astore_1
    //   9: aload_1
    //   10: invokevirtual 220	com/zizun/cs/entities/biz/VoucherPayEntity:getVoucherHeader	()Lcom/zizun/cs/entities/VoucherHeader;
    //   13: astore_3
    //   14: new 56	com/zizun/cs/orm/SqlUtil
    //   17: dup
    //   18: getstatic 62	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   21: aload_3
    //   22: invokespecial 65	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   25: astore_3
    //   26: new 67	java/lang/StringBuilder
    //   29: dup
    //   30: ldc 69
    //   32: invokespecial 72	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   35: aload_3
    //   36: invokevirtual 76	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   39: invokevirtual 81	java/lang/String:toString	()Ljava/lang/String;
    //   42: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: aload_3
    //   46: invokevirtual 89	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   49: invokevirtual 92	java/lang/Object:toString	()Ljava/lang/String;
    //   52: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: invokevirtual 93	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   58: invokestatic 98	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   61: aload_1
    //   62: invokevirtual 221	com/zizun/cs/entities/biz/VoucherPayEntity:getVoucherDetailList	()Ljava/util/ArrayList;
    //   65: astore_1
    //   66: new 104	java/util/ArrayList
    //   69: dup
    //   70: invokespecial 105	java/util/ArrayList:<init>	()V
    //   73: astore 4
    //   75: iconst_0
    //   76: istore_2
    //   77: iload_2
    //   78: aload_1
    //   79: invokevirtual 109	java/util/ArrayList:size	()I
    //   82: if_icmplt +100 -> 182
    //   85: aload_0
    //   86: invokevirtual 113	com/zizun/cs/biz/dao/trans/impl/VoucherTransImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   89: astore_1
    //   90: aload_1
    //   91: invokevirtual 118	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   94: aload_1
    //   95: aload_3
    //   96: invokevirtual 76	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   99: invokevirtual 81	java/lang/String:toString	()Ljava/lang/String;
    //   102: aload_3
    //   103: invokevirtual 89	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   106: invokeinterface 124 1 0
    //   111: invokevirtual 128	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   114: new 130	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl
    //   117: dup
    //   118: invokespecial 131	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:<init>	()V
    //   121: astore_3
    //   122: aload_3
    //   123: new 133	com/zizun/cs/entities/S_Sync_Upload
    //   126: dup
    //   127: ldc -121
    //   129: invokevirtual 140	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   132: invokespecial 141	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   135: invokevirtual 145	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   138: pop
    //   139: iconst_0
    //   140: istore_2
    //   141: iload_2
    //   142: aload 4
    //   144: invokevirtual 109	java/util/ArrayList:size	()I
    //   147: if_icmplt +63 -> 210
    //   150: aload_3
    //   151: new 133	com/zizun/cs/entities/S_Sync_Upload
    //   154: dup
    //   155: ldc -109
    //   157: invokevirtual 140	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   160: invokespecial 141	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   163: invokevirtual 145	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   166: pop
    //   167: aload_1
    //   168: invokevirtual 150	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   171: ldc -33
    //   173: invokestatic 98	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   176: aload_1
    //   177: invokevirtual 155	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   180: iconst_1
    //   181: ireturn
    //   182: aload 4
    //   184: new 56	com/zizun/cs/orm/SqlUtil
    //   187: dup
    //   188: getstatic 62	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   191: aload_1
    //   192: iload_2
    //   193: invokevirtual 159	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   196: invokespecial 65	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   199: invokevirtual 163	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   202: pop
    //   203: iload_2
    //   204: iconst_1
    //   205: iadd
    //   206: istore_2
    //   207: goto -130 -> 77
    //   210: aload_1
    //   211: aload 4
    //   213: iload_2
    //   214: invokevirtual 159	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   217: checkcast 56	com/zizun/cs/orm/SqlUtil
    //   220: invokevirtual 76	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   223: invokevirtual 81	java/lang/String:toString	()Ljava/lang/String;
    //   226: aload 4
    //   228: iload_2
    //   229: invokevirtual 159	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   232: checkcast 56	com/zizun/cs/orm/SqlUtil
    //   235: invokevirtual 89	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   238: invokeinterface 124 1 0
    //   243: invokevirtual 128	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   246: iload_2
    //   247: iconst_1
    //   248: iadd
    //   249: istore_2
    //   250: goto -109 -> 141
    //   253: astore_3
    //   254: aload_3
    //   255: invokevirtual 166	android/database/SQLException:printStackTrace	()V
    //   258: aload_1
    //   259: invokevirtual 155	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   262: iconst_0
    //   263: ireturn
    //   264: astore_3
    //   265: aload_1
    //   266: invokevirtual 155	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   269: aload_3
    //   270: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	271	0	this	VoucherTransImpl
    //   0	271	1	paramPayOweEntity	com.zizun.cs.ui.entity.PayOweEntity
    //   76	174	2	i	int
    //   13	138	3	localObject1	Object
    //   253	2	3	localSQLException	SQLException
    //   264	6	3	localObject2	Object
    //   73	154	4	localArrayList	java.util.ArrayList
    // Exception table:
    //   from	to	target	type
    //   94	139	253	android/database/SQLException
    //   141	176	253	android/database/SQLException
    //   210	246	253	android/database/SQLException
    //   94	139	264	finally
    //   141	176	264	finally
    //   210	246	264	finally
    //   254	258	264	finally
  }
}