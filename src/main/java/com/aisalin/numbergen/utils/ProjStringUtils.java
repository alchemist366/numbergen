package com.aisalin.numbergen.utils;

import java.nio.CharBuffer;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ProjStringUtils {

    private ProjStringUtils(){}

    /**
     * makes next word by lexicographic order in modal field (next word has same number of letters as initial)
     * letters order is important to define next letter
     * @param word initial word
     * @param letters letters to use in word
     * @return next word
     */
    public static String nextWordModal(String word, String letters) {
        if (isEmpty(word) || isEmpty(letters)) return word;

        if (word.length() == 1) {
            return String.valueOf(nextLetter(word.charAt(0), letters));
        }
        String incSubstr = nextWordModal(word.substring(1), letters);
        if (incSubstr.charAt(0) == letters.charAt(0) && incSubstr.charAt(0) != word.charAt(1)) {
            return nextLetter(word.charAt(0), letters) + incSubstr;
        }
        return word.charAt(0) + incSubstr;
    }

    public static char nextLetter( char prev, String letters) {
        int i = letters.indexOf(prev);
        return  i == letters.length() - 1
                ? letters.charAt(0)
                : letters.charAt(i + 1);
    }

    /**
     * @param str
     * @return same string with sorted and distinct letters
     */
    public static Optional<String> sortDistinctChars(String str) {
        if (isEmpty(str)) return Optional.ofNullable(str);
        return Optional.of(CharBuffer.wrap(str)
                .chars()
                .sorted()
                .distinct()
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString());
    }

}
