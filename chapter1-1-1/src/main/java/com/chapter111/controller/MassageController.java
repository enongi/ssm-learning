package com.chapter111.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MassageController {
    @RequestMapping("/massage/go")
    public String goTest(){
        return "jsp/reach";
    }

    @RequestMapping("/massage/detail/data={username}")
    public String  goDetail(@PathVariable("username")String data, Model model){
        model.addAttribute("data",data);
        return "jsp/detail";
    }

    @RequestMapping("/massage/report-get")
    public  String reportGet(@RequestParam("begin") String begin,
                             @RequestParam("end") String end, Model model){
        model.addAttribute("begin",begin);
        model.addAttribute("end",end);
        model.addAttribute("fromType","GET");
        return "jsp/report";
    }

    @RequestMapping("/massage/report-post")
    public  String reportPost(@RequestParam("begin") String begin,
                             @RequestParam("end") String end, Model model){
        model.addAttribute("begin",begin);
        model.addAttribute("end",end);
        model.addAttribute("fromType","POST");
        return "jsp/report";
    }
}