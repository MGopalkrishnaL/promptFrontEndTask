package com.mountBlue.BlogApplication.controller;

import com.mountBlue.BlogApplication.Util.CommentUtil;
import com.mountBlue.BlogApplication.model.Comments;
import com.mountBlue.BlogApplication.model.Posts;
import com.mountBlue.BlogApplication.service.CommentService;
import com.mountBlue.BlogApplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommentController{
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentUtil commentUtil;

    @PostMapping("/comment")
    public String saveCommentForTheComment(@ModelAttribute("Comment")Comments comments,
                                           @RequestParam("post_id")int postId,
                                           @RequestParam(value = "userName",required = false) String name){
        Posts post = postService.getPostById(postId);
        Comments comment = commentUtil.NormalToComment(comments,post);
        commentService.saveComment(comment);
        if(name!=null){
            return "redirect:/post?id="+postId+"&userName="+name;
        }
        return "redirect:/post?id="+postId;
    }

    @GetMapping("/AddComment")
    public ModelAndView addCommentToThePost(@RequestParam("post_id") int postId){
        Comments comment = new Comments();
        ModelAndView modelAndView = new ModelAndView("Comment");
        modelAndView.addObject("post_id",postId);
        modelAndView.addObject("Comment",comment);
        return modelAndView;
    }

    @PostMapping("/deleteComment")
    public String deleteCommentForThePost(@RequestParam("id") int commentId,
                                          @RequestParam("post_id") int postId,
                                          @RequestParam(value = "userName",required = false) String name){
        commentService.deleteCommentById(commentId);
        if(name!=null){
            return "redirect:/post?id="+postId+"&userName="+name;
        }
        return "redirect:/post?id="+postId;
    }

    @PostMapping("/updateComment")
    public ModelAndView updateComment(@RequestParam("id") int commentId,@RequestParam("post_id") int postId){
        Comments comment = commentService.getCommentById(commentId);
        ModelAndView modelAndView = new ModelAndView("Comment");
        modelAndView.addObject("Comment",comment);
        modelAndView.addObject("post_id",postId);
        return modelAndView;
    }
}
