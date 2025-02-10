package net.happytodo.core.file.service;

import net.happytodo.core.file.dto.TodoFile;
import net.happytodo.core.security.dto.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<TodoFile.Response> uploadFiles(String uploadDir, List<MultipartFile> multipartFiles, User.UserAccount user);
}
