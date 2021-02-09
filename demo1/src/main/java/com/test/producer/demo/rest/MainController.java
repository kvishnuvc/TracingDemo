package com.test.producer.demo.rest;

import com.google.common.collect.ImmutableMap;
import io.opentracing.Tracer;
import io.opentracing.contrib.spring.web.client.TracingRestTemplateInterceptor;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;

@RestController
public class MainController {

    private final Tracer tracer;

    public MainController(Tracer tracer) {

        this.tracer = tracer;
        restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new TracingRestTemplateInterceptor(tracer)));
    }

    private RestTemplate restTemplate ;

    @GetMapping(value = "/api/send")
    public String StartRequest(@RequestParam(value = "event") String event, @RequestHeader Map<String, String> headers) {
        tracer.scopeManager().activeSpan().log("Send process request for - " + event);
        return sendHttpRequest(event);
    }

    private String sendHttpRequest(String value) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8082/api/process")
                    .queryParam("event", value).toUriString();

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Bad HTTP result: " + response);
            }
            return response.getBody();
        } catch (Exception e) {
            Tags.ERROR.set(tracer.activeSpan(), true);
            tracer.activeSpan().log(ImmutableMap.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, e));
            throw new RuntimeException(e);
        }
    }
}

