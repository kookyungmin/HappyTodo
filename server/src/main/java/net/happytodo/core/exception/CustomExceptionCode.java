package net.happytodo.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CustomExceptionCode {
    TODO_CONTENT_NOT_NULL("T001", HttpStatus.BAD_REQUEST, "Content 값은 Null일 수 없습니다.")
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
