package com.java.iamauth.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Rohan Gupta
 * Date: 05-06-2021
 * Time: 16:12
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // hard coding the users. All passwords must be encoded.
        final List<AppUser> users = Arrays.asList(
                new AppUser(1, "Rohan", bCryptPasswordEncoder.encode("12345"), "USER"),
                new AppUser(2, "admin", bCryptPasswordEncoder.encode("12345"), "ADMIN")
        );
        for (AppUser appUser : users) {
            if (appUser.getUserName().equalsIgnoreCase(userName)) {
                // Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
                // So, we need to set it in that format,we can verify and compare roles (i.e. hasRole("ADMIN")).
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                        .commaSeparatedStringToAuthorityList("ROLE_" + appUser.getRole());
                return new User(appUser.getUserName(), appUser.getPassword(), grantedAuthorities);

            }
        }
        throw new UsernameNotFoundException("UserName " + userName + " Not Found");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class AppUser {
        private Integer id;
        private String userName;
        private String password;
        private String role;
    }
}
