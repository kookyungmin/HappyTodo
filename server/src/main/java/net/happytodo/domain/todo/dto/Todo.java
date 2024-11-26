package net.happytodo.domain.todo.dto;

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
        private String statusText;
        private LocalDateTime updateDt;
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
