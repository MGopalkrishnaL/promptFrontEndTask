package com.mountBlue.BlogApplication.controller;

import com.mountBlue.BlogApplication.Util.PostUtil;
import com.mountBlue.BlogApplication.Util.TagsUtil;
import com.mountBlue.BlogApplication.model.Comments;
import com.mountBlue.BlogApplication.model.Posts;
import com.mountBlue.BlogApplication.model.Tags;
import com.mountBlue.BlogApplication.model.User;
import com.mountBlue.BlogApplication.service.CommentService;
import com.mountBlue.BlogApplication.service.PostService;
import com.mountBlue.BlogApplication.service.TagService;
import com.mountBlue.BlogApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

@Controller
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    TagService tagService;
    @Autowired
    CommentService commentService;
    @Autowired
    PostUtil postUtil;
    @Autowired
    UserService userService;
    @Autowired
    TagsUtil tagsUtil;

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("posts") Posts post, @RequestParam("tags") String tags){
        List<Tags> tagList= tagsUtil.stringToTags(tags);
        if((post.getContent().isEmpty())){
            return "redirect:/newPost";
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((UserDetails)principal).getUsername();
        post.setAuthor(userName);
        Posts newPost = postUtil.NormalToPost(post, tagList);
        postService.savePost(newPost);
        tagService.saveManyTags(tagList);
        return "redirect:/home";
    }

    @GetMapping("/newPost")
    public String addPost(Model model){
        Posts post = new Posts();
        model.addAttribute("posts",post);
        return "new_post";
    }

    @RequestMapping(value = "/post")
    public ModelAndView viewPost(@RequestParam("id") int postId,@RequestParam(value = "userName",required = false) String name){
        User user =null;
        if(name!=null){
            user = userService.findUserByName(name);
        }
        Posts post = postService.getPostById(postId);
        ModelAndView modelAndView = new ModelAndView("postView");
        modelAndView.addObject("post",post);
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @GetMapping("/UpdatePost")
    public String updatePost(@RequestParam("id") int postId, Model model) {
        Posts post = postService.getPostById(postId);
        List<Tags> tagsList = post.getTagsList();
        String tag = tagsUtil.tagsToString(tagsList);
        model.addAttribute("posts", post);
        model.addAttribute("tags", tag);
        return "new_post";
    }

    @GetMapping({"/home","/"})
    public String getHomePage(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                              @RequestParam(value = "field",required = false)String field,
                              @RequestParam(value = "author",required = false)Set<String> author,
                              @RequestParam(value = "publishedAt",required = false)Set<String> publishedAt,
                              @RequestParam(value = "tagName",required = false)Set<String> tag,
                              @RequestParam(value = "search",required = false)String search,
                              Model model) throws Exception {
        Page<Posts> listOfPostPerPage = postService.findPostWithPaginationSearchAndSorting(pageNo,field,author,publishedAt,tag,search);
        Set<String> authorList = postService.getAllAuthors();
        Set<String> dateList = postService.getAllDates();
        Set<String> tagsList = postService.getAllTags();
        List<Posts> postsList = listOfPostPerPage.getContent();
        model.addAttribute("CurrentPage",pageNo);
        model.addAttribute("totalPages",listOfPostPerPage.getTotalPages());
        model.addAttribute("totalItems",listOfPostPerPage.getTotalElements());
        model.addAttribute("field",field);
        model.addAttribute("PostsList",postsList);
        model.addAttribute("authorList",authorList);
        model.addAttribute("tagsList",tagsList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("author",author);
        model.addAttribute("publishedAt",publishedAt);
        model.addAttribute("tagName",tag);
        return "index";
    }

    @GetMapping("/deletePost")
    public String deletePost(@RequestParam("id") int postId){
        Posts post = postService.getPostById(postId);
        List<Comments> commentsList = post.getCommentsList();
        for(Comments comment:commentsList){
            commentService.deleteCommentById(comment.getId());
        }
        List<Tags> tagsList =post.getTagsList();
        for(Tags tag:tagsList){
            tagService.deleteTagsById(tag.getId());
        }
        postService.deletePostById(postId);
        return "redirect:/home";
    }

}
