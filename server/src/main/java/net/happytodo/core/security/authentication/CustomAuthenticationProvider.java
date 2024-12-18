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
    private Map<String, User.UserAccount> userDB = new ConcurrentHashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthenticationToken token = (CustomAuthenticationToken) authentication;
        //실질적인 인증 로직 -> 인증되었으면 token 발행 or null return
        if (userDB.containsKey(token.getName())) {
            User.UserAccount account = userDB.get(token.getName());
            if (account.getPassword().equals(token.getCredentials())) {
                //인증 성공
                return CustomAuthenticationToken.builder()
                        .principal(User.Principal.builder()
                                .email(account.getEmail())
                                .name(account.getName())
                                .role(account.getRole())
                                .build())
                        .credentials(null)
                        .details(token.getDetails())
                        .authenticated(true)
                        .build();
            }
        }
        //인증 실패
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class == authentication;
    }

    @Override
    public void afterPropertiesSet() {
        userDB.put("rudals4549@naver.com", User.UserAccount.builder()
                .email("rudals4549@naver.com")
                .password("1234")
                .role(Set.of(new SimpleGrantedAuthority("ROLE_USER")))
                .name("유저")
                .build());

        userDB.put("bean@naver.com", User.UserAccount.builder()
                .email("bean@naver.com")
                .password("1234")
                .role(Set.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .name("어드민")
                .build());
    }
}
