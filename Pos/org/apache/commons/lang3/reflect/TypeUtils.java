package org.apache.commons.lang3.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang3.ClassUtils;

public class TypeUtils
{
  public static Map<TypeVariable<?>, Type> determineTypeArguments(Class<?> paramClass, ParameterizedType paramParameterizedType)
  {
    Object localObject = getRawType(paramParameterizedType);
    if (!isAssignable(paramClass, (Class)localObject)) {
      return null;
    }
    if (paramClass.equals(localObject)) {
      return getTypeArguments(paramParameterizedType, (Class)localObject, null);
    }
    localObject = getClosestParentType(paramClass, (Class)localObject);
    if ((localObject instanceof Class)) {
      return determineTypeArguments((Class)localObject, paramParameterizedType);
    }
    localObject = (ParameterizedType)localObject;
    paramParameterizedType = determineTypeArguments(getRawType((ParameterizedType)localObject), paramParameterizedType);
    mapTypeVariablesToArguments(paramClass, (ParameterizedType)localObject, paramParameterizedType);
    return paramParameterizedType;
  }
  
  public static Type getArrayComponentType(Type paramType)
  {
    Class localClass = null;
    if ((paramType instanceof Class))
    {
      paramType = (Class)paramType;
      if (paramType.isArray()) {
        localClass = paramType.getComponentType();
      }
    }
    while (!(paramType instanceof GenericArrayType)) {
      return localClass;
    }
    return ((GenericArrayType)paramType).getGenericComponentType();
  }
  
  private static Type getClosestParentType(Class<?> paramClass1, Class<?> paramClass2)
  {
    if (paramClass2.isInterface())
    {
      Type[] arrayOfType = paramClass1.getGenericInterfaces();
      Object localObject1 = null;
      int j = arrayOfType.length;
      int i = 0;
      if (i < j)
      {
        Type localType = arrayOfType[i];
        if ((localType instanceof ParameterizedType)) {}
        for (Class localClass = getRawType((ParameterizedType)localType);; localClass = (Class)localType)
        {
          Object localObject2 = localObject1;
          if (isAssignable(localClass, paramClass2))
          {
            localObject2 = localObject1;
            if (isAssignable((Type)localObject1, localClass)) {
              localObject2 = localType;
            }
          }
          i += 1;
          localObject1 = localObject2;
          break;
          if (!(localType instanceof Class)) {
            break label111;
          }
        }
        label111:
        throw new IllegalStateException("Unexpected generic interface type found: " + localType);
      }
      if (localObject1 != null) {
        return (Type)localObject1;
      }
    }
    return paramClass1.getGenericSuperclass();
  }
  
  public static Type[] getImplicitBounds(TypeVariable<?> paramTypeVariable)
  {
    paramTypeVariable = paramTypeVariable.getBounds();
    if (paramTypeVariable.length == 0) {
      return new Type[] { Object.class };
    }
    return normalizeUpperBounds(paramTypeVariable);
  }
  
  public static Type[] getImplicitLowerBounds(WildcardType paramWildcardType)
  {
    Type[] arrayOfType = paramWildcardType.getLowerBounds();
    paramWildcardType = arrayOfType;
    if (arrayOfType.length == 0)
    {
      paramWildcardType = new Type[1];
      paramWildcardType[0] = null;
    }
    return paramWildcardType;
  }
  
  public static Type[] getImplicitUpperBounds(WildcardType paramWildcardType)
  {
    paramWildcardType = paramWildcardType.getUpperBounds();
    if (paramWildcardType.length == 0) {
      return new Type[] { Object.class };
    }
    return normalizeUpperBounds(paramWildcardType);
  }
  
  private static Class<?> getRawType(ParameterizedType paramParameterizedType)
  {
    paramParameterizedType = paramParameterizedType.getRawType();
    if (!(paramParameterizedType instanceof Class)) {
      throw new IllegalStateException("Wait... What!? Type of rawType: " + paramParameterizedType);
    }
    return (Class)paramParameterizedType;
  }
  
