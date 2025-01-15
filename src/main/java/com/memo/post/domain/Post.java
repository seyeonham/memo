package com.memo.post.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data // getter, setter
public class Post {
    public int id;
    public String userId;
    public String subject;
    public String content;
    public String imagePath;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
