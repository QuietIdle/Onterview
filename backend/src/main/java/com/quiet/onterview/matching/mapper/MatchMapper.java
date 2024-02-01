package com.quiet.onterview.matching.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiet.onterview.matching.dto.response.MatchTokenResponse;
import com.quiet.onterview.matching.dto.response.MatchWaitingResponse;
import com.quiet.onterview.matching.exception.CanNotGenerateException;
import com.quiet.onterview.matching.exception.CanNotWriteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class MatchMapper {

    private final ObjectMapper objectMapper;

    public String waitingResponseToJson(MatchWaitingResponse matchWaitingResponse) {
        try {
            return objectMapper.writeValueAsString(matchWaitingResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CanNotWriteException();
        }
    }
    public String tokenResponseToJson(MatchTokenResponse matchTokenResponse) {
        try {
            return objectMapper.writeValueAsString(matchTokenResponse);
        } catch (JsonProcessingException e) {
            throw new CanNotWriteException();
        }
    }


    public MatchWaitingResponse toWaitingResponse(int waiting) {
        return MatchWaitingResponse.builder()
                .waiting(waiting)
                .build();
    }

    public MatchTokenResponse toTokenResponse(String token) {
        return MatchTokenResponse.builder()
                .token(token)
                .build();
    }
}
