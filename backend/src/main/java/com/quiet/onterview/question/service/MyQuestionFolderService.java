package com.quiet.onterview.question.service;

import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;
import com.quiet.onterview.question.dto.response.MyQuestionResponse;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import com.quiet.onterview.question.mapper.MyQuestionFolderMapper;
import com.quiet.onterview.question.mapper.MyQuestionMapper;
import com.quiet.onterview.question.repository.MyQuestionFolderRepository;
import com.quiet.onterview.question.repository.MyQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface MyQuestionFolderService {

    List<MyQuestionFolderResponse> getMyQuestionFolder(Member member);
}