package com.aisalin.numbergen.utils;

public class RandomUtils {

    public static String randomWord(int length, String letters) {
        if (StringUtils.isNullOrEmpty(letters)) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(randomChar(letters));
        }
        return sb.toString();
    }

    public static char randomChar(String letters) {
        return letters.charAt(randomInt(0, letters.length() - 1));
    }

    public static int randomInt(int from, int to) {
        return from + (int) (Math.random() * to);
    }
}
