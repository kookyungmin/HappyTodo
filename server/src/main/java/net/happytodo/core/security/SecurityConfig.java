package net.happytodo.core.security;

import jakarta.servlet.http.HttpServletResponse;
import net.happytodo.core.exception.CustomExceptionHandler;
import net.happytodo.core.security.authentication.CustomAuthenticationProvider;
import net.happytodo.core.security.filter.CustomUsernamePasswordAuthenticationFilter;
import net.happytodo.core.security.service.CustomRememberMeService;
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
        "/api/security/login-user"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationManager authenticationManager,
                                           RememberMeServices rememberMeServices,
                                           SecurityContextRepository securityContextRepository) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers(allowedRequestUrlList.toArray(String[]::new))
                .permitAll()
                .anyRequest()
                .authenticated())
            .formLogin(formLogin -> formLogin.disable())
            .httpBasic(httpBasic -> httpBasic.disable())
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(configurationSource()))
            .addFilterAt(new CustomUsernamePasswordAuthenticationFilter(authenticationManager,
                            rememberMeServices,
                            securityContextRepository),
                    UsernamePasswordAuthenticationFilter.class)
            .rememberMe(r -> r.rememberMeServices(rememberMeServices))
            .exceptionHandling(ex -> ex.accessDeniedHandler(getAccessDeniedHandler())
            .authenticationEntryPoint(getAuthenticationEntryPoint()))
            .logout(logout -> logout.logoutUrl("/api/security/logout")
                .logoutSuccessHandler(getLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(List.of(authenticationProvider));
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder,
//                                                         SecurityService securityService) {
//        return new CustomAuthenticationProvider(securityService, passwordEncoder);
//    }

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
            corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));

            return corsConfiguration;
        };
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RememberMeServices rememberMeServices(UserDetailsService userDetailsService,
                                                 PersistentTokenRepository tokenRepository) {
        return new CustomRememberMeService("uniqueAndSecretKey", userDetailsService, tokenRepository);
    }

    @Bean
    public PersistentTokenRepository tokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        try {
            jdbcTokenRepository.removeUserTokens("test");
        } catch (Exception e) {
            jdbcTokenRepository.setCreateTableOnStartup(true);
        }

        return jdbcTokenRepository;
    }
}
