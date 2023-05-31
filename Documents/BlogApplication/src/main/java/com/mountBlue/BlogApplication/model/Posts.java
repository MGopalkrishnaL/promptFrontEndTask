package com.mountBlue.BlogApplication.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts_tbl")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String excerpt;
    private String content;
    private String author;
    private Date publishedAt;
    private boolean isPublished;
    @CurrentTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

}
