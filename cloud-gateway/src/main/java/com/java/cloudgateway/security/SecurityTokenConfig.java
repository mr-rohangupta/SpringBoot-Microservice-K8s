package com.java.cloudgateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Rohan Gupta
 * Date: 02-06-2021
 * Time: 16:52
 */
@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //We need to make sure that we use stateless session, session wont be used to store user's state
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //Handling of un authorize attempts
                .exceptionHandling().authenticationEntryPoint(((httpServletRequest, httpServletResponse, e)
                -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .and()
                //Add a filter to validate every request
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
                //Authorization Requests Config
                .authorizeRequests()
                //Allow only who are using the auth service
                .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
                //Must be an admin if trying to access admin area (Authentication is required here)
                .antMatchers("/department" + "/admin/**").hasRole("ADMIN")
                //Any Other request must be authenticated
                .anyRequest().authenticated();
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }
}
