package org.example.blogproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> replies;


    private String author;
    private LocalDateTime creationDate = LocalDateTime.now();
    private String content;

    public Comment(User user, Post post, Comment parent, String content) {
        this.user = user;
        this.post = post;
        this.parent = parent;
        this.content = content;
    }

    public void setPostId(Long postId) {

    }

    public void setUserId(Long userId) {

    }
}