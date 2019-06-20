package com.melardev.spring.proxy.api;

import com.melardev.spring.proxy.models.Todo;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Date;

@Component
public class FallbackTodoFeignAwareErrorClientFactory implements FallbackFactory<TodoFeignErrorDetailsAwareClient> {
    @Override
    public TodoFeignErrorDetailsAwareClient create(Throwable throwable) {
        return new TodoFeignErrorDetailsAwareClient() {
            @Override
            public ResponseEntity<String> index() {
                return onError();
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
                if (throwable.getClass() == FeignException.class) {
                    FeignException exception = (FeignException) throwable;
                    HttpStatus status = HttpStatus.resolve(exception.status());
                    if (status == null)
                        status = HttpStatus.INTERNAL_SERVER_ERROR;
                    return new ResponseEntity<>(new String(exception.content()), status);

                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"success\": false, \"full_messages\": [\"" + throwable.getMessage() + "\"]}");
                }
            }
        };
    }
}
