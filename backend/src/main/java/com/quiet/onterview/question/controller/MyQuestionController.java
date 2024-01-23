package com.quiet.onterview.question.controller;

import com.quiet.onterview.question.dto.response.MyAnswerAndVideoResponse;
import com.quiet.onterview.question.service.MyQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "my-question-controller", description = "My Question Controller")
@Controller
@Log4j2
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyQuestionController {

    private final MyQuestionService myQuestionService;

    @Operation(summary = "GET", description = "GET 방식으로 특정 면접 문항에 대한 답변과 영상 조회")
    @GetMapping("/my-question/{my_question_id}")
    public ResponseEntity<MyAnswerAndVideoResponse> getMyAnswerAndVideo(@PathVariable("my_question_id") Long myQuestionId) {
        return ResponseEntity.ok(myQuestionService.getMyAnswerAndVideo(myQuestionId));
    }

}
