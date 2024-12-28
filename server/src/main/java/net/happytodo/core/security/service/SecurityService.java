package net.happytodo.core.security.service;

import net.happytodo.core.security.dto.User;

import java.util.Optional;

public interface SecurityService {
    Optional<User.Principal> getLoginUser();
}
