package com.alvin.jwttokenapp.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RedditSearchApiResponse {
    public String kind;
    public Data data;

    @lombok.Data

    public static class Data {
        String modhash;
        List<Child> children;
    }

    @lombok.Data
    public static class Child {
        String kind;
        ChildData data;
    }
    
    @lombok.Data
    public static class ChildData {
        String title;
        String url;
        int ups;
        int downs;
    }
}
