# TCP/IP 4계층 모델과 PDU

## TCP/IP 4계층 모델

![OSI_7Layer_IMG](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F995EFF355B74179035)

> PDU(Protocol Data Unit)는 각 계층에서 데이터를 다루는 단위입니다.

### 1. 네트워크 액세스 계층 (Network Access Layer)
- 역할: 실제 물리적 네트워크(이더넷, Wi-Fi 등)에서 데이터 전송을 담당합니다. OSI 7계층의 물리 계층과 데이터 링크 계층을 포괄합니다.
- 주요 기능:
  - MAC 주소 기반 데이터 전달
  - 프레임 생성 및 에러 검출
  - 전기 신호, 무선 신호 등으로 데이터 변환
- 대표 프로토콜: Ethernet, Wi-Fi 등
- 장비: 스위치, 브릿지 등
- PDU: 프레임(Frame)

### 2. 인터넷 계층 (Internet Layer)
- 역할: 네트워크 간 데이터 전달, 라우팅, 논리적 주소(IP) 지정
- 주요 기능:
  - IP 주소를 통한 목적지 지정
  - 라우터를 통한 경로 설정 및 패킷 전달
- 대표 프로토콜: IP(IPv4/IPv6), ARP, ICMP, RARP
- 장비: 라우터
- PDU: 패킷(Packet)

### 3. 전송 계층 (Transport Layer)
- 역할: 호스트 간 신뢰성 있는 데이터 전송, 포트 번호 기반 통신
- 주요 기능:
  - 데이터의 신뢰성 보장(TCP)
  - 빠른 데이터 전송(UDP)
  - 흐름 제어, 오류 제어, 데이터 분할 및 재조립
- 대표 프로토콜: TCP, UDP
- 장비: 게이트웨이
- PDU: 세그먼트(Segment, TCP), 데이터그램(Datagram, UDP)

### 4. 응용 계층 (Application Layer)
- 역할: 사용자와 가장 가까운 계층, 실제 서비스 제공
- 주요 기능:
  - 웹, 이메일, 파일 전송 등 다양한 네트워크 서비스 제공 
  - 애플리케이션 간 데이터 송수신
- 대표 프로토콜: HTTP, FTP, SMTP, DNS 등
- PDU: 데이터(Data), 메시지(Message)

## 부가질문

## Reference

https://shlee0882.tistory.com/110