package com.example.cronparser.controller;

import com.example.cronparser.factory.CronFieldParserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CronParserController {

    @Autowired
    private CronFieldParserManager cronFieldParserManager;

    @GetMapping("/parse")
    public String parseCronExpression(@RequestParam String expression) {
        try{
            return cronFieldParserManager.parse(expression);
        } catch(Exception e) {
            return e.getMessage();
        }
    }
}
