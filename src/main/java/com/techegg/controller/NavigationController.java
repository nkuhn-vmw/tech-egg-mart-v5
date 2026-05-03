package com.techegg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller that serves Thymeleaf fragments for navigation components.
 */
@Controller
@RequestMapping("/nav")
public class NavigationController {

    @GetMapping("/navbar")
    public String navbar() {
        // Returns the Thymeleaf fragment for the navbar
        return "fragments/navbar";
    }

    @GetMapping("/sidebar")
    public String sidebar() {
        // Returns the Thymeleaf fragment for the sidebar
        return "fragments/sidebar";
    }

    @GetMapping("/breadcrumb")
    public String breadcrumb() {
        // Returns the Thymeleaf fragment for the breadcrumb
        return "fragments/breadcrumb";
    }
}
