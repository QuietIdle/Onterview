package com.quiet.onterview.matching.repository;

import com.quiet.onterview.interview.entity.RoomType;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.LongStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MatchRepository {

    private Map<Long, ConcurrentLinkedDeque<String>> waitingRooms;

    @PostConstruct
    private void init() {
        waitingRooms = new HashMap<>();
        int length = RoomType.values().length;
        LongStream.range(1, length + 1).forEach(i -> waitingRooms.put(i, new ConcurrentLinkedDeque<>()));
    }

    public int enter(Long roomId, String user) {
        waitingRooms.get(roomId).add(user);
        return waitingRooms.get(roomId).size();
    }

    public int leave(Long roomId, String user) {
        waitingRooms.get(roomId).remove(user);
        return waitingRooms.get(roomId).size();
    }

    public boolean isMatch(Long roomId, Integer matchCount) {
        return waitingRooms.get(roomId).size() >= matchCount;
    }

    public List<String> getUsers(Long roomId, Integer matchCount) {
        List<String> result = new ArrayList<>();
        ConcurrentLinkedDeque<String> users = waitingRooms.get(roomId);
        while (matchCount-- > 0) {
            result.add(users.pollFirst());
        }
        return result;
    }
}
