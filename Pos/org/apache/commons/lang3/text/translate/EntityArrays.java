package org.apache.commons.lang3.text.translate;

import java.lang.reflect.Array;

public class EntityArrays
{
  private static final String[][] APOS_ESCAPE;
  private static final String[][] APOS_UNESCAPE;
  private static final String[][] BASIC_ESCAPE;
  private static final String[][] BASIC_UNESCAPE;
  private static final String[][] HTML40_EXTENDED_ESCAPE;
  private static final String[][] HTML40_EXTENDED_UNESCAPE;
  private static final String[][] ISO8859_1_ESCAPE;
  private static final String[][] ISO8859_1_UNESCAPE;
  private static final String[][] JAVA_CTRL_CHARS_ESCAPE;
  private static final String[][] JAVA_CTRL_CHARS_UNESCAPE = invert(JAVA_CTRL_CHARS_ESCAPE);
  
  static
  {
    String[] arrayOfString1 = { " ", "&nbsp;" };
    String[] arrayOfString2 = { "¡", "&iexcl;" };
    String[] arrayOfString3 = { "¢", "&cent;" };
    String[] arrayOfString4 = { "£", "&pound;" };
    String[] arrayOfString5 = { "¤", "&curren;" };
    String[] arrayOfString6 = { "¥", "&yen;" };
    String[] arrayOfString7 = { "¦", "&brvbar;" };
    String[] arrayOfString8 = { "§", "&sect;" };
    String[] arrayOfString9 = { "¨", "&uml;" };
    String[] arrayOfString10 = { "©", "&copy;" };
    String[] arrayOfString11 = { "ª", "&ordf;" };
    String[] arrayOfString12 = { "«", "&laquo;" };
    String[] arrayOfString13 = { "¬", "&not;" };
    String[] arrayOfString14 = { "­", "&shy;" };
    String[] arrayOfString15 = { "®", "&reg;" };
    String[] arrayOfString16 = { "¯", "&macr;" };
    String[] arrayOfString17 = { "°", "&deg;" };
    String[] arrayOfString18 = { "±", "&plusmn;" };
    String[] arrayOfString19 = { "²", "&sup2;" };
    String[] arrayOfString20 = { "³", "&sup3;" };
    String[] arrayOfString21 = { "´", "&acute;" };
    String[] arrayOfString22 = { "µ", "&micro;" };
    String[] arrayOfString23 = { "·", "&middot;" };
    String[] arrayOfString24 = { "¸", "&cedil;" };
    String[] arrayOfString25 = { "¹", "&sup1;" };
    String[] arrayOfString26 = { "º", "&ordm;" };
    String[] arrayOfString27 = { "»", "&raquo;" };
    String[] arrayOfString28 = { "¼", "&frac14;" };
    String[] arrayOfString29 = { "½", "&frac12;" };
    String[] arrayOfString30 = { "¾", "&frac34;" };
    String[] arrayOfString31 = { "¿", "&iquest;" };
    String[] arrayOfString32 = { "À", "&Agrave;" };
    String[] arrayOfString33 = { "Á", "&Aacute;" };
    String[] arrayOfString34 = { "Â", "&Acirc;" };
    String[] arrayOfString35 = { "Ä", "&Auml;" };
    String[] arrayOfString36 = { "Å", "&Aring;" };
    String[] arrayOfString37 = { "Ç", "&Ccedil;" };
    String[] arrayOfString38 = { "È", "&Egrave;" };
    String[] arrayOfString39 = { "É", "&Eacute;" };
    String[] arrayOfString40 = { "Ê", "&Ecirc;" };
    String[] arrayOfString41 = { "Ë", "&Euml;" };
    String[] arrayOfString42 = { "Ì", "&Igrave;" };
    String[] arrayOfString43 = { "Í", "&Iacute;" };
    String[] arrayOfString44 = { "Î", "&Icirc;" };
    String[] arrayOfString45 = { "Ï", "&Iuml;" };
    String[] arrayOfString46 = { "Ð", "&ETH;" };
    String[] arrayOfString47 = { "Ñ", "&Ntilde;" };
    String[] arrayOfString48 = { "Ò", "&Ograve;" };
    String[] arrayOfString49 = { "Ó", "&Oacute;" };
    String[] arrayOfString50 = { "Ô", "&Ocirc;" };
    String[] arrayOfString51 = { "Õ", "&Otilde;" };
    String[] arrayOfString52 = { "Ö", "&Ouml;" };
    String[] arrayOfString53 = { "×", "&times;" };
    String[] arrayOfString54 = { "Ø", "&Oslash;" };
    String[] arrayOfString55 = { "Ù", "&Ugrave;" };
    String[] arrayOfString56 = { "Ú", "&Uacute;" };
    String[] arrayOfString57 = { "Û", "&Ucirc;" };
    String[] arrayOfString58 = { "Ü", "&Uuml;" };
    String[] arrayOfString59 = { "Ý", "&Yacute;" };
    String[] arrayOfString60 = { "Þ", "&THORN;" };
    String[] arrayOfString61 = { "ß", "&szlig;" };
    String[] arrayOfString62 = { "à", "&agrave;" };
    String[] arrayOfString63 = { "á", "&aacute;" };
    String[] arrayOfString64 = { "â", "&acirc;" };
    String[] arrayOfString65 = { "ã", "&atilde;" };
    String[] arrayOfString66 = { "ä", "&auml;" };
    String[] arrayOfString67 = { "å", "&aring;" };
    String[] arrayOfString68 = { "æ", "&aelig;" };
    String[] arrayOfString69 = { "ç", "&ccedil;" };
    String[] arrayOfString70 = { "è", "&egrave;" };
    String[] arrayOfString71 = { "é", "&eacute;" };
    String[] arrayOfString72 = { "ê", "&ecirc;" };
    String[] arrayOfString73 = { "ë", "&euml;" };
    String[] arrayOfString74 = { "ì", "&igrave;" };
    String[] arrayOfString75 = { "í", "&iacute;" };
    String[] arrayOfString76 = { "ï", "&iuml;" };
    String[] arrayOfString77 = { "ð", "&eth;" };
    String[] arrayOfString78 = { "ñ", "&ntilde;" };
    String[] arrayOfString79 = { "ò", "&ograve;" };
    String[] arrayOfString80 = { "ó", "&oacute;" };
    String[] arrayOfString81 = { "ô", "&ocirc;" };
    String[] arrayOfString82 = { "õ", "&otilde;" };
    String[] arrayOfString83 = { "ö", "&ouml;" };
    String[] arrayOfString84 = { "÷", "&divide;" };
    String[] arrayOfString85 = { "ø", "&oslash;" };
    String[] arrayOfString86 = { "ù", "&ugrave;" };
    String[] arrayOfString87 = { "ú", "&uacute;" };
    String[] arrayOfString88 = { "ý", "&yacute;" };
    String[] arrayOfString89 = { "þ", "&thorn;" };
    String[] arrayOfString90 = { "ÿ", "&yuml;" };
    ISO8859_1_ESCAPE = new String[][] { arrayOfString1, arrayOfString2, arrayOfString3, arrayOfString4, arrayOfString5, arrayOfString6, arrayOfString7, arrayOfString8, arrayOfString9, arrayOfString10, arrayOfString11, arrayOfString12, arrayOfString13, arrayOfString14, arrayOfString15, arrayOfString16, arrayOfString17, arrayOfString18, arrayOfString19, arrayOfString20, arrayOfString21, arrayOfString22, { "¶", "&para;" }, arrayOfString23, arrayOfString24, arrayOfString25, arrayOfString26, arrayOfString27, arrayOfString28, arrayOfString29, arrayOfString30, arrayOfString31, arrayOfString32, arrayOfString33, arrayOfString34, { "Ã", "&Atilde;" }, arrayOfString35, arrayOfString36, { "Æ", "&AElig;" }, arrayOfString37, arrayOfString38, arrayOfString39, arrayOfString40, arrayOfString41, arrayOfString42, arrayOfString43, arrayOfString44, arrayOfString45, arrayOfString46, arrayOfString47, arrayOfString48, arrayOfString49, arrayOfString50, arrayOfString51, arrayOfString52, arrayOfString53, arrayOfString54, arrayOfString55, arrayOfString56, arrayOfString57, arrayOfString58, arrayOfString59, arrayOfString60, arrayOfString61, arrayOfString62, arrayOfString63, arrayOfString64, arrayOfString65, arrayOfString66, arrayOfString67, arrayOfString68, arrayOfString69, arrayOfString70, arrayOfString71, arrayOfString72, arrayOfString73, arrayOfString74, arrayOfString75, { "î", "&icirc;" }, arrayOfString76, arrayOfString77, arrayOfString78, arrayOfString79, arrayOfString80, arrayOfString81, arrayOfString82, arrayOfString83, arrayOfString84, arrayOfString85, arrayOfString86, arrayOfString87, { "û", "&ucirc;" }, { "ü", "&uuml;" }, arrayOfString88, arrayOfString89, arrayOfString90 };
    ISO8859_1_UNESCAPE = invert(ISO8859_1_ESCAPE);
    arrayOfString1 = new String[] { "Ε", "&Epsilon;" };
    arrayOfString2 = new String[] { "Θ", "&Theta;" };
    arrayOfString3 = new String[] { "Ν", "&Nu;" };
    arrayOfString4 = new String[] { "Ξ", "&Xi;" };
    arrayOfString5 = new String[] { "Π", "&Pi;" };
    arrayOfString6 = new String[] { "θ", "&theta;" };
    arrayOfString7 = new String[] { "ξ", "&xi;" };
    arrayOfString8 = new String[] { "π", "&pi;" };
    arrayOfString9 = new String[] { "ϑ", "&thetasym;" };
    arrayOfString10 = new String[] { "•", "&bull;" };
    arrayOfString11 = new String[] { "…", "&hellip;" };
    arrayOfString12 = new String[] { "′", "&prime;" };
    arrayOfString13 = new String[] { "‾", "&oline;" };
    arrayOfString14 = new String[] { "←", "&larr;" };
    arrayOfString15 = new String[] { "↓", "&darr;" };
    arrayOfString16 = new String[] { "↔", "&harr;" };
    arrayOfString17 = new String[] { "⇓", "&dArr;" };
    arrayOfString18 = new String[] { "∃", "&exist;" };
    arrayOfString19 = new String[] { "∉", "&notin;" };
    arrayOfString20 = new String[] { "∏", "&prod;" };
    arrayOfString21 = new String[] { "∑", "&sum;" };
    arrayOfString22 = new String[] { "√", "&radic;" };
    arrayOfString23 = new String[] { "∞", "&infin;" };
    arrayOfString24 = new String[] { "∠", "&ang;" };
    arrayOfString25 = new String[] { "∪", "&cup;" };
    arrayOfString26 = new String[] { "≡", "&equiv;" };
    arrayOfString27 = new String[] { "≤", "&le;" };
    arrayOfString28 = new String[] { "≥", "&ge;" };
    arrayOfString29 = new String[] { "⊗", "&otimes;" };
    arrayOfString30 = new String[] { "〉", "&rang;" };
    arrayOfString31 = new String[] { "Œ", "&OElig;" };
    arrayOfString32 = new String[] { "š", "&scaron;" };
    arrayOfString33 = new String[] { "Ÿ", "&Yuml;" };
    arrayOfString34 = new String[] { "˜", "&tilde;" };
    arrayOfString35 = new String[] { " ", "&emsp;" };
    arrayOfString36 = new String[] { "‏", "&rlm;" };
    arrayOfString37 = new String[] { "“", "&ldquo;" };
    HTML40_EXTENDED_ESCAPE = new String[][] { { "ƒ", "&fnof;" }, { "Α", "&Alpha;" }, { "Β", "&Beta;" }, { "Γ", "&Gamma;" }, { "Δ", "&Delta;" }, arrayOfString1, { "Ζ", "&Zeta;" }, { "Η", "&Eta;" }, arrayOfString2, { "Ι", "&Iota;" }, { "Κ", "&Kappa;" }, { "Λ", "&Lambda;" }, { "Μ", "&Mu;" }, arrayOfString3, arrayOfString4, { "Ο", "&Omicron;" }, arrayOfString5, { "Ρ", "&Rho;" }, { "Σ", "&Sigma;" }, { "Τ", "&Tau;" }, { "Υ", "&Upsilon;" }, { "Φ", "&Phi;" }, { "Χ", "&Chi;" }, { "Ψ", "&Psi;" }, { "Ω", "&Omega;" }, { "α", "&alpha;" }, { "β", "&beta;" }, { "γ", "&gamma;" }, { "δ", "&delta;" }, { "ε", "&epsilon;" }, { "ζ", "&zeta;" }, { "η", "&eta;" }, arrayOfString6, { "ι", "&iota;" }, { "κ", "&kappa;" }, { "λ", "&lambda;" }, { "μ", "&mu;" }, { "ν", "&nu;" }, arrayOfString7, { "ο", "&omicron;" }, arrayOfString8, { "ρ", "&rho;" }, { "ς", "&sigmaf;" }, { "σ", "&sigma;" }, { "τ", "&tau;" }, { "υ", "&upsilon;" }, { "φ", "&phi;" }, { "χ", "&chi;" }, { "ψ", "&psi;" }, { "ω", "&omega;" }, arrayOfString9, { "ϒ", "&upsih;" }, { "ϖ", "&piv;" }, arrayOfString10, arrayOfString11, arrayOfString12, { "″", "&Prime;" }, arrayOfString13, { "⁄", "&frasl;" }, { "℘", "&weierp;" }, { "ℑ", "&image;" }, { "ℜ", "&real;" }, { "™", "&trade;" }, { "ℵ", "&alefsym;" }, arrayOfString14, { "↑", "&uarr;" }, { "→", "&rarr;" }, arrayOfString15, arrayOfString16, { "↵", "&crarr;" }, { "⇐", "&lArr;" }, { "⇑", "&uArr;" }, { "⇒", "&rArr;" }, arrayOfString17, { "⇔", "&hArr;" }, { "∀", "&forall;" }, { "∂", "&part;" }, arrayOfString18, { "∅", "&empty;" }, { "∇", "&nabla;" }, { "∈", "&isin;" }, arrayOfString19, { "∋", "&ni;" }, arrayOfString20, arrayOfString21, { "−", "&minus;" }, { "∗", "&lowast;" }, arrayOfString22, { "∝", "&prop;" }, arrayOfString23, arrayOfString24, { "∧", "&and;" }, { "∨", "&or;" }, { "∩", "&cap;" }, arrayOfString25, { "∫", "&int;" }, { "∴", "&there4;" }, { "∼", "&sim;" }, { "≅", "&cong;" }, { "≈", "&asymp;" }, { "≠", "&ne;" }, arrayOfString26, arrayOfString27, arrayOfString28, { "⊂", "&sub;" }, { "⊃", "&sup;" }, { "⊆", "&sube;" }, { "⊇", "&supe;" }, { "⊕", "&oplus;" }, arrayOfString29, { "⊥", "&perp;" }, { "⋅", "&sdot;" }, { "⌈", "&lceil;" }, { "⌉", "&rceil;" }, { "⌊", "&lfloor;" }, { "⌋", "&rfloor;" }, { "〈", "&lang;" }, arrayOfString30, { "◊", "&loz;" }, { "♠", "&spades;" }, { "♣", "&clubs;" }, { "♥", "&hearts;" }, { "♦", "&diams;" }, arrayOfString31, { "œ", "&oelig;" }, { "Š", "&Scaron;" }, arrayOfString32, arrayOfString33, { "ˆ", "&circ;" }, arrayOfString34, { " ", "&ensp;" }, arrayOfString35, { " ", "&thinsp;" }, { "‌", "&zwnj;" }, { "‍", "&zwj;" }, { "‎", "&lrm;" }, arrayOfString36, { "–", "&ndash;" }, { "—", "&mdash;" }, { "‘", "&lsquo;" }, { "’", "&rsquo;" }, { "‚", "&sbquo;" }, arrayOfString37, { "”", "&rdquo;" }, { "„", "&bdquo;" }, { "†", "&dagger;" }, { "‡", "&Dagger;" }, { "‰", "&permil;" }, { "‹", "&lsaquo;" }, { "›", "&rsaquo;" }, { "€", "&euro;" } };
    HTML40_EXTENDED_UNESCAPE = invert(HTML40_EXTENDED_ESCAPE);
    arrayOfString1 = new String[] { "&", "&amp;" };
    arrayOfString2 = new String[] { "<", "&lt;" };
    BASIC_ESCAPE = new String[][] { { "\"", "&quot;" }, arrayOfString1, arrayOfString2, { ">", "&gt;" } };
    BASIC_UNESCAPE = invert(BASIC_ESCAPE);
    APOS_ESCAPE = new String[][] { { "'", "&apos;" } };
    APOS_UNESCAPE = invert(APOS_ESCAPE);
    arrayOfString1 = new String[] { "\r", "\\r" };
    JAVA_CTRL_CHARS_ESCAPE = new String[][] { { "\b", "\\b" }, { "\n", "\\n" }, { "\t", "\\t" }, { "\f", "\\f" }, arrayOfString1 };
  }
  
