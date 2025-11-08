package com.example.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository; // JPA 필수 등록
import org.springframework.stereotype.Repository; // 빈 등록
import com.example.demo.model.domain.Board; // 도메인 연동 - 엔티티 클래스

@Repository // 스프링 컨텍스트에 repository 객체 생성, 등록
public interface BoardRepository extends JpaRepository<Board, Long>{
    // JPA리포지토리 상속으로 모든 CRUD 기능 지원
    //List<Board> findAll();
}
