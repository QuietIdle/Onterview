package com.quiet.onterview.question.controller;

import com.quiet.onterview.question.dto.request.MyQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;
import com.quiet.onterview.question.service.MyQuestionFolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public ResponseEntity<List<MyQuestionFolderResponse>> getMyQuestionList(Long memberId) {
        return ResponseEntity.ok(myQuestionFolderService.getMyQuestionFolder(memberId));
    }

    @Operation(summary = "POST", description = "POST 방식으로 나의 면접 질문 폴더 생성")
    @PostMapping("/my-question-folder")
    public ResponseEntity<Void> registerMyQuestionFolder(@RequestBody MyQuestionFolderRequest myQuestionFolderRequest) {
        myQuestionFolderService.createMyQuestionFolder(myQuestionFolderRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PATCH", description = "PATCH 방식으로 나의 면접 질문 폴더명 수정")
    @PatchMapping("/my-question-folder/{my_question_folder_id}")
    public ResponseEntity<Void> updateMyQuestion(
            @PathVariable("my_question_folder_id") Long myQuestionFolderId,
            @RequestBody MyQuestionFolderRequest myQuestionFolderRequest
    ) {
        myQuestionFolderService.updateMyQuestionFolder(myQuestionFolderId, myQuestionFolderRequest);
        return ResponseEntity.ok().build();
    }

}
