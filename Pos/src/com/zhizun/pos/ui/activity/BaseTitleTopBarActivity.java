package com.zhizun.pos.ui.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import com.zhizun.pos.interfaces.TopBarFunctional;
import com.zhizun.pos.ui.widget.TitleTopBar;

public abstract class BaseTitleTopBarActivity
  extends BaseActivity
  implements TopBarFunctional
{
  protected TitleTopBar mTopBar;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903191);
    this.mTopBar = ((TitleTopBar)findViewById(2131362597));
    addBody((RelativeLayout)findViewById(2131362598));
  }
}