package net.happytodo.core.file.controller;

import lombok.RequiredArgsConstructor;
import net.happytodo.core.exception.CustomException;
import net.happytodo.core.exception.CustomExceptionCode;
import net.happytodo.core.file.dto.TodoFile;
import net.happytodo.core.file.service.FileService;
import net.happytodo.core.security.dto.User;
import net.happytodo.core.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    @Value("${file.upload-path}")
    private String uploadDir;

    private final FileService fileService;
    private final SecurityService securityService;
    @PostMapping("")
    public ResponseEntity<List<TodoFile.Response>> uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles) {
        User.UserAccount loginUser = securityService.getLoginUser().orElseThrow(() -> new CustomException(CustomExceptionCode.USER_UNAUTHORIZED));
        List<TodoFile.Response> fileList = fileService.uploadFiles(uploadDir, multipartFiles, loginUser);
        return ResponseEntity.ok(fileList);
    }
}
