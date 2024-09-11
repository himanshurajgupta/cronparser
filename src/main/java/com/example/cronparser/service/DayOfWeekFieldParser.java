package com.example.cronparser.service;

import com.example.cronparser.utils.FormatListUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DayOfWeekFieldParser implements IParserService {

    private static final Map<String, Integer> dayOfWeekMap = new HashMap<>();
    static {
        dayOfWeekMap.put("SUN", 0);
        dayOfWeekMap.put("MON", 1);
        dayOfWeekMap.put("TUE", 2);
        dayOfWeekMap.put("WED", 3);
        dayOfWeekMap.put("THU", 4);
        dayOfWeekMap.put("FRI", 5);
        dayOfWeekMap.put("SAT", 6);
    }
    @Override
    public String parse(String field) {
        field = preProcessDaysToNumbers(field.toUpperCase());
        validate(field);
        List<Integer> result = new ArrayList<>();
        String[] parts = field.split(",");
        for (String part : parts) {
            result.addAll(expandRange(part, 0, 6));
        }
        return FormatListUtil.formatList(result);
    }

    @Override
    public int getMinValue() {
        return 0;
    }
    @Override
    public int getMaxValue() {
        return 6;
    }
    private String preProcessDaysToNumbers(String field) {
        for(Map.Entry<String, Integer> entry: dayOfWeekMap.entrySet()) {
            field = field.replaceAll(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return field;
    }
}
