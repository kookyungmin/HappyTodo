package net.happytodo.core.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "에러 Response")
public class ErrorResponse {
    @Schema(description = "Custom 에러 코드")
    private String errorCode;
    @Schema(description = "에러 메시지")
    private String errorMessage;
    @Schema(description = "Http Status Code")
    private int httpStatusCode;
    @Schema(description = "Error 클래스 명")
    private String errorClassName;
}
