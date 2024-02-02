package com.quiet.onterview.community.service;

import com.quiet.onterview.community.dto.request.CommentPostRequest;
import com.quiet.onterview.community.dto.response.CommentPostResponse;

public interface CommentService {

    CommentPostResponse postComment(Long memberId, CommentPostRequest commentPostRequest);
}
