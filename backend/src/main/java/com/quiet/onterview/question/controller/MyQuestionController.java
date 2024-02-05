package com.quiet.onterview.question.controller;

import com.quiet.onterview.question.dto.request.MyAnswerUpdateRequest;
import com.quiet.onterview.question.dto.request.MyQuestionMoveRequest;
import com.quiet.onterview.question.dto.request.MyQuestionRequest;
import com.quiet.onterview.question.dto.request.MyQuestionUpdateRequest;
import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;
import com.quiet.onterview.question.service.MyQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "my-question-controller", description = "나의 면접 문항 컨트롤러")
@RestController
@RequestMapping("/api/my-question")
@RequiredArgsConstructor
public class MyQuestionController {

    private final MyQuestionService myQuestionService;

    @Operation(summary = "GET 방식으로 특정 면접 문항에 대한 답변과 영상 조회")
    @GetMapping("/{my_question_id}")
    public ResponseEntity<MyAnswerAndVideoResponse> getMyAnswerAndVideo(@PathVariable("my_question_id") Long myQuestionId) {
        return ResponseEntity.ok(myQuestionService.getMyAnswerAndVideo(myQuestionId));
    }

    @Operation(summary = "POST 방식으로 나의 면접 질문 생성")
    @PostMapping
    public ResponseEntity<Void> registerMyQuestion(@RequestBody MyQuestionRequest myQuestionRequest) {
        myQuestionService.createMyQuestion(myQuestionRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PATCH 방식으로 나의 면접 질문 수정")
    @PatchMapping("/{my_question_id}")
    public ResponseEntity<Void> updateMyQuestion(
            @PathVariable("my_question_id") Long myQuestionId,
            @RequestBody MyQuestionUpdateRequest myQuestionUpdateRequest
    ) {
        myQuestionService.updateMyQuestion(myQuestionId, myQuestionUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PATCH 방식으로 특정 면접 문항에 대한 답변 수정")
    @PatchMapping("/answer/{my_question_id}")
    public ResponseEntity<Void> updateMyAnswer(
            @PathVariable("my_question_id") Long myQuestionId,
            @RequestBody MyAnswerUpdateRequest myAnswerUpdateRequest
    ) {
        myQuestionService.updateMyAnswer(myQuestionId, myAnswerUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "DELETE 방식으로 나의 면접 문항 삭제")
    @DeleteMapping("/{my_question_id}")
    public ResponseEntity<Void> deleteMyQuestion(@PathVariable("my_question_id") Long myQuestionId) {
        myQuestionService.deleteMyQuestion(myQuestionId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "PATCH 방식으로 나의 면접 질문 폴더간 이동")
    @PatchMapping("/move")
    public ResponseEntity<Void> moveMyQuestion(@RequestBody MyQuestionMoveRequest myQuestionMoveRequest) {
        myQuestionService.moveMyQuestion(myQuestionMoveRequest);
        return ResponseEntity.ok().build();
    }
}