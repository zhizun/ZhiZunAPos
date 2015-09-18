package com.ch.epw.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * SHA加密
 * 
 * @author wpl
 * 
 */
public class SHAEncrypt {
    private static final int SALT_LEN = 32;
    private static final String ENCODE_TYPE = "SHA-256";
    /**
     * 静态SALT
     */
    private static final String MAGIC_KEY = "&*(*)HGFFDFFGhdjfjkshdk989";
    /**
     * 循环加密次数
     */
    private final static int ENCODE_COUNT = 100;
    static private final char base64_code[] =
            {'.', '/', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
                    'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
                    'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String genSalt() {
        StringBuffer rs = new StringBuffer();
        SecureRandom random = new SecureRandom();
        byte rnd[] = new byte[SALT_LEN];
        random.nextBytes(rnd);
        rs.append(encode_base64(rnd, rnd.length));
        return rs.toString();
    }

    public static String hashpw(String plaintext, String salt) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance(ENCODE_TYPE);
            String _salt = joinPwWithSalt(plaintext, salt);
            md.update(_salt.getBytes());

            byte byteData[] = md.digest(plaintext.getBytes());
            // iterate remaining number:
            int iterCount = ENCODE_COUNT;
            for (int i = 0; i < iterCount; i++) {
                md.reset();
                byteData = md.digest(byteData);
            }

            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
    
    public static String hashAny(String plaintext){
    	return hashpw(plaintext, MAGIC_KEY);
    }

    private static String joinPwWithSalt(String plaintext, String salt) {
        StringBuffer sb = new StringBuffer();
        sb.append(plaintext);
        sb.append(salt);
        sb.append(MAGIC_KEY);
        return sb.toString();
    }

    public static boolean checkpw(String plaintext, String hashed, String salt) {
        return (hashed.compareTo(hashpw(plaintext, salt)) == 0);
    }

    private static String encode_base64(byte d[], int len) throws IllegalArgumentException {
        int off = 0;
        StringBuffer rs = new StringBuffer();
        int c1, c2;

        if (len <= 0 || len > d.length) throw new IllegalArgumentException("Invalid len");

        while (off < len) {
            c1 = d[off++] & 0xff;
            rs.append(base64_code[(c1 >> 2) & 0x3f]);
            c1 = (c1 & 0x03) << 4;
            if (off >= len) {
                rs.append(base64_code[c1 & 0x3f]);
                break;
            }
            c2 = d[off++] & 0xff;
            c1 |= (c2 >> 4) & 0x0f;
            rs.append(base64_code[c1 & 0x3f]);
            c1 = (c2 & 0x0f) << 2;
            if (off >= len) {
                rs.append(base64_code[c1 & 0x3f]);
                break;
            }
            c2 = d[off++] & 0xff;
            c1 |= (c2 >> 6) & 0x03;
            rs.append(base64_code[c1 & 0x3f]);
            rs.append(base64_code[c2 & 0x3f]);
        }
        return rs.toString();
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            String salt = SHAEncrypt.genSalt();
            System.out.println(salt);
            String password = "123";
            String encodePass = SHAEncrypt.hashpw(password, salt);
            System.out.println(encodePass);
            System.out.println(encodePass.length());
            System.out.println(SHAEncrypt.checkpw(password, encodePass, salt));
        }
        System.out.println("time costs: " + (System.currentTimeMillis() - time));
    }
}
