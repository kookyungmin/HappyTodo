package net.happytodo.core.security;

import jakarta.servlet.http.HttpServletResponse;
import net.happytodo.core.exception.CustomExceptionHandler;
import net.happytodo.core.security.authentication.CustomAuthenticationProvider;
import net.happytodo.core.security.filter.CustomUsernamePasswordAuthenticationFilter;
import net.happytodo.core.security.filter.JwtAuthenticationFilter;
import net.happytodo.core.security.filter.JwtVerifyFilter;
import net.happytodo.core.security.oauth2.CustomOauth2SuccessHandler;
import net.happytodo.core.security.service.CustomRememberMeService;
import net.happytodo.core.security.service.JwtService;
import net.happytodo.core.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.CacheableOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static net.happytodo.core.exception.CustomExceptionCode.*;

@Configuration
@EnableWebSecurity(debug = false)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final List<String> allowedRequestUrlList = List.of(
        "/api/security/login",
        "/api/security/login-user",
        "/error",
        "/api/oauth2/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtAuthenticationFilter,
                                           JwtVerifyFilter jwtVerifyFilter,
                                           CustomOauth2SuccessHandler customOauth2SuccessHandler) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers(allowedRequestUrlList.toArray(String[]::new))
                .permitAll()
                .anyRequest()
                .authenticated())
            .formLogin(formLogin -> formLogin.disable())
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(configurationSource()))
            .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(jwtVerifyFilter, BasicAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex -> ex.accessDeniedHandler(getAccessDeniedHandler())
            .authenticationEntryPoint(getAuthenticationEntryPoint()))
            .oauth2Login(oauth2 -> oauth2.authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig.baseUri("/api/oauth2/authorization"))
                    .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig.baseUri("/api/oauth2/*/callback"))
                    .successHandler(customOauth2SuccessHandler)
            )
            .logout(logout -> logout.logoutUrl("/api/security/logout")
                    .logoutSuccessHandler(getLogoutSuccessHandler())
                    .deleteCookies("rtk"));

        return http.build();
    }

    @Bean
    public CustomOauth2SuccessHandler customOauth2SuccessHandler(SecurityService securityService,
                                                                 JwtService jwtService) {
        return new CustomOauth2SuccessHandler(securityService, jwtService);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                                           JwtService jwtService) {
        return new JwtAuthenticationFilter(authenticationManager, jwtService);
    }

    @Bean
    public JwtVerifyFilter jwtVerifyFilter(AuthenticationManager authenticationManager,
                                           UserDetailsService userDetailsService,
                                           JwtService jwtService) {
        return new JwtVerifyFilter(authenticationManager, userDetailsService, jwtService);
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(List.of(authenticationProvider));
    }

    private LogoutSuccessHandler getLogoutSuccessHandler() {
        return ((request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().flush();
        });
    }

    private AuthenticationEntryPoint getAuthenticationEntryPoint() {
        //인증 X API 접근 (401 UnAuthorized)
        return (request, response, authException) -> {
            CustomExceptionHandler.writeSecurityExceptionResponse(response, USER_UNAUTHORIZED);
        };
    }

    private AccessDeniedHandler getAccessDeniedHandler() {
        //인증 O 인가 X (403 FOR_BIDDEN)
        return (request, response, accessDeniedException) -> {
            CustomExceptionHandler.writeSecurityExceptionResponse(response, USER_FORBIDDEN);
        };
    }

    private CorsConfigurationSource configurationSource() {
        return request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();

            corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
            corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173", "https://happytodo.p-e.kr"));
            corsConfiguration.setExposedHeaders(List.of("atk"));

            return corsConfiguration;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
