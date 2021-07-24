package com.java.cloudgateway.security;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by IntelliJ IDEA.
 * User: Rohan Gupta
 * Date: 02-06-2021
 * Time: 15:42
 */
@Getter
@ToString
public class JwtConfig {

    @Value("${security.jwt.uri:/auth/**}")
    private String Uri;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${security.jwt.secret:JwtSecretKey}")
    private String secret;
}