  public static Class<?> getRawType(Type paramType1, Type paramType2)
  {
    if ((paramType1 instanceof Class)) {
      return (Class)paramType1;
    }
    if ((paramType1 instanceof ParameterizedType)) {
      return getRawType((ParameterizedType)paramType1);
    }
    if ((paramType1 instanceof TypeVariable))
    {
      if (paramType2 == null) {
        return null;
      }
      Object localObject = ((TypeVariable)paramType1).getGenericDeclaration();
      if (!(localObject instanceof Class)) {
        return null;
      }
      localObject = getTypeArguments(paramType2, (Class)localObject);
      if (localObject == null) {
        return null;
      }
      paramType1 = (Type)((Map)localObject).get(paramType1);
      if (paramType1 == null) {
        return null;
      }
      return getRawType(paramType1, paramType2);
    }
    if ((paramType1 instanceof GenericArrayType)) {
      return Array.newInstance(getRawType(((GenericArrayType)paramType1).getGenericComponentType(), paramType2), 0).getClass();
    }
    if ((paramType1 instanceof WildcardType)) {
      return null;
    }
    throw new IllegalArgumentException("unknown type: " + paramType1);
  }
  
  private static Map<TypeVariable<?>, Type> getTypeArguments(Class<?> paramClass1, Class<?> paramClass2, Map<TypeVariable<?>, Type> paramMap)
  {
    if (!isAssignable(paramClass1, paramClass2))
    {
      paramMap = null;
      return paramMap;
    }
    Object localObject = paramClass1;
    if (paramClass1.isPrimitive())
    {
      if (paramClass2.isPrimitive()) {
        return new HashMap();
      }
      localObject = ClassUtils.primitiveToWrapper(paramClass1);
    }
    if (paramMap == null) {}
    for (paramClass1 = new HashMap();; paramClass1 = new HashMap(paramMap))
    {
      paramMap = paramClass1;
      if (((Class)localObject).getTypeParameters().length > 0) {
        break;
      }
      paramMap = paramClass1;
      if (paramClass2.equals(localObject)) {
        break;
      }
      return getTypeArguments(getClosestParentType((Class)localObject, paramClass2), paramClass2, paramClass1);
    }
  }
  
  public static Map<TypeVariable<?>, Type> getTypeArguments(ParameterizedType paramParameterizedType)
  {
    return getTypeArguments(paramParameterizedType, getRawType(paramParameterizedType), null);
  }
  
  private static Map<TypeVariable<?>, Type> getTypeArguments(ParameterizedType paramParameterizedType, Class<?> paramClass, Map<TypeVariable<?>, Type> paramMap)
  {
    Class localClass = getRawType(paramParameterizedType);
    if (!isAssignable(localClass, paramClass)) {
      paramParameterizedType = null;
    }
    label71:
    label154:
    label157:
    do
    {
      return paramParameterizedType;
      Object localObject = paramParameterizedType.getOwnerType();
      int i;
      TypeVariable localTypeVariable;
      if ((localObject instanceof ParameterizedType))
      {
        localObject = (ParameterizedType)localObject;
        paramMap = getTypeArguments((ParameterizedType)localObject, getRawType((ParameterizedType)localObject), paramMap);
        localObject = paramParameterizedType.getActualTypeArguments();
        TypeVariable[] arrayOfTypeVariable = localClass.getTypeParameters();
        i = 0;
        if (i >= arrayOfTypeVariable.length) {
          break label157;
        }
        paramParameterizedType = localObject[i];
        localTypeVariable = arrayOfTypeVariable[i];
        if (!paramMap.containsKey(paramParameterizedType)) {
          break label154;
        }
        paramParameterizedType = (Type)paramMap.get(paramParameterizedType);
      }
      for (;;)
      {
        paramMap.put(localTypeVariable, paramParameterizedType);
        i += 1;
        break label71;
        if (paramMap == null) {}
        for (paramMap = new HashMap();; paramMap = new HashMap(paramMap)) {
          break;
        }
      }
      paramParameterizedType = paramMap;
    } while (paramClass.equals(localClass));
    return getTypeArguments(getClosestParentType(localClass, paramClass), paramClass, paramMap);
  }
  
  public static Map<TypeVariable<?>, Type> getTypeArguments(Type paramType, Class<?> paramClass)
  {
    return getTypeArguments(paramType, paramClass, null);
  }
  
