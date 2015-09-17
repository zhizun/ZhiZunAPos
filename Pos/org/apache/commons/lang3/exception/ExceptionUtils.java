package org.apache.commons.lang3.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

public class ExceptionUtils
{
  private static final String[] CAUSE_METHOD_NAMES = { "getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", "getLinkedCause", "getThrowable" };
  static final String WRAPPED_MARKER = " [wrapped] ";
  
  @Deprecated
  public static Throwable getCause(Throwable paramThrowable)
  {
    return getCause(paramThrowable, CAUSE_METHOD_NAMES);
  }
  
  @Deprecated
  public static Throwable getCause(Throwable paramThrowable, String[] paramArrayOfString)
  {
    if (paramThrowable == null)
    {
      paramArrayOfString = null;
      return paramArrayOfString;
    }
    String[] arrayOfString = paramArrayOfString;
    if (paramArrayOfString == null) {
      arrayOfString = CAUSE_METHOD_NAMES;
    }
    int j = arrayOfString.length;
    int i = 0;
    for (;;)
    {
      if (i >= j) {
        break label62;
      }
      paramArrayOfString = arrayOfString[i];
      if (paramArrayOfString != null)
      {
        Throwable localThrowable = getCauseUsingMethodName(paramThrowable, paramArrayOfString);
        paramArrayOfString = localThrowable;
        if (localThrowable != null) {
          break;
        }
      }
      i += 1;
    }
    label62:
    return null;
  }
  
  private static Throwable getCauseUsingMethodName(Throwable paramThrowable, String paramString)
  {
    Object localObject = null;
    try
    {
      paramString = paramThrowable.getClass().getMethod(paramString, new Class[0]);
      if ((paramString != null) && (Throwable.class.isAssignableFrom(paramString.getReturnType()))) {}
      try
      {
        paramThrowable = (Throwable)paramString.invoke(paramThrowable, new Object[0]);
        return paramThrowable;
      }
      catch (InvocationTargetException paramThrowable)
      {
        return null;
      }
      catch (IllegalArgumentException paramThrowable)
      {
        for (;;) {}
      }
      catch (IllegalAccessException paramThrowable)
      {
        for (;;) {}
      }
    }
    catch (SecurityException paramString)
    {
      for (;;)
      {
        paramString = (String)localObject;
      }
    }
    catch (NoSuchMethodException paramString)
    {
      for (;;)
      {
        paramString = (String)localObject;
      }
    }
  }
  
  @Deprecated
  public static String[] getDefaultCauseMethodNames()
  {
    return (String[])ArrayUtils.clone(CAUSE_METHOD_NAMES);
  }
  
  public static String getMessage(Throwable paramThrowable)
  {
    if (paramThrowable == null) {
      return "";
    }
    String str = ClassUtils.getShortClassName(paramThrowable, null);
    paramThrowable = paramThrowable.getMessage();
    return str + ": " + StringUtils.defaultString(paramThrowable);
  }
  
  public static Throwable getRootCause(Throwable paramThrowable)
  {
    paramThrowable = getThrowableList(paramThrowable);
    if (paramThrowable.size() < 2) {
      return null;
    }
    return (Throwable)paramThrowable.get(paramThrowable.size() - 1);
  }
  
  public static String getRootCauseMessage(Throwable paramThrowable)
  {
    Throwable localThrowable2 = getRootCause(paramThrowable);
    Throwable localThrowable1 = localThrowable2;
    if (localThrowable2 == null) {
      localThrowable1 = paramThrowable;
    }
    return getMessage(localThrowable1);
  }
  
