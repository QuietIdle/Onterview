package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.CommonQuestionRequest;
import com.quiet.onterview.question.dto.request.CommonQuestionUpdateRequest;
import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.CommonQuestionFolder;
import com.quiet.onterview.question.exception.CommonQuestionNotFoundException;
import com.quiet.onterview.question.mapper.CommonQuestionMapper;
import com.quiet.onterview.question.repository.CommonQuestionFolderRepository;
import com.quiet.onterview.question.repository.CommonQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonQuestionServiceImpl implements CommonQuestionService {

    private final CommonQuestionFolderRepository commonQuestionFolderRepository;
    private final CommonQuestionRepository commonQuestionRepository;
    private final CommonQuestionMapper commonQuestionMapper;

    @Override
    public void createCommonQuestion(CommonQuestionRequest commonQuestionRequest) {
        CommonQuestion commonQuestion = commonQuestionMapper.commonQuestionRequestToEntity(commonQuestionRequest);

        Optional<CommonQuestionFolder> commonQuestionFolder = commonQuestionFolderRepository.findById(commonQuestionRequest.getCommonQuestionFolderId());
        commonQuestionFolder.ifPresent(commonQuestion::changeCommonQuestionFolder);

        commonQuestionRepository.save(commonQuestion);
    }

    @Override
    public void updateCommonQuestion(Long commonQuestionId, CommonQuestionUpdateRequest commonQuestionUpdateRequest) {
        CommonQuestion commonQuestion = commonQuestionRepository.findById(commonQuestionId)
                .orElseThrow(CommonQuestionNotFoundException::new);
        Optional.ofNullable(commonQuestionUpdateRequest.getCommonQuestion())
                .ifPresent(commonQuestion::updateCommonQuestion);
    }

    @Override
    public void deleteCommonQuestion(Long commonQuestionId) {
        commonQuestionRepository.delete(
                commonQuestionRepository.findById(commonQuestionId)
                .orElseThrow(CommonQuestionNotFoundException::new));
    }
}
