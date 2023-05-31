package com.mountBlue.BlogApplication.service;

import com.mountBlue.BlogApplication.dao.PostDao;
import com.mountBlue.BlogApplication.model.Posts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostDao postDao;

    public List<Posts> getAllPosts() {
        List<Posts> postsList = postDao.findAll();
        return  postsList;
    }

    public void addPost(Posts post) {
        postDao.save(post);
    }

}