  public static String[] getRootCauseStackTrace(Throwable paramThrowable)
  {
    if (paramThrowable == null) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    }
    Throwable[] arrayOfThrowable = getThrowables(paramThrowable);
    int k = arrayOfThrowable.length;
    ArrayList localArrayList = new ArrayList();
    paramThrowable = getStackFrameList(arrayOfThrowable[(k - 1)]);
    int i = k;
    Throwable localThrowable1 = paramThrowable;
    int m = i - 1;
    if (m >= 0)
    {
      paramThrowable = localThrowable1;
      if (m != 0)
      {
        paramThrowable = getStackFrameList(arrayOfThrowable[(m - 1)]);
        removeCommonFrames(localThrowable1, paramThrowable);
      }
      Throwable localThrowable2 = paramThrowable;
      if (m == k - 1) {
        localArrayList.add(arrayOfThrowable[m].toString());
      }
      for (;;)
      {
        int j = 0;
        for (;;)
        {
          i = m;
          paramThrowable = localThrowable2;
          if (j >= localThrowable1.size()) {
            break;
          }
          localArrayList.add(localThrowable1.get(j));
          j += 1;
        }
        localArrayList.add(" [wrapped] " + arrayOfThrowable[m].toString());
      }
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  static List<String> getStackFrameList(Throwable paramThrowable)
  {
    paramThrowable = new StringTokenizer(getStackTrace(paramThrowable), SystemUtils.LINE_SEPARATOR);
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    do
    {
      for (;;)
      {
        if (!paramThrowable.hasMoreTokens()) {
          return localArrayList;
        }
        String str = paramThrowable.nextToken();
        int j = str.indexOf("at");
        if ((j == -1) || (str.substring(0, j).trim().length() != 0)) {
          break;
        }
        i = 1;
        localArrayList.add(str);
      }
    } while (i == 0);
    return localArrayList;
  }
  
  static String[] getStackFrames(String paramString)
  {
    paramString = new StringTokenizer(paramString, SystemUtils.LINE_SEPARATOR);
    ArrayList localArrayList = new ArrayList();
    while (paramString.hasMoreTokens()) {
      localArrayList.add(paramString.nextToken());
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public static String[] getStackFrames(Throwable paramThrowable)
  {
    if (paramThrowable == null) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    }
    return getStackFrames(getStackTrace(paramThrowable));
  }
  
  public static String getStackTrace(Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    paramThrowable.printStackTrace(new PrintWriter(localStringWriter, true));
    return localStringWriter.getBuffer().toString();
  }
  
  public static int getThrowableCount(Throwable paramThrowable)
  {
    return getThrowableList(paramThrowable).size();
  }
  
  public static List<Throwable> getThrowableList(Throwable paramThrowable)
  {
    ArrayList localArrayList = new ArrayList();
    while ((paramThrowable != null) && (!localArrayList.contains(paramThrowable)))
    {
      localArrayList.add(paramThrowable);
      paramThrowable = getCause(paramThrowable);
    }
    return localArrayList;
  }
  
  public static Throwable[] getThrowables(Throwable paramThrowable)
  {
    paramThrowable = getThrowableList(paramThrowable);
    return (Throwable[])paramThrowable.toArray(new Throwable[paramThrowable.size()]);
  }
  
  private static int indexOf(Throwable paramThrowable, Class<?> paramClass, int paramInt, boolean paramBoolean)
  {
    if ((paramThrowable == null) || (paramClass == null))
    {
      paramInt = -1;
      return paramInt;
    }
    int i = paramInt;
    if (paramInt < 0) {
      i = 0;
    }
    paramThrowable = getThrowables(paramThrowable);
    if (i >= paramThrowable.length) {
      return -1;
    }
    if (paramBoolean) {
      for (;;)
      {
        if (i >= paramThrowable.length) {
          break label106;
        }
        paramInt = i;
        if (paramClass.isAssignableFrom(paramThrowable[i].getClass())) {
          break;
        }
        i += 1;
      }
    }
    for (;;)
    {
      if (i >= paramThrowable.length) {
        break label106;
      }
      paramInt = i;
      if (paramClass.equals(paramThrowable[i].getClass())) {
        break;
      }
      i += 1;
    }
    label106:
    return -1;
  }
  
  public static int indexOfThrowable(Throwable paramThrowable, Class<?> paramClass)
  {
    return indexOf(paramThrowable, paramClass, 0, false);
  }
  
  public static int indexOfThrowable(Throwable paramThrowable, Class<?> paramClass, int paramInt)
  {
    return indexOf(paramThrowable, paramClass, paramInt, false);
  }
  
  public static int indexOfType(Throwable paramThrowable, Class<?> paramClass)
  {
    return indexOf(paramThrowable, paramClass, 0, true);
  }
  
  public static int indexOfType(Throwable paramThrowable, Class<?> paramClass, int paramInt)
  {
    return indexOf(paramThrowable, paramClass, paramInt, true);
  }
  
  public static void printRootCauseStackTrace(Throwable paramThrowable)
  {
    printRootCauseStackTrace(paramThrowable, System.err);
  }
  
  public static void printRootCauseStackTrace(Throwable paramThrowable, PrintStream paramPrintStream)
  {
    if (paramThrowable == null) {
      return;
    }
    if (paramPrintStream == null) {
      throw new IllegalArgumentException("The PrintStream must not be null");
    }
    paramThrowable = getRootCauseStackTrace(paramThrowable);
    int j = paramThrowable.length;
    int i = 0;
    while (i < j)
    {
      paramPrintStream.println(paramThrowable[i]);
      i += 1;
    }
    paramPrintStream.flush();
  }
  
  public static void printRootCauseStackTrace(Throwable paramThrowable, PrintWriter paramPrintWriter)
  {
    if (paramThrowable == null) {
      return;
    }
    if (paramPrintWriter == null) {
      throw new IllegalArgumentException("The PrintWriter must not be null");
    }
    paramThrowable = getRootCauseStackTrace(paramThrowable);
    int j = paramThrowable.length;
    int i = 0;
    while (i < j)
    {
      paramPrintWriter.println(paramThrowable[i]);
      i += 1;
    }
    paramPrintWriter.flush();
  }
  
  public static void removeCommonFrames(List<String> paramList1, List<String> paramList2)
  {
    if ((paramList1 == null) || (paramList2 == null)) {
      throw new IllegalArgumentException("The List must not be null");
    }
    int j = paramList1.size() - 1;
    int i = paramList2.size() - 1;
    while ((j >= 0) && (i >= 0))
    {
      if (((String)paramList1.get(j)).equals((String)paramList2.get(i))) {
        paramList1.remove(j);
      }
      j -= 1;
      i -= 1;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\exception\ExceptionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */