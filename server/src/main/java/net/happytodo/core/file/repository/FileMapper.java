package net.happytodo.core.file.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Profile;

@Mapper
@Profile({ "!jpa" })
public interface FileMapper extends FileRepository {}
