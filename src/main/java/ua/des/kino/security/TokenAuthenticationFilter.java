package ua.des.kino.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;
import ua.des.kino.model.audience.submodel.Authority;
import ua.des.kino.model.audience.submodel.Role;
import ua.des.kino.util.exception_handler.TokenAuthenticationHeaderNotFound;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenAuthenticationFilter extends GenericFilterBean {

    private final AuthenticationManager authenticationManager;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final String header;

    private final boolean ignoreFault;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager,
                                     AuthenticationEntryPoint authenticationEntryPoint,
                                     String header,
                                     boolean ignoreFault) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.header = header;
        this.ignoreFault = ignoreFault;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        try {
            String headerValue = httpServletRequest.getHeader(header);
            if (headerValue == null || headerValue.isEmpty()) {
                throw new TokenAuthenticationHeaderNotFound("Header " + header + " is not found.", null);
            }
            List<GrantedAuthority> authorities = new ArrayList<>();
            if ((headerValue.split(":")[0].equals("ANONYMOUS"))) {
                Authority authority = new Authority();
                authority.setRole(Role.ANONYMOUS);
                authorities.add(authority);
            }

            Authentication authentication = authenticationManager.authenticate
                    (new TokenAuthentication(headerValue, authorities));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (AuthenticationException authenticationException) {
            if (!ignoreFault) {
                authenticationEntryPoint.commence(httpServletRequest, httpServletResponse, authenticationException);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
