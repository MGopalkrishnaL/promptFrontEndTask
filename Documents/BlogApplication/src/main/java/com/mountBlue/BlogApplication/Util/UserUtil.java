package com.mountBlue.BlogApplication.Util;

import com.mountBlue.BlogApplication.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
    public User NormalToUserObject(User userData) {
        User user = new User();
        user.setName(userData.getName());
        user.setEmail(userData.getEmail());
        user.setPassword(userData.getPassword());
        return user;
    }
}
