# Grow In CS 👨‍💻👨‍💻

혼자 공부하기에는 동기 부여가 너무 안되는 것 같아 시작한, 추후 면접을 대비하여 CS를 공부하는 스터디

* [스터디 방식](#------)
  + [최종적인 트리 모습](#----------)
* [스터디 규칙](#------)
* [스터디 목차](#--)
* [면접 질문 모음](#--------)
* [협업](#--)
* [참여자](#--)

----

## 스터디 방식

1. 아래의 목차 순으로 차례대로 진행
2. 목차 폴더 내부에 각자의 공부 내역(Readme) 작성 & PR 등록
3. 공부한 소제목 개수마다 발표자를 랜덤 선정
4. 발표 후 발표 내용 이외에 덧붙이고 싶은 얘기가 있다면 이어서 발표 & 이후 PR 날려서 병합
   * PR을 날릴 때는 본인의 표시 작성
     
   * ```
     ~~ 이런 방식으로 진행합니다. (민경) 이런 방식으로 쓰는 이유에는 00, xx 가 있다고 하며, 주로 ㅁㅁ에서 보인다고 합니다!
      ```

   * Q&A도 Readme에 같이 작성
5. 결과적으로 발표자의 정리본으로 최종 폴더에 업로드
6. 하나의 챕터(큰 제목)가 끝날 때마다 해당 챕터에 대한 모의 면접 & 회고 진행
   * 모의 면접에 경우 각자 면접 질문 & 대답 준비
   * 1:1로 팀을 나눠서 음성 채널 입장
   * 15분은 면접자, 피면접자 나눠서 진행, 나머지 15분은 역할 변경해서 진행
   * 모의면접이 완료되면 회고에 대한 PR 오픈, 본인의 comment 작성

### 최종적인 트리 모습

```
├── 1. 디자인 패턴 (최종 폴더)
│   ├── 개요 (민경)
│   └── 생성 패턴
│       ├── 싱글톤 (영희)
│       └── 팩토리 메서드 (철수)
├── 0. 스터디 내용 정리
│   ├── 민경
│   │   └── 1. 디자인 패턴
│   │       ├── 개요
│   │       ├── 싱글톤
│   │       └── 팩토리 메서드
│   ├── 영희
│   │   └── 1. 디자인 패턴
│   │       ├── 싱글톤
│   │       └── 팩토리 메서드
│   ├── 철수
│   │   └── 1. 디자인 패턴
│   │       ├── 싱글톤
│   │       └── 팩토리 메서드
...
```

## 스터디 규칙
* 불참 시에는 사유를 꼭 말하기
* 불참에 대한 벌칙은 없지만 본인이 공부한 만큼 가져간다는 생각과 열심히 준비하는 서로에게 책임감을 가져주세요!
* 서로에게 예의를 갖추기
* 불편한 점, 개선한 점이 있다면 바로바로 말해서 해결하기

## 스터디 목차
‘면접을 위한 CS 전공지식 노트’ 의 목차를 참고하였으며, 그 외는 깃허브(아래 참고)를 통해 추가

<details>
  <summary><h3>1. 디자인 패턴과 프로그래밍 패러다임 </h3></summary>
  
- 디자인 패턴
    - 개요
    - 생성 패턴
        - 싱글톤
        - 팩토리 메소드
    - 구조 패턴
        - 데코레이터
        - 프록시
        - 어뎁터
        - 노출모듈
    - 행위 패턴
        - 옵저버
        - 템플릿 메소드
        - 전략
        - 이터레이터
    - MVC, MVP, MVVM 패턴
    - 안티 패턴
- 선언형과 함수형 프로그래밍
    - 순수 함수, 고차 함수
- 객체 지향 프로그래밍
    - 4가지 특징, SOLID 원칙
- 절차형 프로그래밍
</div>
</details>

<details>
  <summary><h3>2. 네트워크</h3></summary>

- 네트워크의 기초
    - 처리량과 지연 시간
    - 네트워크 토폴로지와 병목 현상
    - 네트워크 분류
        - LAN, MAN, WAN
    - 네트워크 성능 분석 명령어
        - ping, netstat, nslookup, tracert
    - 네트워크 프로토콜 표준화
        - IEEE 802.3
- TCP/IP 4계층 모델
    - 계층 구조, PDU
    - TCP와 UDP, TCP 3-way-handshake & 4-way-handshake
- 네트워크 기기
    - 애플리케이션 계층
        - L7 스위치
    - 인터넷 계층
        - 라우터, L3 스위치
    - 데이터 링크 계층
        - L2 스위치, 브리지
    - 물리 계층
        - NIC, 리피터, AP
- IP 주소
    - ARP
    - 홉바이홉 통신
        - 라우팅 테이블, 게이트웨이
    - IP 주소 체계
        - IPv4, IPv6, DHCP, NAT
    - DNS
        - Round Robin 방식
- HTTP
    - HTTP/1.0, HTTP/1.1, HTTP/2, HTTP/3
    - HTTPS
</details>

<details>
  <summary><h3>3. 컴퓨터 구조</h3></summary>
  
- 컴퓨터의 구성
- CPU 작동 원리
- 캐시 메모리
- 고정 소수점 & 부동 소수점
- 패리티 비트 & 해밍 코드
- ARM 프로시저
</details>

<details>
  <summary><h3>4. 운영체제</h3></summary>

- 운영체제와 컴퓨터
    - 운영체제의 역할과 구조
        - 시스템
    - 컴퓨터의 요소
        - CPU, 인터럽트, DMA 컨트롤러, 메모리, 타이머, 디바이스 컨트롤러
- 메모리
    - 메모리 계층
        - 캐시
    - 메모리 관리
        - 가상 메모리, 스레딩, 메모리 할당, 페이지 교체 알고리즘
- 프로세스와 스레드
    - 프로세스와 컴파일 과정
        - 전처리, 컴파일러, 어셈블러, 링커
    - 프로세스의 상태
    - 프로세스의 메모리 구조
        - 스택과 힙, 데이터 영역과 코드 영역
    - PCB
        - 컨텍스트 스위칭
    - 멀티프로세싱
        - IPC
    - 스레드와 멀티스레딩
    - 공유 자원과 임계 영역
        - 세마포어와 뮤텍스
    - 교착 상태
    - 경쟁 상태
    - 동기와 비동기
- CPU 스케줄링 알고리즘
    - 비선점형 방식
    - 선점형 방식

</details>

<details>
  <summary><h3>5. 데이터베이스</h3></summary>

- 데이터베이스의 기본
    - 엔터티, 릴레이션, 속성, 도메인, 필드와 레코드, 관계, 키
    - DML, DDL, DCL
- Sharding&Master/Slave
- ERD와 정규화
    - ERD의 중요성
    - 이상 현상, 정규화, 반정규화
- 트랜잭션과 무결성
    - 트랜잭션 개념
    - ACID
    - LOCK
    - 격리 수준
    - 교착 상태
- 데이터베이스의 종류
    - RDBMS
    - NoSQL
- Redis
- 인덱스
    - 인덱스의 필요성
    - B-트리
    - Clustered index, Non-Clustered index
    - 인덱스 최적화 기법
- 프로시저
- SQL Injection
- 조인의 종류
    - 내부 조인
    - 왼쪽, 오른쪽 조인
    - 합집합 조인
- 조인의 원리
    - 중첩 루프 조인
    - 정렬 병합 조인
    - 해시 조인
</details>

<details>
  <summary><h3>6. 자료 구조</h3></summary>
  
- 복잡도
    - 시간, 공간 복잡도
- 선형 자료 구조
    - 연결 리스트
    - 배열
    - 벡터
    - 스택
    - 큐
- 비선형 자료 구조
    - 그래프
    - 트리
        - Binary Tree
        - Full Binary Tree
        - Complete Binary Tree
        - Binary Search Tree
    - 힙
    - 우선순위 큐
    - 맵(Map)
    - 셋(Set)
    - 해시 테이블
    - 트라이(Trie)
    - AVL Tree
    - Red-Black Tree
- 정렬 알고리즘
    - 선택 정렬, 거품 정렬, 삽입 정렬
    - 병합 정렬, 퀵 정렬, 힙 정렬
- 이분 탐색
- 동적계획법
- 최단 경로
- 최소 비용(MST)
</details>

<details>
  <summary><h3>7. 웹(Web)</h3></summary>

- 브라우저 동작 방법
- 쿠키 & 세션
- HTTP Request Methods
- HTTP Status Code
- REST API
- Web Server 와 WAS
- CORS
- 로드 밸런스
- OAuth
- JWT(JSON Web Token)
- 인증 방식
- 로깅 레벨
- UI & UX
- 네이티브 앱 & 웹 앱 & 하이브리드 웹
- Vue.js 와 React
- PWA(Progressive Web APP)
- CSRF & XSS
- 웹 통신 흐름 정리
    - www.example.com을 입력했을 때 

</details>

CS 스터디가 완료된 이후 포지션 별로 나눠서 스터디 진행

<details>
  <summary><h3>Frontend</h3></summary>
  
https://github.com/jbee37142/Interview_Question_for_Beginner/tree/main/FrontEnd#Document-Object-Model

- JavaScript
    - https://github.com/jbee37142/Interview_Question_for_Beginner/tree/main/JavaScript
    - https://gyoogle.dev/blog/computer-language/Javascript/es2015.html
- Vue.js
    - https://gyoogle.dev/blog/web-knowledge/vue-knowledge/Vue.js%20%EB%9D%BC%EC%9D%B4%ED%94%84%EC%82%AC%EC%9D%B4%ED%81%B4.html
- React
    - https://gyoogle.dev/blog/web-knowledge/react-knowledge/React%20Fragment.html
</details>

<details>
  <summary><h3>Backend</h3></summary>

- Java
    - https://github.com/jbee37142/Interview_Question_for_Beginner/blob/main/Java/README.md
    - https://gyoogle.dev/blog/computer-language/Java/%EC%BB%B4%ED%8C%8C%EC%9D%BC%20%EA%B3%BC%EC%A0%95.html
    - https://github.com/hyeong-jun-kim/CS-Study/tree/main/Java
- Spring
    - https://gyoogle.dev/blog/web-knowledge/spring-knowledge/%5BSpring%5D%20Bean%20Scope.html
</details>

----
## 면접 질문 모음

- **면접 당시 받은 질문 (백엔드)**
    - https://github.com/ham-study/cs-study-for-interview/tree/main/Questions
- **Java, 운영체제, 데이터베이스, 네트워크, 웹 면접 질문**
    - https://gyoogle.dev/blog/interview/%EC%96%B8%EC%96%B4.html
- **CS부터 Java, JavaScript, Spring 까지의 면접 질문**
    - https://github.com/WeareSoft/tech-interview
- **리버스 인터뷰 (회사에 질문할 것)**
    - https://github.com/jbee37142/Interview_Question_for_Beginner/tree/main/Reverse_Interview
- **면접 팁**
    - https://github.com/jbee37142/Interview_Question_for_Beginner/tree/main/Tip

 ----

 <details>
   <summary><h2>참고</h2></summary>

https://github.com/gyoogle/tech-interview-for-developer

https://github.com/jbee37142/Interview_Question_for_Beginner

https://github.com/ham-study/cs-study-for-interview

https://github.com/Seogeurim/CS-study

https://github.com/hyeong-jun-kim/CS-Study

https://github.com/jobhope/TechnicalNote

 </details>

---
## 협업

카카오톡 - 사담 & 공지 , 발표자 선정을 위한 추첨 진행 <br>
디스코드 - 스터디 진행 <br>
깃허브 - 스터디 내용 저장소

---
## 참여자

|<a href="https://github.com/yeonna18k">강나연</a>|<a href="https://github.com/20massalia">이지인</a>|<a href="https://github.com/BHC-Chicken">박현철</a>|<a href="https://github.com/doma17">천준민</a>|<a href="https://github.com/X1n9fU">김민경</a>|
|------|---|---|---|---|
|<img src="https://github.com/yeonna18k.png" width="150px;" alt="강나연 프로필"/>|<img src="https://github.com/20massalia.png" width="150px;" alt="이지인 프로필"/>|<img src="https://github.com/BHC-Chicken.png" width="150px;" alt="박현철 프로필"/>|<img src="https://github.com/doma17.png" width="150px;" alt="곽병민 프로필"/>|<img src="https://github.com/X1n9fU.png" width="150px;" alt="김민경 프로필"/>|



