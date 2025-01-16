package com.memo.post.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data // getter, setter
public class Post {
    private int id;
    private String userId;
    private String subject;
    private String content;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
