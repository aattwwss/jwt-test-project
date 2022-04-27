package com.alvin.jwttokenapp.controller;

import com.alvin.jwttokenapp.model.dto.GenderPredictionResponse;
import com.alvin.jwttokenapp.model.dto.RedditSearchApiResponse;
import com.alvin.jwttokenapp.webClient.GenderizeClient;
import com.alvin.jwttokenapp.webClient.RedditClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class TestController {

    GenderizeClient genderizeClient;
    RedditClient redditClient;

    TestController(GenderizeClient genderizeClient, RedditClient redditClient) {
        this.genderizeClient = genderizeClient;
        this.redditClient = redditClient;
    }

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

    @PreAuthorize("hasAnyAuthority('/ADMIN/READ', '/APP/READ')")
    @GetMapping("/predict")
    public ResponseEntity<?> getGenderPrediction(@RequestParam("name") String name) {
        Mono<GenderPredictionResponse> res = genderizeClient.getPrediction(name);
        return new ResponseEntity<>(res.block(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('/ADMIN/READ', '/APP/READ')")
    @GetMapping("/reddit/search")
    public ResponseEntity<?> redditSearch(@RequestParam("searchTerm") String searchTerm) {
        Mono<RedditSearchApiResponse> res = redditClient.search(searchTerm);
        return new ResponseEntity<>(res.block(), HttpStatus.OK);
    }
}
