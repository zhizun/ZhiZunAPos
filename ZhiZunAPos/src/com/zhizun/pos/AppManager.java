package com.zhizun.pos;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * 创建日期：2014-12-15  上午9:47:04
 * 作用：    
 * 修改
 * ===================================================
 *  修改人             修改日期                原因(描述)
 * ===================================================
 */
public class AppManager {
	
	private static Stack<Activity> activityStack;
	private static AppManager instance;
	
	private AppManager(){}
	/**
	 * 单一实例
	 */
	public static AppManager getAppManager(){
		if(instance==null){
			instance=new AppManager();
		}
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		return instance;
	}
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity){
		activityStack.add(activity);
	}
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity(){
		Activity activity=activityStack.lastElement();
		return activity;
	}
	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
	}
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		Activity activity = getActivityInstanceByClassType(cls);
		if(activity != null){
			finishActivity(activity);
		}
	}
	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	
	/**
	 * 启动指定名称的activity
	 * （如果已存在则不执行任何操作）
	 */
	public void startActivity(Context context, String clsName){
		Class objCls = null;
		try{
			objCls = Class.forName(clsName);
		}catch(ClassNotFoundException e){
			//
		}
		if(objCls != null && !isActivityRunning(objCls)){
			Intent intent = new Intent(context, objCls);
			context.startActivity(intent);
		}
	}
	
	/**
	 * 判断指定的activity是否在任务栈
	 * @param clsName
	 * @return
	 */
	public boolean isActivityRunning(Class<?> objCls){
		boolean isRunning = false;
		for (Activity activity : activityStack) {
			if(activity.getClass().isAssignableFrom(objCls)){
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}
	
	/**
	 * 根据activity的class获得其在当前activityStack中的一个实例，并返回
	 * 如果没有，则返回null
	 * @param objCls
	 * @return
	 */
	public Activity getActivityInstanceByClassType(Class<?> objCls){
		for (Activity activity : activityStack) {
			if(activity.getClass().isAssignableFrom(objCls)){
				return activity;
			}
		}
		return null;
	}
	
	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			Log.i("tag", "程序要关闭了！");
			finishAllActivity();
			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {	}
	}
}