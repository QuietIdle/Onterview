package com.quiet.onterview.question.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "COMMON_QUESTION_FOLDER")
public class CommonQuestionFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMON_QUESTION_FOLDER_ID")
    private Long commonQuestionFolderId;

    @Column(name = "COMMON_QUESTION_FOLDER", nullable = false)
    private String commonQuestionFolder;
}