package com.quiet.onterview.community.mapper;

import com.quiet.onterview.community.dto.request.ArticlePostRequest;
import com.quiet.onterview.community.dto.response.ArticlePostResponse;
import com.quiet.onterview.community.entity.Article;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.mapper.VideoMapper;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private VideoMapper videoMapper;

    public Article articlePostRequestToArticle(Member member, Video video,
            ArticlePostRequest articlePostRequest) {
        return Article.builder()
                .member(member)
                .video(video)
                .title(articlePostRequest.getTitle())
                .content(articlePostRequest.getContent())
                .likeCount(0)
                .commentCount(0)
                .build();
    }

    public ArticlePostResponse articleToArticlePostResponse(Article article) {
        return ArticlePostResponse.builder()
                .articleId(article.getArticleId())
                .videoId(article.getVideo().getVideoId())
                .title(article.getTitle())
                .content(article.getContent())
                .likeCount(article.getLikeCount())
                .commentCount(article.getCommentCount())
                .writtenDate(article.getCreateAt().format(formatter))
                .build();
    }
}
