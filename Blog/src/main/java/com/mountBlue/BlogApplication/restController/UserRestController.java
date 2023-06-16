package com.mountBlue.BlogApplication.restController;

import com.mountBlue.BlogApplication.dto.AuthorRequest;
import com.mountBlue.BlogApplication.dto.UserDto;
import com.mountBlue.BlogApplication.model.User;
import com.mountBlue.BlogApplication.security.JwtService;
import com.mountBlue.BlogApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blog")
public class UserRestController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;

    @PostMapping("/saveUser")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
      User user= new User();
      user.setName(userDto.getUserName());
      user.setEmail(userDto.getEmail());
      user.setPassword(userDto.getPassword());
      user.setRole("AUTHOR");
        return userService.addUser(user);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthorRequest authorRequest){
        Authentication authenticationOfUser = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authorRequest.getUsername(),authorRequest.getPassword()));
        if(authenticationOfUser.isAuthenticated()){
            String token = jwtService.generateToken(authorRequest.getUsername());
            return new ResponseEntity<>(token,HttpStatus.ACCEPTED);
        } else{
            return new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);
        }
    }
}
