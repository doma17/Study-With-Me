# 컴퓨터 구성 요소 정리

## 1. 개요
컴퓨터 시스템은 **CPU**, **DMA 컨트롤러**, **메모리**, **타이머**, **디바이스 컨트롤러** 등으로 구성되어 있습니다.  
각 요소가 맡은 역할을 이해하면 운영체제와 하드웨어가 어떻게 상호작용하는지 알 수 있습니다.

---

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FUx1Q6%2FbtsCOB9StsI%2FAAAAAAAAAAAAAAAAAAAAAMzyYtnW5KT_vZCcrylLu9CWmg6Ky1CKVar4ZTvUJfbr%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DNVzWxGh6B0bh6Rx9oU3CmySPBbo%253D)

## 2. CPU (Central Processing Unit)
- #### **역할**: 프로그램 명령어를 해석·실행하고, 연산·제어 기능을 수행하는 ‘일꾼’
- #### **주요 구성**
    1. **제어 장치 (Control Unit, CU)**
        - 메모리에서 명령어를 읽어 해석하고, 연산·입출력 순서를 결정
        - 장치 간 통신을 제어
    2. **산술논리연산 장치 (Arithmetic Logic Unit, ALU)**
        - 산술 연산(+, –)과 논리 연산(AND, OR, XOR 등)을 수행
    3. **레지스터 (Register)**
        - CPU 내부의 초고속 임시 저장소
        - 메모리보다 수십 배 빠른 접근 속도
        - **프로그램 카운터(PC)**, **명령 레지스터(IR)**, **누산기(ACC)** 등 다양한 목적별 레지스터 존재

### 2.1. 기본 동작 흐름
1. #### **Fetch**: 제어 장치가 메모리에서 다음 명령어를 읽어 레지스터에 저장
2. #### **Decode**: 제어 장치가 명령어를 해석
3. #### **Execute**: ALU가 데이터를 연산
4. #### **Write-back**: 결과를 다시 레지스터 또는 메모리에 저장

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FcbWOnG%2FbtsCRhwKS1T%2FAAAAAAAAAAAAAAAAAAAAAJJ6FDiGae5BZVRxi1jI954Z-I2IoPZ2PitM2mRi-O5b%2Fimg.png%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3Dn%252BmCfsRWVTPTzJ5K7FbDLTHd%252B%252FY%253D)

### 2.2. 인터럽트 (Interrupt)
- #### **정의**: 외부 장치나 프로그램이 CPU에게 “지금 멈추고 이 작업 먼저 처리해 주세요” 신호
  - #### **종류**
      - **하드웨어 인터럽트**: 키보드·마우스·타이머 등 I/O 장치에서 발생
      - **소프트웨어 인터럽트(Trap)**: 시스템콜 호출, 예외 상황(0으로 나누기 등)에서 발생
      ```
      public class InterruptExample {
        public static void main(String[] args) throws InterruptedException {
          Thread worker = new Thread(() -> {
              try {
                  while (!Thread.currentThread().isInterrupted()) {
                      // 작업 수행
                      System.out.println("Working...");
                      Thread.sleep(500);  // 블로킹 호출: interrupt 시 예외 발생
                  }
              } catch (InterruptedException e) {
                  System.out.println("Interrupted during sleep!");
                  // 필요 시 플래그 재설정:
                  Thread.currentThread().interrupt();
              }
              System.out.println("Thread exiting.");
          });

          worker.start();
          Thread.sleep(2000);    // 메인 스레드 대기
          worker.interrupt();    // 작업 스레드에 중단 요청
      }
    }
  ```
- #### **처리 순서**
    1. 인터럽트 발생 → 현재 작업 중단
    2. **인터럽트 벡터**를 통해 해당 **인터럽트 핸들러** 호출
    3. 처리 완료 후 원래 작업 재개

---

