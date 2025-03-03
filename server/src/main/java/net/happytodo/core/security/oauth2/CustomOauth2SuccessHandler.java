package net.happytodo.core.security.oauth2;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.happytodo.core.security.dto.User;
import net.happytodo.core.security.service.JwtService;
import net.happytodo.core.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static net.happytodo.core.security.constant.UserRole.USER;
import static net.happytodo.core.security.service.JwtService.BEARER;

public class CustomOauth2SuccessHandler implements AuthenticationSuccessHandler {
    @Value("${oauth2.client-host}")
    private String clientHost;

    private final SecurityService securityService;
    private final JwtService jwtService;
    public CustomOauth2SuccessHandler(SecurityService securityService,
                                      JwtService jwtService) {
        this.securityService = securityService;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        User.UserAccount user = null;

        if ("google".equals(registrationId)) {
            //1. OAuthUser sub(uniqueID) DB 에 있는지 체크
            user = securityService.loadUserByGoogleId(oAuth2User.getAttribute("sub"));
            //2. DB에 있다면? refreshToken / accessToken 발급, 없다면 join page 로 redirect ?uniqueId={}&name={}&email={} But 여기서는 바로 가입시키고 refreshToken / accessToken 발급
            if (Objects.isNull(user)) {
                user = securityService.joinUser(User.UserAccount.builder()
                        .googleId(oAuth2User.getAttribute("sub"))
                        .email(oAuth2User.getAttribute("email"))
                        .name(oAuth2User.getAttribute("name"))
                        .role(Set.of(new SimpleGrantedAuthority(USER.toString())))
                        .build());
            }
        } else {
            response.sendRedirect(clientHost + "/login");
        }

        Cookie cookie = new Cookie("rtk", jwtService.createRefreshToken(user.getEmail()));
        cookie.setMaxAge((int) jwtService.getRtkExpiredTime());
        cookie.setPath("/");
        response.addCookie(cookie);

        response.sendRedirect(String.format("%s?atk=%s", clientHost, BEARER + jwtService.createAccessToken(user.getEmail())));
    }
}
