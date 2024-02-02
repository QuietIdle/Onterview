package com.quiet.onterview.interview.controller;

import com.quiet.onterview.interview.dto.request.InterviewRoomRequest;
import com.quiet.onterview.interview.dto.response.InterviewRoomDetailResponse;
import com.quiet.onterview.interview.dto.response.InterviewRoomResponse;
import com.quiet.onterview.interview.service.InterviewRoomService;
import com.quiet.onterview.security.SecurityUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "interview-room-controller", description = "모의 면접장 컨트롤러")
@RestController
@RequestMapping("/api/interview-room")
@RequiredArgsConstructor
public class InterviewRoomController {
    private final InterviewRoomService interviewRoomService;

    @Operation(summary = "GET 방식으로 모의 면접장 전체 조회")
    @GetMapping
    public ResponseEntity<Page<InterviewRoomResponse>> getInterviewRoomList(
            @AuthenticationPrincipal SecurityUser user,
            @PageableDefault(size = 2, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(interviewRoomService.getInterviewRoomList(user.getMemberId(), pageable));
    }

    @Operation(summary = "GET 방식으로 특정 모의 면접장 상세 조회")
    @GetMapping("/{interview_room_id}")
    public ResponseEntity<InterviewRoomDetailResponse> getInterviewRoomDetail(
            @AuthenticationPrincipal SecurityUser user,
            @PathVariable("interview_room_id") Long interviewRoomId) {
        return ResponseEntity.ok(interviewRoomService.getInterviewRoomDetail(user.getMemberId(), interviewRoomId));
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
        interviewRoomService.deleteInterviewRoom(interviewRoomId);
        return ResponseEntity.ok().build();
    }
}