  public static String[][] APOS_ESCAPE()
  {
    return (String[][])APOS_ESCAPE.clone();
  }
  
  public static String[][] APOS_UNESCAPE()
  {
    return (String[][])APOS_UNESCAPE.clone();
  }
  
  public static String[][] BASIC_ESCAPE()
  {
    return (String[][])BASIC_ESCAPE.clone();
  }
  
  public static String[][] BASIC_UNESCAPE()
  {
    return (String[][])BASIC_UNESCAPE.clone();
  }
  
  public static String[][] HTML40_EXTENDED_ESCAPE()
  {
    return (String[][])HTML40_EXTENDED_ESCAPE.clone();
  }
  
  public static String[][] HTML40_EXTENDED_UNESCAPE()
  {
    return (String[][])HTML40_EXTENDED_UNESCAPE.clone();
  }
  
  public static String[][] ISO8859_1_ESCAPE()
  {
    return (String[][])ISO8859_1_ESCAPE.clone();
  }
  
  public static String[][] ISO8859_1_UNESCAPE()
  {
    return (String[][])ISO8859_1_UNESCAPE.clone();
  }
  
  public static String[][] JAVA_CTRL_CHARS_ESCAPE()
  {
    return (String[][])JAVA_CTRL_CHARS_ESCAPE.clone();
  }
  
  public static String[][] JAVA_CTRL_CHARS_UNESCAPE()
  {
    return (String[][])JAVA_CTRL_CHARS_UNESCAPE.clone();
  }
  
  public static String[][] invert(String[][] paramArrayOfString)
  {
    String[][] arrayOfString = (String[][])Array.newInstance(String.class, new int[] { paramArrayOfString.length, 2 });
    int i = 0;
    while (i < paramArrayOfString.length)
    {
      arrayOfString[i][0] = paramArrayOfString[i][1];
      arrayOfString[i][1] = paramArrayOfString[i][0];
      i += 1;
    }
    return arrayOfString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\classes-dex2jar.jar!\org\apache\commons\lang3\text\translate\EntityArrays.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */