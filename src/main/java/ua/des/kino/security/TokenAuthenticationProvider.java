package ua.des.kino.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

    private final UserDetailsService userDetailsService;

    public TokenAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;

        String token = (String) tokenAuthentication.getPrincipal();
        String login = token.split(":")[0];
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);

        tokenAuthentication.setDetails(userDetails);
        tokenAuthentication.setAuthenticated(true);
        LOGGER.info(userDetails.getUsername() + " is authenticated." + authentication.toString());

        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == TokenAuthentication.class;
    }
}
