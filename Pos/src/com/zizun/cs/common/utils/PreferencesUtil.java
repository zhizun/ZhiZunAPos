package com.zizun.cs.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import java.util.List;

public class PreferencesUtil
{
  public static final String LOGIN_MAP = "NAME_LOGIN";
  private Context context;
  private String name;
  private SharedPreferences preferences = null;
  
  public PreferencesUtil(Context paramContext)
  {
    this.context = paramContext;
    this.preferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
  }
  
  public PreferencesUtil(Context paramContext, String paramString)
  {
    this.context = paramContext;
    this.name = paramString;
    this.preferences = this.context.getSharedPreferences(this.name, 0);
  }
  
  public static SharedPreferences getPreference(Context paramContext)
  {
    return PreferenceManager.getDefaultSharedPreferences(paramContext);
  }
  
  public SharedPreferences.Editor getEditor()
  {
    return this.preferences.edit();
  }
  
  public <T> T getObject(String paramString, Class<T> paramClass)
  {
    SharedPreferences localSharedPreferences = this.preferences;
    Object localObject = null;
    if (localSharedPreferences.contains(paramString)) {
      localObject = JsonUtil.fromJson(localSharedPreferences.getString(paramString, null), paramClass);
    }
    return (T)localObject;
  }
  
  public <T> List<T> getObjectList(String paramString, Class<T> paramClass)
  {
    SharedPreferences localSharedPreferences = this.preferences;
    List localList = null;
    if (localSharedPreferences.contains(paramString)) {
      localList = JsonUtil.fromJsonList(localSharedPreferences.getString(paramString, null), paramClass);
    }
    return localList;
  }
  
  public SharedPreferences getPreference()
  {
    if (this.preferences == null) {
      this.preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
    }
    return this.preferences;
  }
  
  public void saveObject(String paramString, Object paramObject)
  {
    Object localObject = this.preferences;
    paramObject = JsonUtil.toJson(paramObject);
    localObject = ((SharedPreferences)localObject).edit();
    ((SharedPreferences.Editor)localObject).putString(paramString, (String)paramObject);
    ((SharedPreferences.Editor)localObject).commit();
  }
}