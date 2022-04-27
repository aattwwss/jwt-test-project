package com.alvin.jwttokenapp.webClient;

import com.alvin.jwttokenapp.model.dto.RedditSearchApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class RedditClient {

    private static final long TIMEOUT = 5;
    private final WebClient webClient;

    @Autowired
    public RedditClient(@Value("${host.reddit}") String REDDIT_BASE_URL) {
        this.webClient = WebClient.builder()
                .baseUrl(REDDIT_BASE_URL)
                .filter(logRequest())
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(64 * 1024 * 1024))
                        .build())
                .build();
    }

    public Mono<RedditSearchApiResponse> search(String searchTerm) {
        return webClient.get()
                .uri("/search.json?q=url:{searchTerm}&sort=relevance&t=all", searchTerm)
                .retrieve()
                .bodyToMono(RedditSearchApiResponse.class)
                .timeout(Duration.ofSeconds(TIMEOUT));
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return next.exchange(clientRequest);
        };
    }
}
