package com.quiet.onterview.room;

import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.interview.exception.InterviewQuestionNotFoundException;
import com.quiet.onterview.matching.MatchUser;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room {

    private List<MatchUser> users;
    private boolean[] isLeave;
    private boolean[] checked;
    private Integer questionIdx;
    private List<List<InterviewQuestionCreateResponse>> questions;


    public Room(List<MatchUser> users, List<List<InterviewQuestionCreateResponse>> questions) {
        this.users = users;
        this.checked = new boolean[users.size()];
        this.isLeave = new boolean[users.size()];
        this.questionIdx = 0;
        this.questions = questions;
    }

    public boolean count(int idx) {
        checked[idx] = true;
        return allChecked();
    }

    private boolean allChecked() {
        for (boolean c : checked) {
            if (!c) {
                return false;
            }
        }
        for (int i = 0; i < users.size(); i++) {
            checked[i] = isLeave[i];
        }
        return true;
    }

    public void leave(int idx) {
        isLeave[idx] = true;
    }

    public boolean isAllLeave() {
        for (boolean c : checked) {
            if (!c) {
                return false;
            }
        }
        return true;
    }

    public CommonQuestionResponse getQuestion() {
        InterviewQuestionCreateResponse interviewQuestionCreateResponse = questions
                .stream().findFirst().orElseThrow(InterviewQuestionNotFoundException::new)
                .get(questionIdx++);
        return CommonQuestionResponse.builder()
                .commonQuestionId(interviewQuestionCreateResponse.getCommonQuestionId())
                .CommonQuestion(interviewQuestionCreateResponse.getCommonQuestion())
                .build();
    }
}
