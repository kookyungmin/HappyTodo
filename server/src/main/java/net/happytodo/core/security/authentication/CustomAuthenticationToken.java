package net.happytodo.core.security.authentication;

import lombok.*;
import net.happytodo.core.security.dto.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomAuthenticationToken implements Authentication {
    private User.Principal principal;
    private boolean authenticated;
    private String credentials;
    private String details;
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(principal)
                .map(User.Principal::getRole)
                .orElse(Set.of());
    }

    @Override
    public String getName() {
        //UsernamePasswordAuthenticationFilter 에서는 ID -> username 라고 부르기에 getName 도 ID 가 되어야한다.
        return Optional.ofNullable(principal)
                .map(User.Principal::getEmail)
                .orElse(null);
    }
}
