package com.quiet.onterview.community.dto.request;

import lombok.Getter;

@Getter
public class ArticlePostRequest {

    Long videoId;
    String title;
    String content;
}