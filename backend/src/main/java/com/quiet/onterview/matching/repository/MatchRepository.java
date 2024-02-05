package com.quiet.onterview.matching.repository;

import com.quiet.onterview.interview.entity.RoomType;
import com.quiet.onterview.matching.exception.UserNotFoundException;
import jakarta.annotation.PostConstruct;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MatchRepository {

    private int roomSize;
    private Map<Integer, ArrayDeque<String>> waitingRooms;
    private Set<String> completionRoom;

    @PostConstruct
    private void init() {
        waitingRooms = new HashMap<>();
        roomSize = RoomType.values().length;
        IntStream.range(1, roomSize + 1)
                .forEach(i -> waitingRooms.put(i, new ArrayDeque<>()));
        completionRoom = new TreeSet<>();
    }

    public int enter(Integer roomId, String user) {
        waitingRooms.get(roomId).add(user);
        return waitingRooms.get(roomId).size();
    }

    public void saveComplete(String user) {
        completionRoom.add(user);
    }

    public boolean findComplete(String user) {
        return completionRoom.stream().anyMatch(user::equals);
    }
    public void deleteComplete(String user) {
        completionRoom.remove(user);
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
        Deque<String> users = waitingRooms.get(roomId);
        while (matchCount-- > 0) {
            result.add(users.pollFirst());
        }
        return result;
    }
}
