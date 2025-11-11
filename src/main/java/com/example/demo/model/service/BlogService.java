package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BlogRepository;
import com.example.demo.model.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // final 또는 NonNull 필드의 생성자 자동 생성 : 생성자 (로 객체)주입
public class BlogService {
    @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능 : 필드 (로 객체)주입
    private final BlogRepository blogRepository; // 리포지토리 선언
    private final BoardRepository blogRepository2; // 리포지토리 선언


    // public List<Article> findAll() { // 게시판 전체 목록 조회
    //     return blogRepository.findAll();
    // }
    public List<Board> findAll() { // 게시판 전체 목록 조회
        return blogRepository2.findAll();
    }

    // public Optional<Article> findById(Long id) { // 게시판 특정 글 조회
    //     return blogRepository.findById(id);
    // }

    public Optional<Board> findById(Long id) { // 게시판 특정 글 조회
        return blogRepository2.findById(id);
    }


    // public Article save(AddArticleRequest request){
    //     // DTO가 없는 경우 이곳에 직접 구현 가능
    //     // public ResponseEntity<Article> addArticle(@RequestParam String title, @RequestParam String content) {
    //     // Article article = Article.builder()
    //     //  .title(title)
    //     //  .content(content)
    //     //  .build();
    //     return blogRepository.save(request.toEntity());
    // }
    

    // public void update(Long id, AddArticleRequest request){
    //     Optional<Article> optionalArticle = blogRepository.findById(id); // 단일 글 조회
    //     optionalArticle.ifPresent(article -> { // 값이 있으면
    //         article.update(request.getTitle(), request.getContent()); // 값을 수정
    //         blogRepository.save(article); // Article 객체에 저장
    //     });
    // }

    public void update(Long id, AddArticleRequest request){
        Optional<Board> optionalBoard = blogRepository2.findById(id); // 단일 글 조회
        optionalBoard.ifPresent(board -> { // 값이 있으면
            // @ModelAttribute로 request에 변수 바인딩한 폼 데이터는 title과 content 뿐.
            // 하지만 Board의 update 메서드는 빌드 패턴을 적용하지 않았으므로 모든 매개변수 값을 받아야 함 -> Board의 Getter로 처리
            board.update(request.getTitle(), request.getContent(), board.getUser(), board.getNewdate(), board.getCount(), board.getLikec());
            blogRepository2.save(board);
        });
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}

