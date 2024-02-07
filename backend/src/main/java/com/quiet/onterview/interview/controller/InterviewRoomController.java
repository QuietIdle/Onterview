package com.quiet.onterview.interview.controller;

import com.quiet.onterview.interview.service.InterviewRoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "interview-room-controller", description = "모의 면접장 컨트롤러")
@RestController
@RequestMapping("/api/interview-room")
@RequiredArgsConstructor
public class InterviewRoomController {
    private final InterviewRoomService interviewRoomService;

//    @Operation(summary = "GET 방식으로 모의 면접장 전체 조회")
//    @GetMapping
//    public ResponseEntity<Page<InterviewRoomResponse>> getInterviewRoomList(
//            @AuthenticationPrincipal SecurityUser user,
//            @RequestParam(name = "roomType", required = true) String roomType,
//            @PageableDefault(size = 10, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
//    ) {
//        if (roomType.equals("single")) {
//            return ResponseEntity.ok(interviewRoomService.getSingleInterviewRoomList(user.getMemberId(), pageable));
//        } else if (roomType.equals("multi")) {
//            return ResponseEntity.ok(interviewRoomService.getMultiInterviewRoomList(user.getMemberId(), pageable));
//        } else if (roomType.equals("all")) {
//            return ResponseEntity.ok(interviewRoomService.getInterviewRoomList(user.getMemberId(), pageable));
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    @Operation(summary = "GET 방식으로 특정 모의 면접장 상세 조회")
//    @GetMapping("/{interview_room_id}")
//    public ResponseEntity<InterviewRoomDetailResponse> getInterviewRoomDetail(
//            @AuthenticationPrincipal SecurityUser user,
//            @PathVariable("interview_room_id") Long interviewRoomId) {
//        return ResponseEntity.ok(interviewRoomService.getInterviewRoomDetail(user.getMemberId(), interviewRoomId));
//    }
//
//    @Operation(summary = "GET 방식으로 모의 면접 영상 전체 조회")
//    @GetMapping("/video")
//    public ResponseEntity<List<VideoStorageResponse>> getInterviewVideoList(
//            @AuthenticationPrincipal SecurityUser user,
//            @RequestParam(name = "roomType", required = true) String roomType) {
//        if (roomType.equals("single")) {
//            return ResponseEntity.ok(interviewRoomService.getSingleVideoList(user.getMemberId()));
//        } else if (roomType.equals("multi")) {
//            return ResponseEntity.ok(interviewRoomService.getMultiVideoList(user.getMemberId()));
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }

//    @Operation(summary = "POST 방식으로 모의 면접장 생성")
//    @PostMapping
//    public ResponseEntity<List<CommonQuestionResponse>> registerInterviewRoom(
//            @RequestBody InterviewRoomRequest interviewRoomRequest) {
//        return ResponseEntity.ok(interviewRoomService.createInterviewRoom(interviewRoomRequest));
//    }

//    @Operation(summary = "DELETE 방식으로 모의 면접장 삭제")
//    @DeleteMapping("/{interview_room_id}")
//    public ResponseEntity<Void> deleteInterviewRoom(@PathVariable("interview_room_id") Long interviewRoomId) {
//        interviewRoomService.deleteInterviewRoom(interviewRoomId);
//        return ResponseEntity.ok().build();
//    }
}
