package com.mountBlue.BlogApplication.dao;

import com.mountBlue.BlogApplication.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface PostDao extends JpaRepository<Posts,Integer>{
    List<Posts> findByAuthor(String author);
    List<Posts> findByPublishedAt(Date publishedAt);
    @Query(value = "SELECT * FROM posts_tbl " +
            "WHERE post_id IN " +
            "( " +
            "SELECT post_id FROM post_tags " +
            "WHERE tags_id IN " +
            "( " +
            "SELECT tags_id " +
            "FROM tags_tbl WHERE name = :tags" +
            ")" +
            ");",nativeQuery = true)
    List<Posts> findByTagsListContaining(String tags);

    @Query(value = "select * from posts_tbl where " +
            "author @@ to_tsquery('english',:word) " +
            "or" +
            " content @@ to_tsquery('english', :word)" +
            " or " +
            "excerpt @@ to_tsquery('english',:word)" +
            " or " +
            "title @@ to_tsquery('english',:word)" +
            " or " +
            " posts_tbl.post_id IN " +
            "( " +
            "select post_id from post_tags where tags_id IN" +
            " ( " +
            "Select tags_id from tags_tbl where " +
            "name @@ to_tsquery('english',:word)" +
            ")" +
            ");",nativeQuery = true)
    Page<Posts> findPostsBySearch(String word, Pageable pageable);
}
