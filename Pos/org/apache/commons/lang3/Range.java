package org.apache.commons.lang3;

import java.io.Serializable;
import java.util.Comparator;

public final class Range<T>
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private final Comparator<T> comparator;
  private transient int hashCode;
  private final T maximum;
  private final T minimum;
  private transient String toString;
  
  private Range(T paramT1, T paramT2, Comparator<T> paramComparator)
  {
    if ((paramT1 == null) || (paramT2 == null)) {
      throw new IllegalArgumentException("Elements in a range must not be null: element1=" + paramT1 + ", element2=" + paramT2);
    }
    Object localObject = paramComparator;
    if (paramComparator == null) {
      localObject = ComparableComparator.INSTANCE;
    }
    if (((Comparator)localObject).compare(paramT1, paramT2) < 1) {
      this.minimum = paramT1;
    }
    for (this.maximum = paramT2;; this.maximum = paramT1)
    {
      this.comparator = ((Comparator)localObject);
      return;
      this.minimum = paramT2;
    }
  }
  
  public static <T extends Comparable<T>> Range<T> between(T paramT1, T paramT2)
  {
    return between(paramT1, paramT2, null);
  }
  
  public static <T> Range<T> between(T paramT1, T paramT2, Comparator<T> paramComparator)
  {
    return new Range(paramT1, paramT2, paramComparator);
  }
  
  public static <T extends Comparable<T>> Range<T> is(T paramT)
  {
    return between(paramT, paramT, null);
  }
  
  public static <T> Range<T> is(T paramT, Comparator<T> paramComparator)
  {
    return between(paramT, paramT, paramComparator);
  }
  
  public boolean contains(T paramT)
  {
    boolean bool = true;
    if (paramT == null) {
      return false;
    }
    if ((this.comparator.compare(paramT, this.minimum) > -1) && (this.comparator.compare(paramT, this.maximum) < 1)) {}
    for (;;)
    {
      return bool;
      bool = false;
    }
  }
  
  public boolean containsRange(Range<T> paramRange)
  {
    if (paramRange == null) {}
    while ((!contains(paramRange.minimum)) || (!contains(paramRange.maximum))) {
      return false;
    }
    return true;
  }
  
  public int elementCompareTo(T paramT)
  {
    if (paramT == null) {
      throw new NullPointerException("Element is null");
    }
    if (isAfter(paramT)) {
      return -1;
    }
    if (isBefore(paramT)) {
      return 1;
    }
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    do
    {
      return true;
      if ((paramObject == null) || (paramObject.getClass() != getClass())) {
        return false;
      }
      paramObject = (Range)paramObject;
    } while ((this.minimum.equals(((Range)paramObject).minimum)) && (this.maximum.equals(((Range)paramObject).maximum)));
    return false;
  }
  
  public Comparator<T> getComparator()
  {
    return this.comparator;
  }
  
  public T getMaximum()
  {
    return (T)this.maximum;
  }
  
  public T getMinimum()
  {
    return (T)this.minimum;
  }
  
  public int hashCode()
  {
    int i = this.hashCode;
    if (this.hashCode == 0)
    {
      i = ((getClass().hashCode() + 629) * 37 + this.minimum.hashCode()) * 37 + this.maximum.hashCode();
      this.hashCode = i;
    }
    return i;
  }
  
  public Range<T> intersectionWith(Range<T> paramRange)
  {
    if (!isOverlappedBy(paramRange)) {
      throw new IllegalArgumentException(String.format("Cannot calculate intersection with non-overlapping range %s", new Object[] { paramRange }));
    }
    if (equals(paramRange)) {
      return this;
    }
    Object localObject;
    if (getComparator().compare(this.minimum, paramRange.minimum) < 0)
    {
      localObject = paramRange.minimum;
      if (getComparator().compare(this.maximum, paramRange.maximum) >= 0) {
        break label107;
      }
    }
    label107:
    for (paramRange = this.maximum;; paramRange = paramRange.maximum)
    {
      return between(localObject, paramRange, getComparator());
      localObject = this.minimum;
      break;
    }
  }
  
  public boolean isAfter(T paramT)
  {
    if (paramT == null) {}
    while (this.comparator.compare(paramT, this.minimum) >= 0) {
      return false;
    }
    return true;
  }
  
  public boolean isAfterRange(Range<T> paramRange)
  {
    if (paramRange == null) {
      return false;
    }
    return isAfter(paramRange.maximum);
  }
  
  public boolean isBefore(T paramT)
  {
    if (paramT == null) {}
    while (this.comparator.compare(paramT, this.maximum) <= 0) {
      return false;
    }
    return true;
  }
  
  public boolean isBeforeRange(Range<T> paramRange)
  {
    if (paramRange == null) {
      return false;
    }
    return isBefore(paramRange.minimum);
  }
  
  public boolean isEndedBy(T paramT)
  {
    if (paramT == null) {}
    while (this.comparator.compare(paramT, this.maximum) != 0) {
      return false;
    }
    return true;
  }
  
  public boolean isNaturalOrdering()
  {
    return this.comparator == ComparableComparator.INSTANCE;
  }
  
  public boolean isOverlappedBy(Range<T> paramRange)
  {
    if (paramRange == null) {}
    while ((!paramRange.contains(this.minimum)) && (!paramRange.contains(this.maximum)) && (!contains(paramRange.minimum))) {
      return false;
    }
    return true;
  }
  
  public boolean isStartedBy(T paramT)
  {
    if (paramT == null) {}
    while (this.comparator.compare(paramT, this.minimum) != 0) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    String str = this.toString;
    Object localObject = str;
    if (str == null)
    {
      localObject = new StringBuilder(32);
      ((StringBuilder)localObject).append('[');
      ((StringBuilder)localObject).append(this.minimum);
      ((StringBuilder)localObject).append("..");
      ((StringBuilder)localObject).append(this.maximum);
      ((StringBuilder)localObject).append(']');
      localObject = ((StringBuilder)localObject).toString();
      this.toString = ((String)localObject);
    }
    return (String)localObject;
  }
  
  public String toString(String paramString)
  {
    return String.format(paramString, new Object[] { this.minimum, this.maximum, this.comparator });
  }
  
  private static enum ComparableComparator
    implements Comparator
  {
    INSTANCE;
    
    private ComparableComparator() {}
    
    public int compare(Object paramObject1, Object paramObject2)
    {
      return ((Comparable)paramObject1).compareTo(paramObject2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\Range.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */