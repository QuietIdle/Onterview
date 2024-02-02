package com.quiet.onterview.community.service;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.community.dto.request.ArticleModifyContentRequest;
import com.quiet.onterview.community.dto.request.ArticlePostRequest;
import com.quiet.onterview.community.dto.response.ArticlePostResponse;
import com.quiet.onterview.community.entity.Article;
import com.quiet.onterview.community.mapper.ArticleMapper;
import com.quiet.onterview.community.repository.ArticleRepository;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.exception.VideoNotFoundException;
import com.quiet.onterview.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final MemberRepository memberRepository;
    private final VideoRepository videoRepository;

    @Override
    public ArticlePostResponse postArticle(Long memberId, ArticlePostRequest articlePostRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new BaseException(ErrorCode.USER_NOT_EXISTS));
        Video video = videoRepository.findById(articlePostRequest.getVideoId()).orElseThrow(() ->
                new VideoNotFoundException());
        if(!video.getMyQuestion().getMyQuestionFolder().getMember().getMemberId().equals(memberId)) {
            throw new BaseException(ErrorCode.REQUEST_CONDITION_NOT_MATCHES);
        }
        Article article = articleMapper.articlePostRequestToArticle(member, video, articlePostRequest);
        return articleMapper.articleToArticlePostResponse(articleRepository.save(article));
    }

    @Override
    public void modifyArticleContent(Long memberId, Long articleId,
            ArticleModifyContentRequest articleModifyContentRequest) {
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new BaseException(ErrorCode.ARTICLE_NOT_EXISTS));
        if(!article.getMember().getMemberId().equals(memberId)) {
            throw new BaseException(ErrorCode.ARTICLE_WRITER_NOT_MATCHES);
        }
        if(articleModifyContentRequest.getContent().isEmpty()) {
            throw new BaseException(ErrorCode.REQUIRED_VALUE_NOT_EXISTS);
        }
        articleRepository.updateContent(articleId,articleModifyContentRequest.getContent());
    }

    @Override
    public void deleteArticle(Long memberId, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new BaseException(ErrorCode.ARTICLE_NOT_EXISTS));
        if(!article.getMember().getMemberId().equals(memberId)) {
            throw new BaseException(ErrorCode.ARTICLE_WRITER_NOT_MATCHES);
        }
        articleRepository.deleteById(articleId);
    }
}
