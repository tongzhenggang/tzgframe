package tool;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/************************************************************
 * Explain：AES 加密解密类，同时使用了Base64编码和解码
 * Create by TongZhenggang@126.com
 ***********************************************************/
public class AES {


    /**
    * Explain：加密
    * Create by TongZhenggang@126.com
    */
    public static String encrypt(String input, String key) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new String(Base64.encodeToString(crypted, Base64.DEFAULT));
    }


    /**
    * Explain：解密
    * Create by TongZhenggang@126.com
    */
    public static String decrypt(String input, String key) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            output = cipher.doFinal(Base64.decode(input, Base64.DEFAULT));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new String(output);
    }
}
