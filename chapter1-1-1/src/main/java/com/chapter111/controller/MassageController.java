package com.chapter111.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MassageController {
    @RequestMapping("/massage/go")
    public String goTest(){
        return "reach";
    }
}
