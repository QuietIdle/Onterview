package com.quiet.onterview.video.entity;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

import com.quiet.onterview.common.BaseEntity;
import com.quiet.onterview.file.entity.FileInformation;
import com.quiet.onterview.question.entity.MyQuestion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
@Table(name = "VIDEO")
public class Video extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIDEO_ID")
    private Long videoId;

    @Column(name = "TITLE")
    private String title;


    @Column(name = "VIDEO_LENGTH")
    private Long videoLength;

    @Column(name = "BOOKMARK")
    private Boolean bookmark = Boolean.FALSE;

    @Column(name = "feedback")
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "MY_QUESTION_ID")
    private MyQuestion myQuestion;

    @OneToOne(fetch = LAZY, cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "VIDEO_URL")
    private FileInformation videoUrl;

    @OneToOne(fetch = LAZY, cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "THUMBNAIL_URL")
    private FileInformation thumbnailUrl;

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateBookmark(Boolean bookmark) {
        this.bookmark = bookmark;
    }

    public void updateFeedback(String feedback) {
        this.feedback = feedback;
    }
}
