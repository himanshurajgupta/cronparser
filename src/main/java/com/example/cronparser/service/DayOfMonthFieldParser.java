package com.example.cronparser.service;

import com.example.cronparser.utils.FormatListUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class DayOfMonthFieldParser implements IParserService {

    @Override
    public String parse(String field) {
        validate(field);
        List<Integer> result = parsedResult(field);
        return FormatListUtil.formatList(result);
    }
    @Override
    public int getMinValue() {
        return 1;
    }
    @Override
    public int getMaxValue() {
        return 31;
    }

    public List<Integer> parsedResult(String field){
        List<Integer> result = new ArrayList<>();
        String[] parts = field.split(",");
        for (String part : parts) {
            result.addAll(expandRange(part, 1, 31));
        }
        result = result.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        return result;
    }
}
