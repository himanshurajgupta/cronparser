package com.example.cronparser.factory;

import com.example.cronparser.TestHelper;
import com.example.cronparser.exception.InvalidCronExpressionException;
import com.example.cronparser.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CronFieldParserManagerTest {
    private CronFieldParserManager manager;

    @BeforeEach
    void setUp() {
        manager = new CronFieldParserManager();
    }

    @Test
    void testGetParserInstanceValidFields() {
        assertTrue(manager.getParserInstance("minute") instanceof MinuteFieldParser);
        assertTrue(manager.getParserInstance("hour") instanceof HourFieldParser);
        assertTrue(manager.getParserInstance("day of month") instanceof DayOfMonthFieldParser);
        assertTrue(manager.getParserInstance("month") instanceof MonthFieldParser);
        assertTrue(manager.getParserInstance("day of week") instanceof DayOfWeekFieldParser);
        assertTrue(manager.getParserInstance("command") instanceof CommandParser);
    }

    @Test
    void testGetParserInstanceInvalidField() {
        Exception exception = assertThrows(InvalidCronExpressionException.class, () -> {
            manager.getParserInstance("year");
        });
        assertEquals("Invalid field name: year", exception.getMessage());
    }

    @Test
    void testParseValidExpression() {
        // Mocking IParserService to return expected outputs for each field
        MinuteFieldParser minuteParser = mock(MinuteFieldParser.class);
        HourFieldParser hourParser = mock(HourFieldParser.class);
        when(minuteParser.parse("*/15")).thenReturn("0 15 30 45");
        when(hourParser.parse("0")).thenReturn("0");

        assertEquals(TestHelper.result, manager.parse("*/15 0 1,15 * 1-5 /usr/bin/find"));
    }

    @Test
    void testParseInvalidExpression() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            manager.parse("*/15 0 1,15 * 1-5"); // Missing command part
        });
        assertEquals("Invalid cron string. Expected 5 time fields and a command.", exception.getMessage());
    }
}
