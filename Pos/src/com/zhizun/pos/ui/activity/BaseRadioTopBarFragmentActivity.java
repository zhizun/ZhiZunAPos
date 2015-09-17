package com.zhizun.pos.ui.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import com.zhizun.pos.interfaces.TopBarFunctional;
import com.zhizun.pos.ui.widget.RadioTopBar;

public abstract class BaseRadioTopBarFragmentActivity
  extends BaseFragmentActivity
  implements TopBarFunctional
{
  protected RadioTopBar mTopBar;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903182);
    this.mTopBar = ((RadioTopBar)findViewById(2131362585));
    addBody((RelativeLayout)findViewById(2131362586));
  }
}