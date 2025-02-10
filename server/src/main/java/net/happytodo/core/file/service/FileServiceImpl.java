package net.happytodo.core.file.service;

import lombok.RequiredArgsConstructor;
import net.happytodo.core.file.dto.TodoFile;
import net.happytodo.core.file.repository.FileMapper;
import net.happytodo.core.file.repository.FileRepository;
import net.happytodo.core.security.dto.User;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    @Override
    @Transactional
    public List<TodoFile.Response> uploadFiles(String uploadDir,
                                               List<MultipartFile> multipartFiles,
                                               User.UserAccount user) {
        List<TodoFile.Response> result = new ArrayList<>();

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for(MultipartFile multipartFile : multipartFiles) {
            TodoFile.Domain fileInfo = convertIntoDomain(uploadDir, multipartFile, user.getId());
            File file = new File(fileInfo.getSysFilePath());
            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //파일정보 insert
            fileRepository.persistFileInfo(fileInfo);

            result.add(fileInfo.toResponse());
        }

        return result;
    }

    private TodoFile.Domain convertIntoDomain(String dirPath, MultipartFile multipartFile, int writerId) {
        return TodoFile.Domain.builder()
                .fileName(multipartFile.getOriginalFilename())
                .sysFileName(createSysFileName(multipartFile.getOriginalFilename()))
                .size(multipartFile.getSize())
                .type(multipartFile.getContentType())
                .dirPath(dirPath)
                .writerId(writerId)
                .build();
    }

    private String createSysFileName(String fileName) {
        StringBuilder sb = new StringBuilder();

        sb.append(UUID.randomUUID());
        sb.append(".");
        sb.append(FilenameUtils.getExtension(fileName));

        return sb.toString();
    }
}
