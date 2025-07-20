# 계층구조(Layered Architecture)와 PDU(Protocol Data Unit)

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FcqXN4i%2FbtrH0tNCoYs%2FAAAAAAAAAAAAAAAAAAAAAOvg7nXDtY1TpXpqZLkoIyiFoa5ZfCARQRSvD9jLIouT%2Fimg.jpg%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1753973999%26allow_ip%3D%26allow_referer%3D%26signature%3DnYSbwPt%252BZN9VFfqR9Pcpr49O9aU%253D)

> 네트워크 통신을 설계할 때 **계층구조(Layered Architecture)** 를 사용하면, 각 계층이 수행하는 기능을 분리·명확화하여 유지보수성과 확장성을 높일 수 있습니다. 대표적인 모델로는 OSI 7계층 모델과 TCP/IP 4계층 모델이 있습니다.

---

## 1. 계층구조 개요

- **목적**  
  - 복잡한 네트워크 기능을 여러 계층으로 분리하여 모듈화  
  - 계층 간 인터페이스 정의로 구현 독립성 보장  
  - 상위 계층은 하위 계층의 상세 구현을 몰라도 기능 활용 가능  

- **주요 이점**  
  - **관심사 분리**: 각 계층이 자신의 역할에만 집중  
  - **재사용성**: 표준화된 계층을 다양한 시스템에 적용  
  - **호환성**: 서로 다른 벤더·기술 사이의 상호연동 용이  

- **대표 모델**  
  - OSI 7계층(Model)  
  - TCP/IP 4계층(Internet Protocol Suite)

---

## 2. PDU(Protocol Data Unit)

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FdLXTBy%2FbtsHqJbGxYv%2FAAAAAAAAAAAAAAAAAAAAAJslbwLuUS-HTrvVwhr7uvpXutcUItC42mOX1LOxkvkL%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1753973999%26allow_ip%3D%26allow_referer%3D%26signature%3DlK4ggKHF%252F1kdKIocmscwxJt1Cec%253D)

**PDU(프로토콜 데이터 단위)** 는 계층 간 데이터 교환 시 사용하는 단위 단위(메시지)이며, 각 계층마다 이름과 구조가 다릅니다.

| 계층               | PDU 명칭         | 설명                                    |
|------------------|---------------|---------------------------------------|
| 물리 계층         | 비트(Bit)      | 1과 0의 전기·광학 신호 단위                   |
| 데이터 링크 계층    | 프레임(Frame)  | MAC 헤더/트레일러를 포함한 전송 단위           |
| 네트워크/인터넷 계층 | 패킷(Packet)   | IP 헤더를 포함한 논리 주소 기반 전송 단위      |
| 전송 계층         | 세그먼트/데이터그램(Segment/Datagram) | TCP/UDP 헤더를 포함한 종단 간 전송 단위 |
| 응용 계층         | 메시지(Message) | 응용 프로그램 간 교환되는 데이터 단위          |

- ## **캡슐화(Encapsulation)**  
  ![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FcMvsX4%2FbtrH0tUntWQ%2FAAAAAAAAAAAAAAAAAAAAAA_CZAYQeC7h_fk-6kgUF-e59QZ-mzps4n2WgBvBIH7f%2Fimg.jpg%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1753973999%26allow_ip%3D%26allow_referer%3D%26signature%3D3RDfNoKkIzmT0%252BcNQgTNUF07MrA%253D)
  - 상위 계층의 PDU를 하위 계층의 헤더(Header)·트레일러(Trailer)로 감싸면서 전송 단위 생성  
  - 예: 메시지 → 세그먼트 → 패킷 → 프레임 → 비트  

- ## **탈캡슐화(Decapsulation)**  
  ![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FnWyGG%2FbtrH4882B7O%2FAAAAAAAAAAAAAAAAAAAAALPlo_bVRi0lQKoMtwBj4IgOK-ilK2n6tzUMXTCKJQCO%2Fimg.jpg%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1753973999%26allow_ip%3D%26allow_referer%3D%26signature%3DGw3PtWqruSX8psYwH071jCqpa9U%253D)
  - 수신 측은 물리 계층으로부터 받은 비트를 역으로 계층별로 해체하며 원본 메시지 복원  

---

> **요약**:  
> 네트워크 계층구조를 통해 복잡성을 분리하고, 각 계층의 PDU를 이해함으로써 데이터가 어떻게 캡슐화·전송·복원되는지 파악할 수 있습니다.


Reference

https://shortcoding.tistory.com/305

https://jh7722.tistory.com/94