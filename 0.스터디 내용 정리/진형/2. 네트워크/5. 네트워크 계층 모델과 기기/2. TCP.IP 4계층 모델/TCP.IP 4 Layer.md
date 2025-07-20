# TCP/IP 4계층 모델

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F213F623C566BAE253B)

> 정의 : TCP/IP 모델은 네트워크 통신을 4개의 계층으로 나누어, 각 계층이 수행하는 기능과 역할을 정의한 실용적 참조 모델입니다.  
OSI 7계층 모델보다 단순화되어 인터넷 프로토콜 스위트 구현에 많이 활용됩니다.

---

## 1. 링크 계층 (Link Layer)
- **역할**: 물리 매체 위에서 인접 노드 간 프레임 단위의 데이터 전송  
- **주요 기능**: 
  - 물리적 연결 관리 (케이블, 무선 링크)  
  - MAC 주소 기반 프레임 캡슐화/역캡슐화  
  - 오류 검출(프레임 체크 시퀀스) 및 흐름 제어  
- **예시 프로토콜/기술**: Ethernet, 

---

## 2. 인터넷 계층 (Internet Layer)
- **역할**: 논리적 주소(IP) 기반으로 네트워크 간 패킷 전달 및 경로 탐색  
- **주요 기능**:
  - IP 주소 지정 및 패킷 라우팅  
  - 패킷 분할(Fragmentation) 및 재조합  
  - ICMP를 통한 오류 메시지 및 상태 정보 제공  
- **예시 프로토콜**: IPv4, IPv6, ICMP, IGMP (Multicast)

---

## 3. 전송 계층 (Transport Layer)
- **역할**: 종단 간(End-to-End) 데이터 전송의 신뢰성, 순서 제어 및 흐름 제어  
- **주요 기능**:
  - 연결형 서비스 제공(TCP) vs 비연결형 서비스(UDP)  
  - 데이터 분할(Segmentation) 및 재조립(Reassembly)  
  - 포트 번호 기반 다중화(Multiplexing)  
- **예시 프로토콜**: TCP, UDP, RARP

---

## 4. 응용 계층 (Application Layer)
- **역할**: 사용자 애플리케이션과 직접 상호작용하며, 다양한 네트워크 서비스 제공  
- **주요 기능**:
  - 고수준 응용 프로토콜을 통한 데이터 표현 및 교환  
  - 라이브러리나 API를 통한 서비스 접근  
- **예시 프로토콜/서비스**: HTTP, HTTPS, FTP, SMTP, DNS, SSH, Telnet, SNMP  

---

> **참고**:
> - TCP/IP 4계층 모델은 실제 인터넷 스택 구현에 최적화된 구조로,  
>   각 계층이 중첩되어 OSI 모델의 일부 계층 기능을 흡수·통합하기도 합니다.  
> - 단순 명칭의 차이일 뿐, 실제 전송·네트워크·데이터 링크 기능은 OSI 모델과 유사하게 동작합니다.

Reference
https://hahahoho5915.tistory.com/15