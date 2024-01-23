package com.quiet.onterview.question.repository;

import com.quiet.onterview.question.entity.CommonQuestion;
import com.quiet.onterview.question.entity.CommonQuestionFolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonQuestionFolderRepository extends JpaRepository<CommonQuestionFolder, Long> {

}
