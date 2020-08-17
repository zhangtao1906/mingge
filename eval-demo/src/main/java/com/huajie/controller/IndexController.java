package com.huajie.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * @author xwf
 */
@Controller
@RequestMapping
public class IndexController{

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/pie")
    public String pie() {
        return "pie";
    }

    @GetMapping(value = "/bar")
    public String bar() {
        return "bar";
    }

    @GetMapping(value = "/bar-y-category")
    public String barYCategory() {
        return "bar-y-category";
    }

    @GetMapping(value = "/line")
    public String line() {
        return "line";
    }

    @GetMapping(value = "/pie-doughnut")
    public String pieDoughnut() {
        return "pie-doughnut";
    }

    @GetMapping(value = "/area")
    public String area() {
        return "area";
    }

    // 添加显示总体进度
    @GetMapping(value = "/totalJindu")
    public String totalJindu() {
        return "totalJindu";
    }

    // 添加显示各队进度
    @GetMapping(value = "/station1")
    public String station1(HttpServletRequest request, Model model) {
        model.addAttribute("stationID", request.getParameter("stationID"));
        return "station1";
    }

}
