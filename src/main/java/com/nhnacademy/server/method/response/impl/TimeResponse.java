package com.nhnacademy.server.method.response.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.nhnacademy.server.method.response.Response;

public class TimeResponse implements Response{
    
    private final static String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String getMethod() {
        return "time";
    }

    @Override
    public String execute(String value) {
        LocalDateTime now = LocalDateTime.now();

        if(StringUtils.isEmpty(value)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
            return now.format(dateTimeFormatter);
        }

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(value);
            return now.format(dateTimeFormatter);
        } catch (Exception e) {
            return now.format(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
        }
    }
}
