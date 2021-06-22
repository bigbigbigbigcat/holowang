package com.holo.springboot.holoclient.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAEncrypt {
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥

    public static String OUT_PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOhCAawnbn5qQDYOaWghQDAnZH" +
            "yIirN/v61DvNE2jVeUYo6zyNSySl0C9yEa+IhVgRD49pNttGenAbGO3RaNDJw1LX" +
            "lsvxfr49gtTHcdHq9+t/quz3QiAAjbhQulZCYQ+RcR6QpuW0wyXoMMi9aD4Tjyl/" +
            "iooBimoY0i11qW4rUQIDAQAB";

    public static String OUT_PRIVATEKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAM6EIBrCdufmpANg" +
            "5paCFAMCdkfIiKs3+/rUO80TaNV5RijrPI1LJKXQL3IRr4iFWBEPj2k220Z6cBsY" +
            "7dFo0MnDUteWy/F+vj2C1Mdx0er363+q7PdCIACNuFC6VkJhD5FxHpCm5bTDJegw" +
            "yL1oPhOPKX+KigGKahjSLXWpbitRAgMBAAECgYAwzTpE+JVewbsabUiwYnbWKzvG" +
            "RNY/F9C8+nBTSvXoFGuG+efqP8qeQG8l1pYTfedMyM+L/oGcyww46xAv/0PvLR+Q" +
            "tMCEnFdyHf7CGj0vBg9hyBiHPn8Lix2xrvbXJyZeeJIO781BWsq7uWVBTR4FplKX" +
            "1R5rx9TqM8TeVJT6nQJBAPIOyxr40aS1JTLYKyQKUM1doQIYC80IfzAdUjFNtvYN" +
            "BSR3BHD0VQxM9OTwAtTGwSkxDL5tHVv2KxZA+4qeBYcCQQDaaUK2nkFo77a+wZ0H" +
            "/D7zH1YY6p42WxARAGR/eGrrklUKaPkphpIt9g0Y83q26udHPLs0FRMF9ZsbGi5K" +
            "Mv5nAkBH1nAczx+Y3FHv5wvWpGEaseDo+C34ab8ljm4oR8I2/+I40kG1LC5ZejVs" +
            "BHcyotIM4YvAsC14b3SWJC8DEqbdAkABsWcu+zMOmS0VdXK+KRpj0A5eyxLG4RXS" +
            "y+n7gvdxNAUsu3uS8tQXPx0C4IBHArMgPjxFPxSSq0C0iRNqx9JpAkACq+81OJAq" +
            "mj2b4LbAQV2tzgs+D8wsXR0aO79t3HJ6Cssf+rcGoDuMsHAZ4Rswh8L+qZW2hXg8" +
            "tEDg5mO6UcCe";


    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
        genKeyPair();
        //加密字符串
        String message = "df723820";
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
        String messageEn = encrypt(message, OUT_PUBLICKEY);
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt(messageEn, OUT_PRIVATEKEY);
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString);  //0表示公钥
        keyMap.put(1, privateKeyString);  //1表示私钥
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

}
