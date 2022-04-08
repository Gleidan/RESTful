package org.school21.restful.controller;

import lombok.extern.slf4j.Slf4j;
import org.school21.restful.model.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ResponseEntity<ErrorDto> processException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorDto.builder().status(400).message("Bad request").build());
    }
}
