package com.cherrypicks.tcc.util;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GeneralUtil {
    private static Map<Integer, Character> yearMap;
    private static Map<Integer, String> monthMap;
    private static Map<Integer, Integer> bindModeMap;

//    private static String PADDING = "00000000000000000000000"; // total 23 digits, int value: 8388608
    private static int CONSTANT = 999999;

    static {
        yearMap = new ConcurrentHashMap<Integer, Character>();
        monthMap = new ConcurrentHashMap<Integer, String>();
        bindModeMap = new ConcurrentHashMap<Integer, Integer>();

        // 26 letters
        final int y = 2016;
        char a = 'A';
        for (int i = 0; i < 26; i++) {
            yearMap.put(y + i, (a++));
        }

        // 12 months
        for (int i = 0; i < 12; i++) {
            switch (i + 1) {
            case 11:
                monthMap.put(11, "A");
                break;
            case 12:
                monthMap.put(12, "B");
                break;
            default:
                monthMap.put(i + 1, String.valueOf(i + 1));
                break;
            }
        }

        // email -> 1, facebook -> 2
        bindModeMap.put(1, 1);
        bindModeMap.put(2, 2);
    }

    public static String generateMemberId(final long userId, final int registerType, final Date createdTime) {
        final int year = DateUtil.getYear(createdTime);
        final int month = DateUtil.getMonth(createdTime);

        final char y = yearMap.get(year);
        final String m = monthMap.get(month);
        final int bindMode = bindModeMap.get(registerType);
        String.valueOf(userId).getBytes();

        final StringBuilder displayId = new StringBuilder();
        displayId.append(y).append(m).append(bindMode).append(encryptUserId(userId));

        return displayId.toString();
    }

    public static long encryptUserId(final long userId) {
//        // 1. Convert User ID to Binary
//        final String bitStr = Long.toBinaryString(userId);
//
//        // 2. Binary will have 23 digits
//        final StringBuilder sb = new StringBuilder(PADDING);
//        sb.append(bitStr);
//        final String s = sb.toString().substring(sb.length() - PADDING.length() - 1);
//        // System.out.println("userId[" + userId + "] binary value:" + s);
//
//        // 3.1 split
//        final String[] ss = new String[6];
//        for (int i = 0; i < ss.length; i++) {
//            ss[i] = s.substring(i * 4, (i + 1) * 4);
//        }
//
//        // 3.2 swap
//        final String[] temp = new String[6];
//        temp[0] = ss[0];
//        temp[1] = ss[1];
//        temp[2] = ss[3];
//        temp[3] = ss[2];
//        temp[4] = ss[5];
//        temp[5] = ss[4];
//
//        // 4. Convert the binary to Decimal(Integer)
//        final StringBuilder convertedValue = new StringBuilder();
//        for (int i = 0; i < temp.length; i++) {
//            convertedValue.append(temp[i]);
//        }
//        // System.out.println("userId[" + userId + "] converted binary value:" + convertedValue);
//
//        final long convertedUserId = Long.valueOf(convertedValue.toString(), 2);
//        // System.out.println(convertedUserId);
//
//        // 5. Add a constant: 999999
//        final long encryptedUserId = convertedUserId + CONSTANT;
//
//        return encryptedUserId;
        return userId + CONSTANT;
    }

    public static long dencryptUserId(final long encryptedUserId) {
//        // 5.
//        final long convertedUserId = encryptedUserId - CONSTANT;
//
//        // 2
//        final String convertedUserIdBitStr = Long.toBinaryString(convertedUserId);
//        final StringBuilder sb = new StringBuilder(PADDING);
//        sb.append(convertedUserIdBitStr);
//        final String paddingConvertedUserIdBitStr = sb.toString().substring(sb.length() - PADDING.length() - 1);
//
//        // 4.
//        final String[] temp = new String[6];
//        for (int i = 0; i < temp.length; i++) {
//            temp[i] = paddingConvertedUserIdBitStr.substring(i * 4, (i + 1) * 4);
//        }
//
//        // 3.2
//        final String[] ss = new String[6];
//        ss[0] = temp[0];
//        ss[1] = temp[1];
//        ss[2] = temp[3];
//        ss[3] = temp[2];
//        ss[4] = temp[5];
//        ss[5] = temp[4];
//
//        // 3.1
//        final StringBuilder s = new StringBuilder();
//        for (int i = 0; i < ss.length; i++) {
//            s.append(ss[i]);
//        }
//
//        // 1
//        final long userId = Long.valueOf(s.toString(), 2);
//
//        return userId;
        return encryptedUserId - CONSTANT;
    }

    public static void main(final String[] args) {
        final long userId = 1029423;
        final long encryptUserId = GeneralUtil.encryptUserId(userId);
        System.out.println(encryptUserId);
        System.out.println(GeneralUtil.dencryptUserId(encryptUserId));
        System.out.println(GeneralUtil.generateMemberId(userId, 0, DateUtil.getCurrentDate()));
    }
}
