package com.zizun.cs.biz.enumType;

public enum DiscountType
{
  UNKNOWN((byte)-1),  DISCOUNT_AMOUNT((byte)2),  DISCOUNT_PERCENT((byte)1);
  
  private byte value;
  
  private DiscountType(byte paramByte)
  {
    this.value = paramByte;
  }
  
  public static DiscountType fromValue(int paramInt)
  {
    Object localObject1 = null;
    Object localObject2 = values();
    int j = localObject2.length;
    int i = 0;
    for (;;)
    {
      if ((i >= j) || (localObject1 != null))
      {
        localObject2 = localObject1;
        if (localObject1 == null) {
          localObject2 = UNKNOWN;
        }
        return (DiscountType)localObject2;
      }
      if (localObject2[i].value == paramInt) {
        localObject1 = localObject2[i];
      }
      i += 1;
    }
  }
  
  public byte getValue()
  {
    return this.value;
  }
}