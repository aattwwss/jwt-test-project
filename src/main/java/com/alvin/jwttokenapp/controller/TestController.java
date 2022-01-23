package com.alvin.jwttokenapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class TestController {

    @PreAuthorize("hasAnyAuthority('/ADMIN/READ', '/APP/READ')")
    @GetMapping("/test")
    public ResponseEntity<?> test() {

        log.info(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
        Map<String, String> testMap = new HashMap<>();
        testMap.put("1", "A");
        testMap.put("2", "B");
        testMap.put("3", "C");
        return new ResponseEntity<>(testMap, HttpStatus.OK);
    }
}
