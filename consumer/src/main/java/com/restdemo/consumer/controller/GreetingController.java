package com.restdemo.consumer.controller;

import com.restdemo.consumer.annotation.RpcAutowired;
import com.restdemo.rpc.api.GreetingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lindzhao
 */
@Controller
public class GreetingController {

    @RpcAutowired
    private GreetingService greetingService;

    @GetMapping("/hello/{name}")
    public ResponseEntity<String> greet(@PathVariable("name") String name) {
        return ResponseEntity.ok(greetingService.greet(name));
    }

    @GetMapping("/hello/test")
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("OK");
    }
}
