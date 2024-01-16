package com.quiet.onterview.question.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.quiet.onterview.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.GeneratorStrategy;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "MY_QUESTION_LIST")
public class MyQuestionList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MY_QUESTION_LIST_ID")
    private Long myQuestionListId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "LIST_NAME", nullable = false)
    private String listName;
}
