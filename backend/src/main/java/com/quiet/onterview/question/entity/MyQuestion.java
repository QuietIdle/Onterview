package com.quiet.onterview.question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "MY_QUESTION")
public class MyQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MY_QUESTION_ID")
    private Long myQuestionId;

    @ManyToOne
    @JoinColumn(name = "MY_QUESETION_LIST_ID")
    private MyQuestionList myQuestionList;

    @Column(name = "QUESTION", nullable = false)
    private String question;

    @Column(name = "ANSWER", nullable = true)
    private String answer;

    @ManyToOne
    @JoinColumn
    private BasicQuestion basicQuestions;

}
