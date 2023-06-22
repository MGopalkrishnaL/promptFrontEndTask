package com.mountBlue.BlogApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private int id;
    private String title;
    private String content;
    private String author;
    private List<String> tags;
    private List<CommentDto> commentList;
}
