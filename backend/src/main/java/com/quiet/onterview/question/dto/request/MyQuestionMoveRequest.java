package com.quiet.onterview.question.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyQuestionMoveRequest {

    Long myQuestionId;
    Long fromMyQuestionFolderId;
    Long toMyQuestionFolderId;
}
