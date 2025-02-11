package net.happytodo.core.file.service;

import net.happytodo.core.file.dto.FileInfo;
import net.happytodo.core.security.dto.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<FileInfo.Response> uploadFiles(String uploadDir, List<MultipartFile> multipartFiles, User.UserAccount user);

    Resource getFile(String uniqueFileName);
}
