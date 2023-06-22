package com.mountBlue.BlogApplication.service;

import com.mountBlue.BlogApplication.dao.PostDao;
import com.mountBlue.BlogApplication.dto.CommentDto;
import com.mountBlue.BlogApplication.dto.PostDto;
import com.mountBlue.BlogApplication.model.Comments;
import com.mountBlue.BlogApplication.model.Posts;
import com.mountBlue.BlogApplication.model.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PostService {
    @Autowired
    PostDao postDao;

    public void savePost(Posts post) {
        if(post.getId()!=0){
           updatePost(post);
        }else{
            postDao.save(post);
        }
    }

    public Posts  getPostById(int id) {
      Posts post = new Posts();
        if(postDao.findById(id).isPresent()){
            post = postDao.findById(id).get();
        }
        return post;
    }

    public void updatePost(Posts posts) {
        Posts post = new Posts();
        if(postDao.findById(posts.getId()).isPresent()){
            post = postDao.findById(posts.getId()).get();
            post.setId(posts.getId());
            post.setAuthor(posts.getAuthor());
            post.setExcerpt(posts.getContent().substring(0,post.getContent().length()/2));
            post.setTitle(posts.getTitle());
            post.setContent(posts.getContent());
            post.setUpdatedAt(posts.getUpdatedAt());
            post.setPublishedAt(posts.getPublishedAt());
        }
        postDao.save(post);
    }

    public List<Posts> getPostListByAuthorNames(Set<String> authorNames){
        List<Posts> postListByAuthor = new ArrayList<>();
        for(String author:authorNames){
            postListByAuthor = postDao.findByAuthor(author);
        }
        return postListByAuthor;
    }

    public List<Posts> getPostListByPublishedAt(Set<String> publishedAt) throws ParseException {
        List<Posts> postsListByPublishedAt = new ArrayList<>();
        for(String published:publishedAt) {
            Date publishedDate = convertStringToDate(published);
            postsListByPublishedAt = postDao.findByPublishedAt(publishedDate);
        }
        return postsListByPublishedAt;
    }

    public List<Posts> getPostListByTagName(Set<String> tagNames){
        List<Posts> postsListByTagsName = new ArrayList<>();
        for (String tag : tagNames) {
            postsListByTagsName = postDao.findByTagsListContaining(tag);
        }
        return postsListByTagsName;
    }

    public Set<Posts> findUsingFilters(Set<String> authorList,Set<String> publishedAtList,Set<String> tagList) throws Exception{
        List<Posts> postListByAuthor = null;
        List<Posts> postListByPublishedAt =null;
        List<Posts> postListByTag = null;
        Set<Posts> finalList = new HashSet<>();
        if(authorList != null && publishedAtList != null && tagList != null) {
            postListByAuthor = getPostListByAuthorNames(authorList);
            postListByPublishedAt= getPostListByPublishedAt(publishedAtList);
            postListByTag= getPostListByTagName(tagList);
        }else if(authorList!=null && publishedAtList!=null){
            postListByAuthor = getPostListByAuthorNames(authorList);
            postListByPublishedAt = getPostListByPublishedAt(publishedAtList);
        } else if (authorList!=null && tagList!=null) {
            postListByAuthor = getPostListByAuthorNames(authorList);
            postListByTag= getPostListByTagName(tagList);
        } else if (publishedAtList!=null && tagList!= null){
           postListByTag = getPostListByTagName(tagList);
           postListByPublishedAt = getPostListByPublishedAt(publishedAtList);
        }else{
            if(authorList!=null){
                postListByAuthor= getPostListByAuthorNames(authorList);
            }
            if(publishedAtList!=null){
                postListByPublishedAt= getPostListByPublishedAt(publishedAtList);
            }
            if(tagList!=null){
                postListByTag = getPostListByTagName(tagList);
            }
        }
        if(postListByAuthor!=null){
            finalList.addAll(postListByAuthor);
        }
        if(postListByPublishedAt!=null){
            finalList.addAll(postListByPublishedAt);
        }
        if(postListByTag!=null){
            finalList.addAll(postListByTag);
        }
        return finalList;
    }

    public Page<Posts> findUsingSearchWord(int pageNo,String word,String sort){
        Pageable pageable = PageRequest.of( pageNo-1, 10);
        Page<Posts> postList =postDao.findAll(pageable);
        if(word!=null && !word.equals(" ")){
            if(sort!=null && !sort.equals(" ")){
                pageable= PageRequest.of(pageNo-1 ,10).withSort(Sort.by(sort));
                postList = postDao.findPostsBySearch(word,pageable);
            }else{
                postList = postDao.findPostsBySearch(word, pageable);
            }
        }
        return postList;
    }

    public  Page<Posts> findPostWithPaginationSearchAndSorting(int pageNo,
                                                               String sortField,
                                                               Set<String> author,
                                                               Set<String> publishedAt,
                                                               Set<String> tag,
                                                               String searchWord) throws Exception {
        Pageable pageable = PageRequest.of(pageNo-1, 10);
        Page<Posts> postList = postDao.findAll(pageable);
        Set<Posts> filterPosts = null;
        if(searchWord!= null && !searchWord.equals(" ")){
            postList = findUsingSearchWord(pageNo,searchWord,sortField);
        }
        if(author!=null|| publishedAt!=null || tag!=null){
            filterPosts = findUsingFilters(author,publishedAt,tag);
            pageable=PageRequest.of(pageNo-1,10);
            postList = convertSetToPage(filterPosts,pageable);
        }
        if(sortField!=null){
            pageable = PageRequest.of(pageNo-1,10).withSort(Sort.by(sortField));
            postList = postDao.findAll(pageable);
        }
        return postList;
    }

    public void deletePostById(int id){
        postDao.deleteById(id);
    }

    public Page<Posts> convertSetToPage(Set<Posts> postSet, Pageable pageable) {
        List<Posts> postList = new ArrayList<>(postSet);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        int total = postList.size();
        List<Posts> sublist;
        if (postList.size() < startItem) {
            sublist = Collections.emptyList();
        }else {
            int toIndex = Math.min(startItem + pageSize, total);
            sublist = postList.subList(startItem, toIndex);
        }
        return new PageImpl<>(sublist, pageable, total);
    }

    public Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateString);
    }

    public Set<String> getAllAuthors(){
        List<Posts> postsList= postDao.findAll();
        Set<String> AuthorsList = new HashSet<>();
        for(Posts post : postsList){
            AuthorsList.add(post.getAuthor());
        }
        return AuthorsList;
    }

    public Set<String> getAllDates() {
        List<Posts> postsList= postDao.findAll();
        Set<String> DateList = new HashSet<>();
        for(Posts post: postsList){
            DateList.add(String.valueOf(post.getPublishedAt()));
        }
        return DateList;
    }

    public Set<String> getAllTags(){
        List<Posts> postList = postDao.findAll();
        Set<String> tagsList = new HashSet<>();
        for(Posts post: postList){
            for(Tags tags: post.getTagsList()){
                tagsList.add(tags.getName());
            }
        }
        return tagsList;
    }

    public List<Posts> getAllPosts(){
        return postDao.findAll();
    }

    public List<PostDto> getPostDto() {
        List<Posts> postList = postDao.findAll();
        List<PostDto> postDtoList = new ArrayList<>();
        for(Posts post:postList){
            PostDto postDto = postToPostDto(post);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    public PostDto getPostByIdInDto(int postId){
        Posts post = postDao.findById(postId).get();
        PostDto postDto = postToPostDto(post);
        return postDto;
    }

    public PostDto postToPostDto(Posts posts1){
        PostDto postDto = new PostDto();
        postDto.setId(posts1.getId());
        postDto.setTitle(posts1.getTitle());
        postDto.setContent(posts1.getContent());
        postDto.setAuthor(posts1.getAuthor());
        List<Comments> commentsList = posts1.getCommentsList();
        List<Tags> tagsList = posts1.getTagsList();
        List<CommentDto> commentDtoList = new ArrayList<>();
        List<String> tagNameList = new ArrayList<>();
        for(Comments comments: commentsList){
            CommentDto commentDto = new CommentDto();
            commentDto.setComment(comments.getName());
            commentDto.setName(comments.getName());
            commentDto.setEmail(comments.getEmail());
            commentDtoList.add(commentDto);
        }
        postDto.setCommentList(commentDtoList);
        for(Tags tags:tagsList){
            tagNameList.add(tags.getName());
        }
        postDto.setTags(tagNameList);
        return postDto;
    }

}
