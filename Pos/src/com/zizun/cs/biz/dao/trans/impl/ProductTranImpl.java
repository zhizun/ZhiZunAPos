package com.zizun.cs.biz.dao.trans.impl;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.lidroid.xutils.util.LogUtils;
import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.impl.S_Sync_UploadDaoImpl;
import com.zizun.cs.biz.dao.trans.ProductTrans;
import com.zizun.cs.biz.stockcal.manager.StockcalManager;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.entities.ProductStatusStore;
import com.zizun.cs.entities.S_Sync_Upload;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Stock_Detail;
import com.zizun.cs.entities.Stock_Header;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.TransactionLog;
import com.zizun.cs.entities.biz.DeleteProductEntity;
import com.zizun.cs.orm.SqlUtil;
import com.zizun.cs.orm.SqlUtil.SqlType;
import com.zizun.cs.ui.entity.Goods;
import java.util.List;

public class ProductTranImpl
  extends BaseTransaction
  implements ProductTrans
{
  private static final String SQL_DELETE_PRODUCT_STATUS_STORE = "UPDATE ProductStatusStore SET PSS_Status = 3, Modify_DT = ?,Modify_ID = ?,Sync_Status = 1 WHERE ProductSku_ID = ? AND LBU_ID = 1 AND Store_ID = ?";
  private static final String SQL_DELETE_PRODUCT_STATUS_STORE_SYNC = "UPDATE ProductStatusStore SET Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1 ELSE 2 END WHERE ProductSku_ID = ? AND LBU_ID = 1 AND Store_ID = ?";
  private static final String SQL_SELECT_PRICE = "SELECT * FROM V_ProductSku AS vp WHERE vp.ProductSku_ID = ?";
  private static final String SQL_SELECT_PRODUCT_ID = "SELECT pk.Product_ID FROM ProductSku AS pk where ProductSku_ID = ?";
  private static final String SQL_UPDATE_PRODUCT = "UPDATE Product SET Product_Code = ?, Product_Name = ?, Product_Group = ?, Modify_DT = ?, Modify_ID = ?, Sync_Status = ? WHERE 1=1 AND Product_ID = ?";
  private static final String SQL_UPDATE_PRODUCT_SKU = "UPDATE ProductSku SET ProductSku_Name = ?, PruductSku_Description = ?, ProductSku_Code = ?, ProductSku_BarCode = ?, ProductSku_Spec = ?,ProductSku_Image = ?,ProductSku_Unit = ?,PAG_ID = ?, Retail_Price = ?,WholeSale_Price = ?,Purchase_Price = ?,Fixed_Cost = ?, Modify_DT = ?,Modify_ID = ?,Sync_Status = ?   WHERE Product_ID = ?";
  private static final String SQL_UPDATE_PRODUCT_SKUPRICE = "UPDATE ProductSkuPrice SET Retail_Price = ?,WholeSale_Price = ?,Purchase_Price = ?,Fixed_Cost = ?,Modify_DT = ?,Modify_ID = ?, Sync_Status = ?WHERE PSP_ID = ?";
  private static final String SQL_UPDATE_PRODUCT_STATUS = "UPDATE Product SET  Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1 ELSE 2 END WHERE Product_ID = <Product_ID>";
  private static final String SQL_UPDATE_PRODUCT_STATUS_STORE = "UPDATE ProductStatusStore SET PSS_Status = ?, Modify_DT = ?,Modify_ID = ?,Sync_Status = 1 WHERE ProductSku_ID = ? AND LBU_ID = 1 AND Store_ID = ?";
  private static final String SQL_UPDATE_PRODUCT_STATUS_STORE_SYNC = "UPDATE ProductStatusStore SET Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1 ELSE 2 END WHERE ProductSku_ID = ? AND LBU_ID = 1 AND Store_ID = ?";
  private static final String SQL_UPDATE_SKU_STATUS = "UPDATE ProductSku SET  Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1 ELSE 2 END WHERE Product_ID = <Product_ID>";
  private final String SQL_IS_ProductStatusStore_EXIST = "SELECT pss.PSS_ID FROM ProductStatusStore AS pss WHERE pss.LBU_ID = 1 AND pss.ProductSku_ID = ? AND pss.Store_ID = ?";
  
  /* Error */
  public boolean createProductTransaction(com.zizun.cs.entities.biz.CreateProductEntity paramCreateProductEntity)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 58	com/zizun/cs/entities/biz/CreateProductEntity:getProduct	()Lcom/zizun/cs/entities/Product;
    //   4: astore_3
    //   5: new 60	com/zizun/cs/orm/SqlUtil
    //   8: dup
    //   9: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   12: aload_3
    //   13: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   16: astore 4
    //   18: new 71	java/lang/StringBuilder
    //   21: dup
    //   22: ldc 73
    //   24: invokespecial 76	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   27: aload 4
    //   29: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   32: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   35: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: aload 4
    //   40: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   43: invokevirtual 96	java/lang/Object:toString	()Ljava/lang/String;
    //   46: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: invokevirtual 97	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   52: invokestatic 102	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   55: aload_1
    //   56: invokevirtual 106	com/zizun/cs/entities/biz/CreateProductEntity:getListProductSku	()Ljava/util/ArrayList;
    //   59: astore 9
    //   61: aload_1
    //   62: invokevirtual 110	com/zizun/cs/entities/biz/CreateProductEntity:getPrice_Change_Header	()[Lcom/zizun/cs/entities/Price_Change_Header;
    //   65: astore 8
    //   67: aload_1
    //   68: invokevirtual 114	com/zizun/cs/entities/biz/CreateProductEntity:getPrice_Change_Detail	()[Lcom/zizun/cs/entities/Price_Change_Detail;
    //   71: astore 7
    //   73: aload_1
    //   74: invokevirtual 117	com/zizun/cs/entities/biz/CreateProductEntity:getProductStatusStore	()Ljava/util/ArrayList;
    //   77: astore 6
    //   79: aload_1
    //   80: invokevirtual 120	com/zizun/cs/entities/biz/CreateProductEntity:getListProductSkuPrice	()Ljava/util/ArrayList;
    //   83: astore 5
    //   85: aload_0
    //   86: invokevirtual 124	com/zizun/cs/biz/dao/trans/impl/ProductTranImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   89: astore_3
    //   90: aload_3
    //   91: invokevirtual 129	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   94: aload_3
    //   95: aload 4
    //   97: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   100: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   103: aload 4
    //   105: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   108: invokeinterface 135 1 0
    //   113: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   116: new 141	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl
    //   119: dup
    //   120: invokespecial 142	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:<init>	()V
    //   123: astore 4
    //   125: aload 4
    //   127: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   130: dup
    //   131: ldc -110
    //   133: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   136: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   139: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   142: pop
    //   143: iconst_0
    //   144: istore_2
    //   145: iload_2
    //   146: aload 9
    //   148: invokevirtual 162	java/util/ArrayList:size	()I
    //   151: if_icmplt +336 -> 487
    //   154: aload 4
    //   156: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   159: dup
    //   160: ldc -92
    //   162: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   165: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   168: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   171: pop
    //   172: iconst_0
    //   173: istore_2
    //   174: iload_2
    //   175: aload 8
    //   177: arraylength
    //   178: if_icmplt +356 -> 534
    //   181: aload 4
    //   183: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   186: dup
    //   187: ldc -90
    //   189: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   192: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   195: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   198: pop
    //   199: iconst_0
    //   200: istore_2
    //   201: iload_2
    //   202: aload 7
    //   204: arraylength
    //   205: if_icmplt +374 -> 579
    //   208: aload 4
    //   210: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   213: dup
    //   214: ldc -88
    //   216: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   219: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   222: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   225: pop
    //   226: iconst_0
    //   227: istore_2
    //   228: iload_2
    //   229: aload 6
    //   231: invokevirtual 162	java/util/ArrayList:size	()I
    //   234: if_icmplt +390 -> 624
    //   237: aload 4
    //   239: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   242: dup
    //   243: ldc -86
    //   245: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   248: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   251: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   254: pop
    //   255: iconst_0
    //   256: istore_2
    //   257: iload_2
    //   258: aload 5
    //   260: invokevirtual 162	java/util/ArrayList:size	()I
    //   263: if_icmplt +408 -> 671
    //   266: aload 4
    //   268: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   271: dup
    //   272: ldc -84
    //   274: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   277: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   280: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   283: pop
    //   284: aload_1
    //   285: invokevirtual 176	com/zizun/cs/entities/biz/CreateProductEntity:getStock_Header	()Lcom/zizun/cs/entities/Stock_Header;
    //   288: ifnull +178 -> 466
    //   291: aload_1
    //   292: invokevirtual 176	com/zizun/cs/entities/biz/CreateProductEntity:getStock_Header	()Lcom/zizun/cs/entities/Stock_Header;
    //   295: astore 5
    //   297: new 60	com/zizun/cs/orm/SqlUtil
    //   300: dup
    //   301: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   304: aload 5
    //   306: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   309: astore 5
    //   311: aload_3
    //   312: aload 5
    //   314: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   317: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   320: aload 5
    //   322: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   325: invokeinterface 135 1 0
    //   330: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   333: aload 4
    //   335: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   338: dup
    //   339: ldc -78
    //   341: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   344: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   347: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   350: pop
    //   351: aload_1
    //   352: invokevirtual 182	com/zizun/cs/entities/biz/CreateProductEntity:getStock_Detail	()Lcom/zizun/cs/entities/Stock_Detail;
    //   355: astore 5
    //   357: new 60	com/zizun/cs/orm/SqlUtil
    //   360: dup
    //   361: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   364: aload 5
    //   366: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   369: astore 5
    //   371: aload_3
    //   372: aload 5
    //   374: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   377: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   380: aload 5
    //   382: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   385: invokeinterface 135 1 0
    //   390: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   393: aload 4
    //   395: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   398: dup
    //   399: ldc -72
    //   401: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   404: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   407: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   410: pop
    //   411: aload_1
    //   412: invokevirtual 188	com/zizun/cs/entities/biz/CreateProductEntity:getTransactionLog	()Lcom/zizun/cs/entities/TransactionLog;
    //   415: astore_1
    //   416: new 60	com/zizun/cs/orm/SqlUtil
    //   419: dup
    //   420: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   423: aload_1
    //   424: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   427: astore_1
    //   428: aload_3
    //   429: aload_1
    //   430: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   433: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   436: aload_1
    //   437: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   440: invokeinterface 135 1 0
    //   445: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   448: aload 4
    //   450: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   453: dup
    //   454: ldc -66
    //   456: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   459: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   462: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   465: pop
    //   466: aload_3
    //   467: invokevirtual 193	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   470: ldc -61
    //   472: invokestatic 102	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   475: aload_3
    //   476: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   479: invokestatic 204	com/zizun/cs/biz/stockcal/manager/StockcalManager:getInstance	()Lcom/zizun/cs/biz/stockcal/manager/StockcalManager;
    //   482: invokevirtual 207	com/zizun/cs/biz/stockcal/manager/StockcalManager:calStock	()V
    //   485: iconst_1
    //   486: ireturn
    //   487: new 60	com/zizun/cs/orm/SqlUtil
    //   490: dup
    //   491: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   494: aload 9
    //   496: iload_2
    //   497: invokevirtual 211	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   500: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   503: astore 10
    //   505: aload_3
    //   506: aload 10
    //   508: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   511: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   514: aload 10
    //   516: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   519: invokeinterface 135 1 0
    //   524: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   527: iload_2
    //   528: iconst_1
    //   529: iadd
    //   530: istore_2
    //   531: goto -386 -> 145
    //   534: new 60	com/zizun/cs/orm/SqlUtil
    //   537: dup
    //   538: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   541: aload 8
    //   543: iload_2
    //   544: aaload
    //   545: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   548: astore 9
    //   550: aload_3
    //   551: aload 9
    //   553: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   556: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   559: aload 9
    //   561: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   564: invokeinterface 135 1 0
    //   569: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   572: iload_2
    //   573: iconst_1
    //   574: iadd
    //   575: istore_2
    //   576: goto -402 -> 174
    //   579: new 60	com/zizun/cs/orm/SqlUtil
    //   582: dup
    //   583: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   586: aload 7
    //   588: iload_2
    //   589: aaload
    //   590: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   593: astore 8
    //   595: aload_3
    //   596: aload 8
    //   598: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   601: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   604: aload 8
    //   606: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   609: invokeinterface 135 1 0
    //   614: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   617: iload_2
    //   618: iconst_1
    //   619: iadd
    //   620: istore_2
    //   621: goto -420 -> 201
    //   624: new 60	com/zizun/cs/orm/SqlUtil
    //   627: dup
    //   628: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   631: aload 6
    //   633: iload_2
    //   634: invokevirtual 211	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   637: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   640: astore 7
    //   642: aload_3
    //   643: aload 7
    //   645: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   648: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   651: aload 7
    //   653: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   656: invokeinterface 135 1 0
    //   661: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   664: iload_2
    //   665: iconst_1
    //   666: iadd
    //   667: istore_2
    //   668: goto -440 -> 228
    //   671: new 60	com/zizun/cs/orm/SqlUtil
    //   674: dup
    //   675: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   678: aload 5
    //   680: iload_2
    //   681: invokevirtual 211	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   684: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   687: astore 6
    //   689: aload_3
    //   690: aload 6
    //   692: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   695: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   698: aload 6
    //   700: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   703: invokeinterface 135 1 0
    //   708: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   711: iload_2
    //   712: iconst_1
    //   713: iadd
    //   714: istore_2
    //   715: goto -458 -> 257
    //   718: astore_1
    //   719: aload_1
    //   720: invokevirtual 214	android/database/SQLException:printStackTrace	()V
    //   723: aload_3
    //   724: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   727: invokestatic 204	com/zizun/cs/biz/stockcal/manager/StockcalManager:getInstance	()Lcom/zizun/cs/biz/stockcal/manager/StockcalManager;
    //   730: invokevirtual 207	com/zizun/cs/biz/stockcal/manager/StockcalManager:calStock	()V
    //   733: iconst_0
    //   734: ireturn
    //   735: astore_1
    //   736: aload_3
    //   737: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   740: invokestatic 204	com/zizun/cs/biz/stockcal/manager/StockcalManager:getInstance	()Lcom/zizun/cs/biz/stockcal/manager/StockcalManager;
    //   743: invokevirtual 207	com/zizun/cs/biz/stockcal/manager/StockcalManager:calStock	()V
    //   746: aload_1
    //   747: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	748	0	this	ProductTranImpl
    //   0	748	1	paramCreateProductEntity	com.zizun.cs.entities.biz.CreateProductEntity
    //   144	571	2	i	int
    //   4	733	3	localObject1	Object
    //   16	433	4	localObject2	Object
    //   83	596	5	localObject3	Object
    //   77	622	6	localObject4	Object
    //   71	581	7	localObject5	Object
    //   65	540	8	localObject6	Object
    //   59	501	9	localObject7	Object
    //   503	12	10	localSqlUtil	SqlUtil
    // Exception table:
    //   from	to	target	type
    //   94	143	718	android/database/SQLException
    //   145	172	718	android/database/SQLException
    //   174	199	718	android/database/SQLException
    //   201	226	718	android/database/SQLException
    //   228	255	718	android/database/SQLException
    //   257	466	718	android/database/SQLException
    //   466	475	718	android/database/SQLException
    //   487	527	718	android/database/SQLException
    //   534	572	718	android/database/SQLException
    //   579	617	718	android/database/SQLException
    //   624	664	718	android/database/SQLException
    //   671	711	718	android/database/SQLException
    //   94	143	735	finally
    //   145	172	735	finally
    //   174	199	735	finally
    //   201	226	735	finally
    //   228	255	735	finally
    //   257	466	735	finally
    //   466	475	735	finally
    //   487	527	735	finally
    //   534	572	735	finally
    //   579	617	735	finally
    //   624	664	735	finally
    //   671	711	735	finally
    //   719	723	735	finally
  }
  
  public boolean deleteProduct(Goods paramGoods)
  {
    Object localObject = DateTimeUtil.getCurrentTime();
    long l = UserManager.getInstance().getCurrentStore().getStore_ID();
    int i = UserManager.getInstance().getCurrentUser().getUser_ID();
    SQLiteDatabase localSQLiteDatabase = getDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      localSQLiteDatabase.execSQL("UPDATE ProductStatusStore SET Sync_DataStatus = CASE WHEN Sync_Status <> 3 AND Sync_DataStatus = 1 THEN 1 ELSE 2 END WHERE ProductSku_ID = ? AND LBU_ID = 1 AND Store_ID = ?", new Object[] { Long.valueOf(paramGoods.getSkuId()), Long.valueOf(l) });
      localSQLiteDatabase.execSQL("UPDATE ProductStatusStore SET PSS_Status = 3, Modify_DT = ?,Modify_ID = ?,Sync_Status = 1 WHERE ProductSku_ID = ? AND LBU_ID = 1 AND Store_ID = ?", new Object[] { localObject, Integer.valueOf(i), Long.valueOf(paramGoods.getSkuId()), Long.valueOf(l) });
      localObject = new S_Sync_UploadDaoImpl();
      ((S_Sync_UploadDaoImpl)localObject).insert(new S_Sync_Upload(ProductStatusStore.class.getSimpleName()));
      if (paramGoods.getAmount() != 0.0D)
      {
        paramGoods = new DeleteProductEntity(paramGoods);
        SqlUtil localSqlUtil = new SqlUtil(SqlUtil.SqlType.INSERT, paramGoods.getStock_Header());
        localSQLiteDatabase.execSQL(localSqlUtil.getSqlBuffer().toString(), localSqlUtil.getParam().toArray());
        ((S_Sync_UploadDaoImpl)localObject).insert(new S_Sync_Upload(Stock_Header.class.getSimpleName()));
        localSqlUtil = new SqlUtil(SqlUtil.SqlType.INSERT, paramGoods.getStock_Detail());
        localSQLiteDatabase.execSQL(localSqlUtil.getSqlBuffer().toString(), localSqlUtil.getParam().toArray());
        ((S_Sync_UploadDaoImpl)localObject).insert(new S_Sync_Upload(Stock_Detail.class.getSimpleName()));
        paramGoods = new SqlUtil(SqlUtil.SqlType.INSERT, paramGoods.getTransactionLog());
        localSQLiteDatabase.execSQL(paramGoods.getSqlBuffer().toString(), paramGoods.getParam().toArray());
        ((S_Sync_UploadDaoImpl)localObject).insert(new S_Sync_Upload(TransactionLog.class.getSimpleName()));
      }
      localSQLiteDatabase.setTransactionSuccessful();
      LogUtils.i("产品数据表删除事务成功!");
      return true;
    }
    catch (SQLException paramGoods)
    {
      paramGoods.printStackTrace();
      return false;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
      StockcalManager.getInstance().calStock();
    }
  }
  
  /* Error */
  public boolean stockInFromStore(long paramLong1, long paramLong2)
  {
    // Byte code:
    //   0: invokestatic 227	com/zizun/cs/activity/manager/UserManager:getInstance	()Lcom/zizun/cs/activity/manager/UserManager;
    //   3: invokevirtual 241	com/zizun/cs/activity/manager/UserManager:getCurrentUser	()Lcom/zizun/cs/entities/S_User;
    //   6: invokevirtual 281	com/zizun/cs/entities/S_User:getMerchant_ID	()I
    //   9: istore 6
    //   11: aload_0
    //   12: invokevirtual 124	com/zizun/cs/biz/dao/trans/impl/ProductTranImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   15: ldc 45
    //   17: iconst_2
    //   18: anewarray 82	java/lang/String
    //   21: dup
    //   22: iconst_0
    //   23: lload_1
    //   24: invokestatic 284	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   27: aastore
    //   28: dup
    //   29: iconst_1
    //   30: lload_3
    //   31: invokestatic 284	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   34: aastore
    //   35: invokevirtual 288	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   38: astore 8
    //   40: new 158	java/util/ArrayList
    //   43: dup
    //   44: invokespecial 289	java/util/ArrayList:<init>	()V
    //   47: astore 7
    //   49: aload 8
    //   51: invokeinterface 295 1 0
    //   56: ifne +313 -> 369
    //   59: new 297	com/zizun/cs/biz/dao/impl/LBUDaoImpl
    //   62: dup
    //   63: invokespecial 298	com/zizun/cs/biz/dao/impl/LBUDaoImpl:<init>	()V
    //   66: invokevirtual 301	com/zizun/cs/biz/dao/impl/LBUDaoImpl:getAllLBUID	()Ljava/util/ArrayList;
    //   69: astore 8
    //   71: iconst_0
    //   72: istore 5
    //   74: iload 5
    //   76: aload 8
    //   78: invokevirtual 162	java/util/ArrayList:size	()I
    //   81: if_icmplt +290 -> 371
    //   84: aload_0
    //   85: invokevirtual 124	com/zizun/cs/biz/dao/trans/impl/ProductTranImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   88: ldc 16
    //   90: iconst_1
    //   91: anewarray 82	java/lang/String
    //   94: dup
    //   95: iconst_0
    //   96: lload_1
    //   97: invokestatic 284	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   100: aastore
    //   101: invokevirtual 288	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   104: astore 9
    //   106: new 158	java/util/ArrayList
    //   109: dup
    //   110: invokespecial 289	java/util/ArrayList:<init>	()V
    //   113: astore 8
    //   115: aload 9
    //   117: invokeinterface 295 1 0
    //   122: ifeq +149 -> 271
    //   125: new 172	com/zizun/cs/entities/ProductSkuPrice
    //   128: dup
    //   129: invokespecial 302	com/zizun/cs/entities/ProductSkuPrice:<init>	()V
    //   132: astore 10
    //   134: aload 10
    //   136: invokestatic 227	com/zizun/cs/activity/manager/UserManager:getInstance	()Lcom/zizun/cs/activity/manager/UserManager;
    //   139: invokevirtual 241	com/zizun/cs/activity/manager/UserManager:getCurrentUser	()Lcom/zizun/cs/entities/S_User;
    //   142: invokevirtual 246	com/zizun/cs/entities/S_User:getUser_ID	()I
    //   145: invokevirtual 306	com/zizun/cs/entities/ProductSkuPrice:setCreateEntitySync	(I)V
    //   148: aload 10
    //   150: iload 6
    //   152: invokevirtual 309	com/zizun/cs/entities/ProductSkuPrice:setMerchant_ID	(I)V
    //   155: aload 10
    //   157: invokestatic 314	com/zizun/cs/biz/dao/utils/IDUtil:getID	()J
    //   160: invokevirtual 318	com/zizun/cs/entities/ProductSkuPrice:setPSP_ID	(J)V
    //   163: aload 10
    //   165: lload_3
    //   166: invokevirtual 321	com/zizun/cs/entities/ProductSkuPrice:setStore_ID	(J)V
    //   169: aload 10
    //   171: lload_1
    //   172: invokevirtual 324	com/zizun/cs/entities/ProductSkuPrice:setProductSku_ID	(J)V
    //   175: aload 10
    //   177: aload 9
    //   179: aload 9
    //   181: ldc_w 326
    //   184: invokeinterface 330 2 0
    //   189: invokeinterface 334 2 0
    //   194: invokevirtual 338	com/zizun/cs/entities/ProductSkuPrice:setRetail_Price	(D)V
    //   197: aload 10
    //   199: aload 9
    //   201: aload 9
    //   203: ldc_w 340
    //   206: invokeinterface 330 2 0
    //   211: invokeinterface 334 2 0
    //   216: invokevirtual 343	com/zizun/cs/entities/ProductSkuPrice:setWholeSale_Price	(D)V
    //   219: aload 10
    //   221: aload 9
    //   223: aload 9
    //   225: ldc_w 345
    //   228: invokeinterface 330 2 0
    //   233: invokeinterface 334 2 0
    //   238: invokevirtual 348	com/zizun/cs/entities/ProductSkuPrice:setPurchase_Price	(D)V
    //   241: aload 10
    //   243: aload 9
    //   245: aload 9
    //   247: ldc_w 350
    //   250: invokeinterface 330 2 0
    //   255: invokeinterface 334 2 0
    //   260: invokevirtual 353	com/zizun/cs/entities/ProductSkuPrice:setFixed_Cost	(D)V
    //   263: aload 8
    //   265: aload 10
    //   267: invokevirtual 357	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   270: pop
    //   271: aload_0
    //   272: invokevirtual 124	com/zizun/cs/biz/dao/trans/impl/ProductTranImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   275: astore 9
    //   277: aload 9
    //   279: invokevirtual 129	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   282: iconst_0
    //   283: istore 5
    //   285: iload 5
    //   287: aload 7
    //   289: invokevirtual 162	java/util/ArrayList:size	()I
    //   292: if_icmplt +218 -> 510
    //   295: new 141	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl
    //   298: dup
    //   299: invokespecial 142	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:<init>	()V
    //   302: astore 7
    //   304: aload 7
    //   306: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   309: dup
    //   310: ldc -86
    //   312: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   315: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   318: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   321: pop
    //   322: iconst_0
    //   323: istore 5
    //   325: iload 5
    //   327: aload 8
    //   329: invokevirtual 162	java/util/ArrayList:size	()I
    //   332: if_icmplt +229 -> 561
    //   335: aload 7
    //   337: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   340: dup
    //   341: ldc -84
    //   343: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   346: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   349: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   352: pop
    //   353: aload 9
    //   355: invokevirtual 193	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   358: ldc_w 359
    //   361: invokestatic 102	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   364: aload 9
    //   366: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   369: iconst_1
    //   370: ireturn
    //   371: new 170	com/zizun/cs/entities/ProductStatusStore
    //   374: dup
    //   375: invokespecial 360	com/zizun/cs/entities/ProductStatusStore:<init>	()V
    //   378: astore 9
    //   380: aload_0
    //   381: invokevirtual 124	com/zizun/cs/biz/dao/trans/impl/ProductTranImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   384: ldc 19
    //   386: iconst_1
    //   387: anewarray 82	java/lang/String
    //   390: dup
    //   391: iconst_0
    //   392: lload_1
    //   393: invokestatic 284	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   396: aastore
    //   397: invokevirtual 288	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   400: astore 10
    //   402: aload 10
    //   404: invokeinterface 295 1 0
    //   409: pop
    //   410: aload 9
    //   412: aload 10
    //   414: aload 10
    //   416: ldc_w 362
    //   419: invokeinterface 330 2 0
    //   424: invokeinterface 366 2 0
    //   429: invokevirtual 369	com/zizun/cs/entities/ProductStatusStore:setProduct_ID	(J)V
    //   432: aload 9
    //   434: lload_1
    //   435: invokevirtual 370	com/zizun/cs/entities/ProductStatusStore:setProductSku_ID	(J)V
    //   438: aload 9
    //   440: lload_3
    //   441: invokevirtual 371	com/zizun/cs/entities/ProductStatusStore:setStore_ID	(J)V
    //   444: aload 9
    //   446: iload 6
    //   448: invokevirtual 372	com/zizun/cs/entities/ProductStatusStore:setMerchant_ID	(I)V
    //   451: aload 9
    //   453: invokestatic 314	com/zizun/cs/biz/dao/utils/IDUtil:getID	()J
    //   456: invokevirtual 375	com/zizun/cs/entities/ProductStatusStore:setPSS_ID	(J)V
    //   459: aload 9
    //   461: iconst_1
    //   462: invokevirtual 378	com/zizun/cs/entities/ProductStatusStore:setLBU_ID	(I)V
    //   465: aload 9
    //   467: invokestatic 222	com/zizun/cs/common/utils/DateTimeUtil:getCurrentTime	()Ljava/sql/Timestamp;
    //   470: invokevirtual 382	com/zizun/cs/entities/ProductStatusStore:setPSS_StartDate	(Ljava/sql/Timestamp;)V
    //   473: aload 9
    //   475: invokestatic 227	com/zizun/cs/activity/manager/UserManager:getInstance	()Lcom/zizun/cs/activity/manager/UserManager;
    //   478: invokevirtual 241	com/zizun/cs/activity/manager/UserManager:getCurrentUser	()Lcom/zizun/cs/entities/S_User;
    //   481: invokevirtual 246	com/zizun/cs/entities/S_User:getUser_ID	()I
    //   484: invokevirtual 383	com/zizun/cs/entities/ProductStatusStore:setCreateEntitySync	(I)V
    //   487: aload 9
    //   489: iconst_1
    //   490: invokevirtual 387	com/zizun/cs/entities/ProductStatusStore:setPSS_Status	(B)V
    //   493: aload 7
    //   495: aload 9
    //   497: invokevirtual 357	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   500: pop
    //   501: iload 5
    //   503: iconst_1
    //   504: iadd
    //   505: istore 5
    //   507: goto -433 -> 74
    //   510: new 60	com/zizun/cs/orm/SqlUtil
    //   513: dup
    //   514: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   517: aload 7
    //   519: iload 5
    //   521: invokevirtual 211	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   524: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   527: astore 10
    //   529: aload 9
    //   531: aload 10
    //   533: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   536: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   539: aload 10
    //   541: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   544: invokeinterface 135 1 0
    //   549: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   552: iload 5
    //   554: iconst_1
    //   555: iadd
    //   556: istore 5
    //   558: goto -273 -> 285
    //   561: new 60	com/zizun/cs/orm/SqlUtil
    //   564: dup
    //   565: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   568: aload 8
    //   570: iload 5
    //   572: invokevirtual 211	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   575: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   578: astore 10
    //   580: aload 9
    //   582: aload 10
    //   584: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   587: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   590: aload 10
    //   592: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   595: invokeinterface 135 1 0
    //   600: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   603: iload 5
    //   605: iconst_1
    //   606: iadd
    //   607: istore 5
    //   609: goto -284 -> 325
    //   612: astore 7
    //   614: aload 7
    //   616: invokevirtual 214	android/database/SQLException:printStackTrace	()V
    //   619: aload 9
    //   621: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   624: iconst_0
    //   625: ireturn
    //   626: astore 7
    //   628: aload 9
    //   630: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   633: aload 7
    //   635: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	636	0	this	ProductTranImpl
    //   0	636	1	paramLong1	long
    //   0	636	3	paramLong2	long
    //   72	536	5	i	int
    //   9	438	6	j	int
    //   47	471	7	localObject1	Object
    //   612	3	7	localSQLException	SQLException
    //   626	8	7	localObject2	Object
    //   38	531	8	localObject3	Object
    //   104	525	9	localObject4	Object
    //   132	459	10	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   285	322	612	android/database/SQLException
    //   325	364	612	android/database/SQLException
    //   510	552	612	android/database/SQLException
    //   561	603	612	android/database/SQLException
    //   285	322	626	finally
    //   325	364	626	finally
    //   510	552	626	finally
    //   561	603	626	finally
    //   614	619	626	finally
  }
  
  /* Error */
  public boolean updateProduct(Goods paramGoods)
  {
    // Byte code:
    //   0: invokestatic 222	com/zizun/cs/common/utils/DateTimeUtil:getCurrentTime	()Ljava/sql/Timestamp;
    //   3: astore 7
    //   5: invokestatic 227	com/zizun/cs/activity/manager/UserManager:getInstance	()Lcom/zizun/cs/activity/manager/UserManager;
    //   8: invokevirtual 241	com/zizun/cs/activity/manager/UserManager:getCurrentUser	()Lcom/zizun/cs/entities/S_User;
    //   11: invokevirtual 246	com/zizun/cs/entities/S_User:getUser_ID	()I
    //   14: istore_2
    //   15: invokestatic 227	com/zizun/cs/activity/manager/UserManager:getInstance	()Lcom/zizun/cs/activity/manager/UserManager;
    //   18: invokevirtual 231	com/zizun/cs/activity/manager/UserManager:getCurrentStore	()Lcom/zizun/cs/entities/Store;
    //   21: invokevirtual 237	com/zizun/cs/entities/Store:getStore_ID	()J
    //   24: lstore_3
    //   25: new 390	com/zizun/cs/entities/biz/UpdateProductEntity
    //   28: dup
    //   29: aload_1
    //   30: invokespecial 391	com/zizun/cs/entities/biz/UpdateProductEntity:<init>	(Lcom/zizun/cs/ui/entity/Goods;)V
    //   33: astore 5
    //   35: aload 5
    //   37: invokevirtual 393	com/zizun/cs/entities/biz/UpdateProductEntity:getPrice_Change_Header	()Ljava/util/ArrayList;
    //   40: astore 12
    //   42: aload 5
    //   44: invokevirtual 395	com/zizun/cs/entities/biz/UpdateProductEntity:getPrice_Change_Detail	()Ljava/util/ArrayList;
    //   47: astore 11
    //   49: aload 5
    //   51: invokevirtual 398	com/zizun/cs/entities/biz/UpdateProductEntity:getStock_Header	()[Lcom/zizun/cs/entities/Stock_Header;
    //   54: astore 10
    //   56: aload 5
    //   58: invokevirtual 401	com/zizun/cs/entities/biz/UpdateProductEntity:getStock_Detail	()[Lcom/zizun/cs/entities/Stock_Detail;
    //   61: astore 8
    //   63: aload 5
    //   65: invokevirtual 404	com/zizun/cs/entities/biz/UpdateProductEntity:getTransactionLog	()[Lcom/zizun/cs/entities/TransactionLog;
    //   68: astore 5
    //   70: aload_0
    //   71: invokevirtual 124	com/zizun/cs/biz/dao/trans/impl/ProductTranImpl:getDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   74: astore 6
    //   76: aload 6
    //   78: invokevirtual 129	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   81: aload 6
    //   83: ldc 31
    //   85: ldc_w 406
    //   88: aload_1
    //   89: invokevirtual 409	com/zizun/cs/ui/entity/Goods:getId	()J
    //   92: invokestatic 284	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   95: invokevirtual 413	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   98: aconst_null
    //   99: invokevirtual 288	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   102: pop
    //   103: aload 6
    //   105: ldc 22
    //   107: bipush 7
    //   109: anewarray 95	java/lang/Object
    //   112: dup
    //   113: iconst_0
    //   114: aload_1
    //   115: invokevirtual 416	com/zizun/cs/ui/entity/Goods:getCode	()Ljava/lang/String;
    //   118: aastore
    //   119: dup
    //   120: iconst_1
    //   121: aload_1
    //   122: invokevirtual 419	com/zizun/cs/ui/entity/Goods:getName	()Ljava/lang/String;
    //   125: aastore
    //   126: dup
    //   127: iconst_2
    //   128: aload_1
    //   129: invokevirtual 423	com/zizun/cs/ui/entity/Goods:getGroup	()Lcom/zizun/cs/entities/ProductGroup;
    //   132: invokevirtual 428	com/zizun/cs/entities/ProductGroup:getPG_ID	()J
    //   135: invokestatic 257	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   138: aastore
    //   139: dup
    //   140: iconst_3
    //   141: aload 7
    //   143: aastore
    //   144: dup
    //   145: iconst_4
    //   146: iload_2
    //   147: invokestatic 262	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   150: aastore
    //   151: dup
    //   152: iconst_5
    //   153: iconst_1
    //   154: invokestatic 262	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   157: aastore
    //   158: dup
    //   159: bipush 6
    //   161: aload_1
    //   162: invokevirtual 409	com/zizun/cs/ui/entity/Goods:getId	()J
    //   165: invokestatic 257	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   168: aastore
    //   169: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   172: new 141	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl
    //   175: dup
    //   176: invokespecial 142	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:<init>	()V
    //   179: astore 9
    //   181: aload 9
    //   183: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   186: dup
    //   187: ldc -110
    //   189: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   192: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   195: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   198: pop
    //   199: aload 6
    //   201: ldc 38
    //   203: ldc_w 406
    //   206: aload_1
    //   207: invokevirtual 409	com/zizun/cs/ui/entity/Goods:getId	()J
    //   210: invokestatic 284	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   213: invokevirtual 413	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   216: aconst_null
    //   217: invokevirtual 288	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   220: pop
    //   221: aload 6
    //   223: ldc 25
    //   225: bipush 16
    //   227: anewarray 95	java/lang/Object
    //   230: dup
    //   231: iconst_0
    //   232: aload_1
    //   233: invokevirtual 419	com/zizun/cs/ui/entity/Goods:getName	()Ljava/lang/String;
    //   236: aastore
    //   237: dup
    //   238: iconst_1
    //   239: aload_1
    //   240: invokevirtual 431	com/zizun/cs/ui/entity/Goods:getDescribe	()Ljava/lang/String;
    //   243: aastore
    //   244: dup
    //   245: iconst_2
    //   246: aload_1
    //   247: invokevirtual 434	com/zizun/cs/ui/entity/Goods:getNumber	()Ljava/lang/String;
    //   250: aastore
    //   251: dup
    //   252: iconst_3
    //   253: aload_1
    //   254: invokevirtual 416	com/zizun/cs/ui/entity/Goods:getCode	()Ljava/lang/String;
    //   257: aastore
    //   258: dup
    //   259: iconst_4
    //   260: aload_1
    //   261: invokevirtual 437	com/zizun/cs/ui/entity/Goods:getSize	()Ljava/lang/String;
    //   264: aastore
    //   265: dup
    //   266: iconst_5
    //   267: aload_1
    //   268: invokevirtual 440	com/zizun/cs/ui/entity/Goods:getPicture	()Ljava/lang/String;
    //   271: aastore
    //   272: dup
    //   273: bipush 6
    //   275: aload_1
    //   276: invokevirtual 443	com/zizun/cs/ui/entity/Goods:getUnit	()Ljava/lang/String;
    //   279: aastore
    //   280: dup
    //   281: bipush 7
    //   283: ldc_w 445
    //   286: aastore
    //   287: dup
    //   288: bipush 8
    //   290: aload_1
    //   291: invokevirtual 448	com/zizun/cs/ui/entity/Goods:getNewSellPrice	()D
    //   294: invokestatic 453	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   297: aastore
    //   298: dup
    //   299: bipush 9
    //   301: aload_1
    //   302: invokevirtual 456	com/zizun/cs/ui/entity/Goods:getNewWholesalePrice	()D
    //   305: invokestatic 453	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   308: aastore
    //   309: dup
    //   310: bipush 10
    //   312: aload_1
    //   313: invokevirtual 459	com/zizun/cs/ui/entity/Goods:getNewStockPrice	()D
    //   316: invokestatic 453	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   319: aastore
    //   320: dup
    //   321: bipush 11
    //   323: aload_1
    //   324: invokevirtual 462	com/zizun/cs/ui/entity/Goods:getNewCostPrice	()D
    //   327: invokestatic 453	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   330: aastore
    //   331: dup
    //   332: bipush 12
    //   334: aload 7
    //   336: aastore
    //   337: dup
    //   338: bipush 13
    //   340: iload_2
    //   341: invokestatic 262	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   344: aastore
    //   345: dup
    //   346: bipush 14
    //   348: iconst_1
    //   349: invokestatic 262	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   352: aastore
    //   353: dup
    //   354: bipush 15
    //   356: aload_1
    //   357: invokevirtual 409	com/zizun/cs/ui/entity/Goods:getId	()J
    //   360: invokestatic 257	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   363: aastore
    //   364: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   367: aload 9
    //   369: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   372: dup
    //   373: ldc -92
    //   375: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   378: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   381: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   384: pop
    //   385: new 172	com/zizun/cs/entities/ProductSkuPrice
    //   388: dup
    //   389: invokespecial 302	com/zizun/cs/entities/ProductSkuPrice:<init>	()V
    //   392: astore 13
    //   394: aload 13
    //   396: aload_1
    //   397: invokevirtual 465	com/zizun/cs/ui/entity/Goods:getPSP_ID	()J
    //   400: invokevirtual 318	com/zizun/cs/entities/ProductSkuPrice:setPSP_ID	(J)V
    //   403: aload 6
    //   405: new 60	com/zizun/cs/orm/SqlUtil
    //   408: dup
    //   409: getstatic 468	com/zizun/cs/orm/SqlUtil$SqlType:UPDATELOCAL	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   412: aload 13
    //   414: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   417: invokevirtual 471	com/zizun/cs/orm/SqlUtil:getUpdateForLocalBeforeSql	()Ljava/lang/String;
    //   420: invokevirtual 473	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   423: aload 6
    //   425: ldc 28
    //   427: bipush 8
    //   429: anewarray 95	java/lang/Object
    //   432: dup
    //   433: iconst_0
    //   434: aload_1
    //   435: invokevirtual 448	com/zizun/cs/ui/entity/Goods:getNewSellPrice	()D
    //   438: invokestatic 453	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   441: aastore
    //   442: dup
    //   443: iconst_1
    //   444: aload_1
    //   445: invokevirtual 456	com/zizun/cs/ui/entity/Goods:getNewWholesalePrice	()D
    //   448: invokestatic 453	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   451: aastore
    //   452: dup
    //   453: iconst_2
    //   454: aload_1
    //   455: invokevirtual 459	com/zizun/cs/ui/entity/Goods:getNewStockPrice	()D
    //   458: invokestatic 453	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   461: aastore
    //   462: dup
    //   463: iconst_3
    //   464: aload_1
    //   465: invokevirtual 462	com/zizun/cs/ui/entity/Goods:getNewCostPrice	()D
    //   468: invokestatic 453	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   471: aastore
    //   472: dup
    //   473: iconst_4
    //   474: aload 7
    //   476: aastore
    //   477: dup
    //   478: iconst_5
    //   479: iload_2
    //   480: invokestatic 262	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   483: aastore
    //   484: dup
    //   485: bipush 6
    //   487: iconst_1
    //   488: invokestatic 262	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   491: aastore
    //   492: dup
    //   493: bipush 7
    //   495: aload_1
    //   496: invokevirtual 465	com/zizun/cs/ui/entity/Goods:getPSP_ID	()J
    //   499: invokestatic 257	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   502: aastore
    //   503: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   506: aload 9
    //   508: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   511: dup
    //   512: ldc -84
    //   514: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   517: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   520: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   523: pop
    //   524: aload 12
    //   526: ifnull +48 -> 574
    //   529: aload 12
    //   531: invokevirtual 162	java/util/ArrayList:size	()I
    //   534: ifeq +40 -> 574
    //   537: aload 12
    //   539: invokevirtual 162	java/util/ArrayList:size	()I
    //   542: anewarray 60	com/zizun/cs/orm/SqlUtil
    //   545: astore 13
    //   547: iconst_0
    //   548: istore_2
    //   549: iload_2
    //   550: aload 13
    //   552: arraylength
    //   553: if_icmplt +370 -> 923
    //   556: aload 9
    //   558: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   561: dup
    //   562: ldc -90
    //   564: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   567: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   570: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   573: pop
    //   574: aload 11
    //   576: ifnull +50 -> 626
    //   579: aload 11
    //   581: invokevirtual 162	java/util/ArrayList:size	()I
    //   584: ifeq +42 -> 626
    //   587: aload 11
    //   589: invokevirtual 162	java/util/ArrayList:size	()I
    //   592: anewarray 60	com/zizun/cs/orm/SqlUtil
    //   595: astore 12
    //   597: iconst_0
    //   598: istore_2
    //   599: iload_2
    //   600: aload 11
    //   602: invokevirtual 162	java/util/ArrayList:size	()I
    //   605: if_icmplt +372 -> 977
    //   608: aload 9
    //   610: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   613: dup
    //   614: ldc -88
    //   616: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   619: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   622: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   625: pop
    //   626: aload 10
    //   628: ifnull +44 -> 672
    //   631: aload 10
    //   633: arraylength
    //   634: ifeq +38 -> 672
    //   637: aload 10
    //   639: arraylength
    //   640: anewarray 60	com/zizun/cs/orm/SqlUtil
    //   643: astore 11
    //   645: iconst_0
    //   646: istore_2
    //   647: iload_2
    //   648: aload 11
    //   650: arraylength
    //   651: if_icmplt +380 -> 1031
    //   654: aload 9
    //   656: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   659: dup
    //   660: ldc -78
    //   662: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   665: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   668: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   671: pop
    //   672: aload 8
    //   674: ifnull +44 -> 718
    //   677: aload 8
    //   679: arraylength
    //   680: ifeq +38 -> 718
    //   683: aload 8
    //   685: arraylength
    //   686: anewarray 60	com/zizun/cs/orm/SqlUtil
    //   689: astore 10
    //   691: iconst_0
    //   692: istore_2
    //   693: iload_2
    //   694: aload 10
    //   696: arraylength
    //   697: if_icmplt +386 -> 1083
    //   700: aload 9
    //   702: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   705: dup
    //   706: ldc -72
    //   708: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   711: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   714: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   717: pop
    //   718: aload 5
    //   720: ifnull +50 -> 770
    //   723: aload 5
    //   725: arraylength
    //   726: ifeq +44 -> 770
    //   729: aload 5
    //   731: arraylength
    //   732: anewarray 60	com/zizun/cs/orm/SqlUtil
    //   735: astore 8
    //   737: iconst_0
    //   738: istore_2
    //   739: iload_2
    //   740: aload 8
    //   742: arraylength
    //   743: if_icmplt +392 -> 1135
    //   746: aload 9
    //   748: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   751: dup
    //   752: ldc -66
    //   754: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   757: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   760: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   763: pop
    //   764: invokestatic 204	com/zizun/cs/biz/stockcal/manager/StockcalManager:getInstance	()Lcom/zizun/cs/biz/stockcal/manager/StockcalManager;
    //   767: invokevirtual 207	com/zizun/cs/biz/stockcal/manager/StockcalManager:calStock	()V
    //   770: aload_1
    //   771: invokevirtual 477	com/zizun/cs/ui/entity/Goods:getStatus	()Lcom/zizun/cs/ui/entity/GoodsStatus;
    //   774: invokevirtual 482	com/zizun/cs/ui/entity/GoodsStatus:getId	()B
    //   777: aload_1
    //   778: invokevirtual 485	com/zizun/cs/ui/entity/Goods:getNewStatus	()Lcom/zizun/cs/ui/entity/GoodsStatus;
    //   781: invokevirtual 482	com/zizun/cs/ui/entity/GoodsStatus:getId	()B
    //   784: if_icmpeq +104 -> 888
    //   787: aload 6
    //   789: ldc 13
    //   791: iconst_2
    //   792: anewarray 95	java/lang/Object
    //   795: dup
    //   796: iconst_0
    //   797: aload_1
    //   798: invokevirtual 251	com/zizun/cs/ui/entity/Goods:getSkuId	()J
    //   801: invokestatic 257	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   804: aastore
    //   805: dup
    //   806: iconst_1
    //   807: lload_3
    //   808: invokestatic 257	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   811: aastore
    //   812: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   815: aload 6
    //   817: ldc 34
    //   819: iconst_5
    //   820: anewarray 95	java/lang/Object
    //   823: dup
    //   824: iconst_0
    //   825: aload_1
    //   826: invokevirtual 485	com/zizun/cs/ui/entity/Goods:getNewStatus	()Lcom/zizun/cs/ui/entity/GoodsStatus;
    //   829: invokevirtual 482	com/zizun/cs/ui/entity/GoodsStatus:getId	()B
    //   832: invokestatic 490	java/lang/Byte:valueOf	(B)Ljava/lang/Byte;
    //   835: aastore
    //   836: dup
    //   837: iconst_1
    //   838: aload 7
    //   840: aastore
    //   841: dup
    //   842: iconst_2
    //   843: invokestatic 314	com/zizun/cs/biz/dao/utils/IDUtil:getID	()J
    //   846: invokestatic 257	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   849: aastore
    //   850: dup
    //   851: iconst_3
    //   852: aload_1
    //   853: invokevirtual 251	com/zizun/cs/ui/entity/Goods:getSkuId	()J
    //   856: invokestatic 257	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   859: aastore
    //   860: dup
    //   861: iconst_4
    //   862: lload_3
    //   863: invokestatic 257	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   866: aastore
    //   867: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   870: aload 9
    //   872: new 144	com/zizun/cs/entities/S_Sync_Upload
    //   875: dup
    //   876: ldc -86
    //   878: invokevirtual 151	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   881: invokespecial 152	com/zizun/cs/entities/S_Sync_Upload:<init>	(Ljava/lang/String;)V
    //   884: invokevirtual 156	com/zizun/cs/biz/dao/impl/S_Sync_UploadDaoImpl:insert	(Lcom/zizun/cs/entities/S_Sync_Upload;)Z
    //   887: pop
    //   888: aload 6
    //   890: invokevirtual 193	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   893: ldc_w 492
    //   896: invokestatic 102	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   899: aload 6
    //   901: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   904: aload 5
    //   906: ifnull +15 -> 921
    //   909: aload 5
    //   911: arraylength
    //   912: ifeq +9 -> 921
    //   915: invokestatic 204	com/zizun/cs/biz/stockcal/manager/StockcalManager:getInstance	()Lcom/zizun/cs/biz/stockcal/manager/StockcalManager;
    //   918: invokevirtual 207	com/zizun/cs/biz/stockcal/manager/StockcalManager:calStock	()V
    //   921: iconst_1
    //   922: ireturn
    //   923: aload 13
    //   925: iload_2
    //   926: new 60	com/zizun/cs/orm/SqlUtil
    //   929: dup
    //   930: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   933: aload 12
    //   935: iload_2
    //   936: invokevirtual 211	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   939: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   942: aastore
    //   943: aload 6
    //   945: aload 13
    //   947: iload_2
    //   948: aaload
    //   949: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   952: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   955: aload 13
    //   957: iload_2
    //   958: aaload
    //   959: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   962: invokeinterface 135 1 0
    //   967: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   970: iload_2
    //   971: iconst_1
    //   972: iadd
    //   973: istore_2
    //   974: goto -425 -> 549
    //   977: aload 12
    //   979: iload_2
    //   980: new 60	com/zizun/cs/orm/SqlUtil
    //   983: dup
    //   984: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   987: aload 11
    //   989: iload_2
    //   990: invokevirtual 211	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   993: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   996: aastore
    //   997: aload 6
    //   999: aload 12
    //   1001: iload_2
    //   1002: aaload
    //   1003: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   1006: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   1009: aload 12
    //   1011: iload_2
    //   1012: aaload
    //   1013: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   1016: invokeinterface 135 1 0
    //   1021: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1024: iload_2
    //   1025: iconst_1
    //   1026: iadd
    //   1027: istore_2
    //   1028: goto -429 -> 599
    //   1031: aload 11
    //   1033: iload_2
    //   1034: new 60	com/zizun/cs/orm/SqlUtil
    //   1037: dup
    //   1038: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   1041: aload 10
    //   1043: iload_2
    //   1044: aaload
    //   1045: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   1048: aastore
    //   1049: aload 6
    //   1051: aload 11
    //   1053: iload_2
    //   1054: aaload
    //   1055: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   1058: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   1061: aload 11
    //   1063: iload_2
    //   1064: aaload
    //   1065: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   1068: invokeinterface 135 1 0
    //   1073: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1076: iload_2
    //   1077: iconst_1
    //   1078: iadd
    //   1079: istore_2
    //   1080: goto -433 -> 647
    //   1083: aload 10
    //   1085: iload_2
    //   1086: new 60	com/zizun/cs/orm/SqlUtil
    //   1089: dup
    //   1090: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   1093: aload 8
    //   1095: iload_2
    //   1096: aaload
    //   1097: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   1100: aastore
    //   1101: aload 6
    //   1103: aload 10
    //   1105: iload_2
    //   1106: aaload
    //   1107: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   1110: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   1113: aload 10
    //   1115: iload_2
    //   1116: aaload
    //   1117: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   1120: invokeinterface 135 1 0
    //   1125: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1128: iload_2
    //   1129: iconst_1
    //   1130: iadd
    //   1131: istore_2
    //   1132: goto -439 -> 693
    //   1135: aload 8
    //   1137: iload_2
    //   1138: new 60	com/zizun/cs/orm/SqlUtil
    //   1141: dup
    //   1142: getstatic 66	com/zizun/cs/orm/SqlUtil$SqlType:INSERT	Lcom/zizun/cs/orm/SqlUtil$SqlType;
    //   1145: aload 5
    //   1147: iload_2
    //   1148: aaload
    //   1149: invokespecial 69	com/zizun/cs/orm/SqlUtil:<init>	(Lcom/zizun/cs/orm/SqlUtil$SqlType;Ljava/lang/Object;)V
    //   1152: aastore
    //   1153: aload 6
    //   1155: aload 8
    //   1157: iload_2
    //   1158: aaload
    //   1159: invokevirtual 80	com/zizun/cs/orm/SqlUtil:getSqlBuffer	()Ljava/lang/String;
    //   1162: invokevirtual 85	java/lang/String:toString	()Ljava/lang/String;
    //   1165: aload 8
    //   1167: iload_2
    //   1168: aaload
    //   1169: invokevirtual 93	com/zizun/cs/orm/SqlUtil:getParam	()Ljava/util/List;
    //   1172: invokeinterface 135 1 0
    //   1177: invokevirtual 139	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1180: iload_2
    //   1181: iconst_1
    //   1182: iadd
    //   1183: istore_2
    //   1184: goto -445 -> 739
    //   1187: astore_1
    //   1188: aload_1
    //   1189: invokevirtual 214	android/database/SQLException:printStackTrace	()V
    //   1192: aload 6
    //   1194: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   1197: aload 5
    //   1199: ifnull +15 -> 1214
    //   1202: aload 5
    //   1204: arraylength
    //   1205: ifeq +9 -> 1214
    //   1208: invokestatic 204	com/zizun/cs/biz/stockcal/manager/StockcalManager:getInstance	()Lcom/zizun/cs/biz/stockcal/manager/StockcalManager;
    //   1211: invokevirtual 207	com/zizun/cs/biz/stockcal/manager/StockcalManager:calStock	()V
    //   1214: iconst_0
    //   1215: ireturn
    //   1216: astore_1
    //   1217: aload 6
    //   1219: invokevirtual 198	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   1222: aload 5
    //   1224: ifnull +15 -> 1239
    //   1227: aload 5
    //   1229: arraylength
    //   1230: ifeq +9 -> 1239
    //   1233: invokestatic 204	com/zizun/cs/biz/stockcal/manager/StockcalManager:getInstance	()Lcom/zizun/cs/biz/stockcal/manager/StockcalManager;
    //   1236: invokevirtual 207	com/zizun/cs/biz/stockcal/manager/StockcalManager:calStock	()V
    //   1239: aload_1
    //   1240: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1241	0	this	ProductTranImpl
    //   0	1241	1	paramGoods	Goods
    //   14	1170	2	i	int
    //   24	839	3	l	long
    //   33	1195	5	localObject1	Object
    //   74	1144	6	localSQLiteDatabase	SQLiteDatabase
    //   3	836	7	localTimestamp	java.sql.Timestamp
    //   61	1105	8	localObject2	Object
    //   179	692	9	localS_Sync_UploadDaoImpl	S_Sync_UploadDaoImpl
    //   54	1060	10	localObject3	Object
    //   47	1015	11	localObject4	Object
    //   40	970	12	localObject5	Object
    //   392	564	13	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   81	524	1187	android/database/SQLException
    //   529	547	1187	android/database/SQLException
    //   549	574	1187	android/database/SQLException
    //   579	597	1187	android/database/SQLException
    //   599	626	1187	android/database/SQLException
    //   631	645	1187	android/database/SQLException
    //   647	672	1187	android/database/SQLException
    //   677	691	1187	android/database/SQLException
    //   693	718	1187	android/database/SQLException
    //   723	737	1187	android/database/SQLException
    //   739	770	1187	android/database/SQLException
    //   770	888	1187	android/database/SQLException
    //   888	899	1187	android/database/SQLException
    //   923	970	1187	android/database/SQLException
    //   977	1024	1187	android/database/SQLException
    //   1031	1076	1187	android/database/SQLException
    //   1083	1128	1187	android/database/SQLException
    //   1135	1180	1187	android/database/SQLException
    //   81	524	1216	finally
    //   529	547	1216	finally
    //   549	574	1216	finally
    //   579	597	1216	finally
    //   599	626	1216	finally
    //   631	645	1216	finally
    //   647	672	1216	finally
    //   677	691	1216	finally
    //   693	718	1216	finally
    //   723	737	1216	finally
    //   739	770	1216	finally
    //   770	888	1216	finally
    //   888	899	1216	finally
    //   923	970	1216	finally
    //   977	1024	1216	finally
    //   1031	1076	1216	finally
    //   1083	1128	1216	finally
    //   1135	1180	1216	finally
    //   1188	1192	1216	finally
  }
}