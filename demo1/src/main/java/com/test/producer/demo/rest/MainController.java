package com.test.producer.demo.rest;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
public class MainController {


    public MainController() {

        restTemplate = new RestTemplate();
    }

    private RestTemplate restTemplate ;

    @GetMapping(value = "/api/send")
    public String StartRequest(@RequestParam(value = "event") String event, @RequestHeader Map<String, String> headers) {
        return sendHttpRequest(event);
    }

    private String sendHttpRequest(String value) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl("http://localhost:9802/api/process")
                    .queryParam("event", value).toUriString();

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Bad HTTP result: " + response);
            }
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

