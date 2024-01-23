package com.quiet.onterview.question.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
@Table(name = "COMMON_QUESTION")
public class CommonQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMON_QUESTION_ID")
    private Long commonQuestionId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COMMON_QUESTION_FOLDER_ID")
    private CommonQuestionFolder commonQuestionFolder;

    @Column(name = "QUESTION", nullable = false)
    private String commonQuestion;
}
