package org.apache.commons.lang3;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.EntityArrays;
import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper.OPTION;
import org.apache.commons.lang3.text.translate.OctalUnescaper;
import org.apache.commons.lang3.text.translate.UnicodeEscaper;
import org.apache.commons.lang3.text.translate.UnicodeUnescaper;

public class StringEscapeUtils
{
  public static final CharSequenceTranslator ESCAPE_CSV;
  public static final CharSequenceTranslator ESCAPE_ECMASCRIPT;
  public static final CharSequenceTranslator ESCAPE_HTML3;
  public static final CharSequenceTranslator ESCAPE_HTML4;
  public static final CharSequenceTranslator ESCAPE_JAVA;
  public static final CharSequenceTranslator ESCAPE_XML;
  public static final CharSequenceTranslator UNESCAPE_CSV = new CsvUnescaper();
  public static final CharSequenceTranslator UNESCAPE_ECMASCRIPT;
  public static final CharSequenceTranslator UNESCAPE_HTML3;
  public static final CharSequenceTranslator UNESCAPE_HTML4;
  public static final CharSequenceTranslator UNESCAPE_JAVA;
  public static final CharSequenceTranslator UNESCAPE_XML;
  
  static
  {
    Object localObject1 = { "\\", "\\\\" };
    ESCAPE_JAVA = new LookupTranslator(new String[][] { { "\"", "\\\"" }, localObject1 }).with(new CharSequenceTranslator[] { new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE()) }).with(new CharSequenceTranslator[] { UnicodeEscaper.outsideOf(32, 127) });
    localObject1 = new String[] { "\"", "\\\"" };
    Object localObject2 = { "\\", "\\\\" };
    Object localObject3 = { "/", "\\/" };
    ESCAPE_ECMASCRIPT = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator(new String[][] { { "'", "\\'" }, localObject1, localObject2, localObject3 }), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE()), UnicodeEscaper.outsideOf(32, 127) });
    ESCAPE_XML = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.APOS_ESCAPE()) });
    ESCAPE_HTML3 = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()) });
    ESCAPE_HTML4 = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()), new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE()) });
    ESCAPE_CSV = new CsvEscaper();
    localObject1 = new OctalUnescaper();
    localObject2 = new UnicodeUnescaper();
    localObject3 = new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_UNESCAPE());
    String[] arrayOfString1 = { "\\\\", "\\" };
    String[] arrayOfString2 = { "\\\"", "\"" };
    String[] arrayOfString3 = { "\\", "" };
    UNESCAPE_JAVA = new AggregateTranslator(new CharSequenceTranslator[] { localObject1, localObject2, localObject3, new LookupTranslator(new String[][] { arrayOfString1, arrayOfString2, { "\\'", "'" }, arrayOfString3 }) });
    UNESCAPE_ECMASCRIPT = UNESCAPE_JAVA;
    UNESCAPE_HTML3 = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE()), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]) });
    UNESCAPE_HTML4 = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE()), new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE()), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]) });
    UNESCAPE_XML = new AggregateTranslator(new CharSequenceTranslator[] { new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.APOS_UNESCAPE()), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]) });
  }
  
  public static final String escapeCsv(String paramString)
  {
    return ESCAPE_CSV.translate(paramString);
  }
  
  public static final String escapeEcmaScript(String paramString)
  {
    return ESCAPE_ECMASCRIPT.translate(paramString);
  }
  
  public static final String escapeHtml3(String paramString)
  {
    return ESCAPE_HTML3.translate(paramString);
  }
  
  public static final String escapeHtml4(String paramString)
  {
    return ESCAPE_HTML4.translate(paramString);
  }
  
  public static final String escapeJava(String paramString)
  {
    return ESCAPE_JAVA.translate(paramString);
  }
  
  public static final String escapeXml(String paramString)
  {
    return ESCAPE_XML.translate(paramString);
  }
  
  public static final String unescapeCsv(String paramString)
  {
    return UNESCAPE_CSV.translate(paramString);
  }
  
  public static final String unescapeEcmaScript(String paramString)
  {
    return UNESCAPE_ECMASCRIPT.translate(paramString);
  }
  
  public static final String unescapeHtml3(String paramString)
  {
    return UNESCAPE_HTML3.translate(paramString);
  }
  
  public static final String unescapeHtml4(String paramString)
  {
    return UNESCAPE_HTML4.translate(paramString);
  }
  
  public static final String unescapeJava(String paramString)
  {
    return UNESCAPE_JAVA.translate(paramString);
  }
  
  public static final String unescapeXml(String paramString)
  {
    return UNESCAPE_XML.translate(paramString);
  }
  
  static class CsvEscaper
    extends CharSequenceTranslator
  {
    private static final char CSV_DELIMITER = ',';
    private static final char CSV_QUOTE = '"';
    private static final String CSV_QUOTE_STR = String.valueOf('"');
    private static final char[] CSV_SEARCH_CHARS = { 44, 34, 13, 10 };
    
    public int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
      throws IOException
    {
      if (paramInt != 0) {
        throw new IllegalStateException("CsvEscaper should never reach the [1] index");
      }
      if (StringUtils.containsNone(paramCharSequence.toString(), CSV_SEARCH_CHARS)) {
        paramWriter.write(paramCharSequence.toString());
      }
      for (;;)
      {
        return paramCharSequence.length();
        paramWriter.write(34);
        paramWriter.write(StringUtils.replace(paramCharSequence.toString(), CSV_QUOTE_STR, CSV_QUOTE_STR + CSV_QUOTE_STR));
        paramWriter.write(34);
      }
    }
  }
  
  static class CsvUnescaper
    extends CharSequenceTranslator
  {
    private static final char CSV_DELIMITER = ',';
    private static final char CSV_QUOTE = '"';
    private static final String CSV_QUOTE_STR = String.valueOf('"');
    private static final char[] CSV_SEARCH_CHARS = { 44, 34, 13, 10 };
    
    public int translate(CharSequence paramCharSequence, int paramInt, Writer paramWriter)
      throws IOException
    {
      if (paramInt != 0) {
        throw new IllegalStateException("CsvUnescaper should never reach the [1] index");
      }
      if ((paramCharSequence.charAt(0) != '"') || (paramCharSequence.charAt(paramCharSequence.length() - 1) != '"'))
      {
        paramWriter.write(paramCharSequence.toString());
        return paramCharSequence.length();
      }
      String str = paramCharSequence.subSequence(1, paramCharSequence.length() - 1).toString();
      if (StringUtils.containsAny(str, CSV_SEARCH_CHARS)) {
        paramWriter.write(StringUtils.replace(str, CSV_QUOTE_STR + CSV_QUOTE_STR, CSV_QUOTE_STR));
      }
      for (;;)
      {
        return paramCharSequence.length();
        paramWriter.write(paramCharSequence.toString());
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\StringEscapeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */