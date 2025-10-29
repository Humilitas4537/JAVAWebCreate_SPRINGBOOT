// package com.example.demo.controller;

// import com.example.demo.model.domain.Article;
// import com.example.demo.model.service.AddArticleRequest;
// import com.example.demo.model.service.BlogService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity; 
// import org.springframework.web.bind.annotation.*;

// @RequiredArgsConstructor // final 또는 NonNull 필드의 생성자 자동 생성 : 생성자 (로 객체)주입
// @RestController // @Controller + @ResponseBody
// public class BlogRestController {
//     private final BlogService blogService;

//     @PostMapping("/api/articles") // post 요청
//     public ResponseEntity<Article> addArticle(@ModelAttribute AddArticleRequest request) { // 아직 없음(에러)
//         Article saveArticle = blogService.save(request); // 게시글 저장
//         // ResponseEntity<T> : HTTP 응답 전체를 표현 = 상태코드 + header + body, <T>는 body 타입을 의미
//         return ResponseEntity.status(HttpStatus.CREATED) // 상태 코드 및 게시글 정보 반환
//             .body(saveArticle);
//     }


//     @GetMapping("/favicon.ico")
//         public void favicon() {
//         // 아무 작업도 하지 않음
//     }
// }
