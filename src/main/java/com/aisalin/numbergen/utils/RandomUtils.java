package com.aisalin.numbergen.utils;

public class RandomUtils {

    private RandomUtils(){}

    public static char randomChar(String letters) {
        return letters.charAt(randomIntInclude(0, letters.length() - 1));
    }

    public static int randomIntInclude(int from, int to) {
        return from + (int) (Math.random() * to - from + 1);
    }
}
