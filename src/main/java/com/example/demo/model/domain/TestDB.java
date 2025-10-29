package com.example.demo.model.domain;

import jakarta.persistence.*; // javax의 후속보전,
// JPA 관련 어노테이션 사용 가능(entity, table, id, generatedvalue, column 등)
import lombok.Data; 
// 롬복 라이브러리의 data 어노테이션 사용 가능

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

    // JPA는 리플렉션으로 객체를 생성하는데 이때 반드시 기본생성자가 필요
    // 생성자가 없으면 자바가 기본 생성자를 자동으로 생성
    // 만약 중복 생성자를 명시적으로 정의하면 암묵적 기본생성자는 사라져서 페이지 에러

    // TestDB(Long id, String name){
    //     this.id = id;
    //     this.name = name;
    // }
}


