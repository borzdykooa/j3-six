package com.borzdykooa.j3six.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResponseEntity<String> handleApplicationException(ApplicationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<String> handleValidationException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Spring validation failed");
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<String> handleUnexpectedExceptions(Throwable throwable) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder(throwable.getMessage());
        for (StackTraceElement stackTraceElement : stackTrace) {
            stringBuilder.append(stackTraceElement);
            stringBuilder.append(System.lineSeparator());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(stringBuilder.toString());
    }
}
