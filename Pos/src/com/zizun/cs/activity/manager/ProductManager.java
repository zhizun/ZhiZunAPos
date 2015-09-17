package com.zizun.cs.activity.manager;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zizun.cs.biz.api.ImgAPI;
import com.zizun.cs.biz.dao.ProductDao;
import com.zizun.cs.biz.dao.impl.ProductDaoImpl;
import com.zizun.cs.biz.dao.impl.ProductGroupDaoImpl;
import com.zizun.cs.biz.dao.trans.ProductGroupTrans;
import com.zizun.cs.biz.dao.trans.ProductTrans;
import com.zizun.cs.biz.dao.trans.impl.ProductGroupTranImpl;
import com.zizun.cs.biz.dao.trans.impl.ProductTranImpl;
import com.zizun.cs.entities.ProductGroup;
import com.zizun.cs.entities.api.ImgProductParam;
import com.zizun.cs.entities.api.ImgResult;
import com.zizun.cs.entities.biz.CreateProductEntity;
import com.zizun.cs.ui.entity.Goods;
import com.zizun.cs.ui.entity.GoodsFilter;
import java.util.ArrayList;

public class ProductManager
{
  public static boolean createPrimaryGroup(String paramString)
  {
    return new ProductGroupDaoImpl().createPrimaryGroup(paramString);
  }
  
  public static boolean createProduct(Goods paramGoods)
  {
    return new ProductTranImpl().createProductTransaction(new CreateProductEntity(paramGoods));
  }
  
  public static boolean createSecondaryGroup(String paramString, long paramLong)
  {
    return new ProductGroupDaoImpl().createSecondaryGroup(paramString, paramLong);
  }
  
  public static boolean deleteProduct(Goods paramGoods)
  {
    return new ProductTranImpl().deleteProduct(paramGoods);
  }
  
  public static boolean deleteProductGroup(ProductGroup paramProductGroup)
  {
    return new ProductGroupTranImpl().deleteProductGroup(paramProductGroup);
  }
  
  public static ArrayList<ProductGroup> getAllPrimaryGroups()
  {
    return new ProductGroupDaoImpl().getAllPrimaryGroup();
  }
  
  public static ArrayList<Goods> getAllProduct(int paramInt)
  {
    return new ProductDaoImpl().getAllProduct(paramInt);
  }
  
  public static ArrayList<Goods> getAllProduct(GoodsFilter paramGoodsFilter)
  {
    return new ProductDaoImpl().getAllProduct(paramGoodsFilter);
  }
  
  public static ArrayList<ProductGroup> getAllSecondaryGroups(long paramLong)
  {
    return new ProductGroupDaoImpl().getAllSecondaryGroup(paramLong);
  }
  
  public static Goods getSingleGood(String paramString)
  {
    return new ProductDaoImpl().getGood(paramString);
  }
  
  public static ArrayList<Goods> getStockInAllProduct(GoodsFilter paramGoodsFilter)
  {
    return new ProductDaoImpl().getStockInAllProduct(paramGoodsFilter);
  }
  
  public static ImgResult getUpLoadImgCUDResult(String paramString)
  {
    return (ImgResult)ImgAPI.getAPIResultFromJson(paramString, ImgResult.class);
  }
  
  public static boolean stockInFromStore(long paramLong1, long paramLong2)
  {
    return new ProductTranImpl().stockInFromStore(paramLong1, paramLong2);
  }
  
  public static boolean updateProduct(Goods paramGoods)
  {
    return new ProductTranImpl().updateProduct(paramGoods);
  }
  
  public static boolean updateProductGroup(ProductGroup paramProductGroup)
  {
    return new ProductGroupDaoImpl().updateGroup(paramProductGroup);
  }
  
  public static void updateProductImg(ImgProductParam paramImgProductParam, RequestCallBack<String> paramRequestCallBack)
  {
    new ProductDaoImpl().updateProductImg(paramImgProductParam, paramRequestCallBack);
  }
}