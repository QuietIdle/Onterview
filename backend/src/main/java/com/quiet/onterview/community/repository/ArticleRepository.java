package com.quiet.onterview.community.repository;

import com.quiet.onterview.community.entity.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByOrderByCreateAtAsc();

    List<Article> findAllByOrderByLikeCountDesc();

    List<Article> findAllByOrderByCommentCountDesc();

    List<Article> findByMember_MemberIdOrderByCreateAtAsc(Long memberId);

    List<Article> findByMember_MemberIdOrderByLikeCountDesc(Long memberId);

    List<Article> findByMember_MemberIdOrderByCommentCountDesc(Long memberId);

    @Modifying
    @Query("update Article a set a.content = :content where a.articleId = :articleId")
    int updateContent(Long articleId, String content);

    @Modifying
    @Query("update Article a set a.likeCount = a.likeCount + :value where a.articleId = :articleId")
    int updateLikeCount(Long articleId, Integer value);
}
