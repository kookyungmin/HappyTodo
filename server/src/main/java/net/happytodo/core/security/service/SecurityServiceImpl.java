package net.happytodo.core.security.service;

import lombok.RequiredArgsConstructor;
import net.happytodo.core.security.dto.User;
import net.happytodo.core.security.repository.SecurityRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService, UserDetailsService {
    private final SecurityRepository securityRepository;
    @Override
    public Optional<User.UserAccount> getLoginUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication.getPrincipal() instanceof User.UserAccount)
                .map(authentication -> (User.UserAccount) authentication.getPrincipal());
    }

    @Override
    public User.UserAccount loadUserByUsername(String username) {
        return securityRepository.findUserByEmail(username);
    }
}
