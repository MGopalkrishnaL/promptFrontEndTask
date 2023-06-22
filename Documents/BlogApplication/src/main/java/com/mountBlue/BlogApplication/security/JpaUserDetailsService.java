package com.mountBlue.BlogApplication.security;

import com.mountBlue.BlogApplication.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userDao
               .findByName(username)
               .map(SecurityUser::new)
               .orElseThrow(()->new UsernameNotFoundException("Username not found "+username));
    }
}
