package com.zhizun.pos.ui.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import com.zhizun.pos.interfaces.TopBarFunctional;
import com.zhizun.pos.ui.widget.TitleTopBar;

public abstract class BaseFragmentTitleTopBarActivity
  extends BaseFragmentActivity
  implements TopBarFunctional
{
  protected TitleTopBar mTopBar;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(layout_titletopbar);
    this.mTopBar = ((TitleTopBar)findViewById(layout_titletopbar_bar));
    addBody((RelativeLayout)findViewById(layout_titletopbar_relBody));
  }
}