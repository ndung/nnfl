package io.sci.nnfl.web;

import io.sci.nnfl.model.User;
import io.sci.nnfl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BaseController {

    @Autowired
    protected UserService userService;

    private boolean isAdmin(){
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public String getUsername(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : null;
    }

    public User getUser(){
        String username = getUsername();
        return getUser(username);
    }

    public User getUser(String username){
        if (username!=null) {
            return userService.getUser(username);
        }
        return null;
    }

    protected String trimToNull(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return value.trim();
    }
}
