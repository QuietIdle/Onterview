package com.quiet.onterview.matching.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MatchRequest {

    private Integer roomId;
    private Integer matchCount;
}
