package com.queimadas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class RefreshMe {

    private final RestTemplate restTemplate;

    //TODO remover quando deixar de usar heroku
    @Scheduled(cron = "2 * * * * *")
    public void makeMyRequest() {
        final String uri = "https://queimadas-ufscar.herokuapp.com/api/test/all";
        restTemplate.getForObject(URI.create(uri), String.class);
    }
}
