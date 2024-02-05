package com.quiet.onterview.interview.repository;

import com.quiet.onterview.interview.entity.InterviewRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InterviewRoomRepository extends JpaRepository<InterviewRoom, Long> {

    @Query("SELECT ir FROM InterviewRoom ir WHERE ir.member.memberId = :memberId")
    Page<InterviewRoom> findInterviewRoom(Long memberId, Pageable pageable);

    @Query("SELECT ir FROM InterviewRoom ir WHERE ir.member.memberId = :memberId AND ir.interviewRoomId = :interviewRoomId")
    InterviewRoom findInterviewRoomDetail(Long memberId, Long interviewRoomId);
}