  private static Map<TypeVariable<?>, Type> getTypeArguments(Type paramType, Class<?> paramClass, Map<TypeVariable<?>, Type> paramMap)
  {
    Type localType = null;
    if ((paramType instanceof Class))
    {
      paramType = getTypeArguments((Class)paramType, paramClass, paramMap);
      return paramType;
    }
    if ((paramType instanceof ParameterizedType)) {
      return getTypeArguments((ParameterizedType)paramType, paramClass, paramMap);
    }
    if ((paramType instanceof GenericArrayType))
    {
      localType = ((GenericArrayType)paramType).getGenericComponentType();
      paramType = paramClass;
      if (paramClass.isArray()) {
        paramType = paramClass.getComponentType();
      }
      return getTypeArguments(localType, paramType, paramMap);
    }
    Type[] arrayOfType;
    int j;
    int i;
    if ((paramType instanceof WildcardType))
    {
      arrayOfType = getImplicitUpperBounds((WildcardType)paramType);
      j = arrayOfType.length;
      i = 0;
      for (;;)
      {
        paramType = localType;
        if (i >= j) {
          break;
        }
        paramType = arrayOfType[i];
        if (isAssignable(paramType, paramClass)) {
          return getTypeArguments(paramType, paramClass, paramMap);
        }
        i += 1;
      }
    }
    if ((paramType instanceof TypeVariable))
    {
      arrayOfType = getImplicitBounds((TypeVariable)paramType);
      j = arrayOfType.length;
      i = 0;
      for (;;)
      {
        paramType = localType;
        if (i >= j) {
          break;
        }
        paramType = arrayOfType[i];
        if (isAssignable(paramType, paramClass)) {
          return getTypeArguments(paramType, paramClass, paramMap);
        }
        i += 1;
      }
    }
    throw new IllegalStateException("found an unhandled type: " + paramType);
  }
  
  public static boolean isArrayType(Type paramType)
  {
    return ((paramType instanceof GenericArrayType)) || (((paramType instanceof Class)) && (((Class)paramType).isArray()));
  }
  
  private static boolean isAssignable(Type paramType, Class<?> paramClass)
  {
    boolean bool2 = false;
    boolean bool1;
    if (paramType == null) {
      if (paramClass != null)
      {
        bool1 = bool2;
        if (paramClass.isPrimitive()) {}
      }
      else
      {
        bool1 = true;
      }
    }
    label183:
    do
    {
      do
      {
        do
        {
          do
          {
            return bool1;
            bool1 = bool2;
          } while (paramClass == null);
          if (paramClass.equals(paramType)) {
            return true;
          }
          if ((paramType instanceof Class)) {
            return ClassUtils.isAssignable((Class)paramType, paramClass);
          }
          if ((paramType instanceof ParameterizedType)) {
            return isAssignable(getRawType((ParameterizedType)paramType), paramClass);
          }
          if ((paramType instanceof TypeVariable))
          {
            paramType = ((TypeVariable)paramType).getBounds();
            int j = paramType.length;
            int i = 0;
            for (;;)
            {
              bool1 = bool2;
              if (i >= j) {
                break;
              }
              if (isAssignable(paramType[i], paramClass)) {
                return true;
              }
              i += 1;
            }
          }
          if (!(paramType instanceof GenericArrayType)) {
            break label183;
          }
          if (paramClass.equals(Object.class)) {
            break;
          }
          bool1 = bool2;
        } while (!paramClass.isArray());
        bool1 = bool2;
      } while (!isAssignable(((GenericArrayType)paramType).getGenericComponentType(), paramClass.getComponentType()));
      return true;
      bool1 = bool2;
    } while ((paramType instanceof WildcardType));
    throw new IllegalStateException("found an unhandled type: " + paramType);
  }
  
  private static boolean isAssignable(Type paramType, GenericArrayType paramGenericArrayType, Map<TypeVariable<?>, Type> paramMap)
  {
    if (paramType == null) {}
    Type localType;
    do
    {
      do
      {
        return true;
        if (paramGenericArrayType == null) {
          return false;
        }
      } while (paramGenericArrayType.equals(paramType));
      localType = paramGenericArrayType.getGenericComponentType();
      if (!(paramType instanceof Class)) {
        break;
      }
      paramType = (Class)paramType;
    } while ((paramType.isArray()) && (isAssignable(paramType.getComponentType(), localType, paramMap)));
    return false;
    if ((paramType instanceof GenericArrayType)) {
      return isAssignable(((GenericArrayType)paramType).getGenericComponentType(), localType, paramMap);
    }
    int j;
    int i;
    if ((paramType instanceof WildcardType))
    {
      paramType = getImplicitUpperBounds((WildcardType)paramType);
      j = paramType.length;
      i = 0;
      for (;;)
      {
        if (i >= j) {
          break label129;
        }
        if (isAssignable(paramType[i], paramGenericArrayType)) {
          break;
        }
        i += 1;
      }
      label129:
      return false;
    }
    if ((paramType instanceof TypeVariable))
    {
      paramType = getImplicitBounds((TypeVariable)paramType);
      j = paramType.length;
      i = 0;
      for (;;)
      {
        if (i >= j) {
          break label175;
        }
        if (isAssignable(paramType[i], paramGenericArrayType)) {
          break;
        }
        i += 1;
      }
      label175:
      return false;
    }
    if ((paramType instanceof ParameterizedType)) {
      return false;
    }
    throw new IllegalStateException("found an unhandled type: " + paramType);
  }
  
