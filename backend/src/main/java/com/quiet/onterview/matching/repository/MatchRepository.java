package com.quiet.onterview.matching.repository;

import com.quiet.onterview.interview.entity.RoomType;
import com.quiet.onterview.matching.exception.UserNotFoundException;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MatchRepository {

    private int roomSize;
    private Map<Integer, ConcurrentLinkedDeque<String>> waitingRooms;

    @PostConstruct
    private void init() {
        waitingRooms = new HashMap<>();
        roomSize = RoomType.values().length;
        IntStream.range(1, roomSize + 1)
                .forEach(i -> waitingRooms.put(i, new ConcurrentLinkedDeque<>()));
    }

    public int enter(Integer roomId, String user) {
        waitingRooms.get(roomId).add(user);
        return waitingRooms.get(roomId).size();
    }

    public int leave(Integer roomId, String user) {
        waitingRooms.get(roomId).remove(user);
        return waitingRooms.get(roomId).size();
    }

    public boolean isMatch(Integer roomId, Integer matchCount) {
        return waitingRooms.get(roomId).size() >= matchCount;
    }

    public int findRoom(String user) {
        return IntStream.rangeClosed(1, RoomType.values().length)
                .filter(i -> waitingRooms.get(i).contains(user))
                .findFirst().orElseThrow(UserNotFoundException::new);
    }

    public List<String> getUsers(Integer roomId, Integer matchCount) {
        List<String> result = new ArrayList<>();
        ConcurrentLinkedDeque<String> users = waitingRooms.get(roomId);
        while (matchCount-- > 0) {
            result.add(users.pollFirst());
        }
        return result;
    }
}
