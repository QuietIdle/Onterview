package com.quiet.onterview.community.repository;

import com.quiet.onterview.community.entity.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByOrderByCreateAtDesc();

    List<Article> findAllByOrderByLikeCountDesc();

    List<Article> findAllByOrderByCommentCountDesc();

    List<Article> findAllByTitleContainingOrderByCreateAtDesc(String title);

    List<Article> findAllByContentContainingOrderByCreateAtDesc(String content);

    List<Article> findAllByTitleContainingOrderByLikeCountDesc(String title);

    List<Article> findAllByContentContainingOrderByLikeCountDesc(String content);

    List<Article> findAllByTitleContainingOrderByCommentCountDesc(String title);

    List<Article> findAllByContentContainingOrderByCommentCountDesc(String content);

    List<Article> findByMember_MemberIdOrderByCreateAtDesc(Long memberId);

    List<Article> findByMember_MemberIdOrderByLikeCountDesc(Long memberId);

    List<Article> findByMember_MemberIdOrderByCommentCountDesc(Long memberId);

    List<Article> findByMember_MemberIdAndTitleContainingOrderByCreateAtDesc(Long memberId, String title);

    List<Article> findByMember_MemberIdAndContentContainingOrderByCreateAtDesc(Long memberId, String content);

    List<Article> findByMember_MemberIdAndTitleContainingOrderByLikeCountDesc(Long memberId, String title);

    List<Article> findByMember_MemberIdAndContentContainingOrderByLikeCountDesc(Long memberId, String content);

    List<Article> findByMember_MemberIdAndTitleContainingOrderByCommentCountDesc(Long memberId, String title);

    List<Article> findByMember_MemberIdAndContentContainingOrderByCommentCountDesc(Long memberId, String content);

    @Modifying
    @Query("update Article a set a.content = :content where a.articleId = :articleId")
    int updateContent(Long articleId, String content);

    @Modifying
    @Query("update Article a set a.likeCount = a.likeCount + :value where a.articleId = :articleId")
    int updateLikeCount(Long articleId, Integer value);

    @Modifying
    @Query("update Article a set a.commentCount = a.commentCount + :value where a.articleId = :articleId")
    void updateCommentCount(Long articleId, Integer value);
}
