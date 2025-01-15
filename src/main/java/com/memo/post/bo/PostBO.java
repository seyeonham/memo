package com.memo.post.bo;

import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor // DI, 생성자 주입
@Service
public class PostBO {

    private final PostMapper postMapper;

    // 생성자 주입
//    public PostBO(PostMapper postMapper) {
//        this.postMapper = postMapper;
//    }

    // input: userId
    // output: List<Post>
    public List<Post> getPostListByUserId(int userId) {
        return postMapper.selectPostListByUserId(userId);
    }

    public int addPost(int userId, String subject, String content) {
        return postMapper.insertPost(userId, subject, content);
    }
}
