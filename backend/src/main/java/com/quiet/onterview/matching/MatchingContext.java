package com.quiet.onterview.matching;

import com.quiet.onterview.matching.dto.request.MatchRequest;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import java.security.Principal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MatchingContext {

    private final Integer roomId;
    private final MatchUser matchUser;
    private final Integer matchCount;

    @Setter
    private String sessionId;
    @Setter
    private List<MatchUser> matchUsers;
    @Setter
    private List<CommonQuestionResponse> questions;

    public MatchingContext(MatchRequest matchRequest, String principal, Long memberId) {
        this.roomId = matchRequest.getRoomId();
        this.matchUser = MatchUser.builder().memberId(memberId).principal(principal).build();
        this.matchCount = matchRequest.getMatchCount();
        this.sessionId = null;
        this.matchUsers = null;
    }
}
