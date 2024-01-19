package com.quiet.onterview.video.service;

import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.repository.MyQuestionRepository;
import com.quiet.onterview.video.dto.request.VideoInformationRequest;
import com.quiet.onterview.video.dto.request.VideoUpdateRequest;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.exception.VideoNotFoundException;
import com.quiet.onterview.video.mapper.VideoMapper;
import com.quiet.onterview.video.repository.VideoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final MyQuestionRepository myQuestionRepository;
    private final VideoMapper videoMapper;


    @Override
    public VideoDetailResponse loadVideoInformation(Long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
        return videoMapper.videoToVideoDetailResponse(video);
    }

    @Override
    public List<VideoInformationResponse> loadVideoInformationByMyQuestion(Long myQuestionId) {
        MyQuestion myQuestion = myQuestionRepository.findById(myQuestionId).orElseThrow(IllegalArgumentException::new);
        List<Video> allByMyQuestion = videoRepository.findAllByMemberAndMyQuestion(
                myQuestion.getMyQuestionList().getMember().getMemberId(), myQuestionId);

        return videoMapper.videoListToVideoInformationResponseList(myQuestionId, allByMyQuestion);
    }

    @Override
    public void registerVideo(VideoInformationRequest videoInformationRequest) {
        MyQuestion myQuestion = myQuestionRepository.findById(videoInformationRequest.getQuestionId())
                .orElseThrow(IllegalArgumentException::new);

        Video video = videoMapper.videoInformationRequestToVideo(myQuestion, videoInformationRequest);

        videoRepository.save(video);
    }

    @Override
    public void updateVideo(Long videoId, VideoUpdateRequest videoUpdateRequest) {
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
        video.updateEntity(videoUpdateRequest);
    }

}
