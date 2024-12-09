package net.happytodo.core.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(Exception e,
                                                              HandlerMethod handlerMethod,
                                                              HttpServletRequest request) {
        if (e instanceof CustomException) {
            CustomException customException = (CustomException) e;
            CustomExceptionCode customExceptionCode = customException.getCustomExceptionCode();
            HttpStatus httpStatus = customExceptionCode.getHttpStatus();

            return new ResponseEntity<>(ErrorResponse.builder()
                    .errorCode(customExceptionCode.getCode())
                    .errorClassName(e.getClass().getName())
                    .errorMessage(customExceptionCode.getMessage())
                    .httpStatusCode(httpStatus.value())
                    .build(), httpStatus);
        } else {
            HttpStatus httpStatus = e instanceof HttpRequestMethodNotSupportedException ? METHOD_NOT_ALLOWED : INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(ErrorResponse.builder()
                    .errorCode("UNCAUGHT_ERROR")
                    .errorClassName(e.getClass().getName())
                    .errorMessage(e.getMessage())
                    .httpStatusCode(httpStatus.value())
                    .build(), httpStatus);
        }
    }
}
