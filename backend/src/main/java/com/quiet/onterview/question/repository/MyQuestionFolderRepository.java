package com.quiet.onterview.question.repository;

import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.question.entity.MyQuestionFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyQuestionFolderRepository extends JpaRepository<MyQuestionFolder, Long> {

    @Query("SELECT mqf FROM MyQuestionFolder mqf WHERE mqf.member = :memberId")
    List<MyQuestionFolder> findMyQuestionFolder(@Param("memberId") Member member);
}
