package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController{
    @ExceptionHandler(Exception.class)
    public String ExceptionControll(Model model, Exception exception){
        // 데이터 보내기만
        model.addAttribute("errorMsg", exception.getMessage());
        return "error_page/article_error";
    }
}