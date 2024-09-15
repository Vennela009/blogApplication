package com.mountblue.springboot.blogApplication.blogApplication.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatchException(MethodArgumentTypeMismatchException ex, Model model) {
        String errorMessage = "Invalid input: " + ex.getValue() + ". Expected a valid number.";
        model.addAttribute("message", errorMessage);
        return "error-page";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        String errorMessage = "An unexpected error occurred: " + ex.getMessage();
        model.addAttribute("message", errorMessage);
        return "error-page";
    }
}

