package net.happytodo.core.file.repository;

import net.happytodo.core.file.dto.TodoFile;

public interface FileRepository {
    void persistFileInfo(TodoFile.Domain fileInfo);
}