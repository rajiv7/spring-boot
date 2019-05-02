/**
 * @Author: Rajiv Kumar
 * @CreatedDate : 30-Apr-2019
 */
package com.sanlark.apps.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/rest")
public class CommonPageRestController {
	@GetMapping({"/", "/index", "/index/", "/index.html", 
		"/home", "/home/", "/home.html"})
    public ResponseEntity<String> getHomePage() {
        log.info("Serving for home page");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
	
	
}