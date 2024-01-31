package com.quiet.onterview.question.repository;

import com.quiet.onterview.question.entity.MyQuestionFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyQuestionFolderRepository extends JpaRepository<MyQuestionFolder, Long> {

    @Query("SELECT mqf FROM MyQuestionFolder mqf where mqf.member.memberId = :memberId")
    List<MyQuestionFolder> findMyQuestionFolder(Long memberId);

}
