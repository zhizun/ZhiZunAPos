package org.apache.commons.lang3.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;

public class ConstructorUtils
{
  public static <T> Constructor<T> getAccessibleConstructor(Class<T> paramClass, Class<?>... paramVarArgs)
  {
    try
    {
      paramClass = getAccessibleConstructor(paramClass.getConstructor(paramVarArgs));
      return paramClass;
    }
    catch (NoSuchMethodException paramClass) {}
    return null;
  }
  
  public static <T> Constructor<T> getAccessibleConstructor(Constructor<T> paramConstructor)
  {
    if ((MemberUtils.isAccessible(paramConstructor)) && (Modifier.isPublic(paramConstructor.getDeclaringClass().getModifiers()))) {
      return paramConstructor;
    }
    return null;
  }
  
  public static <T> Constructor<T> getMatchingAccessibleConstructor(Class<T> paramClass, Class<?>... paramVarArgs)
  {
    try
    {
      Constructor localConstructor1 = paramClass.getConstructor(paramVarArgs);
      MemberUtils.setAccessibleWorkaround(localConstructor1);
      return localConstructor1;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Object localObject = null;
      Constructor[] arrayOfConstructor = paramClass.getConstructors();
      int j = arrayOfConstructor.length;
      int i = 0;
      for (paramClass = (Class<T>)localObject; i < j; paramClass = (Class<T>)localObject)
      {
        Constructor localConstructor2 = arrayOfConstructor[i];
        localObject = paramClass;
        if (ClassUtils.isAssignable(paramVarArgs, localConstructor2.getParameterTypes(), true))
        {
          localConstructor2 = getAccessibleConstructor(localConstructor2);
          localObject = paramClass;
          if (localConstructor2 != null)
          {
            MemberUtils.setAccessibleWorkaround(localConstructor2);
            if (paramClass != null)
            {
              localObject = paramClass;
              if (MemberUtils.compareParameterTypes(localConstructor2.getParameterTypes(), paramClass.getParameterTypes(), paramVarArgs) >= 0) {}
            }
            else
            {
              localObject = localConstructor2;
            }
          }
        }
        i += 1;
      }
    }
    return paramClass;
  }
  
  public static <T> T invokeConstructor(Class<T> paramClass, Object... paramVarArgs)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
  {
    Object[] arrayOfObject = paramVarArgs;
    if (paramVarArgs == null) {
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    paramVarArgs = new Class[arrayOfObject.length];
    int i = 0;
    while (i < arrayOfObject.length)
    {
      paramVarArgs[i] = arrayOfObject[i].getClass();
      i += 1;
    }
    return (T)invokeConstructor(paramClass, arrayOfObject, paramVarArgs);
  }
  
  public static <T> T invokeConstructor(Class<T> paramClass, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
  {
    Object localObject = paramArrayOfClass;
    if (paramArrayOfClass == null) {
      localObject = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    paramArrayOfClass = paramArrayOfObject;
    if (paramArrayOfObject == null) {
      paramArrayOfClass = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    paramArrayOfObject = getMatchingAccessibleConstructor(paramClass, (Class[])localObject);
    if (paramArrayOfObject == null) {
      throw new NoSuchMethodException("No such accessible constructor on object: " + paramClass.getName());
    }
    return (T)paramArrayOfObject.newInstance(paramArrayOfClass);
  }
  
  public static <T> T invokeExactConstructor(Class<T> paramClass, Object... paramVarArgs)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
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
    return (T)invokeExactConstructor(paramClass, arrayOfObject, paramVarArgs);
  }
  
  public static <T> T invokeExactConstructor(Class<T> paramClass, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass)
    throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
  {
    Object[] arrayOfObject = paramArrayOfObject;
    if (paramArrayOfObject == null) {
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY;
    }
    paramArrayOfObject = paramArrayOfClass;
    if (paramArrayOfClass == null) {
      paramArrayOfObject = ArrayUtils.EMPTY_CLASS_ARRAY;
    }
    paramArrayOfObject = getAccessibleConstructor(paramClass, paramArrayOfObject);
    if (paramArrayOfObject == null) {
      throw new NoSuchMethodException("No such accessible constructor on object: " + paramClass.getName());
    }
    return (T)paramArrayOfObject.newInstance(arrayOfObject);
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\reflect\ConstructorUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */