package com.holo.springboot.holoclient.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明: RSA加密工具类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author hedz14761@hundsun.com<br>
 * 开发时间: 2018年5月8日<br>
 */
public class RSA {

    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    public static final String WBS_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALmz4XFU8BIfxxlcW5AgUA7ijYbcleCogYREERXqHQpRKB3cGksy7T+0JqLkPN4LKV/uYG7VZaRkkFJYnyHYbf4TmVRqIzIgah2L73oS48AKHTj5wSIg11Kt7UbNyu6bdLRDJrVDWyXvxVXNoMqSWj9DRbnqeIkqxIF19/phvngNAgMBAAECgYB6VIjaQ32yMsTRbAnVM7QJWiyo/n6s89P98Wjf6s0ekr++i9O2XeHz6RTakUeXbfrfewSDfofqd2SrGj4Z6ZzqduRx0lJSFvfeZc6uZJU/w0/L20ppzpkzysUZitbHtwbAVC+qH9dNg39Ic2M80qWoJot3cDXK/V6O/U0LrUYQAQJBAOgMNp0RMTBSz2A5DC07LqkR0VAclmXSHgmv3TihDrVWBax0XkTtvHG/h8TJUL5Ukk/lQuezaKL1xI1v9ZEzkQ0CQQDM3wOfXOHhxt2pRmckO0SxJYA3oqpend8ED0bn3r27k7EoGDkofx9IS1NkHVPLnGTLyr0B+uEWQVKZqkl9xsMBAkAfsktjeQ23BBi/lAtNwl/FSGHHB6Cjl+fyestFCPrxWia8P8kAsVikUP1CrB0TYon5z98rtx375IZMq5ZnlxuhAkA2c9QXqG1VQDEsS80gQSyJ1QjWQML9sQJUpubWvEo7sA63NHEExX0ADx1UD8N069WSoQlJnG/1JVmaBDvSVWEBAkEAyQmMcjwl2rj/Uds74gBH4jyAZ4Xw/y0OCAN2xar/Ia4QoYK6ZqbkGqWL9xZRBJFYVrstzipg0Z39SWKHsB12jg==";
    public static final String ENCODE_ALGORITHM = "SHA-256";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String SIGNATURE_ALGORITHM2 = "shihaiming@#!RSA";


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

    /**
     * 生成密钥对
     *
     * @param keySize
     * @return
     */
    public static Map<String, String> createKeys(int keySize) {
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }
        // 初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        // 生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        // 得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        return keyPairMap;
    }

    /**
     * 获取公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 获取私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 使用公钥进行加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            data = Base64.encodeBase64String(data.getBytes("UTF-8"));
//			data=Base64.encodeBase64String(data.getBytes(""));
            return Base64.encodeBase64String(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 使用私钥进行解密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
//            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 使用私钥进行加密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 使用公钥进行解密
     *
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

    /**
     * 使用私钥进行签名
     *
     * @param
     * @param plain_text
     * @return
     */
    public static String sign(String plain_text) {
        // 解密由base64编码的私钥
        byte[] keyBytes = Base64.decodeBase64(WBS_PRIVATE_KEY);
        try {
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            // 取私钥对象
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(plain_text.getBytes());
            return Base64.encodeBase64String(signature.sign());
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | SignatureException e) {
            throw new RuntimeException("签名异常:" + plain_text, e);
        }
    }

    /**
     * byte[] 转16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public static void main(String arg[]) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String data = "sddxx";
        String orig = "123456789";
        String s = RSA.publicEncrypt(orig, RSA.getPublicKey(RSA.OUT_PUBLICKEY));
        System.out.println(s);
//        String res="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";
        String res = RSA.privateDecrypt(s, RSA.getPrivateKey(RSA.OUT_PRIVATEKEY));
        res = new String(Base64.decodeBase64(res.getBytes()));
//        res=RSA.privateDecrypt(s,RSA.getPrivateKey(WBS_PRIVATE_KEY));
//        res= URLDecoder.decode(res,"utf-8");
//		res=new String(Base64.decodeBase64(data.getBytes()));
        System.out.println(res);

    }
}
