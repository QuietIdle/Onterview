package com.quiet.onterview.question.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommonQuestionRequest {

    Long commonQuestionFolderId;
    String commonQuestion;
}
