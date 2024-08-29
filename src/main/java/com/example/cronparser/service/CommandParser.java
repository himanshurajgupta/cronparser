package com.example.cronparser.service;

import org.springframework.stereotype.Service;

@Service
public class CommandParser implements IParserService {
    @Override
    public String parse(String field) {
        return field;
    }
}
