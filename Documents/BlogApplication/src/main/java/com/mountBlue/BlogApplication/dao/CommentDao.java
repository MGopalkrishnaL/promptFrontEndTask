package com.mountBlue.BlogApplication.dao;

import com.mountBlue.BlogApplication.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends JpaRepository<Comments, Integer> {

}
