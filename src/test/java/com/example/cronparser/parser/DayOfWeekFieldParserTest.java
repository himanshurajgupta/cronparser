package com.example.cronparser.parser;

import com.example.cronparser.service.DayOfWeekFieldParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DayOfWeekFieldParserTest {
    private DayOfWeekFieldParser parser;

    @BeforeEach
    void setUp() {
        parser = new DayOfWeekFieldParser();
    }

    @Test
    void testParseSingleValue() {
        assertEquals("0", parser.parse("0"));
    }

    @Test
    void testParseRange() {
        assertEquals("0 1 2 3 4 5 6", parser.parse("0-6"));
    }

    @Test
    void testParseList() {
        assertEquals("0 5 6", parser.parse("0,5,6"));
    }

    @Test
    void testParseInvalidRange() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            parser.parse("8");
        });
        assertEquals("Value out of bounds: 8", exception.getMessage());
    }
}
