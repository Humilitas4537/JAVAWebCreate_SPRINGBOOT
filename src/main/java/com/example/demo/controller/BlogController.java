package com.example.demo.controller;

import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.domain.Member;
import com.example.demo.model.repository.MemberRepository;
import com.example.demo.model.service.BlogService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import com.example.demo.model.service.AddArticleRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
// 폼 데이터나 쿼리 스트링을 객체로 변수 바인딩
// RequestParam은 개별 변수로 바인딩
import org.springframework.web.bind.annotation.PathVariable;
// 경로 변수를 개별 변수로 바인딩 : /users/{id} → @PathVariable Long id
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RequiredArgsConstructor
@Controller // 컨트롤러 어노테이션 명시
public class BlogController {
    // 클래스 하단 작성
    @Autowired
    BlogService blogService; // DemoController 클래스 아래 객체 생성

    private final MemberRepository memberRepository;

    /* article 게시판 페이지
    // @GetMapping("/article_list") // 게시판 링크 지정
    // public String article_list(Model model) {
    //     List<Article> list = blogService.findAll(); // 게시판 리스트
    //     model.addAttribute("articles", list); // 모델에 추가
    //     return "article_list"; // .HTML 연결
    // }   
    */

    /* board 게시판 페이지 - 모든 게시글 목록
    // @GetMapping("/board_list") // 새로운 게시판 링크 지정
    // public String board_list(Model model) {
    //     List<Board> list = blogService.findAll(); // 게시판 전체 리스트, 기존 Article에서 Board로 변경됨
    //     model.addAttribute("boards", list); // 모델에 추가
    //     return "board_list"; // .HTML 연결
    // }
    */

    // board 게시판 페이지 - 게시글 Paging
    @GetMapping("/board_list") // 새로운 게시판 링크 지정
    // 1) 게시글 리스트는 페이징 / 클래스 Page<T>, Pageable - PageRequest
    // 2) 키워드, 페이지 값 필요 : String keyword, Int page + 처음 들어왔을 때, 디폴트값 필요
    public String board_list(
        Model model,
        // @RequestParam -> 자동 타입변환
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "") String keyword,
        HttpSession session) // 세션 객체 전달
    {
        // Object 타입으로 반환되므로 타입 캐스팅
        String userId = (String) session.getAttribute("userId"); // 세션 아이디 존재 확인
        String email = (String) session.getAttribute("email"); // 세션에서 이메일 확인
        if (userId == null) {
            return "redirect:/member_login"; // 로그인 페이지로 리다이렉션
        }
        System.out.println("세션 userId: " + userId);

        // 페이지 정보 구현
        PageRequest pageable = PageRequest.of(page, 3); // Pageable - (구현체)PageRequest.of(현재 페이지, 페이지 게시글 수)
        Page<Board> list; // Page를 반환
        if (keyword.isEmpty()) {
            list = blogService.findAll(pageable); // 기본 전체 출력(키워드 x)
        } else {
            list = blogService.searchByKeyword(keyword, pageable); // 키워드로 검색
        }
        model.addAttribute("email", email); // 로그인 사용자(이메일)
        model.addAttribute("startNum", (page * pageable.getPageSize()) + 1);
        model.addAttribute("boards", list); // 모델에 추가
        model.addAttribute("totalPages", list.getTotalPages()); // 전체 페이지 수
        model.addAttribute("currentPage", page); // 페이지 번호
        model.addAttribute("keyword", keyword); // 키워드
        return "board_list"; // .HTML 연결
    }

    // 게시글 조회
    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id, HttpSession session) {
        Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
        if (list.isPresent()) {
            model.addAttribute("boards", list.get()); // 존재할 경우 실제 Board 객체를 모델에 추가
        } else {
        // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
            return "/error_page/article_error"; // 오류 처리 페이지로 연결
        }

        String email = (String) session.getAttribute("email");
        Member member = memberRepository.findByEmail(email);
        if(list.get().getUser().equals(member.getName())){
            model.addAttribute("user", true);
        }
        return "board_view"; // .HTML 연결
    }

    // 게시판 글쓰기 페이지
    @GetMapping("/board_write")
    public String board_write() {
        return "board_write";
    }

    @DeleteMapping("/api/board_delete/{id}")
    public String board_delete(@PathVariable Long id){
        blogService.delete(id);
        return "redirect:/board_list";
    }

    /* article 게시판 글 생성
    // @PostMapping("/api/articles") // post 요청
    // public String addArticle(@ModelAttribute AddArticleRequest request) { // 아직 없음(에러)
    //     blogService.save(request); // 게시글 저장
    //     return "redirect:/article_list";
    // }
    */

    /* article 게시글 수정 페이지 이동
    // @GetMapping("/article_edit/{id}") // 게시판 링크 지정
    // public String article_edit(Model model, @PathVariable Long id) {
    //     Optional<Article> list = blogService.findById(id); // 선택한 게시판 글
    //         if (list.isPresent()) {
    //             model.addAttribute("article", list.get()); // 존재하면 Article 객체를 모델에 추가
    //         } else {
    //         // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
    //             return "/error_page/article_error"; // 오류 처리 페이지로 연결
    //         }
    //         return "article_edit"; // .HTML 연결
    // }
    */

    /* article 게시글 수정
    // @PutMapping("/api/article_edit/{id}")
    // public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
    //     blogService.update(id, request);
    //     return "redirect:/article_list"; // 글 수정 이후 .html 연결
    // }
    */

    // board 게시판 글 추가
    @PostMapping("/api/boards") // 글쓰기 게시판 저장
    public String addboards(@ModelAttribute AddArticleRequest request, HttpSession session) {
        String email = (String) session.getAttribute("email");
        Member member = memberRepository.findByEmail(email);
        request.setUser(member.getName());;
        blogService.save(request);
        return "redirect:/board_list"; // .HTML 연결
    }
    // board 게시글 수정 페이지 이동
    @GetMapping("/board_edit/{id}")
    public String board_edit(Model model, @PathVariable Long id) {
        Optional<Board> list = blogService.findById(id);
        if(list.isPresent()){
            model.addAttribute("board", list.get());
        }
        else{
            return "/error_page/article_error";
        }
        return "board_edit";
    }
    // board 게시글 수정
    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request){
        blogService.update(id, request);
        return "redirect:/board_list"; // 글 수정 이후 .html 연결
    }

    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id){
        blogService.delete(id);
        return "redirect:/article_list";
    }

    @GetMapping("/favicon.ico")
    public void favicon() {
        // 아무 작업도 하지 않음
    }
}
