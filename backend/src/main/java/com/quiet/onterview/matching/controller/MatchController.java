package com.quiet.onterview.matching.controller;

import com.quiet.onterview.matching.dto.request.MatchRequest;
import com.quiet.onterview.matching.service.MatchService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @MessageMapping("/enter")
    public void enter(@Payload MatchRequest matchRequest, Principal user) {
        log.info("ENTER : {}", matchRequest);
        matchService.enter(matchRequest, user.getName());
    }

    @MessageMapping("/leave")
    public void leave(@Payload MatchRequest matchRequest, Principal user) {
        log.info("LEAVE : {}", matchRequest);
        matchService.leave(matchRequest, user.getName());
    }

    @MessageMapping("/match")
    public void waiting(@Payload MatchRequest matchRequest) {
        log.info("MATCH : {}", matchRequest);
        matchService.match(matchRequest);
    }
}
