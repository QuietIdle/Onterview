![Frame](https://github.com/QuietIdle/Onterview/assets/83208807/545dccd2-b0eb-4dbd-9d74-c6e486df1814)


1. [서비스 개요](#1-서비스-개요)
1. [기술 스택](#2-기술-스택)
1. [한눈에 보는 시스템 아키텍처](#3-한눈에-보는-시스템-아키텍처)
1. [BackEnd Structure](#4-backend-structure)
1. [FrontEnd Structure](#5-frontend-structure)
1. [ERD](#6-erd)
1. [API 명세서](#7-API명세서)
1. [온터뷰 사용설명서](#8-온터뷰 사용설명서)


## 1. 서비스 개요
자주 등장하는 면접 문항에 대한 답변을 준비하고 싶어

여러 사람들 앞에서 말하는 상황을 경험하고 싶어

면접 프로세스를 경험하며 면접 전형 과정에 익숙해지고 싶어

내가 잘 준비하고 있는지 다른 사람들의 피드백이 궁금해...

**혼자 면접을 준비하는 취업준비생들의 든든한 면접 파트너, 온터뷰**

## 2. 기술 스택

### IDE
<img src="https://img.shields.io/badge/intellij idea-000000?style=for-the-badge&logo=intellij idea&logoColor=white"> <img src="https://img.shields.io/badge/visual studio code-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white">

### Frontend
<img src="https://img.shields.io/badge/node.js 20.11.0-339933?style=for-the-badge&logo=node.js&logoColor=white"> <img src="https://img.shields.io/badge/axios 1.6.5-5A29E4?style=for-the-badge&logo=axios&logoColor=white"> <img src="https://img.shields.io/badge/openvidu browser 2.29.1-000000?style=for-the-badge&logo=&logoColor=white">
<img src="https://img.shields.io/badge/sass-CC6699?style=for-the-badge&logo=sass&logoColor=white"> <img src="https://img.shields.io/badge/vue 3.3.11-4FC98D?style=for-the-badge&logo=vue&logoColor=white"> <img src="https://img.shields.io/badge/vuetify 3.4.10-1867C0?style=for-the-badge&logo=vuetify&logoColor=white">


### Backend
<img src="https://img.shields.io/badge/java 17-E84135?style=for-the-badge&logo=y&logoColor=white"> <img src="https://img.shields.io/badge/spring boot 3.2.1-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white"> <img src="https://img.shields.io/badge/spring data jpa 3.2.1-6DB33F?style=for-the-badge&logo=t&logoColor=white"> <img src="https://img.shields.io/badge/spring security 6.2.1-6DB33F?style=for-the-badge&logo=spring security&logoColor=white"> <img src="https://img.shields.io/badge/spring security messaging 6.2.1-6DB33F?style=for-the-badge&logo=spring security&logoColor=white"> <img src="https://img.shields.io/badge/swagger 2.0.2-5EA2D?style=for-the-badge&logo=spring security&logoColor=white"> <img src="https://img.shields.io/badge/jjwt 0.8.0-000000?style=for-the-badge&logo=y&logoColor=white"> <img src="https://img.shields.io/badge/mysql 8.0.35-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/openvidu 2.29.0-000000?style=for-the-badge&logo=&logoColor=white"> <img src="https://img.shields.io/badge/Spring webflux 6.1.2-000000?style=for-the-badge&logo=&logoColor=white"> <img src="https://img.shields.io/badge/Spring websocket 6.1.2-000000?style=for-the-badge&logo=&logoColor=white"> <img src="https://img.shields.io/badge/querydsl 5.0.0-000000?style=for-the-badge&logo=&logoColor=white">

### File Server
<img src="https://img.shields.io/badge/java 17-E84135?style=for-the-badge&logo=y&logoColor=white"> <img src="https://img.shields.io/badge/spring boot 3.2.1-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white"> <img src="https://img.shields.io/badge/spring security 6.2.1-6DB33F?style=for-the-badge&logo=spring security&logoColor=white"> <img src="https://img.shields.io/badge/ffmpeg 0.8.0-007808?style=for-the-badge&logo=ffmpeg&logoColor=white"> <img src="https://img.shields.io/badge/jjwt 0.8.0-000000?style=for-the-badge&logo=y&logoColor=white">

### Infra
<img src="https://img.shields.io/badge/jenkins 2.426.3-D24939?style=for-the-badge&logo=jenkins&logoColor=white"> <img src="https://img.shields.io/badge/docker 25.0.0-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/nginx 1.25.3-009639?style=for-the-badge&logo=nginx&logoColor=white"> <img src="https://img.shields.io/badge/aws ec2-FF9900?style=for-the-badge&logo=amazon ec2&logoColor=white">

### Version control
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/gitlab-FC6D26?style=for-the-badge&logo=gitlab&logoColor=white">


### Management Tools
<img src="https://img.shields.io/badge/jira software-0052CC?style=for-the-badge&logo=jira software&logoColor=white"> <img src="https://img.shields.io/badge/mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white"> <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"> <img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white">


## 3. 한눈에 보는 시스템 아키텍처
![system_architecture](https://github.com/QuietIdle/Onterview/assets/83208807/f7001998-4240-4555-ba31-9f8b4952c599)

## 4. BackEnd Structure

```

├── backend
│   ├── gradle
│   │   └── wrapper
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── quiet
│       │   │           └── onterview
│       │   │               ├── common
│       │   │               ├── community
│       │   │               │   ├── controller
│       │   │               │   ├── dto
│       │   │               │   │   ├── request
│       │   │               │   │   └── response
│       │   │               │   ├── entity
│       │   │               │   ├── mapper
│       │   │               │   ├── repository
│       │   │               │   │   └── querydsl
│       │   │               │   └── service
│       │   │               ├── file
│       │   │               ├── interview
│       │   │               ├── matching
│       │   │               ├── member
│       │   │               ├── question
│       │   │               ├── room
│       │   │               ├── security
│       │   │               ├── utils
│       │   │               ├── video
│       │   │               └── websocket
│       │   │                   ├── config
│       │   │                   ├── handler
│       │   │                   ├── interceptor
│       │   │                   └── mapper
│       │   └── resources
│       └── test
│           └── java
│               └── com
│                   └── quiet
│                       └── onterview
```
```
.
└── onterview-storage
    ├── gradle
    │   └── wrapper
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── quiet
        │   │           └── onterviewstorage
        │   │               ├── auth
        │   │               ├── file
        │   │               │   ├── controller
        │   │               │   ├── dto
        │   │               │   └── service
        │   │               ├── global
        │   │               └── util
        │   └── resources
        └── test
            └── java
                └── com
                    └── quiet
                        └── onterviewstorage

```

## 5. FrontEnd Structure

```
FrontEnd
.
├── frontend
│   ├── public
│   └── src
│       ├── api
│       ├── assets
│       ├── components
│       │   ├── common
│       │   ├── community
│       │   │   └── modal
│       │   ├── interview
│       │   ├── question
│       │   │   └── modal
│       │   ├── selfSpeech
│       │   ├── storage
│       │   ├── user
│       │   └── video
│       ├── router
│       ├── stores
│       └── views
```

## 6. ERD
![ERD](https://github.com/QuietIdle/Onterview/assets/83208807/31ca0d30-6f28-4d58-bec9-4a15e3f6cfc9)

## 7. API 명세서
![image_143](https://github.com/QuietIdle/Onterview/assets/83208807/e9103b26-c74b-4981-8a75-8da46a998b00)

[API 명세서 확인하러 가기](https://meenyweeny.notion.site/API-cce33e1f591d46e79f0f6c2bd2db7d80?pvs=4)

## 8. 온터뷰 사용 설명서
![1](https://github.com/QuietIdle/Onterview/assets/83208807/f9c4a5d1-a015-4081-b2c7-d8bcce423449)
![2](https://github.com/QuietIdle/Onterview/assets/83208807/8f18aa6b-ed7b-4417-ae68-8c9391da3fb2)
![3](https://github.com/QuietIdle/Onterview/assets/83208807/fcf6afa8-9da5-4bec-accb-67a770fa474e)
![4](https://github.com/QuietIdle/Onterview/assets/83208807/66620a24-4ed9-4e44-b32b-df12018c4f66)
![5](https://github.com/QuietIdle/Onterview/assets/83208807/67b02204-dc25-4de5-8e21-975501a4aaa3)
![6](https://github.com/QuietIdle/Onterview/assets/83208807/27691ab4-5cf0-4cd7-b91d-b82c317b8fdf)
![7](https://github.com/QuietIdle/Onterview/assets/83208807/addfb536-c2df-4ace-8418-1f663d555674)
![8](https://github.com/QuietIdle/Onterview/assets/83208807/ea8948f4-acdf-41b2-80f4-5d7d751aa83f)
![9](https://github.com/QuietIdle/Onterview/assets/83208807/484c3eea-5580-439b-affb-9275d122f95c)
![10](https://github.com/QuietIdle/Onterview/assets/83208807/de5bb2c1-e790-4a8d-aba6-36aba7d5aa53)
![11](https://github.com/QuietIdle/Onterview/assets/83208807/fa90c313-8f24-4412-a09a-214d06f472b1)
![12](https://github.com/QuietIdle/Onterview/assets/83208807/1bb3b15b-bd54-4f02-8b50-d3fdfd6b6c15)
![13](https://github.com/QuietIdle/Onterview/assets/83208807/3141e1b7-590a-4e3a-8c9b-546ec79d88f0)
![14](https://github.com/QuietIdle/Onterview/assets/83208807/208054a9-c343-4904-9f6b-a385594bb970)
![15](https://github.com/QuietIdle/Onterview/assets/83208807/f8f600f8-198c-4bbc-ba2b-03f0fb19c911)
![16](https://github.com/QuietIdle/Onterview/assets/83208807/c464bf9e-264f-4472-b3c6-398f865070d7)
![17](https://github.com/QuietIdle/Onterview/assets/83208807/4c979e19-341b-4951-a696-8cf942b63824)
![18](https://github.com/QuietIdle/Onterview/assets/83208807/10f6ba0d-b499-4d89-bccb-60795b4f0d5e)
![19](https://github.com/QuietIdle/Onterview/assets/83208807/0fb42e64-dc08-42fc-a076-9292517076e8)
![20](https://github.com/QuietIdle/Onterview/assets/83208807/d0d4ef55-6cb0-471d-a1ce-21ca0a0bdb23)
![21](https://github.com/QuietIdle/Onterview/assets/83208807/a09ea9dd-d680-442b-8f66-5b886ef224ba)
![22](https://github.com/QuietIdle/Onterview/assets/83208807/ea9485b8-4aef-451d-9aac-94131e50941e)
![23](https://github.com/QuietIdle/Onterview/assets/83208807/55638b9d-34af-4e4a-be44-99e14fff9850)
