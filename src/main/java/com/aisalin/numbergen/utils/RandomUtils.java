package com.aisalin.numbergen.utils;

public class RandomUtils {

    /**
     * creates from passed letters random word with particular length
     * @param length of created word
     * @param letters contains in word
     * @return random word
     */
    public static String randomWord(int length, String letters) {
        if (StringUtils.isNullOrEmpty(letters)) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(randomChar(letters));
        }
        return sb.toString();
    }

    public static char randomChar(String letters) {
        return letters.charAt(randomIntInclude(0, letters.length() - 1));
    }

    public static int randomIntInclude(int from, int to) {
        return from + (int) (Math.random() * to - from + 1);
    }
}
