package com.example.cronparser.exception;

public class InvalidCronExpressionException extends CronParserException {
    public InvalidCronExpressionException(String message) {
        super(message);
    }
}