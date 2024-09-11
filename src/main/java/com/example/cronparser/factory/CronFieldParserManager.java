package com.example.cronparser.factory;

import com.example.cronparser.constants.CommonConstants;
import com.example.cronparser.exception.InvalidCronExpressionException;
import com.example.cronparser.service.*;
import com.example.cronparser.utils.FormatListUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;


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
            case CommonConstants.YEAR:
                return new YearFieldParser();
            case CommonConstants.COMMAND:
                return new CommandParser();
            default:
                throw new InvalidCronExpressionException("Invalid field name: " + fieldName);
        }
    }
    public String parse(String expression) {
        String[] fields = expression.split(" ");
        if (fields.length < CommonConstants.CRON_STRING_MIN_LENGTH) {
            throw new RuntimeException("Invalid cron string. Expected 5 time fields and a command.");
        }

        StringBuilder result = new StringBuilder();
        result.append(formatField(CommonConstants.MINUTE, fields[0]));
        result.append(formatField(CommonConstants.HOUR, fields[1]));
        result.append(formatField(CommonConstants.DAY_OF_MONTH, fields[2]));
        result.append(formatField(CommonConstants.MONTH, fields[3]));
        result.append(formatField(CommonConstants.DAY_OF_WEEK, fields[4]));
        String fieldAt5thIndex = fields[5];
        int flagYearExist = 1;
        try {
            FormatListUtil.validate(fieldAt5thIndex, 1987, 2025);
            result.append(formatField(CommonConstants.YEAR, fields[5]));
        } catch (InvalidCronExpressionException e ){
//            e.printStackTrace();
            flagYearExist = 0;
        }
        if(flagYearExist == 1) {
            result.append(formatField(CommonConstants.COMMAND, String.join(" ", Arrays.copyOfRange(fields, 6, fields.length))));
        } else {
            result.append(formatField(CommonConstants.COMMAND, String.join(" ", Arrays.copyOfRange(fields, 5, fields.length))));
        }
        return result.toString();
    }

    private String formatField(String fieldName, String field) {
        IParserService iParserService = getParserInstance(fieldName);
        String expanded = iParserService.parse(field);
        return String.format("%-14s %s\n", fieldName, expanded);
    }
}
