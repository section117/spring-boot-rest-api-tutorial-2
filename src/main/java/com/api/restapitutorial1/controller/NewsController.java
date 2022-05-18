package com.api.restapitutorial1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class NewsController {
    @GetMapping("/news/get")
    public Object getProtectedNews(Principal principal){
        return "This is a protected news.";
    }
}
