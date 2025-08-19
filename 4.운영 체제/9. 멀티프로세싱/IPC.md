# IPC (Inter-Process Communication, 프로세스 간 통신)

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FdmK4z1%2FbtsPO8NfDpT%2FAAAAAAAAAAAAAAAAAAAAAGUO4bu9j7MMeaUrvSvfYkMuwKve8du91oNsZhz9p5CE%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DjM8BfCPytpLjL9Ubh%252BRUGiLwU%252Fg%253D)

> 정의 : 운영체제 내에서 서로 독립적인 프로세스들이 데이터를 주고 받거나 자원을 공유하는 메커니즘.
각 프로세스는 메모리, CPU 시간, 파일과 같은 자원을 독립적으로 관리하기 때문에 다른 프로세스와 직접적으로 데이터를 공유할 수 없습니다.
따라서 프로세스 간 데이터를 주고받기 위한 여러가지 통신 방법이 필요함.

---

## 1. IPC의 주요 목적

### **1. 데이터 교환** : 서로 다른 프로세스 간에 데이터를 주고받기 위해 사용


### **2. 자원 공유** : 파일, 메모리 등 자원을 여러 프로세스가 동시에 사용할 수 있게 함


### **3. 동기화** : 여러 프로세스가 **동시에 실행될 때** 충돌을 방지하고 **정확한 동작**을 보장하기 위해 사용됨


### **4. 프로세스 제어** : 한 프로세스가 다른 프로세스의 실행을 제어하거나 멈추게 할 수 있다

---

## 2. IPC의 주요 기법

### 1. 공유 메모리(Shared Memory)

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2F1sUin%2FbtsPLXzI1a5%2FAAAAAAAAAAAAAAAAAAAAAIdsNAIZoa_VuurIy5Ry6ZcW16tjXhrWHLCFuzx9B1fB%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DYiJuqQC4tSr77YDLMBCdP6SyYgw%253D)

**특징** : 여러 프로세스가 같은 메모리 공간을 공유함으로써 데이터를 주고받는 방식.


공유 메모리 공간을 이용해 데이터를 **직접 접근**할 수 있기 때문에 **매우 빠른 속도**로 통신할 수 있다.
**장점** : 데이터 교환 속도가 빠르다.


**단점** : 프로세스 간에 메모리 접근을 관리하기 위한 **동기화 기법(세마포어 등)**이 필요


**예시** : 운영체제는 공유 메모리 영역을 할당하고, 여러 프로세스가 그 메모리 공간에 데이터를 읽고 씀

### 2. 소켓(Socket)

**특징** : 네트워크를 통해 데이터를 주고받을 수 있는 통신 방식이다. IPC뿐만 아니라
네트워크 상에서 **서버와 클라이언트** 간에 데이터를 주고받는 데에도 사용된다.

**장점** : 네트워크를 통해 **원격지 프로세스 간 통신**이 가능합니다

**단점** : 속도가 상대적으로 느리며, 설정이 복잡하다

**예시** : 웹 서버와 클라이언트가 소켓을 통해 통신하는 구조

---

### 3. 세마포어(Semaphore)


**특징** : 프로세스 간 동기화와 자원 접근 관리를 위해 사용되는 기법.

세마포어는 프로세스들이 **공유 자원에 동시 접근하지 못하도록 제어**하는데 사용됨.

**장점** : 여러 프로세스가 동시에 자원에 접근하지 않도록 **동기화**를 관리할 수 있다.

**단점** : 구현과 관리가 복잡할 수 있으며, 교착 상태(DeadLock)가 발생할 수 있다

**예시** : 임계 구역(critical section) 문제 해결을 위해 세마포어를 사용해 자원 접근 조정

---

### 4. 메시지 큐

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2Fddnksa%2FbtsPPk7Z0ee%2FAAAAAAAAAAAAAAAAAAAAAAYGX2tdB-mcQXJTlm1f02dv6xKl7EWDd8XiqHMDBHs2%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DQJWP74Z1KsUh3xk456TaETow5O8%253D)


**특징** : 메시지를 저장하는 큐를 통해 프로세스 간 데이터를 주고받는 방식.
메세지는 FIFO 방식으로 큐에 저장되며, 비동기적으로 전송이 가능함

**장점** : 비동기 방식으로 데이터를 주고받을 수 있어 프로세스 간 동작을 독립적으로 관리할 수 있다.

**단점** : 메시지 전송 속도가 비교적 느릴 수 있으며, 큐가 꽉 차면 메시지가 유실될 수 있다.

