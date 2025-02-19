package net.happytodo.core.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.happytodo.core.exception.CustomException;
import net.happytodo.core.exception.CustomExceptionCode;
import net.happytodo.core.security.dto.JwtDTO;
import net.happytodo.core.security.service.JwtService;
import net.happytodo.core.security.service.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static net.happytodo.core.security.service.JwtService.BEARER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class JwtVerifyFilter extends BasicAuthenticationFilter {
    private UserDetailsService userDetailsService;
    private JwtService jwtService;
    public JwtVerifyFilter(AuthenticationManager authenticationManager,
                           UserDetailsService userDetailsService,
                           JwtService jwtService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String atk = request.getHeader(AUTHORIZATION);
        if (StringUtils.isEmpty(atk) || !atk.startsWith(BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        atk = atk.substring(BEARER.length());
        JwtDTO atkJwtDTO = jwtService.verifyToken(atk);
        boolean isRefreshAtk = false;

        if (!atkJwtDTO.isValidToken() && Objects.nonNull(request.getCookies())) {
            String rtk = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("rtk"))
                    .map(Cookie::getValue)
                    .findAny()
                    .orElse(null);
            JwtDTO rtkJwtDTO = jwtService.verifyToken(rtk);
            if (rtkJwtDTO.isValidToken()) {
                isRefreshAtk = true;
                atkJwtDTO.setValidToken(true);
                atkJwtDTO.setSubject(rtkJwtDTO.getSubject());
            }
        }

        if (atkJwtDTO.isValidToken()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(atkJwtDTO.getSubject());
            if (Objects.nonNull(userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                if (isRefreshAtk) {
                    response.setHeader("atk", BEARER + jwtService.createAccessToken(authentication));
                }
                chain.doFilter(request, response);
                return;
            }
        }

        log.error(atkJwtDTO.getErrorMessage());
        throw new CustomException(CustomExceptionCode.JWT_NOT_VALID);
    }
}
