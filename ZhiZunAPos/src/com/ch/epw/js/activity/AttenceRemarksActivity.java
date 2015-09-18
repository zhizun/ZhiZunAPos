package com.ch.epw.js.activity;

import java.util.ArrayList;
import java.util.List;
import com.zhizun.pos.R;
import com.zhizun.pos.bean.AttenceDetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class AttenceRemarksActivity extends FragmentActivity implements OnClickListener{

	/**
	 * 四个导航按钮
	 */
	Button buttonOne;
	Button buttonTwo;
	Button buttonThree;
	/**
	 * 作为页面容器的ViewPager
	 */
	ViewPager mViewPager;
	/**
	 * 页面集合
	 */
	List<Fragment> fragmentList;
	
	/**
	 * 四个Fragment（页面）
	 */
	AttenceRemarksFragmentLate oneFragment;
	AttenceRemarksFragment_Qj twoFragment;
	AttenceRemarksFragment_Qt threeFragment;
	
	//覆盖层
//	ImageView imageviewOvertab;
	
	//屏幕宽度
	int screenWidth;
	//当前选中的项
	int currenttab=-1;
	private ArrayList<AttenceDetail> listItems;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bz_teacher);
		WindowManager m = getWindowManager();    
	    Display d = m.getDefaultDisplay();  //为获取屏幕宽、高    
	    LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值    
	    p.height = (int) (d.getHeight() * 0.5);   //高度设置为屏幕的1.0   
	    p.width = (int) (d.getWidth() * 0.9);    //宽度设置为屏幕的0.8 
	    listItems = (ArrayList<AttenceDetail>) getIntent().getSerializableExtra("listItems");
	    position = getIntent().getIntExtra("position", 0);
		buttonOne=(Button)findViewById(R.id.btn_cd);
		buttonTwo=(Button)findViewById(R.id.btn_qj);
		buttonThree=(Button)findViewById(R.id.btn_qt);
		
		buttonOne.setOnClickListener(this);
		buttonTwo.setOnClickListener(this);
		buttonThree.setOnClickListener(this);
		
		mViewPager=(ViewPager) findViewById(R.id.viewpager);
		Bundle bu=new Bundle();
		bu.putSerializable("remarks", listItems);
		bu.putInt("position", position);
		fragmentList=new ArrayList<Fragment>();
		oneFragment=new AttenceRemarksFragmentLate();
		twoFragment=new AttenceRemarksFragment_Qj();
		threeFragment=new AttenceRemarksFragment_Qt();
		oneFragment.setArguments(bu);
		twoFragment.setArguments(bu);
		threeFragment.setArguments(bu);
		
		fragmentList.add(oneFragment);
		fragmentList.add(twoFragment);
		fragmentList.add(threeFragment);
		
		screenWidth=getResources().getDisplayMetrics().widthPixels;
		buttonTwo.measure(0, 0);
		mViewPager.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));
		AttenceDetail attenceLsit=listItems.get(position);
	    if (attenceLsit.getRemarks().size()!=0) {
	    	String type=attenceLsit.getRemarks().get(0).getTypt();//获取type 0，迟到  1，请假  2，其他
//	    	changeView(Integer.parseInt(type));
	    	if (type.equals("0")) {
	    		changeView(0);
	    		buttonOne.setBackgroundColor(getResources().getColor(R.color.white));
	    		buttonTwo.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
	    		buttonThree.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
			}else if (type.equals("1")) {
				changeView(1);
				buttonOne.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
				buttonTwo.setBackgroundColor(getResources().getColor(R.color.white));
				buttonThree.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
			}else {
				changeView(2);
				buttonOne.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
				buttonTwo.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
				buttonThree.setBackgroundColor(getResources().getColor(R.color.white));
			}
		}else {
			changeView(0);
		}
	}

	/**
	 * 定义自己的ViewPager适配器。
	 */
	class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter
	{

		public MyFrageStatePagerAdapter(FragmentManager fm) 
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}
		
		/**
		 * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动
		 */
		@Override
		public void finishUpdate(ViewGroup container) 
		{
			super.finishUpdate(container);//这句话要放在最前面，否则会报错
			//获取当前的视图是位于ViewGroup的第几个位置，用来更新对应的覆盖层所在的位置
			int currentItem=mViewPager.getCurrentItem();
			if (currentItem==currenttab)
			{
				return ;
			}
			imageMove(mViewPager.getCurrentItem());
			currenttab=mViewPager.getCurrentItem();
			switch (currentItem) {
			case 0:
				buttonOne.setBackgroundColor(getResources().getColor(R.color.white));
	    		buttonTwo.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
	    		buttonThree.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
				break;
			case 1:
				buttonOne.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
				buttonTwo.setBackgroundColor(getResources().getColor(R.color.white));
				buttonThree.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
				break;
			case 2:
				buttonOne.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
				buttonTwo.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
				buttonThree.setBackgroundColor(getResources().getColor(R.color.white));
				break;

			default:
				break;
			}
		}
		
	}
	
	/**
	 * 移动覆盖层
	 * @param moveToTab 目标Tab，也就是要移动到的导航选项按钮的位置
	 * 第一个导航按钮对应0，第二个对应1，以此类推
	 */
	private void imageMove(int moveToTab)
	{
		int startPosition=0;
		int movetoPosition=0;
		
		startPosition=currenttab*(screenWidth/3);
		movetoPosition=moveToTab*(screenWidth/3);
		//平移动画
		TranslateAnimation translateAnimation=new TranslateAnimation(startPosition,movetoPosition, 0, 0);
		translateAnimation.setFillAfter(true);
		translateAnimation.setDuration(200);
	}
	
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_cd:
			buttonOne.setBackgroundColor(getResources().getColor(R.color.white));
			buttonTwo.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
			buttonThree.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
			changeView(0);
			break;
		case R.id.btn_qj:
			buttonOne.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
			buttonTwo.setBackgroundColor(getResources().getColor(R.color.white));
			buttonThree.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
			changeView(1);
			break;
		case R.id.btn_qt:
			buttonOne.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
			buttonTwo.setBackgroundColor(getResources().getColor(R.color.darkblue_3));
			buttonThree.setBackgroundColor(getResources().getColor(R.color.white));
			changeView(2);
			break;
		default:
			break;
		}
	}
	//手动设置ViewPager要显示的视图
	private void changeView(int desTab)
	{
		mViewPager.setCurrentItem(desTab, true);
	}

}
