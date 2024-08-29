package com.example.cronparser.parser;

import com.example.cronparser.service.DayOfMonthFieldParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DayOfMonthFieldParserTest {
    private DayOfMonthFieldParser parser;

    @BeforeEach
    void setUp() {
        parser = new DayOfMonthFieldParser();
        // Assuming FormatListUtil.formatList is a static method that needs no mock
    }

    @Test
    void testParseSingleValue() {
        assertEquals("1", parser.parse("1"));
    }

    @Test
    void testParseRange() {
        assertEquals("1 2 3 4 5", parser.parse("1-5"));
    }

    @Test
    void testParseList() {
        assertEquals("1 5 10", parser.parse("1,5,10"));
    }

    @Test
    void testParseInvalidRange() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            parser.parse("32");
        });
        assertEquals("Value out of bounds: 32", exception.getMessage());
    }
}
