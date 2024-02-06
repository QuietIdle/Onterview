package com.quiet.onterview.interview.mapper;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.InterviewQuestionResponse;
import com.quiet.onterview.interview.dto.response.InterviewRoomDetailResponse;
import com.quiet.onterview.interview.dto.response.InterviewRoomResponse;
import com.quiet.onterview.interview.dto.response.InterviewVideoResponse;
import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.interview.entity.InterviewRoom;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InterviewRoomMapper {

    private final InterviewQuestionMapper interviewQuestionMapper;
    private final VideoMapper videoMapper;

    public InterviewRoom interviewRoomRequestToEntity(Member member, InterviewRoomRequest interviewRoomRequest) {
        return InterviewRoom.builder()
                .member(member)
                .interviewQuestionList(new ArrayList<>())
                .questionType(interviewRoomRequest.getQuestionType())
                .roomType(interviewRoomRequest.getRoomType())
                .build();
    }

    public InterviewRoomResponse interviewRoomTointerviewRoomResponse(InterviewRoom interviewRoom) {
        return InterviewRoomResponse.builder()
                .interviewRoomId(interviewRoom.getInterviewRoomId())
                .runTime(interviewRoom.getRuntime())
                .questionType(interviewRoom.getQuestionType())
                .roomType(interviewRoom.getRoomType())
                .createAt(interviewRoom.getCreateAt())
                .numOfQuestion(interviewRoom.getInterviewQuestionList().size())
                .build();
    }

    public InterviewRoomDetailResponse interviewRoomToInterviewRoomDetailResponse(InterviewRoom interviewRoom) {
        List<InterviewQuestion> interviewQuestionList = interviewRoom.getInterviewQuestionList();
        List<InterviewQuestionResponse> interviewQuestionResponseList = interviewQuestionList.stream()
                .map(interviewQuestionMapper::interviewQuestionToInterviewQuestionResponse)
                .toList();

        InterviewQuestion firstInterviewQuestion= interviewQuestionList.get(0);
        Video video = firstInterviewQuestion.getVideo();
        VideoDetailResponse videoDetailResponse = (video != null) ? videoMapper.videoToDetailResponse(video) : null;

        return InterviewRoomDetailResponse.builder()
                .interviewRoomId(interviewRoom.getInterviewRoomId())
                .questionType(interviewRoom.getQuestionType())
                .roomType(interviewRoom.getRoomType())
                .runTime(interviewRoom.getRuntime())
                .createAt(interviewRoom.getCreateAt())
                .feedback(interviewRoom.getFeedback())
                .interviewQuestionList(interviewQuestionResponseList)
                .videoDetail(videoDetailResponse)
                .build();
    }
    public InterviewVideoResponse entityToInterviewVideoResponse(InterviewQuestion interviewQuestion, Video video) {

        return InterviewVideoResponse.builder()
                .videoId(video.getVideoId())
                .thumbnailUrl(video.getThumbnailUrl())
                .title(video.getTitle())
                .question(interviewQuestion.getCommonQuestion().getCommonQuestion())
                .build();
    }
}