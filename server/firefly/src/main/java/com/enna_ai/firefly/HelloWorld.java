package com.enna_ai.firefly;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @GetMapping(path = "/hello")
    public String helloWorld() {
        return "Hello World!\n";
    }
}
