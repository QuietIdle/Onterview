package com.quiet.onterview.interview.entity;

import com.quiet.onterview.common.BaseEntity;
import com.quiet.onterview.question.entity.CommonQuestion;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "INTERVIEW_QUESTION")
public class InterviewQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERVIEW_QUESTION_ID")
    private Long interviewQuestionId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "INTERVIEW_ROOM_ID")
    private InterviewRoom interviewRoom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COMMON_QUESTION_ID")
    private CommonQuestion commonQuestion;


}
