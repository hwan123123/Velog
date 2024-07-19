# Blog 요구사항 체크리스트

### 1. 회원가입
- [x] 회원 가입폼 구현
- [x] ID, Email 중복 체크 API 구현
- [x] 회원 등록 기능 구현
- [x] 회원 가입 후 로그인 폼으로 이동

### 2. 로그인
- [x] 로그인 폼 구현
- [x] 로그인 기능 구현
- [x] 로그인 성공 후 메인 페이지로 이동 (/velog)
- [x] 로그인 실패 후 다시 로그인 폼으로 이동 (오류 메시지 출력)
- [x] Spring Security를 이용한 로그인 구현 (Form Login, JWT Login, OAuth2 로그인)

### 3. 사이트 상단
- [x] 사이트 로고 좌측 상단에 배치
- [x] 로그인 링크 또는 사용자 정보 우측 상단에 표시
    - [x] 로그인하지 않았을 경우 로그인 링크 표시
    - [x] 로그인했을 경우 사용자 이름 표시
    - [x] 사용자 이름 클릭 시 설정, 사용자 블로그 이동 링크, 임시저장글 목록 보기, 로그아웃 표시

### 4. 로그아웃
- [x] 로그아웃 기능 구현

### 5. 메인 페이지 (/)
- [ ] 블로그 글 목록 보기 (최신 순, 조회수 높은 순, 즐겨찾기 순)
- [x] 페이징 처리 또는 무한 스크롤 구현
- [ ] 제목, 내용, 사용자 이름으로 검색 기능 구현

### 6. 블로그 글 쓰기
- [x] 블로그 제목, 내용, 사진 등을 입력하여 글 작성
- [x] "출간하기" 버튼 클릭 시 블로그 썸네일, 공개 유무, 시리즈 설정 후 글 등록
- [x] "임시저장" 버튼 클릭 시 글 임시 저장

### 7. 임시 글 저장 목록 보기
- [x] 로그인 후 임시 글 저장 목록 보기 링크 표시
- [ ] 임시 글 저장 목록에서 글 제목 클릭 시 글 수정 가능
- [ ] 임시 글 수정 페이지에서 "임시저장", "출간하기" 버튼 구현

### 8. 특정 사용자 블로그 글 보기 (/@사용자아이디)
- [ ] 사용자 정보 보기
- [ ] 사용자가 쓴 글 목록 보기 (최신 순, 조회수 높은 순, 즐겨찾기 순)
- [x] 페이징 처리 또는 무한 스크롤 구현
- [ ] 사용자의 태그 목록 보기 (태그당 글의 수 표시)
- [ ] 제목, 내용으로 검색 기능 구현

### 9. 시리즈 목록 보기
- [ ] 시리즈 목록 보기 구현
- [ ] 벨로그의 시리즈 기능을 확인해보세요. 글쓰고 출간하기를 하면 보여짐

### 10. 시리즈 제목 클릭시 시리즈에 포함된 블로그 글 목록 보여주기
- [ ] 시리즈에 속한 블로그 글 목록 보기

### 11. 블로그 글 상세 보기
- [ ] 메인 페이지에서 제목 클릭 시 블로그 글 상세 보기
- [x] 특정 사용자 블로그에서 제목 클릭 시 블로그 상세 보기
- [ ] 시리즈에 속한 블로그 글 목록에서 제목 클릭 시 블로그 글 상세 보기

### 12. 사용자 정보 보기
- [x] 로그인한 사용자 이름 클릭 시 사용자 정보 보기
- [ ] 사용자 이름, 이메일 출력
- [ ] 회원 탈퇴 링크 제공

### 13. 회원 탈퇴
- [ ] 회원 탈퇴 폼 구현
- [ ] 회원 탈퇴 확인 후 회원 정보 삭제

### 14. 댓글 목록 보여주기
- [x] 블로그 글 상세보기 하단에 댓글 목록 표시
- [ ] 댓글과 대댓글 최신 순으로 표시
- [ ] 댓글 최대 20개 표시 및 페이징 처리

### 15. 댓글 달기
- [x] 블로그에 댓글 달기 기능 구현
- [x] 댓글에 대댓글 달기 기능 구현

### 16. 블로그 글 좋아요 기능
- [ ] 블로그 글에 좋아요 기능 구현

### 17. 추가 기능
- [ ] 벨로그 사이트 분석 및 추가 기능 구현
- [ ] 벨로그와 비슷하거나 더 편리하게 구현
- [ ] 프론트 개발 (React.js 등) 학습 및 팀 프로젝트에서 프론트 개발 시도