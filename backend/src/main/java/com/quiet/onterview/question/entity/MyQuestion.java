package com.quiet.onterview.question.entity;

import com.quiet.onterview.video.entity.Video;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "MY_QUESTION")
public class MyQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MY_QUESTION_ID")
    private Long myQuestionId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MY_QUESTION_FOLDER_ID")
    private MyQuestionFolder myQuestionFolder;

    @Column(name = "QUESTION", nullable = false)
    private String question;

    @Column(name = "ANSWER", nullable = true)
    private String answer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COMMON_QUESTION_ID")
    private CommonQuestion commonQuestion;

    @OneToMany(mappedBy = "myQuestion")
    List<Video> videoList = new ArrayList<>();

    public void changeMyQuestionFolder(MyQuestionFolder myQuestionFolder) {
        this.myQuestionFolder = myQuestionFolder;
        myQuestionFolder.getMyQuestionList().add(this);
    }
}
