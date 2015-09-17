package com.zizun.cs.biz.dao.trans;

import com.zizun.cs.entities.biz.CreateProductEntity;
import com.zizun.cs.ui.entity.Goods;

public abstract interface ProductTrans
{
  public abstract boolean createProductTransaction(CreateProductEntity paramCreateProductEntity);
  
  public abstract boolean deleteProduct(Goods paramGoods);
  
  public abstract boolean stockInFromStore(long paramLong1, long paramLong2);
  
  public abstract boolean updateProduct(Goods paramGoods);
}