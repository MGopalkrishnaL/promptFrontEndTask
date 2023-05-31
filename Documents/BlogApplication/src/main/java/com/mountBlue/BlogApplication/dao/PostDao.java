package com.mountBlue.BlogApplication.dao;

import com.mountBlue.BlogApplication.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao extends JpaRepository<Posts,Integer>{
}
