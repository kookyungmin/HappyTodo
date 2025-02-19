package net.happytodo.core.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtDTO {
    private boolean isValidToken;
    private String subject;
    private String errorMessage;
}
