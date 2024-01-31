package com.quiet.onterview.question.repository;

import com.quiet.onterview.question.entity.CommonQuestionFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommonQuestionFolderRepository extends JpaRepository<CommonQuestionFolder, Long> {

    @Query("SELECT cqf FROM CommonQuestionFolder cqf")
    List<CommonQuestionFolder> findAllCommonQuestionFolderInfo();

    @Query("SELECT cqf FROM CommonQuestionFolder cqf WHERE cqf.commonQuestionFolderId = :commonQuestionFolderId")
    Optional<CommonQuestionFolder> findOneCommonQuestionFolderInfo(Long commonQuestionFolderId);

    @Query("SELECT cqf FROM CommonQuestionFolder cqf WHERE cqf.commonQuestionFolder = :commonQuestionFolderName")
    Optional<CommonQuestionFolder> findInterviewQuestionFolder(String commonQuestionFolderName);
}
