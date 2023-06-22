package com.mountBlue.BlogApplication.Util;

import com.mountBlue.BlogApplication.model.Comments;
import com.mountBlue.BlogApplication.model.Posts;
import org.springframework.stereotype.Component;

@Component
public class CommentUtil {
    public Comments NormalToComment(Comments comments, Posts posts) {
        Comments comment = new Comments();
        comment.setId(comments.getId());
        comment.setName(comments.getName());
        comment.setComment(comments.getComment());
        comment.setEmail(comments.getEmail());
        comment.setPostId(posts);
        return comment;
    }
}
