package com.quiet.onterview.question.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.quiet.onterview.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "MY_QUESTION_FOLDER")
public class MyQuestionFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MY_QUESTION_FOLDER_ID")
    private Long myQuestionFolderId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "myQuestionFolder")
    List<MyQuestion> myQuestionList = new ArrayList<>();

    @Column(name = "MY_QUESTION_FOLDER", nullable = false)
    private String myQuestionFolder;

    public void updateMyQuestionFolder(String myQuestionFolder) {
        this.myQuestionFolder = myQuestionFolder;
    }
}
