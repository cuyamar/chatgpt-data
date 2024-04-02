package com.codify.chatgpt.data.types.sdk.weixin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author: Sky
 */
public class SignUtil {
    private static String token = "b8b6";

    /**
     * 校证签名
     * signature 签名
     * timestamp 时间戳
     * nonce 随机数
     * @return 布尔值
     */
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        String checkText = null;
        if(null != signature){
            //对token,timestamp,nonce进行字典排序
            String[] paramArr = {token, timestamp, nonce};
            Arrays.sort(paramArr);

            //将排序后的结果拼接成字符串
            String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                //对拼接后的字符串进行SHA-1加密
                byte[] digest = md.digest(content.toString().getBytes());
                checkText = byteToStr(digest);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        //将加密后的字符串与signature对比
        return checkText != null? checkText.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转化为16进制字符串
     */
    private static String byteToStr(byte[] byteArrays){
        String str = "";
        for(int i = 0;i < byteArrays.length;i++){
            str += byteToHexStr(byteArrays[i]);
        }
        return str;
    }


    /**
     * 将字节转化为16进制字符串
     */
    private static String byteToHexStr(byte myByte){
        char[] Digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(myByte >> 4) & 0X0F];
        tempArr[1] = Digit[myByte & 0X0F];
        String str = new String(tempArr);
        return str;
    }
}
