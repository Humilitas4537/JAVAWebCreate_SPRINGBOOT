package com.example.demo.model.domain;

import jakarta.persistence.*;
import lombok.Data;

// 'TestDB' 엔티티 클래스(데이터베이스 테이블 구조)를 만든 것. - Persistence layer 영속계층에 해당

@Entity // TestDB라는 객체와 DB 테이블을 매핑. JPA가 관리
@Table(name = "testdb") // 매핑할 테이블 이름은 testdb
@Data // set/get/tostring 등 필수 어노테이션 자동 생성
public class TestDB{
    @Id // 해당 변수가 PK(기본키)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이 없어도 자동으로 생성(자동증가 = 1,2,3..)
    private Long id;

    @Column(nullable = true) // 테이블의 컬럼 설정 값(제약조건) 명시
    private String name;
}


