package com.quiet.onterview.question.controller;

import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.question.dto.request.MyQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;
import com.quiet.onterview.question.dto.response.MyQuestionResponse;
import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.question.service.MyQuestionFolderService;
import com.quiet.onterview.question.service.MyQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "my-question-folder-controller", description = "My Question Folder Controller")
@Controller
@Log4j2
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyQuestionFolderController {
    private final MyQuestionFolderService myQuestionFolderService;

    @Operation(summary = "GET", description = "GET 방식으로 나의 면접 질문 전체 조회")
    @GetMapping("/my-question")
    public ResponseEntity<List<MyQuestionFolderResponse>> getMyQuestionList(Member member) {
        return ResponseEntity.ok(myQuestionFolderService.getMyQuestionFolder(member));
    }

    @Operation(summary = "PATCH", description = "PATCH 방식으로 나의 면접 질문 수정")
    @PatchMapping("/my-question-folder/{my_question_folder_id}")
    public ResponseEntity<Void> updateMyQuestion(
            @PathVariable("my_question_folder_id") Long myQuestionFolderId,
            @RequestBody MyQuestionFolderRequest myQuestionFolderRequest
    ) {
        myQuestionFolderService.updateMyQuestionFolder(myQuestionFolderId, myQuestionFolderRequest);
        return ResponseEntity.ok().build();
    }

}
