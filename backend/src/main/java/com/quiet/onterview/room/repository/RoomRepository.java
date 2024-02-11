package com.quiet.onterview.room.repository;

import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.matching.MatchUser;
import com.quiet.onterview.matching.exception.UserNotFoundException;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import com.quiet.onterview.room.Room;
import com.quiet.onterview.room.RoomStatus;
import com.quiet.onterview.room.exception.RoomNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository {

    private Map<String, Room> rooms = new ConcurrentHashMap<>();

    public void generate(
            String sessionId,
            List<MatchUser> users,
            List<CommonQuestionResponse> questions
    ) {
        Room room = new Room(users, 0, 0, questions);
        rooms.put(sessionId, room);
    }

    public RoomStatus start(String sessionId) {
        return rooms.get(sessionId).count() ? RoomStatus.START : null;
    }

    public RoomStatus enter(String sessionId) {
        return rooms.get(sessionId).count() ? RoomStatus.ENTER : null;
    }

    public RoomStatus proceeding(String sessionId, RoomStatus roomStatus) {
        boolean count = rooms.get(sessionId).count();
        if (allFinish(sessionId) && count) {
            return RoomStatus.END;
        }
        return count ? RoomStatus.FINISH : roomStatus;
    }

    private boolean allFinish(String sessionId) {
        Room room = rooms.get(sessionId);
        return room.getQuestions().size() == room.getQuestionIdx();
    }

    public Boolean leave(String sessionId, String user) {
        Room room = Optional.ofNullable(rooms.get(sessionId))
                .orElseThrow(RoomNotFoundException::new);
        try {
            room.getUsers().remove(MatchUser.builder().principal(user).build());
        } catch (Exception e) {
            throw new UserNotFoundException();
        }
        return room.getUsers().isEmpty();
    }

    public void remove(String sessionId) {
        try {
            rooms.remove(sessionId);
        } catch (Exception e) {
            throw new RoomNotFoundException();
        }
    }

    public String findRoomByUser(String user) {
        return rooms.keySet().stream()
                .filter(sessionId -> rooms.get(sessionId).getUsers()
                        .contains(MatchUser.builder().principal(user).build()))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
    }


    public int findIndexByUser(String sessionId, String user) {
        int idx = rooms.get(sessionId)
                .getUsers()
                .indexOf(
                        MatchUser.builder().principal(user).build()
                );
        if (idx == -1) {
            throw new UserNotFoundException();
        }
        return idx;
    }

    public List<Integer> shuffle(String sessionId) {
        int size = rooms.get(sessionId).getUsers().size();
        List<Integer> result = IntStream.range(0, size).boxed().collect(Collectors.toList());
        Collections.shuffle(result);
        return result;
    }

    public CommonQuestionResponse getQuestion(String sessionId) {
        return rooms.get(sessionId).getQuestion();
    }
}
