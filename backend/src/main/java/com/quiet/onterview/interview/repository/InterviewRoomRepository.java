package com.quiet.onterview.interview.repository;

import com.quiet.onterview.interview.entity.InterviewRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterviewRoomRepository extends JpaRepository<InterviewRoom, Long> {

    @Query("SELECT ir FROM InterviewRoom ir where ir.member.memberId = :memberId")
    List<InterviewRoom> findInterviewRoom(Long memberId);
}
