package com.quiet.onterview.question.service;

import com.quiet.onterview.question.dto.request.CommonQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.CommonQuestionFolderResponse;
import com.quiet.onterview.question.entity.CommonQuestionFolder;
import com.quiet.onterview.question.exception.CommonQuestionFolderNotFoundException;
import com.quiet.onterview.question.mapper.CommonQuestionFolderMapper;
import com.quiet.onterview.question.repository.CommonQuestionFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonQuestionFolderServiceImpl implements CommonQuestionFolderService {

    private final CommonQuestionFolderRepository commonQuestionFolderRepository;
    private final CommonQuestionFolderMapper commonQuestionFolderMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CommonQuestionFolderResponse> getAllCommonQuestionFolderInfo() {
        List<CommonQuestionFolder> commonQuestionFolderList = commonQuestionFolderRepository.findAllCommonQuestionFolderInfo();
        return commonQuestionFolderList.stream()
                .map(commonQuestionFolderMapper::commonQuestionFolderToCommonQuestionFolderResponse)
                .toList();
    }

    @Override
    public CommonQuestionFolderResponse getOneCommonQuestionFolderInfo(Long commonQuestionFolderId) {
        CommonQuestionFolder commonQuestionFolder = commonQuestionFolderRepository.findOneCommonQuestionFolderInfo(commonQuestionFolderId);
        return commonQuestionFolderMapper.commonQuestionFolderToCommonQuestionFolderResponse(commonQuestionFolder);
    }


    @Override
    public void createCommonQuestionFolder(CommonQuestionFolderRequest commonQuestionFolderRequest) {
        CommonQuestionFolder commonQuestionFolder = commonQuestionFolderMapper.commonQuestionFolderRequestToEntity(commonQuestionFolderRequest);
        commonQuestionFolderRepository.save(commonQuestionFolder);
    }

    @Override
    public void updateCommonQuestionFolder(Long commonQuestionFolderId, CommonQuestionFolderRequest commonQuestionFolderRequest) {
        CommonQuestionFolder commonQuestionFolder = commonQuestionFolderRepository.findById(commonQuestionFolderId)
                .orElseThrow(CommonQuestionFolderNotFoundException::new);
        Optional.ofNullable(commonQuestionFolderRequest.getCommonQuestionFolder())
                .ifPresent(commonQuestionFolder::updateCommonQuestionFolder);
    }

    @Override
    public void deleteCommonQuestionFolder(Long commonQuestionFolderId) {
        commonQuestionFolderRepository.delete(
                commonQuestionFolderRepository.findById(commonQuestionFolderId)
                        .orElseThrow(CommonQuestionFolderNotFoundException::new));
    }

}