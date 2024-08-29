package com.example.cronparser.utils;

import com.example.cronparser.exception.InvalidCronExpressionException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class FormatListUtil {
    public static String formatList(List<Integer> list) {
        StringJoiner joiner = new StringJoiner(" ");
        for (int value : list) {
            joiner.add(String.valueOf(value));
        }
        return joiner.toString();
    }
    public static void validate(String field, int minValue, int maxValue) throws InvalidCronExpressionException {
        if (field.equals("*")) return;  // '*' is always valid

        String[] parts = field.split(",");
        for (String part : parts) {
            if (part.contains("-")) {
                String[] range = part.split("-");
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);
                if (start > end || start < minValue || end > maxValue) {
                    throw new InvalidCronExpressionException("Range out of bounds: " + part);
                }
            } else if (part.contains("/")) {
                String[] stepParts = part.split("/");
                int stepStart = stepParts[0].equals("*") ? minValue : Integer.parseInt(stepParts[0]);
                int step = Integer.parseInt(stepParts[1]);
                if (stepStart < minValue || stepStart > maxValue || step < 1) {
                    throw new InvalidCronExpressionException("Step definition out of bounds: " + part);
                }
            } else {
                int value = Integer.parseInt(part);
                if (value < minValue || value > maxValue) {
                    throw new InvalidCronExpressionException("Value out of bounds: " + value);
                }
            }
        }
    }

    public static List<Integer> expandRange(String part, int min, int max) {
        List<Integer> result = new ArrayList<>();
        if (part.equals("*")) {
            for (int i = min; i <= max; i++) {
                result.add(i);
            }
        } else if (part.contains("-")) {
            String[] range = part.split("-");
            int start = Integer.parseInt(range[0]);
            int end = Integer.parseInt(range[1]);
            for (int i = start; i <= end; i++) {
                result.add(i);
            }
        } else if (part.contains("/")) {
            String[] stepParts = part.split("/");
            int stepStart = stepParts[0].equals("*") ? min : Integer.parseInt(stepParts[0]);
            int step = Integer.parseInt(stepParts[1]);
            for (int i = stepStart; i <= max; i += step) {
                result.add(i);
            }
        } else {
            result.add(Integer.parseInt(part));
        }
        return result;
    }

}
