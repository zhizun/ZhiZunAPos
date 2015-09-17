package org.apache.commons.lang3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;

public class SerializationUtils
{
  /* Error */
  public static <T extends Serializable> T clone(T paramT)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +7 -> 8
    //   4: aconst_null
    //   5: astore_1
    //   6: aload_1
    //   7: areturn
    //   8: new 20	java/io/ByteArrayInputStream
    //   11: dup
    //   12: aload_0
    //   13: invokestatic 24	org/apache/commons/lang3/SerializationUtils:serialize	(Ljava/io/Serializable;)[B
    //   16: invokespecial 27	java/io/ByteArrayInputStream:<init>	([B)V
    //   19: astore 4
    //   21: aconst_null
    //   22: astore_1
    //   23: aconst_null
    //   24: astore_3
    //   25: aconst_null
    //   26: astore_2
    //   27: new 6	org/apache/commons/lang3/SerializationUtils$ClassLoaderAwareObjectInputStream
    //   30: dup
    //   31: aload 4
    //   33: aload_0
    //   34: invokevirtual 31	java/lang/Object:getClass	()Ljava/lang/Class;
    //   37: invokevirtual 37	java/lang/Class:getClassLoader	()Ljava/lang/ClassLoader;
    //   40: invokespecial 40	org/apache/commons/lang3/SerializationUtils$ClassLoaderAwareObjectInputStream:<init>	(Ljava/io/InputStream;Ljava/lang/ClassLoader;)V
    //   43: astore_0
    //   44: aload_0
    //   45: invokevirtual 44	org/apache/commons/lang3/SerializationUtils$ClassLoaderAwareObjectInputStream:readObject	()Ljava/lang/Object;
    //   48: checkcast 46	java/io/Serializable
    //   51: astore_2
    //   52: aload_2
    //   53: astore_1
    //   54: aload_0
    //   55: ifnull -49 -> 6
    //   58: aload_0
    //   59: invokevirtual 49	org/apache/commons/lang3/SerializationUtils$ClassLoaderAwareObjectInputStream:close	()V
    //   62: aload_2
    //   63: areturn
    //   64: astore_0
    //   65: new 51	org/apache/commons/lang3/SerializationException
    //   68: dup
    //   69: ldc 53
    //   71: aload_0
    //   72: invokespecial 56	org/apache/commons/lang3/SerializationException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   75: athrow
    //   76: astore_0
    //   77: aload_2
    //   78: astore_1
    //   79: new 51	org/apache/commons/lang3/SerializationException
    //   82: dup
    //   83: ldc 58
    //   85: aload_0
    //   86: invokespecial 56	org/apache/commons/lang3/SerializationException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   89: athrow
    //   90: astore_0
    //   91: aload_1
    //   92: ifnull +7 -> 99
    //   95: aload_1
    //   96: invokevirtual 49	org/apache/commons/lang3/SerializationUtils$ClassLoaderAwareObjectInputStream:close	()V
    //   99: aload_0
    //   100: athrow
    //   101: astore_0
    //   102: aload_3
    //   103: astore_1
    //   104: new 51	org/apache/commons/lang3/SerializationException
    //   107: dup
    //   108: ldc 60
    //   110: aload_0
    //   111: invokespecial 56	org/apache/commons/lang3/SerializationException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   114: athrow
    //   115: astore_0
    //   116: new 51	org/apache/commons/lang3/SerializationException
    //   119: dup
    //   120: ldc 53
    //   122: aload_0
    //   123: invokespecial 56	org/apache/commons/lang3/SerializationException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   126: athrow
    //   127: astore_2
    //   128: aload_0
    //   129: astore_1
    //   130: aload_2
    //   131: astore_0
    //   132: goto -41 -> 91
    //   135: astore_2
    //   136: aload_0
    //   137: astore_1
    //   138: aload_2
    //   139: astore_0
    //   140: goto -36 -> 104
    //   143: astore_2
    //   144: aload_0
    //   145: astore_1
    //   146: aload_2
    //   147: astore_0
    //   148: goto -69 -> 79
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	paramT	T
    //   5	141	1	localObject1	Object
    //   26	52	2	localSerializable	Serializable
    //   127	4	2	localObject2	Object
    //   135	4	2	localIOException	IOException
    //   143	4	2	localClassNotFoundException	ClassNotFoundException
    //   24	79	3	localObject3	Object
    //   19	13	4	localByteArrayInputStream	ByteArrayInputStream
    // Exception table:
    //   from	to	target	type
    //   58	62	64	java/io/IOException
    //   27	44	76	java/lang/ClassNotFoundException
    //   27	44	90	finally
    //   79	90	90	finally
    //   104	115	90	finally
    //   27	44	101	java/io/IOException
    //   95	99	115	java/io/IOException
    //   44	52	127	finally
    //   44	52	135	java/io/IOException
    //   44	52	143	java/lang/ClassNotFoundException
  }
  
  /* Error */
  public static Object deserialize(InputStream paramInputStream)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +13 -> 14
    //   4: new 66	java/lang/IllegalArgumentException
    //   7: dup
    //   8: ldc 68
    //   10: invokespecial 71	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   13: athrow
    //   14: aconst_null
    //   15: astore_1
    //   16: aconst_null
    //   17: astore_3
    //   18: aconst_null
    //   19: astore_2
    //   20: new 73	java/io/ObjectInputStream
    //   23: dup
    //   24: aload_0
    //   25: invokespecial 76	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   28: astore_0
    //   29: aload_0
    //   30: invokevirtual 77	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   33: astore_1
    //   34: aload_0
    //   35: ifnull +7 -> 42
    //   38: aload_0
    //   39: invokevirtual 78	java/io/ObjectInputStream:close	()V
    //   42: aload_1
    //   43: areturn
    //   44: astore_0
    //   45: aload_2
    //   46: astore_1
    //   47: new 51	org/apache/commons/lang3/SerializationException
    //   50: dup
    //   51: aload_0
    //   52: invokespecial 81	org/apache/commons/lang3/SerializationException:<init>	(Ljava/lang/Throwable;)V
    //   55: athrow
    //   56: astore_0
    //   57: aload_1
    //   58: ifnull +7 -> 65
    //   61: aload_1
    //   62: invokevirtual 78	java/io/ObjectInputStream:close	()V
    //   65: aload_0
    //   66: athrow
    //   67: astore_0
    //   68: aload_3
    //   69: astore_1
    //   70: new 51	org/apache/commons/lang3/SerializationException
    //   73: dup
    //   74: aload_0
    //   75: invokespecial 81	org/apache/commons/lang3/SerializationException:<init>	(Ljava/lang/Throwable;)V
    //   78: athrow
    //   79: astore_0
    //   80: aload_1
    //   81: areturn
    //   82: astore_1
    //   83: goto -18 -> 65
    //   86: astore_2
    //   87: aload_0
    //   88: astore_1
    //   89: aload_2
    //   90: astore_0
    //   91: goto -34 -> 57
    //   94: astore_2
    //   95: aload_0
    //   96: astore_1
    //   97: aload_2
    //   98: astore_0
    //   99: goto -29 -> 70
    //   102: astore_2
    //   103: aload_0
    //   104: astore_1
    //   105: aload_2
    //   106: astore_0
    //   107: goto -60 -> 47
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	110	0	paramInputStream	InputStream
    //   15	66	1	localObject1	Object
    //   82	1	1	localIOException1	IOException
    //   88	17	1	localInputStream	InputStream
    //   19	27	2	localObject2	Object
    //   86	4	2	localObject3	Object
    //   94	4	2	localIOException2	IOException
    //   102	4	2	localClassNotFoundException	ClassNotFoundException
    //   17	52	3	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   20	29	44	java/lang/ClassNotFoundException
    //   20	29	56	finally
    //   47	56	56	finally
    //   70	79	56	finally
    //   20	29	67	java/io/IOException
    //   38	42	79	java/io/IOException
    //   61	65	82	java/io/IOException
    //   29	34	86	finally
    //   29	34	94	java/io/IOException
    //   29	34	102	java/lang/ClassNotFoundException
  }
  
  public static Object deserialize(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      throw new IllegalArgumentException("The byte[] must not be null");
    }
    return deserialize(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  /* Error */
  public static void serialize(Serializable paramSerializable, java.io.OutputStream paramOutputStream)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +13 -> 14
    //   4: new 66	java/lang/IllegalArgumentException
    //   7: dup
    //   8: ldc 89
    //   10: invokespecial 71	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   13: athrow
    //   14: aconst_null
    //   15: astore_2
    //   16: aconst_null
    //   17: astore_3
    //   18: new 91	java/io/ObjectOutputStream
    //   21: dup
    //   22: aload_1
    //   23: invokespecial 94	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   26: astore_1
    //   27: aload_1
    //   28: aload_0
    //   29: invokevirtual 98	java/io/ObjectOutputStream:writeObject	(Ljava/lang/Object;)V
    //   32: aload_1
    //   33: ifnull +7 -> 40
    //   36: aload_1
    //   37: invokevirtual 99	java/io/ObjectOutputStream:close	()V
    //   40: return
    //   41: astore_0
    //   42: aload_3
    //   43: astore_2
    //   44: new 51	org/apache/commons/lang3/SerializationException
    //   47: dup
    //   48: aload_0
    //   49: invokespecial 81	org/apache/commons/lang3/SerializationException:<init>	(Ljava/lang/Throwable;)V
    //   52: athrow
    //   53: astore_0
    //   54: aload_2
    //   55: ifnull +7 -> 62
    //   58: aload_2
    //   59: invokevirtual 99	java/io/ObjectOutputStream:close	()V
    //   62: aload_0
    //   63: athrow
    //   64: astore_0
    //   65: return
    //   66: astore_1
    //   67: goto -5 -> 62
    //   70: astore_0
    //   71: aload_1
    //   72: astore_2
    //   73: goto -19 -> 54
    //   76: astore_0
    //   77: aload_1
    //   78: astore_2
    //   79: goto -35 -> 44
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	82	0	paramSerializable	Serializable
    //   0	82	1	paramOutputStream	java.io.OutputStream
    //   15	64	2	localObject1	Object
    //   17	26	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   18	27	41	java/io/IOException
    //   18	27	53	finally
    //   44	53	53	finally
    //   36	40	64	java/io/IOException
    //   58	62	66	java/io/IOException
    //   27	32	70	finally
    //   27	32	76	java/io/IOException
  }
  
  public static byte[] serialize(Serializable paramSerializable)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(512);
    serialize(paramSerializable, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }
  
  static class ClassLoaderAwareObjectInputStream
    extends ObjectInputStream
  {
    private ClassLoader classLoader;
    
    public ClassLoaderAwareObjectInputStream(InputStream paramInputStream, ClassLoader paramClassLoader)
      throws IOException
    {
      super();
      this.classLoader = paramClassLoader;
    }
    
    protected Class<?> resolveClass(ObjectStreamClass paramObjectStreamClass)
      throws IOException, ClassNotFoundException
    {
      paramObjectStreamClass = paramObjectStreamClass.getName();
      try
      {
        Class localClass = Class.forName(paramObjectStreamClass, false, this.classLoader);
        return localClass;
      }
      catch (ClassNotFoundException localClassNotFoundException) {}
      return Class.forName(paramObjectStreamClass, false, Thread.currentThread().getContextClassLoader());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\SerializationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */