package com.oaec.dingtalk.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 钉钉jsapi签名工具类
 */
public class DingTalkJsApiSingnature {
    /**
     * 获取jsapi签名
     * @param url
     * @param nonce
     * @param timeStamp
     * @param jsTicket
     * @return
     * @throws DingTalkEncryptException
     */
    public static String getJsApiSignature(String url,String nonce,Long timeStamp,String jsTicket) throws DingTalkEncryptException{
        String plainTex = "jsapi_ticket=" + jsTicket +"&noncestr=" + nonce +"&timestamp=" + timeStamp + "&url=" + url;
        System.out.println(plainTex);
        String signature = "";
        try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(plainTex.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            return signature;
        }catch (Exception e){
            throw new DingTalkEncryptException(DingTalkEncryptException.COMPUTE_SIGNATURE_ERROR);
        }
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


}