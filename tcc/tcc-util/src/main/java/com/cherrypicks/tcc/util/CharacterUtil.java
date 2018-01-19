package com.cherrypicks.tcc.util;

public class CharacterUtil {

    // GENERAL_PUNCTUATION 判断中文的“
    // CJK_SYMBOLS_AND_PUNCTUATIO 判断中文的，
    // HALFWIDTH_AND_FULLWIDTH_FORMS
    private static final boolean isChinese(final char c) {
        final Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static final boolean isChinese(final String strName) {
        final char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            final char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    public static void main(final String[] args) {
        System.out.println(isChinese("sdfgrg3q3512/asddf`11312342423546768809-=vf"));
        System.out.println(isChinese("test,.?!%^&*(){}[]你"));
        System.out.println(isChinese("测试"));
        System.out.println(isChinese("“测试”，。？！%……&*（）——{}【】”你土土土土cvvvvvvvvvvvv"));
    }
}
