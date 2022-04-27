package com.alvin.jwttokenapp.webClient;

import com.alvin.jwttokenapp.model.dto.GenderPredictionResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class GenderizeClient {

    private static final String GENDERIZE_BASE_URL = "https://api.genderize.io";
    private static final long TIMEOUT = 5;


    private final WebClient webClient;

    @Autowired
    public GenderizeClient() {
        this.webClient = WebClient.builder()
                .baseUrl(GENDERIZE_BASE_URL)
                .filter(logRequest())
                .build();
    }

    public GenderPredictionResponse getPrediction(String name) {
        return webClient.get()
                .uri("?name={name}", name)
                .retrieve()
                .onStatus(status -> status.value() == HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        response -> Mono.error(new NotFoundException("Missing 'name' parameter.")))
                .bodyToMono(GenderPredictionResponse.class)
                .timeout(Duration.ofSeconds(TIMEOUT))
                .block();
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