  private static boolean isAssignable(Type paramType, ParameterizedType paramParameterizedType, Map<TypeVariable<?>, Type> paramMap)
  {
    if (paramType == null) {}
    Object localObject1;
    Object localObject2;
    do
    {
      while (!paramParameterizedType.hasNext())
      {
        do
        {
          do
          {
            return true;
            if (paramParameterizedType == null) {
              return false;
            }
          } while (paramParameterizedType.equals(paramType));
          localObject1 = getRawType(paramParameterizedType);
          paramType = getTypeArguments(paramType, (Class)localObject1, null);
          if (paramType == null) {
            return false;
          }
        } while (paramType.isEmpty());
        paramParameterizedType = getTypeArguments(paramParameterizedType, (Class)localObject1, paramMap).entrySet().iterator();
      }
      localObject2 = (Map.Entry)paramParameterizedType.next();
      localObject1 = (Type)((Map.Entry)localObject2).getValue();
      localObject2 = (Type)paramType.get(((Map.Entry)localObject2).getKey());
    } while ((localObject2 == null) || (localObject1.equals(localObject2)) || (((localObject1 instanceof WildcardType)) && (isAssignable((Type)localObject2, (Type)localObject1, paramMap))));
    return false;
  }
  
  public static boolean isAssignable(Type paramType1, Type paramType2)
  {
    return isAssignable(paramType1, paramType2, null);
  }
  
  private static boolean isAssignable(Type paramType1, Type paramType2, Map<TypeVariable<?>, Type> paramMap)
  {
    if ((paramType2 == null) || ((paramType2 instanceof Class))) {
      return isAssignable(paramType1, (Class)paramType2);
    }
    if ((paramType2 instanceof ParameterizedType)) {
      return isAssignable(paramType1, (ParameterizedType)paramType2, paramMap);
    }
    if ((paramType2 instanceof GenericArrayType)) {
      return isAssignable(paramType1, (GenericArrayType)paramType2, paramMap);
    }
    if ((paramType2 instanceof WildcardType)) {
      return isAssignable(paramType1, (WildcardType)paramType2, paramMap);
    }
    if ((paramType2 instanceof TypeVariable)) {
      return isAssignable(paramType1, (TypeVariable)paramType2, paramMap);
    }
    throw new IllegalStateException("found an unhandled type: " + paramType2);
  }
  
  private static boolean isAssignable(Type paramType, TypeVariable<?> paramTypeVariable, Map<TypeVariable<?>, Type> paramMap)
  {
    if (paramType == null) {
      return true;
    }
    if (paramTypeVariable == null) {
      return false;
    }
    if (paramTypeVariable.equals(paramType)) {
      return true;
    }
    if ((paramType instanceof TypeVariable))
    {
      Type[] arrayOfType = getImplicitBounds((TypeVariable)paramType);
      int j = arrayOfType.length;
      int i = 0;
      while (i < j)
      {
        if (isAssignable(arrayOfType[i], paramTypeVariable, paramMap)) {
          return true;
        }
        i += 1;
      }
    }
    if (((paramType instanceof Class)) || ((paramType instanceof ParameterizedType)) || ((paramType instanceof GenericArrayType)) || ((paramType instanceof WildcardType))) {
      return false;
    }
    throw new IllegalStateException("found an unhandled type: " + paramType);
  }
  
