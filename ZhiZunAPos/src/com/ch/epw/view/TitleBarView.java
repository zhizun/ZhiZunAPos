package com.ch.epw.view;

import com.zhizun.pos.R;
import com.zhizun.pos.widget.circularimage.CircularImage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 自定义标题栏
 * 创建日期：2014-11-12  下午2:06:45
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */
public class TitleBarView extends RelativeLayout {

	private static final String TAG = TitleBarView.class.getName();
	private Context mContext;
	private Button btnLeft;
	private Button btnRight;
//	private Button btn_titleLeft;
//	private Button btn_titleRight;
	private CircularImage ivLeft;
	private TextView tv_center,tv_right,tv_left;
	
	private RelativeLayout rl_area_right, rl_area_left;

//	private LinearLayout common_constact;
	public TitleBarView(Context context){
		super(context);
		mContext=context;
		initView();
	}
	
	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		initView();
	}
	
	private void initView(){
		LayoutInflater.from(mContext).inflate(R.layout.common_title_bar, this);
		btnLeft=(Button) findViewById(R.id.title_btn_left);
		ivLeft=(CircularImage) this.findViewById(R.id.title_iv_left);
		btnRight=(Button) findViewById(R.id.title_btn_right);
		tv_right=(TextView) this.findViewById(R.id.title_tv_right);
		tv_left=(TextView) this.findViewById(R.id.title_tv_left);
//		btn_titleLeft=(Button) findViewById(R.id.constact_group);
//		btn_titleRight=(Button) findViewById(R.id.constact_all);
		tv_center=(TextView) findViewById(R.id.title_txt);
//		common_constact=(LinearLayout) findViewById(R.id.common_constact);
		
		rl_area_right = (RelativeLayout)this.findViewById(R.id.rl_area_right);
		rl_area_left = (RelativeLayout)this.findViewById(R.id.rl_area_left);
	}
	
	public void setCommonTitle(int LeftVisibility,int centerVisibility,int center1Visibilter,int rightVisibility){
		btnLeft.setVisibility(LeftVisibility);
		btnRight.setVisibility(rightVisibility);
		tv_center.setVisibility(centerVisibility);
//		common_constact.setVisibility(center1Visibilter);
		
	}
	public void setCommonTitle(int LeftBtnVisibility,int LeftTextViewVisibility,int leftImageViewVisibility,int centerTextVisibility,int rightTextVisibility,int rightBtnVisibility){
		btnLeft.setVisibility(LeftBtnVisibility);
		ivLeft.setVisibility(leftImageViewVisibility);
		tv_right.setVisibility(rightTextVisibility);
		tv_left.setVisibility(LeftTextViewVisibility);
		btnRight.setVisibility(rightBtnVisibility);
		tv_center.setVisibility(centerTextVisibility);
//		common_constact.setVisibility(center1Visibilter);
		
	}
	
	public void setBtnLeft(int icon,int txtRes){
		
		btnLeft.setText(txtRes);
		btnLeft.setBackgroundResource(icon);
	}
	
	public void setBtnLeft(int txtRes){
		btnLeft.setText(txtRes);
	}

	public void setBtnRight(int icon){
		btnRight.setBackgroundResource(icon);
	}
	public void setRightText(int txtRes){
		tv_right.setText(txtRes);
	}
	public void setRightText(String txt){
		tv_right.setText(txt.trim());
	}
	public void setLeftText(int txtRes){
		tv_left.setText(txtRes);
	}
	public void setLeftText(String txt){
		tv_left.setText(txt.trim());
	}
//	public void setTitleLeft(int resId){
//		btn_titleLeft.setText(resId);
//	}
//	
//	public void setTitleRight(int resId){
//		btn_titleRight.setText(resId);
//	}
	

	
	public void setTitleText(int txtRes){
		tv_center.setText(txtRes);
	}
	public void setTitleText(String txt){
		tv_center.setText(txt.trim());
	}
	
	public void setBtnLeftOnclickListener(OnClickListener listener){
		btnLeft.setOnClickListener(listener);
	}
	
	public void setBtnRightOnclickListener(OnClickListener listener){
		btnRight.setOnClickListener(listener);
	}
	
	public RelativeLayout getLeftClickableArea(){
		return rl_area_left;
	}
	
	public RelativeLayout getRightClickableArea(){
		return rl_area_right;
	}

	public void setBarLeftOnclickListener(OnClickListener listener){
		rl_area_left.setOnClickListener(listener);
		if(btnLeft!=null){
			btnLeft.setOnClickListener(listener);
		}
		if(tv_left!=null){
			tv_left.setOnClickListener(listener);
		}
		if(ivLeft!=null){
			ivLeft.setOnClickListener(listener);
		}
	}
	
	public void setBarRightOnclickListener(OnClickListener listener){
		rl_area_right.setOnClickListener(listener);
		if(btnRight!=null){
			btnRight.setOnClickListener(listener);
		}
		if(tv_right!=null){
			tv_right.setOnClickListener(listener);
		}
	}
	
//	public Button getTitleLeft(){
//		return btn_titleLeft;
//	}
//	
//	public Button getTitleRight(){
//		return btn_titleRight;
//	}
	public Button getBtnRight(){
		return btnRight;
	}
	public CircularImage getIvLeft(){
		return ivLeft;
	}
	
	public TextView getTitileTextView(){
		return tv_center;
	}
	public TextView getLeftTextView(){
		return tv_left;
	}
	public TextView getRightTextView(){
		return tv_right;
	}
	public void destoryView(){
		btnLeft.setText(null);
		btnRight.setText(null);
		tv_center.setText(null);
	}

}
