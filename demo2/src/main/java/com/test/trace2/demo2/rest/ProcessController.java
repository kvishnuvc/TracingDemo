package com.test.trace2.demo2.rest;


import com.test.trace2.demo2.message.ResponseDataProducer;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class ProcessController {

    private final Tracer tracer;

    @Autowired
    ResponseDataProducer producer;

    public ProcessController(Tracer tracer) {
        this.tracer = tracer;
    }

    @GetMapping(value = "/api/process")
    public String processRequest(@RequestParam(value = "event") String event, @RequestHeader Map<String, String> headers) {
        tracer.scopeManager().activeSpan().log(Collections.singletonMap("processEvent", "for item" + event));

        //Send kafka Message
        producer.sendMessage("Message - "+ event);

        return "Processed_item_" + event;
    }

}
