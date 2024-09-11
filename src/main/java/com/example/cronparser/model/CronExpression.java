package com.example.cronparser.model;

import java.util.List;

public class CronExpression {
    private List<Integer> minutes;
    private List<Integer> hours;
    private List<Integer> months;
    private List<Integer> daysOfMonth;
    private List<Integer> daysOfWeek;

    public List<Integer> getMinutes() {
        return minutes;
    }

    public void setMinutes(List<Integer> minutes) {
        this.minutes = minutes;
    }

    public List<Integer> getHours() {
        return hours;
    }

    public void setHours(List<Integer> hours) {
        this.hours = hours;
    }

    public List<Integer> getMonths() {
        return months;
    }

    public void setMonths(List<Integer> months) {
        this.months = months;
    }

    public List<Integer> getDaysOfMonth() {
        return daysOfMonth;
    }

    public void setDaysOfMonth(List<Integer> dayOfMonth) {
        this.daysOfMonth = dayOfMonth;
    }

    public List<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<Integer> dayOfWeek) {
        this.daysOfWeek = dayOfWeek;
    }



}
