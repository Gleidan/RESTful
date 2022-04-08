package org.school21.restful.config;

import lombok.RequiredArgsConstructor;
import org.school21.restful.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private JwtFilter jwtFilter;

    @Autowired
    public void setJwtFilter(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users", "/courses",
                        "/courses/*/lessons", "/courses/*/students", "/courses/*/teachers").hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.PUT, "/users/*", "/courses/*",
                        "/courses/*/lessons/*").hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.DELETE, "/users/*", "/courses/*", "/courses/*/lessons/*",
                        "/courses/*/students/*", "/courses/*/teachers/*").hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.GET, "/users", "/courses", "/courses/*",
                        "/courses/*/lessons", "/courses/*/students", "/courses/*/teachers").hasAnyRole("ADMINISTRATOR", "TEACHER", "STUDENT")
        .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
