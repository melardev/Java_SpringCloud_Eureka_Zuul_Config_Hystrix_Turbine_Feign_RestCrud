package com.melardev.cloud.rest.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("trigger_error")
public class ErrorController {

    @HystrixCommand(fallbackMethod = "onError")
    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getError() {
        // trigger an error so hystrix can deal with it
        throw new RuntimeException("Not that bad");
    }

    public ResponseEntity<HashMap<String, Object>> onError() {
        HashMap<String, Object> res = new HashMap<>();
        res.put("success", false);
        res.put("full_messages", new String[]{"[Rest Todo] Something went wrong"});
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
