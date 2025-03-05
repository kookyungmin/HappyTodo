package net.happytodo.core.security.service;

import net.happytodo.core.security.dto.User;

import java.util.Optional;

public interface SecurityService {
    Optional<User.UserAccount> getLoginUser();

    User.UserAccount loadUserByGoogleId(String googleId);

    User.UserAccount joinUser(User.UserAccount build);

    User.UserAccount loadUserByKakaoId(String kakaoId);
}
