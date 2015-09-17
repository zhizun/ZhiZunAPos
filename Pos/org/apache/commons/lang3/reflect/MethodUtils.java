package org.apache.commons.lang3.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;

public class MethodUtils
{
  public static Method getAccessibleMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
  {
    try
    {
      paramClass = getAccessibleMethod(paramClass.getMethod(paramString, paramVarArgs));
      return paramClass;
    }
    catch (NoSuchMethodException paramClass) {}
    return null;
  }
  
  public static Method getAccessibleMethod(Method paramMethod)
  {
    Method localMethod;
    if (!MemberUtils.isAccessible(paramMethod)) {
      localMethod = null;
    }
    Class localClass;
    String str;
    Class[] arrayOfClass;
    do
    {
      do
      {
        return localMethod;
        localClass = paramMethod.getDeclaringClass();
        localMethod = paramMethod;
      } while (Modifier.isPublic(localClass.getModifiers()));
      str = paramMethod.getName();
      arrayOfClass = paramMethod.getParameterTypes();
      paramMethod = getAccessibleMethodFromInterfaceNest(localClass, str, arrayOfClass);
      localMethod = paramMethod;
    } while (paramMethod != null);
    return getAccessibleMethodFromSuperclass(localClass, str, arrayOfClass);
  }
  
