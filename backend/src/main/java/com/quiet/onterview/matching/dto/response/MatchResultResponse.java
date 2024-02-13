package com.quiet.onterview.matching.dto.response;

import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.interview.dto.response.InterviewQuestionResponse;
import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.interview.repository.InterviewQuestionRepository;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchResultResponse {
    String sessionId;
    String token;
    Integer index;
}
