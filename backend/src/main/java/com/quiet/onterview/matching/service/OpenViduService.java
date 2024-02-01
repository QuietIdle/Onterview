package com.quiet.onterview.matching.service;

import com.quiet.onterview.matching.exception.CanNotGenerateException;
import io.openvidu.java.client.Connection;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import org.springframework.stereotype.Service;

@Service
public class OpenViduService {

    private final String OPEN_VIDU_HOSTNAME = "http://localhost:4443";
    private final String OPEN_VIDU_SECRET = "onterview";
    private OpenVidu openVidu = new OpenVidu(OPEN_VIDU_HOSTNAME, OPEN_VIDU_SECRET);


    public String getToken(String sessionId) {
        Session activeSession = openVidu.getActiveSession(sessionId);
        try {
            if (activeSession == null) {
                activeSession = openVidu.createSession(
                        new SessionProperties.Builder().customSessionId(sessionId).build());
            }
            Connection connection = activeSession.createConnection();
            return connection.getToken();
        } catch (OpenViduJavaClientException e) {
            throw new CanNotGenerateException();
        } catch (OpenViduHttpException e) {
            throw new CanNotGenerateException();
        }
    }
}
