package org.example.blogproject.domain;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "posts")
@Getter @Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String encodedTitle;
    private String tag;
    private String ment;
    private String content;
    private String author;

    @Enumerated(EnumType.STRING)
    private PostPopular postPopular;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    private LocalDateTime releaseDate = LocalDateTime.now();
    private String thumbnailImage; // 파일명 저장 필드

    @Transient
    private MultipartFile thumbnailImageFile;

    @ManyToOne
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "blog_id")
//    private Blog blog;
//
//    @OneToMany(mappedBy = "post")
//    private List<Image> images;
//
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "userBlog_id")
    private UserBlog userBlog;

}