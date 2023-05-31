package com.mountBlue.BlogApplication.dao;

import com.mountBlue.BlogApplication.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsDao extends JpaRepository<Tags,Integer> {
}
