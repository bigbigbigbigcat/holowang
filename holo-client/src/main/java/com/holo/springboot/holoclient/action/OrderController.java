package com.holo.springboot.holoclient.action;

import com.holo.springboot.holoclient.beans.Result;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class OrderController {
    private final RestTemplate restTemplate;

    public OrderController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Result someRestCall(String name) {
//        restTemplate.getForObject()
        return this.restTemplate.getForObject("/{name}/details", Result.class, name);
    }
}