## 3. DMA 컨트롤러 (Direct Memory Access)

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdna%2FXRliB%2FbtsCRihGbDH%2FAAAAAAAAAAAAAAAAAAAAAJlSHNZv1EiWChn8vNcdUNoLGEE-benx5V80INOmnVJE%2Fimg.jpg%3Fcredential%3DyqXZFxpELC7KVnFOS48ylbz2pIh7yKj8%26expires%3D1756652399%26allow_ip%3D%26allow_referer%3D%26signature%3DGZUZTbbyv%252Bm%252FEda3Q1E0q5IazAU%253D)

- #### **역할**: I/O 장치가 CPU 개입 없이 메모리와 직접 데이터를 주고받도록 지원
- #### **이점**
    - CPU 부하 감소 → 고속 대용량 데이터 전송 시 CPU 여유
    - 입출력 작업 수행 중에도 CPU는 다른 연산 가능
- #### **동작 원리**
    1. CPU가 DMA에게 “메모리 주소 A에서 장치로 B바이트 전송” 요청
    2. DMA가 메모리 버스 점유 → 직접 데이터 전송
    3. 완료 후 CPU에 인터럽트 발생

---

## 4. 메모리 (Memory)
- #### **역할**: 실행 중인 프로그램과 데이터를 일시 저장하는 ‘작업장’
- #### **종류**
    - **레지스터** < **캐시(Cache)** < **주기억장치(RAM)** < **보조기억장치(SSD/HDD)**
- #### **RAM(Random Access Memory)**
    - 읽기/쓰기 자유로운 휘발성 메모리
    - **DDR (Double Data Rate)** SDRAM: 클록 상승·하강 엣지에서 데이터 전송
        - 최신 세대: **DDR5**
- #### **메모리 계층 구조**(Memory Hierarchy)
    1. 레지스터 (가장 빠름)
    2. L1/L2/L3 캐시
    3. 주기억장치(RAM)
    4. 보조기억장치(디스크)

---

## 5. 타이머 (Timer)
- #### **역할**: 일정 시간마다 인터럽트를 발생시켜 **스케줄링**·**시간 제한** 기능 제공
- #### **용례**
    - 운영체제의 **프로세스 스케줄러**가 타임 슬라이스(time-slice)를 관리
    - 특정 작업에 **타임아웃**을 설정

---

## 6. 디바이스 컨트롤러 (Device Controller)
- #### **역할**: 특정 I/O 장치(디스크, 네트워크 카드, 프린터 등)를 제어하는 하드웨어
- #### **구성**
    - 자체 **레지스터**와 **DMA 채널**을 갖추고, CPU와 메모리 간 통신 중개
    - 운영체제로부터 **장치 드라이버** 소프트웨어를 통해 명령 수신
- #### **동작 흐름**
    1. CPU → 디바이스 컨트롤러에 **명령어** 쓰기
    2. 컨트롤러가 장치 제어, 완료 후 **인터럽트** 발생
    3. 운영체제가 인터럽트 핸들러에서 결과 처리

---

## 7. 추가 보충: 버스 구조와 메모리 맵 I/O
- #### **시스템 버스**: CPU, 메모리, I/O 장치를 연결하는 공통 통신 통로
- #### **메모리 맵 I/O**: I/O 장치 레지스터를 물리 메모리 주소 공간에 매핑
- #### **포트 맵 I/O**: 별도의 주소 공간(port)으로 I/O 장치 제어

---

## 8. 전체 요약
| 구성 요소            | 역할                                           |
|---------------------|-----------------------------------------------|
| CPU                 | 명령어 해석·수행, 연산·제어                     |
| 레지스터            | CPU 내부 임시 저장소, 고속 접근               |
| ALU                 | 산술·논리 연산                                   |
| 인터럽트            | 우선 순위에 따른 작업 전환                     |
| DMA 컨트롤러        | I/O ↔ 메모리 직접 데이터 전송                   |
| 메모리 (RAM)        | 프로그램·데이터 일시 저장                        |
| 타이머              | 주기적 인터럽트로 스케줄링·타임아웃 제어         |
| 디바이스 컨트롤러   | I/O 장치 제어, 장치 드라이버와 인터페이스        |

이 구조를 통해 운영체제는 **효율적 자원 관리**와 **안전한 장치 제어**를 달성합니다.  