package com.example.cronparser.factory;

import com.example.cronparser.constants.CommonConstants;
import com.example.cronparser.exception.InvalidCronExpressionException;
import com.example.cronparser.model.CronExpression;
import com.example.cronparser.service.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
        if (fields.length < CommonConstants.CRON_STRING_MIN_LENGTH) {
            throw new RuntimeException("Invalid cron string. Expected 5 time fields and a command.");
        }

        StringBuilder result = new StringBuilder();
        result.append(formatField(CommonConstants.MINUTE, fields[0]));
        result.append(formatField(CommonConstants.HOUR, fields[1]));
        result.append(formatField(CommonConstants.DAY_OF_MONTH, fields[2]));
        result.append(formatField(CommonConstants.MONTH, fields[3]));
        result.append(formatField(CommonConstants.DAY_OF_WEEK, fields[4]));
        result.append(formatField(CommonConstants.COMMAND, String.join(" ", Arrays.copyOfRange(fields, 5, fields.length))));
        return result.toString();
    }

    private String formatField(String fieldName, String field) {
        String expanded = getExpandedField(fieldName, field);
        return String.format("%-14s %s\n", fieldName, expanded);
    }
    private String getExpandedField(String fieldName, String field) {
        IParserService iParserService = getParserInstance(fieldName);
        String expanded = iParserService.parse(field);
        return expanded;
    }
    public List<LocalDateTime> getNextNSchedules(LocalDateTime start, int n, String expression) {
        String[] fields = expression.split(" ");
        if (fields.length < CommonConstants.CRON_STRING_MIN_LENGTH) {
            throw new RuntimeException("Invalid cron string. Expected 5 time fields and a command.");
        }
        List<Integer> minute = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> hour = Arrays.asList(0);
        List<Integer> dayOfMonth = Arrays.asList(5,6,8,9);
        List<Integer> month = Arrays.asList(6);
        List<Integer> dayOfWeek = Arrays.asList(1,2,4,6);
        CronExpression cronExpression = new CronExpression();
        cronExpression.setMinutes(minute);
        cronExpression.setHours(hour);
        cronExpression.setDaysOfMonth(dayOfMonth);
        cronExpression.setMonths(month);
        cronExpression.setDaysOfWeek(dayOfWeek);

        List<LocalDateTime> schedules = new ArrayList<>();
        LocalDateTime current = start;
        while (schedules.size() < n) {
            if (isValidSchedule(current, cronExpression)) {
                schedules.add(current);
                System.out.println(String.valueOf(current));
            }
            current = current.plusMinutes(1); // Increment by minute
        }

        return schedules;
    }
    private boolean isValidSchedule(LocalDateTime dateTime, CronExpression cronExpression) {
        return cronExpression.getMinutes().contains(dateTime.getMinute()) &&
                cronExpression.getHours().contains(dateTime.getHour()) &&
                cronExpression.getDaysOfMonth().contains(dateTime.getDayOfMonth()) &&
                cronExpression.getMonths().contains(dateTime.getMonthValue()) &&
                cronExpression.getDaysOfWeek().contains(dateTime.getDayOfWeek().getValue());
    }
}
