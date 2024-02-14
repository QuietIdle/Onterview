package com.quiet.onterview.question.repository;

import com.quiet.onterview.question.entity.MyQuestionFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyQuestionFolderRepository extends JpaRepository<MyQuestionFolder, Long> {

    @Query("SELECT mqf FROM MyQuestionFolder mqf WHERE mqf.member.memberId = :memberId")
    List<MyQuestionFolder> findMyQuestionFolder(Long memberId);

}
