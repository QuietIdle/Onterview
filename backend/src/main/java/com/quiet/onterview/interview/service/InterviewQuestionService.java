package com.quiet.onterview.interview.service;

import com.quiet.onterview.interview.entity.InterviewQuestion;
import com.quiet.onterview.interview.entity.InterviewRoom;
import com.quiet.onterview.question.entity.CommonQuestion;

public interface InterviewQuestionService {

     InterviewQuestion createInterviewQuestion(InterviewRoom interviewRoom, CommonQuestion commonQuestion);
}