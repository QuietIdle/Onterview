package com.quiet.onterview.question.entity;

import com.quiet.onterview.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "RECORD_QUESTION")
public class RecordQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_QUESTION_ID")
    private Long recordQuestionId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "RECORD_ROOM_ID")
    private RecordRoom recordRoom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MY_QUESTION_ID")
    private MyQuestion myQuestion;
}
