package net.happytodo.core.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.happytodo.core.exception.CustomException;
import net.happytodo.core.exception.CustomExceptionCode;
import net.happytodo.core.exception.CustomExceptionHandler;
import net.happytodo.core.security.authentication.CustomAuthenticationToken;
import net.happytodo.core.security.dto.User;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static net.happytodo.core.exception.CustomExceptionCode.FAILURE_AUTHENTICATION;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      RememberMeServices rememberMeServices,
                                                      SecurityContextRepository securityContextRepository) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/security/login");
        setAuthenticationSuccessHandler(getAuthenticationSuccessHandler());
        setAuthenticationFailureHandler(getAuthenticationFailureHandler());
        setSecurityContextRepository(securityContextRepository);
        setRememberMeServices(rememberMeServices);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = request.getInputStream()) {
            User.LoginRequest loginRequest = objectMapper.readValue(inputStream, User.LoginRequest.class);
            request.setAttribute("rememberMe", loginRequest.isRememberMe());

            return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (IOException e) {
            throw new CustomException(CustomExceptionCode.NOT_SUPPORTED_CONTENT_TYPE);
        }
    }

    private AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(new Gson().toJson(((User.UserAccount) authentication.getPrincipal()).toResponse()));
        };
    }

    private AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            CustomExceptionHandler.writeSecurityExceptionResponse(response, FAILURE_AUTHENTICATION);
        };
    }

}
