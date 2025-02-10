package net.happytodo.core.file.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TodoFile {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Domain {
        private int id;
        private int writerId;
        private String fileName;
        private String dirPath;
        private String sysFileName;
        private String type;
        private long size;
        private LocalDateTime createDt;

        public String getSysFilePath() {
            StringBuilder sb = new StringBuilder();

            sb.append(dirPath);
            sb.append(File.separator);
            sb.append(sysFileName);

            return sb.toString();
        }

        public Response toResponse() {
            return Response.builder()
                    .id(id)
                    .fileName(fileName)
                    .uniqueFileName(sysFileName)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private int id;
        private String fileName;
        private String uniqueFileName;
    }
}
