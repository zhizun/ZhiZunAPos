package com.zizun.cs.biz.dao;

import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zizun.cs.entities.api.ImgProductParam;
import com.zizun.cs.ui.entity.Goods;
import com.zizun.cs.ui.entity.GoodsFilter;
import java.util.ArrayList;

public abstract interface ProductDao
{
  public abstract ArrayList<Goods> getAllProduct(int paramInt);
  
  public abstract ArrayList<Goods> getAllProduct(GoodsFilter paramGoodsFilter);
  
  public abstract Goods getGood(String paramString);
  
  public abstract ArrayList<Goods> getStockInAllProduct(GoodsFilter paramGoodsFilter);
  
  public abstract boolean isProductBarCodeExist(String paramString);
  
  public abstract boolean isProductNameExist(String paramString);
  
  public abstract void updateProductImg(ImgProductParam paramImgProductParam, RequestCallBack<String> paramRequestCallBack);
}