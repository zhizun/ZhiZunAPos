package com.zizun.cs.common.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import com.lidroid.xutils.util.LogUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil
{
  public static final String APP_FILE_NAME = "CloudStore";
  
  /* Error */
  public static void CopyDbToSdcard(Context paramContext, String paramString1, String paramString2)
    throws IOException
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aconst_null
    //   4: astore 6
    //   6: aconst_null
    //   7: astore 4
    //   9: aconst_null
    //   10: astore 5
    //   12: aconst_null
    //   13: astore 8
    //   15: aconst_null
    //   16: astore 9
    //   18: aconst_null
    //   19: astore 7
    //   21: new 21	java/lang/StringBuilder
    //   24: dup
    //   25: aload_2
    //   26: invokestatic 27	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   29: invokespecial 30	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   32: aload_1
    //   33: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   39: astore_2
    //   40: aload_2
    //   41: invokestatic 43	com/lidroid/xutils/util/LogUtils:i	(Ljava/lang/String;)V
    //   44: new 45	java/io/File
    //   47: dup
    //   48: aload_2
    //   49: invokespecial 46	java/io/File:<init>	(Ljava/lang/String;)V
    //   52: astore 10
    //   54: aload 10
    //   56: invokevirtual 50	java/io/File:exists	()Z
    //   59: ifne +84 -> 143
    //   62: aload 10
    //   64: invokevirtual 54	java/io/File:getParentFile	()Ljava/io/File;
    //   67: invokevirtual 57	java/io/File:mkdirs	()Z
    //   70: pop
    //   71: aload 4
    //   73: astore_2
    //   74: aload 9
    //   76: astore 4
    //   78: aload_0
    //   79: aload_1
    //   80: invokestatic 61	com/yumendian/cs/common/utils/FileUtil:getInputStreamFromAssets	(Landroid/content/Context;Ljava/lang/String;)Ljava/io/InputStream;
    //   83: astore_0
    //   84: aload_0
    //   85: astore 5
    //   87: aload_0
    //   88: astore 6
    //   90: aload_0
    //   91: astore_2
    //   92: aload 9
    //   94: astore 4
    //   96: new 63	java/io/FileOutputStream
    //   99: dup
    //   100: aload 10
    //   102: invokespecial 66	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   105: astore_1
    //   106: sipush 4096
    //   109: newarray <illegal type>
    //   111: astore_2
    //   112: aload_0
    //   113: aload_2
    //   114: invokevirtual 72	java/io/InputStream:read	([B)I
    //   117: istore_3
    //   118: iload_3
    //   119: iconst_m1
    //   120: if_icmpne +27 -> 147
    //   123: aload_1
    //   124: invokevirtual 77	java/io/OutputStream:flush	()V
    //   127: aload_0
    //   128: ifnull +7 -> 135
    //   131: aload_0
    //   132: invokevirtual 80	java/io/InputStream:close	()V
    //   135: aload_1
    //   136: ifnull +7 -> 143
    //   139: aload_1
    //   140: invokevirtual 81	java/io/OutputStream:close	()V
    //   143: ldc 2
    //   145: monitorexit
    //   146: return
    //   147: aload_1
    //   148: aload_2
    //   149: iconst_0
    //   150: iload_3
    //   151: invokevirtual 85	java/io/OutputStream:write	([BII)V
    //   154: goto -42 -> 112
    //   157: astore 5
    //   159: aload_0
    //   160: astore_2
    //   161: aload_1
    //   162: astore 4
    //   164: aload 5
    //   166: invokevirtual 88	java/io/FileNotFoundException:printStackTrace	()V
    //   169: aload_0
    //   170: ifnull +7 -> 177
    //   173: aload_0
    //   174: invokevirtual 80	java/io/InputStream:close	()V
    //   177: aload_1
    //   178: ifnull -35 -> 143
    //   181: aload_1
    //   182: invokevirtual 81	java/io/OutputStream:close	()V
    //   185: goto -42 -> 143
    //   188: astore_0
    //   189: ldc 2
    //   191: monitorexit
    //   192: aload_0
    //   193: athrow
    //   194: astore_2
    //   195: aload 8
    //   197: astore_1
    //   198: aload 5
    //   200: astore_0
    //   201: aload_2
    //   202: astore 5
    //   204: aload_0
    //   205: astore_2
    //   206: aload_1
    //   207: astore 4
    //   209: aload 5
    //   211: invokevirtual 89	java/io/IOException:printStackTrace	()V
    //   214: aload_0
    //   215: ifnull +7 -> 222
    //   218: aload_0
    //   219: invokevirtual 80	java/io/InputStream:close	()V
    //   222: aload_1
    //   223: ifnull -80 -> 143
    //   226: aload_1
    //   227: invokevirtual 81	java/io/OutputStream:close	()V
    //   230: goto -87 -> 143
    //   233: aload_0
    //   234: ifnull +7 -> 241
    //   237: aload_0
    //   238: invokevirtual 80	java/io/InputStream:close	()V
    //   241: aload 4
    //   243: ifnull +8 -> 251
    //   246: aload 4
    //   248: invokevirtual 81	java/io/OutputStream:close	()V
    //   251: aload_1
    //   252: athrow
    //   253: astore_2
    //   254: aload_1
    //   255: astore 4
    //   257: aload_2
    //   258: astore_1
    //   259: goto -26 -> 233
    //   262: astore 5
    //   264: goto -60 -> 204
    //   267: astore 5
    //   269: aload 6
    //   271: astore_0
    //   272: aload 7
    //   274: astore_1
    //   275: goto -116 -> 159
    //   278: astore_0
    //   279: goto -90 -> 189
    //   282: astore_1
    //   283: aload_2
    //   284: astore_0
    //   285: goto -52 -> 233
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	288	0	paramContext	Context
    //   0	288	1	paramString1	String
    //   0	288	2	paramString2	String
    //   117	34	3	i	int
    //   7	249	4	localObject1	Object
    //   10	76	5	localContext1	Context
    //   157	42	5	localFileNotFoundException1	java.io.FileNotFoundException
    //   202	8	5	str	String
    //   262	1	5	localIOException	IOException
    //   267	1	5	localFileNotFoundException2	java.io.FileNotFoundException
    //   4	266	6	localContext2	Context
    //   19	254	7	localObject2	Object
    //   13	183	8	localObject3	Object
    //   16	77	9	localObject4	Object
    //   52	49	10	localFile	File
    // Exception table:
    //   from	to	target	type
    //   106	112	157	java/io/FileNotFoundException
    //   112	118	157	java/io/FileNotFoundException
    //   123	127	157	java/io/FileNotFoundException
    //   147	154	157	java/io/FileNotFoundException
    //   21	71	188	finally
    //   173	177	188	finally
    //   181	185	188	finally
    //   218	222	188	finally
    //   226	230	188	finally
    //   237	241	188	finally
    //   246	251	188	finally
    //   251	253	188	finally
    //   78	84	194	java/io/IOException
    //   96	106	194	java/io/IOException
    //   106	112	253	finally
    //   112	118	253	finally
    //   123	127	253	finally
    //   147	154	253	finally
    //   106	112	262	java/io/IOException
    //   112	118	262	java/io/IOException
    //   123	127	262	java/io/IOException
    //   147	154	262	java/io/IOException
    //   78	84	267	java/io/FileNotFoundException
    //   96	106	267	java/io/FileNotFoundException
    //   131	135	278	finally
    //   139	143	278	finally
    //   78	84	282	finally
    //   96	106	282	finally
    //   164	169	282	finally
    //   209	214	282	finally
  }
  
  /* Error */
  public static void copyFile(File paramFile1, File paramFile2)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore_3
    //   5: new 94	java/io/BufferedInputStream
    //   8: dup
    //   9: new 96	java/io/FileInputStream
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 97	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   17: invokespecial 100	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   20: astore_0
    //   21: new 102	java/io/BufferedOutputStream
    //   24: dup
    //   25: new 63	java/io/FileOutputStream
    //   28: dup
    //   29: aload_1
    //   30: invokespecial 66	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   33: invokespecial 105	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   36: astore_1
    //   37: sipush 5120
    //   40: newarray <illegal type>
    //   42: astore_3
    //   43: aload_0
    //   44: aload_3
    //   45: invokevirtual 106	java/io/BufferedInputStream:read	([B)I
    //   48: istore_2
    //   49: iload_2
    //   50: iconst_m1
    //   51: if_icmpne +24 -> 75
    //   54: aload_1
    //   55: invokevirtual 107	java/io/BufferedOutputStream:flush	()V
    //   58: aload_0
    //   59: ifnull +7 -> 66
    //   62: aload_0
    //   63: invokevirtual 108	java/io/BufferedInputStream:close	()V
    //   66: aload_1
    //   67: ifnull +7 -> 74
    //   70: aload_1
    //   71: invokevirtual 109	java/io/BufferedOutputStream:close	()V
    //   74: return
    //   75: aload_1
    //   76: aload_3
    //   77: iconst_0
    //   78: iload_2
    //   79: invokevirtual 110	java/io/BufferedOutputStream:write	([BII)V
    //   82: goto -39 -> 43
    //   85: astore 4
    //   87: aload_1
    //   88: astore_3
    //   89: aload 4
    //   91: astore_1
    //   92: aload_0
    //   93: ifnull +7 -> 100
    //   96: aload_0
    //   97: invokevirtual 108	java/io/BufferedInputStream:close	()V
    //   100: aload_3
    //   101: ifnull +7 -> 108
    //   104: aload_3
    //   105: invokevirtual 109	java/io/BufferedOutputStream:close	()V
    //   108: aload_1
    //   109: athrow
    //   110: astore_1
    //   111: aload 4
    //   113: astore_0
    //   114: goto -22 -> 92
    //   117: astore_1
    //   118: goto -26 -> 92
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	121	0	paramFile1	File
    //   0	121	1	paramFile2	File
    //   48	31	2	i	int
    //   4	101	3	localObject1	Object
    //   1	1	4	localObject2	Object
    //   85	27	4	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   37	43	85	finally
    //   43	49	85	finally
    //   54	58	85	finally
    //   75	82	85	finally
    //   5	21	110	finally
    //   21	37	117	finally
  }
  
  public static void deleteFile(File paramFile)
  {
    if (paramFile.exists())
    {
      if (paramFile.isFile()) {
        paramFile.delete();
      }
      for (;;)
      {
        paramFile.delete();
        return;
        if (paramFile.isDirectory())
        {
          File[] arrayOfFile = paramFile.listFiles();
          int i = 0;
          while (i < arrayOfFile.length)
          {
            deleteFile(arrayOfFile[i]);
            i += 1;
          }
        }
      }
    }
    LogUtils.i("文件不存在!");
  }
  
  public static String getAppStoragePath()
  {
    return getRootFilePath("CloudStore/");
  }
  
  public static String getAppStoragePath(int paramInt)
  {
    return getRootFilePath("CloudStore/" + paramInt);
  }
  
  public static InputStream getInputStreamFromAssets(Context paramContext, String paramString)
    throws IOException
  {
    return paramContext.getResources().getAssets().open(paramString);
  }
  
  public static String getRootFilePath(String paramString)
  {
    if (hasSDCard()) {
      return getSDCardPath() + "/" + paramString + "/";
    }
    return getSDCardPath() + "/data/" + paramString;
  }
  
  public static String getSDCardPath()
  {
    return Environment.getExternalStorageDirectory().getAbsolutePath();
  }
  
  public static boolean hasSDCard()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }
  
  public static boolean isFileExist(String paramString)
  {
    return new File(paramString).exists();
  }
}