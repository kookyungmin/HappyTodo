package net.happytodo.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum CustomExceptionCode {
    TODO_CONTENT_NOT_NULL("T001", HttpStatus.BAD_REQUEST, "Content 값은 Null일 수 없습니다."),
    NOT_SUPPORTED_CONTENT_TYPE("S001", HttpStatus.BAD_REQUEST, "지원하지 않는 Content Type입니다."),
    FAILURE_UNAUTHORIZED("S002", HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
