package com.memo.post.bo;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor // DI, 생성자 주입
@Service
public class PostBO {

    private final PostMapper postMapper;
    private final FileManagerService fileManager;
    // private Logger log = LoggerFactory.getLogger(PostBO.class);
    // private Logger log = LoggerFactory.getLogger(this.getClass());

    // 생성자 주입
//    public PostBO(PostMapper postMapper) {
//        this.postMapper = postMapper;
//    }

    // input: userId
    // output: List<Post>
    public List<Post> getPostListByUserId(int userId) {
        return postMapper.selectPostListByUserId(userId);
    }

    // input: postId, userId
    // output: Post or null
    public Post getPostByPostIdUserId(int postId, int userId) {
        return postMapper.selectPostByPostIdUserId(postId, userId);
    }

    // input: userId, userLoginId, subject, content, file
    // output: int(성공 행 개수)
    public int addPost(int userId, String userLoginId, String subject,
                       String content, MultipartFile file) {

        String imagePath = null;

        // 파일 존재시 업로드
        if (file != null) {
            imagePath = fileManager.uploadFile(file, userLoginId);
        }

        return postMapper.insertPost(userId, subject, content, imagePath);
    }

    public void updatePostByPostIdUserId(int userId, String userLoginId, int postId,
                                         String subject, String content, MultipartFile file) {

        // 기존글 가져오기 - 1. 이미지 교체시 기존 이미지 제거 위해 2. 대상 존재 확인(생략 가능)
        Post post = postMapper.selectPostByPostIdUserId(postId, userId);
        if (post == null) {
            log.warn("[### 글 수정] post is null. postId:{}, userId:{}", postId, userId);
            return;
        }

        // 파일 존재시 이미지 업로드
        String imagePath = null;
        if (file != null) {
            // 새이미지 업로드
            imagePath = fileManager.uploadFile(file, userLoginId);

            // imagePath가 있으면(성공) 이고, 기존이미지가 있다면 기존이미지를 삭제
            if (imagePath != null && post.getImagePath() != null) {
                // 폴더와 이미지 삭제(컴퓨터 서버에 있는)
                fileManager.deleteFile(post.getImagePath());
            }
        }

        // DB update
        // imagePath는 null이거나 있다. 분기는 mapper 쿼리에서 한다.
        postMapper.updatePostById(postId, subject, content, imagePath);
    }

    public void deletePostById(int postId) {

        // 기존 글 가져오기 - 이미지파일 존재시 파일 삭제를 위해
        Post post = postMapper.selectPostById(postId);

        if (post == null) {
            log.warn("[### 글 삭제] post is null. postId:{}", postId);
            return;
        }

        // imagePath가 있다면 삭제
        String imagePath = post.getImagePath();
        if (imagePath != null) {
            fileManager.deleteFile(imagePath);
        }

        // DB delete
        postMapper.deletePostById(postId);

    }
}
