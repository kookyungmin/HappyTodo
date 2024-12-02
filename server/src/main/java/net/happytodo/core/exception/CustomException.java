package net.happytodo.core.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final CustomExceptionCode customExceptionCode;
    public CustomException(CustomExceptionCode customExceptionCode) {
        super(customExceptionCode.getMessage());
        this.customExceptionCode = customExceptionCode;
    }
}
