package com.yj.common;

import com.yj.domain.user.Member;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtil {
    public static void checkCurrentUser(String userId)  {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!userDetails.getUsername().equals(userId)){
            throw new InsufficientAuthenticationException("Unmatched User Id");
        }
    }

    public static Member getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Member.builder().id(userDetails.getUsername()).build();
    }
}
