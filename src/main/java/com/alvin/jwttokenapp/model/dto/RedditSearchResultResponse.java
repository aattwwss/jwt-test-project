package com.alvin.jwttokenapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class RedditSearchResultResponse {
    private List<Result> results;

    @AllArgsConstructor
    @Data
    public static class Result {
        private String title;
        private String permalink;
        private String url;
        private int ups;
        private int downs;
    }
}
