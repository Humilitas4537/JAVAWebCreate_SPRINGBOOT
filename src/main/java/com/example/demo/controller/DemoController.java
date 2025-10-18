package com.example.demo.controller;

import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.TestService; // 최상단 서비스 클래스 연동 추가

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller // 컨트롤러 어노테이션 = View를 제공하는 컨트롤러로 설정. 
// 매핑핸들러가 @Controller가 붙은 클래스에서 매핑을 찾아 url 요청을 처리
public class DemoController {
    // 클래스 하단 작성
    @Autowired
    TestService testService; // DemoController 클래스 아래 객체 생성

    @GetMapping("/hello") // 전송 방식 GET
    public String hello(Model model) {
        model.addAttribute("data", "방갑습니다."); // model 설정
        // 모델은 페이지에 그려질 정보로써 View에 데이터를 키 값으로 전달
        return "hello"; // viewName 반환
        // 자동으로 ViewResolver가 .html로 연결하여 템플릿 폴더에서 찾음
    }

    @GetMapping("/hello2")
    public String hello2(Model model) {
        model.addAttribute("data1", "model로 페이지에 그릴 정보를 전달합니다.");
        model.addAttribute("data2", "model은 key-value 값으로,");
        model.addAttribute("data3", "key를 이용해 html에 값을 출력합니다.");
        model.addAttribute("data4", "사용법은 th:text='${key}'");
        model.addAttribute("data5", "${}는 변수 표현식으로 {}안에 key를 넣습니다.");
        return "hello2";
    }
    

    @GetMapping("/about_detailed")
    public String about() {
        return "about_detailed";
    }
    
    @GetMapping("/test1")
    public String thymeleaf_test1(Model model) {
        model.addAttribute("data1", "<h2> 방갑습니다 </h2>");
        model.addAttribute("data2", "태그의 속성 값");
        model.addAttribute("link", 01);
        model.addAttribute("name", "홍길동");
        model.addAttribute("para1", "001");
        model.addAttribute("para2", 002);
        return "thymeleaf_test1";
    }
    
    // 하단에 맵핑 이어서 추가
    @GetMapping("/testdb")
    public String getAllTestDBs(Model model) {
        TestDB test = testService.findByName("홍길동");
        model.addAttribute("data4", test);
        System.out.println("데이터 출력 디버그 : " + test);
        return "testdb";
    }
} 
