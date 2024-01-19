package com.quiet.onterview.video.repository;

import com.quiet.onterview.video.entity.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query("SELECT v FROM Video v JOIN v.myQuestion mq "
            + "JOIN mq.myQuestionList mql "
            + "JOIN mql.member m "
            + "WHERE m.memberId = :memberId AND mq.myQuestionId = :myQuestionId")
    List<Video> findAllByMemberAndMyQuestion(Long memberId, Long myQuestionId);
}
