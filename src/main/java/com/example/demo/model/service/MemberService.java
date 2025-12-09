package com.example.demo.model.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;

import com.example.demo.model.domain.Member;
import com.example.demo.model.repository.MemberRepository;

import jakarta.validation.Valid;

@Service
@Validated // 모든 메서드의 매개변수 Validation 어노테이션 검증
@Transactional // 트랜잭션 처리(클래스 내 모든 메소드 대상)
@RequiredArgsConstructor // final 또는 NonNull 필드의 생성자 자동 생성 : 생성자 (로 객체)주입
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // 스프링 버전 5 이후, 단방향 해싱 알고리즘 지원 : salt(무작위) + password => hashFunction => 해시값
    
    // 대표적인 표준 예외
    // IllegalArgumentException : 잘못된 인자가 전달되었을 때 오류, 사용자 오류
    // IllegalStateException : 잘못된 상태에서 메서드가 호출 되었을 때 오류, 시스템 오류

    private void validateDuplicateMember(AddMemberRequest request){
        Member findMember = memberRepository.findByEmail(request.getEmail()); // 이메일 존재 유무
        if(findMember != null){
            // 에러가 잡힐 때까지 위로 계속 던짐
            // vaildateDuplicateMember() -> saveMember() -> controller.addmembers() -> spring dispatcherServlet => 화이트라벨 에러 처리
            throw new IllegalStateException("이미 가입된 회원입니다."); // 예외처리
        }
    }

    public Member saveMember(@Valid AddMemberRequest request){
        validateDuplicateMember(request); // 이메일 체크
        
        String encodedPassword = passwordEncoder.encode(request.getPassword()); // 비밀번호 암호화
        request.setPassword(encodedPassword); // 암호화된 비밀번호 설정
        return memberRepository.save(request.toEntity());
    }


    public Member loginCheck(String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email); // 이메일 조회
        if (member == null) {
            throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
        }
        if (!passwordEncoder.matches(rawPassword, member.getPassword())) { // 비밀번호 확인
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return member; // 인증 성공 시 회원 객체 반환
    }
}
