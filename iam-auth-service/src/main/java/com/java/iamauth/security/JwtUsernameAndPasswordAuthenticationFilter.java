package com.java.iamauth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: Rohan Gupta
 * Date: 04-06-2021
 * Time: 15:50
 */
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //TODO Authentication Manager is to validate the user credentials.
    private final AuthenticationManager authenticationManager;

    private final JwtConfig jwtConfig;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;

        //TODO By default UsernamePasswordAuthenticationFilter looks for "/login" we overrides to "/auth".
        this.setRequiresAuthenticationRequestMatcher
                (new AntPathRequestMatcher(jwtConfig.getUri(), HttpMethod.POST.toString()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            //TODO Step 1. Get the user credentials from request
            UserCredentials userCredentials = new ObjectMapper().readValue(request.getInputStream(),
                    UserCredentials.class);

            //TODO Step 2. Create the auth object
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                    UsernamePasswordAuthenticationToken(userCredentials.getUserName(), userCredentials.getPassword()
                    , Collections.emptyList());

            //TODO Step 3. Authentication Manager will authenticate the user and after that
            //TODO use the UserDetailsServiceImpl to loadUserByUserName() to load user
            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                //TODO converting to list as we need in gateway
                .claim("authorities", authResult.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
        response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
    }

    @Data
    private static class UserCredentials {
        private String userName;
        private String password;
    }

}
