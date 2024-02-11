package com.quiet.onterview.video.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
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

    public List<VideoInformationResponse> findAllInterviewVideoByMemberAndType(Long memberId, RoomType roomType) {
        QFileInformation thumbnailUrl = new QFileInformation("thumbnailUrl");
        QFileInformation videoUrl = new QFileInformation("videoUrl");
        JPAQuery<VideoInformationResponse> query = queryFactory
                .select(Projections.constructor(VideoInformationResponse.class,
                        video.videoId,
                        getQuestionIdByType(roomType),
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
                .leftJoin(video.videoUrl, videoUrl)
                .leftJoin(video.thumbnailUrl, thumbnailUrl);

        joinByRoomType(query, roomType);

        query
                .where(eqMemberId(roomType, memberId).and(eqRoomType(roomType)));
        return query.fetch();
    }

    private void joinByRoomType(JPAQuery<VideoInformationResponse> query, RoomType roomType) {
        switch (roomType) {
            case SELF -> query
                    .leftJoin(video.myQuestion, myQuestion)
                    .leftJoin(myQuestion.myQuestionFolder, myQuestionFolder1);
            case MULTI, SINGLE -> query
                    .leftJoin(video.interviewQuestion, interviewQuestion)
                    .leftJoin(interviewQuestion.interviewee, interviewee)
                    .leftJoin(interviewee.interviewRoom, interviewRoom);
        }
    }

    private BooleanExpression eqRoomType(RoomType roomType) {
        if (roomType == RoomType.SELF) {
            return null;
        }
        return interviewRoom.roomType.eq(roomType);
    }

    private BooleanExpression eqMemberId(RoomType roomType, Long memberId) {
        if (roomType == RoomType.SELF) {
            return myQuestionFolder1.member.memberId.eq(memberId);
        }
        return interviewee.member.memberId.eq(memberId);
    }

    private NumberPath<Long> getQuestionIdByType(RoomType roomType) {
        if (roomType == RoomType.SELF) {
            return video.myQuestion.myQuestionId;
        }
        return video.interviewQuestion.interviewQuestionId;
    }
}
