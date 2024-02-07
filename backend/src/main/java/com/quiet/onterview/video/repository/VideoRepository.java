package com.quiet.onterview.video.repository;

import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.video.entity.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findAllByMyQuestion(MyQuestion myQuestion);

    @Query("SELECT v FROM Video v JOIN v.myQuestion q JOIN q.myQuestionFolder f WHERE f.member.email = :email")
    List<Video> findAllByEmail(String email);

    @Query("SELECT v FROM Video v "
            + "join fetch v.interviewQuestion viq "
            + "join fetch viq.interviewee viqi "
            + "join fetch viqi.interviewRoom viqii "
            + "where viqi.member.memberId =:memberId")
    List<Video> findAllMultiVideoByMember(Long memberId);
}
