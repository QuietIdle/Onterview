package com.quiet.onterview.question.repository;

import com.quiet.onterview.question.entity.CommonQuestionFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommonQuestionFolderRepository extends JpaRepository<CommonQuestionFolder, Long> {

    @Query("SELECT cqf FROM CommonQuestionFolder cqf")
    List<CommonQuestionFolder> findAllCommonQuestionFolderInfo();

    @Query("SELECT cqf FROM CommonQuestionFolder cqf where cqf.commonQuestionFolderId = :commonQuestionFolderId")
    CommonQuestionFolder findOneCommonQuestionFolderInfo(Long commonQuestionFolderId);
}
