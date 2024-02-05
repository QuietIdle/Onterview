package com.quiet.onterview.member.entity;

import com.quiet.onterview.community.entity.Article;
import com.quiet.onterview.community.entity.Comment;
import com.quiet.onterview.community.entity.Likes;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@ToString
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NICKNAME", unique = true, nullable = true)
    private String nickname;

    @Column(name = "IMAGE_URL", nullable = true)
    private String imageUrl;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Article> articleList = new ArrayList<>();

    @OneToMany(mappedBy = "likesPrimaryKey.member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Likes> likesList = new ArrayList<>();
}
