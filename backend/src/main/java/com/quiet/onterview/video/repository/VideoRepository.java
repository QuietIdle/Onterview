package com.quiet.onterview.video.repository;

import com.quiet.onterview.question.entity.MyQuestion;
import com.quiet.onterview.video.entity.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findAllByMyQuestion(MyQuestion myQuestion);
}
