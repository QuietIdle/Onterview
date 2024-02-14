package com.quiet.onterview.room;

import com.quiet.onterview.interview.dto.response.InterviewQuestionCreateResponse;
import com.quiet.onterview.interview.exception.InterviewQuestionNotFoundException;
import com.quiet.onterview.matching.MatchUser;
import com.quiet.onterview.question.dto.response.CommonQuestionResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceArray;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room {

    private List<MatchUser> users;
    private AtomicReferenceArray<Boolean> isLeave;
    private AtomicReferenceArray<Boolean> checked;
    private Integer questionIdx;
    private List<List<InterviewQuestionCreateResponse>> questions;


    public Room(List<MatchUser> users, List<List<InterviewQuestionCreateResponse>> questions) {
        this.users = users;
        this.checked = new AtomicReferenceArray<>(users.size());
        this.isLeave = new AtomicReferenceArray<>(users.size());
        this.questionIdx = 0;
        this.questions = questions;
        for (int i = 0; i < users.size(); i++) {
            checked.set(i, false);
            isLeave.set(i, false);
        }
    }

    public boolean count(int idx) {
        checked.getAndSet(idx, true);
        return allChecked();
    }

    private boolean allChecked() {
        for (int i = 0; i < users.size(); i++) {
            if (!checked.get(i)) {
                return false;
            }
        }
        for (int i = 0; i < users.size(); i++) {
            checked.set(i, isLeave.get(i));
        }
        return true;
    }

    public void leave(int idx) {
        isLeave.set(idx, true);
    }

    public boolean isAllLeave() {
        for (int i = 0; i < users.size(); i++) {
            if (!isLeave.get(i)) {
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
