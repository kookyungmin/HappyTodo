package net.happytodo.core.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;
    @RequestMapping("/error")
    public ResponseEntity<ErrorResponse> handleError(WebRequest webRequest) {
        Map<String, Object> defaultErrorAttributes = errorAttributes.getErrorAttributes(
            webRequest,
            ErrorAttributeOptions.of(
                    ErrorAttributeOptions.Include.MESSAGE,
                    ErrorAttributeOptions.Include.EXCEPTION,
                    ErrorAttributeOptions.Include.STATUS,
                    ErrorAttributeOptions.Include.STACK_TRACE
            ));

        HttpStatus status = HttpStatus.valueOf((int) defaultErrorAttributes.getOrDefault("status", HttpStatus.INTERNAL_SERVER_ERROR.value()));

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode("UNCAUGHT_ERROR")
                .errorClassName((String) defaultErrorAttributes.get("exception"))
                .errorMessage((String) defaultErrorAttributes.get("message"))
                .httpStatusCode(status.value())
                .build(), status);
    }
}
