package com.example.cronparser.parser;

import com.example.cronparser.service.MonthFieldParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MonthFieldParserTest {
    private MonthFieldParser parser;

    @BeforeEach
    void setUp() {
        parser = new MonthFieldParser();
    }

    @Test
    void testParseSingleValue() {
        assertEquals("1", parser.parse("1"));
    }

    @Test
    void testParseRange() {
        assertEquals("1 2 3 4 5 6 7 8 9 10 11 12",  parser.parse("1-12"));
    }

    @Test
    void testParseList() {
        assertEquals("1 6 12", parser.parse("1,6,12"));
    }

    @Test
    void testParseInvalidRange() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            parser.parse("13");
        });
        assertEquals("Value out of bounds: 13", exception.getMessage());
    }
}
