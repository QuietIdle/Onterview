package com.quiet.onterview.matching.service;

import com.quiet.onterview.matching.dto.request.MatchRequest;
import com.quiet.onterview.matching.dto.response.MatchTokenResponse;
import com.quiet.onterview.matching.dto.response.MatchWaitingResponse;
import com.quiet.onterview.matching.mapper.MatchMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchMapper matchMapper;
    private final MatchManager matchManager;
    private final MessageService messageService;
    private final OpenViduService openViduService;

    @Override
    public void enter(MatchRequest matchRequest, String user) {
        MatchWaitingResponse waitingResponse = matchMapper.toWaitingResponse(
                matchManager.enter(matchRequest, user)
        );
        messageService.announceAll(
                matchRequest.getRoomId(),
                matchMapper.waitingResponseToJson(waitingResponse)
        );
    }

    @Override
    public void match(MatchRequest matchRequest) {
        List<String> users = matchManager.match(matchRequest);
        String sessionId = UUID.randomUUID().toString();

        users.forEach(u -> {
            matchManager.complete(u);
            String token = openViduService.getToken(sessionId);
            MatchTokenResponse tokenResponse = matchMapper.toTokenResponse(token);
            messageService.announceToUser(matchRequest.getRoomId(), u,
                    matchMapper.tokenResponseToJson(tokenResponse));
        });
    }
}

