package com.quiet.onterview.matching.service;

import com.quiet.onterview.matching.dto.request.MatchRequest;
import com.quiet.onterview.matching.dto.response.MatchWaitingResponse;
import java.util.List;

public interface MatchService {
    void enter(MatchRequest matchRequest, String user);
    void leave(MatchRequest matchRequest, String user);
    void match(MatchRequest matchRequest);
}
