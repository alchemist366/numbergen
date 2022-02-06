package com.aisalin.numbergen;

import com.aisalin.numbergen.utils.ProjStringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Objects;

import static com.aisalin.numbergen.utils.ProjStringUtils.nextWordModal;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
        Assert.isTrue(Objects.equals(nextWordModal(word, ""), word), "fails whe no alphabet provided");

    }

    @Test
    public void nextWordTestWordForEmpty() {
        Assert.isTrue(Objects.equals(nextWordModal("", LETTERS), ""), "not empty returned val");
        Assert.isTrue(Objects.equals(nextWordModal(null, LETTERS), null), "not null returned val");
    }

    @Test
    public void nextWordTestWordHasLetterOutDictionary() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            nextWordModal("ААБЯ", LETTERS);
        });

        String expectedMessage = "wrong letter";
        String actualMessage = exception.getMessage();

        Assert.isTrue(actualMessage.contains(expectedMessage), "exception hasn't been thrown");

        exception = assertThrows(IllegalArgumentException.class, () -> {
            nextWordModal("-", LETTERS);
        });

        expectedMessage = "wrong letter";
        actualMessage = exception.getMessage();

        Assert.isTrue(actualMessage.contains(expectedMessage), "exception hasn't been thrown");
    }
}
