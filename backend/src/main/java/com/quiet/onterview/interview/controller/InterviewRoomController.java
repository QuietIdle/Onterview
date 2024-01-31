package com.quiet.onterview.interview.controller;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.service.InterviewRoomService;
import com.quiet.onterview.security.SecurityUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "interview-room-controller", description = "모의 면접장 컨트롤러")
@RestController
@RequestMapping("/api/interview-room")
@RequiredArgsConstructor
public class InterviewRoomController {
    private final InterviewRoomService interviewRoomService;

    @Operation(summary = "POST 방식으로 모의 면접장 생성")
    @PostMapping
    public ResponseEntity<Void> registerInterviewRoom(
            @AuthenticationPrincipal SecurityUser user,
            @RequestBody InterviewRoomRequest interviewRoomRequest) {
        interviewRoomService.createInterviewRoom(user.getMemberId(), interviewRoomRequest);
        return ResponseEntity.ok().build();
    }
}
