package com.quiet.onterview.interview.entity;

import com.quiet.onterview.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "QUESTION_TYPE", nullable = false)
    private QuestionType questionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROOM_TYPE", nullable = false)
    private RoomType roomType;

    @Column(name = "RUNTIME")
    private Integer runtime;

    @Column(name = "FEEDBACK")
    private String feedback;

    @OneToMany(mappedBy = "interviewRoom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<InterviewQuestion> interviewQuestionList = new ArrayList<>();

    @OneToMany(mappedBy = "interviewRoom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<Interviewee> intervieweeList = new ArrayList<>();

    public void addInterviewQuestion(InterviewQuestion interviewQuestion) {
        interviewQuestion.setInterviewRoom(this);
        interviewQuestionList.add(interviewQuestion);
    }

    public void addInterviewee(Interviewee interviewee) {
        interviewee.setInterviewRoom(this);
        intervieweeList.add(interviewee);
    }
}
