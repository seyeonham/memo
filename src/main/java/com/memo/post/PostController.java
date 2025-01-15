package com.memo.post;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {

    private final PostBO postBO;

    @GetMapping("/post-list-view")
    public String postListView(Model model, HttpSession session) {
        // 로그인 된 사람인지 검사
        Integer userId = (Integer)session.getAttribute("userId");
        if (userId == null) {
            // 비로그인이면 로그인 화면으로 이동시킨다.
            return "redirect:/user/sign-in-view";
        }

        // 데이터 가져오기
        List<Post> postList = postBO.getPostListByUserId(userId);

        // 모델에 담기
        model.addAttribute("postList", postList);
        return "post/postList";
    }

    @GetMapping("/post-create-view")
    public String postCreateView() {
        return "post/postCreate";
    }
}
