package com.zizun.cs.entities.biz;

import com.zizun.cs.activity.manager.UserManager;
import com.zizun.cs.biz.dao.impl.LBUDaoImpl;
import com.zizun.cs.biz.dao.impl.ProductGroupDaoImpl;
import com.zizun.cs.biz.dao.utils.IDUtil;
import com.zizun.cs.common.utils.DateTimeUtil;
import com.zizun.cs.entities.Price_Change_Detail;
import com.zizun.cs.entities.Price_Change_Header;
import com.zizun.cs.entities.Product;
import com.zizun.cs.entities.ProductGroup;
import com.zizun.cs.entities.ProductSku;
import com.zizun.cs.entities.ProductSkuPrice;
import com.zizun.cs.entities.ProductStatusStore;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Stock_Detail;
import com.zizun.cs.entities.Stock_Header;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.TransactionLog;
import com.zizun.cs.ui.entity.Goods;
import com.zizun.cs.ui.entity.GoodsStatus;
import java.util.ArrayList;

public class CreateProductEntity
{
  private ArrayList<ProductSku> listProductSku;
  private ArrayList<ProductSkuPrice> listProductSkuPrice;
  private Goods mGoods;
  private int mMerchantId;
  private long mStoreId;
  private int mUserId;
  private Price_Change_Detail[] price_Change_Detail;
  private Price_Change_Header[] price_Change_Header;
  private Product product;
  private ArrayList<ProductStatusStore> productStatusStore;
  private Stock_Detail stock_Detail;
  private Stock_Header stock_Header;
  private TransactionLog transactionLog;
  
  public CreateProductEntity(Goods paramGoods)
  {
    this.mGoods = paramGoods;
    this.mMerchantId = UserManager.getInstance().getCurrentUser().getMerchant_ID();
    this.mUserId = UserManager.getInstance().getCurrentUser().getUser_ID();
    this.mStoreId = UserManager.getInstance().getCurrentStore().getStore_ID();
    setProduct();
    setProductSku();
    int i = 0;
    for (;;)
    {
      if (i >= this.listProductSku.size())
      {
        setProductStatusStore();
        setProductSkuPrice();
        if (this.mGoods.getAmount() > 0.0D) {
          createStockIn();
        }
        return;
      }
      paramGoods = (ProductSku)this.listProductSku.get(i);
      setPrice_Change_Header();
      setPrice_Change_Detail(paramGoods);
      i += 1;
    }
  }
  
  private void createStockIn()
  {
    this.stock_Header = new Stock_Header();
    this.stock_Header.setMerchant_ID(this.mMerchantId);
    this.stock_Header.setStock_ID(IDUtil.getID());
    this.stock_Header.setStock_Number(String.valueOf("TI" + this.stock_Header.getStock_ID()));
    this.stock_Header.setStock_Type((byte)1);
    this.stock_Header.setStock_TransType((byte)1);
    this.stock_Header.setStock_TransID(0L);
    this.stock_Header.setStock_StoreID(this.mStoreId);
    this.stock_Header.setStock_TransDate(DateTimeUtil.getCurrentTime());
    this.stock_Header.setStock_TransStaff(this.mUserId);
    this.stock_Header.setStock_Status((byte)1);
    this.stock_Header.setCreateEntitySync(this.mUserId);
    this.stock_Detail = new Stock_Detail();
    this.stock_Detail.setMerchant_ID(this.mMerchantId);
    this.stock_Detail.setSD_ID(IDUtil.getID());
    this.stock_Detail.setStock_ID(this.stock_Header.getStock_ID());
    this.stock_Detail.setSequence_ID(1);
    this.stock_Detail.setProductSku_ID(this.mGoods.getSkuId());
    this.stock_Detail.setStock_Quantity(this.mGoods.getAmount());
    this.stock_Detail.setStock_Cost(this.mGoods.getCostPrice());
    this.stock_Detail.setStock_Price(this.mGoods.getSellPrice());
    this.stock_Detail.setCreateEntitySync(this.mUserId);
    this.transactionLog = new TransactionLog();
    this.transactionLog.setMerchant_ID(this.mMerchantId);
    this.transactionLog.setTLog_ID(IDUtil.getID());
    this.transactionLog.setTLog_AffectStock((byte)1);
    this.transactionLog.setTLog_TransactionId(this.stock_Header.getStock_ID());
    this.transactionLog.setTLog_TransactionNumber(this.stock_Header.getStock_Number());
    this.transactionLog.setTLog_TransactionType(10);
    this.transactionLog.setTLog_PostingDate(this.stock_Header.getStock_TransDate());
    this.transactionLog.setTLog_StoreID(this.mStoreId);
    this.transactionLog.setTLog_ProductSku(this.stock_Detail.getProductSku_ID());
    this.transactionLog.setTLog_StockQuantity(this.stock_Detail.getStock_Quantity());
    this.transactionLog.setTLog_CostPrice(this.stock_Detail.getStock_Cost());
    this.transactionLog.setTLog_PriceSold(this.stock_Detail.getStock_Price());
    this.transactionLog.setTLog_IsLocal((byte)1);
    this.transactionLog.setTLog_Status((byte)1);
    this.transactionLog.setCreateEntitySync(this.mUserId);
  }
  
