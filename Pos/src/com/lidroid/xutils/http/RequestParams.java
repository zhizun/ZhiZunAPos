package com.lidroid.xutils.http;

import android.text.TextUtils;
import com.lidroid.xutils.http.client.entity.BodyParamsEntity;
import com.lidroid.xutils.http.client.multipart.HttpMultipartMode;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.ContentBody;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.http.client.multipart.content.InputStreamBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;
import com.lidroid.xutils.task.Priority;
import com.lidroid.xutils.util.LogUtils;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

public class RequestParams
{
  private HttpEntity bodyEntity;
  private List<NameValuePair> bodyParams;
  private String charset = "UTF-8";
  private HashMap<String, ContentBody> fileParams;
  private List<HeaderItem> headers;
  private Priority priority;
  private List<NameValuePair> queryStringParams;
  
  public RequestParams() {}
  
  public RequestParams(String paramString)
  {
    if (!TextUtils.isEmpty(paramString)) {
      this.charset = paramString;
    }
  }
  
  public void addBodyParameter(String paramString, File paramFile)
  {
    if (this.fileParams == null) {
      this.fileParams = new HashMap();
    }
    this.fileParams.put(paramString, new FileBody(paramFile));
  }
  
  public void addBodyParameter(String paramString1, File paramFile, String paramString2)
  {
    if (this.fileParams == null) {
      this.fileParams = new HashMap();
    }
    this.fileParams.put(paramString1, new FileBody(paramFile, paramString2));
  }
  
  public void addBodyParameter(String paramString1, File paramFile, String paramString2, String paramString3)
  {
    if (this.fileParams == null) {
      this.fileParams = new HashMap();
    }
    this.fileParams.put(paramString1, new FileBody(paramFile, paramString2, paramString3));
  }
  
  public void addBodyParameter(String paramString1, File paramFile, String paramString2, String paramString3, String paramString4)
  {
    if (this.fileParams == null) {
      this.fileParams = new HashMap();
    }
    this.fileParams.put(paramString1, new FileBody(paramFile, paramString2, paramString3, paramString4));
  }
  
  public void addBodyParameter(String paramString, InputStream paramInputStream, long paramLong)
  {
    if (this.fileParams == null) {
      this.fileParams = new HashMap();
    }
    this.fileParams.put(paramString, new InputStreamBody(paramInputStream, paramLong));
  }
  
  public void addBodyParameter(String paramString1, InputStream paramInputStream, long paramLong, String paramString2)
  {
    if (this.fileParams == null) {
      this.fileParams = new HashMap();
    }
    this.fileParams.put(paramString1, new InputStreamBody(paramInputStream, paramLong, paramString2));
  }
  
  public void addBodyParameter(String paramString1, InputStream paramInputStream, long paramLong, String paramString2, String paramString3)
  {
    if (this.fileParams == null) {
      this.fileParams = new HashMap();
    }
    this.fileParams.put(paramString1, new InputStreamBody(paramInputStream, paramLong, paramString2, paramString3));
  }
  
  public void addBodyParameter(String paramString1, String paramString2)
  {
    if (this.bodyParams == null) {
      this.bodyParams = new ArrayList();
    }
    this.bodyParams.add(new BasicNameValuePair(paramString1, paramString2));
  }
  
  public void addBodyParameter(List<NameValuePair> paramList)
  {
    if (this.bodyParams == null) {
      this.bodyParams = new ArrayList();
    }
    if ((paramList != null) && (paramList.size() > 0)) {
      paramList = paramList.iterator();
    }
    for (;;)
    {
      if (!paramList.hasNext()) {
        return;
      }
      NameValuePair localNameValuePair = (NameValuePair)paramList.next();
      this.bodyParams.add(localNameValuePair);
    }
  }
  
  public void addBodyParameter(NameValuePair paramNameValuePair)
  {
    if (this.bodyParams == null) {
      this.bodyParams = new ArrayList();
    }
    this.bodyParams.add(paramNameValuePair);
  }
  
  public void addHeader(String paramString1, String paramString2)
  {
    if (this.headers == null) {
      this.headers = new ArrayList();
    }
    this.headers.add(new HeaderItem(paramString1, paramString2));
  }
  
  public void addHeader(Header paramHeader)
  {
    if (this.headers == null) {
      this.headers = new ArrayList();
    }
    this.headers.add(new HeaderItem(paramHeader));
  }
  
  public void addHeaders(List<Header> paramList)
  {
    if (this.headers == null) {
      this.headers = new ArrayList();
    }
    paramList = paramList.iterator();
    for (;;)
    {
      if (!paramList.hasNext()) {
        return;
      }
      Header localHeader = (Header)paramList.next();
      this.headers.add(new HeaderItem(localHeader));
    }
  }
  
