package com.example.cronparser.service;

import com.example.cronparser.utils.FormatListUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class YearFieldParser implements IParserService {
    @Override
    public String parse(String field) {
        validate(field);
        List<Integer> result = new ArrayList<>();
        String[] parts = field.split(",");
        for (String part : parts) {
            result.addAll(expandRange(part, 1987, 2025));
        }
        return FormatListUtil.formatList(result);
    }
    @Override
    public int getMinValue() {
        return 1987;
    }
    @Override
    public int getMaxValue() {
        return 2025;
    }
}
