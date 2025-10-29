package com.example.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository; // JPA 필수 등록
// Jpa를 상속 받아 Jpa가 제공하는 데이터 제어 기능(CRUD)과 Jpa가 이름만 보고 자동으로 메서드를 구현해줌
import org.springframework.stereotype.Repository; // 빈 등록
import com.example.demo.model.domain.TestDB; // 도메인 연동

// 리포지토리 클래스 추가 - Persistence layer 영속계층
// 데이터베이스 제어 기능 작성
// interface로 정의만 한 메서드를 JPA가 메서드 이름만 보고 자동으로 구현해줌

@Repository // 리포지토리 등록
public interface TestRepository extends JpaRepository<TestDB, Long>{
    // JPA가 제공하는 데이터 제어(CRUD) 기능 상속, TestDB: 엔티티 클래스, Long: 기본 키 타입

    // Find all TestDB entities by a name
    TestDB findByName(String name);
}
