package ua.des.kino.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ua.des.kino.repository.audience.UserRepository;
import ua.des.kino.security.TokenAuthenticationEntryPoint;
import ua.des.kino.security.TokenAuthenticationFilter;
import ua.des.kino.security.TokenAuthenticationProvider;
import ua.des.kino.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(tokenAuthenticationProvider(auth.getDefaultUserDetailsService()));
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService);
//                .passwordEncoder(passwordEncoder());
    }

    //    TODO define correct mapping
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String header = "X-Auth-Token";
        boolean ignoreFault = false;
        http
                .addFilterBefore
                        (new TokenAuthenticationFilter(authenticationManager(), tokenAuthenticationEntryPoint(),
                                header, ignoreFault), BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
                .antMatchers(HttpMethod.GET, "/configuration/ui").permitAll()
                .antMatchers(HttpMethod.GET, "/configuration/security").permitAll()
                .antMatchers(HttpMethod.GET, "/webjars/**").permitAll()
                .antMatchers("/customer/**").hasRole("USER")
                .antMatchers("/auth/login", "/auth/registration").hasRole("ANONYMOUS")
                .antMatchers("/auth/logout").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/film/**").hasAnyRole("ANONYMOUS", "USER")

                .antMatchers(HttpMethod.GET, "/film/current").hasRole("ANONYMOUS")

                .antMatchers(HttpMethod.GET, "/cinema/**").hasAnyRole("ANONYMOUS", "USER")
                .antMatchers("/news/").hasAnyRole("ANONYMOUS", "USER")
                .antMatchers(HttpMethod.GET,
                        "/news/",
                        "/news/{id}",
                        "/news/promo").hasAnyRole("ANONYMOUS", "USER")
                .antMatchers(HttpMethod.GET,
                        "/room/{id}",
                        "/room/{cinema}").hasAnyRole("ANONYMOUS", "USER")
                .anyRequest().hasRole("ADMIN")
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .cors().disable();
    }

    @Bean
    public AuthenticationEntryPoint tokenAuthenticationEntryPoint() {
        return new TokenAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationProvider tokenAuthenticationProvider(UserDetailsService userDetailsService) {
        return new TokenAuthenticationProvider(userDetailsService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }
}