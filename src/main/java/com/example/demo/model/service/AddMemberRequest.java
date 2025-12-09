package com.example.demo.model.service;
 
import com.example.demo.model.domain.Member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor // 기본 생성자 추가
// @AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Data // getter, setter, toString, equals 등 자동 생성
public class AddMemberRequest {
    
    @NotBlank
    @Pattern(
        regexp = "^[a-zA-Z가-힣]+$", 
        message = "이름에는 숫자와 특수문자를 사용할 수 없습니다"
    )
    private String name;

    @NotBlank
    @Email
    private String email;

    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z]+$",
        message = "비밀번호는 영문이고 대문자와 소문자를 포함해야합니다"
    )
    @Size(min = 8)
    private String password;

    @Max(value = 90)
    @Min(value = 19)
    private String age;

    @NotEmpty
    private String mobile;
    @NotEmpty
    private String address;

    public Member toEntity(){ // Member 생성자를 통해 객체 생성
        return Member.builder()
        .name(name)
        .email(email)
        .password(password)
        .age(age)
        .mobile(mobile)
        .address(address)
        .build();
    }
}
