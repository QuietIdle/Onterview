package com.quiet.onterview.matching.service;

import com.quiet.onterview.matching.MatchingContext;
import com.quiet.onterview.matching.dto.request.MatchRequest;
import com.quiet.onterview.matching.step.MatchStep;
import com.quiet.onterview.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchService {

    private final MatchStep matchStep;
    private final JwtTokenProvider jwtTokenProvider;

    public MatchService(@Qualifier("beforeMatchStep") MatchStep matchStep, JwtTokenProvider jwtTokenProvider) {
        this.matchStep = matchStep;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void enter(MatchRequest matchRequest, String user) {
        MatchingContext matchingContext = new MatchingContext(matchRequest, user, jwtTokenProvider.getEmail(
                matchRequest.getAccessToken()));
        matchStep.process(matchingContext);
    }
}

