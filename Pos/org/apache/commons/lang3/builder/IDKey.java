package org.apache.commons.lang3.builder;

final class IDKey
{
  private final int id;
  private final Object value;
  
  public IDKey(Object paramObject)
  {
    this.id = System.identityHashCode(paramObject);
    this.value = paramObject;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof IDKey)) {}
    do
    {
      return false;
      paramObject = (IDKey)paramObject;
    } while ((this.id != ((IDKey)paramObject).id) || (this.value != ((IDKey)paramObject).value));
    return true;
  }
  
  public int hashCode()
  {
    return this.id;
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\builder\IDKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */