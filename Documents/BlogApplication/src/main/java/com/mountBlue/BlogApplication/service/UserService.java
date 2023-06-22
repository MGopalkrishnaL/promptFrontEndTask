package com.mountBlue.BlogApplication.service;

import com.mountBlue.BlogApplication.dao.UserDao;
import com.mountBlue.BlogApplication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public void saveUser(User user){
        if(user.getId()!=0){
            updateUser(user);
        }
        userDao.save(user);
    }

    public ResponseEntity<User> addUser(User user){
        userDao.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    private void updateUser(User user) {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setRole(user.getRole());
        userDao.save(newUser);
    }

    public User findUserByName(String author) {
        if(userDao.existsByName(author)){
            List<User> userList = userDao.findAllByName(author);
            for(User user: userList){
                if(user.getName().equals(author)){
                    return user;
                }
            }
        }
       return new User();
    }
}
