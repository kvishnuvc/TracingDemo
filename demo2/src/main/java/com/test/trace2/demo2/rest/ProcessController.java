package com.test.trace2.demo2.rest;


import com.test.trace2.demo2.message.ResponseDataProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProcessController {


    @Autowired
    ResponseDataProducer producer;


    @GetMapping(value = "/api/process")
    public String processRequest(@RequestParam(value = "event") String event, @RequestHeader Map<String, String> headers) {

        test(event);
        return "Processed_item_" + event;
    }

    public void test(String event)
    {
        //Send kafka Message
        String str = "Message - " + event;
        producer.sendMessage(str);
    }

}
