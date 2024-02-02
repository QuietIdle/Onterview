package com.quiet.onterview.interview.entity;

import com.quiet.onterview.common.BaseEntity;
import com.quiet.onterview.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
@Table(name = "INTERVIEW_ROOM")
public class InterviewRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERVIEW_ROOM_ID")
    private Long interviewRoomId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "QUESTION_TYPE", nullable = false)
    private QuestionType questionType;

    private Integer runTime;

    private String feedback;

    @OneToMany(mappedBy = "interviewRoom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<InterviewQuestion> interviewQuestionList = new ArrayList<>();

    public void addInterviewQuestion(InterviewQuestion interviewQuestion) {
        interviewQuestion.setInterviewRoom(this);
        interviewQuestionList.add(interviewQuestion);
    }
}
