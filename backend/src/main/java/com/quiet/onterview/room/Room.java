package com.quiet.onterview.room;

import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.matching.MatchUser;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Room {

    private List<MatchUser> users;
    private Integer count;
    private Integer questionIdx;
    private List<CommonQuestionResponse> questions;

    public boolean count() {
        count = (count + 1) % users.size();
        return count == 0;
    }

    public CommonQuestionResponse getQuestion() {
        return questions.get(questionIdx++);
    }
}
