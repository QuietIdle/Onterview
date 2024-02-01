package com.quiet.onterview.matching.service;

import com.quiet.onterview.matching.dto.request.MatchRequest;
import com.quiet.onterview.matching.dto.request.MatchRequest.MessageType;
import com.quiet.onterview.matching.exception.TypeMismatchException;
import com.quiet.onterview.matching.repository.MatchRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchManager {

    private final MatchRepository matchRepository;

    public Integer enter(MatchRequest matchRequest, String user) {
        if (matchRequest.getType() != MessageType.ENTER) {
            throw new TypeMismatchException();
        }

        Integer roomId = matchRequest.getRoomId();
        return matchRepository.enter(roomId, user);
    }

    public void leave(String user) {
        int roomId = matchRepository.findRoom(user);
        matchRepository.leave(roomId, user);
    }

    public List<String> match(MatchRequest matchRequest) {
        if (matchRequest.getType() != MessageType.MATCH) {
            throw new TypeMismatchException();
        }

        Integer roomId = matchRequest.getRoomId();
        Integer matchCount = matchRequest.getMatchCount();

        if (matchRepository.isMatch(roomId, matchCount)) {
            return matchRepository.getUsers(roomId, matchCount);
        }
        return Collections.emptyList();
    }
}

