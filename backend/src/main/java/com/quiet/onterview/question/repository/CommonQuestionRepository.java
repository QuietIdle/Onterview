package com.quiet.onterview.question.repository;

import com.quiet.onterview.question.entity.CommonQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonQuestionRepository extends JpaRepository<CommonQuestion, Long> {

}
