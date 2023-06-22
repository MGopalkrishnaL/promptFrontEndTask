package com.mountBlue.BlogApplication.restController;

import com.mountBlue.BlogApplication.Util.PostUtil;
import com.mountBlue.BlogApplication.dto.PostDto;
import com.mountBlue.BlogApplication.model.Posts;
import com.mountBlue.BlogApplication.model.Tags;
import com.mountBlue.BlogApplication.service.PostService;
import com.mountBlue.BlogApplication.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/blog")
public class PostRestController {
    @Autowired
    PostService postService;
    @Autowired
    TagService tagService;
    @Autowired
    PostUtil postUtil;

    @GetMapping("/getAllPost")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        List<PostDto> postDtoList = postService.getPostDto();
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/getPost")
    public ResponseEntity<PostDto> getPostById(@RequestParam int postId){
        PostDto postDto = postService.getPostByIdInDto(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
    @PutMapping("/update-post")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    public ResponseEntity<String> UpdatePostById(@RequestBody PostDto postDto,@RequestParam int postId) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Posts post = postService.getPostById(postId);
        if (post.getAuthor().equals(user)) {
            List<Tags> tagsList = new ArrayList<>();
            for (String tagName : postDto.getTags()) {
                Tags tag = new Tags();
                tag.setName(tagName);
                tagsList.add(tag);
            }
            tagService.saveManyTags(tagsList);
            post.setTagsList(tagsList);
            post.setContent(postDto.getContent());
            post.setExcerpt(postDto.getContent().substring(0, postDto.getContent().length() / 2));
            post.setTitle(postDto.getTitle());
            post = postUtil.NormalToPost(post, tagsList);
            postService.savePost(post);
            return new ResponseEntity<>("User Saved Successfully", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Only Post Author Able to update the post",HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/delete-post")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    public ResponseEntity<String> deletePostById(@RequestParam("postId") int postId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Posts post = postService.getPostById(postId);
        if (post.getAuthor().equals(userName)) {
            postService.deletePostById(postId);
            return new ResponseEntity<>("it is deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Only This Post Author can be able to delete",HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/save-post")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    public ResponseEntity<String> savePost(@RequestBody PostDto postDto){
            Posts post = new Posts();
            post.setContent(postDto.getContent());
            post.setExcerpt(postDto.getContent().substring(0, postDto.getContent().length() / 2));
            post.setTitle(postDto.getContent());
            List<Tags> tagsList = new ArrayList<>();
            for (String tagName : postDto.getTags()) {
                Tags tag = new Tags();
                tag.setName(tagName);
                tagsList.add(tag);
            }
            post.setTagsList(tagsList);
            post.setPublished(true);
            tagService.saveManyTags(tagsList);
            postService.savePost(post);
            return new ResponseEntity<>("The saved post is "+post, HttpStatus.CREATED);
    }

    @GetMapping({"/home","/"})
    public ResponseEntity<List<Posts>> getHomePage(@RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                              @RequestParam(value = "field",required = false)String field,
                              @RequestParam(value = "author",required = false) Set<String> author,
                              @RequestParam(value = "publishedAt",required = false)Set<String> publishedAt,
                              @RequestParam(value = "tagName",required = false)Set<String> tag,
                              @RequestParam(value = "search",required = false)String search,
                              Model model) throws Exception {
        Page<Posts> listOfPostPerPage = postService.findPostWithPaginationSearchAndSorting(pageNo,field,author,publishedAt,tag,search);
        List<Posts> postsList = listOfPostPerPage.getContent();
        return new ResponseEntity<>(postsList,HttpStatus.ACCEPTED);
    }

}
