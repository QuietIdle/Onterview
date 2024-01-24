package com.quiet.onterview.question.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyQuestionRequest {

    Long myQuestionFolderId;
    String question;
    Long commonQuestionId;
}
