package com.quiet.onterview.community.service;

import com.quiet.onterview.community.dto.request.ArticlePostRequest;
import com.quiet.onterview.community.dto.response.ArticlePostResponse;

public interface ArticleService {

    ArticlePostResponse postArticle(Long memberId, ArticlePostRequest articlePostRequest);
}
