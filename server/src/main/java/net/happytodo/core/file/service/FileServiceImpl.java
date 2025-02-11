package net.happytodo.core.file.service;

import lombok.RequiredArgsConstructor;
import net.happytodo.core.exception.CustomException;
import net.happytodo.core.exception.CustomExceptionCode;
import net.happytodo.core.file.dto.FileInfo;
import net.happytodo.core.file.repository.FileRepository;
import net.happytodo.core.security.dto.User;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    @Override
    @Transactional
    public List<FileInfo.Response> uploadFiles(String uploadDir,
                                               List<MultipartFile> multipartFiles,
                                               User.UserAccount user) {
        List<FileInfo.Response> result = new ArrayList<>();

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for(MultipartFile multipartFile : multipartFiles) {
            FileInfo.Domain fileInfo = convertIntoDomain(uploadDir, multipartFile, user.getId());
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

    @Override
    public Resource getFile(String uniqueFileName) {
        try {
            FileInfo.Domain fileInfo = fileRepository.findByUniqueFileName(uniqueFileName);
            Path path = Paths.get(fileInfo.getDirPath()).resolve(uniqueFileName);
            return new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new CustomException(CustomExceptionCode.FILE_NOT_FOUND);
        }
    }

    private FileInfo.Domain convertIntoDomain(String dirPath, MultipartFile multipartFile, int writerId) {
        return FileInfo.Domain.builder()
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
