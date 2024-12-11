package net.happytodo.core.security.authentication;

import lombok.*;
import net.happytodo.core.security.dto.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomAuthenticationToken implements Authentication {
    private User.Principal principal;
    private String credentials;
    private boolean authenticated;
    private String details;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(principal)
                .map(User.Principal::getRole)
                .orElse(new HashSet<>());
    }

    @Override
    public String getName() {
        return Optional.ofNullable(principal)
                .map(User.Principal::getEmail)
                .orElse(null);
    }
}
