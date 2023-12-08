package com.sandrajavaschool.OnlineStore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorExamplesController {


    @GetMapping(value = "/index")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/arithmeticException")
    public String arithmeticException() {
        Integer number = 100/0;
        return "index";
    }

    @GetMapping(value = "/numberException")
    public String numberFormatException() {
        Integer number = Integer.parseInt("10xaaa");
        return "index";
    }


}
