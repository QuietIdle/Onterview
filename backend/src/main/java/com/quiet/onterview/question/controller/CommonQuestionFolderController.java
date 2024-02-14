package com.quiet.onterview.question.controller;

import com.quiet.onterview.question.dto.request.CommonQuestionFolderRequest;
import com.quiet.onterview.question.dto.request.MyQuestionFolderRequest;
import com.quiet.onterview.question.dto.response.CommonQuestionFolderResponse;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;
import com.quiet.onterview.question.service.CommonQuestionFolderService;
import com.quiet.onterview.question.service.MyQuestionFolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "common-question-folder-controller", description = "빈출 면접 문항 폴더 컨트롤러")
@RestController
@RequestMapping("/api/common-question-folder")
@RequiredArgsConstructor
public class CommonQuestionFolderController {
    private final CommonQuestionFolderService commonQuestionFolderService;

    @Operation(summary = "GET 방식으로 모든 빈출 면접 폴더별 질문 리스트 조회")
    @GetMapping
    public ResponseEntity<List<CommonQuestionFolderResponse>> getAllCommonQuestionFolderInfo() {
        return ResponseEntity.ok(commonQuestionFolderService.getAllCommonQuestionFolderInfo());
    }

    @Operation(summary = "GET 방식으로 특정 빈출 면접 폴더의 질문 리스트 조회")
    @GetMapping("/{common_question_folder_id}")
    public ResponseEntity<CommonQuestionFolderResponse> getOneCommonQuestionFolderInfo(
            @PathVariable("common_question_folder_id") Long commonQuestionFolderId
    ) {
        return ResponseEntity.ok(commonQuestionFolderService.getOneCommonQuestionFolderInfo(commonQuestionFolderId));
    }

    @Operation(summary = "POST 방식으로 빈출 면접 질문 폴더 생성")
    @PostMapping
    public ResponseEntity<Void> registerCommonQuestionFolder(
            @RequestBody CommonQuestionFolderRequest commonQuestionFolderRequest) {
        commonQuestionFolderService.createCommonQuestionFolder(commonQuestionFolderRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PATCH 방식으로 빈출 면접 질문 폴더명 수정")
    @PatchMapping("/{common_question_folder_id}")
    public ResponseEntity<Void> updateCommonQuestion(
            @PathVariable("common_question_folder_id") Long commonQuestionFolderId,
            @RequestBody CommonQuestionFolderRequest commonQuestionFolderRequest
    ) {
        commonQuestionFolderService.updateCommonQuestionFolder(commonQuestionFolderId, commonQuestionFolderRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "DELETE 방식으로 빈출 면접 질문 폴더 삭제")
    @DeleteMapping("/{common_question_folder_id}")
    public ResponseEntity<Void> deleteCommonQuestionFolder(@PathVariable("common_question_folder_id") Long commonQuestionFolderId) {
        commonQuestionFolderService.deleteCommonQuestionFolder(commonQuestionFolderId);
        return ResponseEntity.ok().build();
    }

}
