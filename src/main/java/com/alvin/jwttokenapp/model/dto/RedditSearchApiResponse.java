package com.alvin.jwttokenapp.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RedditSearchApiResponse {
    private String kind;
    private Data data;

    @lombok.Data
    public static class Data {
        private String modhash;
        private List<Child> children;
    }

    @lombok.Data
    public static class Child {
        private String kind;
        private ChildData data;
    }

    @lombok.Data
    public static class ChildData {
        private String title;
        private String permalink;
        private String url;
        private int ups;
    }
}
