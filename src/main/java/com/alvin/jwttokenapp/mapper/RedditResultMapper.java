package com.alvin.jwttokenapp.mapper;

import com.alvin.jwttokenapp.model.dto.RedditSearchApiResponse;
import com.alvin.jwttokenapp.model.dto.RedditSearchResultResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RedditResultMapper {

    @Value("${host.reddit}")
    private String REDDIT_BASE_URL;

    public RedditSearchResultResponse toResult(RedditSearchApiResponse apiResponse) {
        if (apiResponse == null) {
            return null;
        }

        RedditSearchResultResponse dto = new RedditSearchResultResponse();
        List<RedditSearchResultResponse.Result> results = apiResponse.getData().getChildren().stream()
                .map(RedditSearchApiResponse.Child::getData)
                .map(data -> new RedditSearchResultResponse.Result(
                        data.getTitle(),
                        REDDIT_BASE_URL + data.getPermalink(),
                        data.getUrl(),
                        data.getUps()))
                .collect(Collectors.toList());
        dto.setResults(results);
        return dto;
    }
}
