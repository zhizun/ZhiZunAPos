package com.ch.epw.task;

import com.baidu.location.BDLocation;
import com.zhizun.pos.base.BaseBean;


public abstract class TaskCallBack {
	
	//所有任务接口应该实现onTaskFinshed(BaseBean result)，不再实现onTaskFinshed()方法
	@Deprecated
	public void onTaskFinshed(){};

	public void onTaskFinshed(BaseBean result){};
	
	public void onTaskFailed(){};
	
	//暂时新增方法onLocated。该方法将来可以以 onTaskFinshed(Object o) 替代
	public void onLocated(BDLocation location){};
	
}
