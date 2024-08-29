package com.example.cronparser.parser;

import com.example.cronparser.service.HourFieldParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HourFieldParserTest {
    private HourFieldParser parser;

    @BeforeEach
    void setUp() {
        parser = new HourFieldParser();
    }

    @Test
    void testParseSingleValue() {
        assertEquals("0", parser.parse("0"));
    }

    @Test
    void testParseRange() {
        assertEquals("0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23", parser.parse("0-23"));
    }

    @Test
    void testParseList() {
        assertEquals("0 12 23", parser.parse("0,12,23"));
    }

    @Test
    void testParseInvalidRange() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            parser.parse("24");
        });
        assertEquals("Value out of bounds: 24", exception.getMessage());
    }
}
