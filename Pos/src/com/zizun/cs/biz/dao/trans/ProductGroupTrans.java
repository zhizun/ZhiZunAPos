package com.zizun.cs.biz.dao.trans;

import com.zizun.cs.entities.ProductGroup;

public abstract interface ProductGroupTrans
{
  public abstract boolean deleteProductGroup(ProductGroup paramProductGroup);
}