package com.quiet.onterview.video.service;

import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.repository.MyQuestionRepository;
import com.quiet.onterview.video.dto.request.VideoInformationRequest;
import com.quiet.onterview.video.dto.request.VideoUpdateRequest;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.exception.VideoNotFoundException;
import com.quiet.onterview.video.mapper.VideoMapper;
import com.quiet.onterview.video.repository.VideoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final MyQuestionRepository myQuestionRepository;
    private final VideoMapper videoMapper;

    @Override
    @Transactional(readOnly = true)
    public VideoDetailResponse loadVideoInformation(Long videoId) {
        return videoMapper.videoInformationToResponse(
                videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new)
        );
    }

    @Override
    public void createVideoInformation(VideoInformationRequest videoInformationRequest) {
        MyQuestion myQuestion = myQuestionRepository.findById(videoInformationRequest.getQuestionId())
                .orElseThrow(IllegalArgumentException::new);
        videoRepository.save(
                videoMapper.videoInformationToEntity(videoInformationRequest, myQuestion)
        );
    }

    @Override
    public void updateVideo(Long videoId, VideoUpdateRequest videoUpdateRequest) {
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
        video.updateTitle(videoUpdateRequest.getTitle());
        video.updateBookmark(videoUpdateRequest.getBookmark());
        video.updateFeedback(videoUpdateRequest.getFeedback());
        Optional.ofNullable(videoUpdateRequest.getTitle())
                .ifPresent(video::updateTitle);
        Optional.ofNullable(videoUpdateRequest.getFeedback()).ifPresent(video::updateFeedback);
        Optional.ofNullable(videoUpdateRequest.getBookmark()).ifPresent(video::updateBookmark);
    }

    @Override
    public void deleteVideo(Long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
        videoRepository.delete(video);
    }
}
