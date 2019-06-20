package com.melardev.spring.proxy.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melardev.spring.proxy.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

// This should also be a Bean
@Service
public class FallbackTodoFeignClient implements TodoFeignClient {

    @Autowired
    ObjectMapper mapper;

    @Override
    public ResponseEntity<Iterable<Todo>> index() {
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> getById(Long id) {
        return onError();
    }

    @Override
    public ResponseEntity<String> getNotCompletedTodos() {
        return onError();
    }

    @Override
    public ResponseEntity<String> getCompletedTodos() {
        return onError();
    }

    @Override
    public ResponseEntity<String> create(@Valid Todo todo) {
        return onError();
    }

    @Override
    public ResponseEntity<String> update(Long id, Todo todoInput) {
        return onError();
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return onError();
    }

    @Override
    public ResponseEntity<String> deleteAll() {
        return onError();
    }

    @Override
    public ResponseEntity<String> getByDateAfter(Date date) {
        return onError();
    }

    @Override
    public ResponseEntity<String> getByDateBefore(Date date) {
        return onError();
    }


    private ResponseEntity<String> onError() {
        HashMap<String, Object> res = new HashMap<>();
        res.put("success", false);
        res.put("full_messages", new String[]{"[Feign Client] Something went wrong"});

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        try {
            return new ResponseEntity<>(mapper.writeValueAsString(res), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("{\"success:\": false}", headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
