package com.quiet.onterview.matching.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String SUB_PREFIX = "/sub/";

    public void announceAll(Integer roomId, String message) {
        simpMessagingTemplate.convertAndSend(SUB_PREFIX + roomId, message);
    }

    public void announceToUser(Integer roomId, String user, String message) {
        simpMessagingTemplate.convertAndSendToUser(user, SUB_PREFIX + roomId, message);
    }
}

