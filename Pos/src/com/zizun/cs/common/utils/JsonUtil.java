package com.zizun.cs.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.reflect.TypeToken;
import java.util.List;

public class JsonUtil
{
  private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS").setLongSerializationPolicy(LongSerializationPolicy.STRING).create();
  
  public static <T> T fromJson(String paramString, Class<T> paramClass)
  {
    return (T)gson.fromJson(paramString, paramClass);
  }
  
  public static <T> List<T> fromJsonList(String paramString, Class<T> paramClass)
  {
    paramClass = new TypeToken() {}.getType();
    return (List)gson.fromJson(paramString, paramClass);
  }
  
  public static String toJson(Object paramObject)
  {
    return gson.toJson(paramObject);
  }
}