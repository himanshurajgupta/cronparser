package com.example.cronparser.utils;

import com.example.cronparser.exception.InvalidCronExpressionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class FormatListUtilTest {

    @Test
    void testFormatList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals("1 2 3 4 5", FormatListUtil.formatList(list));
    }

    @Test
    void testValidateValidInput() {
        assertDoesNotThrow(() -> FormatListUtil.validate("1,2-3,4/2", 1, 4));
    }

    @Test
    void testValidateInvalidRange() {
        Exception exception = assertThrows(InvalidCronExpressionException.class, () -> {
            FormatListUtil.validate("1-5", 1, 4);
        });
        assertTrue(exception.getMessage().contains("Range out of bounds"));
    }

    @Test
    void testValidateInvalidStep() {
        Exception exception = assertThrows(InvalidCronExpressionException.class, () -> {
            FormatListUtil.validate("1/0", 1, 10);
        });
        assertTrue(exception.getMessage().contains("Step definition out of bounds"));
    }

    @Test
    void testValidateInvalidValue() {
        Exception exception = assertThrows(InvalidCronExpressionException.class, () -> {
            FormatListUtil.validate("5", 1, 4);
        });
        assertTrue(exception.getMessage().contains("Value out of bounds"));
    }

    @Test
    void testExpandRangeWithAsterisk() {
        assertEquals(Arrays.asList(1, 2, 3, 4), FormatListUtil.expandRange("*", 1, 4));
    }

    @Test
    void testExpandRangeWithRange() {
        assertEquals(Arrays.asList(1, 2, 3), FormatListUtil.expandRange("1-3", 1, 3));
    }

    @Test
    void testExpandRangeWithStep() {
        assertEquals(Arrays.asList(2, 4, 6), FormatListUtil.expandRange("2/2", 2, 6));
    }

    @Test
    void testExpandRangeSingleValue() {
        assertEquals(Arrays.asList(3), FormatListUtil.expandRange("3", 1, 5));
    }

}
