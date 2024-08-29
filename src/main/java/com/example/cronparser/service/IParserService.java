package com.example.cronparser.service;

import com.example.cronparser.exception.InvalidCronExpressionException;
import com.example.cronparser.utils.FormatListUtil;

import java.util.ArrayList;
import java.util.List;

public interface IParserService {
     default List<Integer> expandRange(String part, int min, int max) {
        return FormatListUtil.expandRange(part, min, max);
    }
    default void validate(String field) throws InvalidCronExpressionException {
        FormatListUtil.validate(field, getMinValue(), getMaxValue());
    }
    default int getMinValue() {return 0; };
    default int getMaxValue() {return 0; };
    String parse(String field);
}
