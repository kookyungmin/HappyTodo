package net.happytodo.core.security.authentication;

import net.happytodo.core.security.dto.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CustomAuthenticationProvider implements AuthenticationProvider, InitializingBean {
    private Map<String, User.LoginInfo> userDB = new ConcurrentHashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //token 발행이 일어남 (아이디 / 비밀번호 체크)
        CustomAuthenticationToken token = (CustomAuthenticationToken) authentication;

        if (userDB.containsKey(token.getName())
                && userDB.get(token.getName()).getPassword().equals(token.getCredentials())) {
            return CustomAuthenticationToken.builder()
                    .principal(User.Principal.builder()
                            .email(userDB.get(token.getName()).getEmail())
                            .name(userDB.get(token.getName()).getName())
                            .role(userDB.get(token.getName()).getRole())
                            .build())
                    .credentials(token.getCredentials())
                    .details(token.getDetails())
                    .authenticated(true)
                    .build();
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == CustomAuthenticationToken.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        userDB.put("rudals4549@naver.com", User.LoginInfo.builder()
                .email("rudals4549@naver.com")
                .password("1234")
                .role(Set.of(new SimpleGrantedAuthority("ROLE_USER")))
                .name("유저")
                .build());

        userDB.put("bean@naver.com", User.LoginInfo.builder()
                .email("bean@naver.com")
                .password("1234")
                .role(Set.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .name("어드민")
                .build());
    }
}
