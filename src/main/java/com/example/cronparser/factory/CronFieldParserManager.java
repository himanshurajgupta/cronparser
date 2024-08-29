package com.example.cronparser.factory;

import com.example.cronparser.constants.CommonConstants;
import com.example.cronparser.exception.InvalidCronExpressionException;
import com.example.cronparser.service.*;
import org.springframework.stereotype.Service;


@Service
public class CronFieldParserManager {

    public IParserService getParserInstance(String fieldName) {
        switch (fieldName) {
            case CommonConstants.MINUTE:
                return new MinuteFieldParser();
            case CommonConstants.HOUR:
                return new HourFieldParser();
            case CommonConstants.DAY_OF_MONTH:
                return new DayOfMonthFieldParser();
            case CommonConstants.MONTH:
                return new MonthFieldParser();
            case CommonConstants.DAY_OF_WEEK:
                return new DayOfWeekFieldParser();
            case CommonConstants.COMMAND:
                return new CommandParser();
            default:
                throw new InvalidCronExpressionException("Invalid field name: " + fieldName);
        }
    }
    public String parse(String expression) {
        String[] fields = expression.split(" ");
        if (fields.length != 6) {
            throw new RuntimeException("Invalid cron string. Expected 5 time fields and a command.");
        }

        StringBuilder result = new StringBuilder();
        result.append(formatField(CommonConstants.MINUTE, fields[0]));
        result.append(formatField(CommonConstants.HOUR, fields[1]));
        result.append(formatField(CommonConstants.DAY_OF_MONTH, fields[2]));
        result.append(formatField(CommonConstants.MONTH, fields[3]));
        result.append(formatField(CommonConstants.DAY_OF_WEEK, fields[4]));
        result.append(formatField(CommonConstants.COMMAND, fields[5]));
        return result.toString();
    }

    private String formatField(String fieldName, String field) {
        IParserService iParserService = getParserInstance(fieldName);
        String expanded = iParserService.parse(field);
        return String.format("%-14s %s\n", fieldName, expanded);
    }
}
