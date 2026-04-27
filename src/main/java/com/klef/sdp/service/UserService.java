package com.klef.sdp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService
{
    public Object getUserByLogin(String username);
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsernameAndRole(String username, String role);
    public Object getUserByLoginAndRole(String username, String role);
}