package com.zhizun.pos.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WaitProgressDialog
  extends BaseAlertDialog
{
  private ImageView mImaRound;
  private TextView mTxtTitle;
  
  public WaitProgressDialog(Context paramContext)
  {
    super(paramContext, progress_dialog_theme);
  }
  
  public WaitProgressDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }
  
  public void open()
  {
    super.open();
    setHeart(dialog_progress);
    this.mTxtTitle = ((TextView)this.mAlertDialog.findViewById(dialog_progress_txtTitle));
    this.mImaRound = ((ImageView)this.mAlertDialog.findViewById(dialog_progress_imgRound));
    Animation localAnimation = AnimationUtils.loadAnimation(getContext(), loading_anim);
    this.mImaRound.startAnimation(localAnimation);
  }
  
  public void open(boolean paramBoolean)
  {
    super.open(paramBoolean);
    setHeart(dialog_progress);
    this.mTxtTitle = ((TextView)this.mAlertDialog.findViewById(dialog_progress_txtTitle));
    this.mImaRound = ((ImageView)this.mAlertDialog.findViewById(dialog_progress_imgRound));
    Animation localAnimation = AnimationUtils.loadAnimation(getContext(), loading_anim);
    this.mImaRound.startAnimation(localAnimation);
  }
  
  public void setMessage(String paramString)
  {
    this.mTxtTitle.setText(paramString);
  }
}