# SPRING_BOOT 개발
[프로젝트 메인 주소](https://github.com/Humilitas4537/JAVAWebCreate_SPRINGBOOT.git)

## 📘 2주차 자바웹 개발
### ✅ 스프링부트 준비 및 실행
- 프레임워크 스프링부트 개발 환경 설정
- URI 매핑 구현
### ✅ 연습문제 'URL 맵핑과 컨트롤러 추가' 완료
- hello2.html 작성 및 매핑 + 5개 속성 변수 출력

## 📘 3주차 자바웹 개발
### ✅ 개인 포트폴리오 페이지 작성
- 페이지 수정 및 변경
- 상세 소개 페이지 작성 & Mapping
### ✅ 연습문제 '상세 페이지 수정' 완료
- 상세 페이지 '되돌아가기' 버튼 기능 수정<br>
창을 닫을지 확인, 1초 후 close

## 📘 4주차 자바웹 개발
### ✅ 데이터 베이스 연동
- MySQL 설치
- 데이터 베이스 생성 및 연동
### ✅ 연습문제 '사용자 추가 및 출력하기' 완료
- DB에 2명의 사용자 추가, testdb.html에서 사용자 id, 이름 출력

## 📘 5주차 자바웹 개발
### ✅ 블로그 게시판 기능 추가
- 게시판 페이지 작성
- 데이터 베이스와 연동, 게시글 추가
### ✅ 연습문제 '페이지 리다이렉트' 완료
- 게시글 추가 후 자동으로 /article_list 게시판 이동

## 📘 6주차 자바웹 개발
### :white_check_mark: 게시판 수정&삭제 및 에러 페이지 추가
- 수정&삭제 버튼 구현
- 에러 페이지 구현
### :white_check_mark: 연습문제 '예외처리' 완료
- 루트 경로 내에서 잘못된 페이지 접속 시 에러 페이지로 이동

## 📘 7주차 자바웹 개발
### :white_check_mark: 포트폴리오 페이지&게시판 추가 수정
- 포트폴리오 페이지 프로젝트 항목 수정
- New 게시판 페이지 및 DB("Board") 생성
### ✅ 연습문제 '게시판 페이지 - 글 수정' 완료
- <b>New 게시판에 글 수정 기능 추가</b>


board_edit 페이지에서 수정 버튼 클릭 시, 게시글 **업데이트** 동작 수행
```html (board_edit.html)
<form th:action="@{/api/board_edit/{id}(id=${board.id})}">
    <input type="hidden" name="_method" value="put">
</form>
```
게시글 수정 후, 게시판 페이지로 리다이렉트
```java (BlogController.java)
    // board 게시글 수정
    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request){
        blogService.update(id, request);
        return "redirect:/board_list"; // 글 수정 이후 .html 연결
    }

    public void update(Long id, AddArticleRequest request){
        Optional<Board> optionalBoard = blogRepository2.findById(id); // 단일 글 조회
        optionalBoard.ifPresent(board -> { // 값이 있으면
            // @ModelAttribute로 request에 변수 바인딩한 폼 데이터는 title과 content 뿐.
            // 하지만 Board의 update 메서드는 빌드 패턴을 적용하지 않았으므로 모든 매개변수 값을 받아야 함 -> Board의 Getter로 처리
            board.update(request.getTitle(), request.getContent(), board.getUser(), board.getNewdate(), board.getCount(), board.getLikec());
            blogRepository2.save(board);
        });
    }
```

## 📘 8주차 자바웹 개발
### :white_check_mark: 게시판 페이지 기능 추가
- 게시판 글 작성 기능 추가
- 게시글 검색창 및 페이징 기능 추가
### ✅ 연습문제 '게시판 페이지 - 추가 구현' 완료
- ID 대신 게시판 번호 : ((page * pagesize) + 1) + num.index 출력
- 게시글 삭제 기능 추가

## 📘 9주차 자바웹 개발
### :white_check_mark: 로그인 및 회원가입 기능 추가
- 회원가입 페이지 및 회원 정보 DB("Member") 추가 구현
- 로그인 기능 구현 : 회원 정보 인증 성공 시 게시판 이동

## 📘 10주차 자바웹 개발
### :white_check_mark: 세션 쿠키와 로그아웃 기능 추가
- 게시판 접근 시 로그인 상태 확인 -> 세션 userId 조회
- 로그아웃 시 기존 세션 userId 삭제

## 📘 11주차 자바웹 개발
### :white_check_mark: 프로필 메일 업로드 기능 추가
- 메일 기능 -> .txt 파일로 vscode에 생성
- 기본 지도 위치 수정