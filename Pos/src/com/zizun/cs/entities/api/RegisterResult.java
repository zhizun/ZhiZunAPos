package com.zizun.cs.entities.api;

import com.zizun.cs.entities.S_Merchant;
import com.zizun.cs.entities.S_Module;
import com.zizun.cs.entities.S_User;
import com.zizun.cs.entities.Store;
import com.zizun.cs.entities.StoreStaff;
import java.util.List;

public class RegisterResult
  extends BaseResult
{
  private static final long serialVersionUID = -2891561049050904900L;
  public RegisterResultData Data;
  
  public class RegisterResultData
  {
    public S_Merchant S_Merchant;
    public List<S_Module> S_Module;
    public S_User S_User;
    public List<Store> Store;
    public List<StoreStaff> StoreStaff;
    
    public RegisterResultData() {}
  }
}