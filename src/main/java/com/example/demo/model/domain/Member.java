package com.example.demo.model.domain;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
// "로그인이 안되던 근본적 이유", 기본생성자가 없었기 때문.
// Member 엔티티에는 Builder 생성자가 존재 -> 명시적 생성자 생성으로 기본 생성자 사라짐
// 회원가입은 DTO request.toEntity()로 'builder 생성자'로 DB에 데이터를 저장하므로 Error 없음
// 하지만 로그인은 findByEmail()로 Member를 반환, JPA는 리플렉션으로 DB 데이터를 엔티티로 생성하는데 '기본생성자'가 없어서 Error 발생
// 그런데 기본생성자로 인자를 받지 않은 채 어떻게 DB 데이터를 엔티티에 담을까? -> 리플렉션 : 필드에 직접 접근해 값 저장, 따라서 Builder, 인자있는 생성자가 아닌 기본생성자만 필요
// Builder가 없다면 암묵적 기본생성자로 로그인 가능(대신 회원가입때 에러), Builder가 있으므로 기본생성자는 사라지므로 직접 @NoArgsConstructor 필요!!
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 1씩 증가
    @Column(name = "id", updatable = false) // 수정 x
    private Long id;
    @Column(name = "name", nullable = false) // null x
    private String name = "";
    @Column(name = "email", unique = true, nullable = false) // unique 중복 x
    private String email = "";
    @Column(name = "password", nullable = false)
    private String password = "";
    @Column(name = "age", nullable = false)
    private String age = "";
    @Column(name = "mobile", nullable = false)
    private String mobile = "";
    @Column(name = "address", nullable = false)
    private String address = "";

    @Builder // 생성자에 빌더 패턴 적용(불변성)
    public Member(String name, String email, String password, String age, String mobile, String address){
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
    }
}
