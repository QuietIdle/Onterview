package com.quiet.onterview.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "LIKES")
public class Likes {

    @EmbeddedId
    private LikesPrimaryKey likesPrimaryKey;

    @Column(name="STATUS", nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("1")
    private Boolean status;
}