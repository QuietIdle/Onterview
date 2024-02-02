package com.quiet.onterview.community.repository;

import com.quiet.onterview.community.entity.Likes;
import com.quiet.onterview.community.entity.LikesPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, LikesPrimaryKey> {

}