  private void setPrice_Change_Detail(ProductSku paramProductSku)
  {
    this.price_Change_Detail = new Price_Change_Detail[4];
    double d1 = this.mGoods.getSellPrice();
    double d2 = this.mGoods.getCostPrice();
    double d3 = this.mGoods.getWholesalePrice();
    double d4 = this.mGoods.getStockPrice();
    int i = 0;
    for (;;)
    {
      if (i >= this.price_Change_Detail.length) {
        return;
      }
      this.price_Change_Detail[i] = new Price_Change_Detail();
      this.price_Change_Detail[i].setCreateEntitySync(this.mUserId);
      this.price_Change_Detail[i].setPCD_ID(IDUtil.getID());
      this.price_Change_Detail[i].setMerchant_ID(this.mMerchantId);
      this.price_Change_Detail[i].setPCH_ID(this.price_Change_Header[i].getPCH_ID());
      this.price_Change_Detail[i].setPCD_Sequence(1);
      this.price_Change_Detail[i].setStoreID(this.mStoreId);
      this.price_Change_Detail[i].setProduct_ID(this.product.getProduct_ID());
      this.price_Change_Detail[i].setProductSku_ID(paramProductSku.getProductSku_ID());
      this.price_Change_Detail[i].setPrice(new double[] { d1, d2, d3, d4 }[i]);
      i += 1;
    }
  }
  
  private void setPrice_Change_Header()
  {
    this.price_Change_Header = new Price_Change_Header[4];
    int i = 0;
    for (;;)
    {
      if (i >= this.price_Change_Header.length) {
        return;
      }
      this.price_Change_Header[i] = new Price_Change_Header();
      this.price_Change_Header[i].setCreateEntitySync(this.mUserId);
      this.price_Change_Header[i].setMerchant_ID(this.mMerchantId);
      this.price_Change_Header[i].setPCH_ID(IDUtil.getID());
      this.price_Change_Header[i].setPCH_StartDate(DateTimeUtil.getCurrentTime());
      this.price_Change_Header[i].setPCH_PriceType((byte)(i + 1));
      this.price_Change_Header[i].setPCH_Type((byte)1);
      this.price_Change_Header[i].setPCH_Description(DateTimeUtil.getTimeString() + "Create");
      this.price_Change_Header[i].setPCH_Status((byte)1);
      i += 1;
    }
  }
  
  private void setProduct()
  {
    this.product = new Product();
    this.product.setMerchant_ID(this.mMerchantId);
    this.product.setProduct_ID(IDUtil.getID());
    this.product.setProduct_Code(this.mGoods.getCode());
    this.product.setProduct_Name(this.mGoods.getName());
    this.product.setCreateEntitySync(this.mUserId);
    if (this.mGoods.getGroup() == null) {}
    for (long l = new ProductGroupDaoImpl().getDefaultGroupId();; l = this.mGoods.getGroup().getPG_ID())
    {
      this.product.setProduct_Group(l);
      this.product.setProduct_Remark(this.mGoods.getDescribe());
      this.product.setProduct_Status((byte)1);
      return;
    }
  }
  
  private void setProductSku()
  {
    this.listProductSku = new ArrayList();
    ProductSku localProductSku = new ProductSku();
    localProductSku.setCreateEntitySync(this.mUserId);
    localProductSku.setMerchant_ID(this.mMerchantId);
    localProductSku.setProduct_ID(this.product.getProduct_ID());
    localProductSku.setProductSku_ID(this.mGoods.getSkuId());
    localProductSku.setProductSku_Name(this.mGoods.getName());
    localProductSku.setPruductSku_Description(this.mGoods.getDescribe());
    localProductSku.setProductSku_Code(this.mGoods.getNumber());
    localProductSku.setProductSku_BarCode(this.mGoods.getCode());
    localProductSku.setProductSku_Spec(this.mGoods.getSize());
    localProductSku.setProductSku_Image(this.mGoods.getPicture());
    localProductSku.setProductSku_Unit(this.mGoods.getUnit());
    localProductSku.setRetail_Price(this.mGoods.getSellPrice());
    localProductSku.setWholeSale_Price(this.mGoods.getWholesalePrice());
    localProductSku.setPurchase_Price(this.mGoods.getStockPrice());
    localProductSku.setFixed_Cost(this.mGoods.getCostPrice());
    localProductSku.setProductSku_Status((byte)1);
    this.listProductSku.add(localProductSku);
  }
  
