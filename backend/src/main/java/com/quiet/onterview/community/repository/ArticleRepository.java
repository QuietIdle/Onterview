package com.quiet.onterview.community.repository;

import com.quiet.onterview.community.entity.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Modifying
    @Query("update Article a set a.content = :content where a.articleId = :articleId")
    int updateContent(Long articleId, String content);
}
