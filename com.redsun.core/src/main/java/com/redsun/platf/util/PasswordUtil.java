package com.redsun.platf.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswordUtil {

//    static final String salt = "EPSALT";
    static final String salt="admin" ;
    private static final String password = "1234";

    /**
     * @param args
     */
    public static void main(String[] args) {
        String standardPasswordEncoder = EncryptUtil.encrypt(password);

        System.out.println("StandardPasswordEncoder string:"+EncryptUtil.encrypt(password));
        System.out.println("length:="+standardPasswordEncoder.length());

        System.out.println("StandardPasswordEncoder mathch:"+ EncryptUtil.match(password, standardPasswordEncoder)); // true

        // String salt = KeyGenerators.string().generateKey();
        // System.out.println("salt random:"+salt);
        // TextEncryptor encryptor =Encryptors.text(password,salt);
        //
        // System.out.println(encryptor.encrypt(password));
        String md5 = EncryptUtil.md5Encrypt(password, salt);
        System.out.println("md5 pass:" + md5);
        System.out.println("md5 length:" + md5.length());
        System.out.println("md5 match:"
                + EncryptUtil.md5Match(password, md5, salt));
        System.out.println("md5 match:"
                + EncryptUtil.md5Match(md5, password, salt));

        String sha = EncryptUtil.shaEncrypt(password, salt);
        System.out.println("sha pass:" + sha);
        System.out.println("sha length:" + sha.length());
        System.out.println("sha match:"
                + EncryptUtil.shaMatch(password, sha, salt));
        // System.out.println("sha match:"+EncryptUtil.shaMatch(sha, password,
        // salt));
        // DigestUtils.
    }


    /**
     * 取得MD5加密后的字符
     *
     * @param source
     * @return
     */
    public static String enCoderMD5(String source) {

        return enCoderMD5(source, salt);
    }

    /**
     * 取得MD5加密后的字符
     *
     * @param source
     * @param saltString 为空时用 salt
     * @return
     */
    public static String enCoderMD5(String source, String saltString) {
        if (saltString == null) saltString = salt;

        return EncryptUtil.md5Encrypt(source, saltString);
    }

    /**
     * 取得SHA加密后的字符
     *
     * @param source
     * @return
     */
    public static String enCoderSHA(String source) {

        return enCoderSHA(source, salt);
    }

    /**
     * 取得SHA加密后的字符
     *
     * @param source
     * @param saltString 为空时用 salt
     * @return
     */
    public static String enCoderSHA(String source, String saltString) {
        if (saltString == null) saltString = salt;

        return EncryptUtil.shaEncrypt(source, saltString);
    }
}

/**
 * 内部类
 */
class EncryptUtil {

    private static final StandardPasswordEncoder encoder = new StandardPasswordEncoder();

    private static final Md5PasswordEncoder md5Encoder = new Md5PasswordEncoder();
    private static final MessageDigestPasswordEncoder shaEncoder = new ShaPasswordEncoder(
            256);

    /**
     * 加密
     *
     * @param rawPassword
     * @return
     */
    public static String encrypt(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static String md5Encrypt(String rawPassword, String salt) {
        return md5Encoder.encodePassword(rawPassword, salt);
    }

    public static String shaEncrypt(String rawPassword, String salt) {
        return shaEncoder.encodePassword(rawPassword, salt);
    }

    /**
     * 驗證
     *
     * @param rawPassword 原來的密碼
     * @param password    加密後的密碼
     * @return
     */
    public static boolean match(String rawPassword, String password) {
        return encoder.matches(rawPassword, password);
    }

    public static boolean md5Match(String rawPassword, String password,
                                   String salt) {
        return md5Encoder.isPasswordValid(password, rawPassword, salt);
    }

    public static boolean shaMatch(String rawPassword, String password,
                                   String salt) {
        return shaEncoder.isPasswordValid(password, rawPassword, salt);
    }

}