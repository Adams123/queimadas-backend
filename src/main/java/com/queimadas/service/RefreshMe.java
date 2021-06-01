package com.queimadas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Slf4j
public class RefreshMe {

    private final RestTemplate restTemplate;

    public RefreshMe(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "0 * * * * *")
    public void makeMyRequest(){
        final String uri = "https://queimadas-ufscar.herokuapp.com/api/test/all";
        String response = restTemplate.getForObject(URI.create(uri), String.class);
        log.info(response);
    }
}
