package com.example.cronparser.controller;

import com.example.cronparser.factory.CronFieldParserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

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
    @GetMapping("/schedules")
    public List<LocalDateTime> getSchedules(@RequestParam String expression) {
        try{
//            return cronFieldParserManager.parse(expression);
            return cronFieldParserManager.getNextNSchedules(LocalDateTime.now(),5, expression);
        } catch(Exception e) {
            System.out.println("exception"+ e.getMessage());
            throw e;
//            return e.getMessage();
        }
    }
}
