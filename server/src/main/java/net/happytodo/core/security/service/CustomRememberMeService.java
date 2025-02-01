package net.happytodo.core.security.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class CustomRememberMeService extends PersistentTokenBasedRememberMeServices {
    private final static String REMEMBER_ME_PARAMETER = "rememberMe";
    public CustomRememberMeService(String key,
                                   UserDetailsService userDetailsService,
                                   PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
        setParameter(REMEMBER_ME_PARAMETER);
    }

    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        Object rememberMe = request.getAttribute(REMEMBER_ME_PARAMETER);
        return rememberMe != null && Boolean.TRUE.equals(rememberMe);
    }
}
