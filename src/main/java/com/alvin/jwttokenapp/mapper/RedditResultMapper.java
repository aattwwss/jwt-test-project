package com.alvin.jwttokenapp.mapper;

import com.alvin.jwttokenapp.model.dto.RedditSearchApiResponse;
import com.alvin.jwttokenapp.model.dto.RedditSearchResultResponse;

import java.util.List;
import java.util.stream.Collectors;

public class RedditResultMapper {

    private static final String REDDIT_BASE_URL = "https://www.reddit.com";

    public RedditSearchResultResponse toResult(RedditSearchApiResponse apiResponse) {
        if (apiResponse == null) {
            return null;
        }

        RedditSearchResultResponse dto = new RedditSearchResultResponse();
        List<RedditSearchResultResponse.Result> results = apiResponse.getData().getChildren().stream()
                .map(res -> new RedditSearchResultResponse.Result(
                        res.getData().getTitle(),
                        REDDIT_BASE_URL + res.getData().getPermalink(),
                        res.getData().getUrl(),
                        res.getData().getUps(),
                        res.getData().getDowns()))
                .collect(Collectors.toList());
        dto.setResults(results);
        return dto;
    }
}
