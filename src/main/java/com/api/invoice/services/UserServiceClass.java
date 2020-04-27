package com.api.invoice.services;

import com.api.invoice.models.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserServiceClass {
    public UserDetails loadUserByUsername(String user);
    public User saveUser(User user);
}