  private static boolean isAssignable(Type paramType, WildcardType paramWildcardType, Map<TypeVariable<?>, Type> paramMap)
  {
    if (paramType == null) {
      return true;
    }
    if (paramWildcardType == null) {
      return false;
    }
    if (paramWildcardType.equals(paramType)) {
      return true;
    }
    Type[] arrayOfType = getImplicitUpperBounds(paramWildcardType);
    paramWildcardType = getImplicitLowerBounds(paramWildcardType);
    if ((paramType instanceof WildcardType))
    {
      Object localObject = (WildcardType)paramType;
      paramType = getImplicitUpperBounds((WildcardType)localObject);
      localObject = getImplicitLowerBounds((WildcardType)localObject);
      int k = arrayOfType.length;
      i = 0;
      int m;
      while (i < k)
      {
        Type localType = substituteTypeVariables(arrayOfType[i], paramMap);
        m = paramType.length;
        j = 0;
        while (j < m)
        {
          if (!isAssignable(paramType[j], localType, paramMap)) {
            return false;
          }
          j += 1;
        }
        i += 1;
      }
      k = paramWildcardType.length;
      i = 0;
      while (i < k)
      {
        paramType = substituteTypeVariables(paramWildcardType[i], paramMap);
        m = localObject.length;
        j = 0;
        while (j < m)
        {
          if (!isAssignable(paramType, localObject[j], paramMap)) {
            return false;
          }
          j += 1;
        }
        i += 1;
      }
      return true;
    }
    int j = arrayOfType.length;
    int i = 0;
    while (i < j)
    {
      if (!isAssignable(paramType, substituteTypeVariables(arrayOfType[i], paramMap), paramMap)) {
        return false;
      }
      i += 1;
    }
    j = paramWildcardType.length;
    i = 0;
    while (i < j)
    {
      if (!isAssignable(substituteTypeVariables(paramWildcardType[i], paramMap), paramType, paramMap)) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  public static boolean isInstance(Object paramObject, Type paramType)
  {
    if (paramType == null) {}
    do
    {
      return false;
      if (paramObject != null) {
        break;
      }
    } while (((paramType instanceof Class)) && (((Class)paramType).isPrimitive()));
    return true;
    return isAssignable(paramObject.getClass(), paramType, null);
  }
  
  private static <T> void mapTypeVariablesToArguments(Class<T> paramClass, ParameterizedType paramParameterizedType, Map<TypeVariable<?>, Type> paramMap)
  {
    Object localObject1 = paramParameterizedType.getOwnerType();
    if ((localObject1 instanceof ParameterizedType)) {
      mapTypeVariablesToArguments(paramClass, (ParameterizedType)localObject1, paramMap);
    }
    localObject1 = paramParameterizedType.getActualTypeArguments();
    paramParameterizedType = getRawType(paramParameterizedType).getTypeParameters();
    paramClass = Arrays.asList(paramClass.getTypeParameters());
    int i = 0;
    while (i < localObject1.length)
    {
      Object localObject2 = paramParameterizedType[i];
      Object localObject3 = localObject1[i];
      if ((paramClass.contains(localObject3)) && (paramMap.containsKey(localObject2))) {
        paramMap.put((TypeVariable)localObject3, paramMap.get(localObject2));
      }
      i += 1;
    }
  }
  
  public static Type[] normalizeUpperBounds(Type[] paramArrayOfType)
  {
    if (paramArrayOfType.length < 2) {
      return paramArrayOfType;
    }
    HashSet localHashSet = new HashSet(paramArrayOfType.length);
    int n = paramArrayOfType.length;
    int i = 0;
    if (i < n)
    {
      Type localType1 = paramArrayOfType[i];
      int m = 0;
      int i1 = paramArrayOfType.length;
      int j = 0;
      for (;;)
      {
        int k = m;
        if (j < i1)
        {
          Type localType2 = paramArrayOfType[j];
          if ((localType1 != localType2) && (isAssignable(localType2, localType1, null))) {
            k = 1;
          }
        }
        else
        {
          if (k == 0) {
            localHashSet.add(localType1);
          }
          i += 1;
          break;
        }
        j += 1;
      }
    }
    return (Type[])localHashSet.toArray(new Type[localHashSet.size()]);
  }
  
  private static Type substituteTypeVariables(Type paramType, Map<TypeVariable<?>, Type> paramMap)
  {
    if (((paramType instanceof TypeVariable)) && (paramMap != null))
    {
      Type localType = (Type)paramMap.get(paramType);
      paramMap = localType;
      if (localType == null) {
        throw new IllegalArgumentException("missing assignment type for type variable " + paramType);
      }
    }
    else
    {
      paramMap = paramType;
    }
    return paramMap;
  }
  
  public static boolean typesSatisfyVariables(Map<TypeVariable<?>, Type> paramMap)
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = (Map.Entry)localIterator.next();
      Object localObject1 = (TypeVariable)((Map.Entry)localObject2).getKey();
      localObject2 = (Type)((Map.Entry)localObject2).getValue();
      localObject1 = getImplicitBounds((TypeVariable)localObject1);
      int j = localObject1.length;
      int i = 0;
      while (i < j)
      {
        if (!isAssignable((Type)localObject2, substituteTypeVariables(localObject1[i], paramMap), paramMap)) {
          return false;
        }
        i += 1;
      }
    }
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\reflect\TypeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */