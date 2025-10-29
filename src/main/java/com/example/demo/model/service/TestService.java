package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // 빈 등록
import com.example.demo.model.domain.TestDB;
import com.example.demo.model.repository.TestRepository;
import lombok.RequiredArgsConstructor;

// 서비스 클래스 추가 - Service layer 비지니스 계층
// 주요 기능(로직) 작성

@Service // 서비스 등록, 스프링 내부 자동 등록됨
@RequiredArgsConstructor // final 또는 NonNull 필드의 생성자 자동 생성 : 생성자 (로 객체)주입
public class TestService{
    @Autowired // 객체 의존성 주입 DI(컨테이너 내부 등록) : 필드 (로 객체)주입
    // 직접 new TestRepository() 하지 않아도 스프링이 자동으로 객체 생성해 넣어줌 = 의존성 주입
    private TestRepository testRepository;

    public TestDB findByName(String name){ // 이름 찾기
        return (TestDB) testRepository.findByName(name);
    }
}