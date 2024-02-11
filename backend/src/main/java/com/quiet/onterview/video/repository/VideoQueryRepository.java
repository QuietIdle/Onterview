package com.quiet.onterview.video.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quiet.onterview.file.dto.response.FileInformationResponse;
import com.quiet.onterview.file.entity.QFileInformation;
import com.quiet.onterview.interview.entity.RoomType;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.quiet.onterview.interview.entity.QInterviewQuestion.interviewQuestion;
import static com.quiet.onterview.interview.entity.QInterviewRoom.interviewRoom;
import static com.quiet.onterview.interview.entity.QInterviewee.interviewee;
import static com.quiet.onterview.question.entity.QMyQuestion.myQuestion;
import static com.quiet.onterview.question.entity.QMyQuestionFolder.myQuestionFolder1;
import static com.quiet.onterview.video.entity.QVideo.video;

@RequiredArgsConstructor
@Repository
public class VideoQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<VideoInformationResponse> findAllInterviewVideoByMemberAndType(Long memberId, RoomType type) {
        QFileInformation thumbnailUrl = new QFileInformation("thumbnailUrl");
        QFileInformation videoUrl = new QFileInformation("videoUrl");
        return queryFactory
                .select(Projections.constructor(VideoInformationResponse.class,
                        video.videoId,
                        video.interviewQuestion.interviewQuestionId,
                        video.title,
                        Projections.constructor(FileInformationResponse.class,
                                video.thumbnailUrl.originFilename,
                                video.thumbnailUrl.saveFilename),
                        Projections.constructor(FileInformationResponse.class,
                                video.videoUrl.originFilename,
                                video.videoUrl.saveFilename),
                        video.feedback,
                        video.bookmark))
                .from(video)
                .leftJoin(video.interviewQuestion, interviewQuestion)
                .leftJoin(interviewQuestion.interviewee, interviewee)
                .leftJoin(interviewee.interviewRoom, interviewRoom)
                .leftJoin(video.videoUrl, videoUrl)
                .leftJoin(video.thumbnailUrl, thumbnailUrl)
                .where(interviewee.member.memberId.eq(memberId).and(interviewRoom.roomType.eq(type)))
                .fetch();
    }

    public List<VideoInformationResponse> findAllSelfVideoByMemberAndType(Long memberId, RoomType type) {
        QFileInformation thumbnailUrl = new QFileInformation("thumbnailUrl");
        QFileInformation videoUrl = new QFileInformation("videoUrl");
        return queryFactory
                .select(Projections.constructor(VideoInformationResponse.class,
                        video.videoId,
                        video.myQuestion.myQuestionId,
                        video.title,
                        Projections.constructor(FileInformationResponse.class,
                                video.thumbnailUrl.originFilename,
                                video.thumbnailUrl.saveFilename),
                        Projections.constructor(FileInformationResponse.class,
                                video.videoUrl.originFilename,
                                video.videoUrl.saveFilename),
                        video.feedback,
                        video.bookmark))
                .from(video)
                .innerJoin(video.interviewQuestion, interviewQuestion)
                .leftJoin(video.myQuestion, myQuestion)
                .leftJoin(myQuestion.myQuestionFolder, myQuestionFolder1)
                .leftJoin(video.videoUrl, videoUrl)
                .leftJoin(video.thumbnailUrl, thumbnailUrl)
                .where(interviewee.member.memberId.eq(memberId))
                .fetch();
    }
}
