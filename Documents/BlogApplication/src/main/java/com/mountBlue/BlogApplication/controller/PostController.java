package com.mountBlue.BlogApplication.controller;

import com.mountBlue.BlogApplication.dto.PostDto;
import com.mountBlue.BlogApplication.model.Posts;
import com.mountBlue.BlogApplication.model.Tags;
import com.mountBlue.BlogApplication.service.PostService;
import com.mountBlue.BlogApplication.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    TagService tagService;

    @PostMapping("/savePost")
    public String addPost(@ModelAttribute("posts") Posts post, @ModelAttribute("tags")Tags tags){
       PostDto.stringToObject(post.toString());
       PostDto.stringToTags(tags.toString());
        return "redirect:/";
    }
    @GetMapping("/showNewPostAdd")
    public String add(Model model){
        Posts post = new Posts();
        Tags tags = new Tags();
        model.addAttribute("posts",post);
        model.addAttribute("tags",tags);
        return "new_post";
    }
    @GetMapping("/")
    public String viewHomePage(Model model){
        model.addAttribute("PostsList",postService.getAllPosts());
    return "index";
    }
    @GetMapping("/getAllPosts")
    public List<Posts> getAllPosts(){
       return postService.getAllPosts();
    }

}