  public void addQueryStringParameter(String paramString1, String paramString2)
  {
    if (this.queryStringParams == null) {
      this.queryStringParams = new ArrayList();
    }
    this.queryStringParams.add(new BasicNameValuePair(paramString1, paramString2));
  }
  
  public void addQueryStringParameter(List<NameValuePair> paramList)
  {
    if (this.queryStringParams == null) {
      this.queryStringParams = new ArrayList();
    }
    if ((paramList != null) && (paramList.size() > 0)) {
      paramList = paramList.iterator();
    }
    for (;;)
    {
      if (!paramList.hasNext()) {
        return;
      }
      NameValuePair localNameValuePair = (NameValuePair)paramList.next();
      this.queryStringParams.add(localNameValuePair);
    }
  }
  
  public void addQueryStringParameter(NameValuePair paramNameValuePair)
  {
    if (this.queryStringParams == null) {
      this.queryStringParams = new ArrayList();
    }
    this.queryStringParams.add(paramNameValuePair);
  }
  
  public String getCharset()
  {
    return this.charset;
  }
  
  public HttpEntity getEntity()
  {
    Object localObject;
    if (this.bodyEntity != null) {
      localObject = this.bodyEntity;
    }
    do
    {
      Iterator localIterator;
      do
      {
        return (HttpEntity)localObject;
        localIterator = null;
        if ((this.fileParams != null) && (!this.fileParams.isEmpty()))
        {
          localObject = new MultipartEntity(HttpMultipartMode.STRICT, null, Charset.forName(this.charset));
          if ((this.bodyParams != null) && (!this.bodyParams.isEmpty()))
          {
            localIterator = this.bodyParams.iterator();
            if (localIterator.hasNext()) {}
          }
          else
          {
            localIterator = this.fileParams.entrySet().iterator();
          }
          for (;;)
          {
            if (!localIterator.hasNext())
            {
              return (HttpEntity)localObject;
              NameValuePair localNameValuePair = (NameValuePair)localIterator.next();
              try
              {
                ((MultipartEntity)localObject).addPart(localNameValuePair.getName(), new StringBody(localNameValuePair.getValue()));
              }
              catch (UnsupportedEncodingException localUnsupportedEncodingException)
              {
                LogUtils.e(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
              }
              break;
            }
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            ((MultipartEntity)localObject).addPart((String)localEntry.getKey(), (ContentBody)localEntry.getValue());
          }
        }
        localObject = localIterator;
      } while (this.bodyParams == null);
      localObject = localIterator;
    } while (this.bodyParams.isEmpty());
    return new BodyParamsEntity(this.bodyParams, this.charset);
  }
  
  public List<HeaderItem> getHeaders()
  {
    return this.headers;
  }
  
  public Priority getPriority()
  {
    return this.priority;
  }
  
  public List<NameValuePair> getQueryStringParams()
  {
    return this.queryStringParams;
  }
  
  public void setBodyEntity(HttpEntity paramHttpEntity)
  {
    this.bodyEntity = paramHttpEntity;
    if (this.bodyParams != null)
    {
      this.bodyParams.clear();
      this.bodyParams = null;
    }
    if (this.fileParams != null)
    {
      this.fileParams.clear();
      this.fileParams = null;
    }
  }
  
  public void setContentType(String paramString)
  {
    setHeader("Content-Type", paramString);
  }
  
  public void setHeader(String paramString1, String paramString2)
  {
    if (this.headers == null) {
      this.headers = new ArrayList();
    }
    this.headers.add(new HeaderItem(paramString1, paramString2, true));
  }
  
  public void setHeader(Header paramHeader)
  {
    if (this.headers == null) {
      this.headers = new ArrayList();
    }
    this.headers.add(new HeaderItem(paramHeader, true));
  }
  
  public void setHeaders(List<Header> paramList)
  {
    if (this.headers == null) {
      this.headers = new ArrayList();
    }
    paramList = paramList.iterator();
    for (;;)
    {
      if (!paramList.hasNext()) {
        return;
      }
      Header localHeader = (Header)paramList.next();
      this.headers.add(new HeaderItem(localHeader, true));
    }
  }
  
  public void setPriority(Priority paramPriority)
  {
    this.priority = paramPriority;
  }
  
  public class HeaderItem
  {
    public final Header header;
    public final boolean overwrite;
    
    public HeaderItem(String paramString1, String paramString2)
    {
      this.overwrite = false;
      this.header = new BasicHeader(paramString1, paramString2);
    }
    
    public HeaderItem(String paramString1, String paramString2, boolean paramBoolean)
    {
      this.overwrite = paramBoolean;
      this.header = new BasicHeader(paramString1, paramString2);
    }
    
    public HeaderItem(Header paramHeader)
    {
      this.overwrite = false;
      this.header = paramHeader;
    }
    
    public HeaderItem(Header paramHeader, boolean paramBoolean)
    {
      this.overwrite = paramBoolean;
      this.header = paramHeader;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\com\lidroid\xutils\http\RequestParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */