package net.happytodo.core.security.repository;

import net.happytodo.core.security.dto.User;

public interface SecurityRepository {
    User.UserAccount findUserByEmail(String name);
}
