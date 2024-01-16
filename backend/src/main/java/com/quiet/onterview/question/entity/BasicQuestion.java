package com.quiet.onterview.question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "BASIC_QUESTION")
public class BasicQuestion {

    @Id
    @GeneratedValue
    @Column(name = "BASIC_QUESTION_ID")
    private Long basicQuestionId;

    @Column(name = "QUESTION", nullable = false)
    private String question;

    @Column(name = "CATEGORY", nullable = false)
    private String category;
}
