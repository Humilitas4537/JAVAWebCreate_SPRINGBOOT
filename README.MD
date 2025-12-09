# SPRING_BOOT 개발
[프로젝트 메인 주소](https://github.com/Humilitas4537/JAVAWebCreate_SPRINGBOOT.git)

## 📘 2주차 자바웹 개발
### ✅ 스프링부트 준비 및 실행
- 프레임워크 스프링부트 개발 환경 설정
- URI 매핑 구현
### ✅ 연습문제 'URL 맵핑과 컨트롤러 추가' 완료
- hello2.html 작성 및 매핑 + 5개 속성 변수 출력  

```java
@GetMapping("/hello2")
public String hello2(Model model) {
    model.addAttribute("data1", "model로 페이지에 그릴 정보를 전달합니다.");
    model.addAttribute("data2", "model은 key-value 값으로");
    model.addAttribute("data3", "thymeleaf가 th: 속성에 데이터를 넣어서 화면을 구성합니다.");
    model.addAttribute("data4", "예) th:text='${key}'");
    model.addAttribute("data5", "${}는 변수 표현식으로 {}안에 key를 넣습니다.");
    return "hello2";
}
```
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>두번째 Hello 페이지</title>
</head>
<body>
    <p th:text="${data1}"></p>
    <p th:text="${data2}"></p>
    <p th:text="${data3}"></p>
    <p th:text="${data4}"></p>
    <p th:text="${data5}"></p>
    <a href="/">메인 홈페이지</a>
</body>
</html>
```

## 📘 3주차 자바웹 개발
### ✅ 개인 포트폴리오 페이지 작성
- 페이지 수정 및 변경
- 상세 소개 페이지 작성 & Mapping
### ✅ 연습문제 '상세 페이지 수정' 완료
- 상세 페이지 '되돌아가기' 버튼 기능 수정<br>

창을 닫을지 확인, 1초 후 close
```js
// 새로 추가한 이벤트 : '되돌아가기' 버튼 클릭 시 창을 닫을 지 물어봄
function confirmClose(){
    if(confirm("정말로 창을 닫으시겠습니까?")){
        alert("확인 : true값 확인, 1초 후 창을 닫습니다.")
        // setTimeout(window.close,1000);
        setTimeout("window.close()",1000);
    }
    else{
        alert("취소 : false값 확인, 창을 닫지 않습니다.")
    }
}
```
## 📘 4주차 자바웹 개발
### ✅ 데이터 베이스 연동
- MySQL 설치
- 데이터 베이스 생성 및 연동
### ✅ 연습문제 '사용자 추가 및 출력하기' 완료
- DB에 2명의 사용자 추가, testdb.html에서 사용자 id, 이름 출력
```java
// Repository
TestDB findByName(String name);

// Service
public TestDB findByName(String name){ // 이름 찾기
    return (TestDB) testRepository.findByName(name);
}

// Controller
@GetMapping("/testdb")
public String getAllTestDBs(Model model) {
    TestDB test = testService.findByName("홍길동");
    model.addAttribute("data4", test);
    System.out.println("데이터 출력 디버그 : " + test);

    TestDB test1 = testService.findByName("아저씨");
    model.addAttribute("data5", test1);
    System.out.println("데이터 출력 디버그 : " + test1);

    TestDB test2 = testService.findByName("꾸러기");
    model.addAttribute("data6", test2);
    System.out.println("데이터 출력 디버그 : " + test2);
    return "testdb";
}
```
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>데이터베이스 테스트</title>
</head>
<body>
    <h1>User List</h1>
    <p>아이디(기본키) : <span th:text="${data4.id}"></span></p>
    <p>이름 : <span th:text="${data4.name}"></span></p>
    <p>아이디(기본키) : <span th:text="${data5.id}"></span></p>
    <p>이름 : <span th:text="${data5.name}"></span></p>
    <p>아이디(기본키) : <span th:text="${data6.id}"></span></p>
    <p>이름 : <span th:text="${data6.name}"></span></p>
</body>
</html>
```

## 📘 5주차 자바웹 개발
### ✅ 블로그 게시판 기능 추가
- 게시판 페이지 작성
- 데이터 베이스와 연동, 게시글 추가
### ✅ 연습문제 '페이지 리다이렉트' 완료
- **게시글 추가 후 자동으로 /article_list 게시판 이동**

toEntity() 메서드로 빌더패턴을 사용한 Article 생성자 호출<br>
-> Article 객체 생성 및 저장
```java
// DTO : AddArticleRequest
public Article toEntity(){ // Article 객체 생성
    return Article.builder()
        .title(title)
        .content(content)
        .build();
}

// service
public void save(AddArticleRequest request){
    blogRepository2.save(request.toEntity());
}
```
리다이렉트로 블로그 게시판 URI 요청 및 이동
```java
// Controller
@PostMapping("/api/articles") // post 요청
public String addArticle(@ModelAttribute AddArticleRequest request) {
    blogService.save(request); // 게시글 저장
    return "redirect:/article_list";
}
```

## 📘 6주차 자바웹 개발
### :white_check_mark: 게시판 수정&삭제 및 에러 페이지 추가
- 수정&삭제 버튼 구현
- 에러 페이지 구현
### :white_check_mark: 연습문제 '예외처리' 완료
- 루트 경로 내에서 잘못된 페이지 접속 시 에러 페이지로 이동
```java
@ControllerAdvice
public class ExceptionController{
    @ExceptionHandler(Exception.class)
    public String ExceptionControll(Model model, Exception exception){
        // 데이터 보내기만
        model.addAttribute("errorMsg", exception.getMessage());
        return "error_page/article_error";
    }
}
```

## 📘 7주차 자바웹 개발
### :white_check_mark: 포트폴리오 페이지&게시판 추가 수정
- 포트폴리오 페이지 프로젝트 항목 수정
- New 게시판 페이지 및 DB("Board") 생성
### ✅ 연습문제 '게시판 페이지 - 글 수정' 완료
- <b>New 게시판에 글 수정 기능 추가</b>

board_edit 페이지에서 수정 버튼 클릭 시, 게시글 **업데이트** 동작 수행
```html
<form th:action="@{/api/board_edit/{id}(id=${board.id})}">
    <input type="hidden" name="_method" value="put">
</form>
```
게시글 수정 후, 게시판 페이지로 리다이렉트
```java
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
- **ID 대신, 게시판 번호로 순서대로 출력**
```java
model.addAttribute("startNum", (page * pageable.getPageSize()) + 1);
```
게시판 번호 : ((페이지 번호 * 페이지 당 게시글 수) + 1) + num.index<br>
num.index는 num이 반복되어 테이블 행이 추가될 때마다 0, 1, 2.. 씩 증가 <br>
따라서 페이지 번호가 0 이면 게시판 번호는 1, 2, 3 ...
```html
<tbody>
    <tr th:each="board, num : ${boards}">
        <td th:text="${startNum} + ${num.index}"></td>
    </tr>
<tbody>
```
<br>

- **게시글 삭제 기능 추가**

board_view 페이지에서 삭제 버튼 클릭 시, 게시글 바로 삭제
```html
<!-- 삭제 버튼 -->
<form th:action="@{/api/board_delete/{id}(id=${board.id})}" method="post" style="display:inline;">
    <input type="hidden" name="_method" value="delete">
    <button type="submit" class="btn btn-danger">삭제</button>
</form>
```
삭제 후 board_list 게시판 페이지로 이동
```java
@DeleteMapping("/api/article_delete/{id}")
public String deleteArticle(@PathVariable Long id){
    blogService.delete(id);
    return "redirect:/article_list";
}

// BlogService
public void delete(Long id){
    blogRepository2.deleteById(id);
}
```

## 📘 9주차 자바웹 개발
### :white_check_mark: 로그인 및 회원가입 기능 추가
- 회원가입 페이지 및 회원 정보 DB("Member") 추가 구현
- 로그인 기능 구현 : 회원 정보 인증 성공 시 게시판 이동
### ✅ 연습문제 '입력값 필터링' 완료
- **Validation 어노테이션으로 DTO 회원정보 입력값 검증**

의존성 추가
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```
DTO 회원정보 입력값 필터링
```java
@NotBlank // 공백, 빈 문자열, Null 허용 X
@Pattern( // 정규식 검사
    regexp = "^[a-zA-Z가-힣]+$", 
    message = "이름에는 숫자와 특수문자를 사용할 수 없습니다"
)
private String name;
@NotBlank
@Email // 이메일 형식 검사 (빈 문자열, Null -> 통과)
private String email;
@Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z]+$",
    message = "비밀번호는 영문이고 대문자와 소문자를 포함해야합니다"
)
@Size(min = 8) // 길이 제한
private String password;
@Max(value = 90) // 최대값 지정
@Min(value = 19) // 최소값 지정
private String age;
@NotEmpty // 공백은 허용, 빈 문자열과 Null은 허용 X
private String mobile;
@NotEmpty
private String address;
```
회원가입 DTO만 입력값 검증<br>
DTO : @Valid - DTO의 Validation 어노테이션 검사<br>
Service 계층 : 메서드의 매개변수 Validation 어노테이션 검증
```java
// Controller
@PostMapping("/api/members") // @Valid : DTO(request)에 붙어있는 Validation 어노테이션 검사
public String addmembers(@Valid @ModelAttribute AddMemberRequest request) {
    memberService.saveMember(request);
    return "join_end"; // .HTML 연결
}

// Service
@Service
@Validated // 모든 메서드의 매개변수 Validation 어노테이션 검증
@Transactional // 트랜잭션 처리(클래스 내 모든 메소드 대상)
@RequiredArgsConstructor // final 또는 NonNull 필드의 생성자 자동 생성 : 생성자 (로 객체)주입
public class MemberService {
    public Member saveMember(@Valid AddMemberRequest request){}
}
```

## 📘 10주차 자바웹 개발
### :white_check_mark: 세션 쿠키와 로그아웃 기능 추가
- 로그인 시 세션 저장소에 userID, email 생성
- securityConfig 보안 관리 클래스에 HttpSecurity 보안 설정 추가
- 로그인, 로그아웃 시 기존 세션 무효화 -> 세션 저장소와 세션 쿠키 JSESSIONID 삭제

### ✅ 연습문제 '게시판 수정하기' 완료
- **현재 로그인한 사용자로 글 작성자 저장**

실제 글 작성 로직에서 회원의 이름 정보를 가져와 게시글 DTO 수정
```java
// board 게시판 글 추가
@PostMapping("/api/boards") // 글쓰기 게시판 저장
public String addboards(@ModelAttribute AddArticleRequest request, HttpSession session) {
    String email = (String) session.getAttribute("email");
    Member member = memberRepository.findByEmail(email);
    request.setUser(member.getName());;
    blogService.save(request);
    return "redirect:/board_list"; // .HTML 연결
}
```
- **글 작성자만 board_view/{id} 에서 수정/삭제 버튼 접근 가능**

== : 참조값 비교
Board, Member의 실제 문자열 값 비교는 equals() 활용
```java
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

    // 수정, 삭제 버튼 노출 결정
    String email = (String) session.getAttribute("email");
    Member member = memberRepository.findByEmail(email);
    if(list.get().getUser().equals(member.getName())){
        model.addAttribute("user", true);
    }
    return "board_view"; // .HTML 연결
}
```
```html
<tr th:each="board : ${boards}" th:if="${user}">
```

## 📘 11주차 자바웹 개발
### :white_check_mark: 프로필 메일 업로드 기능 추가
- 메일 기능 -> .txt 파일로 vscode에 생성
- 기본 지도 위치 수정