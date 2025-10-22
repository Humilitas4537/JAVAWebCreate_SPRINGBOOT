package com.example.demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController{
    @ExceptionHandler(Exception.class)
    public String ExpectControll(){
        return "error_page/article_error";
    }
}