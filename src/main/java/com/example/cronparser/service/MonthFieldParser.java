package com.example.cronparser.service;

import com.example.cronparser.utils.FormatListUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MonthFieldParser implements IParserService {

    private static final Map<String, Integer> month = new HashMap<>();
    static {
        month.put("JAN", 1);
        month.put("FEB", 2);
        month.put("MAR", 3);
        month.put("APR", 4);
        month.put("MAY", 5);
        month.put("JUN", 6);
        month.put("JUL", 7);
        month.put("AUG", 8);
        month.put("SEP", 9);
        month.put("OCT", 10);
        month.put("NOV", 11);
        month.put("DEC", 12);

    }
    @Override
    public String parse(String field) {
        field = preProcessMonthsToNumbers(field);
        validate(field);
        List<Integer> result = new ArrayList<>();
        String[] parts = field.split(",");
        for (String part : parts) {
            result.addAll(expandRange(part, 1, 12));
        }
        return FormatListUtil.formatList(result);
    }

    @Override
    public int getMinValue() {
        return 1;
    }
    @Override
    public int getMaxValue() {
        return 12;
    }
    private String preProcessMonthsToNumbers(String field) {
        for(Map.Entry<String, Integer> entry: month.entrySet()) {
            field = field.replaceAll(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return field;
    }


}
