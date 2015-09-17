package com.zhizun.pos.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import com.zizun.cs.common.utils.LogUtil;
import java.util.LinkedList;
import java.util.List;

public class StoreApplication
  extends Application
  implements Thread.UncaughtExceptionHandler
{
  private static Context mContext;
  public static boolean mIsMerchant = true;
  private static List<Activity> mListActivity = new LinkedList<Activity>();
  
  public static void addActivity(Activity paramActivity)
  {
    mListActivity.add(paramActivity);
  }
  
  /* Error */
  public static void exitApplication()
  {
    // Byte code:
    //   0: getstatic 22	com/zhizun/pos/app/StoreApplication:mListActivity	Ljava/util/List;
    //   3: invokeinterface 41 1 0
    //   8: astore_1
    //   9: aload_1
    //   10: invokeinterface 47 1 0
    //   15: istore_0
    //   16: iload_0
    //   17: ifne +8 -> 25
    //   20: iconst_0
    //   21: invokestatic 53	java/lang/System:exit	(I)V
    //   24: return
    //   25: aload_1
    //   26: invokeinterface 57 1 0
    //   31: checkcast 59	android/app/Activity
    //   34: astore_2
    //   35: aload_2
    //   36: ifnull -27 -> 9
    //   39: aload_2
    //   40: invokevirtual 62	android/app/Activity:finish	()V
    //   43: goto -34 -> 9
    //   46: astore_1
    //   47: aload_1
    //   48: invokevirtual 65	java/lang/Exception:printStackTrace	()V
    //   51: iconst_0
    //   52: invokestatic 53	java/lang/System:exit	(I)V
    //   55: return
    //   56: astore_1
    //   57: iconst_0
    //   58: invokestatic 53	java/lang/System:exit	(I)V
    //   61: aload_1
    //   62: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   15	2	0	bool	boolean
    //   8	18	1	localIterator	java.util.Iterator
    //   46	2	1	localException	Exception
    //   56	6	1	localObject	Object
    //   34	6	2	localActivity	Activity
    // Exception table:
    //   from	to	target	type
    //   0	9	46	java/lang/Exception
    //   9	16	46	java/lang/Exception
    //   25	35	46	java/lang/Exception
    //   39	43	46	java/lang/Exception
    //   0	9	56	finally
    //   9	16	56	finally
    //   25	35	56	finally
    //   39	43	56	finally
    //   47	51	56	finally
  }
  
  public static Context getContext()
  {
    return mContext;
  }
  
  public void onCreate()
  {
    super.onCreate();
    mContext = getApplicationContext();
    com.lidroid.xutils.util.LogUtils.allowI = LogUtil.mIsLog;
    com.lidroid.xutils.util.LogUtils.allowD = LogUtil.mIsLog;
  }
  
  public void onLowMemory()
  {
    super.onLowMemory();
    System.gc();
  }
  
  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    exitApplication();
    Process.killProcess(Process.myPid());
    System.exit(1);
  }
}
