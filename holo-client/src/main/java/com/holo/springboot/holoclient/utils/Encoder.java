package com.holo.springboot.holoclient.utils;

import java.io.UnsupportedEncodingException;

public class Encoder {
    public static String encodeURL(String encrypt) {
        try {
            // 采用RFC 3986规范进行urlencode编码。
            encrypt = java.net.URLEncoder.encode(encrypt, "UTF-8")
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encrypt;
    }
}
