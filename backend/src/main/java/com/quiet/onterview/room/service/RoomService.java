package com.quiet.onterview.room.service;

import static com.quiet.onterview.room.RoomStatus.ENTER;
import static com.quiet.onterview.room.RoomStatus.FINISH;

import com.quiet.onterview.matching.MatchUser;
import com.quiet.onterview.matching.exception.TypeMismatchException;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import com.quiet.onterview.room.RoomStatus;
import com.quiet.onterview.room.dto.request.RoomProgressRequest;
import com.quiet.onterview.room.dto.response.RoomLeaveResponse;
import com.quiet.onterview.room.dto.response.RoomProgressResponse;
import com.quiet.onterview.room.dto.response.RoomProgressResponse.RoomProgressResponseBuilder;
import com.quiet.onterview.room.repository.RoomRepository;
import com.quiet.onterview.websocket.MessageAnnounce;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final String ROOM_PREFIX = "/client/answer/";
    private final RoomRepository roomRepository;
    private final MessageAnnounce messageService;

    public void generate(
            String sessionId,
            List<MatchUser> users,
            List<CommonQuestionResponse> questions
    ) {
        roomRepository.generate(sessionId, users, questions);
    }

    public void leave(String sessionId, String user) {
        int idx = roomRepository.findIndexByUser(sessionId, user);
        if (roomRepository.leave(sessionId, user)) {
            roomRepository.remove(sessionId);
        }
        messageService.announceAll(ROOM_PREFIX + sessionId, RoomLeaveResponse.builder().idx(idx).build());
    }

    public void process(String sessionId, RoomProgressRequest roomProgressRequest) {
        RoomStatus roomStatus = roomProgressRequest.getType();
        RoomProgressResponseBuilder progressResponseBuilder = RoomProgressResponse.builder()
                .type(roomStatus)
                .number(roomProgressRequest.getIndex());

        RoomStatus type = switch (roomProgressRequest.getType()) {
            case ENTER -> roomRepository.enter(sessionId);
            case START -> roomRepository.start(sessionId);
            case PROCEEDING, TIMEOUT -> roomRepository.proceeding(sessionId, roomStatus);
            default -> throw new TypeMismatchException();
        };

        if (type == null) {
            return;
        }

        progressResponseBuilder.type(type);
        progressResponseBuilder.number(roomProgressRequest.getIndex());
        if (type == ENTER || type == FINISH) {
            progressResponseBuilder.orders(roomRepository.shuffle(sessionId));
            progressResponseBuilder.question(roomRepository.getQuestion(sessionId));
        }
        messageService.announceAll(ROOM_PREFIX + sessionId, progressResponseBuilder.build());
    }

    public String getSessionId(String user) {
        return roomRepository.findRoomByUser(user);
    }
}
