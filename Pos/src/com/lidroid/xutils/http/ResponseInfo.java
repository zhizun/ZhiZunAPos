package com.lidroid.xutils.http;

import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;

public final class ResponseInfo<T>
{
  public final Header contentEncoding;
  public final long contentLength;
  public final Header contentType;
  public final Locale locale;
  public final ProtocolVersion protocolVersion;
  public final String reasonPhrase;
  private final HttpResponse response;
  public T result;
  public final boolean resultFormCache;
  public final int statusCode;
  
  public ResponseInfo(HttpResponse paramHttpResponse, T paramT, boolean paramBoolean)
  {
    this.response = paramHttpResponse;
    this.result = paramT;
    this.resultFormCache = paramBoolean;
    if (paramHttpResponse != null)
    {
      this.locale = paramHttpResponse.getLocale();
      paramT = paramHttpResponse.getStatusLine();
      if (paramT != null)
      {
        this.statusCode = paramT.getStatusCode();
        this.protocolVersion = paramT.getProtocolVersion();
      }
      for (this.reasonPhrase = paramT.getReasonPhrase();; this.reasonPhrase = null)
      {
        paramHttpResponse = paramHttpResponse.getEntity();
        if (paramHttpResponse == null) {
          break;
        }
        this.contentLength = paramHttpResponse.getContentLength();
        this.contentType = paramHttpResponse.getContentType();
        this.contentEncoding = paramHttpResponse.getContentEncoding();
        return;
        this.statusCode = 0;
        this.protocolVersion = null;
      }
      this.contentLength = 0L;
      this.contentType = null;
      this.contentEncoding = null;
      return;
    }
    this.locale = null;
    this.statusCode = 0;
    this.protocolVersion = null;
    this.reasonPhrase = null;
    this.contentLength = 0L;
    this.contentType = null;
    this.contentEncoding = null;
  }
  
  public Header[] getAllHeaders()
  {
    if (this.response == null) {
      return null;
    }
    return this.response.getAllHeaders();
  }
  
  public Header getFirstHeader(String paramString)
  {
    if (this.response == null) {
      return null;
    }
    return this.response.getFirstHeader(paramString);
  }
  
  public Header[] getHeaders(String paramString)
  {
    if (this.response == null) {
      return null;
    }
    return this.response.getHeaders(paramString);
  }
  
  public Header getLastHeader(String paramString)
  {
    if (this.response == null) {
      return null;
    }
    return this.response.getLastHeader(paramString);
  }
}