package com.mountBlue.BlogApplication.restController;

import com.mountBlue.BlogApplication.dto.CommentDto;
import com.mountBlue.BlogApplication.model.Comments;
import com.mountBlue.BlogApplication.model.Posts;
import com.mountBlue.BlogApplication.model.User;
import com.mountBlue.BlogApplication.service.CommentService;
import com.mountBlue.BlogApplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
public class CommentRestController {
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @PostMapping("/savecomment")
    public ResponseEntity<String> saveCommentForTheComment(@RequestBody CommentDto comments , @RequestParam int postId) {
        Posts post = postService.getPostById(postId);
        Comments comment = new Comments();
        comment.setComment(comments.getComment());
        comment.setName(comments.getName());
        comment.setEmail(comments.getEmail());
        comment.setPostId(post);
        commentService.saveComment(comment);
        return new ResponseEntity<>("Save the comment successfully", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/DeleteComment")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    public ResponseEntity<String> deleteCommentForThePost(@RequestParam int commentId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Comments comment = commentService.getCommentById(commentId);
        Posts post = comment.getPostId();
        User user = post.getUser();
        if (user.getName().equals(userName)) {
            return commentService.deleteCommentById(commentId);
        } else {
            return new ResponseEntity<>("This comment is not able to delete by you", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/UpdateComment")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    public ResponseEntity<String> updateComment(@RequestParam int commentId, @RequestBody CommentDto commentDto) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Comments comment = commentService.getCommentById(commentId);
        Posts post = comment.getPostId();
        User user = post.getUser();
        if (user.getName().equals(userName)) {
            return commentService.updateComment(commentId, commentDto);
        } else {
            return new ResponseEntity<>("This comment is unable to update by you", HttpStatus.UNAUTHORIZED);
        }
    }

}
