package com.quiet.onterview.matching.handler;

import com.quiet.onterview.matching.StompUser;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Component
@Slf4j
public class CustomHandShakeHandler extends DefaultHandshakeHandler {


    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        String sessionId = UUID.randomUUID().toString();
        log.info("handshake sessionId : {}", sessionId);
        return new StompUser(sessionId);
    }


}
