package com.quiet.onterview.community.service;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.community.dto.request.CommentPostRequest;
import com.quiet.onterview.community.dto.response.CommentListResponse;
import com.quiet.onterview.community.dto.response.CommentObjectResponse;
import com.quiet.onterview.community.dto.response.CommentPostResponse;
import com.quiet.onterview.community.dto.response.CommentResponse;
import com.quiet.onterview.community.entity.Article;
import com.quiet.onterview.community.entity.Comment;
import com.quiet.onterview.community.mapper.CommentMapper;
import com.quiet.onterview.community.repository.ArticleRepository;
import com.quiet.onterview.community.repository.CommentRepository;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentPostResponse postComment(Long memberId, CommentPostRequest commentPostRequest) {
        if(commentPostRequest.getContent()==null) {
            throw new BaseException(ErrorCode.REQUIRED_VALUE_NOT_EXISTS);
        }
        Article article = articleRepository.findById(commentPostRequest.getArticleId()).orElseThrow(() ->
                new BaseException(ErrorCode.ARTICLE_NOT_EXISTS));
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new BaseException(ErrorCode.USER_NOT_EXISTS));
        Comment comment;
        if(commentPostRequest.getParentCommentId()!=0) { // 답글
            Comment parent = commentRepository.findById(commentPostRequest.getParentCommentId()).orElseThrow(() ->
                    new BaseException(ErrorCode.COMMENT_NOT_EXISTS));
            if(parent.getParent()!=null) { // 자식 댓글에 답글을 달려고 하는 경우
                throw new BaseException(ErrorCode.CANNOT_CREATE_CHILD_COMMENT_TO_CHILD_COMMENT);
            }
            comment = commentMapper.commentPostRequestToComment(commentPostRequest, parent, article, member);
        } else { // 댓글
            comment = commentMapper.commentPostRequestToComment(commentPostRequest, null, article, member);
        }
        return commentMapper.commentToCommentPostResponse(commentRepository.save(comment),memberId);
    }

    @Override
    public void deleteComment(Long memberId, Long commentId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new BaseException(ErrorCode.USER_NOT_EXISTS));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new BaseException(ErrorCode.COMMENT_NOT_EXISTS));
        if(!comment.getMember().getMemberId().equals(member.getMemberId())) {
            throw new BaseException(ErrorCode.COMMENT_WRITER_NOT_MATCHES);
        }
        commentRepository.deleteById(comment.getCommentId());
    }

    @Override
    public CommentListResponse getArticleCommentInfo(Long articleId, Long memeberId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new BaseException(ErrorCode.ARTICLE_NOT_EXISTS));
        return CommentListResponse.builder()
                .commentCount(commentRepository.countByArticle_ArticleId(article.getArticleId()))
                .comments(getArticleCommentList(article.getArticleId(),memeberId))
                .build();
    }

    protected List<CommentObjectResponse> getArticleCommentList(Long articleId, Long memeberId) {
        List<Comment> parentCommentList = getParentCommentList(articleId);
        List<CommentObjectResponse> commentObjectResponseList = new ArrayList<>();
        parentCommentList.stream().forEach(parentComment -> {

            List<Comment> childList = getChildCommentList(parentComment.getCommentId());
            List<CommentResponse> childResponse = new ArrayList<>();
            childList.stream().forEach(childComment -> {
                childResponse.add(commentMapper.commentToCommentResponse(childComment, memeberId));
            });
            System.out.println("CHILD SIZE " + childResponse.size());
            commentObjectResponseList.add(CommentObjectResponse.builder()
                    .parentComment(commentMapper.commentToCommentResponse(parentComment, memeberId))
                    .childCommentList(childResponse)
                    .build());
        });
        return commentObjectResponseList;
    }

    protected List<Comment> getParentCommentList(Long articleId) {
        return commentRepository.findAllParentCommentByArticleId(articleId);
    }

    protected List<Comment> getChildCommentList(Long parentCommentId) {
        return commentRepository.findAllChildCommentByCommentId(parentCommentId);
    }
}
