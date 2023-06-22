package com.mountBlue.BlogApplication.service;

import com.mountBlue.BlogApplication.dao.CommentDao;
import com.mountBlue.BlogApplication.dto.CommentDto;
import com.mountBlue.BlogApplication.model.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;

    public void saveComment(Comments comments) {
        commentDao.save(comments);
    }

    public Comments getCommentById(int id) {
        Comments comment = null;
        if(commentDao.findById(id).isPresent()) {
            comment= commentDao.findById(id).get();
        }
        return comment;
    }

    public ResponseEntity<String> deleteCommentById(int id){
        if(commentDao.existsById(id)){
            commentDao.deleteById(id);
            return new ResponseEntity<>("Comment is deleted "+id, HttpStatus.OK);
        }
        return new ResponseEntity<>("Comment with id is not found "+id,HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> updateComment(int commentId, CommentDto commentDto) {
        if(commentDao.existsById(commentId)) {
            Comments comments = commentDao.findById(commentId).get();
            comments.setName(commentDto.getName());
            comments.setComment(commentDto.getComment());
            comments.setEmail(commentDto.getEmail());
            commentDao.save(comments);
            return new ResponseEntity<>("Comment is updated" + commentId, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("Comment is not Found "+commentId,HttpStatus.NOT_FOUND);
        }
    }

}
