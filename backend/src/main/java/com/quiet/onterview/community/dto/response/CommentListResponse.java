package com.quiet.onterview.community.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentListResponse {

    Long commentCount;
    List<CommentObjectResponse> comments;
}
