package org.apache.commons.lang3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LocaleUtils
{
  private static final ConcurrentMap<String, List<Locale>> cCountriesByLanguage = new ConcurrentHashMap();
  private static final ConcurrentMap<String, List<Locale>> cLanguagesByCountry = new ConcurrentHashMap();
  
  public static List<Locale> availableLocaleList()
  {
    return SyncAvoid.AVAILABLE_LOCALE_LIST;
  }
  
  public static Set<Locale> availableLocaleSet()
  {
    return SyncAvoid.AVAILABLE_LOCALE_SET;
  }
  
  public static List<Locale> countriesByLanguage(String paramString)
  {
    if (paramString == null) {
      localObject = Collections.emptyList();
    }
    do
    {
      return (List<Locale>)localObject;
      localList = (List)cCountriesByLanguage.get(paramString);
      localObject = localList;
    } while (localList != null);
    Object localObject = new ArrayList();
    List localList = availableLocaleList();
    int i = 0;
    while (i < localList.size())
    {
      Locale localLocale = (Locale)localList.get(i);
      if ((paramString.equals(localLocale.getLanguage())) && (localLocale.getCountry().length() != 0) && (localLocale.getVariant().length() == 0)) {
        ((List)localObject).add(localLocale);
      }
      i += 1;
    }
    localObject = Collections.unmodifiableList((List)localObject);
    cCountriesByLanguage.putIfAbsent(paramString, localObject);
    return (List)cCountriesByLanguage.get(paramString);
  }
  
  public static boolean isAvailableLocale(Locale paramLocale)
  {
    return availableLocaleList().contains(paramLocale);
  }
  
  public static List<Locale> languagesByCountry(String paramString)
  {
    if (paramString == null) {
      localObject = Collections.emptyList();
    }
    do
    {
      return (List<Locale>)localObject;
      localList = (List)cLanguagesByCountry.get(paramString);
      localObject = localList;
    } while (localList != null);
    Object localObject = new ArrayList();
    List localList = availableLocaleList();
    int i = 0;
    while (i < localList.size())
    {
      Locale localLocale = (Locale)localList.get(i);
      if ((paramString.equals(localLocale.getCountry())) && (localLocale.getVariant().length() == 0)) {
        ((List)localObject).add(localLocale);
      }
      i += 1;
    }
    localObject = Collections.unmodifiableList((List)localObject);
    cLanguagesByCountry.putIfAbsent(paramString, localObject);
    return (List)cLanguagesByCountry.get(paramString);
  }
  
  public static List<Locale> localeLookupList(Locale paramLocale)
  {
    return localeLookupList(paramLocale, paramLocale);
  }
  
  public static List<Locale> localeLookupList(Locale paramLocale1, Locale paramLocale2)
  {
    ArrayList localArrayList = new ArrayList(4);
    if (paramLocale1 != null)
    {
      localArrayList.add(paramLocale1);
      if (paramLocale1.getVariant().length() > 0) {
        localArrayList.add(new Locale(paramLocale1.getLanguage(), paramLocale1.getCountry()));
      }
      if (paramLocale1.getCountry().length() > 0) {
        localArrayList.add(new Locale(paramLocale1.getLanguage(), ""));
      }
      if (!localArrayList.contains(paramLocale2)) {
        localArrayList.add(paramLocale2);
      }
    }
    return Collections.unmodifiableList(localArrayList);
  }
  
  public static Locale toLocale(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    int i = paramString.length();
    if ((i != 2) && (i != 5) && (i < 7)) {
      throw new IllegalArgumentException("Invalid locale format: " + paramString);
    }
    int j = paramString.charAt(0);
    int k = paramString.charAt(1);
    if ((j < 97) || (j > 122) || (k < 97) || (k > 122)) {
      throw new IllegalArgumentException("Invalid locale format: " + paramString);
    }
    if (i == 2) {
      return new Locale(paramString, "");
    }
    if (paramString.charAt(2) != '_') {
      throw new IllegalArgumentException("Invalid locale format: " + paramString);
    }
    j = paramString.charAt(3);
    if (j == 95) {
      return new Locale(paramString.substring(0, 2), "", paramString.substring(4));
    }
    k = paramString.charAt(4);
    if ((j < 65) || (j > 90) || (k < 65) || (k > 90)) {
      throw new IllegalArgumentException("Invalid locale format: " + paramString);
    }
    if (i == 5) {
      return new Locale(paramString.substring(0, 2), paramString.substring(3, 5));
    }
    if (paramString.charAt(5) != '_') {
      throw new IllegalArgumentException("Invalid locale format: " + paramString);
    }
    return new Locale(paramString.substring(0, 2), paramString.substring(3, 5), paramString.substring(6));
  }
  
  static class SyncAvoid
  {
    private static List<Locale> AVAILABLE_LOCALE_LIST = Collections.unmodifiableList(new ArrayList(Arrays.asList(Locale.getAvailableLocales())));
    private static Set<Locale> AVAILABLE_LOCALE_SET = Collections.unmodifiableSet(new HashSet(LocaleUtils.availableLocaleList()));
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\LocaleUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */