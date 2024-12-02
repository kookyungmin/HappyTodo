package net.happytodo.domain.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

public class Todo {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Domain {
        private int id;
        private int userId; //TODO: User reference 로 변경
        private String title;
        private String content;
        private int statusCode;
        private String statusName;
        private LocalDateTime updateDt;
        private LocalDateTime createDt;

        public DomainResponse toResponse() {
            return DomainResponse.builder()
                    .id(this.id)
                    .userId(this.userId)
                    .title(this.title)
                    .content(this.content)
                    .statusCode(this.statusCode)
                    .statusName(this.statusName)
                    .updateDt(this.updateDt)
                    .createDt(this.createDt)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Todo 도메인 Request")
    public static class DomainRequest {
        @Schema(description = "도메인 생성 유저 ID")
        private int userId; //TODO: User reference 로 변경
        @Schema(description = "도메인 제목")
        private String title;
        @Schema(description = "도메인 Note")
        private String content;
        @Schema(description = "도메인 상태 ID")
        private int statusCode;

        public Domain toDomain() {
            return Todo.Domain.builder()
                    .title(this.title)
                    .content(this.content)
                    .userId(this.userId)
                    .statusCode(this.statusCode)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Todo 도메인 Response")
    public static class DomainResponse {
        @Schema(description = "도메인 ID")
        private int id;
        @Schema(description = "도메인 생성 유저 ID")
        private int userId; //TODO: User reference 로 변경
        @Schema(description = "도메인 제목")
        private String title;
        @Schema(description = "도메인 Note")
        private String content;
        @Schema(description = "도메인 상태 ID")
        private int statusCode;
        @Schema(description = "도메인 상태 설명")
        private String statusName;
        @Schema(description = "수정 일시")
        private LocalDateTime updateDt;
        @Schema(description = "생성 일시")
        private LocalDateTime createDt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Item {
        private int id;
        private int domainId;
        private String content;
        private boolean checked;
        private LocalDateTime updateDt;
        private LocalDateTime createDt;
    }
}
