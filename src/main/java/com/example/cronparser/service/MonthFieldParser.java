package com.example.cronparser.service;

import com.example.cronparser.utils.FormatListUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonthFieldParser implements IParserService {

    @Override
    public String parse(String field) {
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
}
