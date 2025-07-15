# TCP와 UDP

TCP와 UDP는 네트워크 통신의 핵심인 전송계층에서 작동하는 프로토콜입니다. 

![TCP/UDP](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRg0jdmAL7n0ybEKVbnQ6xWxAEPg-7y67-kjQ&s)

## TCP(Transmission Control Protocol): 신뢰성을 책임지는 연결형 프로토콜

TCP는 신뢰성과 데이터 순서 보장이 중요한 통신에 사용되는 연결 지향형 프로토콜입니다.
데이터를 전송하기 전에 반드시 상대방과 연결을 설정하고, 통신이 끝나면 연결을 해체하는 과정을 거칩니다.

### 1. 주요 특징 및 동작 과정

- 연결지향 (Connection-Oriented)

![4-way-handshake](https://mblogthumb-phinf.pstatic.net/MjAxNzA0MThfMTA0/MDAxNDkyNTA2NzEyNzUy.1cxOSe3iprkos7F0tBgrwzCZ_xLpT8HlMnDVIo_ziTwg.kILtTcOE1dGISZlQI_7uiTcbQ4kU0CWngPkEQpaf6UEg.GIF.bloodsoda/Image2001.gif?type=w800)

  - 3-Way Handshake: 데이터 전송 전, 송신자와 수신자는 3단계의 과정을 통해 연결을 설정합니다.
  	1. SYN: 클라이언트가 서버에 연결을 요청하는 패킷
    2. SYN+ACK: 서버가 요청을 수락하고, 클라이언트에게 연결을 요청하는 응답
    3. ACK: 클라이언트가 서버의 요청을 수락하며, 이로써 연결이 확립
  - 4-Way Handshake: 연결을 종료할 때도 4단계의 과정을 거침

- 높은 신뢰성 (High Reliability)
  - 데이터 순서 보장: TCP는 데이터를 세그먼트 단위로 나누어 각 세그먼트 순서 번호(Sequence Number)를 부여합니다.
  - 오류 검출 및 재전송: 각 세그먼트에 대해서 ACK를 보냅니다. 일정 시간 이내에 ACK가 도착하지 않으면, 송신 측에서 데이터가 유실되었다고 판단하고 해당 세그먼트를 재전송합니다.

- 흐름 제어 (Flow Control)
  - 수신 측이 처리할 수 있는 데이터의 량을 송신 측에 알리는 기능입니다. 
  - 슬라이딩 윈도우(Sliding Window) 방식을 통해 수신 측의 버퍼가 넘치치 않도록 데이터 전송량을 동적으로 조절합니다.

- 혼잡 제어 (Congestion Control)
  - 네트워크의 혼잡 상태를 파악하고, 데이터 전송 속도를 조절하여 네트워크 안정성을 유지합니다.
  - Slow Start, Congestion Avoidance 등의 알고리즘을 사용합니다. 

> HTTP/HTTPS, FTP, SMTP, POP3 등 신뢰성이 중요한 서비스에서 사용됩니다.

### 2. TCP 헤더 구조

![TCP 헤더 구조](https://www.pynetlabs.com/wp-content/uploads/2024/01/tcp-header-format.jpeg)

TCP 헤더는 최소 20바이트로, 신뢰성 제어를 위한 다양한 필드(순서 번호, ACK 번호, 윈도우 크기, 플래그 등)를 포함합니다.

## UDP (User Datagram Protocol): 속도를 우선하는 비연결형 프로토콜

UDP는 신뢰성보다 속도와 실시간성이 중요한 통신에 사용되는 비연결형(Connectionless) 프로토콜입니다. TCP와 같은 복잡한 제어 기능이 없어 오버헤드가 적고 전송 속도가 빠릅니다.

### 1. 주요 특징 및 동작 과정

- 비연결형 (Connectionless)
  - 데이터를 보내기 전에 Handshake 과정이 없습니다.
  - 단순히 수신사의 IP 주소와 포트 번호만으로 데이터를 전송합니다.
- 비신뢰성 (Unreliable)
  - 데이터 패킷의 순서를 보장하지 않습니다.
  - 패킷이 유실되거나 중복되어도 확인하거나 재전송하지 않습니다.
  - 데이터 전송의 신뢰성을 애플리케이션 계층에서 직접 구현해야 합니다.
- 흐름/혼잡 제어 없음
- 데이터그램 (Datagram) 단위 전송
  - 데이터를 독립적인 메세지 단위인 데이터그램으로 전송합니다.


> 실시간 스트리밍(동영상, 음성 통화 (VoIP), WebRTC), 온라인 게임, DNS, 실시간 모니터링에서 사용됩니다.

### 2. UDP 헤더 구조

![UDP 헤더 구조](https://blog.kakaocdn.net/dna/v9sjb/btqEInCmCFr/AAAAAAAAAAAAAAAAAAAAAASaXfPHzl0BpnPoQuvbrJCd8Iwhi8yc1I08vXnSlSkI/img.png?credential=yqXZFxpELC7KVnFOS48ylbz2pIh7yKj8&expires=1753973999&allow_ip=&allow_referer=&signature=PR7mzvq68rLqgOhZEPhQtEOyUcE%3D)
UDP 헤더는 8바이트(출발지/목적지 포트, 길이, 체크섬)로 매우 단순하여 처리 속도가 빠릅니다.

## 부가질문

### TCP와 UDP 중 어느 프로토콜이 Checksum을 수행할까요?

TCP와 UDP 모두 헤더에 체크섬 필드를 가지고 있어 오류 검출을 수행합니다.

- TCP: 체크섬 사용이 필수입니다. 이를 통해 헤더와 데이터의 무결성을 보장합니다.
- UDP: 체크섬 사용이 선택 사항입니다. 송신 측에서 체크섬 필드를 모두 0으로 채워 보내면 수신 측은 체크섬 검사를 수행하지 않습니다

### 4-Way Handshake 없이 TCP 연결 종료가 가능한가?

실제 네트워크 환경에서 빈번하게 발생하는 비정상 종료입니다.

서버는 클라이언트가 사라졌다는 사실을 즉시 알지 못합니다. 이로 인해 서버 측에는 연결이 아직 살아있는 것처럼 보이는 하프 오픈(Half-Open) 상태의 TCP 소켓이 남게 됩니다.
이러한 하프 오픈 상태의 연결은 서버의 자원(메모리, 파일 디스크립터 등)을 불필요하게 점유하므로 반드시 처리되어야 합니다. 서버는 다음과 같은 방법으로 이런 비정상 종료를 처리합니다.

- TCP Keep-Alive 메커니즘
  - 서버는 특정 시간 동안 아무런 데이터도 수신하지 못하면, 클라이언트가 살아있는지 확인하기 위해 작은 Keep-Alive 프로브(Probe) 패킷을 보냅니다.
  - 서버가 여러 번 프로브를 보냈음에도 클라이언트로부터 아무런 응답(ACK)이 없으면, 서버는 클라이언트와의 연결이 끊어졌다고 판단하고 해당 소켓 연결을 강제로 종료합니다.
- 애플리케이션 레벨 타임아웃 (Application-Level Timeout)
  - 애플리케이션 서버(Tomcat, Netty)는 자체적으로 연결 타임아웃(Connection Timeout) 또는 유휴 타임아웃(Idle Timeout) 기능을 제공합니다.
  - 설정된 시간 동안 해당 소켓을 통해 아무런 요청이나 데이터 교환이 없으면, 서버는 해당 연결이 비활성 상태라고 판단하고 일방적으로 연결을 닫습니다
  - Spring Boot의 server.tomcat.connection-timeout 설정

### Checksum을 통해 오류를 정정할 수 있을까?

아닙니다. Checksum의 기능은 오류를 검출하는 기능입니다. 상위 계층에서 재전송 로직등을 활용해서 데이터를 정정해야 합니다.

## Reference

https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=bloodsoda&logNo=220986271516
https://webstone.tistory.com/147