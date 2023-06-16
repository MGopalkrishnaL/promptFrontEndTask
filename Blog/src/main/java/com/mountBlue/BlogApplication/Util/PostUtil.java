package com.mountBlue.BlogApplication.Util;

import com.mountBlue.BlogApplication.model.Posts;
import com.mountBlue.BlogApplication.model.Tags;
import com.mountBlue.BlogApplication.model.User;
import com.mountBlue.BlogApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PostUtil {
    @Autowired
    UserService userService;

    public Posts NormalToPost(Posts post , List<Tags> tagsList) {
        Posts newPost = new Posts();
        if(post.getAuthor()!=null){
            User user = userService.findUserByName(post.getAuthor());
            user.setRole("AUTHOR");
            userService.saveUser(user);
            newPost.setUser(user);
            newPost.setAuthor(post.getAuthor());
        }else{
            newPost.setAuthor("Gopal");
        }
        newPost.setId(post.getId());
        newPost.setTitle(post.getTitle());
        int length = post.getContent().length();
        if(length>50){
            newPost.setExcerpt(post.getContent().substring(0,49));
        }else{
            newPost.setExcerpt(post.getContent());
        }
        newPost.setContent(post.getContent());
        newPost.setTagsList(tagsList);
        newPost.setPublished(true);
        return newPost;
    }
}
