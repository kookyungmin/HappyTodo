package net.happytodo.core.file.repository;

import net.happytodo.core.file.dto.FileInfo;

public interface FileRepository {
    void persistFileInfo(FileInfo.Domain fileInfo);

    FileInfo.Domain findByUniqueFileName(String uniqueFileName);
}