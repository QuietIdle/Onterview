package com.quiet.onterview.question.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "RECORD_QUESTION")
public class RecordQuestion {

    @Id
    @GeneratedValue
    @Column(name = "RECORD_QUESTION_ID")
    private Long recordQuestionId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "RECORD_ROOM_ID")
    private RecordRoom recordRoom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MY_QUESTION_ID")
    private MyQuestion myQuestion;
}
