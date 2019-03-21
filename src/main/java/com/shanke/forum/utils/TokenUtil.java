package com.shanke.forum.utils;

import com.shanke.forum.entity.DeviceInfo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class TokenUtil {

    private static MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getToken(DeviceInfo deviceInfo) {
        String device = String.format("%s#*%s#*%s#*%s", deviceInfo.getIMEI(), deviceInfo.getAndroidId(), deviceInfo.getMac(), deviceInfo.getSerialnumber());
        return getMd5(device);
    }

    private static String getMd5(String device) {
        byte[] bs = new byte[0];
        try {
            bs = md5.digest(device.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bs) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }
}
