package com.quiet.onterview.video.service;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.file.service.FileService;
import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.interview.entity.RoomType;
import com.quiet.onterview.interview.repository.InterviewQuestionRepository;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.repository.MyQuestionRepository;
import com.quiet.onterview.security.SecurityUser;
import com.quiet.onterview.video.dto.request.*;
import com.quiet.onterview.video.dto.response.VideoDetailResponse;
import com.quiet.onterview.video.dto.response.VideoInformationResponse;
import com.quiet.onterview.video.entity.Video;
import com.quiet.onterview.video.exception.VideoNotFoundException;
import com.quiet.onterview.video.mapper.VideoMapper;
import com.quiet.onterview.video.repository.VideoQueryRepository;
import com.quiet.onterview.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoQueryRepository videoQueryRepository;
    private final MyQuestionRepository myQuestionRepository;
    private final InterviewQuestionRepository interviewQuestionRepository;
    private final VideoMapper videoMapper;
    private final FileService fileService;

    @Override
    @Transactional(readOnly = true)
    public VideoDetailResponse loadVideoInformation(Long videoId) {
        return videoMapper.videoToDetailResponse(
                videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoInformationResponse> loadAllMyVideo(SecurityUser user, RoomType roomType) {
        switch (roomType) {
            case SELF -> {
                return videoQueryRepository.findAllSelfVideoByMemberAndType(user.getMemberId(), roomType);
            }case MULTI, SINGLE -> {
                return videoQueryRepository.findAllInterviewVideoByMemberAndType(user.getMemberId(), roomType);
            }
            default -> throw new BaseException(ErrorCode.VIDEO_NOT_FOUND);
        }
    }

    @Override
    public void createVideoInformation(VideoInformationRequest videoInformationRequest) {
        MyQuestion myQuestion = Optional.ofNullable(
                        videoInformationRequest.getMyQuestionId())
                .flatMap(myQuestionRepository::findById)
                .orElse(null);

        InterviewQuestion interviewQuestion = Optional.ofNullable(
                        videoInformationRequest.getInterviewQuestionId())
                .flatMap(interviewQuestionRepository::findById)
                .orElse(null);

        videoRepository.save(videoMapper.videoInformationToEntity(videoInformationRequest,
                myQuestion,
                interviewQuestion));
    }

    @Override
    public void updateVideo(Long videoId, VideoUpdateRequest videoUpdateRequest) {
        Video video = videoRepository.findById(videoId).orElseThrow(VideoNotFoundException::new);
        Optional.ofNullable(videoUpdateRequest.getTitle()).ifPresent(video::updateTitle);
        Optional.ofNullable(videoUpdateRequest.getFeedback()).ifPresent(video::updateFeedback);
        Optional.ofNullable(videoUpdateRequest.getBookmark()).ifPresent(video::updateBookmark);
    }

    @Override
    public void deleteVideo(VideoDeleteRequest videos, String token) {
        fileService.deleteFilesOnFileServer(token, videos.getVideos().toArray(Long[]::new));
        videos.getVideos().forEach(v -> videoRepository.delete(
                videoRepository.findById(v).orElseThrow(VideoNotFoundException::new)
        ));
    }
}
