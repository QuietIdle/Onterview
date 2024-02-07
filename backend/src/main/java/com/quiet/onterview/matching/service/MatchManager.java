package com.quiet.onterview.matching.service;

import com.quiet.onterview.matching.dto.request.MatchRequest;
import com.quiet.onterview.matching.dto.request.MatchRequest.MessageType;
import com.quiet.onterview.matching.exception.TypeMismatchException;
import com.quiet.onterview.matching.exception.UserNotFoundException;
import com.quiet.onterview.matching.repository.MatchRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchManager {

    private final MatchRepository matchRepository;

    public Integer enter(MatchRequest matchRequest, String user) {
        if (matchRequest.getType() != MessageType.ENTER) {
            throw new TypeMismatchException();
        }

        Integer roomId = matchRequest.getRoomId();
        int enter = matchRepository.enter(roomId, user);
        log.info("After Enter Room Size : {}", enter);
        return enter;
    }

    public void complete(String user) {
        matchRepository.saveComplete(user);
    }

    public void deleteComplete(String user) throws UserNotFoundException {
        boolean complete = matchRepository.findComplete(user);
        if (!complete) {
            throw new UserNotFoundException();
        }
        matchRepository.deleteComplete(user);
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

