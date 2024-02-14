package com.quiet.onterview.video.repository;

import com.quiet.onterview.interview.entity.RoomType;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.video.entity.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findAllByMyQuestion(MyQuestion myQuestion);

    @Query("SELECT v FROM Video v "
            + "JOIN v.myQuestion q on v.myQuestion.myQuestionId = q.myQuestionId "
            + "JOIN q.myQuestionFolder f on f.myQuestionFolderId = q.myQuestionFolder.myQuestionFolderId "
            + "and f.member.memberId =:memberId")
    List<Video> findAllSelfVideoByMember(Long memberId);

    @Query("SELECT v FROM Video v "
            + "join InterviewQuestion iq on v.interviewQuestion.interviewQuestionId = iq.interviewQuestionId "
            + "join Interviewee iv on iq.interviewee.intervieweeId = iv.intervieweeId and iv.member.memberId =:memberId "
            + "join InterviewRoom ivr on iv.interviewRoom.interviewRoomId = ivr.interviewRoomId and ivr.roomType=:type")
    List<Video> findAllMultiVideoByMember(Long memberId, RoomType type);

    @Query("SELECT v FROM Video v "
            + "join InterviewQuestion iq on v.interviewQuestion.interviewQuestionId = iq.interviewQuestionId "
            + "join Interviewee iv on iq.interviewee.intervieweeId = iv.intervieweeId and iv.member.memberId =:memberId "
            + "join InterviewRoom ivr on iv.interviewRoom.interviewRoomId = ivr.interviewRoomId and ivr.roomType=:type")
    List<Video> findAllSingleVideoByMember(Long memberId, RoomType type);
}
