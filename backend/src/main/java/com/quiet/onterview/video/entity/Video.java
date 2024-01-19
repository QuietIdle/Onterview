package com.quiet.onterview.video.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.quiet.onterview.common.BaseEntity;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.entity.RecordQuestion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MY_QUESTION_ID")
    private MyQuestion myQuestion;

    @Column(name = "VIDEO_URL", nullable = false)
    private String videoUrl;

    @Column(name = "VIDEO_LENGTH", nullable = false)
    private Long videoLength;

    @Column(name = "TUNBNAIL_URL", nullable = false)
    private String thumbnailUrl;

    @Column(name = "BOOKMARK")
    private Boolean bookmark = Boolean.FALSE;

    @Column(name = "feedback", nullable = false)
    private String feedback;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "RECORD_QUESTION_ID")
    private RecordQuestion recordQuestion;
}
