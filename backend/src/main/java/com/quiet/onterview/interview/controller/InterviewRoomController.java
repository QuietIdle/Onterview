package com.quiet.onterview.interview.controller;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.InterviewRoomResponse;
import com.quiet.onterview.interview.service.InterviewRoomService;
import com.quiet.onterview.question.dto.response.MyQuestionFolderResponse;
import com.quiet.onterview.security.SecurityUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "interview-room-controller", description = "모의 면접장 컨트롤러")
@RestController
@RequestMapping("/api/interview-room")
@RequiredArgsConstructor
public class InterviewRoomController {
    private final InterviewRoomService interviewRoomService;

    @Operation(summary = "GET 방식으로 모의 면접장 전체 조회")
    @GetMapping
    public ResponseEntity<List<InterviewRoomResponse>> getInterviewRoomList(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(interviewRoomService.getInterviewRoomList(user.getMemberId()));
    }

    @Operation(summary = "POST 방식으로 모의 면접장 생성")
    @PostMapping
    public ResponseEntity<Void> registerInterviewRoom(
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody InterviewRoomRequest interviewRoomRequest) {
        interviewRoomService.createInterviewRoom(user.getMemberId(), interviewRoomRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "DELETE 방식으로 모의 면접장 삭제")
    @DeleteMapping("/{interview_room_id}")
    public ResponseEntity<Void> deleteInterviewRoom(@PathVariable("interview_room_id") Long interviewRoomId) {
        interviewRoomService.deleteinterviewRoom(interviewRoomId);
        return ResponseEntity.ok().build();
    }
}
