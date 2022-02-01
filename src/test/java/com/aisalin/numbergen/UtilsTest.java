package com.aisalin.numbergen;

import com.aisalin.numbergen.utils.ProjStringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static com.aisalin.numbergen.utils.ProjStringUtils.nextWordModal;


public class UtilsTest {

    private static final String LETTERS = ProjStringUtils.sortDistinctChars("АЕТОРНУКХСЯВМ").orElse("");


    @Test
    public void nextWordTest() {
        String word = nextWordModal("ААЯЯ", LETTERS);
        String test = "АВАА";
        Assert.isTrue(word.equals(test), String.format("Strings %s and %s not equals", word, test));
        test = "АВАВ";
        word = nextWordModal(word, LETTERS);
        Assert.isTrue(word.equals(test), String.format("Strings %s and %s not equals", word, test));
        word = nextWordModal("ЯЯЯ", LETTERS);
        test = "ААА";
        Assert.isTrue(word.equals(test), String.format("Strings %s and %s not equals", word, test));
    }

    @Test
    public void randomTest() {
        int length = 3;
        String w = RandomStringUtils.random(3, LETTERS);
        Assert.notNull(w, "word is null");
        Assert.isTrue(!w.isEmpty(), "word is empty");
        Assert.isTrue(w.length() == length, String.format("diff length = %s", w.length()));
        w.chars()
                .forEach(c -> Assert.isTrue(LETTERS.indexOf(c) != -1, String.format("unknown letter %s", c)));
    }
}
