package com.example.Social_App.Security;

import com.example.Social_App.Filter.CustomAuthentificationFilter;
import com.example.Social_App.Filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        final CustomAuthentificationFilter customAuthentificationFilter = new CustomAuthentificationFilter(authenticationManagerBean());
        customAuthentificationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().antMatchers("/api/login/**", "/api/refreshToken/**", "/Sign/**").permitAll();
        /* ******************give access to ressources**************** */
        /*http.authorizeHttpRequests().antMatchers(GET, "/Posts/**").hasAnyAuthority("MEMBER");*/
        http.authorizeHttpRequests().antMatchers(GET, "/Users/**").hasAnyAuthority("MEMBER");

        http.authorizeHttpRequests().antMatchers(POST, "/Role/**").hasAnyAuthority("ADMIN");
        /* ****************** the user must be logged in *****************
        http.authorizeHttpRequests().anyRequest().permitAll();*/
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.addFilter(customAuthentificationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