  private static Method getAccessibleMethodFromInterfaceNest(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
  {
    Object localObject1 = null;
    Object localObject2 = paramClass;
    paramClass = (Class<?>)localObject1;
    Class[] arrayOfClass;
    int i;
    if (localObject2 != null)
    {
      arrayOfClass = ((Class)localObject2).getInterfaces();
      i = 0;
      for (;;)
      {
        localObject1 = paramClass;
        if (i >= arrayOfClass.length) {
          break label74;
        }
        if (Modifier.isPublic(arrayOfClass[i].getModifiers())) {
          break;
        }
        label46:
        i += 1;
      }
    }
    try
    {
      localObject1 = arrayOfClass[i].getDeclaredMethod(paramString, paramVarArgs);
      paramClass = (Class<?>)localObject1;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      for (;;) {}
    }
    if (paramClass != null) {
      localObject1 = paramClass;
    }
    for (;;)
    {
      label74:
      localObject2 = ((Class)localObject2).getSuperclass();
      paramClass = (Class<?>)localObject1;
      break;
      localObject1 = getAccessibleMethodFromInterfaceNest(arrayOfClass[i], paramString, paramVarArgs);
      paramClass = (Class<?>)localObject1;
      if (localObject1 == null) {
        break label46;
      }
    }
    return paramClass;
  }
  
  private static Method getAccessibleMethodFromSuperclass(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
  {
    Object localObject2 = null;
    for (paramClass = paramClass.getSuperclass();; paramClass = paramClass.getSuperclass())
    {
      Object localObject1 = localObject2;
      if ((paramClass == null) || (Modifier.isPublic(paramClass.getModifiers()))) {
        try
        {
          localObject1 = paramClass.getMethod(paramString, paramVarArgs);
          return (Method)localObject1;
        }
        catch (NoSuchMethodException paramClass)
        {
          return null;
        }
      }
    }
  }
  
  public static Method getMatchingAccessibleMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
  {
    try
    {
      Method localMethod1 = paramClass.getMethod(paramString, paramVarArgs);
      MemberUtils.setAccessibleWorkaround(localMethod1);
      return localMethod1;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Object localObject = null;
      Method[] arrayOfMethod = paramClass.getMethods();
      int j = arrayOfMethod.length;
      int i = 0;
      for (paramClass = (Class<?>)localObject; i < j; paramClass = (Class<?>)localObject)
      {
        Method localMethod2 = arrayOfMethod[i];
        localObject = paramClass;
        if (localMethod2.getName().equals(paramString))
        {
          localObject = paramClass;
          if (ClassUtils.isAssignable(paramVarArgs, localMethod2.getParameterTypes(), true))
          {
            localMethod2 = getAccessibleMethod(localMethod2);
            localObject = paramClass;
            if (localMethod2 != null) {
              if (paramClass != null)
              {
                localObject = paramClass;
                if (MemberUtils.compareParameterTypes(localMethod2.getParameterTypes(), paramClass.getParameterTypes(), paramVarArgs) >= 0) {}
              }
              else
              {
                localObject = localMethod2;
              }
            }
          }
        }
        i += 1;
      }
      if (paramClass != null) {
        MemberUtils.setAccessibleWorkaround(paramClass);
      }
    }
    return paramClass;
  }
  
  public static Object invokeExactMethod(Object paramObject, String paramString, Object... paramVarArgs)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
    Object[] arrayOfObject = paramVarArgs;
    if (paramVarArgs == null) {
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    int j = arrayOfObject.length;
    paramVarArgs = new Class[j];
    int i = 0;
    while (i < j)
    {
      paramVarArgs[i] = arrayOfObject[i].getClass();
      i += 1;
    }
    return invokeExactMethod(paramObject, paramString, arrayOfObject, paramVarArgs);
  }
  
  public static Object invokeExactMethod(Object paramObject, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
    Object[] arrayOfObject = paramArrayOfObject;
    if (paramArrayOfObject == null) {
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    paramArrayOfObject = paramArrayOfClass;
    if (paramArrayOfClass == null) {
      paramArrayOfObject = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    paramArrayOfObject = getAccessibleMethod(paramObject.getClass(), paramString, paramArrayOfObject);
    if (paramArrayOfObject == null) {
      throw new NoSuchMethodException("No such accessible method: " + paramString + "() on object: " + paramObject.getClass().getName());
    }
    return paramArrayOfObject.invoke(paramObject, arrayOfObject);
  }
  
  public static Object invokeExactStaticMethod(Class<?> paramClass, String paramString, Object... paramVarArgs)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
    Object[] arrayOfObject = paramVarArgs;
    if (paramVarArgs == null) {
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    int j = arrayOfObject.length;
    paramVarArgs = new Class[j];
    int i = 0;
    while (i < j)
    {
      paramVarArgs[i] = arrayOfObject[i].getClass();
      i += 1;
    }
    return invokeExactStaticMethod(paramClass, paramString, arrayOfObject, paramVarArgs);
  }
  
  public static Object invokeExactStaticMethod(Class<?> paramClass, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
    Object[] arrayOfObject = paramArrayOfObject;
    if (paramArrayOfObject == null) {
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    paramArrayOfObject = paramArrayOfClass;
    if (paramArrayOfClass == null) {
      paramArrayOfObject = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    paramArrayOfObject = getAccessibleMethod(paramClass, paramString, paramArrayOfObject);
    if (paramArrayOfObject == null) {
      throw new NoSuchMethodException("No such accessible method: " + paramString + "() on class: " + paramClass.getName());
    }
    return paramArrayOfObject.invoke(null, arrayOfObject);
  }
  
  public static Object invokeMethod(Object paramObject, String paramString, Object... paramVarArgs)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
    Object[] arrayOfObject = paramVarArgs;
    if (paramVarArgs == null) {
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    int j = arrayOfObject.length;
    paramVarArgs = new Class[j];
    int i = 0;
    while (i < j)
    {
      paramVarArgs[i] = arrayOfObject[i].getClass();
      i += 1;
    }
    return invokeMethod(paramObject, paramString, arrayOfObject, paramVarArgs);
  }
  
  public static Object invokeMethod(Object paramObject, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
    Object localObject = paramArrayOfClass;
    if (paramArrayOfClass == null) {
      localObject = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    paramArrayOfClass = paramArrayOfObject;
    if (paramArrayOfObject == null) {
      paramArrayOfClass = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    paramArrayOfObject = getMatchingAccessibleMethod(paramObject.getClass(), paramString, (Class[])localObject);
    if (paramArrayOfObject == null) {
      throw new NoSuchMethodException("No such accessible method: " + paramString + "() on object: " + paramObject.getClass().getName());
    }
    return paramArrayOfObject.invoke(paramObject, paramArrayOfClass);
  }
  
  public static Object invokeStaticMethod(Class<?> paramClass, String paramString, Object... paramVarArgs)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
    Object[] arrayOfObject = paramVarArgs;
    if (paramVarArgs == null) {
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    int j = arrayOfObject.length;
    paramVarArgs = new Class[j];
    int i = 0;
    while (i < j)
    {
      paramVarArgs[i] = arrayOfObject[i].getClass();
      i += 1;
    }
    return invokeStaticMethod(paramClass, paramString, arrayOfObject, paramVarArgs);
  }
  
  public static Object invokeStaticMethod(Class<?> paramClass, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
    Object localObject = paramArrayOfClass;
    if (paramArrayOfClass == null) {
      localObject = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    paramArrayOfClass = paramArrayOfObject;
    if (paramArrayOfObject == null) {
      paramArrayOfClass = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    paramArrayOfObject = getMatchingAccessibleMethod(paramClass, paramString, (Class[])localObject);
    if (paramArrayOfObject == null) {
      throw new NoSuchMethodException("No such accessible method: " + paramString + "() on class: " + paramClass.getName());
    }
    return paramArrayOfObject.invoke(null, paramArrayOfClass);
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\reflect\MethodUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */