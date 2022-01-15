package com.alvin.jwttokenapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("1", "A");
        testMap.put("2", "B");
        testMap.put("3", "C");
        return new ResponseEntity<>(testMap, HttpStatus.OK);
    }
}
