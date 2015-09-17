package com.zizun.cs.entities.api;

import com.zizun.cs.entities.S_Merchant;
import com.zizun.cs.entities.S_Module;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import java.util.List;

public class LoginResult
  extends BaseResult
{
  private static final long serialVersionUID = 5837581278626922772L;
  public LoginResultData Data;
  
  public LoginResultData getData()
  {
    return this.Data;
  }
  
  public void setData(LoginResultData paramLoginResultData)
  {
    this.Data = paramLoginResultData;
  }
  
  public class LoginResultData
  {
    public S_Merchant S_Merchant;
    public List<S_Module> S_Module;
    public S_User S_User;
    public List<Store> Store;
    
    public LoginResultData() {}
    
    public S_Merchant getS_Merchant()
    {
      return this.S_Merchant;
    }
    
    public List<S_Module> getS_Module()
    {
      return this.S_Module;
    }
    
    public S_User getS_User()
    {
      return this.S_User;
    }
    
    public List<Store> getStore()
    {
      return this.Store;
    }
    
    public void setS_Merchant(S_Merchant paramS_Merchant)
    {
      this.S_Merchant = paramS_Merchant;
    }
    
    public void setS_Module(List<S_Module> paramList)
    {
      this.S_Module = paramList;
    }
    
    public void setS_User(S_User paramS_User)
    {
      this.S_User = paramS_User;
    }
    
    public void setStore(List<Store> paramList)
    {
      this.Store = paramList;
    }
  }
}