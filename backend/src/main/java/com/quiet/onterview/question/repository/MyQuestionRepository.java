package com.quiet.onterview.question.repository;

import com.quiet.onterview.question.entity.MyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyQuestionRepository extends JpaRepository<MyQuestion, Long> {

    @Query("SELECT mq FROM MyQuestion mq WHERE mq.myQuestionId = :myQuestionId")
    MyQuestion findMyAnswerAndVideo(@Param("myQuestionId") Long myQuestionId);
}
