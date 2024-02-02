package com.quiet.onterview.community.controller;

import com.quiet.onterview.community.dto.request.ArticlePostRequest;
import com.quiet.onterview.community.dto.response.ArticlePostResponse;
import com.quiet.onterview.community.service.ArticleService;
import com.quiet.onterview.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {

    private final ArticleService articleService;
    @PostMapping
    public ResponseEntity<ArticlePostResponse> postArticle(@AuthenticationPrincipal SecurityUser user,
            @RequestBody ArticlePostRequest articlePostRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(articleService.postArticle(user.getMemberId(),articlePostRequest));
    }
}