  private void setProductSkuPrice()
  {
    this.listProductSkuPrice = new ArrayList();
    int i = 0;
    for (;;)
    {
      if (i >= this.listProductSku.size()) {
        return;
      }
      ProductSkuPrice localProductSkuPrice = new ProductSkuPrice();
      ProductSku localProductSku = (ProductSku)this.listProductSku.get(i);
      localProductSkuPrice.setCreateEntitySync(this.mUserId);
      localProductSkuPrice.setMerchant_ID(localProductSku.getMerchant_ID());
      localProductSkuPrice.setPSP_ID(IDUtil.getID());
      localProductSkuPrice.setStore_ID(this.mStoreId);
      localProductSkuPrice.setProductSku_ID(localProductSku.getProductSku_ID());
      localProductSkuPrice.setRetail_Price(localProductSku.getRetail_Price());
      localProductSkuPrice.setWholeSale_Price(localProductSku.getWholeSale_Price());
      localProductSkuPrice.setPurchase_Price(localProductSku.getPurchase_Price());
      localProductSkuPrice.setFixed_Cost(localProductSku.getFixed_Cost());
      localProductSkuPrice.setAverage_Cost(localProductSku.getAverage_Cost());
      this.listProductSkuPrice.add(localProductSkuPrice);
      i += 1;
    }
  }
  
  private void setProductStatusStore()
  {
    ArrayList localArrayList = new LBUDaoImpl().getAllLBUID();
    this.productStatusStore = new ArrayList();
    int i = 0;
    for (;;)
    {
      if (i >= localArrayList.size()) {
        return;
      }
      ProductStatusStore localProductStatusStore = new ProductStatusStore();
      localProductStatusStore.setCreateEntitySync(this.mUserId);
      localProductStatusStore.setMerchant_ID(this.mMerchantId);
      localProductStatusStore.setPSS_ID(IDUtil.getID());
      localProductStatusStore.setLBU_ID(((Integer)localArrayList.get(i)).intValue());
      localProductStatusStore.setProduct_ID(this.product.getProduct_ID());
      localProductStatusStore.setProductSku_ID(((ProductSku)this.listProductSku.get(0)).getProductSku_ID());
      localProductStatusStore.setStore_ID(this.mStoreId);
      localProductStatusStore.setPSS_StartDate(DateTimeUtil.getCurrentTime());
      localProductStatusStore.setPSS_Status(this.mGoods.getStatus().getId());
      this.productStatusStore.add(localProductStatusStore);
      i += 1;
    }
  }
  
  public ArrayList<ProductSku> getListProductSku()
  {
    return this.listProductSku;
  }
  
  public ArrayList<ProductSkuPrice> getListProductSkuPrice()
  {
    return this.listProductSkuPrice;
  }
  
  public Price_Change_Detail[] getPrice_Change_Detail()
  {
    return this.price_Change_Detail;
  }
  
  public Price_Change_Header[] getPrice_Change_Header()
  {
    return this.price_Change_Header;
  }
  
  public Product getProduct()
  {
    return this.product;
  }
  
  public ArrayList<ProductStatusStore> getProductStatusStore()
  {
    return this.productStatusStore;
  }
  
  public Stock_Detail getStock_Detail()
  {
    return this.stock_Detail;
  }
  
  public Stock_Header getStock_Header()
  {
    return this.stock_Header;
  }
  
  public TransactionLog getTransactionLog()
  {
    return this.transactionLog;
  }
  
  public void setListProductSku(ArrayList<ProductSku> paramArrayList)
  {
    this.listProductSku = paramArrayList;
  }
  
  public void setListProductSkuPrice(ArrayList<ProductSkuPrice> paramArrayList)
  {
    this.listProductSkuPrice = paramArrayList;
  }
  
  public void setPrice_Change_Detail(Price_Change_Detail[] paramArrayOfPrice_Change_Detail)
  {
    this.price_Change_Detail = paramArrayOfPrice_Change_Detail;
  }
  
  public void setPrice_Change_Header(Price_Change_Header[] paramArrayOfPrice_Change_Header)
  {
    this.price_Change_Header = paramArrayOfPrice_Change_Header;
  }
  
  public void setProduct(Product paramProduct)
  {
    this.product = paramProduct;
  }
  
  public void setProductStatusStore(ArrayList<ProductStatusStore> paramArrayList)
  {
    this.productStatusStore = paramArrayList;
  }
  
  public void setStock_Detail(Stock_Detail paramStock_Detail)
  {
    this.stock_Detail = paramStock_Detail;
  }
  
  public void setStock_Header(Stock_Header paramStock_Header)
  {
    this.stock_Header = paramStock_Header;
  }
  
  public void setTransactionLog(TransactionLog paramTransactionLog)
  {
    this.transactionLog = paramTransactionLog;
  }
}