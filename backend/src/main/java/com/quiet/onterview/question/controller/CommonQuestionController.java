package com.quiet.onterview.question.controller;

import com.quiet.onterview.question.dto.request.*;
import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;
import com.quiet.onterview.question.service.CommonQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "common-question-controller", description = "빈출 면접 문항 컨트롤러")
@RestController
@RequestMapping("/api/common-question")
@RequiredArgsConstructor
public class CommonQuestionController {

    private final CommonQuestionService commonQuestionService;

    @Operation(summary = "POST 방식으로 빈출 면접 질문 생성")
    @PostMapping
    public ResponseEntity<Void> registerCommonQuestion(@RequestBody CommonQuestionRequest commonQuestionRequest) {
        commonQuestionService.createCommonQuestion(commonQuestionRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PATCH 방식으로 빈출 면접 질문 수정")
    @PatchMapping("/{common_question_id}")
    public ResponseEntity<Void> updateMyQuestion(
            @PathVariable("common_question_id") Long commonQuestionId,
            @RequestBody CommonQuestionUpdateRequest commonQuestionUpdateRequest
    ) {
        commonQuestionService.updateCommonQuestion(commonQuestionId, commonQuestionUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "DELETE 방식으로 빈출 면접 문항 삭제")
    @DeleteMapping("/{common_question_id}")
    public ResponseEntity<Void> deleteMyQuestion(@PathVariable("common_question_id") Long commonQuestionId) {
        commonQuestionService.deleteCommonQuestion(commonQuestionId);
        return ResponseEntity.ok().build();
    }
}
