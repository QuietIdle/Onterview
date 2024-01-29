package com.quiet.onterview.question.mapper;

import com.quiet.onterview.question.dto.request.CommonQuestionFolderRequest;
import com.quiet.onterview.question.entity.CommonQuestionFolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonQuestionFolderMapper {

    public CommonQuestionFolder commonQuestionFolderRequestToEntity(CommonQuestionFolderRequest commonQuestionFolderRequest) {
        return CommonQuestionFolder.builder()
                .commonQuestionFolder(commonQuestionFolderRequest.getCommonQuestionFolder())
                .build();

    }

}