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
                validateRange(part, minValue, maxValue);
            } else if (part.contains("/")) {
                validateStep(part, minValue, maxValue);
            } else {
                validateNumber(part, minValue, maxValue);
            }
        }
    }
    // Validate that a range is numeric and within bounds
    private static void validateRange(String range, int minValue, int maxValue) throws InvalidCronExpressionException {
        if (!range.matches("^\\d+-\\d+$")) { // Valid: 5-10, 0-59, 1-2 , Invalid: 5-, -10, 5--10, abc-def
            throw new InvalidCronExpressionException("Invalid range format: " + range);
        }
        String[] numbers = range.split("-");
        int start = Integer.parseInt(numbers[0]);
        int end = Integer.parseInt(numbers[1]);
        if (start > end || start < minValue || end > maxValue) {
            throw new InvalidCronExpressionException("Range out of bounds: " + range);
        }
    }

    // Validate that a step is numeric and within bounds
    private static void validateStep(String step, int minValue, int maxValue) throws InvalidCronExpressionException {
        if (!step.matches("^(\\d+|\\*)/\\d+$")) { // Valid: "*/5", "10/2", or "2/7", Invalid:  "2/*15"
            throw new InvalidCronExpressionException("Invalid step format: " + step);
        }
        String[] parts = step.split("/");
        int stepStart = parts[0].equals("*") ? minValue : Integer.parseInt(parts[0]);
        int stepIncrement = Integer.parseInt(parts[1]);
        if (stepStart < minValue || stepStart > maxValue || stepIncrement < 1) {
            throw new InvalidCronExpressionException("Step definition out of bounds: " + step);
        }
    }

    // Validate that a single number is within bounds
    private static void validateNumber(String number, int minValue, int maxValue) throws InvalidCronExpressionException {
        if (!number.matches("^\\d+$")) {
            throw new InvalidCronExpressionException("Invalid number format: " + number);
        }
        int value = Integer.parseInt(number);
        if (value < minValue || value > maxValue) {
            throw new InvalidCronExpressionException("Value out of bounds: " + value);
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
