package com.quiet.onterview.matching.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MatchRequest {

    public enum MessageType {
        ENTER, MATCH, LEAVE
    }

    private MessageType type;
    private Long roomId;
    private Integer matchCount;
}
