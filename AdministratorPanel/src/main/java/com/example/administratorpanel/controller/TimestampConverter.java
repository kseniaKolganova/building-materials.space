package com.example.administratorpanel.controller;

import org.springframework.core.convert.converter.Converter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConverter implements Converter<String, Timestamp> {
    @Override
    public Timestamp convert(String source) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(source);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
