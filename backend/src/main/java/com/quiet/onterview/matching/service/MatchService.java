package com.quiet.onterview.matching.service;

import com.quiet.onterview.matching.dto.request.MatchRequest;

public interface MatchService {
    void enter(MatchRequest matchRequest, String user);
    void match(MatchRequest matchRequest);
}
