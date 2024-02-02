package com.quiet.onterview.community.controller;

import com.quiet.onterview.community.dto.request.ArticleModifyContentRequest;
import com.quiet.onterview.community.dto.request.ArticlePostRequest;
import com.quiet.onterview.community.dto.request.CommentPostRequest;
import com.quiet.onterview.community.dto.response.ArticlePostResponse;
import com.quiet.onterview.community.dto.response.CommentPostResponse;
import com.quiet.onterview.community.service.ArticleService;
import com.quiet.onterview.community.service.CommentService;
import com.quiet.onterview.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {

    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ArticlePostResponse> postArticle(@AuthenticationPrincipal SecurityUser user,
            @RequestBody ArticlePostRequest articlePostRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(articleService.postArticle(user.getMemberId(),articlePostRequest));
    }

    @PatchMapping("/{articleId}")
    public ResponseEntity modifyArticleContent(@AuthenticationPrincipal SecurityUser user,
            @PathVariable("articleId") Long articleId,
            @RequestBody ArticleModifyContentRequest articleModifyContentRequest) {
        articleService.modifyArticleContent(user.getMemberId(), articleId, articleModifyContentRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity deleteArticle(@AuthenticationPrincipal SecurityUser user,
            @PathVariable("articleId") Long articleId) {
        articleService.deleteArticle(user.getMemberId(), articleId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentPostResponse> postComment(@AuthenticationPrincipal SecurityUser user,
            @RequestBody CommentPostRequest commentPostRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.postComment(user.getMemberId(), commentPostRequest));
    }
}
