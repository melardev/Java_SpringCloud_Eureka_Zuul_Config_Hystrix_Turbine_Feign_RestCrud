package com.melardev.spring.proxy.api;


import com.melardev.spring.proxy.models.Todo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@FeignClient(name = "todo-service-feign-client", url = "http://localhost:8081", path = "/", fallback = FallbackTodoFeignClient.class)
public interface TodoFeignClient {

    // We can return ResponseEntity<List<Todo>>
    // or Todo, or anything that can be serialized/deserialized
    // But I almost always prefer to keep it simple and return ResponseEntity<String> why?
    // because that way the proxy service does not have to know anything about the remote objects
    // nor about the serialization, for example:
    // if the targeted micro service returns a json with snake case: created_at instead of createdAt
    // you MUST make sure your Jackson ObjectMapper also deals with snake case otherwise you would get
    // a null on createdAt field, anyways, ResponseEntity<String> to make our proxy loosely coupled to the
    // targeted micro service(todo-service)
    @GetMapping
    ResponseEntity<Iterable<Todo>> index();

    @GetMapping("{id}")
    ResponseEntity<String> getById(@PathVariable("id") Long id);

    @GetMapping("/pending")
    ResponseEntity<String> getNotCompletedTodos();

    @GetMapping("/completed")
    ResponseEntity<String> getCompletedTodos();

    @PostMapping
    ResponseEntity<String> create(@Valid @RequestBody Todo todo);

    @PutMapping("/{id}")
    ResponseEntity<String> update(@PathVariable("id") Long id,
                                  @RequestBody Todo todoInput);

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable("id") Long id);

    @DeleteMapping
    ResponseEntity<String> deleteAll();

    @GetMapping(value = "/after/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getByDateAfter(@PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date);

    @GetMapping(value = "/before/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getByDateBefore(@PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date);

}
