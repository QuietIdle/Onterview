package com.quiet.onterview.room.controller;

import com.quiet.onterview.room.dto.request.RoomProgressRequest;
import com.quiet.onterview.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @MessageMapping("/answer/{sessionId}")
    public void answer(
            @DestinationVariable String sessionId,
            @Payload RoomProgressRequest roomProgressRequest
    ) {
        log.info("ROOM ANSWER PROCESS : {}, REQUEST TYPE : {}", sessionId, roomProgressRequest.getType());
        roomService.process(sessionId, roomProgressRequest);
    }

}