**예시** : 한 프로세스가 메시지를 큐에 넣고, 다른 프로세스가 나중에 메시지를 꺼내 읽음

---

### 5. 파이프(pipes)

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FC3nZs%2FbtsPLmGMvGS%2FAAAAAAAAAAAAAAAAAAAAAHtiZSiujw6doAvEvep_vC7Wwujqs_TbqtA0l4KtlppJ%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3D7sgjHkYpJkkqlBIH9CfBtgXoqcw%253D)

**특징** : 파이프는 한 프로세스의 출력 데이터를 다른 프로세스의 입력 데이터로 전달하는 방식이다.
기본적으로 **단방향** 통신만 가능하며, 두 프로세스는 같은 호스트 내에서 동작해야 함.

**장점** : 설정이 간단하고, 간단한 데이터 교환에 적합하다.

**단점** : **단방향** 통신이 기본이며, 데이터 크기에 제한이 있을 수 있음.

**예시** : | 파이프 연산자를 사용해 명령어의 출력을 다른 명령어의 입력으로 전달하는 구조 ( ls|grep )

---

### 6. 메모리 맵 파일 (Memory-Mapped File)
**특징** : 파일의 내용을 메모리에 맵핑하여 프로세스가 마치 메모리처럼 파일을 쓸 수 있게 하는 방식이다.
여러 프로세스가 같은 파일을 공유함으로써 데이터를 교환할 수 있다.

**장점** : 파일 입출력보다 속도가 빠르며, 큰 파일을 효율적으로 관리할 수 있다.

**단점** : 공유된 메모리 공간에 대한 동기화가 필요함.

**예시** : 파일을 메모리에 맵핑하여 여러 프로세스가 동시에 접근 가능

---

## IPC 주요 기법 비교
| 기법 | 핵심 개념/특징 | 장점 | 단점 | 동기화 필요 | 예시 |
|---|---|---|---|---|---|
| 공유 메모리 (Shared Memory) | 여러 프로세스가 동일 메모리 영역에 직접 접근 | **매우 빠름**(복사 없음) | 동기화 복잡 | 필요(세마포어/뮤텍스) | 생산자–소비자 버퍼 |
| 소켓 (Socket) | 네트워크(로컬/원격) 바이트 스트림 통신 | 원격 통신 가능, 범용 | 비교적 느림, 설정 복잡 | 보통 불필요(프로토콜로 해결) | 웹 서버–클라이언트 |
| 세마포어 (Semaphore) | 공유자원 접근을 제어하는 **동기화** 프리미티브 | 경쟁 상태 방지 | 설계 난도↑, 데드락 위험 | 해당 없음(자체가 동기화) | 임계구역 보호 |
| 메시지 큐 (Message Queue) | 커널 큐에 메시지를 넣고 빼는 비동기 통신 | 결합도 낮음, 비동기 | 속도 느릴 수 있음, 큐 포화 | 보통 불필요 | 작업 지시 전달 |
| 파이프 (Pipe) | 한 프로세스 출력 → 다른 프로세스 입력(단방향) | 간단, 동일 호스트에 적합 | 기본 단방향, 크기 제한 | 보통 불필요 | `ls | grep` |
| 메모리 맵 파일 (Memory-Mapped File) | 파일을 메모리에 매핑해 공유 | 큰 파일 효율적, 파일 I/O보다 빠름 | 일관성/동기화 필요 | 필요(세마포어/뮤텍스) | 다중 프로세스 로그 뷰 |

---


---

## IPC의 중요한 고려 사항
1. 동기화 문제
   - 프로세스들이 공유 자원에 동시에 접근하려 할 때, 데이터 충돌이나 일관성 문제가 발생할 수 있다.
   이를 방지하기 위해 **세마포어, 뮤텍스**와 같은 동기화 기법이 필요함
2. 보안 문제
   - 여러 프로세스 간의 통신에서는 데이터 유출이나 공격에 대비한 보안 대책이 필요함. 
    특히 **소켓 통신**과 같은 네트워크 기반의 IPC에서는 **암호화 와 인증**이 중요함.
3. 성능
   - IPC 방법에 따라 성능이 크게 다를 수 있습니다. 예를 들어 **공유 메모리**는 매우 빠르지만
   동기화 문제가 발생할 수 있고, **소켓**은 원격 통신이 가능하지만 성능이 떨어질 수 있다.
Reference
- https://conding-note.tistory.com/236