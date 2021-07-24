package com.java.cloudgateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: Rohan Gupta
 * Date: 02-06-2021
 * Time: 16:05
 */
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //Step 1 Get the authentication header
        String header = httpServletRequest.getHeader(jwtConfig.getHeader());

        // Step 2 validate the header and check the prefix
        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);        // If not valid, go to the next filter.
            return;
        }

        // If there is no token provided so the user is either unauthenticated or asking for ne token.

        // All secured paths that needs a token are already defined and secured in config class.
        // And If user tried to access without access token, then he won't be authenticated and an exception will be thrown.

        // Step 3 Get the token
        String token = header.replace(jwtConfig.getPrefix(), "");

        try {

            //Step 4 Validate the token
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String userName = claims.getSubject();
            if (userName != null) {
                List<String> authorities = (List<String>) claims.get("authorities");

                //Step 5 Create auth object
                //UsernamePasswordAuthenticationToken: Build in by Spring given to authenticate current or being authenticate user

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userName, null
                                , authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                //Step 6 Authenticate the User
                //Finally User is successfully authenticated now..
                SecurityContextHolder.getContext().setAuthentication(auth);

            }

        } catch (Exception e) {
            //In case of exception we need to make sure that to clear the context so that the user wont be able to authenticated
            SecurityContextHolder.clearContext();
            e.printStackTrace();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
