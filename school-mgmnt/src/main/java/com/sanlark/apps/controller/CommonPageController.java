/**
 * @Author: Rajiv Kumar
 * @CreatedDate : 30-Apr-2019
 */
package com.sanlark.apps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CommonPageController {
	@GetMapping({"/", "/index", "/index/", "/index.html", 
		"/home", "/home/", "/home.html"})
    public String getHomePage() {
        log.info("Serving for home page");
        return "home";
    }
	
	@GetMapping({"/login", "/login/", "/login.html"})
    public String getLoginPage() {
        log.info("Serving for login page");
        return "login";
    }
}