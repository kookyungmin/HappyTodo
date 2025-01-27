package net.happytodo.core.security.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Profile;

@Profile({ "!jpa" })
@Mapper
public interface SecurityMapper extends SecurityRepository {
}
