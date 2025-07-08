# 프로토콜의 개념

## 1. 프로토콜이란?

**프로토콜(Protocol)** 은  **누가, 언제, 무엇을, 어떻게** 통신할 것인지를 정의하는 약속, 양식입니다. \
노드와 노드사이에 **어떤 데이터를 어떻게 송,수신 하는지의 양식**입니다.

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FTcU08%2FbtsO8AjWDo8%2FAAAAAAAAAAAAAAAAAAAAALucy7k7K3Qp6ojbiECG2-ndwTmyILlg0D29w3avp5UE%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1753973999%26allow_ip%3D%26allow_referer%3D%26signature%3DKIMU9DXuam7FndggRbsFY8qACLU%253D)

* 택배는 택배만의 양식
* 편지는 편지만의 양식
* 전화는 전화만의 양식

---

## 2. 여러가지 프로토콜

![](https://velog.velcdn.com/images/rlarudgns970/post/b50e6aed-f6cf-4182-8983-181ede62bea7/image.png)
프로토콜은 일반적으로 다음과 같은 구성 요소를 포함합니다.

- 가까운 곳과 통신할 때는 이더넷 프로토콜을 사용한다.(MAC 주소 사용)
- 멀리 있는 곳과 연락할 때는 ICMP, IPv4, ARP를 사용한다. (IP주소 사용)
- 특정 프로그램과 연락할 때는 TCP, UDP라는 프로토콜을 사용한다. (포트 번호 사용)
- 위 프로토콜을 따로 사용하는 것이 아니라 함께 사용한다.

## 패킷

![](https://velog.velcdn.com/images/rlarudgns970/post/1a9dc099-8bb4-4fdd-8444-978ed9aca74b/image.png)

여러 프로토콜로 캡슐화된 **패킷**

- 보내고싶은 데이터가 존재하고, 어떤 프로그램이랑 통신할지, 얼마나 떨어져있는 지역인지, 어떤 컴퓨터인지 합쳐놓은 형태

---

## 3. 프로토콜의 역할

- 데이터 **전송의 신뢰성** 보장 (예: TCP)
- **데이터 형식** 정의 (예: HTTP 요청 헤더)
- **통신 흐름 제어** (예: 패킷 순서 관리)
- **에러 제어 및 복구** (예: 재전송 메커니즘)

---

## 4. 계층별 주요 프로토콜 예시 (OSI 7계층 기준)

| 계층 | 역할 | 대표 프로토콜 |
|------|------|---------------|
| 응용 계층 | 사용자와 직접 통신 | HTTP, FTP, SMTP |
| 표현 계층 | 데이터 형식 변환 | SSL/TLS, MPEG |
| 세션 계층 | 연결 제어 | NetBIOS, PPTP |
| 전송 계층 | 신뢰성 있는 전송 | TCP, UDP |
| 네트워크 계층 | 경로 결정, 주소 지정 | IP, ICMP |
| 데이터 링크 계층 | 물리적 주소 지정 | Ethernet, PPP |
| 물리 계층 | 전기/기계적 연결 | RS-232, DSL |

---

## 5. 프로토콜 예시

- **HTTP (Hypertext Transfer Protocol)**  
  웹 브라우저와 서버가 HTML 문서를 주고받는 데 사용하는 프로토콜. 비연결형이며 요청/응답 구조를 가진다.

- **TCP (Transmission Control Protocol)**  
  연결형 프로토콜로, 데이터의 순서 보장 및 오류 복구 기능을 제공한다.

- **UDP (User Datagram Protocol)**  
  비연결형 프로토콜로, 빠른 속도가 필요한 스트리밍이나 게임 등에 사용된다.

---

## 6. 정리

프로토콜은 **컴퓨터 간 통신을 가능하게 하는 규칙의 집합**이며, 다양한 계층과 목적에 맞게 존재한다.  
네트워크를 이해하고 활용하려면 각 프로토콜의 역할과 특징을 정확히 이해하는 것이 중요하다.