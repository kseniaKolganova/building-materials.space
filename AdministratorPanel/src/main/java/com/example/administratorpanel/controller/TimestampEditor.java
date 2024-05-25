package com.example.administratorpanel.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(text);
                setValue(new Timestamp(date.getTime()));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd", e);
            }
        } else {
            setValue(null);
        }
    }

    @Override
    public String getAsText() {
        Timestamp value = (Timestamp) getValue();
        return (value != null ? value.toString() : "");
    }
}
