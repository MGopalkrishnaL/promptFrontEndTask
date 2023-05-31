package com.mountBlue.BlogApplication.dto;

import com.mountBlue.BlogApplication.model.Posts;
import com.mountBlue.BlogApplication.model.Tags;
import org.json.JSONObject;

import java.sql.Date;
public class PostDto {

    public static Posts stringToObject(String postData){
        JSONObject json = new JSONObject(postData);
        Posts posts = new Posts();
        posts.setTitle(json.getString("title"));
        posts.setExcerpt(json.getString("excerpt"));
        posts.setContent(json.getString("content"));
        posts.setAuthor("Gopal");
        Date date = new Date(System.currentTimeMillis());
        posts.setPublishedAt(date);
        posts.setPublished(true);
        return posts;
    }
    public static Tags stringToTags(String tagsData){

    }
}
